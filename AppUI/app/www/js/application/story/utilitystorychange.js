//get/create package
var packageObj = library.getChildPackage();    

(function(packageObj){
	//get used node
	var node_CONSTANT;
	var node_COMMONCONSTANT;
	var node_COMMONATRIBUTECONSTANT;
	var node_storyUtility;
	var node_ElementId;

//*******************************************   Start Node Definition  ************************************** 	

/**
 * 
 */
var node_utility = function(){

	var loc_createChangeItemDelete = function(targetCategary, targetId){
		var out = {};
		out[node_COMMONATRIBUTECONSTANT.CHANGEITEM_CHANGETYPE] = node_COMMONCONSTANT.STORYDESIGN_CHANGETYPE_DELETE;
		out[node_COMMONATRIBUTECONSTANT.CHANGEITEM_TARGETELEMENTREF] = {};
		out[node_COMMONATRIBUTECONSTANT.CHANGEITEM_TARGETELEMENTREF][node_COMMONATRIBUTECONSTANT.IDELEMENT_CATEGARY] = targetCategary;
		out[node_COMMONATRIBUTECONSTANT.CHANGEITEM_TARGETELEMENTREF][node_COMMONATRIBUTECONSTANT.IDELEMENT_ID] = targetId;
		return out;
	};
	
	var loc_createChangeItemNew = function(element, aliasObj, story){           
		var out = {};
		out[node_COMMONATRIBUTECONSTANT.CHANGEITEM_CHANGETYPE] = node_COMMONCONSTANT.STORYDESIGN_CHANGETYPE_NEW;
		
		var eleId = element[node_COMMONATRIBUTECONSTANT.ENTITYINFO_ID];
		if(eleId==undefined){
			var eleCategary = element[node_COMMONATRIBUTECONSTANT.STORYELEMENT_CATEGARY];
			var eleType = element[node_COMMONATRIBUTECONSTANT.STORYELEMENT_TYPE];
			eleId = eleCategary+node_COMMONCONSTANT.SEPERATOR_LEVEL1+eleType+node_COMMONCONSTANT.SEPERATOR_LEVEL1+node_storyUtility.getNextIdForStory(story);
			element[node_COMMONATRIBUTECONSTANT.ENTITYINFO_ID] = eleId;
		}
		out[node_COMMONATRIBUTECONSTANT.CHANGEITEM_ELEMENT] = element;

		out[node_COMMONATRIBUTECONSTANT.CHANGEITEM_ALIAS] = aliasObj;
		return out;
	};
	
	var loc_createChangeItemPatch = function(targetRef, path, value){
		var out = {};
		out[node_COMMONATRIBUTECONSTANT.CHANGEITEM_CHANGETYPE] = node_COMMONCONSTANT.STORYDESIGN_CHANGETYPE_PATCH;
		out[node_COMMONATRIBUTECONSTANT.CHANGEITEM_PATH] = path;
		out[node_COMMONATRIBUTECONSTANT.CHANGEITEM_VALUE] = value;
		out[node_COMMONATRIBUTECONSTANT.CHANGEITEM_TARGETELEMENTREF] = targetRef;
		return out;
	};
	
	var loc_createChangeItemAlias = function(aliasName, elementId){
		var out = {};
		out[node_COMMONATRIBUTECONSTANT.CHANGEITEM_CHANGETYPE] = node_COMMONCONSTANT.STORYDESIGN_CHANGETYPE_ALIAS;
		out[node_COMMONATRIBUTECONSTANT.CHANGEITEM_ALIAS] = aliasName;
		out[node_COMMONATRIBUTECONSTANT.CHANGEITEM_ELEMENTID] = elementId;
		return out;
	};

	var loc_createChangeItemStoryInfo = function(infoName, infoValue){           
		var out = {};
		out[node_COMMONATRIBUTECONSTANT.CHANGEITEM_CHANGETYPE] = node_COMMONCONSTANT.STORYDESIGN_CHANGETYPE_STORYINFO;
		out[node_COMMONATRIBUTECONSTANT.CHANGEITEM_INFONAME] = infoName;
		out[node_COMMONATRIBUTECONSTANT.CHANGEITEM_INFOVALUE] = infoValue;
		return out;
	};
	
	var loc_createChangeItemDeleteForStoryElement = function(element){  return loc_createChangeItemDelete(element[node_COMMONATRIBUTECONSTANT.STORYELEMENT_CATEGARY], element[node_COMMONATRIBUTECONSTANT.ENTITYINFO_ID]);   };
	var loc_createChangeItemPatchForStoryElement = function(element, path, value){	return loc_createChangeItemPatch(new node_ElementId(element[node_COMMONATRIBUTECONSTANT.STORYELEMENT_CATEGARY], element[node_COMMONATRIBUTECONSTANT.ENTITYINFO_ID]), path, value);	};
	var loc_createChangeItemNewForStoryElement = function(element, aliasObj, story){  return loc_createChangeItemNew(element, aliasObj, story);   };

	var loc_applyChangeNew = function(story, changeItem, saveRevert){
		var aliasObj = changeItem[node_COMMONATRIBUTECONSTANT.CHANGEITEM_ALIAS];
		if(aliasObj!=undefined){
			var oldAliasEleId = node_storyUtility.getElementIdByReference(story, aliasObj);
		}
		var element = node_storyUtility.addStoryElement(story, changeItem[node_COMMONATRIBUTECONSTANT.CHANGEITEM_ELEMENT], aliasObj);
		if(loc_isRevertable(saveRevert, changeItem)!=false){
			var revertChanges = [];
			revertChanges.push(loc_createChangeItemDelete(element[node_COMMONATRIBUTECONSTANT.STORYELEMENT_CATEGARY], element[node_COMMONATRIBUTECONSTANT.ENTITYINFO_ID]));
			if(aliasObj!=undefined)  revertChanges.push(loc_createChangeItemAlias(aliasObj[node_COMMONATRIBUTECONSTANT.ALIASELEMENT_NAME], oldAliasEleId));
			changeItem[node_COMMONATRIBUTECONSTANT.CHANGEITEM_REVERTCHANGES] = revertChanges;
		}
	};
	
	var loc_applyChangePatch = function(story, changeItem, extend, allChanges, saveRevert){
		if(allChanges!=undefined)	allChanges.push(changeItem);
		var changes = loc_applyChangePatchSingle(story, changeItem, extend, saveRevert);
		_.each(changes, function(change, i){
			loc_applyChangePatch(story, change, extend, allChanges, saveRevert);
		});
	};
	
	var loc_applyChangeDelete = function(story, changeItem, saveRevert){
		var element = node_storyUtility.deleteStoryElementByRef(story, changeItem[node_COMMONATRIBUTECONSTANT.CHANGEITEM_TARGETELEMENTREF]);
		if(loc_isRevertable(saveRevert, changeItem))  changeItem[node_COMMONATRIBUTECONSTANT.CHANGEITEM_REVERTCHANGES] = [loc_createChangeItemNew(element, undefined, story)];
	};
	
	//output: a array of new change item
	var loc_applyChangePatchSingle = function(story, changeItem, extend, saveRevert){
		var path = changeItem[node_COMMONATRIBUTECONSTANT.CHANGEITEM_PATH];
		var value = changeItem[node_COMMONATRIBUTECONSTANT.CHANGEITEM_VALUE];
		var element = node_storyUtility.getStoryElementByRef(story, changeItem[node_COMMONATRIBUTECONSTANT.CHANGEITEM_TARGETELEMENTREF]);
		var eleCategary = element[node_COMMONATRIBUTECONSTANT.STORYELEMENT_CATEGARY];
		var eleType = element[node_COMMONATRIBUTECONSTANT.STORYELEMENT_TYPE];

		var out = [];
		if(extend==true){
			if(eleCategary==node_COMMONCONSTANT.STORYELEMENT_CATEGARY_GROUP){ 
				var children = element[node_COMMONATRIBUTECONSTANT.ELEMENTGROUP_ELEMENTS];
				if(eleType==node_COMMONCONSTANT.STORYGROUP_TYPE_SWITCH){
					if(path==node_COMMONATRIBUTECONSTANT.ELEMENTGROUPSWITCH_CHOICE){
						var currentChoice = element[node_COMMONATRIBUTECONSTANT.ELEMENTGROUPSWITCH_CHOICE];
						if(currentChoice!=value){
							_.each(children, function(child, i){
								var childName = child[node_COMMONATRIBUTECONSTANT.ENTITYINFO_NAME];
								var childEleId = node_storyUtility.getElementIdByReference(story, child[node_COMMONATRIBUTECONSTANT.INFOELEMENT_ELEMENTREF]);
								var childEle = node_storyUtility.getStoryElement(story, childEleId[node_COMMONATRIBUTECONSTANT.IDELEMENT_CATEGARY], childEleId[node_COMMONATRIBUTECONSTANT.IDELEMENT_ID])
								if(childName==value){
									if(childEle[node_COMMONATRIBUTECONSTANT.STORYELEMENT_ENABLE]==false){
										out.push(loc_createChangeItemPatchForStoryElement(childEle, node_COMMONATRIBUTECONSTANT.STORYELEMENT_ENABLE, true));
									}
								}
								else{
									if(childEle[node_COMMONATRIBUTECONSTANT.STORYELEMENT_ENABLE]==true){
										out.push(loc_createChangeItemPatchForStoryElement(childEle, node_COMMONATRIBUTECONSTANT.STORYELEMENT_ENABLE, false));
									}
								}
							});
						}
					}
				}
				else if(eleType==node_COMMONCONSTANT.STORYGROUP_TYPE_BATCH){
					if(path==node_COMMONATRIBUTECONSTANT.STORYELEMENT_ENABLE){
						_.each(children, function(child, i){
							var childEleId = node_storyUtility.getElementIdByReference(story, child[node_COMMONATRIBUTECONSTANT.INFOELEMENT_ELEMENTREF]);
							var childEle = node_storyUtility.getStoryElement(story, childEleId[node_COMMONATRIBUTECONSTANT.IDELEMENT_CATEGARY], childEleId[node_COMMONATRIBUTECONSTANT.IDELEMENT_ID])
							out.push(loc_createChangeItemPatchForStoryElement(childEle, node_COMMONATRIBUTECONSTANT.STORYELEMENT_ENABLE, value));
						});
					}
				}
			}
		}
		
		if(eleCategary==node_COMMONCONSTANT.STORYELEMENT_CATEGARY_GROUP && path==node_COMMONATRIBUTECONSTANT.ELEMENTGROUP_ELEMENT){
			//group element patch
			if(value[node_COMMONATRIBUTECONSTANT.INFOELEMENT_ELEMENTREF]!=undefined){
				//append element
				var id = value[node_COMMONATRIBUTECONSTANT.ENTITYINFO_ID];
				if(id==undefined){
					value[node_COMMONATRIBUTECONSTANT.ENTITYINFO_ID] = element[node_COMMONATRIBUTECONSTANT.ELEMENTGROUP_IDINDEX];
					element[node_COMMONATRIBUTECONSTANT.ELEMENTGROUP_IDINDEX]++;
				}
				element[node_COMMONATRIBUTECONSTANT.ELEMENTGROUP_ELEMENTS].push(value);
				if(loc_isRevertable(saveRevert, changeItem)){
					changeItem[node_COMMONATRIBUTECONSTANT.CHANGEITEM_REVERTCHANGES] = [loc_createChangeItemPatchForStoryElement(element, path, id)];
				}
			}
			else if(typeof value === 'string'){
				//delete element
				for(var i in element[node_COMMONATRIBUTECONSTANT.ELEMENTGROUP_ELEMENTS]){
					if(element[i][node_COMMONATRIBUTECONSTANT.ENTITYINFO_ID]==value){
						var oldValue = element[i];
						array.splice(i, 1);
						if(loc_isRevertable(saveRevert, changeItem)){
							changeItem[node_COMMONATRIBUTECONSTANT.CHANGEITEM_REVERTCHANGES] = [loc_createChangeItemPatchForStoryElement(element, path, oldValue)];
						}
						break;
					}
				}
			}
		}
		else{
			var oldValue = node_objectOperationUtility.operateObject(element, path, node_CONSTANT.WRAPPER_OPERATION_SET, value);
			if(loc_isRevertable(saveRevert, changeItem)){
				changeItem[node_COMMONATRIBUTECONSTANT.CHANGEITEM_REVERTCHANGES] = [loc_createChangeItemPatchForStoryElement(element, path, oldValue)];
			}
		}
		
		return out;
	};
	
	var loc_applyChangeStoryInfo = function(story, changeItem, saveRevert){
		var info = story[node_COMMONATRIBUTECONSTANT.ENTITYINFO_INFO];
		var infoName = changeItem[node_COMMONATRIBUTECONSTANT.CHANGEITEM_INFONAME];
		var infoValue = changeItem[node_COMMONATRIBUTECONSTANT.CHANGEITEM_INFOVALUE];
		var oldInfoValue = info[infoName];
		info[infoName] = infoValue;
		if(loc_isRevertable(saveRevert, changeItem))  changeItem[node_COMMONATRIBUTECONSTANT.CHANGEITEM_REVERTCHANGES] = [loc_createChangeItemStoryInfo(infoName, oldInfoValue)];
	};
	
	var loc_isRevertable = function(saveRevert, changeItem){
		if(saveRevert==false)  return false;
		return changeItem[node_COMMONATRIBUTECONSTANT.CHANGEITEM_REVERTABLE];
	};
	
	var loc_out = {
		
		applyChange : function(story, changeItem, saveRevert){
			var changeType = changeItem[node_COMMONATRIBUTECONSTANT.CHANGEITEM_CHANGETYPE];
			if(changeType==node_COMMONCONSTANT.STORYDESIGN_CHANGETYPE_NEW){
				loc_applyChangeNew(story, changeItem, saveRevert);
			}
			else if(changeType==node_COMMONCONSTANT.STORYDESIGN_CHANGETYPE_PATCH){
				loc_applyChangePatch(story, changeItem, false, undefined, saveRevert);
			}
			else if(changeType==node_COMMONCONSTANT.STORYDESIGN_CHANGETYPE_DELETE){
				loc_applyChangeDelete(story, changeItem, saveRevert);
			}
			else if(changeType==node_COMMONCONSTANT.STORYDESIGN_CHANGETYPE_STORYINFO){
				loc_applyChangeStoryInfo(story, changeItem, saveRevert);
			}
		},
		
		//return an array of all changes
		applyChangeAll : function(story, changeItem, allChanges){
			var changeType = changeItem[node_COMMONATRIBUTECONSTANT.CHANGEITEM_CHANGETYPE];
			if(changeType==node_COMMONCONSTANT.STORYDESIGN_CHANGETYPE_PATCH){
				loc_applyChangePatch(story, changeItem, true, allChanges);
			}
			else if(changeType==node_COMMONCONSTANT.STORYDESIGN_CHANGETYPE_NEW){
				loc_applyChangeNew(story, changeItem);
			}
			else if(changeType==node_COMMONCONSTANT.STORYDESIGN_CHANGETYPE_DELETE){
				loc_applyChangeDelete(story, changeItem);
			}
		},
		
		reverseChange : function(story, changeItem){
			var that  = this;
			var reverseChanges = changeItem[node_COMMONATRIBUTECONSTANT.CHANGEITEM_REVERTCHANGES];
			if(reverseChanges!=undefined){
				_.each(reverseChanges, function(reverseChange, i){
					that.applyChange(story, reverseChange, false);
				});
			}
		},
		
		createChangeItemPatch : function(targetRef, path, value){  return loc_createChangeItemPatch(targetRef, path, value);		},
		
		createChangeItemStoryIdIndex : function(story){
			var out = loc_createChangeItemStoryInfo(node_COMMONCONSTANT.STORY_INFO_IDINDEX, node_storyUtility.getIdIndex(story));
			out[node_COMMONATRIBUTECONSTANT.CHANGEITEM_REVERTABLE] = false;
			return out;
		},
	};		
			
	return loc_out;
}();

//*******************************************   End Node Definition  ************************************** 	

//populate dependency node data
nosliw.registerSetNodeDataEvent("constant.CONSTANT", function(){node_CONSTANT = this.getData();});
nosliw.registerSetNodeDataEvent("constant.COMMONCONSTANT", function(){node_COMMONCONSTANT = this.getData();});
nosliw.registerSetNodeDataEvent("constant.COMMONATRIBUTECONSTANT", function(){node_COMMONATRIBUTECONSTANT = this.getData();});
nosliw.registerSetNodeDataEvent("application.instance.story.storyUtility", function(){node_storyUtility = this.getData();});
nosliw.registerSetNodeDataEvent("application.story.module.builder.ElementId", function(){node_ElementId = this.getData();});

//Register Node by Name
packageObj.createChildNode("storyChangeUtility", node_utility); 

})(packageObj);
