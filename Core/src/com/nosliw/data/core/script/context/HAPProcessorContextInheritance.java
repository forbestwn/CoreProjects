package com.nosliw.data.core.script.context;

import java.util.Arrays;
import java.util.Map;

import com.nosliw.common.utils.HAPConstant;

public class HAPProcessorContextInheritance {

	//merge with parent through inheritance
	public static HAPContextGroup processContextInheritance(HAPContextGroup orgContext, HAPContextGroup parentContextGroup, HAPConfigureContextProcessor configure, HAPEnvContextProcessor contextProcessorEnv) {
		HAPContextGroup out = processConstant(orgContext);
		if(!HAPConfigureContextProcessor.VALUE_INHERITMODE_NONE.equals(configure.inheritMode)) {
			for(String categary : HAPContextGroup.getAllContextTypes()){
				if(parentContextGroup!=null && Arrays.asList(HAPContextGroup.getInheritableContextTypes()).contains(categary)) {
					HAPContext parentContext = parentContextGroup.getContext(categary);
					Map<String, HAPContextNodeRoot> parentEles = parentContext.getElements();
					for(String eleName : parentEles.keySet()) {
						if(isInheritable(out, parentContextGroup, categary, eleName, configure.inheritMode)) {
							out.addElement(eleName, createInheritedElement(parentContextGroup, categary, eleName), categary);
						}
					}
				}
			}
		}
		return out;
	}
	
	private static HAPContextGroup processConstant(HAPContextGroup contextGroup) {
		HAPContextGroup out = contextGroup.clone();
		for(String contextCategary : HAPContextGroup.getAllContextTypes()) {
			for(String name : out.getContext(contextCategary).getElementNames()) {
				HAPContextNodeRoot node = out.getElement(contextCategary, name);
				if(node.getType().equals(HAPConstant.UIRESOURCE_ROOTTYPE_CONSTANT)) {
					node.getInfo().getInfo().setValue(HAPContextNodeRoot.INHERIT_MODE, HAPContextNodeRoot.INHERIT_MODE_FINAL);
				}
			}
		}
		return out;
	}
	
	//process element that inherited from parent
	private static HAPContextNodeRoot createInheritedElement(HAPContextGroup parentContextGroup, String contextCategary, String eleName) {
		HAPContextNodeRoot out = null;
		HAPContextNodeRoot node = parentContextGroup.getElement(contextCategary, eleName);
		if(node.getType().equals(HAPConstant.UIRESOURCE_ROOTTYPE_CONSTANT)) {
			out = node.cloneContextNodeRoot();
		}
		else {
			HAPContextNodeRootRelative relativeEle = new HAPContextNodeRootRelative();
			relativeEle.setPath(eleName);
			relativeEle.setParentCategary(contextCategary);
			out = relativeEle;
		}
		return out;
	}
	
	//whether child can herit from parent element
	private static boolean isInheritable(HAPContextGroup childContextGroup, HAPContextGroup parentContextGroup, String categary, String eleName, String inheritMode) {
		boolean out = false;
		HAPContextNodeRoot childNode = childContextGroup.getElement(categary, eleName);
		if(childNode==null) 		out = true;
		else {
			if(HAPConfigureContextProcessor.VALUE_INHERITMODE_PARENT.equals(inheritMode)) {
				if(!HAPContextNodeRoot.INHERIT_MODE_FINAL.equals(childNode.getInfo().getInfo().getValue(HAPContextNodeRoot.INHERIT_MODE_FINAL))) {
					out = true;
				}
			}
		}
		return out;
	}
	
}
