//get/create package
var packageObj = library;    

(function(packageObj){
	//get used node
	var node_CONSTANT;
	var node_buildInterface;
	var node_getInterface;
	var node_eventUtility;
	var node_getObjectName;
	var node_getObjectType;
	var node_requestUtility;
	var node_createServiceRequestInfoSequence;
	var node_ServiceInfo;
	var node_createServiceRequestInfoSimple;
	var node_createStateMachine;
	var node_CommandInfo;
	var createStateMachineDef;
	var node_TransitInfo;
	var node_StateTransitPath;
	var node_requestServiceProcessor;

//*******************************************   Start Node Definition  ************************************** 	

var INTERFACENAME = "componentLifecycle";
	
/*
 * utility functions to build lifecycle object
 */
var node_makeObjectWithComponentLifecycle = function(baseObject, lifecycleCallback, thisContext){
	return node_buildInterface(baseObject, INTERFACENAME, loc_createComponentLifecycle(thisContext==undefined?baseObject:thisContext, lifecycleCallback));
};
	
var node_getComponentLifecycleInterface = function(baseObject){
	return node_getInterface(baseObject, INTERFACENAME);
};

var loc_createComponentLifecycle = function(thisContext, lifecycleCallback){
	var loc_stateMachineDef;
	
	//this context for lifycycle callback method
	var loc_thisContext = thisContext;
	
	var loc_baseObject;
	
	//life cycle call back including all call back method
	var loc_lifecycleCallback = lifecycleCallback==undefined? {}:lifecycleCallback;
	
	var loc_stateMachine;

	var loc_init = function(){
		loc_stateMachineDef = node_createStateMachineDef();
		var loc_validTransits = [
			new node_TransitInfo(node_CONSTANT.LIFECYCLE_COMPONENT_STATUS_INIT, node_CONSTANT.LIFECYCLE_COMPONENT_STATUS_ACTIVE),
			new node_TransitInfo(node_CONSTANT.LIFECYCLE_COMPONENT_STATUS_INIT, node_CONSTANT.LIFECYCLE_COMPONENT_STATUS_DEAD),
			new node_TransitInfo(node_CONSTANT.LIFECYCLE_COMPONENT_STATUS_ACTIVE, node_CONSTANT.LIFECYCLE_COMPONENT_STATUS_SUSPENDED),
			new node_TransitInfo(node_CONSTANT.LIFECYCLE_COMPONENT_STATUS_ACTIVE, node_CONSTANT.LIFECYCLE_COMPONENT_STATUS_INIT),
			new node_TransitInfo(node_CONSTANT.LIFECYCLE_COMPONENT_STATUS_SUSPENDED, node_CONSTANT.LIFECYCLE_COMPONENT_STATUS_ACTIVE),
		];

		var loc_commands = [
			new node_CommandInfo("activate", undefined, [node_CONSTANT.LIFECYCLE_COMPONENT_STATUS_ACTIVE]),
			new node_CommandInfo("deactive", undefined, [node_CONSTANT.LIFECYCLE_COMPONENT_STATUS_INIT]),
			new node_CommandInfo("suspend", undefined, [node_CONSTANT.LIFECYCLE_COMPONENT_STATUS_SUSPENDED]),
			new node_CommandInfo("resume", [node_CONSTANT.LIFECYCLE_COMPONENT_STATUS_SUSPENDED], [node_CONSTANT.LIFECYCLE_COMPONENT_STATUS_ACTIVE]),
			new node_CommandInfo("destroy", undefined, [node_CONSTANT.LIFECYCLE_COMPONENT_STATUS_DEAD]),
			new node_CommandInfo("restart", undefined, [node_CONSTANT.LIFECYCLE_COMPONENT_STATUS_INIT, node_CONSTANT.LIFECYCLE_COMPONENT_STATUS_ACTIVE]),
		];

		var loc_statePaths = [
			new node_StateTransitPath(node_CONSTANT.LIFECYCLE_COMPONENT_STATUS_ACTIVE, node_CONSTANT.LIFECYCLE_COMPONENT_STATUS_DEAD, [node_CONSTANT.LIFECYCLE_COMPONENT_STATUS_INIT])
		];
		
		_.each(loc_validTransits, function(transit, i){		
			var from = transit.from;
			var to = transit.to;
			loc_stateMachineDef.addStateInfo(transit, 
				function(){
					var fun = loc_lifecycleCallback[transit.from+"_"+transit.to];
					if(fun!=undefined)   return fun.apply(loc_thisContext, arguments);
					else  return true;
				}, 
				function(){
					var fun = loc_lifecycleCallback["_"+transit.from+"_"+transit.to];
					if(fun!=undefined)   return fun.apply(loc_thisContext, arguments);
					else  return true;
				});
		});
		_.each(loc_commands, function(commandInfo, i){      loc_stateMachineDef.addCommand(commandInfo);      });
		_.each(loc_statePaths, function(statePath, index){  loc_stateMachineDef.addTransitPath(statePath);  });
		
		loc_stateMachine = node_createStateMachine(loc_stateMachineDef, node_CONSTANT.LIFECYCLE_COMPONENT_STATUS_INIT, loc_thisContext);
	};

	var loc_out = {

		transit : function(commandName, request){  
			var task = loc_stateMachine.newTask(commandName);
			if(task!=undefined)  	return task.process(request);
		},
		getTransitRequest : function(commandName, handlers, request){
			var task = loc_stateMachine.newTask(commandName);
			if(task!=undefined)  	return task.getProcessRequest(handlers, request);
		},
		executeTransitRequest : function(commandName, handlers, request){
			var request = loc_out.getTransitRequest(commandName, handlers, request);
			node_requestServiceProcessor.processRequest(request);
		},
			
		getStateMachine : function(){  return loc_stateMachine;   },
		getComponentStatus : function(){		return loc_stateMachine.getCurrentState();		},

		registerEventListener : function(listener, handler){	return loc_stateMachine.prv_registerEventListener(listener, handler, thisContext);	},
		unregisterEventListener : function(listener){  loc_stateMachine.prv_unregisterEventListener(listener);  },

		bindBaseObject : function(baseObject){		loc_baseObject = baseObject;	}
	};

	loc_init();
	
	return loc_out;
};

//*******************************************   End Node Definition  ************************************** 	
//populate dependency node data
nosliw.registerSetNodeDataEvent("constant.CONSTANT", function(){node_CONSTANT = this.getData();});
nosliw.registerSetNodeDataEvent("common.interface.buildInterface", function(){node_buildInterface = this.getData();});
nosliw.registerSetNodeDataEvent("common.interface.getInterface", function(){node_getInterface = this.getData();});
nosliw.registerSetNodeDataEvent("common.event.utility", function(){node_eventUtility = this.getData();});
nosliw.registerSetNodeDataEvent("common.objectwithname.getObjectName", function(){node_getObjectName = this.getData();});
nosliw.registerSetNodeDataEvent("common.objectwithtype.getObjectType", function(){node_getObjectType = this.getData();});
nosliw.registerSetNodeDataEvent("request.utility", function(){node_requestUtility = this.getData();});
nosliw.registerSetNodeDataEvent("request.request.createServiceRequestInfoSequence", function(){	node_createServiceRequestInfoSequence = this.getData();	});
nosliw.registerSetNodeDataEvent("common.service.ServiceInfo", function(){node_ServiceInfo = this.getData();	});
nosliw.registerSetNodeDataEvent("request.request.createServiceRequestInfoSimple", function(){node_createServiceRequestInfoSimple = this.getData();});
nosliw.registerSetNodeDataEvent("statemachine.createStateMachine", function(){node_createStateMachine = this.getData();	});
nosliw.registerSetNodeDataEvent("statemachine.CommandInfo", function(){node_CommandInfo = this.getData();	});
nosliw.registerSetNodeDataEvent("statemachine.createStateMachineDef", function(){node_createStateMachineDef = this.getData();	});
nosliw.registerSetNodeDataEvent("statemachine.TransitInfo", function(){node_TransitInfo = this.getData();	});
nosliw.registerSetNodeDataEvent("statemachine.StateTransitPath", function(){node_StateTransitPath = this.getData();	});
nosliw.registerSetNodeDataEvent("request.requestServiceProcessor", function(){node_requestServiceProcessor = this.getData();});


//Register Node by Name
packageObj.createChildNode("makeObjectWithComponentLifecycle", node_makeObjectWithComponentLifecycle); 
packageObj.createChildNode("getComponentLifecycleInterface", node_getComponentLifecycleInterface); 

})(packageObj);
