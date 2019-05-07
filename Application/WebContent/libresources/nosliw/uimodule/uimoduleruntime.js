//get/create package
var packageObj = library;    

(function(packageObj){
	//get used node
	var node_COMMONATRIBUTECONSTANT;
	var node_COMMONCONSTANT;
	var node_createServiceRequestInfoSequence;
	var node_ServiceInfo;
	var node_createUIModuleRequest;
	var node_makeObjectWithComponentLifecycle;
	var node_makeObjectWithComponentInterface;
	var node_createComponentComplex;
	var node_createStateBackupService;
	var node_getComponentLifecycleInterface;
	var node_createServiceRequestInfoSimple;
	
//*******************************************   Start Node Definition  ************************************** 	

var node_createModuleRuntimeRequest = function(id, uiModuleDef, configure, componentDecorationInfos, ioInput, handlers, request){
	var out = node_createServiceRequestInfoSequence(new node_ServiceInfo("createModuleRuntime", {"moduleDef":uiModuleDef}), handlers, request);
	out.addRequest(node_createUIModuleRequest(id, uiModuleDef, undefined, ioInput, {
		success : function(request, uiModule){
			var runtime = loc_createModuleRuntime(uiModule, configure, componentDecorationInfos);
			return runtime.prv_getInitRequest({
				success : function(request){
					return request.getData();
				}
			}).withData(runtime);
		}
	}));
	return out;
};

var loc_createModuleRuntime = function(uiModule, configure, componentDecorationInfos){
	
	var loc_componentComplex = node_createComponentComplex(configure);
	var loc_localStore = configure.getConfigureData().__storeService;
	var loc_stateBackupService = node_createStateBackupService("module", uiModule.getId(), uiModule.getVersion(), loc_localStore);

	var loc_init = function(uiModule, configure, componentDecorationInfos){
		loc_componentComplex.addComponent(uiModule);
		loc_componentComplex.addDecorations(componentDecorationInfos);
	};

	var loc_getIOContext = function(){  return loc_getModule().getIOContext();   };
	
	var loc_getModule = function(){  return loc_componentComplex.getComponent();   };
	
	var loc_getProcessEnv = function(){   return loc_componentComplex.getInterface();    };
	
	var loc_getExecuteModuleProcessRequest = function(process, extraInput, handlers, request){
		return nosliw.runtime.getProcessRuntimeFactory().createProcessRuntime(loc_getProcessEnv()).getExecuteProcessRequest(process, loc_getModule().getIOContext(), extraInput, handlers, request);
	};
	
	var loc_getExecuteModuleProcessByNameRequest = function(processName, extraInput, handlers, request){
		var process = loc_getModule().getProcess(processName);
		if(process!=undefined)  return loc_getExecuteModuleProcessRequest(process, extraInput, handlers, request);
	};
	
	var loc_getGoActiveRequest = function(request){
		var out = node_createServiceRequestInfoSequence(new node_ServiceInfo("StartUIModuleRuntime", {}), undefined, request);
		//start module
		out.addRequest(loc_componentComplex.getStartRequest());
		out.addRequest(loc_getExecuteModuleProcessByNameRequest("active"));
		return out;
	};
	
	var loc_getResumeActiveRequest = function(stateData, request){
		var out = node_createServiceRequestInfoSequence(new node_ServiceInfo("ResumeUIModuleRuntime", {}), undefined, request);

		loc_componentComplex.setAllStateData(stateData.state);
		
		var backupContextData = stateData.context;
		_.each(backupContextData, function(contextData, name){
			out.addRequest(loc_getIOContext().getSetDataValueRequest(name, contextData, true));
		});
		
		out.addRequest(loc_componentComplex.getResumeRequest());
		
		out.addRequest(loc_getExecuteModuleProcessByNameRequest("resume"));
		return out;
	};
	
	
	var lifecycleCallback = {};
	lifecycleCallback[node_CONSTANT.LIFECYCLE_COMPONENT_TRANSIT_ACTIVE] = function(request){
		var out;
		var stateData = loc_stateBackupService.getBackupData();
		if(stateData==undefined)	out = loc_getGoActiveRequest(request);
		else	out = loc_getResumeActiveRequest(stateData, request);
		return out;
	};

	lifecycleCallback[node_CONSTANT.LIFECYCLE_COMPONENT_TRANSIT_DEACTIVE]=
	lifecycleCallback[node_CONSTANT.LIFECYCLE_COMPONENT_TRANSIT_ACTIVE_REVERSE] = function(request){
		var out = node_createServiceRequestInfoSequence(new node_ServiceInfo("DeactiveUIModuleRuntime", {}), undefined, request);
		out.addRequest(loc_componentComplex.getDeactiveRequest());
		loc_componentComplex.clearState();
		return out;
	};	

	lifecycleCallback[node_CONSTANT.LIFECYCLE_COMPONENT_TRANSIT_SUSPEND] = function(request){
		var out = node_createServiceRequestInfoSequence(new node_ServiceInfo("SuspendUIModuleRuntime", {}), undefined, request);
		out.addRequest(loc_getExecuteModuleProcessByNameRequest("suspend"));
		out.addRequest(loc_componentComplex.getSuspendRequest());
		
		out.addRequest(loc_getIOContext().getGetDataSetValueRequest({
			success : function(request, contextDataSet){
				var backupData = {
						state : loc_componentComplex.getAllStateData(),
						context : contextDataSet,
					};
				loc_stateBackupService.saveBackupData(backupData);
			}
		}));
		
		return out;
	};
	
	lifecycleCallback[node_CONSTANT.LIFECYCLE_COMPONENT_TRANSIT_RESUME] = function(request){
		loc_stateBackupService.clearBackupData();
	};

	var loc_out = {
		
		prv_getInitRequest : function(handlers, request){
			var out = node_createServiceRequestInfoSequence(new node_ServiceInfo("InitUIModuleRuntime", {}), handlers, request);
			out.addRequest(loc_componentComplex.getInitRequest());
			return out;
		},

		prv_getExecuteCommandRequest : function(command, parms, handlers, request){	
			return loc_componentComplex.getExecuteCommandRequest(command, parms, handlers, request);	
		},

		prv_getIODataSet : function(){  return loc_getIOContext();	},
		
		getModule : function(){  return loc_getModule();  },
		
		registerEventListener : function(listener, handler, thisContext){	return loc_componentComplex.registerEventListener(listener, handler, thisContext);	},
		unregisterEventListener : function(listener){	return loc_componentComplex.unregisterEventListener(listener); },
		
		getContextRequest : function(handlers, request){	return loc_getIOContext().getGetDataSetValueRequest(handlers, request);	},
		
		getExecuteCommandRequest : function(command, parms, handlers, request){	
			return node_getComponentInterface(loc_out).getExecuteCommandRequest(command, parms, handlers, request);
		}
		
	};
	
	loc_init(uiModule, configure, componentDecorationInfos);
	
	loc_out = node_makeObjectWithComponentLifecycle(loc_out, lifecycleCallback);
	
	loc_out = node_makeObjectWithComponentInterface(loc_out, loc_out);
	
	return loc_out;
};


//*******************************************   End Node Definition  ************************************** 	

//populate dependency node data
nosliw.registerSetNodeDataEvent("constant.COMMONCONSTANT", function(){node_COMMONCONSTANT = this.getData();});
nosliw.registerSetNodeDataEvent("constant.COMMONATRIBUTECONSTANT", function(){node_COMMONATRIBUTECONSTANT = this.getData();});
nosliw.registerSetNodeDataEvent("request.request.createServiceRequestInfoSequence", function(){	node_createServiceRequestInfoSequence = this.getData();	});
nosliw.registerSetNodeDataEvent("common.service.ServiceInfo", function(){node_ServiceInfo = this.getData();	});
nosliw.registerSetNodeDataEvent("uimodule.createUIModuleRequest", function(){node_createUIModuleRequest = this.getData();});
nosliw.registerSetNodeDataEvent("component.makeObjectWithComponentLifecycle", function(){node_makeObjectWithComponentLifecycle = this.getData();});
nosliw.registerSetNodeDataEvent("component.makeObjectWithComponentInterface", function(){node_makeObjectWithComponentInterface = this.getData();});
nosliw.registerSetNodeDataEvent("component.getComponentLifecycleInterface", function(){node_getComponentLifecycleInterface = this.getData();});
nosliw.registerSetNodeDataEvent("component.createComponentComplex", function(){node_createComponentComplex = this.getData();});
nosliw.registerSetNodeDataEvent("component.createStateBackupService", function(){node_createStateBackupService = this.getData();});
nosliw.registerSetNodeDataEvent("request.request.createServiceRequestInfoSimple", function(){	node_createServiceRequestInfoSimple = this.getData();	});

//Register Node by Name
packageObj.createChildNode("createModuleRuntimeRequest", node_createModuleRuntimeRequest); 

})(packageObj);
