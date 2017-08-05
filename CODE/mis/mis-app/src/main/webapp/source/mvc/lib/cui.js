(function(window) {

// 申明以 ES5严格标准运行
"use strict";
window.CUI =  {config:{}};
 
function getScriptLocation() {
	var r = new RegExp("(^|(.*?\\/))(cui.js)(\\?|$)"), s = window.document
			.getElementsByTagName('script'), src, m, l = "", appName = "", appInitMethod = "";
	for (var i = 0, len = s.length; i < len; i++) {
		src = s[i].getAttribute('src');
		appName = s[i].getAttribute('app-name');
		appInitMethod = s[i].getAttribute('app-init-method');
		if (src) {
			var m = src.match(r);
			if (m) {
				l = m[1];
				break;
			}
		}
	}
	return [ l, appName, appInitMethod ];
}

var cuiLoadInfo = getScriptLocation(),host = cuiLoadInfo[0], 
	appName =  cuiLoadInfo[1], appInitMethod =cuiLoadInfo[2],
	headNode = window.document.head || window.document.getElementsByTagName("head")[0];
 
 /**
 * 判断一个对象是否是数组
 * @param  {Object}  obj
 * @return {boolean}
 */
function isArray (obj) {
    return Object.prototype.toString.call(obj) === "[object Array]";
}

/**
 * 判断一个对象是否是函数
 * @param  {Object}  obj
 * @return {boolean}
 */
function isFunction (obj) {
    return Object.prototype.toString.call(obj) === "[object Function]";
}

/**
 * 异步加载一个js文件
 * 
 * @param {String}
 *            url js文件的绝对地址
 * @param {Function}
 *            回调函数
 * @return {Void}
 */
window.CUI.loadJs = function (url, callback) {
	var scriptNode = window.document.createElement("script");
	scriptNode.type = "text/javascript";
	scriptNode.async = "false";
	scriptNode.defer = "true";
	if ("onload" in scriptNode) {
		scriptNode.onload = function() {
			var moduleId, moduleDef;
			isFunction(callback) && callback();
		};
	} else {
		scriptNode.onreadystatechange = function() {
			if (/loaded|complete/.test(this.readyState)) {
				this.onreadystatechange = null;
				isFunction(callback) && callback();
			}
		};
	}
	/**
	 * 我们先定义事件函数，因为IE有可能在给src赋值后便立即开始加载相应 的脚本文件，而不管我们是否已经将其插入到DOM中
	 */
	scriptNode.src = url;
	headNode.appendChild(scriptNode);
}
/**
 * 异步加载一个css文件
 * 
 * @param {String}
 *            url js文件的绝对地址
 * @param {Function}
 *            回调函数
 * @return {Void}
 */
window.CUI.loadCss = function(url,useHost) {
	if(useHost){
		url = host+url;
	}
	
	var linkNode = window.document.createElement("link");
	linkNode.rel="stylesheet";
	linkNode.type="text/css"
	linkNode.async = "true";
	linkNode.defer = "true";
	linkNode.href = url;
	headNode.appendChild(linkNode);
}

/**
 * 解析config配置的js的真正路径
 * @param  [String]  modules
 * @return {Array,Array}
 */
function parseDeps (modules) {
	var depMap = {};
	 	var paths = window.CUI.config.paths,path = [],as = [];
	 	for(var i=0; i<modules.length; i++){
	 		var moduleInfo = paths[modules[i]];
	 		if(moduleInfo.depends){
	 			 for(var j =0 ; j < moduleInfo.depends.length; j++){
	 				var modName = moduleInfo.depends[j];
	 				if(modName.indexOf('css!') != -1){
	 					if(!depMap[modName]){
    	 					var url = (host+modName.substring(4)).replace(/\/\//g,'');
    	 					window.CUI.loadCss(url);
    	 					depMap[modName] = true;
	 					}
	 				}else{
	 					if(!depMap[modName]){
    	 					path.push(paths[modName].path);
    	 					depMap[modName] = true;
    	 					as.push(modName);
	 					}
	 				}
	 			 }
	 		}
	 		if(!depMap[modules[i]]){
	 			path.push(moduleInfo.path);
    	 		depMap[modules[i]] = true;
    	 		as.push(modules[i]);
	 		}
	 	}
	 	return [path,as];
}

var factoryEvent,modules = 0, deps;

function moduleLoad(modules,factory){
	 	var url = (host+deps[modules]).replace(/\/\//g,'');
		
	if(url.indexOf('.js') === -1){
		url += '.js';
	}
	
		if(modules == (deps.length-1)){
			window.CUI.loadJs(url,function(){
				factoryEvent = factory();
				if(appInitMethod){
					factoryEvent[appInitMethod]();
				}else{
					factoryEvent['initialize']();
				}
				modules = 0;
    		});
		
	}else{
		window.CUI.loadJs(url,function(){
    			if(modules < (deps.length-1)){
    				modules++;
    				moduleLoad(modules,factory);
    			}
    		});
	}
}

window.CUI.use = function(depArray, factory, initMethod){
		function unique(arr) {
		    var result = [], hash = {};
		    for (var i = 0, elem; (elem = arr[i]) != null; i++) {
		        if (!hash[elem]) {
		            result.push(elem);
		            hash[elem] = true;
		        }
		    }
		    return result;
		}
		window.resizeArray = [];
		window.onresize = function() {
			var ay = unique(window.resizeArray);
			for(var i=0;i<ay.length;i++){
				ay[i]();
			}
		};
	
    	if(initMethod){
    		appInitMethod = initMethod;
    	}
		if(!validateHashJquery('jquery')){
			window.CUI.loadJs(host+'jquery.js',function(){
    			deps = parseDeps(depArray)[0];
    			moduleLoad(0,factory);
			});
		}else{
			deps = parseDeps(depArray)[0];
			moduleLoad(0,factory);
		}
};

function validateHashJquery(name) {
	var r = new RegExp("(^|(.*?\\/))("+name+".js)(\\?|$)"), s = window.document
			.getElementsByTagName('script'), src, m, l = false;
	for (var i = 0, len = s.length; i < len; i++) {
		src = s[i].getAttribute('src');
		if (src) {
			var m = src.match(r);
			if (m) {
				l = true;
				break;
			}
		}
	}
	return l;
}

})(window);