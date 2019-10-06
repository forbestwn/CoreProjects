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
	var node_createApp;
	var node_createComponentCoreComplex;
	var node_makeObjectWithComponentLifecycle;
	var node_makeObjectWithComponentManagementInterface;
	var node_createStateBackupService;
	var node_createEventObject;

//*******************************************   Start Node Definition  ************************************** 	

var node_createAppRuntimeRequest = function(id, appDef, configure, componentDecorationInfos, ioInput, state, handlers, request){
	var out = node_createServiceRequestInfoSimple(new node_ServiceInfo("createUIModule"), function(request){
		var app = node_createApp(id, appDef, ioInput);
		var runtime = node_createAppRuntime(app, configure, componentDecorationInfos);
		return runtime.prv_getInitRequest({
			success : function(request){
				return request.getData();
			}
		}).withData(runtime);
	}, handlers, request);
	return out;
};
	
var node_createAppRuntime = function(uiApp, configure, componentDecorationInfos){
	
	var loc_interface = {
		getPart : function(partId){  
			return loc_componentComplex.getCore().getPart(partId);
//			return loc_out.getPart(partId);	
		},

		getExecutePartCommandRequest : function(partId, commandName, commandData, handlers, requestInfo){
			return this.getPart(partId).getExecuteCommandRequest(commandName, commandData, handlers, requestInfo);
		},
	};
	
	var loc_componentComplex = node_createComponentCoreComplex(configure, loc_interface);
	var loc_localStore = configure.getConfigureValue().__storeService;
	var loc_applicationDataService = configure.getConfigureValue().__appDataService;
	var loc_stateBackupService = node_createStateBackupService("app", uiApp.getId(), uiApp.getVersion(), loc_localStore);
	
	var loc_eventSource = node_createEventObject();
	var loc_eventListener = node_createEventObject();

	var loc_init = function(uiApp, configure, componentDecorationInfos){
		loc_componentComplex.setCore(uiApp);
		loc_componentComplex.addDecorations(componentDecorationInfos);
		
		loc_componentComplex.registerEventListener(loc_eventListener, function(eventName, eventData, request){
			if(eventName=="executeProcess"){
				nosliw.runtime.getProcessRuntimeFactory().createProcessRuntime(loc_getProcessEnv()).executeProcessResourceRequest(eventData, undefined, undefined, undefined, request);
			}
		}, loc_out);
	};
	
	var loc_getIOContext = function(){  return loc_out.prv_getComponent().getIOContext();   };
	
	var loc_getProcessEnv = function(){   return loc_componentComplex.getInterface();    };
	
	var loc_getExecuteAppProcessRequest = function(process, extraInput, handlers, request){
		return nosliw.runtime.getProcessRuntimeFactory().createProcessRuntime(loc_getProcessEnv()).getExecuteEmbededProcessRequest(process, loc_out.prv_getComponent().getIOContext(), extraInput, handlers, request);
	};
	
	var loc_getExecuteAppProcessByNameRequest = function(processName, extraInput, handlers, request){
		var process = loc_out.prv_getComponent().getProcess(processName);
		if(process!=undefined)  return loc_getExecuteAppProcessRequest(process, extraInput, handlers, request);
	};

	var loc_getGoActiveRequest = function(request){
		var out = node_createServiceRequestInfoSequence(new node_ServiceInfo("StartUIModuleRuntime", {}), undefined, request);
		//start module
		out.addRequest(loc_componentComplex.getLifeCycleRequest(node_CONSTANT.LIFECYCLE_COMPONENT_TRANSIT_ACTIVE));
		out.addRequest(loc_getExecuteAppProcessByNameRequest("active"));
		return out;
	};

	
	
	var lifecycleCallback = {};
	lifecycleCallback[node_CONSTANT.LIFECYCLE_COMPONENT_TRANSIT_ACTIVE] = function(request){
		return loc_getGoActiveRequest(request);
	};

	lifecycleCallback[node_CONSTANT.LIFECYCLE_COMPONENT_TRANSIT_DEACTIVE]=
	lifecycleCallback[node_CONSTANT.LIFECYCLE_COMPONENT_TRANSIT_ACTIVE_REVERSE] = function(request){
		var out = node_createServiceRequestInfoSequence(new node_ServiceInfo("DeactiveUIAppRuntime", {}), undefined, request);
		out.addRequest(loc_componentComplex.getLifeCycleRequest(node_CONSTANT.LIFECYCLE_COMPONENT_TRANSIT_DEACTIVE));
		loc_componentComplex.clearState();
		return out;
	};	

	lifecycleCallback[node_CONSTANT.LIFECYCLE_COMPONENT_TRANSIT_DESTROY] = function(request){
		var out = node_createServiceRequestInfoSequence(new node_ServiceInfo("DestroyUIAppRuntime", {}), undefined, request);
		out.addRequest(loc_componentComplex.getLifeCycleRequest(node_CONSTANT.LIFECYCLE_COMPONENT_TRANSIT_DESTROY));
		return out;
	};

	var loc_out = {
			
		prv_getInitRequest : function(handlers, request){
			var out = node_createServiceRequestInfoSequence(new node_ServiceInfo("InitUIModuleRuntime", {}), handlers, request);
			out.addRequest(loc_componentCoreComplex.getLifeCycleRequest(node_CONSTANT.LIFECYCLE_COMPONENT_TRANSIT_INIT));
			return out;
		},
	
		prv_getExecuteCommandRequest : function(command, parms, handlers, request){	
			return loc_componentComplex.getExecuteCommandRequest(command, parms, handlers, request);	
		},
		
		prv_getComponent : function(){  return loc_componentComplex.getComponent();   },
		prv_getIODataSet : function(){  return loc_getIOContext();	},

		prv_registerEventListener : function(listener, handler, thisContext){	return loc_componentComplex.registerEventListener(listener, handler, thisContext);	},
		prv_unregisterEventListener : function(listener){	return loc_componentComplex.unregisterEventListener(listener); },
		
		getInterface : function(){   return node_getComponentManagementInterface(loc_out);  },
	};
	
	loc_init(uiApp, configure, componentDecorationInfos);
	
	loc_out = node_makeObjectWithComponentLifecycle(loc_out, lifecycleCallback);
	loc_out = node_makeObjectWithComponentManagementInterface(loc_out, loc_out);

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
nosliw.registerSetNodeDataEvent("uiapp.createApp", function(){node_createApp = this.getData();	});
nosliw.registerSetNodeDataEvent("component.createComponentCoreComplex", function(){node_createComponentCoreComplex = this.getData();});
nosliw.registerSetNodeDataEvent("component.makeObjectWithComponentLifecycle", function(){node_makeObjectWithComponentLifecycle = this.getData();});
nosliw.registerSetNodeDataEvent("component.makeObjectWithComponentManagementInterface", function(){node_makeObjectWithComponentManagementInterface = this.getData();});
nosliw.registerSetNodeDataEvent("component.createStateBackupService", function(){node_createStateBackupService = this.getData();});
nosliw.registerSetNodeDataEvent("common.event.createEventObject", function(){node_createEventObject = this.getData();});

//Register Node by Name
packageObj.createChildNode("createAppRuntimeRequest", node_createAppRuntimeRequest); 

})(packageObj);
