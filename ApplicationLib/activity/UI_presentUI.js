/**
 * 
 */
{
	type : "UI_presentUI",
	
	processor : "com.nosliw.uiresource.module.activity.HAPPresentUIActivityProcessor",
	
	definition : "com.nosliw.uiresource.module.activity.HAPPresentUIActivityDefinition",
	
	script : {
		
		javascript : function(nosliw, env){
			var loc_env = env;
			var loc_expressionService = nosliw.runtime.getExpressionService(); 
			var node_COMMONATRIBUTECONSTANT = nosliw.getNodeData("constant.COMMONATRIBUTECONSTANT");
			var node_COMMONCONSTANT = nosliw.getNodeData("constant.COMMONCONSTANT");
			var node_createServiceRequestInfoService = nosliw.getNodeData("request.request.createServiceRequestInfoService");
			var node_DependentServiceRequestInfo = nosliw.getNodeData("request.request.entity.DependentServiceRequestInfo");
			var node_IOTaskResult = nosliw.getNodeData("iotask.entity.IOTaskResult");
			var node_createServiceRequestInfoSequence = nosliw.getNodeData("request.request.createServiceRequestInfoSequence");
			var node_objectOperationUtility = nosliw.getNodeData("common.utility.objectOperationUtility");
			var node_ServiceInfo = nosliw.getNodeData("common.service.ServiceInfo");

			var loc_out = {
				getExecuteActivityRequest : function(activity, input, env, handlers, request){
					var out = node_createServiceRequestInfoSequence(new node_ServiceInfo("PresentUIActivity", {}), handlers, request);
					out.addRequest(env.getPresentUIRequest(activity.ui, undefined, {
						success : function(request){
							return new node_IOTaskResult(node_COMMONCONSTANT.ACTIVITY_RESULT_SUCCESS);
						}
					}));
					return out;
				}
			};
			return loc_out;
		}
	} 
}