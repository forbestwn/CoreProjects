
if(typeof nosliw!='undefined' && nosliw.runtime!=undefined && nosliw.runtime.getResourceService()!=undefined) nosliw.runtime.getResourceService().importResource({"id":{"id":"Decoration_application_framework7",
"type":"uiResource"
},
"children":[],
"dependency":{},
"info":{}
}, {"id":"Decoration_application_framework7",
"type":"resource",
"context":{"local2Global":{"nosliw_ui_info":"nosliw_ui_info___public",
"nosliw_application_uiStatus":"nosliw_application_uiStatus___public",
"backIconDisplay":"backIconDisplay___public"
},
"context":{"element":{"nosliw_ui_info___public":{"name":"",
"description":"",
"info":{},
"definition":{"type":"node",
"processed":"true",
"child":{"id":{"type":"value",
"processed":"true"
},
"title":{"type":"value",
"processed":"true"
}
}
}
},
"nosliw_ui_info":{"info":{},
"definition":{"type":"relative",
"processed":"true",
"path":{"rootEleName":"nosliw_ui_info___public"
},
"parent":"self",
"definition":{"type":"node",
"processed":"true",
"child":{"id":{"type":"value",
"processed":"true"
},
"title":{"type":"value",
"processed":"true"
}
}
}
}
},
"nosliw_application_uiStatus___public":{"name":"",
"description":"",
"info":{},
"definition":{"type":"node",
"processed":"true",
"child":{"index":{"type":"value",
"processed":"true"
}
}
}
},
"nosliw_application_uiStatus":{"info":{},
"definition":{"type":"relative",
"processed":"true",
"path":{"rootEleName":"nosliw_application_uiStatus___public"
},
"parent":"self",
"definition":{"type":"node",
"processed":"true",
"child":{"index":{"type":"value",
"processed":"true"
}
}
}
}
},
"backIconDisplay___public":{"name":"",
"description":"",
"info":{},
"definition":{"type":"value",
"processed":"true"
},
"defaultValue":"none"
},
"backIconDisplay":{"info":{},
"definition":{"type":"relative",
"processed":"true",
"path":{"rootEleName":"backIconDisplay___public"
},
"parent":"self",
"definition":{"type":"value",
"processed":"true"
}
},
"defaultValue":"none"
}
}
}
},
"elementEvents":[{"uiId":"381",
"event":"click",
"function":"transferBack"
}],
"tagEvents":[],
"attributes":{"nosliwattribute_placeholder":"id:pleaseEmbed"
},
"html":"&lt;div class=&quot;page-content&quot; nosliwid=&quot;377&quot;&gt;  &lt;div class=&quot;navbar&quot; nosliwid=&quot;378&quot;&gt;   &lt;div class=&quot;navbar-inner&quot; nosliwid=&quot;379&quot;&gt;    &lt;div class=&quot;left&quot; nosliwid=&quot;380&quot;&gt;     &lt;a href=&quot;&quot; class=&quot;link&quot; style=&quot;&quot; nosliwid=&quot;381&quot;&gt;&lt;span&gt;Back&lt;/span&gt;&lt;/a&gt;    &lt;/div&gt;    &lt;div class=&quot;title&quot; nosliwid=&quot;382&quot;&gt;    &lt;span nosliwid=&quot;383&quot;&gt;&lt;/span&gt;   &lt;/div&gt;    &lt;div class=&quot;right&quot; nosliwid=&quot;384&quot;&gt;     &lt;!--			            &lt;a href=&quot;&quot; class=&quot;link&quot; nosliw-event=&quot;click:refresh:&quot;&gt;Refresh&lt;/a&gt;  --&gt;    &lt;/div&gt;   &lt;/div&gt;  &lt;/div&gt;  &lt;div id=&quot;pleaseEmbed&quot; nosliwid=&quot;385&quot;&gt;&lt;/div&gt; &lt;/div&gt;",
"constants":{},
"events":{"transferBack":{"name":"transferBack",
"description":"",
"info":{},
"data":{"element":{}
}
},
"refresh":{"name":"refresh",
"description":"",
"info":{},
"data":{"element":{}
}
}
},
"commands":{},
"services":{},
"serviceProviders":{},
"scriptExpressionsInContent":[{"scriptExpressions":{"0":{"definition":"?(nosliw_ui_info.title)?",
"variableNames":["nosliw_ui_info.title"],
"expressions":{}
}
},
"uiId":"383",
"scriptFunction":function(scriptExpressionData){
	return scriptExpressionData["0"];
} 
,
"scriptExpressionScriptFunction":{"0":function(expressionsData, constantsData, variablesData){
	return variablesData["nosliw_ui_info.title"];
} 

}
}],
"scriptExpressionInAttributes":[{"scriptExpressions":{"1":{"definition":"?(nosliw_application_uiStatus.index)?==0?'none':'inline'",
"variableNames":["nosliw_application_uiStatus.index"],
"expressions":{}
}
},
"uiId":"381",
"attribute":"style",
"scriptFunction":function(scriptExpressionData){
	return "display:"+scriptExpressionData["1"];
} 
,
"scriptExpressionScriptFunction":{"1":function(expressionsData, constantsData, variablesData){
	return variablesData["nosliw_application_uiStatus.index"]==0?'none':'inline';
} 

}
}],
"scriptExpressionTagAttributes":[],
"uiTags":{},
"script":{ transferBack : function(info, env){ event.preventDefault(); env.trigueEvent("nosliw_transferBack", info.eventData); }, refresh : function(info, env){ event.preventDefault(); env.trigueEvent("nosliw_refresh", info.eventData); }, }
}, {"loadPattern":"file"
});

