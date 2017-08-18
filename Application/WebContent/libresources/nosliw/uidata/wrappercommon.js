//get/create package
var packageObj = library.getChildPackage("wrapper");    

(function(packageObj){
//get used node
var node_CONSTANT;
var node_createEventObject;
var node_makeObjectWithLifecycle;
var node_getLifecycleInterface;
var node_makeObjectWithType;
var node_makeObjectWithId;
	
//*******************************************   Start Node Definition  ************************************** 	
/**
 * 
 */
var node_createWraperCommon = function(data, path, request){
	//sync task name for remote call 
	var loc_moduleName = "wrapper";

	/*
	 * mark data as invalid so that it would be recalculated
	 * also trigue event to inform that data need to be updated
	 */
	var loc_invalidateData = function(requestInfo){
		loc_out.pri_validData = false;
		loc_out.pri_object = undefined;
	};
	
	var loc_resourceLifecycleObj = {};
	loc_resourceLifecycleObj[node_CONSTANT.LIFECYCLE_RESOURCE_EVENT_DESTROY] = function(requestInfo){
		//for delete event, it means itself and all children should be destroy
		loc_invalidateData();
		//forward the event
		loc_out.pri_trigueDataOperationEvent(NOSLIWCONSTANT.WRAPPER_EVENT_DELETE, "", {}, requestInfo);
		//clear up event source
		loc_out.pri_dataOperationEventObject.clearup();
		
		//unregister listeners
		nosliwEventUtility.unregisterAllListeners(loc_out.pri_allListeners);
		
		loc_out.pri_parent = undefined;
		loc_out.pri_rootData = undefined;
		loc_out.pri_path = undefined;
	};
	
	loc_resourceLifecycleObj[node_CONSTANT.LIFECYCLE_RESOURCE_EVENT_INIT] = function(obj, path, request){
		
		//if true, this wrapper is based on root data, otherwise, this wrapper is based on parent wrapper, 
		loc_out.pri_dataBased = true;
		//root data used when data based
		loc_out.pri_rootData = undefined;
		//parent wrapper used when wraper based
		loc_out.pri_parent = undefined;
		//the path from basis (data or wraper) to current data
		loc_out.pri_path = nosliwCommonUtility.emptyStringIfUndefined(path);
		
		//all listeners that listen to others 
		loc_out.pri_allListeners = [];
		
		//event and listener for data operation event
		loc_out.pri_dataOperationEventObject = node_createEventObject();

		//calculated: the data this wrapper represented
		loc_out.pri_validData = false;
		loc_out.pri_object = undefined;
		
		//whether data based or wrapper based
		if(nosliwTypedObjectUtility.getObjectType(obj)==NOSLIWCONSTANT.TYPEDOBJECT_TYPE_WRAPPER){
			//wrapper based
			loc_out.pri_parent = obj;
			loc_out.pri_dataBased = false;
		}
		else{
			//data based
			loc_out.pri_rootData = nosliwDataUtility.createDataByObject(obj);
			loc_out.pri_dataBased = true;
		}
		
		if(loc_out.pri_dataBased==false){
			//if parent based, then listen to parent's event
			var listener = loc_out.pri_parent.registerDataOperationEvent(function(event, path, eventData, requestInfo){
				
				if(event==NOSLIWCONSTANT.WRAPPER_EVENT_FORWARD){
					event = eventData.event;
					path = eventData.path;
					eventData = eventData.eventData;
				}
				
				if(event==NOSLIWCONSTANT.WRAPPER_EVENT_CHANGE){
					//for change event from parent, just make data invalid & forward the event, 
					loc_invalidateData(requestInfo);
					loc_out.pri_trigueDataOperationEvent(NOSLIWCONSTANT.WRAPPER_EVENT_CHANGE, "", {}, requestInfo);
				}
				else{
					var k = -1;
					if(loc_out.pri_path==path){
						//event happens on this wrapper, trigue the same
						//inform the change of wrapper
						if(event==NOSLIWCONSTANT.WRAPPER_EVENT_DELETE){
							loc_out.destroy();
						}
						else{
							loc_invalidateData(requestInfo);
							loc_out.pri_trigueDataOperationEvent(event, "", eventData, requestInfo);
						}
					}
					else if(loc_out.pri_path.startsWith(path+".") || nosliwCommonUtility.isStringEmpty(path)){
						//something happens in the middle between parent and this
						if(event==NOSLIWCONSTANT.WRAPPER_EVENT_SET){
							loc_invalidateData(requestInfo);
							loc_out.pri_trigueDataOperationEvent(NOSLIWCONSTANT.WRAPPER_EVENT_CHANGE, "", {}, requestInfo);
						}
						else if(event==NOSLIWCONSTANT.WRAPPER_EVENT_DELETE){
							loc_out.destroy();
						}
					}
					else if(path.startsWith(loc_out.pri_path+".")){
						//something happens beyond this, just forward the event with rest path, only set event
						loc_out.pri_triggerForwardEvent(event, path.substring(loc_out.pri_path.length+1), eventData, requestInfo);
					}
					else if(nosliwCommonUtility.isStringEmpty(loc_out.pri_path)){
						//something happens beyond this, just forward the event with rest path, only set event
						loc_out.pri_triggerForwardEvent(event, path, eventData, requestInfo);
					}
					else{
						//not on right path, do nothing
					}
				}
			}, this);
			loc_out.pri_allListeners.push(listener);
		}
	};

	
	var loc_out = {
			pri_getPath : function(){  return this.pri_path;  },
			pri_getParent : function(){  return this.pri_parent; },
			pri_getRootData : function(){  return this.pri_rootData; },
			pri_isDataBased : function(){  return this.pri_dataBased; },
			pri_setObject : function(object){ this.pri_object = object;},
			pri_triggerForwardEvent : function(event, path, eventData, requestInfo){
				var eData = {
						event : event, 
						path : path, 
						eventData : eventData,
				};
				this.pri_trigueDataOperationEvent(NOSLIWCONSTANT.WRAPPER_EVENT_FORWARD, "", eData, requestInfo);
			},
			
			pri_trigueDataOperationEvent : function(event, path, operationValue, requestInfo){this.pri_dataOperationEventObject.triggerEvent(event, path, operationValue, requestInfo);},

			ovr_getResourceLifecycleObject : function(){	return loc_resourceLifecycleObj;	},
			
			ovr_calObject : function(){},
			
			isDataBased : function(){  return this.pri_dataBased; },
			
			getPath : function(){ return this.pri_path;  },
			
			getData : function(){
				if(this.pri_isDataBased()==true){
					return this.pri_rootData;
				}
				else{
					var object = this.getObject();
					return nosliwDataUtility.createDataByObject(object);
				}
			},
			
			getObject : function(){
				if(this.pri_validData==false){
					this.ovr_calObject();
				}
				return this.pri_object;
			},
			
			getDataType : function(){ return this.getData().dataTypeInfo; },

			getFullPath : function(){
				if(loc_out.pri_dataBased==true)   return loc_out.pri_path;
				return nosliwCommonUtility.cascadePath(loc_out.pri_parent.getFullPath(), loc_out.pri_path);
			},
			
			getRootWrapper : function(){
				if(loc_out.pri_dataBased==true)   return this;
				return loc_out.pri_parent.getRootWrapper();
			},
			
			getRootData : function(){
				return this.getRootWrapper().getData();
			},

			/*
			 * handler : function (event, path, operationValue, requestInfo)
			 */
			registerDataOperationEvent : function(listenerEventObj, handler, thisContext){
				return this.pri_dataOperationEventObject.registerListener(undefined, listenerEventObj, handler, thisContext);
			},

			createChildWrapper : function(path, request){		return nosliwCreateWraper(this, path, request);		},

			getWrapperType : function(){},
			getChildData : function(path){},
			requestDataOperation : function(service, request){},
			handleEachElement : function(handler, thatContext){	},
	};
	
	//append resource life cycle method to out obj
	loc_out = node_makeObjectWithLifecycle(loc_out, loc_resourceLifecycleObj, loc_out);
	
	loc_out = node_makeObjectWithType.makeTypedObject(loc_out, NOSLIWCONSTANT.TYPEDOBJECT_TYPE_WRAPPER);
	
	loc_out = node_makeObjectWithId(loc_out, nosliw.generateId());
	
	node_getLifecycleInterface(loc_out).init(data, path, request);
	
	return loc_out;
};

//*******************************************   End Node Definition  ************************************** 	

//populate dependency node data
nosliw.registerSetNodeDataEvent("constant.CONSTANT", function(){node_CONSTANT = this.getData();});
nosliw.registerSetNodeDataEvent("common.event.createEventObject", function(){node_createEventObject = this.getData();});
nosliw.registerSetNodeDataEvent("common.lifecycle.makeObjectWithLifecycle", function(){node_makeObjectWithLifecycle = this.getData();});
nosliw.registerSetNodeDataEvent("common.lifecycle.getLifecycleInterface", function(){node_getLifecycleInterface = this.getData();});
nosliw.registerSetNodeDataEvent("common.objectwithtype.makeObjectWithType", function(){node_makeObjectWithType = this.getData();});
nosliw.registerSetNodeDataEvent("common.objectwithid.makeObjectWithId", function(){node_makeObjectWithId = this.getData();});


//Register Node by Name
packageObj.createChildNode("createWraperCommon", node_createWraperCommon); 

})(packageObj);
