

(function(global) {
	function getVersion(){
		
		if (navigator.userAgent.indexOf("Opera") != -1) {
			return Opera;
		} else if (navigator.userAgent.indexOf("MSIE") != -1) {
			if (navigator.appName == "Microsoft Internet Explorer" && navigator.appVersion.match(/6./i) == "6.") {
				return 'IE6';
			} else if (navigator.appName == "Microsoft Internet Explorer" && navigator.appVersion.match(/7./i) == "7.") {
				return 'IE7';
			} else if (navigator.appName == "Microsoft Internet Explorer" && navigator.appVersion.match(/8./i) == "8.") {
				return 'IE8';
			} else if (navigator.appName == "Microsoft Internet Explorer" && navigator.appVersion.match(/9./i) == "9.") {
				return 'IE9';
			}
		} else if (navigator.userAgent.indexOf("Firefox") != -1) {
			return 'Firefox';
		} else if (navigator.userAgent.indexOf("Netscape") != -1) {
			return 'Netscape';
		} else if (navigator.userAgent.indexOf("Safari") != -1) {
			return 'Safari';
		} else {
			return '';
		}
	}
	
 
	CUI.config = {
	    	paths:{
		   		//lib
		   		 underscore : {path:'underscore'},
		   		 json : {path:'json2'},
		   		 keyboardtrap : {path:'keyboardtrap'},
		   		 utils : {path:'utils'},
		   		 //native
		   		 ajaxform : {path:'../native/form/cui.ajaxform',depends:['css!../native/form/css/mui.ajaxform.css']},
		   		 layer : {path:'../native/info/layer',depends:['css!../native/info/css/layer.css']},
//		   		 page:{path:'../native/OwnJs/page',depends:['css!../native/OwnJs/css/page.css']}
		    	}
	};
	
})(window);