
nosliw.runtime.getResourceService().importResource({"id":{"id":"UI_executeUICommand",
"type":"activityPlugin"
},
"children":[],
"dependency":{},
"info":{}
}, {"script":
function (nosliw, env) {
    var node_COMMONCONSTANT = nosliw.getNodeData("constant.COMMONCONSTANT");
    var node_createServiceRequestInfoSimple = nosliw.getNodeData("request.request.createServiceRequestInfoSimple");
    var node_IOTaskResult = nosliw.getNodeData("iotask.entity.IOTaskResult");
    var node_ServiceInfo = nosliw.getNodeData("common.service.ServiceInfo");
    var loc_out = {getExecuteActivityRequest: function (activity, input, env, handlers, request) {
        return node_createServiceRequestInfoSimple(new node_ServiceInfo("ExecuteUICommandActivity", {}), function (requestInfo) {
            env.executeUICommand(activity.ui, activity.command, input);
            return new node_IOTaskResult(node_COMMONCONSTANT.ACTIVITY_RESULT_SUCCESS);
        }, handlers, request);
    }};
    return loc_out;
}

}, {"loadPattern":"file"
});
