
nosliw.runtime.getResourceService().importResource({"id":{"id":"Example1",
"type":"uiResource"
},
"children":[],
"dependency":{},
"info":{}
}, {"id":"Example1",
"type":"resource",
"context":{"publicContext":{"business":{"children":{"a":{"children":{"aa":{"children":{},
"definition":"test.string;1.0.0"
},
"bb":{"children":{},
"definition":"test.array;1.0.0%||element:@||!(test.expression)!.outputCriteria(&amp;(expression)&amp;;;&amp;(parms)&amp;)||@||%"
},
"cc":{"children":{"element":{"children":{},
"definition":"test.string;1.0.0"
}
}
}
}
}
},
"type":"absolute",
"default":{"a":{"aa":{"dataTypeId":"test.string;1.0.0","value":"This is my world!"},"dd":"HELLO!!!!","cc":[{"dataTypeId":"test.string;1.0.0","value":"This is my world 1111!"},{"dataTypeId":"test.string;1.0.0","value":"This is my world 2222!"}]}}
}
},
"internalContext":{}
},
"scriptExpressionsInContent":[{"uiId":"15",
"scriptExpressions":{"0":{"id":"0",
"definition":"#|?(business)?.a.aa.subString(from:&(from)&,to:&(to)&)|#.value + ?(business.a.dd)? + ' 6666 ' ",
"variableNames":["business.a.aa","business.a.dd"],
"expressions":{"0_0":{"id":"0_0_no7",
"expressionDefinition":{"name":"0_0",
"expression":"?(business)?.a.aa.subString(from:&(from)&,to:&(to)&)",
"variableCriterias":{},
"constants":{},
"references":{}
},
"operand":{"type":"operation",
"outputCriteria":"test.string;1.0.0",
"operation":"subString",
"dataTypeId":"test.string;1.0.0",
"parms":{"from":{"type":"constant",
"outputCriteria":"test.integer;1.0.0",
"name":"from",
"data":{"dataTypeId":"test.integer;1.0.0",
"valueFormat":"JSON",
"value":3
}
},
"to":{"type":"constant",
"outputCriteria":"test.integer;1.0.0",
"name":"to",
"data":{"dataTypeId":"test.integer;1.0.0",
"valueFormat":"JSON",
"value":7
}
},
"base":{"type":"variable",
"outputCriteria":"#||test.string;1.0.0||#",
"variableName":"business.a.aa"
}
},
"matchersParms":{"base":{"test.string;1.0.0":{"dataTypeId":"test.string;1.0.0",
"relationship":{"source":"test.string;1.0.0",
"target":"test.string;1.0.0",
"path":[],
"targetType":"self"
},
"subMatchers":{}
}
},
"from":{"test.integer;1.0.0":{"dataTypeId":"test.integer;1.0.0",
"relationship":{"source":"test.integer;1.0.0",
"target":"test.integer;1.0.0",
"path":[],
"targetType":"self"
},
"subMatchers":{}
}
},
"to":{"test.integer;1.0.0":{"dataTypeId":"test.integer;1.0.0",
"relationship":{"source":"test.integer;1.0.0",
"target":"test.integer;1.0.0",
"path":[],
"targetType":"self"
},
"subMatchers":{}
}
}
}
},
"variableInfos":{"business.a.aa":{"status":"close",
"criteria":"test.string;1.0.0"
},
"business.a.bb":{"status":"close",
"criteria":"test.array;1.0.0%||element:@||!(test.expression)!.outputCriteria(&amp;(expression)&amp;;;&amp;(parms)&amp;)||@||%"
},
"business.a.cc.element":{"status":"close",
"criteria":"test.string;1.0.0"
}
},
"errorMsgs":[],
"variablesMatchers":{"business.a.aa":{"test.string;1.0.0":{"dataTypeId":"test.string;1.0.0",
"relationship":{"source":"test.string;1.0.0",
"target":"test.string;1.0.0",
"path":[],
"targetType":"self"
},
"subMatchers":{}
}
}
},
"references":{}
}
},
"scriptFunction":function(expressionsData, constantsData, variablesData){
	return expressionsData["0_0"].value + variablesData["business.a.dd"] + ' 6666 ' ;
} 

}
},
"scriptFunction":function(scriptExpressionData){
	return scriptExpressionData["0"];
} 

},{"uiId":"14",
"scriptExpressions":{"0":{"id":"0",
"definition":"?(business.a.aa)?.value + ' 6666 ' ",
"variableNames":["business.a.aa"],
"expressions":{},
"scriptFunction":function(expressionsData, constantsData, variablesData){
	return variablesData["business.a.aa"].value + ' 6666 ' ;
} 

}
},
"scriptFunction":function(scriptExpressionData){
	return scriptExpressionData["0"];
} 

}],
"scriptExpressionInAttributes":[{"uiId":"19",
"scriptExpressions":{"0":{"id":"0",
"definition":"#|?(business)?.a.aa.subString(from:&(from)&,to:&(to)&)|#.value=='s isfff'?'red':'blue'",
"variableNames":["business.a.aa"],
"expressions":{"0_0":{"id":"0_0_no8",
"expressionDefinition":{"name":"0_0",
"expression":"?(business)?.a.aa.subString(from:&(from)&,to:&(to)&)",
"variableCriterias":{},
"constants":{},
"references":{}
},
"operand":{"type":"operation",
"outputCriteria":"test.string;1.0.0",
"operation":"subString",
"dataTypeId":"test.string;1.0.0",
"parms":{"from":{"type":"constant",
"outputCriteria":"test.integer;1.0.0",
"name":"from",
"data":{"dataTypeId":"test.integer;1.0.0",
"valueFormat":"JSON",
"value":3
}
},
"to":{"type":"constant",
"outputCriteria":"test.integer;1.0.0",
"name":"to",
"data":{"dataTypeId":"test.integer;1.0.0",
"valueFormat":"JSON",
"value":7
}
},
"base":{"type":"variable",
"outputCriteria":"#||test.string;1.0.0||#",
"variableName":"business.a.aa"
}
},
"matchersParms":{"base":{"test.string;1.0.0":{"dataTypeId":"test.string;1.0.0",
"relationship":{"source":"test.string;1.0.0",
"target":"test.string;1.0.0",
"path":[],
"targetType":"self"
},
"subMatchers":{}
}
},
"from":{"test.integer;1.0.0":{"dataTypeId":"test.integer;1.0.0",
"relationship":{"source":"test.integer;1.0.0",
"target":"test.integer;1.0.0",
"path":[],
"targetType":"self"
},
"subMatchers":{}
}
},
"to":{"test.integer;1.0.0":{"dataTypeId":"test.integer;1.0.0",
"relationship":{"source":"test.integer;1.0.0",
"target":"test.integer;1.0.0",
"path":[],
"targetType":"self"
},
"subMatchers":{}
}
}
}
},
"variableInfos":{"business.a.aa":{"status":"close",
"criteria":"test.string;1.0.0"
},
"business.a.bb":{"status":"close",
"criteria":"test.array;1.0.0%||element:@||!(test.expression)!.outputCriteria(&amp;(expression)&amp;;;&amp;(parms)&amp;)||@||%"
},
"business.a.cc.element":{"status":"close",
"criteria":"test.string;1.0.0"
}
},
"errorMsgs":[],
"variablesMatchers":{"business.a.aa":{"test.string;1.0.0":{"dataTypeId":"test.string;1.0.0",
"relationship":{"source":"test.string;1.0.0",
"target":"test.string;1.0.0",
"path":[],
"targetType":"self"
},
"subMatchers":{}
}
}
},
"references":{}
}
},
"scriptFunction":function(expressionsData, constantsData, variablesData){
	return expressionsData["0_0"].value=='s isfff'?'red':'blue';
} 

}
},
"scriptFunction":function(scriptExpressionData){
	return "color:"+scriptExpressionData["0"];
} 
,
"attribute":"style"
}],
"scriptExpressionTagAttributes":[],
"elementEvents":[{"uiId":"21",
"event":"click",
"function":"testLinkEvent"
}],
"tagEvents":[],
"uiTags":{"23":{"id":"23",
"type":"tag",
"context":{"publicContext":{},
"internalContext":{}
},
"scriptExpressionsInContent":[],
"scriptExpressionInAttributes":[],
"scriptExpressionTagAttributes":[],
"elementEvents":[],
"tagEvents":[],
"uiTags":{},
"attributes":{"data":"business.a.aa",
"nosliwid":"23"
},
"html":"",
"constants":{"from":{"literate":{"dataTypeId":"test.integer","value":3},
"processed":true,
"value":{"dataTypeId":"test.integer","value":3}
},
"to":{"literate":{"dataTypeId":"test.integer","value":7},
"processed":true,
"value":{"dataTypeId":"test.integer","value":7}
},
"aaaa":{"literate":"<%=5+6+7%>",
"processed":true,
"value":18.0
},
"bbbb":{"literate":"<%=(5+6+7)>5%>",
"processed":true,
"value":true
},
"cccc":{"literate":{"a":12345,"b":true,"c":"good","d":18},
"processed":true,
"value":{"a":12345,"b":true,"c":"good","d":18}
},
"dddd":{"literate":"<%=&(cccc)&.a+6%>",
"processed":true,
"value":12351.0
},
"ffff":{"literate":"<%=#|&(#test##string___Thisismyworldabcdef)&|#%>",
"processed":true,
"value":{"dataTypeId":"test.string;1.0.0","valueFormat":"LITERATE","value":"Thisismyworldabcdef"}
},
"eeee":{"literate":"<%=#|&(ffff)&.subString(from:&(#test##integer___3)&,to:&(#test##integer___7)&)|#%>",
"processed":true,
"value":{"dataTypeId":"test.string;1.0.0","value":"sismfff"}
},
"base":{"literate":{"dataTypeId":"test.string","value":"This is my world!"},
"processed":true,
"value":{"dataTypeId":"test.string","value":"This is my world!"}
}
},
"tagName":"textinput"
},
"25":{"id":"25",
"type":"tag",
"context":{"publicContext":{},
"internalContext":{}
},
"scriptExpressionsInContent":[],
"scriptExpressionInAttributes":[],
"scriptExpressionTagAttributes":[],
"elementEvents":[],
"tagEvents":[],
"uiTags":{},
"attributes":{"data":"business.a.aa",
"nosliwid":"25"
},
"html":"",
"constants":{"from":{"literate":{"dataTypeId":"test.integer","value":3},
"processed":true,
"value":{"dataTypeId":"test.integer","value":3}
},
"to":{"literate":{"dataTypeId":"test.integer","value":7},
"processed":true,
"value":{"dataTypeId":"test.integer","value":7}
},
"aaaa":{"literate":"<%=5+6+7%>",
"processed":true,
"value":18.0
},
"bbbb":{"literate":"<%=(5+6+7)>5%>",
"processed":true,
"value":true
},
"cccc":{"literate":{"a":12345,"b":true,"c":"good","d":18},
"processed":true,
"value":{"a":12345,"b":true,"c":"good","d":18}
},
"dddd":{"literate":"<%=&(cccc)&.a+6%>",
"processed":true,
"value":12351.0
},
"ffff":{"literate":"<%=#|&(#test##string___Thisismyworldabcdef)&|#%>",
"processed":true,
"value":{"dataTypeId":"test.string;1.0.0","valueFormat":"LITERATE","value":"Thisismyworldabcdef"}
},
"eeee":{"literate":"<%=#|&(ffff)&.subString(from:&(#test##integer___3)&,to:&(#test##integer___7)&)|#%>",
"processed":true,
"value":{"dataTypeId":"test.string;1.0.0","value":"sismfff"}
},
"base":{"literate":{"dataTypeId":"test.string","value":"This is my world!"},
"processed":true,
"value":{"dataTypeId":"test.string","value":"This is my world!"}
}
},
"tagName":"textinput"
}
},
"attributes":{},
"html":"&lt;span&gt; &lt;/span&gt;&lt;span nosliwid=&quot;14&quot;&gt;&lt;/span&gt;&lt;span&gt; tttttttttt222 &lt;/span&gt;&lt;br nosliwid=&quot;16&quot;&gt;&lt;span&gt; lalalala &lt;/span&gt;&lt;span nosliwid=&quot;15&quot;&gt;&lt;/span&gt;&lt;span&gt; tttttttttt222 &lt;/span&gt;&lt;br nosliwid=&quot;17&quot;&gt;&lt;span&gt;Test Attribute Expression:&lt;/span&gt;&lt;br nosliwid=&quot;18&quot;&gt;&lt;span&gt; &lt;/span&gt;&lt;span style=&quot;&quot; nosliwid=&quot;19&quot;&gt;&lt;span&gt;Phone Number : &lt;/span&gt;&lt;/span&gt;&lt;span&gt; &lt;/span&gt;&lt;br nosliwid=&quot;20&quot;&gt;&lt;a href=&quot;&quot; nosliwid=&quot;21&quot;&gt;&lt;span&gt;New Department&lt;/span&gt;&lt;/a&gt; &lt;br nosliwid=&quot;22&quot;&gt; &lt;nosliw style=&quot;display:none;&quot; nosliwid=&quot;23-tag-start&quot;&gt;&lt;/nosliw&gt;&lt;nosliw style=&quot;display:none;&quot; nosliwid=&quot;23-tag-end&quot;&gt;&lt;/nosliw&gt; &lt;br nosliwid=&quot;24&quot;&gt; &lt;nosliw style=&quot;display:none;&quot; nosliwid=&quot;25-tag-start&quot;&gt;&lt;/nosliw&gt;&lt;nosliw style=&quot;display:none;&quot; nosliwid=&quot;25-tag-end&quot;&gt;&lt;/nosliw&gt;    &lt;!-- This part can be used to define context (variable)				it describle data type criteria for each context element and its default value		--&gt;  &lt;!-- This part can be used to define expressions		--&gt;",
"constants":{"from":{"literate":{"dataTypeId":"test.integer","value":3},
"processed":true,
"value":{"dataTypeId":"test.integer","value":3}
},
"to":{"literate":{"dataTypeId":"test.integer","value":7},
"processed":true,
"value":{"dataTypeId":"test.integer","value":7}
},
"aaaa":{"literate":"<%=5+6+7%>",
"processed":true,
"value":18.0
},
"bbbb":{"literate":"<%=(5+6+7)>5%>",
"processed":true,
"value":true
},
"cccc":{"literate":{"a":12345,"b":true,"c":"good","d":18},
"processed":true,
"value":{"a":12345,"b":true,"c":"good","d":18}
},
"dddd":{"literate":"<%=&(cccc)&.a+6%>",
"processed":true,
"value":12351.0
},
"ffff":{"literate":"<%=#|&(#test##string___Thisismyworldabcdef)&|#%>",
"processed":true,
"value":{"dataTypeId":"test.string;1.0.0","valueFormat":"LITERATE","value":"Thisismyworldabcdef"}
},
"eeee":{"literate":"<%=#|&(ffff)&.subString(from:&(#test##integer___3)&,to:&(#test##integer___7)&)|#%>",
"processed":true,
"value":{"dataTypeId":"test.string;1.0.0","value":"sismfff"}
},
"base":{"literate":{"dataTypeId":"test.string","value":"This is my world!"},
"processed":true,
"value":{"dataTypeId":"test.string","value":"This is my world!"}
}
},
"script":{
		newElementInLoop : function(data, info){

			event.preventDefault();
			
			var node_createDataOperationService = nosliw.getNodeData("uidata.dataoperation.createDataOperationService");
			var node_createDataOperationRequest = nosliw.getNodeData("uidata.dataoperation.createDataOperationRequest");
			var node_CONSTANT = nosliw.getNodeData("constant.CONSTANT");
			
			var ele = {
				dataTypeId: "test.string;1.0.0",
				value: "This is my world 33333!"
			};

			var dataOperationService = node_createDataOperationService(undefined, "WRAPPER_OPERATION_ADDELEMENT", "business.a.cc", ele, 4);
			this.getContext().requestDataOperation(dataOperationService);
			
		},
	
	
		testLinkEvent : function(data, info){
			var node_createContextVariable = nosliw.getNodeData("uidata.context.createContextVariable");
			
			event.preventDefault();
			alert("aaaaa");
			
			var context = this.getContext();
			var variable = context.createVariable(new node_createContextVariable("business.a.aa"));
			variable.registerDataChangeEventListener(undefined, function(eventName){
				alert(eventName);
			});
			
			variable.requestDataOperation({
				command:"WRAPPER_OPERATION_SET",
				parms :
				{
					value: {
						dataTypeId: "test.string",
						value: "This is "
					}
					
				}
			});
			
		},
	},
"uiTagLibs":["textinput"]
}, {"loadPattern":"file"
});
