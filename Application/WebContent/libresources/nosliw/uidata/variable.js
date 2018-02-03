//get/create package
var packageObj = library.getChildPackage("variable");    

(function(packageObj){
//get used node
var node_ServiceInfo;
var node_CONSTANT;
var node_makeObjectWithLifecycle;
var node_getLifecycleInterface;
var node_makeObjectWithType;
var node_getObjectType;
var node_makeObjectWithId;
var node_eventUtility;
var node_requestUtility;
var node_wrapperFactory;
var node_basicUtility;
var node_createEventObject;
var node_RelativeEntityInfo;
var node_variableUtility;
var node_createServiceRequestInfoSequence;
var node_createServiceRequestInfoSet;
var node_OrderedContainerElementInfo;
var node_createVariableWrapper;
var node_createOrderedContainersInfo;
var node_createOrderVariableContainer;
var node_uiDataOperationServiceUtility;
var node_dataUtility;


//*******************************************   Start Node Definition  ************************************** 	
/**
 * two input model : 
 * 		1. parent variable + path from parent +  requestInfo
 *      2. data + undefined + requestInfo
 *      3. value + value type + requestInfo
 */
var node_createWrapperVariable = function(data1, data2, requestInfo){
	
	var loc_resourceLifecycleObj = {};
	loc_resourceLifecycleObj[node_CONSTANT.LIFECYCLE_RESOURCE_EVENT_INIT] = function(data1, data2, requestInfo){
		//every variable has a id, it is for debuging purpose
		loc_out.prv_id = nosliw.runtime.getIdService().generateId();

		//adapter that will apply to value of wrapper
		loc_out.prv_valueAdapter = undefined;

		loc_out.prv_pathAdapter = undefined;
		
		//adapter that affect the event from wrapper and listener of this variable
		loc_out.prv_eventAdapter = undefined;

		//adapter that affect the data operation on wrapper
		loc_out.prv_dataOperationRequestAdapter = undefined;
		
		//event source for event that communicate with child wrapper variables 
		loc_out.prv_lifecycleEventObject = node_createEventObject();
		//event source for event that communicate operation information with outsiders
		loc_out.prv_dataOperationEventObject = node_createEventObject();

		//wrapper object
		loc_out.prv_wrapper = undefined;

		loc_out.prv_isBase = true;
		
		//relative to parent : parent + path
		loc_out.prv_relativeVariableInfo;
		
		//child variables by path
		loc_out.prv_childrenVariable = {};
		
		//store information for children order info
		loc_out.prv_orderChildrenInfo;
		
		//record how many usage of this variable.
		//when usage go to 0, that means it should be clean up
		loc_out.usage = 0;
		
		var data1Type = node_getObjectType(data1);
		if(data1Type==node_CONSTANT.TYPEDOBJECT_TYPE_VARIABLE){
			//for variable having parent variable
			loc_out.prv_isBase = false;
			loc_out.prv_relativeVariableInfo = new node_RelativeEntityInfo(data1, data2);
			
			//build wrapper relationship with parent
			loc_updateWrapperByParent();
		}
		else{
			//for object/data
			loc_out.prv_isBase = true;
			loc_setWrapper(node_wrapperFactory.createWrapper(data1, data2, requestInfo));
		}
	};
	
	loc_resourceLifecycleObj[node_CONSTANT.LIFECYCLE_RESOURCE_EVENT_DESTROY] = function(requestInfo){
		//destroy wrapper fist
		loc_destroyWrapper(requestInfo);
		//clean up event object
		loc_out.prv_dataOperationEventObject.clearup();
		loc_out.prv_lifecycleEventObject.clearup();
		
		//clear children variable
		for (var key in loc_out.prv_childrenVariable){
		    if (loc_out.prv_childrenVariable.hasOwnProperty(key)){
		    	loc_out.prv_childrenVariable[key].destroy(requestInfo);
		        delete loc_out.prv_childrenVariable[key];
		    }
		}		
		
		for (var key in loc_out){
		    if (loc_out.hasOwnProperty(key)){
		        delete lock_out[key];
		    }
		}		
	};
	
	
	/*
	 * destroy wrapper within this variable
	 * no event triggered
	 * destroy wrapper means wrapper's all resource get released
	 * it does not means variable is destroyed 
	 */
	var loc_destroyWrapper = function(requestInfo){
		//if no wrapper, no effect
		if(loc_out.prv_wrapper==undefined)   return;

		//unregister listener from wrapper
		loc_out.prv_wrapper.unregisterDataOperationListener(loc_out.prv_dataOperationEventObject);
		
		//destroy wrapper
		loc_out.prv_wrapper.destroy(requestInfo);

		loc_out.prv_wrapper = undefined;
	};
	
	var loc_updateWrapperByParent = function(requestInfo){
		var parentWrapper = loc_out.prv_relativeVariableInfo.parent.prv_getWrapper();
		if(parentWrapper!=undefined)   loc_setWrapper(node_wrapperFactory.createWrapper(parentWrapper, loc_out.prv_relativeVariableInfo.path), requestInfo);
		else loc_setWrapper(undefined, requestInfo);
	};
	

	var loc_setWrapper = function(wrapper, requestInfo){
		loc_out.prv_wrapper = wrapper;
		if(loc_out.prv_wrapper!=undefined){
			loc_out.prv_wrapper.setValueAdapter(loc_out.prv_valueAdapter);
			loc_out.prv_wrapper.setPathAdapter(loc_out.prv_pathAdapter);
			loc_registerWrapperDataOperationEvent();
		}
	};

	//listen to wrapper data operation event
	var loc_registerWrapperDataOperationEvent = function(){
		if(loc_out.prv_wrapper==undefined)  return;
		loc_out.prv_wrapper.registerDataOperationListener(loc_out.prv_dataOperationEventObject, function(event, eventData, requestInfo){
			var events = [{
				event : event,
				value : eventData
			}];
			if(loc_out.prv_eventAdapter!=undefined){
				//if have eventAdapter, then apply eventAdapter to wrapper event
				events = loc_out.prv_eventAdapter(event, eventData, requestInfo);
			}
			
			_.each(events, function(eventInfo, index){
				loc_out.prv_dataOperationEventObject.triggerEvent(eventInfo.event, eventInfo.value, requestInfo);
			});
		});
	};

	var loc_addChildVariable = function(childVar, path){
		loc_out.prv_childrenVariable[path] = childVar;
		childVar.registerLifecycleListener(loc_out.prv_lifecycleEventObject, function(event, eventData){
			if(event==node_CONSTANT.WRAPPER_EVENT_DELETE)	delete loc_out.prv_childrenVariable[path];
		});
	};
	
	var loc_getHandleEachElementOfOrderContainer = function(elementHandleRequestFactory, handlers, request){
		//container looped
		//handle each element
		var i = 0;
		var handleElementsRequest = node_createServiceRequestInfoSet(new node_ServiceInfo("HandleElements", {"elements":loc_out.prv_orderChildrenInfo.getElements()}));
		_.each(loc_out.prv_orderChildrenInfo.getElements(), function(ele, index){
			//add child request from factory
			//eleId as path
			handleElementsRequest.addRequest(i+"", elementHandleRequestFactory.call(loc_out, node_createVariableWrapper(loc_out), node_createVariableWrapper(loc_out.prv_childrenVariable[ele.path]), node_createVariableWrapper(ele.indexVariable)));
			i++;
		});
		return handleElementsRequest;
	};
	
	var loc_updateChildrenData = function(requestInfo){
		_.each(loc_out.prv_childrenVariable, function(childVariable, path){
			childVariable.prv_updateRelativeData(requestInfo);
		});
	};
	
	
	var loc_out = {
			prv_getWrapper : function(){return this.prv_wrapper;},

			//has to be base variable
			prv_setBaseData : function(parm1, parm2, requestInfo){	
				loc_destroyWrapper(requestInfo);
				var wrapper = node_wrapperFactory.createWrapper(parm1, parm2, requestInfo);
				loc_setWrapper(wrapper, requestInfo);
				loc_updateChildrenData(requestInfo);
			},

			//update data when parent's wrapper changed
			prv_updateRelativeData : function(requestInfo){
				//create new wrapper based on wrapper in parent and path
				loc_destroyWrapper(requestInfo);
				loc_updateWrapperByParent(requestInfo);
				loc_updateChildrenData(requestInfo);
			},
			
			setValueAdapter : function(valueAdapter){  
				this.prv_valueAdapter = valueAdapter;
				if(this.prv_wrapper!=undefined)		this.prv_wrapper.setValueAdapter(valueAdapter);
			},
			
			setPathAdapter : function(pathAdapter){  
				this.prv_pathAdapter = pathAdapter;
				if(this.prv_wrapper!=undefined)		this.prv_wrapper.setPathAdapter(pathAdapter);
			},

			setEventAdapter : function(eventAdapter){	this.prv_eventAdapter = eventAdapter;	},
			
			
			use : function(){	
				loc_out.prv_usage++;
				return this;
			},
			
			release : function(requestInfo){
				loc_out.prv_usage--;
				if(loc_out.prv_usage<=0){
					loc_trigueLifecycleEvent(node_CONSTANT.WRAPPER_EVENT_CLEARUP, {}, requestInfo);
					node_getLifecycleInterface(loc_out).destroy(requestInfo);
				}
			},
			
			destroy : function(requestInfo){
				loc_out.prv_usage = 0;
				this.release(requestInfo);
			},

			//create child variable, if exist, then reuse it
			createChildVariable : function(path, requestInfo){
				var out = loc_out.prv_childrenVariable[path];
				if(out==undefined){
					out = node_createWrapperVariable(loc_out, path, requestInfo);
					loc_addChildVariable(out, path);
				}
				return out;
			},

			/*
			 * register handler for operation event
			 */
			registerDataChangeEventListener : function(listenerEventObj, handler, thisContext){return this.prv_dataOperationEventObject.registerListener(undefined, listenerEventObj, handler, thisContext);		},
			getDataChangeEventObject : function(){   return this.prv_dataOperationEventObject;   },
			
			/*
			 * register handler for event of communication between parent and child variables
			 */
			registerLifecycleEventListener : function(listenerEventObj, handler, thisContext){return this.prv_lifecycleEventObject.registerListener(undefined, listenerEventObj, handler, thisContext);	},
			getLifecycleEventObject : function(){   return this.prv_lifecycleEventObject;   },
			
			getDataOperationRequest : function(operationService, handlers, requester_parent){
				var that = this;
				var out;
				
				if(operationService.command==node_CONSTANT.WRAPPER_OPERATION_SET && loc_out.prv_isBase==true){
					//for set root data
					return node_createServiceRequestInfoSimple(operationService, function(requestInfo){	
						loc_out.prv_setBaseData(operationService.value, operationService.dataType, requestInfo);
					}, handlers, requester_parent);
				}
				
				if(this.prv_wrapper!=undefined){
					if(loc_out.prv_dataOperationRequestAdapter!=undefined){
						out = loc_out.prv_dataOperationRequestAdapter.dataOperationRequest(operationService, handlers, requester_parent);
					}
					else{
						out = this.prv_wrapper.getDataOperationRequest(operationService, handlers, requester_parent);
					}
				}
				
				out.setRequestProcessors({
					success : function(requestInfo, data){
						nosliw.logging.info("************************  variable operation   ************************");
						nosliw.logging.info("ID: " + that.prv_id);
						nosliw.logging.info("Wrapper: " + (that.prv_wrapper==undefined?"":that.prv_wrapper.prv_id));
						nosliw.logging.info("Parent: " , ((that.prv_relativeVariableInfo==undefined)?"":that.prv_relativeVariableInfo.parent.prv_id));
						nosliw.logging.info("ParentPath: " , ((that.prv_relativeVariableInfo==undefined)?"":that.prv_relativeVariableInfo.path)); 
						nosliw.logging.info("Request: " , JSON.stringify(operationService));
						nosliw.logging.info("Result: " , JSON.stringify(data));
						nosliw.logging.info("***************************************************************");
						return data;
					}
				});
				
				return out;
			},
			
			getHandleEachElementRequest : function(elementHandleRequestFactory, handlers, request){
				if(loc_out.prv_wrapper==null){
					return node_createServiceRequestInfoSimple({}, function(){}, handlers, request);
				}
				
				if(loc_out.prv_orderChildrenInfo!=undefined){
					return loc_getHandleEachElementOfOrderContainer(elementHandleRequestFactory, handlers, request);
				}
				else{
					//not loop yet, get value first, then loop it
					loc_out.getDataOperationRequest(node_uiDataOperationServiceUtility.createGetOperationService(), {
						success : function(request, data){
							loc_out.prv_wrapper.getDataTypeHelper.getGetElementsRequest(data.value, {
								success : function(request, valueElements){
									loc_out.prv_orderChildrenInfo = node_createContainerOrderInfo();
									//create child variables
									_.each(valueElements, function(valueEle, index){
										var path = loc_out.prv_orderChildrenInfo.insert(index, valueEle.id);
										loc_out.prv_childrenVariable[path] = loc_out.createChildVariable(path);
										loc_out.prv_childrenVariable[path].registerDataOperationEvent(undefined, function(){
											if(event==DELETE){
												loc_out.prv_orderChildrenInfo.removeByPath(path);
											}
										});
									});
									
									//path adapter
									loc_out.setPathAdapter(loc_out.prv_orderChildrenInfo);
									
									//event adapter
									loc_out.prv_containerVariable.setEventAdapter(function(event, eventData, request){
										var events = [];
										events.push({
											event: event,
											value : eventData
										});
										
										if(event==node_CONSTANT.WRAPPER_EVENT_ADDELEMENT){
											var eleVar = loc_insertValue(eventData.value, eventData.index, eventData.id);
											events.push({
												event : node_CONSTANT.WRAPPER_EVENT_NEWELEMENT,
												value : new node_OrderedContainerElementInfo(node_createVariableWrapper(eleVar), undefined),
											});
										}
										return events;
									});
									
									//data operation request adapter
									loc_out.prv_containerVariable.setDataOperationAdapter(
											{
												dataOperationRequest : function(operationService, handlers, requester_parent){
													var wrapper = this.prv_wrapper;
													var os = operationService.clone();
													var command = os.command;
													var operationData = os.parms;
													if(command==node_CONSTANT.WRAPPER_OPERATION_DELETE && node_basicUtility.isStringEmpty(operationData.path)){
														var parentOrderContainerInfo = this.prv_relativeVariableInfo.parent.prv_getOrderedContainerInformation();
														if(parentOrderContainerInfo!=undefined){
															//change to delete element command on parent variable
															var delEleOpData = node_createDeleteElementOperationData();
															parentOrderContainerInfo.populateDeleteElementOperationDataByPath(delEleOpData, loc_out.prv_relativeVariableInfo.path);
															operationService = node_uiDataOperationServiceUtility.createDeleteElementOperationService(undefined, undefined, delEleOpData.index, delEleOpData.id);
															wrapper = this.prv_relativeVariableInfo.parent.prv_wrapper;
														}
													}
													out = wrapper.getDataOperationRequest(operationService, handlers, requester_parent);
												}
											}
									);
								}
							});
						}
					});
				}
			},
			
	};
	
	loc_out = node_makeObjectWithLifecycle(loc_out, loc_resourceLifecycleObj, loc_out);
	loc_out = node_makeObjectWithType(loc_out, node_CONSTANT.TYPEDOBJECT_TYPE_VARIABLE);
	loc_out = node_makeObjectWithId(loc_out, nosliw.generateId());
	
	node_getLifecycleInterface(loc_out).init(data1, data2, requestInfo);
	return loc_out;
};

//*******************************************   End Node Definition  ************************************** 	

//populate dependency node data
nosliw.registerSetNodeDataEvent("common.service.ServiceInfo", function(){node_ServiceInfo = this.getData();	});
nosliw.registerSetNodeDataEvent("constant.CONSTANT", function(){node_CONSTANT = this.getData();});
nosliw.registerSetNodeDataEvent("common.lifecycle.makeObjectWithLifecycle", function(){node_makeObjectWithLifecycle = this.getData();});
nosliw.registerSetNodeDataEvent("common.lifecycle.getLifecycleInterface", function(){node_getLifecycleInterface = this.getData();});
nosliw.registerSetNodeDataEvent("common.objectwithtype.makeObjectWithType", function(){node_makeObjectWithType = this.getData();});
nosliw.registerSetNodeDataEvent("common.objectwithtype.getObjectType", function(){node_getObjectType = this.getData();});
nosliw.registerSetNodeDataEvent("common.objectwithid.makeObjectWithId", function(){node_makeObjectWithId = this.getData();});
nosliw.registerSetNodeDataEvent("common.event.utility", function(){node_eventUtility = this.getData();});
nosliw.registerSetNodeDataEvent("common.request.utility", function(){node_requestUtility = this.getData();});
nosliw.registerSetNodeDataEvent("uidata.wrapper.wrapperFactory", function(){node_wrapperFactory = this.getData();});
nosliw.registerSetNodeDataEvent("common.utility.basicUtility", function(){node_basicUtility = this.getData();});
nosliw.registerSetNodeDataEvent("common.event.createEventObject", function(){node_createEventObject = this.getData();});
nosliw.registerSetNodeDataEvent("request.utility", function(){node_requestUtility = this.getData();});
nosliw.registerSetNodeDataEvent("uidata.entity.RelativeEntityInfo", function(){node_RelativeEntityInfo = this.getData();});
nosliw.registerSetNodeDataEvent("request.request.createServiceRequestInfoSequence", function(){	node_createServiceRequestInfoSequence = this.getData();	});
nosliw.registerSetNodeDataEvent("request.request.createServiceRequestInfoSet", function(){node_createServiceRequestInfoSet = this.getData();});
nosliw.registerSetNodeDataEvent("uidata.data.utility", function(){node_dataUtility = this.getData();});
nosliw.registerSetNodeDataEvent("uidata.variable.OrderedContainerElementInfo", function(){node_OrderedContainerElementInfo = this.getData();});
nosliw.registerSetNodeDataEvent("uidata.variable.createVariableWrapper", function(){node_createVariableWrapper = this.getData();});
nosliw.registerSetNodeDataEvent("uidata.variable.createOrderedContainersInfo", function(){node_createOrderedContainersInfo = this.getData();});
nosliw.registerSetNodeDataEvent("uidata.variable.createOrderVariableContainer", function(){node_createOrderVariableContainer = this.getData();});
nosliw.registerSetNodeDataEvent("uidata.variable.utility", function(){node_variableUtility = this.getData();});
nosliw.registerSetNodeDataEvent("uidata.uidataoperation.uiDataOperationServiceUtility", function(){node_uiDataOperationServiceUtility = this.getData();});


//Register Node by Name
packageObj.createChildNode("createWrapperVariable", node_createWrapperVariable); 

})(packageObj);
