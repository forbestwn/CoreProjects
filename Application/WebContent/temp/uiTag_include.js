
if(typeof nosliw!='undefined' && nosliw.runtime!=undefined && nosliw.runtime.getResourceService()!=undefined) nosliw.runtime.getResourceService().importResource({"id":{"id":"include",
"type":"uiTag"
},
"children":[],
"dependency":{},
"info":{}
}, {"name":"include",
"context":{"group":{"public":{"element":{}
},
"protected":{"element":{}
},
"internal":{"element":{}
},
"private":{"element":{}
}
},
"info":{"inherit":"false",
"escalate":"true"
}
},
"attributes":{"source":{"name":"source",
"description":""
},
"context":{"name":"context",
"description":""
},
"event":{"name":"event",
"description":""
}
},
"event":[],
"script":
function (env) {
    var node_createServiceRequestInfoSequence = nosliw.getNodeData("request.request.createServiceRequestInfoSequence");
    var loc_env = env;
    var loc_resourceView;
    var loc_view;
    var loc_out = {findFunctionDown: function (name) {
        return loc_resourceView.findFunctionDown(name);
    }, initViews: function (requestInfo) {
        loc_view = $("<div/>");
        return loc_view;
    }, postInit: function (requestInfo) {
        var out = node_createServiceRequestInfoSequence(undefined);
        out.addRequest(loc_env.getCreateDefaultUIViewRequest({success: function (requestInfo, uiView) {
            loc_resourceView = uiView;
            loc_resourceView.appendTo(loc_view);
        }}));
        return out;
    }, destroy: function () {
        loc_resourceView.detachViews();
        loc_resourceView.destroy();
    }};
    return loc_out;
}

}, {"loadPattern":"file"
});

