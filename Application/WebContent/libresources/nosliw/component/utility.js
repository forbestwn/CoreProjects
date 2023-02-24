//get/create package
var packageObj = library;    

(function(packageObj){
	//get used node
	var node_CONSTANT;
	var node_getComponentLifecycleInterface;
	var node_createConfigure;
	var node_basicUtility;
	var node_buildComponentCore;
	var node_buildComplexEntityCoreObject;

//*******************************************   Start Node Definition  ************************************** 	
var node_createComplexEntityEnvInterface = function(complexEntityCore, valueContextId, bundleCore){
	
	var loc_complexEntityCore = complexEntityCore;
	
	var loc_bundleCore = bundleCore;
	
	var loc_valueContextId = valueContextId;
	
	var loc_out = {
		complexUtility : {
			createChildComplexRuntimeRequest : function(complexEntityId, configure, handlers, request){
				return nosliw.runtime.getPackageService().getCreateComplexEntityRuntimeRequest(complexEntityId, loc_complexEntityCore, loc_bundleCore, configure, handlers, request);
			},
		}	
	};
	
	return loc_out;
};
		
	
var node_componentUtility = {
	
	makeComplexEntityCored : function(rawComplexEntityCore, valueContextId, bundleCore){
		var complexEntityCore = node_buildComponentCore(rawComplexEntityCore, false);
		
		complexEntityCore = node_buildComplexEntityCoreObject(complexEntityCore, valueContextId, bundleCore);
		
		var envInterface = node_createComplexEntityEnvInterface(complexEntityCore, valueContextId, bundleCore);
		complexEntityCore.setEnvironmentInterface(envInterface);
		return complexEntityCore;
	},
		
	makeNewRuntimeContext : function(oldRuntimeContext, override){
		return _.extend({}, oldRuntimeContext, override);
	},

	makeChildRuntimeContext : function(currentRuntimeContext, childId, childComponent, view){
		var newRuntimeContext = {
			backupState : currentRuntimeContext.backupState.createChildState(childId),
			lifecycleEntity : currentRuntimeContext.lifecycleEntity.createChild(childId)
		};
		if(view!=undefined)  newRuntimeContext.view = view;
		
		return this.makeNewRuntimeContext(currentRuntimeContext, newRuntimeContext); 
	},

	isActive : function(status){  return status==node_CONSTANT.LIFECYCLE_COMPONENT_STATUS_ACTIVE;    },

	//process runtime configure to figure out
	//    configure for runtime itself
	//    decoration configure
	processRuntimeConfigure : function(configure){
		configure = node_createConfigure(configure);
		var packageConfigure;
		var decorationInfos = [];
		var decorationConfigureName = node_basicUtility.buildNosliwFullName(node_CONSTANT.CONFIGURE_DECORATION);
		var coreConfigureName = node_basicUtility.buildNosliwFullName(node_CONSTANT.CONFIGURE_CORE);
		if(configure.isChildExist(decorationConfigureName) || configure.isChildExist(coreConfigureName)){
			var coreConfigure = configure.getChildConfigure(coreConfigureName);
			var decsConfigure = configure.getChildConfigure(decorationConfigureName);

			var idSet = decsConfigure.getChildrenIdSet();
			_.each(idSet, function(id, index){
				var decConfigure = decsConfigure.getChildConfigure(id);
				
				var decInfoConfigureValue = decConfigure.getChildConfigure(node_basicUtility.buildNosliwFullName(node_CONSTANT.CONFIGURE_DECORATION_INFO)).getConfigureValue();
				var decCoreConfigure = decConfigure.getChildConfigure(node_basicUtility.buildNosliwFullName(node_CONSTANT.CONFIGURE_DECORATION_CORE));
				decorationInfos.push(new node_DecorationInfo(decInfoConfigureValue.name, decInfoConfigureValue.type, decInfoConfigureValue.id, decCoreConfigure));
			});
		}
		else{
			coreConfigure = configure;
		}
		
		return {
			coreConfigure : coreConfigure,
			decorations : decorationInfos
		};
	},
	
	buildDecorationInfoArrayFromConfigure : function(configure, path, type){
		var out = [ ];
		var idSet = configure.getChildrenIdSet(path);
		_.each(idSet, function(id, index){
			var decConfigure = configure.getChildConfigure(path, id);
			var decConfigureValue = decConfigure.getConfigureValue();
			out.push(new node_DecorationInfo(type, decConfigureValue.id, decConfigureValue.name, decConfigureValue.resource, decConfigure));
		});
		return out;
	},
	
//	buildDecorationInfoArrayFromConfigure : function(configure, path, type){
//		var out = [];
//		var idSet = configure.getChildrenIdSet(path);
//		_.each(idSet, function(id, index){
//			var decConfigure = configure.getChildConfigure(path, id);
//			var decConfigureValue = decConfigure.getConfigureValue();
//			out.push(new node_DecorationInfo(type, decConfigureValue.id, decConfigureValue.name, decConfigureValue.resource, decConfigure));
//		});
//		return out;
//	},
	

	cloneDecorationInfoArray : function(decInfoArray){
		var out = [];
		_.each(decInfoArray, function(decInfo, index){
			out.push(new node_DecorationInfo(decInfo.type, decInfo.id, decInfo.name, decInfo.resource, decInfo.configure));
		});
		return out;
	},
	
	//process predefined command for component
	getProcessNosliwCommand : function(componentRuntime, command, parms, handlers, request){
		var coreCommandName = node_basicUtility.getNosliwCoreName(command);
		if(coreCommandName!=undefined && coreCommandName.startsWith("lifecycle_")){
			var lifecycleTransit = coreCommandName.substring("lifecycle_".length);
			return node_getComponentLifecycleInterface(componentRuntime).getTransitRequest(lifecycleTransit, handlers, request);
		}
		return null;
	},
	
};

//*******************************************   End Node Definition  ************************************** 	
//populate dependency node data
nosliw.registerSetNodeDataEvent("constant.CONSTANT", function(){node_CONSTANT = this.getData();});
nosliw.registerSetNodeDataEvent("component.getComponentLifecycleInterface", function(){node_getComponentLifecycleInterface = this.getData();});
nosliw.registerSetNodeDataEvent("component.createConfigure", function(){node_createConfigure = this.getData();});
nosliw.registerSetNodeDataEvent("component.getStateMachineDefinition", function(){node_getStateMachineDefinition = this.getData();});
nosliw.registerSetNodeDataEvent("common.utility.basicUtility", function(){node_basicUtility = this.getData();});
nosliw.registerSetNodeDataEvent("component.buildComponentCore", function(){node_buildComponentCore = this.getData();});
nosliw.registerSetNodeDataEvent("component.buildComplexEntityCoreObject", function(){node_buildComplexEntityCoreObject = this.getData();});

//Register Node by Name
packageObj.createChildNode("componentUtility", node_componentUtility); 

})(packageObj);
