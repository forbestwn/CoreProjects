
nosliw.runtime.getResourceService().importResource({"id":{"id":"casedefault",
"type":"uiTag"
},
"children":[],
"dependency":{},
"info":{}
}, {"name":"casedefault",
"context":{"inherit":true,
"public":{}
},
"attributes":{},
"script":
function (context, parentResourceView, uiTagResource, attributes, tagEnv) {
    var node_createContextVariableInfo = nosliw.getNodeData("uidata.context.createContextVariableInfo");
    var node_createDataOperationRequest = nosliw.getNodeData("uidata.dataoperation.createDataOperationRequest");
    var node_DataOperationService = nosliw.getNodeData("uidata.dataoperation.DataOperationService");
    var node_CONSTANT = nosliw.getNodeData("constant.CONSTANT");
    var node_requestServiceProcessor = nosliw.getNodeData("request.requestServiceProcessor");
    var node_createUIResourceViewFactory = nosliw.getNodeData("uiresource.createUIResourceViewFactory");
    var node_createContextElementInfo = nosliw.getNodeData("uidata.context.createContextElementInfo");
    var node_createContext = nosliw.getNodeData("uidata.context.createContext");
    var loc_context = context;
    var loc_uiTagResource = uiTagResource;
    var loc_parentResourceView = parentResourceView;
    var loc_tagEnv = tagEnv;
    var loc_foundContextEleName = "private_found";
    var loc_foundVariable = loc_context.createVariable(node_createContextVariableInfo(loc_foundContextEleName));
    var loc_view;
    var loc_startEle;
    var loc_endEle;
    var loc_resourceView;
    var loc_out = {ovr_initViews: function (startEle, endEle, requestInfo) {
        loc_startEle = startEle;
        loc_endEle = endEle;
        loc_resourceView = node_createUIResourceViewFactory().createUIResourceView(loc_uiTagResource, loc_tagEnv.getId(), loc_parentResourceView, loc_context, requestInfo);
    }, ovr_postInit: function () {
    }, ovr_preInit: function () {
    }, found: function (found) {
        if (found == true) {
            loc_resourceView.detachViews();
        } else {
            loc_resourceView.insertAfter(loc_startEle);
        }
    }};
    return loc_out;
}

}, {"loadPattern":"file"
});

