
if(typeof nosliw!='undefined' && nosliw.runtime!=undefined && nosliw.runtime.getResourceService()!=undefined) nosliw.runtime.getResourceService().importResource({"id":{"id":"string-options",
"type":"uiTag"
},
"children":[],
"dependency":{},
"info":{}
}, {"name":"string-options",
"context":{"group":{"public":{"element":{}
},
"protected":{"element":{}
},
"internal":{"element":{}
},
"private":{"element":{"internal_data":{"name":"",
"description":"",
"info":{},
"definition":{"type":"relative",
"processed":"false",
"path":{"rootEleName":"<%=&(nosliwattribute_data)&%>"
},
"parent":"default",
"definition":{"type":"data",
"processed":"false",
"criteria":{"status":"close",
"criteria":"test.string;1.0.0",
"info":{}
}
}
}
}
}
}
},
"info":{"inherit":"false"
}
},
"attributes":{"id":{"name":"id",
"description":""
},
"data":{"name":"data",
"description":""
}
},
"event":[],
"script":
function (env) {
    var node_OperationParm = nosliw.getNodeData("expression.entity.OperationParm");
    var loc_env = env;
    var loc_dataVariable = env.createVariable("internal_data");
    var loc_view;
    var loc_revertChange = function () {
    };
    var loc_getViewData = function () {
        return {dataTypeId: "test.string;1.0.0", value: loc_view.val()};
    };
    var loc_updateView = function (request) {
        loc_env.executeDataOperationRequestGet(loc_dataVariable, "", {success: function (requestInfo, data) {
            if (data == undefined) {
                loc_view.val();
            } else {
                loc_view.val(data.value.value);
            }
        }}, request);
    };
    var loc_setupUIEvent = function () {
        loc_view.bind("change", function () {
            loc_env.executeBatchDataOperationRequest([loc_env.getDataOperationSet(loc_dataVariable, "", loc_getViewData())]);
        });
    };
    var loc_out = {preInit: function () {
    }, initViews: function (requestInfo) {
        loc_view = $("<select/>");
        var operationParms = [];
        operationParms.push(new node_OperationParm({dataTypeId: "test.string;1.0.0", value: loc_env.getAttributeValue("id")}, "optionsId"));
        loc_env.executeExecuteOperationRequest("test.options;1.0.0", "all", operationParms, {success: function (request, optionsValueArray) {
            _.each(optionsValueArray.value, function (optionsValue, i) {
                loc_view.append($("<option>", {value: optionsValue.value, text: optionsValue.value}));
            });
            loc_updateView(request);
        }}, requestInfo);
        return loc_view;
    }, postInit: function (request) {
        loc_updateView(request);
        loc_setupUIEvent();
        loc_dataVariable.registerDataOperationEventListener(undefined, function (event, eventData, request) {
            loc_updateView(request);
        }, this);
    }, destroy: function () {
        loc_dataVariable.release();
        loc_view.remove();
    }};
    return loc_out;
}

}, {"loadPattern":"file"
});
