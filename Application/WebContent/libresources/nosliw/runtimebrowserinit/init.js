//
var libResources = [
	"external.Underscore;1.6.0",
	"external.Backbone;1.1.2",
	"external.log4javascript;1.0.0",
	"nosliw.core",
	"nosliw.constant",
	"nosliw.common",
	"nosliw.expression",
	"nosliw.request",
	"nosliw.id",
	"nosliw.init",
	"nosliw.logging",
	"nosliw.resource",
	"nosliw.remoteservice",
	"nosliw.runtime",
	"nosliw.runtimebrowser"
];


var requestLoadLibraryResources = function(resourceIds, callBackFunction){
	var data = {
		command : "requestLoadLibraryResources",
		data : resourceIds
	};
	
	$.ajax({
		url : "gateway",
		type : "POST",
		dataType: "json",
		contentType: "application/json; charset=utf-8",
		data : JSON.stringify(data),
		async : true,
		success : function(serviceData, status){
			var result = serviceData.data;
			var fileNumber = result.length;
			var count = 0;
			
			
			var loadScriptInOrder = function(){
				var url = result[count];
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
					callBackFunction.call();
				}
				else{
					loadScriptInOrder();
				}
			};
			
			loadScriptInOrder();
		},
		error: function(obj, textStatus, errorThrown){
		},
	});
};




requestLoadLibraryResources(libResources, function(){
	
	  nosliw.createNode(nosliw.getNodeData("constant.COMMONCONSTANT").RUNTIME_LANGUAGE_JS_GATEWAY, gateway);  

	  
	  nosliw.runtimeName = "browser";

	  nosliw.initModules();

	  var runtimeRhino = nosliw.getNodeData("runtime.createRuntime")(nosliw.runtimeName);

	  nosliw.runtime = runtimeRhino;


	  runtimeRhino.interfaceObjectLifecycle.init();

	  nosliw.generateId = runtimeRhino.getIdService().generateId;
	  
	  var event = new CustomEvent("runtime.start", { "detail": "Example of an event" });
	  document.dispatchEvent(event);
	  
});