/**
 * 
 */
var init = function(baseServer, callBackFunction){

	//load lib utility function
	var loadLibrary = function(libs, callBackFunction){
		var count = 0;
		var fileNumber = libs.length;

		var loadScriptInOrder = function(){
			var url = libs[count];
			
			var script = document.createElement('script');
			script.setAttribute('src', url);
			script.setAttribute('defer', "defer");
			script.setAttribute('type', 'text/javascript');

			script.onload = callBack;
			document.getElementsByTagName("head")[0].appendChild(script);
		};
		
		var callBack = function(){
			count++;
			if(count==fileNumber){
				if(callBackFunction!=undefined)		callBackFunction.call();
			}
			else{
				loadScriptInOrder();
			}
		};

		loadScriptInOrder();
	};

	//when nosliw active
	$(document).on("nosliwActive", function(){
		//load mini libs
		loadLibrary([
			"js/miniapp/0_package_service.js",
			"js/miniapp/service.js",
			"js/miniapp/miniapp.js",
		], function(){
			//create miniapp
			var minapp = nosliw.getNodeData("miniapp.createMiniApp")();
			nosliw.miniapp = minapp;
			nosliw.createNode("miniapp", minapp);
			minapp.interfaceObjectLifecycle.init();
		});
	});

	//nosliw init first
	loadLibrary([
	//  "libresources/external/log4javascript/1.0.0/log4javascript.js",
		baseServer+"libresources/nosliw/runtimebrowserinit/init.js",
	], function(){
		nosliw.init(baseServer);
	});

};

