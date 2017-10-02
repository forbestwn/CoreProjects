//get/create package
var packageObj = library;    

(function(packageObj){
	//get used node
	var node_buildServiceProvider;
	var node_requestUtility;
	var node_createServiceRequestInfoExecutor;
	var node_requestServiceProcessor;
	var node_ServiceInfo;
	var node_ServiceRequestExecuteInfo;
	var node_COMMONATRIBUTECONSTANT;
//*******************************************   Start Node Definition  ************************************** 	
	
/**
 * Create Resource Service
 * This service response to request from user
 * Load resource to resource manager if needed
 */
var node_createGatewayService = function(){
	
	var loc_getGatewayObject = function(){
		return nosliw.getNodeData(node_COMMONATRIBUTECONSTANT.RUNTIME_NODENAME_GATEWAY);
	};
	
	var loc_out = {
		
		getExecuteGatewayCommandRequest : function(gatewayId, command, parms, handlers, requester_parent){
			var requestInfo = loc_out.getRequestInfo(requester_parent);

			var gatewayObject = loc_getGatewayObject();
			var executor = new node_ServiceRequestExecuteInfo(function(requestInfo){
				var serviceData = this.executeGateway(gatewayId, command, parms);
				loc_out.exectueHandlerByServiceData(serviceData, this);
			}, gatewayObject);
			
			var service = new node_ServiceInfo("ExecuteGatewayCommand", {"gatewayId":gatewayId, "command":command, "parms": parms});
			var out = node_createServiceRequestInfoExecutor(service, executor, handlers, requestInfo);
			return out;
		},	
			
		executeExecuteGatewayCommandRequest : function(gatewayId, gatewayCommand, parms, requester_parent){
			var requestInfo = this.getExecuteGatewayCommandRequest(gatewayId, gatewayCommand, parms, requester_parent);
			node_requestServiceProcessor.processRequest(requestInfo, false);
		},
		
	};
	
	loc_out = node_buildServiceProvider(loc_out, "gatewayService");
	
	return loc_out;
};	

//*******************************************   End Node Definition  ************************************** 	

//populate dependency node data
nosliw.registerSetNodeDataEvent("request.utility", function(){node_requestUtility = this.getData();});
nosliw.registerSetNodeDataEvent("request.buildServiceProvider", function(){node_buildServiceProvider = this.getData();});
nosliw.registerSetNodeDataEvent("request.request.createServiceRequestInfoExecutor", function(){node_createServiceRequestInfoExecutor = this.getData();});
nosliw.registerSetNodeDataEvent("request.requestServiceProcessor", function(){node_requestServiceProcessor = this.getData();});
nosliw.registerSetNodeDataEvent("common.service.ServiceInfo", function(){node_ServiceInfo = this.getData();});
nosliw.registerSetNodeDataEvent("request.entity.ServiceRequestExecuteInfo", function(){node_ServiceRequestExecuteInfo = this.getData();});
nosliw.registerSetNodeDataEvent("constant.COMMONATRIBUTECONSTANT", function(){node_COMMONATRIBUTECONSTANT = this.getData();});

//Register Node by Name
packageObj.createChildNode("createGatewayService", node_createGatewayService); 

})(packageObj);
