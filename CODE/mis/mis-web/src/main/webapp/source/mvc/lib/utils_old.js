!function($) {
	'use strict';
	
	//获取32位的随机数
	$.randomNumber = function(){
		var id = "";
		for (var i = 1; i <= 32; i++) {
			id += Math.floor(Math.random() * 16.0).toString(16);
		}
		return id;
	}
	
	//DOM对象的拖拽排序
	$.sortable = function(selector,items,startFn,stopFn){
		 $(selector).sortable({
			 items: items,
			 opacity: 0.8,
			 coneHelperSize: true,
			 forcePlaceholderSize: true,
			 tolerance: "pointer",
			 start : function(e, t) {
				 if(startFn) startFn(e,t);
			 },
			 stop : function(e, t) {
				if(stopFn) stopFn(e,t);
			 }
		 }).disableSelection();
	}
	
	//获取页面地址栏传递的参数对象
	$.getPageAddressParameter = function() {
		var url = location.search;
		var result = new Object();
		if (url.indexOf("?") != -1) {
			var str = url.substring(1);
			var strs = str.split("&");
			for (var i = 0; i < strs.length; i++) {
				result[strs[i].split("=")[0]] = strs[i].split("=")[1];
			}
		}
		return result;
	}
	
	// Ajax request
	$.asyncRequest = function(option){
		$.ajax({
			url : option.url,
			type : "post",
			data : option.data?option.data:{},
			dataType : 'json',
			async :option.async?option.async:false,
			success : function(result) {
				if (option.event != undefined) {
						option.event(result);
				}
			},
			error : function(XMLHttpRequest, textStatus, errorThrown) {
				if(option.errorFn){
					option.errorFn(XMLHttpRequest, textStatus, errorThrown);
					return;
				}
				var errorMsg = option.errorMsg?option.errorMsg:'请求失败!';
				layer.alert(errorMsg);
			}
		});
	}
	//获取文件的文本字符串
	$.getFileText = function(url){
		return $.ajax({url : url,type : "get",dataType : 'text',async :false,
			error : function(XMLHttpRequest, textStatus, errorThrown) {
				layer.alert('文件'+url+'加载失败!');
			}
		}).responseText;
	}

 	//屏蔽页面回车提交事件
	 $.screenEnter = function(){
			var forms = document.forms;
			for ( var int = 0; int < forms.length; int++) {
				$(forms[int])
					.bind(
							'keydown',
							function() {
								var elem = event.target, nodeNameIsInput = (elem.nodeName && elem.nodeName
										.toUpperCase() === "input"
										.toUpperCase()), nodeNameIsTextarea = (elem.nodeName && elem.nodeName
										.toUpperCase() === "textarea"
										.toUpperCase()), type = nodeNameIsInput ? elem.type
										: "";
								if ((event.keyCode === 13 && !nodeNameIsTextarea)
										|| (event.keyCode === 32 && (type === "checkbox" || type === "radio"))
										|| type === "select-multiple") {
									return false;
								}
							});
			}
	}
	//验证数据类型
	$.validateDataType = function(data) {
         return Object.prototype.toString.call(data).match(/^\[object\s(.*)\]$/)[1];
    };
	 
	//将数据插入当前textarea元素指定的光标处
	$.fn.insertContentIntoTextareaAtCursor = function(content){
		var textAreaElement = $(this).get(0);

		if (document.selection) {
			textAreaElement.focus();
			sel = document.selection.createRange();
			sel.text = content;
			sel.select();
		} else if (textAreaElement.selectionStart
				|| textAreaElement.selectionStart == '0') {
			var startPos = textAreaElement.selectionStart;
			var endPos = textAreaElement.selectionEnd;
			var restoreTop = textAreaElement.scrollTop;
			textAreaElement.value = textAreaElement.value.substring(0, startPos)
					+ content
					+ textAreaElement.value.substring(endPos,
							textAreaElement.value.length);
			if (restoreTop > 0) {
				textAreaElement.scrollTop = restoreTop;
			}
			textAreaElement.focus();
			textAreaElement.selectionStart = startPos + content.length;
			textAreaElement.selectionEnd = startPos + content.length;
		} else {
			textAreaElement.value += content;
			textAreaElement.focus();
		}
	}
	
	$.fn.HTML = function() {
		if (arguments.length)
			return $.fn.html.apply(this, arguments);
		$("input,button", this).each(function() {
			this.setAttribute('value', this.value);
		});
		$("textarea", this).each(function() {
			this.setAttribute('value', this.value);
			$(this).text(this.value);
		});
		$(":radio,:checkbox", this).each(function() {
			if (this.checked)
				this.setAttribute('checked', 'checked');
			else
				this.removeAttribute('checked');
		});
		$("option", this).each(function() {
			if (this.selected)
				this.setAttribute('selected', 'selected');
			else
				this.removeAttribute('selected');
		});
		return $.fn.html.apply(this);
	};
	
	//获取表单数据并转换成 key:value 形式的对象
	$.fn.buildFormData = function(){
		if($(this).is('form')){
			var params = $(this).serializeArray(), data = {};
			$.each(params, function(index, elm) {
				if(data[elm.name]){
					data[elm.name] += ','+elm.value;
				}else
				data[elm.name] = elm.value;
			});
			return data;
		}
		return null;
	}
	
	$.fn.serializeElement = function() {
		var  rinput = /^(?:color|date|datetime|datetime-local|email|hidden|month|number|password|checkbox|radio|range|search|tel|text|time|url|week|file)$/i,
			 rselectTextarea = /^(?:select|textarea)/i;
		
		return this.map(function(){
			return this.elements ? jQuery.makeArray( this.elements ) : this;
		})
		.filter(function(){
			return this.name && !this.disabled &&
				( this.checked || rselectTextarea.test( this.nodeName ) ||
					rinput.test( this.type ) );
		})
		.map(function( i, elem ){
			var val = jQuery( this ).val();
			if(val == null){
				val = this.value;
			}
			
			return val == null ?
				null :
				jQuery.isArray( val ) ?
					jQuery.map( val, function( val, i ){
						return elem;
					}) : elem;
		}).get();
    }
	
	$.fn.buildQueryInfo = function(){
		if($(this).is('form')){
			var params = $(this).serializeElement(), data = {}, queryTermStr = '';
			$.each(params, function(index, elm) {
				if(data[$(elm).attr('name')]){
					data[$(elm).attr('name')] += ','+$(elm).val();
				}else
				data[$(elm).attr('name')] = $(elm).val();
			  	var usemap = $(elm).attr('usemap');
				if(!usemap){
					usemap = $(elm).parent().prev().attr('usemap');
				}
				
				var filter = eval('('+usemap+')');
				if(data[$(elm).attr('name')] !== '' && data[$(elm).attr('name')] !== null){
					if($(elm).attr('name').indexOf("_startTime") > 0){
						var oldName = $(elm).attr('name');
						var index = oldName.indexOf('_startTime');
						var newName = oldName.substring(0,index);
						queryTermStr += ' '+filter.logic+' '+newName+filter.compare+'${'+oldName+'}';
					}else if($(elm).attr('name').indexOf("_endTime") > 0){
						var oldName = $(elm).attr('name');
						var index = oldName.indexOf('_endTime');
						var newName = oldName.substring(0,index);
						queryTermStr += ' '+filter.logic+' '+newName+filter.compare+'${'+oldName+'}';
					}else{
						queryTermStr += ' '+filter.logic+' '+$(elm).attr('name')+' '+filter.compare+' ${'+$(elm).attr('name')+'}';
					}
					if(filter.compare == 'like'){
						data[$(elm).attr('name')] = '%' +data[$(elm).attr('name')] +'%';
					}
				} // [ 'and aaa= ?'=123  ]
			});
			data['queryTermStr'] = queryTermStr;
			return data;
		}
	}
	
	$.insertDynamicDataForTheForm = function(formData, jqForm, options,name,val){
		if($(jqForm).find('input[type="file"]').length > 0){
			options.data[name] = val;
		}else{
			formData.push({name:name,value:val});
		}
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	//表单数据回填
	$.formDataFill = function(form, selectedRowDatas){
		$(window.frameElement).load(function(){
				var data = selectedRowDatas[0];
				if(Object.prototype.toString.call(selectedRowDatas).match(/^\[object\s(.*)\]$/)[1] !== 'Array'){
					data = selectedRowDatas;
				}
				
				var formElems = $(form).serializeElement();
				
				for(var i=0 ; i< formElems.length; i++){
					var val = data[formElems[i].name];
					if(val){
						val = val.replace(/&lt;/g,'<').replace(/&gt;/g,'>').replace(/\\/g,'').replace(/&amp;/g,'&').replace(/&middot;/g,'·').replace(/&quot;/g,'"');
					}else{
						continue;
					}
				    if(formElems[i].type == 'select-one'){
					    		$(formElems[i]).val(val);
					    	  	if($(formElems[i]).find('option:selected').val() !== val){
					    	  		$(formElems[i]).val(data[formElems[i].name]);
					    	  	}
				    }else if(formElems[i].type == 'checkbox'){
				    		if(formElems[i].value == val){
				    			formElems[i].checked = true;
				    		}
				    }else if(formElems[i].type == 'radio'){
				    		if(formElems[i].value == val){
				    			formElems[i].checked = true;
				    		}
				    }else if(formElems[i].type == 'file'){}
				     else{
					    	 if(val){
					    		 formElems[i].value  =   val;
					    	 }
 				    }
				}
			});
	}
	
	//表单元素的隐藏
	$.hideFormElem = function(form, fields){
		var hideFields = [];
		if(Object.prototype.toString.call(fields).match(/^\[object\s(.*)\]$/)[1] === 'Array'){
			hideFields = fields;
		}
		
		hideFields = ','+hideFields.join(',')+',';
		
		var formElems = $(form).serializeElement();
		
		for(var i=0 ; i< formElems.length; i++){
			if(hideFields.indexOf(','+formElems[i].name+',') !== -1){
				if(formElems[i].type == 'file'){
					$(formElems[i]).next().next().attr('disabled','disabled');
				}else{
					$(formElems[i]).attr('disabled','disabled');
				}
			}
		}
	}
	
	
	$.formDataView = function(form, selectedRowDatas){
		$(form).find('.row').addClass('detail-row');
		$(form).find('s').remove();
		var data = selectedRowDatas[0];
		if(Object.prototype.toString.call(selectedRowDatas).match(/^\[object\s(.*)\]$/)[1] !== 'Array'){
			data = selectedRowDatas;
		}
		
		var formElems = $(form).serializeElement();
		
		for(var i=0 ; i< formElems.length; i++){
			var val = data[formElems[i].name];
			 
			if(val){
				val = val.replace(/&lt;/g,'<').replace(/&gt;/g,'>').replace(/\\/g,'').replace(/&amp;/g,'&').replace(/&middot;/g,'·').replace(/&quot;/g,'"');
			}else{
				val = '';
			}
			$(form).attr('class','detail-page');
			var controlsObj = $(formElems[i]).closest('.controls');
			controlsObj.prev().removeAttr('class');
		    if(formElems[i].type == 'select-one'){
		    		formElems[i].value  =   val;
		    	  	if($(formElems[i]).find('option:selected').val() !== val){
		    	  		$(formElems[i]).val(data[formElems[i].name]);
		    	  	}
		    	  	controlsObj.after('<span class="detail-text">'+$(formElems[i]).find('option:selected').text()+'</span>');
		    }else if(formElems[i].type == 'checkbox'){
		    		if(formElems[i].value == val){
		    			formElems[i].checked = true;
		    			$(formElems[i]).attr('disabled','disabled');
		    		}
		    }else if(formElems[i].type == 'radio'){
		    		if(formElems[i].value == val){
		    			formElems[i].checked = true;
		    			$(formElems[i]).attr('disabled','disabled');
		    		}
		    }else if(formElems[i].type == 'file'){
		    } else{
			    	 controlsObj.after('<span class="detail-text">'+val+'</span>');
			    }
		    controlsObj.remove();
		}
		
	}
	
	$.insertDynamicDataForTheForm = function(formData, jqForm, options,name,val){
		if($(jqForm).find('input[type="file"]').length > 0){
			if(!val){
				var info = JSON.parse($.unicode(window.frameElement.getAttribute('info')));
				if(Object.prototype.toString.call(info).match(/^\[object\s(.*)\]$/)[1] == 'Array'){
					info = info[0];
				}
				 options.data[name] = info[name];
			}else{
				 options.data[name] = val;
			}
		}else{
			if(!val){
				var info = JSON.parse($.unicode(window.frameElement.getAttribute('info')));
				if(Object.prototype.toString.call(info).match(/^\[object\s(.*)\]$/)[1] == 'Array'){
					info = info[0];
				}
				formData.push({name:name,value:info[name]});
			}else{
				formData.push({name:name,value:val});
			}
		}
		
		
	}
	//文字的解密
	$.unicode = function(ps) {
		var a = ps.substring(3).split("").reverse().join("").split("."),
		source = "";
		for ( var i in a) {
			source += String.fromCharCode(a[i])
		}
		return source.substring(0,source.length-1);
	}
	
	//文字的加密
	$.runicode = function(source) {
		var nstr = [], s;
		var str = source.slice(0).split("");
		while (str.length) {
			s = str.shift();
			nstr.push(String(s.charCodeAt()).split("").reverse()
					.join(""));
		}
		return '53.' + nstr.reverse().join('.');
	}
	 

    $.parseJSON = function ( data ) {
	    var rvalidchars = /^[\],:{}\s]*$/,
	    core_version = "1.9.1",
	    core_trim = core_version.trim,
	    trim = core_trim && !core_trim.call("\uFEFF\xA0") ?
	    		function( text ) {
	    			return text == null ? "" : core_trim.call( text );
	    		} :
	    		function( text ) {
	    			return text == null ? "" : ( text + "" ).replace( rtrim, "" );
	    		};
	    	
			if ( window.JSON && window.JSON.parse ) {
				return window.JSON.parse( data );
			}
		
			if ( data === null ) {
				return data;
			}
		
			if ( typeof data === "string" ) {
		
				data = trim( data );
		
				if ( data ) {
					if ( rvalidchars.test( data.replace( rvalidescape, "@" )
						.replace( rvalidtokens, "]" )
						.replace( rvalidbraces, "")) ) {
		
						return ( new Function( "return " + data ) )();
					}
				}
			}
		}
   
}(window.jQuery);
 