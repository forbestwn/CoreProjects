package com.nosliw.uiresource.page.story.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.nosliw.common.utils.HAPConstant;
import com.nosliw.data.core.script.context.HAPContextDefinitionElement;
import com.nosliw.data.core.script.context.HAPContextDefinitionLeafData;
import com.nosliw.data.core.script.context.HAPContextGroup;
import com.nosliw.data.core.script.context.HAPContextPath;
import com.nosliw.data.core.script.context.HAPInfoRelativeContextResolve;
import com.nosliw.data.core.script.context.HAPRequirementContextProcessor;
import com.nosliw.data.core.script.context.HAPUtilityContext;
import com.nosliw.data.core.story.HAPAliasElement;
import com.nosliw.data.core.story.HAPIdElement;
import com.nosliw.data.core.story.HAPIdElementInfo;
import com.nosliw.data.core.story.HAPStory;
import com.nosliw.data.core.story.HAPUtilityConnection;
import com.nosliw.data.core.story.HAPUtilityStory;
import com.nosliw.data.core.story.change.HAPChangeItem;
import com.nosliw.data.core.story.change.HAPChangeItemNew;
import com.nosliw.data.core.story.change.HAPRequestChange;
import com.nosliw.data.core.story.change.HAPUtilityChange;
import com.nosliw.data.core.story.element.connection.HAPConnectionContain;
import com.nosliw.uiresource.page.processor.HAPUtilityConfiguration;
import com.nosliw.uiresource.page.story.element.HAPStoryNodeUI;
import com.nosliw.uiresource.page.story.element.HAPUIDataStructureInfo;
import com.nosliw.uiresource.page.tag.HAPUITagManager;

public class HAPUINode {

	private HAPStory m_story;

	private String m_nodeId;
	
	private List<HAPUIChild> m_children;
	
	private List<HAPChangeItem> m_changes;
	
	private HAPStoryNodeUI m_storyNode;
	
	public HAPUINode(String nodeId, HAPStory story) {
		this.m_children = new ArrayList<HAPUIChild>();
		this.m_story = story;
		this.m_nodeId = nodeId;
	}

	public HAPUINode(HAPStoryNodeUI storyNode, HAPStory story) {
		this(storyNode.getId(), story);
		this.m_storyNode = storyNode;
	}

	public HAPUINode addChildNode(HAPStoryNodeUI childStoryNode, HAPConnectionContain connection) {
		HAPUIChild child = new HAPUIChild(new HAPUINode(childStoryNode, m_story), connection.getChildId(), connection.getId(), m_story);
		this.m_children.add(child);
		return child.getUINode();
	}
	
	public HAPUINode newChildNode(HAPStoryNodeUI childStoryNode, Object childId, HAPRequirementContextProcessor contextProcessRequirement, HAPUITagManager uiTagMan) {
		//build data info in ui node
		HAPUIDataStructureInfo dataStructureInfo = HAPUtility.buildDataStructureInfoForUIStoryNode(childStoryNode, this.getStoryNode().getDataStructureInfo().getContext(), contextProcessRequirement, uiTagMan);
		childStoryNode.setDataStructureInfo(dataStructureInfo);
		
		HAPAliasElement nodeName = this.m_story.generateTemporaryAlias();
		HAPAliasElement connectionName = this.m_story.generateTemporaryAlias();
		
		HAPRequestChange changeRequest = new HAPRequestChange();
		changeRequest.addChange(new HAPChangeItemNew(childStoryNode, nodeName));
		changeRequest.addChange(new HAPChangeItemNew(HAPUtilityConnection.newConnectionContain(HAPUtilityStory.newNodeId(this.m_nodeId), nodeName, (String)childId), connectionName));
		List<HAPChangeItem> changes = this.m_story.change(changeRequest);
		this.m_changes.addAll(changes);
		
		HAPUIChild childNode = new HAPUIChild(this.m_story.getElementId(nodeName.getAlias()).getId(), childId, this.m_story.getElementId(connectionName.getAlias()).getId(), this.m_story);
		return childNode.getUINode();
	}
	
