//get/create package
var packageObj = library;    

(function(packageObj){
	//get used node
	var node_COMMONATRIBUTECONSTANT;
	var node_COMMONCONSTANT;
	var node_createEventObject;
	var node_createServiceRequestInfoSequence;
	var node_createServiceRequestInfoSimple;
	var node_ServiceInfo;
	var node_makeObjectWithType;
	var node_getObjectType;
	var node_requestServiceProcessor;

//*******************************************   Start Node Definition  ************************************** 	
//interface for decoration plug in
var node_buildDecorationPlugInObject = function(rawPluginObj){
	var loc_rawPluginObj = rawPluginObj;
	
	var loc_out = {

		getInterface : function(){   return loc_rawPluginObj.getInterface==undefined?undefined:loc_rawPluginObj.getInterface();  },
		
		processComponentCoreEvent : function(eventName, eventData, request){	return loc_rawPluginObj.processComponentCoreEvent==undefined? undefined:loc_rawPluginObj.processComponentCoreEvent(eventName, eventData, request);	},
		
		processComponentCoreValueChangeEvent : function(eventName, eventData, request){	return loc_rawPluginObj.processComponentCoreValueChangeEvent==undefined? undefined:loc_rawPluginObj.processComponentCoreValueChangeEvent(eventName, eventData, request);},
			
		getProcessCommandRequest : function(command, parms, handlers, request){	return loc_rawPluginObj.getProcessCommandRequest==undefined? undefined:loc_rawPluginObj.getProcessCommandRequest(command, parms, handlers, request);},
		getProcessNosliwCommandRequest : function(command, parms, handlers, request){	return this.getProcessCommandRequest(node_basicUtility.buildNosliwFullName(command), parms, handlers, request);},
		
		getUpdateViewRequest : function(view, handlers, request){
			if(loc_rawPluginObj.getUpdateViewRequest!=undefined){
				return loc_rawPluginObj.getUpdateViewRequest(view, handlers, request);
			}
			else{
				//callback not defined, then return view 
				return node_createServiceRequestInfoSimple(undefined, function(request){
					return view;
				}, handlers, request);
			}
		},
		
		getLifeCycleRequest : function(transitName, handlers, request){	return loc_rawPluginObj.getLifeCycleRequest==undefined?undefined:loc_rawPluginObj.getLifeCycleRequest(transitName, handlers, request);},
		setLifeCycleStatus : function(status){},
		
		//restore data in state to decoration 
		getRestoreStateDataRequest : function(handlers, request){return loc_rawPluginObj.getRestoreStateDataRequest==undefined? undefined:loc_rawPluginObj.getRestoreStateDataRequest(handlers, request);},
	};
	
	return loc_out;
};

//interface for component core 
var node_buildComponentCore = function(rawComponentCore){
	
	var interfaceDef = {
		//execute command
		getExecuteCommandRequest : function(commandName, parm, handlers, requestInfo){},
		getExecuteNosliwCommandRequest : function(commandName, parm, handlers, requestInfo){   this.getExecuteCommandRequest(node_basicUtility.buildNosliwFullName(commandName), parm, handlers, requestInfo);    },
		//get part by id
		getPart : function(partId){ },
		//get interface exposed
		getInterface : function(){ return {}; },

		//set state for the component core
		setState : function(state){   },

		//value by name
		getValue : function(name){},
		setValue : function(name, value){},
		
		//lifecycle handler
		getLifeCycleRequest : function(transitName, handlers, request){},
		setLifeCycleStatus : function(status){},
		
		getUpdateViewRequest : function(view, handlers, request){
			return node_createServiceRequestInfoSimple(undefined, function(request){return view}, handlers, request);
		},

		registerEventListener : function(listener, handler, thisContext){  },
		unregisterEventListener : function(listener){ },

		registerValueChangeEventListener : function(listener, handler, thisContext){   },
		unregisterValueChangeEventListener : function(listener){ },
		
		startLifecycleTask : function(){},
		endLifecycleTask : function(){},
	};
	
	return _.extend({}, interfaceDef, rawComponentCore);
};

//interface for component external env
var node_buildComponentEnv = function(rawComponentEnv){
	var interfaceDef = {
		//process request
		processRequest : function(request){   },
		//execute process
		getExecuteProcessRequest : function(process, extraInput, handlers, request){    },
		//execute process
		getExecuteProcessResourceRequest : function(processId, input, handlers, request){    },
	};
	return _.extend({}, interfaceDef, rawComponentEnv);
};

//interface for component management interface delegate object
var node_createComponentManagementInterfaceDelegateObject = function(delegateObj){
	var loc_interface = {
		getContextIODataSet :  function(){    },
		getExecuteCommandRequest : function(command, parms, handlers, request){      },
		getExecuteNosliwCommandRequest : function(commandName, parm, handlers, requestInfo){   this.getExecuteCommandRequest(node_basicUtility.buildNosliwFullName(commandName), parm, handlers, requestInfo);    },
		registerEventListener : function(listener, handler, thisContext){     },
		unregisterEventListener : function(listener){     },
		registerValueChangeEventListener : function(listener, handler, thisContext){    },
		unregisterValueChangeEventListener : function(listener){       }
	};
	return _.extend({}, loc_interface, delegateObj);
};


//*******************************************   End Node Definition  ************************************** 	

//populate dependency node data
nosliw.registerSetNodeDataEvent("constant.COMMONCONSTANT", function(){node_COMMONCONSTANT = this.getData();});
nosliw.registerSetNodeDataEvent("constant.COMMONATRIBUTECONSTANT", function(){node_COMMONATRIBUTECONSTANT = this.getData();});
nosliw.registerSetNodeDataEvent("common.event.createEventObject", function(){node_createEventObject = this.getData();});
nosliw.registerSetNodeDataEvent("request.request.createServiceRequestInfoSequence", function(){	node_createServiceRequestInfoSequence = this.getData();	});
nosliw.registerSetNodeDataEvent("request.request.createServiceRequestInfoSimple", function(){node_createServiceRequestInfoSimple = this.getData();});
nosliw.registerSetNodeDataEvent("common.service.ServiceInfo", function(){node_ServiceInfo = this.getData();	});
nosliw.registerSetNodeDataEvent("common.objectwithtype.makeObjectWithType", function(){node_makeObjectWithType = this.getData();});
nosliw.registerSetNodeDataEvent("common.objectwithtype.getObjectType", function(){node_getObjectType = this.getData();});
nosliw.registerSetNodeDataEvent("request.requestServiceProcessor", function(){node_requestServiceProcessor = this.getData();});

//Register Node by Name
packageObj.createChildNode("buildDecorationPlugInObject", node_buildDecorationPlugInObject); 
packageObj.createChildNode("buildComponentCore", node_buildComponentCore); 
packageObj.createChildNode("node_buildComponentEnv", node_buildComponentEnv); 
packageObj.createChildNode("createComponentManagementInterfaceDelegateObject", node_createComponentManagementInterfaceDelegateObject); 

})(packageObj);
