//get/create package
var packageObj = library;    

(function(packageObj){
	//get used node
	var node_CONSTANT;
	var node_COMMONATRIBUTECONSTANT;
	var node_COMMONCONSTANT;
	var node_createServiceRequestInfoSimple;
	var node_createServiceRequestInfoSequence;
	var node_ServiceInfo;
	var node_createConfigure;
	var node_createErrorData;
	var node_createTestSimple1;
	
//*******************************************   Start Node Definition  ************************************** 	

var node_createTestComplex1Plugin = function(){
	
	var loc_out = {

		createComplexEntityCore : function(complexEntityDef, variableGroupId, bundleCore, configure){
			return loc_createTestComplex1ComponentCore(complexEntityDef, variableGroupId, bundleCore, configure);
		},
			
	};

	return loc_out;
};

var loc_createTestComplex1ComponentCore = function(complexEntityDef, variableGroupId, bundleCore, configure){
	var loc_variableGroupId = variableGroupId;
	var loc_complexEntityDef = complexEntityDef;
	var loc_runtimeInterface;
	var loc_bundleCore = bundleCore;
	var loc_configureValue = node_createConfigure(configure).getConfigureValue();
	var loc_parentView;
	var loc_mainView;
	var loc_children = {};
	var loc_childrenViews = {};
	var loc_stateValueView;
	
	var loc_simpleTest1Atts = {};
	
	var loc_init = function(complexEntityDef, variableGroupId, bundleCore, configure){
		var attrs = complexEntityDef[node_COMMONATRIBUTECONSTANT.EXECUTABLEENTITY_ATTRIBUTE];
		_.each(attrs, function(attr, attrName){
			var entityType = attr[node_COMMONATRIBUTECONSTANT.EXECUTABLEENTITY_ENTITYTYPE];
			if(entityType==node_COMMONCONSTANT.RUNTIME_RESOURCE_TYPE_TEST_SIMPLE1){
				//test simple attribute
				loc_simpleTest1Atts[attrName] = node_createTestSimple1(attr);
			}
		});
	};
	
	var loc_simpleTest1AttrsInvoke = function(funName, parm1, parm2){
		var argus = arguments;
		_.each(loc_simpleTest1Atts, function(simpleTest1Att, name){
			simpleTest1Att.callBack.apply(simpleTest1Att, argus);
		});
	};
	
	var loc_out = {

		getDataType: function(){    return  "testComplex1";   },
		getConfigure : function(){   return loc_configureValue;    },
		getVariableGroupId : function(){   return loc_variableGroupId;     },
		
		getPreInitRequest : function(handlers, request){
			var out = node_createServiceRequestInfoSequence(new node_ServiceInfo("PreInitCoreTextComplex", {}), handlers, request);
			
			//complex children
			var childrenComplex = loc_complexEntityDef[node_COMMONATRIBUTECONSTANT.EXECUTABLEENTITYCOMPLEX_CHILDREN];
			_.each(childrenComplex, function(childComplex, attrName){
				var complexEntityId = childComplex[node_COMMONATRIBUTECONSTANT.EMBEDEDWITHID_ENTITYID];
				var attrEntity = nosliw.runtime.getPackageService().createComplexEntityRuntime(complexEntityId, loc_out, loc_bundleCore, loc_configureValue[attrName]);
				loc_children[attrName] = attrEntity;
				out.addRequest(attrEntity.getPreInitRequest());
			});
			
			//simpletest1 attribute
			loc_simpleTest1AttrsInvoke("getPreInitRequest");
			
			return out;
		},		
		
		//call back to provide runtime context : view (during init phase)
		getUpdateRuntimeContextRequest : function(runtimeContext, handlers, request){
			var out = node_createServiceRequestInfoSequence(new node_ServiceInfo("UpdateRuntimeContextCoreTextComplex", {}), handlers, request);

			loc_parentView = $(runtimeContext.view);
			loc_mainView = $('<div class="view view-main" style="overflow-y1: scroll; border-width:thick; border-style:solid; border-color:black">testComplex</div>');
			loc_parentView.append(loc_mainView);
			
//			var stateValueViewWrapper = $('<div>state:</div>');
//			loc_stateValueView = $('<input type="text">');
//			stateValueViewWrapper.append(loc_stateValueView);
//			loc_mainView.append(stateValueViewWrapper);

			//complex children
			_.each(loc_children, function(child, childName){
				var childView = $('<div>childAttr: '+childName+'</div>');
				loc_mainView.append(childView);
				loc_childrenViews[childName] = childView;
				
				var childRuntimeContext = _.extend({}, runtimeContext, {
					view : childView
				});
				out.addRequest(child.getUpdateRuntimeContextRequest(childRuntimeContext));
			});
			
			//simpletest1 attribute
			loc_simpleTest1AttrsInvoke("getUpdateRuntimeContextRequest", runtimeContext);
			
			return out;
		},

		getUpdateRuntimeInterfaceRequest : function(runtimeInterface, handlers, request){   
			var out = node_createServiceRequestInfoSequence(new node_ServiceInfo("UpdateRuntimeInterfaceInComponentRuntime", {}), handlers, request);
			loc_runtimeInterface = runtimeInterface;
			return out;
		},

		getPostInitRequest : function(handlers, request){
		},

		//lifecycle handler
		getLifeCycleRequest : function(transitName, handlers, request){
			if(!transitName.startsWith("_")){
//				return node_createErrorData("code", "message", "data");
//				var k = aaa.bbb.ccc;
			}
		},
		
		
		getGetStateDataRequest : function(handlers, request){
			return node_createServiceRequestInfoSimple(undefined, function(request){
				var out = {};
				_.each(loc_simpleTest1Atts, function(simpleTest1Att, name){
					out[name] = simpleTest1Att.callBack("getGetStateDataRequest");
				});
				return out;
			}, handlers, request);
		},
		getRestoreStateDataRequest : function(stateData, handlers, request){
			return node_createServiceRequestInfoSimple(undefined, function(request){
				_.each(loc_simpleTest1Atts, function(simpleTest1Att, name){
					simpleTest1Att.callBack("getRestoreStateDataRequest", stateData==undefined?undefined:stateData[name]);
				});
			}, handlers, request);
		},

		
		
		
		//execute command
		getExecuteCommandRequest : function(commandName, parm, handlers, requestInfo){},
		getExecuteNosliwCommandRequest : function(commandName, parm, handlers, requestInfo){   this.getExecuteCommandRequest(node_basicUtility.buildNosliwFullName(commandName), parm, handlers, requestInfo);    },

		//get part by id
		getPart : function(partId){ },
		
		//get interface exposed
		getInterface : function(){ return {}; },

		//set state for the component core
		setState : function(state){   },

		//component runtime env
		getRuntimeEnv : function(){   return loc_runtimeEnv;    },
		setRuntimeEnv : function(runtimeEnv){   loc_runtimeEnv = runtimeEnv;     },
		
		//value by name
		getValue : function(name){},
		setValue : function(name, value){},
		
		setLifeCycleStatus : function(status){},
		
		//call back when core embeded into runtime during init phase
		getUpdateRuntimeRequest : function(runtimeEnv, handlers, request){},
		
		registerEventListener : function(listener, handler, thisContext){  },
		unregisterEventListener : function(listener){ },

		registerValueChangeEventListener : function(listener, handler, thisContext){   },
		unregisterValueChangeEventListener : function(listener){ },
		
		startLifecycleTask : function(){},
		endLifecycleTask : function(){},
			
	};
	
	loc_init(complexEntityDef, variableGroupId, bundleCore, configure);
	
	return loc_out;
};

//*******************************************   End Node Definition  ************************************** 	

//populate dependency node data
nosliw.registerSetNodeDataEvent("constant.CONSTANT", function(){node_CONSTANT = this.getData();});
nosliw.registerSetNodeDataEvent("constant.COMMONCONSTANT", function(){node_COMMONCONSTANT = this.getData();});
nosliw.registerSetNodeDataEvent("constant.COMMONATRIBUTECONSTANT", function(){node_COMMONATRIBUTECONSTANT = this.getData();});
nosliw.registerSetNodeDataEvent("request.request.createServiceRequestInfoSimple", function(){	node_createServiceRequestInfoSimple = this.getData();	});
nosliw.registerSetNodeDataEvent("request.request.createServiceRequestInfoSequence", function(){	node_createServiceRequestInfoSequence = this.getData();	});
nosliw.registerSetNodeDataEvent("common.service.ServiceInfo", function(){node_ServiceInfo = this.getData();	});
nosliw.registerSetNodeDataEvent("component.createConfigure", function(){node_createConfigure = this.getData();});
nosliw.registerSetNodeDataEvent("error.entity.createErrorData", function(){node_createErrorData = this.getData();});
nosliw.registerSetNodeDataEvent("testcomponent.createTestSimple1", function(){node_createTestSimple1 = this.getData();	});

//Register Node by Name
packageObj.createChildNode("createTestComplex1Plugin", node_createTestComplex1Plugin); 

})(packageObj);
