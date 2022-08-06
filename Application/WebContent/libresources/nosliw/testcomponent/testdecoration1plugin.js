//get/create package
var packageObj = library;    

(function(packageObj){
	//get used node
	var node_CONSTANT;
	var node_COMMONATRIBUTECONSTANT;
	var node_COMMONCONSTANT;
	var node_createServiceRequestInfoSimple;
	
//*******************************************   Start Node Definition  ************************************** 	

var node_createTestDecoration1Plugin = function(){
	
	var loc_out = {

		createComplexEntityCore : function(complexEntityDef, variableGroupId, bundleCore, configure){
			return loc_createTestDecoration1ComponentCore(complexEntityDef, variableGroupId, bundleCore, configure);
		},
			
	};

	return loc_out;
};

var loc_createTestDecoration1ComponentCore = function(complexEntityDef, variableGroupId, bundleCore, configure){
	var loc_id = nosliw.generateId();
	
	var loc_parentView;
	var loc_mainView;
	var loc_wrapperView;
	var loc_configure = configure;
	
	var loc_out = {

		getDataType: function(){    return  "testDecoration1";   },
		getId : function(){  return loc_id;   },
		getConfigure : function(){   return loc_configure;    },
			
		//call back to provide runtime context : view (during init phase)
		getUpdateRuntimeContextRequest : function(runtimeContext, handlers, request){
			loc_parentView = $(runtimeContext.view);
			return node_createServiceRequestInfoSimple(undefined, function(request){
				loc_mainView = $('<div class="dock" style="border-width:thick; border-style:solid; border-color:green">Decoration1</div>');
				loc_wrapperView = $('<div></div>');
				loc_mainView.append(loc_wrapperView);
				loc_parentView.append(loc_mainView);
				
				return _.extend({}, runtimeContext, {
					view : loc_wrapperView.get(),
				});
			}, handlers, request);
		},
	};
	
	return loc_out;
};

//*******************************************   End Node Definition  ************************************** 	

//populate dependency node data
nosliw.registerSetNodeDataEvent("constant.CONSTANT", function(){node_CONSTANT = this.getData();});
nosliw.registerSetNodeDataEvent("constant.COMMONCONSTANT", function(){node_COMMONCONSTANT = this.getData();});
nosliw.registerSetNodeDataEvent("constant.COMMONATRIBUTECONSTANT", function(){node_COMMONATRIBUTECONSTANT = this.getData();});
nosliw.registerSetNodeDataEvent("request.request.createServiceRequestInfoSimple", function(){	node_createServiceRequestInfoSimple = this.getData();	});

//Register Node by Name
packageObj.createChildNode("createTestDecoration1Plugin", node_createTestDecoration1Plugin); 

})(packageObj);
