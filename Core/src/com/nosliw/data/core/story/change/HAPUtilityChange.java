package com.nosliw.data.core.story.change;

import java.util.ArrayList;
import java.util.List;

import com.nosliw.common.utils.HAPConstant;
import com.nosliw.data.core.story.HAPElementGroup;
import com.nosliw.data.core.story.HAPIdElement;
import com.nosliw.data.core.story.HAPInfoElement;
import com.nosliw.data.core.story.HAPStory;
import com.nosliw.data.core.story.HAPStoryElement;

public class HAPUtilityChange {

	public static void revertChange(HAPStory story, List<HAPChangeItem> changeItems) {
		//apply in revert sequence
		for(int i=changeItems.size()-1; i>=0; i--) {
			HAPChangeItem changeItem = changeItems.get(i);
			List<HAPChangeItem> revertChanges = changeItem.getRevertChanges();
			if(revertChanges!=null) {
				for(HAPChangeItem revertChange : revertChanges) {
					applySingleChange(story, revertChange, null, false);
				}
			}
		}
	}

	//apply change, not triggured extra changes to story
	public static void applyChanges(HAPStory story, List<HAPChangeItem> changeItems) {		
		for(HAPChangeItem change : changeItems) {
			applyChange(story, change);
		}
	}

	//apply change and triggued extra changes to story
	//allChanges : story all changes
	public static void applyChanges(HAPStory story, List<HAPChangeItem> changeItems, List<HAPChangeItem> allChanges) {
		for(HAPChangeItem change : changeItems) {
			applyChange(story, change, allChanges);
		}
	}
	
	//apply change, not triggured extra changes to story
	public static void applyChange(HAPStory story, HAPChangeItem changeItem) {		applySingleChange(story, changeItem, null, true);	}
	
	//apply change and triggued extra changes to story
	//allChanges : story all changes
	public static HAPStoryElement applyChange(HAPStory story, HAPChangeItem change, List<HAPChangeItem> allChanges) {
		allChanges.add(change);
		
		List<HAPChangeItem> extraChanges = new ArrayList<HAPChangeItem>();
		HAPStoryElement element = applySingleChange(story, change, extraChanges, true);
		
		for(HAPChangeItem extraChange : extraChanges) {
			applyChange(story, extraChange, allChanges);
		}
		return element;
	}	

	//apply change to story
	//     extraChanges :  sometimes, one change may trigue more extra changes
	//     saveRevert : whether save revert information when apply change.
	//return story element related with change
	private static HAPStoryElement applySingleChange(HAPStory story, HAPChangeItem changeItem, List<HAPChangeItem> extraChanges, boolean saveRevert) {
		HAPStoryElement out = null;
		String changeType = changeItem.getChangeType();
		if(changeType.equals(HAPConstant.STORYDESIGN_CHANGETYPE_PATCH)) {
			HAPChangeItemPatch changePatch = (HAPChangeItemPatch)changeItem;
			out = story.getElement(changePatch.getTargetCategary(), changePatch.getTargetId());
			HAPChangeResult changeResult = out.patch(changePatch.getPath(), changePatch.getValue());
			//extra changes info
			if(extraChanges!=null) 	extraChanges.addAll(changeResult.getExtraChanges());
			//revert changes info
			if(saveRevert && changeItem.getRevertChanges()==null) 	changeItem.setRevertChanges(changeResult.getRevertChanges());
		}
		else if(changeType.equals(HAPConstant.STORYDESIGN_CHANGETYPE_NEW)) {
			HAPChangeItemNew changeNew = (HAPChangeItemNew)changeItem;
			HAPStoryElement element = changeNew.getElement();
			out = story.addElement(element);
			changeNew.setElement(null);
			changeNew.setTargetId(out.getId());
			//revert changes info
			if(saveRevert) {
				List<HAPChangeItem> revertChanges = new ArrayList<HAPChangeItem>();
				revertChanges.add(new HAPChangeItemDelete(out.getCategary(), out.getId()));
				changeItem.setRevertChanges(revertChanges);
			}
		}		
		else if(changeType.equals(HAPConstant.STORYDESIGN_CHANGETYPE_DELETE)) {
			HAPChangeItemDelete changeDelete = (HAPChangeItemDelete)changeItem;
			HAPStoryElement element = story.deleteElement(changeDelete.getTargetCategary(), changeDelete.getTargetId());
			//revert changes info
			if(saveRevert) {
				List<HAPChangeItem> revertChanges = new ArrayList<HAPChangeItem>();
				revertChanges.add(new HAPChangeItemNew(element));
				changeItem.setRevertChanges(revertChanges);
			}
		}
		return out;
	}
	
	public static HAPChangeInfo applyNew(HAPStory story, HAPStoryElement ele, List<HAPChangeItem> changes, HAPElementGroup group) {
		HAPChangeInfo out = applyNew(story, ele, changes);
		group.addElement(new HAPInfoElement(out.getStoryElement().getElementId()));
		return out;
	}

	public static HAPChangeInfo applyNew(HAPStory story, HAPStoryElement ele, List<HAPChangeItem> changes) {
		HAPChangeItemNew change = new HAPChangeItemNew(ele);
		HAPStoryElement element = applyChange(story, change, changes);
		return new HAPChangeInfo(change, element);
	}
	
	public static HAPChangeInfo applyDelete(HAPStory story, HAPIdElement elementId, List<HAPChangeItem> changes) {
		HAPChangeItemDelete change = new HAPChangeItemDelete(elementId.getCategary(), elementId.getId());
		HAPStoryElement element = applyChange(story, change, changes);
		return new HAPChangeInfo(change, element);
	}
	
	public static HAPChangeInfo applyPatch(HAPStory story, HAPIdElement targetEleId, String path, Object value, List<HAPChangeItem> changes) {
		HAPChangeItemPatch change = new HAPChangeItemPatch(targetEleId.getCategary(), targetEleId.getId(), path, value);
		HAPStoryElement element = applyChange(story, change, changes);
		return new HAPChangeInfo(change, element);
	}
	
	public static HAPChangeItem buildChangePatch(HAPStoryElement element, String path, Object value) {	return new HAPChangeItemPatch(element.getCategary(), element.getId(), path, value);	}
	
	public static HAPChangeItem buildChangeNew(String itemCategary, String itemId) { return new HAPChangeItemNew(itemCategary, itemId); }
	
}