	public HAPUIDataInfo getDataInfo(String name) {
		HAPUIDataInfo out = new HAPUIDataInfo();
		HAPInfoRelativeContextResolve resolve = HAPUtilityContext.resolveReferencedParentContextNode(new HAPContextPath(name), this.getStoryNode().getDataStructureInfo().getContext(), Arrays.asList(HAPContextGroup.getVisibleContextTypes()).toArray(new String[0]), HAPUtilityConfiguration.getContextProcessConfigurationForUIUit(HAPConstant.UIRESOURCE_TYPE_RESOURCE).relativeResolveMode);
		HAPContextDefinitionElement resolvedNode = resolve.resolvedNode;
		String nodeType = resolvedNode.getType();
		if(nodeType.equals(HAPConstant.CONTEXT_ELEMENTTYPE_DATA)) {
			HAPContextDefinitionLeafData dataNode = (HAPContextDefinitionLeafData)resolvedNode;
			out.setDataTypeCriteria(dataNode.getCriteria().getCriteria());
		}
		return out;
	}
	
	public HAPUINode getFirstDataUINode() {
		HAPIdElement eleId = this.getStoryElementId();
		HAPIdElementInfo idInfo = HAPUtilityStory.parseStoryElementId(eleId.getId());
		if(idInfo.getType().equals(HAPConstant.STORYNODE_TYPE_UIDATA)) 	return this;
		for(HAPUIChild child : this.getChildren()) {
			HAPUINode firstDataUINode = child.getUINode().getFirstDataUINode();
			if(firstDataUINode!=null)  return firstDataUINode;
		}
		return null;
	}
	
	public List<HAPIdElement> getAllStoryElements(){
		List<HAPIdElement> out = new ArrayList<HAPIdElement>();
		out.add(this.getStoryElementId());
		for(HAPUIChild child : this.getChildren()) {
			out.add(child.getConnectionElementId());
			out.addAll(child.getUINode().getAllStoryElements());
		}
		return out;
	}
	
	public List<HAPUINode> getAllUINodes(){
		List<HAPUINode> out = new ArrayList<HAPUINode>();
		out.add(this);
		for(HAPUIChild child : this.getChildren()) {
			out.addAll(child.getUINode().getAllUINodes());
		}
		return out;
	}
	
	public void updateUIDataStructureInfo(HAPUIDataStructureInfo parentDataStructureInfo, HAPRequirementContextProcessor contextProcessRequirement, HAPUITagManager uiTagMan) {
		HAPRequestChange changeRequest = new HAPRequestChange();
		//update data structure
		HAPUIDataStructureInfo dataStructureInfo = buildDataStructureInfo(parentDataStructureInfo, contextProcessRequirement, uiTagMan);
		changeRequest.addChange(HAPUtilityChange.buildChangePatch(this.getStoryElementId(), HAPStoryNodeUI.DATASTRUCTURE, dataStructureInfo));
		this.getStory().change(changeRequest);
		
		for(HAPUIChild child : this.getChildren()) {
			child.getUINode().updateUIDataStructureInfo(dataStructureInfo, contextProcessRequirement, uiTagMan);
		}
		
	}
	
	protected HAPUIDataStructureInfo buildDataStructureInfo(HAPUIDataStructureInfo parentDataStructureInfo, HAPRequirementContextProcessor contextProcessRequirement, HAPUITagManager uiTagMan) {
		HAPUIDataStructureInfo out;
		if(parentDataStructureInfo==null) {
			//for page node
			out = HAPUtility.buildDataStructureInfoForPageNode(this.getStory());
		}
		else {
			//for ui node
			out = HAPUtility.buildDataStructureInfoForUIStoryNode(this.getStoryNode(), parentDataStructureInfo.getContext(), contextProcessRequirement, uiTagMan);
		}
		return out;
	}
	
	private HAPStoryNodeUI getStoryNode() {
		if(this.m_storyNode==null) {
			this.m_story.getNode(this.m_nodeId);
		}
		return this.m_storyNode;
	}
	
	
	public List<HAPUIChild> getChildren(){   return this.m_children;    }
	
	public String getNodeId() {  return this.m_nodeId; 	}
	public HAPIdElement getStoryElementId() {    return new HAPIdElement(HAPConstant.STORYELEMENT_CATEGARY_NODE, this.m_nodeId);     }
	
	protected HAPStory getStory() {    return this.m_story;     }
}
