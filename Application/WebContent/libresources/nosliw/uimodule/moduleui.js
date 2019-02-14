//get/create package
var packageObj = library;    

(function(packageObj){
	//get used node
	var node_COMMONATRIBUTECONSTANT;
	var node_COMMONCONSTANT;
	var node_buildServiceProvider;
	var node_createServiceRequestInfoSimple;
	var node_createServiceRequestInfoSequence;
	var node_ServiceInfo;
	var node_contextUtility;
	var node_ioTaskUtility;

//*******************************************   Start Node Definition  ************************************** 	

var node_createModuleUIRequest = function(moduleUIDef, moduleContext, handlers, request){
	var out = node_createServiceRequestInfoSequence(new node_ServiceInfo("createModuleUI", {"moduleUIDef":moduleUIDef, "moduleContext":moduleContext}), handlers, request);
	
	//generate page
	out.addRequest(nosliw.runtime.getUIPageService().getGenerateUIPageRequest(moduleUIDef[node_COMMONATRIBUTECONSTANT.EXECUTABLEMODULEUI_PAGE], undefined, {
		success :function(requestInfo, page){
			var moduleUI = node_createModuleUI(moduleUIDef, page);
			//syn in data with module context
			return moduleUI.getSynInUIDataRequest(moduleContext, {
				success : function(requestInfo){
					return moduleUI;
				}
			});
		}
	}));
	return out;
};

var node_createModuleUI = function(moduleUIDef, page){
	var loc_moduleUIDef = moduleUIDef;
	var loc_page = page;
	
	var loc_out = {
			
		//take command
		getExecuteCommandRequest : function(commandName, parms, handlers, request){
			return loc_page.getExecuteCommandRequest(commandName, parms, handlers, reqeustInfo);
		},
		
		executeExecuteCommandRequest : function(commandName, parms, handlers, request){
			var requestInfo = this.getExecuteCommandRequest(commandName, parms, handlers, request);
			node_requestServiceProcessor.processRequest(requestInfo);
		},
		
		registerListener : function(handler){		loc_page.registerEventListener(handler);	},
		
		getSynInUIDataRequest : function(moduleContext, handlers, request){ 
			var out = node_createServiceRequestInfoSequence(new node_ServiceInfo("SynInUIData", {"moduleContext":moduleContext}), handlers, request);
			out.addRequest(node_ioTaskUtility.getExecuteDataAssociationRequest(moduleContext, loc_moduleUIDef[node_COMMONATRIBUTECONSTANT.EXECUTABLEMODULEUI_INPUTMAPPING], {
				success : function(request, input){
					return loc_page.getExecuteCommandRequest(node_CONSTANT.PAGE_COMMAND_REFRESH, input);
				}
			}, request));
			return out;
		},
		
		getSynOutUIDataRequest : function(moduleContext, handlers, request){
			var out = node_createServiceRequestInfoSequence(new node_ServiceInfo("SynOutUIData", {"moduleContext":moduleContext}), handlers, request);
			out.addRequest(loc_page.getGetContextValueRequest({
				success : function(request, contextValue){
					return node_ioTaskUtility.getExecuteDataAssociationToTargetRequest(contextValue, loc_moduleUIDef[node_COMMONATRIBUTECONSTANT.EXECUTABLEMODULEUI_OUTPUTMAPPING], moduleContext);
				}
			}));
			return out;
		},
		
		getPage : function(){		return loc_page;		},
		
		getName : function(){	return loc_moduleUIDef[node_COMMONATRIBUTECONSTANT.EXECUTABLEMODULEUI_ID];	}
	};
	
	return loc_out;
	
};
	
//*******************************************   End Node Definition  ************************************** 	

//populate dependency node data
nosliw.registerSetNodeDataEvent("constant.COMMONCONSTANT", function(){node_COMMONCONSTANT = this.getData();});
nosliw.registerSetNodeDataEvent("constant.COMMONATRIBUTECONSTANT", function(){node_COMMONATRIBUTECONSTANT = this.getData();});
nosliw.registerSetNodeDataEvent("request.buildServiceProvider", function(){node_buildServiceProvider = this.getData();});
nosliw.registerSetNodeDataEvent("request.request.createServiceRequestInfoSimple", function(){node_createServiceRequestInfoSimple = this.getData();});
nosliw.registerSetNodeDataEvent("request.request.createServiceRequestInfoSequence", function(){	node_createServiceRequestInfoSequence = this.getData();	});
nosliw.registerSetNodeDataEvent("common.service.ServiceInfo", function(){node_ServiceInfo = this.getData();	});
nosliw.registerSetNodeDataEvent("uidata.context.utility", function(){node_contextUtility = this.getData();});
nosliw.registerSetNodeDataEvent("iotask.ioTaskUtility", function(){node_ioTaskUtility = this.getData();});

//Register Node by Name
packageObj.createChildNode("createModuleUIRequest", node_createModuleUIRequest); 

})(packageObj);
