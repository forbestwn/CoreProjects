//get/create package
var packageObj = library;    

(function(packageObj){
	//get used node
	var node_COMMONATRIBUTECONSTANT;
	var node_COMMONCONSTANT;
	var node_makeObjectWithLifecycle;
	var node_makeObjectWithType;
	var node_createContext;
	var node_createContextElementInfo;
	var node_dataUtility;
	var node_uiResourceUtility;
	var node_createEmbededScriptExpressionInContent;
	var node_createEmbededScriptExpressionInAttribute;
	var node_getLifecycleInterface;
	var node_basicUtility;
	var node_createContextVariable;
	var node_requestServiceProcessor;
	var node_createUIDataOperationRequest;
	var node_UIDataOperation;
	var node_uiDataOperationServiceUtility;
//*******************************************   Start Node Definition  ************************************** 	

	/**
	 * 
	 * base customer tag object, child tag just provide extendObj which implements its own method 
	 * it is also constructor object for customer tag object  
	 * 		id: 	id for this tag
	 * 		uiTagResource:	ui tag resource 
	 * 		parentUIResourceView: 	parent ui resource view
	 */
var node_createUITag = function(id, uiTagResource, parentUIResourceView, requestInfo){
	//object to implement tag logic, it is from tag library
	var loc_uiTagObj;
	
	//id of this tag object
	var loc_id = id;
	//parent resource view
	var loc_parentResourceView = parentUIResourceView;
	//all tag attributes
	var loc_attributes = {};

	var loc_tagName = uiTagResource[node_COMMONATRIBUTECONSTANT.UIRESOURCEDEFINITION_TAGNAME];
	
	var loc_context;
	
	//boundary element for this tag
	var loc_startEle = undefined;
	var loc_endEle = undefined;
	
	//runtime env for uiTagObj
	//include : basic info, utility method
	var loc_envObj = {
		getStartElement : function(){  return loc_startEle;  },
		getEneElement : function(){  return loc_endEle;  },
		getContext : function(){   return loc_context;  },
		getAttributeValue : function(name){  return loc_attributes[name];  },
		getAttributes : function(){   return loc_attributes;   },
		
		//utility methods
		createVariable : function(fullPath){  return loc_context.createVariable(node_createContextVariable(fullPath));  },
		processRequest : function(requestInfo){   node_requestServiceProcessor.processRequest(requestInfo, false);  },
		
		getDataOperationGet : function(target, path){  return new node_UIDataOperation(target, node_uiDataOperationServiceUtility.createGetOperationService(path)); },
		getDataOperationRequestGet : function(target, path, handler, request){
			return node_createUIDataOperationRequest(loc_context, this.getDataOperationGet(target, path), handler, request);
		},
		executeDataOperationRequestGet : function(target, path, handler, request){	this.processRequest(this.getDataOperationRequestGet(target, path, handler, request));	},
		
		
		executeBatchDataOperationRequest : function(operations){
			var requestInfo = node_createBatchUIDataOperationRequest(loc_context);
			_.each(operations, function(operation, i){
				requestInfo.addUIDataOperation(operation);						
			});
			this.processRequest(requestInfo);
		} 
		
	};
	
	var lifecycleCallback = {};
	lifecycleCallback[node_CONSTANT.LIFECYCLE_RESOURCE_EVENT_INIT]  = function(id, uiTagResource, parentUIResourceView, requestInfo){
		//get wraper element
		loc_startEle = loc_parentResourceView.get$EleByUIId(loc_id+node_COMMONCONSTANT.UIRESOURCE_CUSTOMTAG_WRAPER_START_POSTFIX);
		loc_endEle = loc_parentResourceView.get$EleByUIId(loc_id+node_COMMONCONSTANT.UIRESOURCE_CUSTOMTAG_WRAPER_END_POSTFIX);
		
		//init all attributes value
		_.each(uiTagResource[node_COMMONATRIBUTECONSTANT.UIRESOURCEDEFINITION_ATTRIBUTES], function(attrValue, attribute, list){
			loc_attributes[attribute] = attrValue;
		});
		
		//create context
		var parentContext;
		if(parentUIResourceView!=undefined)   parentContext = parentUIResourceView.getContext();
		loc_context = node_uiResourceUtility.buildContext(uiTagResource[node_COMMONATRIBUTECONSTANT.UIRESOURCEDEFINITION_CONTEXT], parentContext);
		
		//create uiTagObject
		var uiTagResourceId = node_uiResourceUtility.createTagResourceId(uiTagResource[node_COMMONATRIBUTECONSTANT.UIRESOURCEDEFINITION_TAGNAME]);
		var uiTagResourceObj = nosliw.runtime.getResourceService().getResource(uiTagResourceId);
		loc_uiTagObj = uiTagResourceObj[node_COMMONATRIBUTECONSTANT.RESOURCE_RESOURCEDATA][node_COMMONATRIBUTECONSTANT.UITAGDEFINITION_SCRIPT].call(loc_out, loc_context, loc_parentResourceView, uiTagResource, loc_attributes, loc_envObj);
		
		//overriden method before view is attatched to dom
		loc_uiTagObj.ovr_preInit(requestInfo);
		
		//overridden method to create init view
		var views = loc_uiTagObj.ovr_initViews(loc_startEle, loc_endEle, requestInfo);
		//attach view to resourve view
		if(views!=undefined)  loc_startEle.after(views);	

		//overridden method to do sth after view is attatched to dom
		loc_uiTagObj.ovr_postInit(requestInfo);
	};
	
	
	var loc_out = {
		
		getId : function(){  return loc_id;   },
		
		getTagName : function(){ return loc_tagName;   },
	
		getTagObject : function(){ return loc_uiTagObj;  }
			
	};
	
	//append resource and object life cycle method to out obj
	loc_out = node_makeObjectWithLifecycle(loc_out, lifecycleCallback);
	loc_out = node_makeObjectWithType(loc_out, node_CONSTANT.TYPEDOBJECT_TYPE_UITAG);

	node_getLifecycleInterface(loc_out).init(id, uiTagResource, parentUIResourceView, requestInfo);
	
	return loc_out;
	
};
	
//*******************************************   End Node Definition  ************************************** 	

//populate dependency node data
nosliw.registerSetNodeDataEvent("constant.COMMONCONSTANT", function(){node_COMMONCONSTANT = this.getData();});
nosliw.registerSetNodeDataEvent("constant.COMMONATRIBUTECONSTANT", function(){node_COMMONATRIBUTECONSTANT = this.getData();});
nosliw.registerSetNodeDataEvent("common.lifecycle.makeObjectWithLifecycle", function(){node_makeObjectWithLifecycle = this.getData();});
nosliw.registerSetNodeDataEvent("common.objectwithtype.makeObjectWithType", function(){node_makeObjectWithType = this.getData();});
nosliw.registerSetNodeDataEvent("uidata.context.createContext", function(){node_createContext = this.getData();});
nosliw.registerSetNodeDataEvent("uidata.context.createContextElementInfo", function(){node_createContextElementInfo = this.getData();});
nosliw.registerSetNodeDataEvent("uidata.data.utility", function(){node_dataUtility = this.getData();});
nosliw.registerSetNodeDataEvent("uiresource.utility", function(){node_uiResourceUtility = this.getData();});
nosliw.registerSetNodeDataEvent("uiresource.createEmbededScriptExpressionInContent", function(){node_createEmbededScriptExpressionInContent = this.getData();});
nosliw.registerSetNodeDataEvent("uiresource.createEmbededScriptExpressionInAttribute", function(){node_createEmbededScriptExpressionInAttribute = this.getData();});
nosliw.registerSetNodeDataEvent("common.lifecycle.getLifecycleInterface", function(){node_getLifecycleInterface = this.getData();});
nosliw.registerSetNodeDataEvent("common.utility.basicUtility", function(){node_basicUtility = this.getData();});
nosliw.registerSetNodeDataEvent("uidata.context.createContextVariable", function(){node_createContextVariable = this.getData();});
nosliw.registerSetNodeDataEvent("request.requestServiceProcessor", function(){node_requestServiceProcessor = this.getData();});
nosliw.registerSetNodeDataEvent("uidata.uidataoperation.createUIDataOperationRequest", function(){node_createUIDataOperationRequest = this.getData();});
nosliw.registerSetNodeDataEvent("uidata.uidataoperation.UIDataOperation", function(){node_UIDataOperation = this.getData();});
nosliw.registerSetNodeDataEvent("uidata.uidataoperation.uiDataOperationServiceUtility", function(){node_uiDataOperationServiceUtility = this.getData();});

//Register Node by Name
packageObj.createChildNode("createUITag", node_createUITag); 

})(packageObj);

