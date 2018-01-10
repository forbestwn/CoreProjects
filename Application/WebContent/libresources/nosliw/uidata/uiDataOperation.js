//get/create package
var packageObj = library.getChildPackage("uidataoperation");    

(function(packageObj){
//get used node
var node_CONSTANT;	
var node_getObjectType;
var node_ServiceInfo;
var node_createServiceRequestInfoSet;
var node_namingConvensionUtility;
var node_dataUtility;
//*******************************************   Start Node Definition  ************************************** 	

//create request for data operation
var node_createUIDataOperationRequest = function(context, uiDataOperation, handlers, requester_parent){
	var target = uiDataOperation.target;
	var targetType = node_getObjectType(target);
	var operationService = uiDataOperation.operationService;
	var loc_context = context;
	var request;
	switch(targetType)
	{
	case node_CONSTANT.TYPEDOBJECT_TYPE_WRAPPER:
		request = target.getDataOperationRequest(operationService, handlers, requester_parent);
		break;
	case node_CONSTANT.TYPEDOBJECT_TYPE_VARIABLE:
		request = target.getDataOperationRequest(operationService, handlers, requester_parent);
		break;
	case node_CONSTANT.TYPEDOBJECT_TYPE_CONTEXTVARIABLE:
		operationService.parms.path = node_namingConvensionUtility.cascadePath(target.path, operationService.path);
		request = loc_context.getDataOperationRequest(target.name, operationService, handlers, requester_parent);
		break;
	default : 
		//target is context element name
		request = loc_context.getDataOperationRequest(target, operationService, handlers, requester_parent);
	}
	return request;
};

/**
 * Request for batch of data operation
 * It contains a set of data operation service, so that this request is a batch of data operation as a whole
 * 
 */
var node_createBatchUIDataOperationRequest = function(context, handlers, requester_parent){

	//all the child requests service  
	var loc_uiDataOperations = [];
	
	//data context
	var loc_context = context;
	
	var loc_index = 0;
	
	var loc_out = node_createServiceRequestInfoSet(new node_ServiceInfo("BatchUIDataOperation", {}), handlers, requester_parent)
	
	loc_out.addUIDataOperation = function(uiDataOperation){
		this.addRequest(loc_index+"", node_createUIDataOperationRequest(loc_context, uiDataOperation));
		loc_index++;

		//for debugging purpose
		loc_uiDataOperations.push(uiDataOperation);
	};
	return loc_out;
};

//operate on targe
//   target : variable, wrapper, context variable
//   operationService : service for operation
var node_UIDataOperation = function(target, operationService){
	this.target = target;
	this.operationService = operationService;
};


//utility method to build data operation service
var node_uiDataOperationServiceUtility = function(){

	var loc_createServiceInfo = function(command, parms){
		var out = new node_ServiceInfo(command, parms);
		out.clone = function(){
			return loc_createServiceInfo(out.command, out.parms.clone());
		}
		return out;
	};
	
	var loc_out = {
			createSetOperationData : function(path, value){
				var that = this;
				return {
					path : path,
					value : value,
					clone : function(){
						return that.createSetOperationData(this.path, node_dataUtility.cloneValue(this.value));
					}
				};
			},
			
			createSetOperationService : function(path, value){
				return loc_createServiceInfo(node_CONSTANT.WRAPPER_OPERATION_SET, this.createSetOperationData(path, value));
			},

			createAddElementOperationData : function(path, index, value){
				var that = this;
				return {
					path : path,
					index : index+"",
					value : value,
					clone : function(){
						return that.createAddElementOperationData(this.path, this.index, node_dataUtility.cloneValue(this.value));
					}
				};
			},

			createAddElementOperationService : function(path, index, value){
				return loc_createServiceInfo(node_CONSTANT.WRAPPER_OPERATION_ADDELEMENT, this.createAddElementOperationData(path, index, value));
			},

			createDeleteElementOperationData : function(path, index){
				var that = this;
				return {
					path : path,
					index : index+"",
					clone : function(){
						return that.createDeleteElementOperationData(this.path, this.index);
					}
				};
			},
			
			createDeleteElementOperationService : function(path, index){
				return loc_createServiceInfo(node_CONSTANT.WRAPPER_OPERATION_DELETEELEMENT, this.createDeleteElementOperationData(path, index));
			},

			createGetOperationData : function(path){
				var that = this;
				return {
					path : path,
					clone : function(){
						return that.createGetOperationData(this.path);
					}
				};
			},
			
			createGetOperationService : function(path){
				return loc_createServiceInfo(node_CONSTANT.WRAPPER_OPERATION_GET, this.createGetOperationData(path));
			},
			
			createDestroyOperationData : function(path){
				var that = this;
				return {
					path : path,
					clone : function(){
						return that.createDestroyOperationData(this.path);
					}
				};
			},
			
			createDestroyOperationService : function(path){
				return loc_createServiceInfo(node_CONSTANT.WRAPPER_OPERATION_DESTROY, this.createDestroyOperationData(path));
			}
	};
		
	
	return loc_out;
}();


//*******************************************   End Node Definition  ************************************** 	

//populate dependency node data
nosliw.registerSetNodeDataEvent("constant.CONSTANT", function(){node_CONSTANT = this.getData();});
nosliw.registerSetNodeDataEvent("common.objectwithtype.getObjectType", function(){node_getObjectType = this.getData();});
nosliw.registerSetNodeDataEvent("common.service.ServiceInfo", function(){node_ServiceInfo = this.getData();	});
nosliw.registerSetNodeDataEvent("request.request.createServiceRequestInfoSet", function(){node_createServiceRequestInfoSet = this.getData();});
nosliw.registerSetNodeDataEvent("common.namingconvension.namingConvensionUtility", function(){node_namingConvensionUtility = this.getData();});
nosliw.registerSetNodeDataEvent("uidata.data.utility", function(){node_dataUtility = this.getData();});


//Register Node by Name
packageObj.createChildNode("createUIDataOperationRequest", node_createUIDataOperationRequest); 
packageObj.createChildNode("createBatchUIDataOperationRequest", node_createBatchUIDataOperationRequest); 
packageObj.createChildNode("UIDataOperation", node_UIDataOperation); 
packageObj.createChildNode("uiDataOperationServiceUtility", node_uiDataOperationServiceUtility); 

})(packageObj);
