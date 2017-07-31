//get/create package
var packageObj = library;    

(function(packageObj){
//get used node
var node_createConfigures;
var node_ServiceInfo;
var node_RemoteServiceTask;
var node_COMMONCONSTANT;
var node_COMMONATRIBUTECONSTANT;
//*******************************************   Start Node Definition  ************************************** 	

	
var node_gateway = function(){
	
	
	return {
		getExpression : function(suite, name){
			
		},
		
		/**
		 * Callback method used to request to discover resources into runtime env
		 * @param objResourcesInfo: a list of resource id 
		 * @param callBackFunction (discovered resource info)
		 */
		requestDiscoverResources : function(objResourceIds, handlers){
			var parms = {};
			parms[node_COMMONATRIBUTECONSTANT.GATEWAYSERVLET_REQUEST_DISCOVERRESOURCES_RESOURCEIDS] = objResourceIds; 
			
			var service = new node_ServiceInfo(node_COMMONATRIBUTECONSTANT.GATEWAYSERVLET_REQUEST_DISCOVERRESOURCES, parms);
			
			var remoteServiceTask = new node_RemoteServiceTask("gateway", service, {
				success : function(request, resourceInfos){
					handlers.success.call(request, request, resourceInfos);
				},
				error : function(){
					
				},
				exception : function(){
					
				}
			}, undefined, undefined);
			
			nosliw.runtime.getRemoteService().addServiceTask(remoteServiceTask);
		},
		
		/**
		 * Callback method used to request to discover resources and load into runtime env
		 * @param objResourcesInfo: a list of resource id 
		 * @param callBackFunction (discovered and loaded resource info)
		 */
		requestDiscoverAndLoadResources : function(objResourceIds, handlers){
			
		},
		
		/**
		 * Callback method used to request to load resources into runtime env
		 * @param objResourcesInfo: a list of resource info 
		 * @param callBackFunction (nothing)
		 */
		requestLoadResources : function(resourcesInfo, handlers){

			var parms = {};
			parms[node_COMMONATRIBUTECONSTANT.GATEWAYSERVLET_REQUEST_LOADRESOURCES_RESOURCEINFOS] = resourcesInfo; 
			
			var service = new node_ServiceInfo(node_COMMONATRIBUTECONSTANT.GATEWAYSERVLET_REQUEST_LOADRESOURCES, parms);
			
			var remoteServiceTask = new node_RemoteServiceTask("gateway", service, {
				success : function(request, scriptInfos){
					
					_.each(scriptInfos, function(scriptInfo, i, list){
						var file = scriptInfo[node_COMMONATRIBUTECONSTANT.JSSCRIPTINFO_FILE];
						if(file!=undefined){
							
						}
						else{
							eval(scriptInfo[node_COMMONATRIBUTECONSTANT.JSSCRIPTINFO_SCRIPT]);
						}
					});
					
					handlers.success.call(request, request, resourceInfos);
				},
				error : function(){
					
				},
				exception : function(){
					
				}
			}, undefined, undefined);
			
			nosliw.runtime.getRemoteService().addServiceTask(remoteServiceTask);
			
			
			var remoteServiceTask = new node_RemoteServiceTask("gateway", service, {
				success : function(data){
					_.each(data, function(resourceLoaded){
						var resourceInfo = resourceLoaded.resourceInfo;
						var resource = resourceLoaded.resource;
						switch(resource.type)
						{
						case library:
							break
						default : 
							break;
						}
					}, this);
				},
				error : function(){
					
				},
				exception : function(){
					
				}
			}, requestInfo, setting);
			
			nosliw.runtime.getRemoteService().addServiceTask(remoteServiceTask);
			
		},
	};
}();	
	

//*******************************************   End Node Definition  ************************************** 	

//populate dependency node data
nosliw.registerSetNodeDataEvent("common.setting.createConfigures", function(){node_createConfigures = this.getData();});
nosliw.registerSetNodeDataEvent("common.service.ServiceInfo", function(){node_ServiceInfo = this.getData();});
nosliw.registerSetNodeDataEvent("remote.entity.RemoteServiceTask", function(){node_RemoteServiceTask = this.getData();});
nosliw.registerSetNodeDataEvent("constant.COMMONCONSTANT", function(){node_COMMONCONSTANT = this.getData();});
nosliw.registerSetNodeDataEvent("constant.COMMONATRIBUTECONSTANT", function(){node_COMMONATRIBUTECONSTANT = this.getData();});


nosliw.registerSetNodeDataEvent("runtime", function(){
	var configure = node_createConfigures({
		url : "gateway",
//		contentType: "application/json; charset=utf-8"
	});
	
	nosliw.runtime.getRemoteService().registerSyncTaskConfigure("gateway", configure);
});

	
//Register Node by Name
packageObj.createChildNode("gateway", node_gateway); 

})(packageObj);
