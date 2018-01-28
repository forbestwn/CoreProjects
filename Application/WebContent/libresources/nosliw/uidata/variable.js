//get/create package
var packageObj = library.getChildPackage("variable");    

(function(packageObj){
//get used node
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
var node_requestUtility;

//*******************************************   Start Node Definition  ************************************** 	
/**
 * two input model : 
 * 		1. parent variable + path from parent + requestInfo
 *      2. data + requestInfo
 *      3. value + value type + requestInfo
 */
var node_createWrapperVariable = function(data1, data2, data3){
	
	var loc_resourceLifecycleObj = {};
	loc_resourceLifecycleObj[node_CONSTANT.LIFECYCLE_RESOURCE_EVENT_INIT] = function(data1, data2, data3){
		loc_out.prv_isBase = true;
		
		//every variable has a id, it is for debuging purpose
		loc_out.pri_id = nosliw.runtime.getIdService().generateId();
		
		//relative variable
		loc_out.prv_relativeVariableInfo;
		
		//root variable
		//root variable store information for containers
		loc_out.prv_orderedContainersInfo = node_createOrderedContainersInfo(loc_out);
		
		//wrapper object
		loc_out.prv_wrapper = undefined;
		
		//adapter that will apply to value of wrapper
		loc_out.prv_valueAdapter = undefined;

		//adapter that affect the event from wrapper and listener of this variable
		loc_out.prv_eventAdapter = undefined;
		
		//event source for event that communicate with child wrapper variables 
		loc_out.prv_lifecycleEventObject = node_createEventObject();
		//event source for event that communicate operation information with outsiders
		loc_out.prv_dataOperationEventObject = node_createEventObject();

		var requestInfo = node_requestUtility.getRequestInfoFromFunctionArguments(arguments);
		var data1Type = node_getObjectType(data1);
		if(data1Type==node_CONSTANT.TYPEDOBJECT_TYPE_VARIABLE){
			//for variable having parent variable
			loc_out.prv_isBase = false;
			loc_out.prv_relativeVariableInfo = new node_RelativeEntityInfo(data1, data2);
			
			//build wrapper relationship with parent
			loc_updateWrapperByParent();
			
			//register parent listener
			loc_out.prv_relativeVariableInfo.parent.registerLifecycleEventListener(loc_out.prv_lifecycleEventObject, function(event, data, requestInfo){
				switch(event){
				case node_CONSTANT.WRAPPERVARIABLE_EVENT_SETDATA:
					//create new wrapper based on wrapper in parent and path
					loc_destroyWrapper(requestInfo);
					loc_updateWrapperByParent(requestInfo);
					break;
				case node_CONSTANT.WRAPPERVARIABLE_EVENT_CLEARUP:
					loc_out.destroy(requestInfo);
					break;
				};
			});
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
		//triggue variable destroy event
		loc_out.prv_lifecycleEventObject.triggerEvent(node_CONSTANT.WRAPPERVARIABLE_EVENT_CLEARUP, {}, requestInfo);
		//clean up event object
		loc_out.prv_dataOperationEventObject.clearup();
		loc_out.prv_lifecycleEventObject.clearup();
		
		loc_out.prv_relativeVariableInfo = undefined;
		loc_out.prv_containerInfo = undefined;
		loc_out.prv_valueAdapter = undefined;
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

		if(loc_out.prv_sortedContainersInfo!=undefined)  loc_out.prv_sortedContainersInfo.clearup();
		
		loc_out.prv_wrapper = undefined;
	};
	
	var loc_updateWrapperByParent = function(requestInfo){
		var parentWrapper = loc_out.prv_parent.getWrapper();
		if(parentWrapper!=undefined)   loc_setWrapper(node_wrapperFactory.createWrapper(parentWrapper, loc_out.prv_path), requestInfo);
		else loc_setWrapper(undefined, requestInfo);
	};
	

	var loc_setWrapper = function(wrapper, requestInfo){
		loc_out.prv_wrapper = wrapper;
		if(loc_out.prv_wrapper!=undefined){
			loc_out.prv_orderedContainersInfo.setDataTypeHelper(loc_out.prv_wrapper.getDataTypeHelper());
			loc_out.prv_wrapper.setValueAdapter(loc_out.prv_valueAdapter);
			loc_registerWrapperDataOperationEvent();
			loc_registerWrapperLifecycleEvent();
		}
	};

	//listen to wrapper event
	var loc_registerWrapperDataOperationEvent = function(){
		if(loc_out.prv_wrapper==undefined)  return;
		loc_out.prv_wrapper.registerDataOperationListener(loc_out.prv_dataOperationEventObject, function(event, eventData, requestInfo){
			//ignore forward event
			//we should not ignore forward event, as forward event also indicate that something get changed on child, in that case, the data also get changed
			//inform the operation
			loc_out.prv_dataOperationEventObject.triggerEvent(event, eventData, requestInfo);
		});
	};

	//listen to wrapper event
	var loc_registerWrapperLifecycleEvent = function(){
		if(loc_out.prv_wrapper==undefined)  return;
		loc_out.prv_wrapper.registerLifecycleListener(loc_out.prv_lifecycleEventObject, function(event, eventData, requestInfo){
			//ignore forward event
			//we should not ignore forward event, as forward event also indicate that something get changed on child, in that case, the data also get changed
			//inform the lifecycle
			loc_out.prv_dataOperationEventObject.triggerEvent(event, eventData, requestInfo);
		});
	};

	var loc_getHandleEachElementOfOrderContainer = function(containerInfo, elementHandleRequestFactory, handlers, request){
		//container looped
		//handle each element
		var i = 0;
		var handleElementsRequest = node_createServiceRequestInfoSet(new node_ServiceInfo("HandleElements", {"elements":containerInfo.getElements()}));
		_.each(containerInfo.getElements(), function(element, index){
			//add child request from factory
			//eleId as path
			handleElementsRequest.addRequest(i+"", elementHandleRequestFactory.call(loc_out, node_createVariableWrapper(element.element), node_createVariableWrapper(element.index)));
			i++;
		});
		return handleElementsRequest;
	};
	
	
	var loc_out = {

			prv_getOrderedContainerInformation : function(){
				var rootRelativeVarInfo = node_variableUtility.getVariableInfoFromRoot(this.prv_relativeVariableInfo);
				return rootRelativeVarInfo.prv_orderedContainersInfo.getContainerInfoByPath();
			},
			
			//handle each element on base value
			prv_getHandleEachElementOfRootRequest : function(path, elementHandleRequestFactory, handlers, request){
				var out = node_createServiceRequestInfoSequence(new node_ServiceInfo("HandleEachElementOnBaseValue"), handlers, request);
				var containerInfo = loc_out.prv_orderedContainersInfo.getContainerInfoByPath(path);
				
				if(containerInfo!=undefined){
					//already exists
					out.addRequest(loc_getHandleEachElementOfOrderContainer(containerInfo, elementHandleRequestFactory, handlers, request));
				}
				else{
					//not exists
					out.addRequest(loc_out.prv_orderedContainersInfo.getContainerElementsByPathRequest(path, {
						success : function(request, containerInfo){
							return loc_getHandleEachElementOfOrderContainer(containerInfo, elementHandleRequestFactory, handlers, request);
						}
					}));
				}
			},
			
			prv_getWrapper : function(){return this.prv_wrapper;},

			setValueAdapter : function(valueAdapter){  
				this.prv_valueAdapter = valueAdapter;
				if(this.prv_wrapper!=undefined){
					this.prv_wrapper.setValueAdapter(valueAdapter);
				}
			},

			//has to be base variable
			setData : function(parm1, parm2, requestInfo){	
				loc_destroyWrapper(requestInfo);
				var wrapper = node_wrapperFactory.createWrapper(data1, data2);
				loc_setWrapper(wrapper, requestInfo);	
			},
			
			isBase : function(){   return  this.prv_isBase;  },
			
			getRelativeInfo : function(){  return this.prv_relativeVariableInfo;  },
			
			destroy : function(requestInfo){node_getLifecycleInterface(loc_out).destroy(requestInfo);},
			
			createChildVariable : function(path, requestInfo){		return node_createWrapperVariable(this, path, requestInfo);			},

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

				if(this.prv_wrapper!=undefined){
					var wrapper = this.prv_populateDeleteElementOperationDataByPathwrapper;
					var os = operationService.clone();
					var command = os.command;
					var operationData = os.parms;
					if(command==node_CONSTANT.WRAPPER_OPERATION_DELETEELEMENT && node_basicUtility.isStringEmpty(operationData.path)){
						//process delete element command
						var orderContainerInfo = loc_out.prv_getOrderedContainerInformation();
						if(orderContainerInfo!=undefined)	orderContainerInfo.populateDeleteElementOperationData(operationData);
					}
					else if(command==node_CONSTANT.WRAPPER_OPERATION_DELETE && node_basicUtility.isStringEmpty(operationData.path)){
						var parentOrderContainerInfo = this.prv_parent.prv_getOrderedContainerInformation();
						if(parentOrderContainerInfo!=undefined){
							//change to delete element command on parent variable
							var delEleOpData = node_createDeleteElementOperationData();
							parentOrderContainerInfo.populateDeleteElementOperationDataByPath(delEleOpData, loc_out.prv_relativeVariableInfo.path);
							operationService = node_uiDataOperationServiceUtility.createDeleteElementOperationService(undefined, undefined, delEleOpData.index, delEleOpData.id);
							wrapper = this.prv_parent.prv_wrapper;
						}
					}
					return wrapper.getDataOperationRequest(operationService, handlers, requester_parent);
				}
			},
			
			getHandleEachElementRequest : function(elementHandleRequestFactory, handlers, request){
				var varInfoFromRoot = node_variableUtility.getVariableInfoFromRoot(loc_out.prv_relativeVariableInfo);
				return varInfoFromRoot.parent.prv_getHandleEachElementOfRootRequest(varInfoFromRoot.path, elementHandleRequestFactory, handlers, request);
			},
			
			
			use : function(){
				
			}
	};
	
	loc_out = node_makeObjectWithLifecycle(loc_out, loc_resourceLifecycleObj, loc_out);
	loc_out = node_makeObjectWithType(loc_out, node_CONSTANT.TYPEDOBJECT_TYPE_VARIABLE);
	loc_out = node_makeObjectWithId(loc_out, nosliw.generateId());
	
	node_getLifecycleInterface(loc_out).init(data1, data2, data3);

	//debug event
//	loc_out.registerDataChangeEventListener(undefined, function(eventName, data){
//		nosliw.logging.info("---------------------  Variable data change event  ----------------");
//		nosliw.logging.info("Path : " + loc_out.getPath());
//		nosliw.logging.info("FullPath : " + loc_out.getWrapper().getFullPath());
//		nosliw.logging.info("EventName : " + eventName);
//		nosliw.logging.info("Data : " + JSON.stringify(data));
//		nosliw.logging.info("---------------------    ----------------");
//		
//	}, loc_out);
	
	
	return loc_out;
};

//*******************************************   End Node Definition  ************************************** 	

//populate dependency node data
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

nosliw.registerSetNodeDataEvent("uidata.variable.OrderedContainerElementInfo", function(){node_OrderedContainerElementInfo = this.getData();});
nosliw.registerSetNodeDataEvent("uidata.variable.createVariableWrapper", function(){node_createVariableWrapper = this.getData();});
nosliw.registerSetNodeDataEvent("uidata.variable.createOrderedContainersInfo", function(){node_createOrderedContainersInfo = this.getData();});
nosliw.registerSetNodeDataEvent("uidata.variable.createOrderVariableContainer", function(){node_createOrderVariableContainer = this.getData();});


//Register Node by Name
packageObj.createChildNode("createWrapperVariable", node_createWrapperVariable); 

})(packageObj);
