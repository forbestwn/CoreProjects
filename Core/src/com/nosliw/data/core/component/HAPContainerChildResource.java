package com.nosliw.data.core.component;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.nosliw.data.core.component.attachment.HAPContainerAttachment;
import com.nosliw.data.core.component.attachment.HAPAttachmentUtility;
import com.nosliw.data.core.resource.HAPResourceId;

public class HAPContainerChildResource {

	private Map<String, List<HAPInfoChildResource>> m_children;
	
	public HAPContainerChildResource() {
		this.m_children = new LinkedHashMap<String, List<HAPInfoChildResource>>();
	}
	
	public void addChildCompoentId(HAPInfoChildResource childResourceInfo, HAPContainerAttachment parentAttachment) {
		HAPResourceId resourceId = childResourceInfo.getResourceId();
		
		HAPAttachmentUtility.mergeContainerToResourceIdSupplement(resourceId, parentAttachment);
		
		List<HAPInfoChildResource> byName = this.m_children.get(resourceId.getType());
		if(byName==null) {
			byName = new ArrayList<HAPInfoChildResource>();
			this.m_children.put(resourceId.getType(), byName);
		}
		byName.add(childResourceInfo);
	}

	public Map<String, List<HAPInfoChildResource>> getChildren(){
		return this.m_children;
	}
}