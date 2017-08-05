
(function(window, document, undefined) {

    /**
     * 映射到它们相应的keycodes特殊键
     *
     * 这键值都不能使用keypress事件
     * 所以在这里，它们被映射到正确的keycodes keyup/keydown events
     */
    var _MAP = {
            8: 'backspace',
            9: 'tab',
            13: 'enter',
            16: 'shift',
            17: 'ctrl',
            18: 'alt',
            20: 'capslock',
            27: 'esc',
            32: 'space',
            33: 'pageup',
            34: 'pagedown',
            35: 'end',
            36: 'home',
            37: 'left',
            38: 'up',
            39: 'right',
            40: 'down',
            45: 'ins',
            46: 'del',
            91: 'meta',
            93: 'meta',
            224: 'meta'
        },

        /**
         * 支持特殊字符映射
         *
         * 这些键值只用于你想绑定 KeyUp和KeyDown事件中的一个
         */
        _KEYCODE_MAP = {
            106: '*',
            107: '+',
            109: '-',
            110: '.',
            111 : '/',
            186: ';',
            187: '=',
            188: ',',
            189: '-',
            190: '.',
            191: '/',
            192: '`',
            219: '[',
            220: '\\',
            221: ']',
            222: '\''
        },

        /**
         * 按下shift键后特殊符号对应的值的获取
         */
        _SHIFT_MAP = {
            '~': '`',
            '!': '1',
            '@': '2',
            '#': '3',
            '$': '4',
            '%': '5',
            '^': '6',
            '&': '7',
            '*': '8',
            '(': '9',
            ')': '0',
            '_': '-',
            '+': '=',
            ':': ';',
            '\"': '\'',
            '<': ',',
            '>': '.',
            '?': '/',
            '|': '\\'
        },

        _SPECIAL_ALIASES = {
            'option': 'alt',
            'command': 'meta',
            'return': 'enter',
            'escape': 'esc',
            'mod': /Mac|iPod|iPhone|iPad/.test(navigator.platform) ? 'meta' : 'ctrl'
        },

        _REVERSE_MAP,

        _callbacks = {},

        _directMap = {},

        _sequenceLevels = {},

        _resetTimer,

        _ignoreNextKeyup = false,

        _ignoreNextKeypress = false,

        _nextExpectedAction = false;

    for (var i = 1; i < 20; ++i) {
        _MAP[111 + i] = 'f' + i;
    }

    for (i = 0; i <= 9; ++i) {
        _MAP[i + 96] = i;
    }

    /**
     * 跨浏览器添加事件的方法
     *
     * @param {Element|HTMLDocument} object
     * @param {string} type
     * @param {Function} callback
     * @returns void
     */
    function _addEvent(object, type, callback) {
        if (object.addEventListener) {
            object.addEventListener(type, callback, false);
            return;
        }

        object.attachEvent('on' + type, callback);
    }

    /**
     * 拿到特殊键的事件名称
     *
     * @param {Event} e
     * @return {string}
     */
    function _characterFromEvent(e) {

        if (e.type == 'keypress') {
            var character = String.fromCharCode(e.which);

            if (!e.shiftKey) {
                character = character.toLowerCase();
            }

            return character;
        }

        if (_MAP[e.which]) {
            return _MAP[e.which];
        }

        if (_KEYCODE_MAP[e.which]) {
            return _KEYCODE_MAP[e.which];
        }

        return String.fromCharCode(e.which).toLowerCase();
    }

    /**
     * 检查两个数组是否相等
     *
     * @param {Array} modifiers1
     * @param {Array} modifiers2
     * @returns {boolean}
     */
    function _modifiersMatch(modifiers1, modifiers2) {
        return modifiers1.sort().join(',') === modifiers2.sort().join(',');
    }

    /**
     * 重置所有序列计数器除了通过的
     *
     * @param {Object} doNotReset
     */
    function _resetSequences(doNotReset) {
        doNotReset = doNotReset || {};

        var activeSequences = false,
            key;

        for (key in _sequenceLevels) {
            if (doNotReset[key]) {
                activeSequences = true;
                continue;
            }
            _sequenceLevels[key] = 0;
        }

        if (!activeSequences) {
            _nextExpectedAction = false;
        }
    }

    /**
     *发现匹配基键值，改变其回调动作 
     * @param {string} character
     * @param {Array} modifiers
     * @param {Event|Object} e
     * @param {string=} sequenceName - name of the sequence we are looking for
     * @param {string=} combination
     * @param {number=} level
     * @returns {Array}
     */
    function _getMatches(character, modifiers, e, sequenceName, combination, level) {
        var i,
            callback,
            matches = [],
            action = e.type;

        if (!_callbacks[character]) {
            return [];
        }

        if (action == 'keyup' && _isModifier(character)) {
            modifiers = [character];
        }

        for (i = 0; i < _callbacks[character].length; ++i) {
            callback = _callbacks[character][i];

            if (!sequenceName && callback.seq && _sequenceLevels[callback.seq] != callback.level) {
                continue;
            }

            if (action != callback.action) {
                continue;
            }

            if ((action == 'keypress' && !e.metaKey && !e.ctrlKey) || _modifiersMatch(modifiers, callback.modifiers)) {

                var deleteCombo = !sequenceName && callback.combo == combination;
                var deleteSequence = sequenceName && callback.seq == sequenceName && callback.level == level;
                if (deleteCombo || deleteSequence) {
                    _callbacks[character].splice(i, 1);
                }

                matches.push(callback);
            }
        }

        return matches;
    }

    /**
     * 当前事件的修饰词是什么
     * @param {Event} e
     * @returns {Array}
     */
    function _eventModifiers(e) {
        var modifiers = [];

        if (e.shiftKey) {
            modifiers.push('shift');
        }

        if (e.altKey) {
            modifiers.push('alt');
        }

        if (e.ctrlKey) {
            modifiers.push('ctrl');
        }

        if (e.metaKey) {
            modifiers.push('meta');
        }

        return modifiers;
    }

    /**
     * 阻止默认事件
     *
     * @param {Event} e
     * @returns void
     */
    function _preventDefault(e) {
        if (e.preventDefault) {
            e.preventDefault();
            return;
        }

        e.returnValue = false;
    }

    /**
     * 停止传播此事件
     *
     * @param {Event} e
     */
    function _stopPropagation(e) {
        if (e.stopPropagation) {
            e.stopPropagation();
            return;
        }

        e.cancelBubble = true;
    }

    /**
     * 实际调用的回调函数
     *
     * @param {Function} callback
     * @param {Event} e
     */
    function _fireCallback(callback, e, combo, sequence) {

        if (Keyboardtrap.stopCallback(e, e.target || e.srcElement, combo, sequence)) {
            return;
        }

        if (callback(e, combo) === false) {
            _preventDefault(e);
            _stopPropagation(e);
        }
    }

    /**
     * 处理字符键事件
     *
     * @param {string} character
     * @param {Array} modifiers
     * @param {Event} e
     * @returns void
     */
    function _handleKey(character, modifiers, e) {
        var callbacks = _getMatches(character, modifiers, e),
            i,
            doNotReset = {},
            maxLevel = 0,
            processedSequenceCallback = false;

        for (i = 0; i < callbacks.length; ++i) {
            if (callbacks[i].seq) {
                maxLevel = Math.max(maxLevel, callbacks[i].level);
            }
        }

        for (i = 0; i < callbacks.length; ++i) {
            if (callbacks[i].seq) {
                if (callbacks[i].level != maxLevel) {
                    continue;
                }

                processedSequenceCallback = true;

                doNotReset[callbacks[i].seq] = 1;
                _fireCallback(callbacks[i].callback, e, callbacks[i].combo, callbacks[i].seq);
                continue;
            }

            if (!processedSequenceCallback) {
                _fireCallback(callbacks[i].callback, e, callbacks[i].combo);
            }
        }

        var ignoreThisKeypress = e.type == 'keypress' && _ignoreNextKeypress;
        if (e.type == _nextExpectedAction && !_isModifier(character) && !ignoreThisKeypress) {
            _resetSequences(doNotReset);
        }

        _ignoreNextKeypress = processedSequenceCallback && e.type == 'keydown';
    }

    /**
     * 操作KeyDown事件
     *
     * @param {Event} e
     * @returns void
     */
    function _handleKeyEvent(e) {

        if (typeof e.which !== 'number') {
            e.which = e.keyCode;
        }

        var character = _characterFromEvent(e);

        if (!character) {
            return;
        }

        if (e.type == 'keyup' && _ignoreNextKeyup === character) {
            _ignoreNextKeyup = false;
            return;
        }

        Keyboardtrap.handleKey(character, _eventModifiers(e), e);
    }

    /**
     * 定密码是否指定一个修饰键
     *
     * @param {string} key
     * @returns {boolean}
     */
    function _isModifier(key) {
        return key == 'shift' || key == 'ctrl' || key == 'alt' || key == 'meta';
    }

    /**
     *设定一个1秒的顺序
     *
     * @returns void
     */
    function _resetSequenceTimer() {
        clearTimeout(_resetTimer);
        _resetTimer = setTimeout(_resetSequences, 1000);
    }

    /**
     * 反转map对象查找以便我们可以寻找特定的键看看能不能使用按键
     */
    function _getReverseMap() {
        if (!_REVERSE_MAP) {
            _REVERSE_MAP = {};
            for (var key in _MAP) {
 
                if (key > 95 && key < 112) {
                    continue;
                }

                if (_MAP.hasOwnProperty(key)) {
                    _REVERSE_MAP[_MAP[key]] = key;
                }
            }
        }
        return _REVERSE_MAP;
    }

    /**
     * 基于组合键选择最好的动作
     *
     * @param {string} key - character for key
     * @param {Array} modifiers
     * @param {string=} action passed in
     */
    function _pickBestAction(key, modifiers, action) {

        if (!action) {
            action = _getReverseMap()[key] ? 'keydown' : 'keypress';
        }

        if (action == 'keypress' && modifiers.length) {
            action = 'keydown';
        }

        return action;
    }

    /**
     * 结合的关键序列的事件
     *
     * @param {string} combo - combo specified in bind call
     * @param {Array} keys
     * @param {Function} callback
     * @param {string=} action
     * @returns void
     */
    function _bindSequence(combo, keys, callback, action) {

        _sequenceLevels[combo] = 0;

        function _increaseSequence(nextAction) {
            return function() {
                _nextExpectedAction = nextAction;
                ++_sequenceLevels[combo];
                _resetSequenceTimer();
            };
        }

        function _callbackAndReset(e) {
            _fireCallback(callback, e, combo);

            if (action !== 'keyup') {
                _ignoreNextKeyup = _characterFromEvent(e);
            }

            setTimeout(_resetSequences, 10);
        }

        for (var i = 0; i < keys.length; ++i) {
            var isFinal = i + 1 === keys.length;
            var wrappedCallback = isFinal ? _callbackAndReset : _increaseSequence(action || _getKeyInfo(keys[i + 1]).action);
            _bindSingle(keys[i], wrappedCallback, action, combo, i);
        }
    }

    /**
     * 将一个字符串组合键转换成数组
     *
     * @param  {string} combination like "command+shift+l"
     * @return {Array}
     */
    function _keysFromString(combination) {
        if (combination === '+') {
            return ['+'];
        }

        return combination.split('+');
    }

    /**
     * 获取一个特定的组合键信息
     *
     * @param  {string} combination key combination ("command+s" or "a" or "*")
     * @param  {string=} action
     * @returns {Object}
     */
    function _getKeyInfo(combination, action) {
        var keys,
            key,
            i,
            modifiers = [];

        keys = _keysFromString(combination);

        for (i = 0; i < keys.length; ++i) {
            key = keys[i];

            if (_SPECIAL_ALIASES[key]) {
                key = _SPECIAL_ALIASES[key];
            }

            if (action && action != 'keypress' && _SHIFT_MAP[key]) {
                key = _SHIFT_MAP[key];
                modifiers.push('shift');
            }

            if (_isModifier(key)) {
                modifiers.push(key);
            }
        }

        action = _pickBestAction(key, modifiers, action);

        return {
            key: key,
            modifiers: modifiers,
            action: action
        };
    }

    /**
     * 结合一个单一的键盘组合事件
     *
     * @param {string} combination
     * @param {Function} callback
     * @param {string=} action
     * @param {string=} sequenceName - name of sequence if part of sequence
     * @param {number=} level - what part of the sequence the command is
     * @returns void
     */
    function _bindSingle(combination, callback, action, sequenceName, level) {

        _directMap[combination + ':' + action] = callback;

        combination = combination.replace(/\s+/g, ' ');

        var sequence = combination.split(' '),
            info;

        if (sequence.length > 1) {
            _bindSequence(combination, sequence, callback, action);
            return;
        }

        info = _getKeyInfo(combination, action);

        _callbacks[info.key] = _callbacks[info.key] || [];
        
        _getMatches(info.key, info.modifiers, {type: info.action}, sequenceName, combination, level);

        _callbacks[info.key][sequenceName ? 'unshift' : 'push']({
            callback: callback,
            modifiers: info.modifiers,
            action: info.action,
            seq: sequenceName,
            level: level,
            combo: combination
        });
    }

    /**
     * 结合多个组合到同一个回调
     *
     * @param {Array} combinations
     * @param {Function} callback
     * @param {string|undefined} action
     * @returns void
     */
    function _bindMultiple(combinations, callback, action) {
        for (var i = 0; i < combinations.length; ++i) {
            _bindSingle(combinations[i], callback, action);
        }
    }

    // start!
    _addEvent(document, 'keypress', _handleKeyEvent);
    _addEvent(document, 'keydown', _handleKeyEvent);
    _addEvent(document, 'keyup', _handleKeyEvent);

    var Keyboardtrap = {

        /**
         * 绑定一个keyboardtrap事件
         *
         * @param {string|Array} keys
         * @param {Function} callback
         * @param {string=} action - 'keypress', 'keydown', or 'keyup'
         */
        bind: function(keys, callback, action) {
            keys = keys instanceof Array ? keys : [keys];
            _bindMultiple(keys, callback, action);
            return this;
        },

        /**
         * 解除 keyboardtrap 指定事件
         * @param {string|Array} keys
         * @param {string} action
         */
        unbind: function(keys, action) {
            return Keyboardtrap.bind(keys, function() {}, action);
        },

        /**
         * 触发一个已经绑定事件
         *
         * @param {string} keys
         * @param {string=} action
         */
        trigger: function(keys, action) {
            if (_directMap[keys + ':' + action]) {
                _directMap[keys + ':' + action]({}, keys);
            }
            return this;
        },

        /**
         * 重置整个键盘事件回到初始状态。
         */
        reset: function() {
            _callbacks = {};
            _directMap = {};
            return this;
        },

       /**
        * 我们要使用这一事件之前阻止这一事件的回调函数
        *
        * @param {Event} e
        * @param {Element} element
        * @return {boolean}
        */
        stopCallback: function(e, element) {

            // 如果元素有类”keyboardtrap”则不需要停止
            if ((' ' + element.className + ' ').indexOf(' keyboardtrap ') > -1) {
                return false;
            }

            // 停止输入，选择，和文本
            return element.tagName == 'INPUT' || element.tagName == 'SELECT' || element.tagName == 'TEXTAREA' || element.isContentEditable;
        },

        /**
         * 暴露_handlekey，可以通过其扩展被覆盖
         */
        handleKey: _handleKey
    };

 
    window.Keyboardtrap = Keyboardtrap;

 
    if (typeof define === 'function' && define.amd) {
        define(Keyboardtrap);
    }
}) (window, document);
