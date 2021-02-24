package com.nosliw.data.core.script.context;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.nosliw.common.exception.HAPErrorUtility;
import com.nosliw.common.info.HAPInfo;
import com.nosliw.common.path.HAPComplexPath;
import com.nosliw.common.path.HAPPath;
import com.nosliw.common.utils.HAPBasicUtility;
import com.nosliw.common.utils.HAPConstant;
import com.nosliw.common.utils.HAPConstantShared;
import com.nosliw.common.utils.HAPNamingConversionUtility;
import com.nosliw.data.core.data.criteria.HAPCriteriaUtility;
import com.nosliw.data.core.data.criteria.HAPDataTypeCriteria;
import com.nosliw.data.core.data.criteria.HAPDataTypeCriteriaId;
import com.nosliw.data.core.data.criteria.HAPInfoCriteria;
import com.nosliw.data.core.data.variable.HAPVariableDataInfo;
import com.nosliw.data.core.matcher.HAPMatchers;
import com.nosliw.data.core.runtime.HAPRuntimeEnvironment;

public class HAPUtilityContext {

	
	
	public static HAPContextFlat buildFlatContextFromContextGroup(HAPContextGroup context, Set<String> excludedInfo) {
		HAPContextFlat out = new HAPContextFlat(excludedInfo);
		
		List<String> categarys = Arrays.asList(HAPContextGroup.getContextTypesWithPriority());
		Collections.reverse(categarys);
		for(String categary : categarys) {
			Map<String, HAPContextDefinitionRoot> eles = context.getElements(categary);
			for(String name : eles.keySet()) {
				out.addElementFromContextGroup(context, categary, name);
			}
		}
		return out;
	}
	
	
	//traverse through all the context definition element, and process it
	public static void processContextDefElement(HAPContextDefinitionElement contextDefEle, HAPContextDefEleProcessor processor, Object value) {
		if(processor.process(contextDefEle, value)) {
			if(HAPConstantShared.CONTEXT_ELEMENTTYPE_NODE.equals(contextDefEle.getType())) {
				HAPContextDefinitionNode nodeEle = (HAPContextDefinitionNode)contextDefEle;
				for(String childNodeName : nodeEle.getChildren().keySet()) {
					processContextDefElement(nodeEle.getChild(childNodeName), processor, value);
				}
			}
		}
		processor.postProcess(contextDefEle, value);
	}

	public static void processContextDefElementWithPathInfo(HAPContextDefinitionElement contextDefEle, HAPContextDefEleProcessor processor, String path) {
		if(processor.process(contextDefEle, path)) {
			if(HAPConstantShared.CONTEXT_ELEMENTTYPE_NODE.equals(contextDefEle.getType())) {
				HAPContextDefinitionNode nodeEle = (HAPContextDefinitionNode)contextDefEle;
				for(String childNodeName : nodeEle.getChildren().keySet()) {
					processContextDefElementWithPathInfo(nodeEle.getChild(childNodeName), processor, HAPNamingConversionUtility.buildPath(path, childNodeName));
				}
			}
		}
		processor.postProcess(contextDefEle, path);
	}

	public static void processExpandedContextDefElementWithPathInfo(HAPContextDefinitionElement contextDefEle, HAPContextDefEleProcessor processor, String path) {
		HAPContextDefinitionElement solidated = contextDefEle.getSolidContextDefinitionElement();
		if(processor.process(solidated, path)) {
			if(HAPConstantShared.CONTEXT_ELEMENTTYPE_NODE.equals(solidated.getType())) {
				HAPContextDefinitionNode nodeEle = (HAPContextDefinitionNode)solidated;
				for(String childNodeName : nodeEle.getChildren().keySet()) {
					processContextDefElementWithPathInfo(nodeEle.getChild(childNodeName), processor, HAPNamingConversionUtility.buildPath(path, childNodeName));
				}
			}
		}
		processor.postProcess(solidated, path);
	}
	
	public static HAPContextStructure getReferedContext(String name, HAPParentContext parentContext, HAPContextStructure self) {
		if(HAPConstantShared.DATAASSOCIATION_RELATEDENTITY_SELF.equals(name))  return self;
		else return parentContext.getContext(name);
	}
	
	public static HAPContextDefinitionElement getDescendant(HAPContextDefinitionElement contextDefEle, String path) {
		HAPContextDefinitionElement out = contextDefEle;
		HAPPath pathObj = new HAPPath(path);
		for(String pathSeg : pathObj.getPathSegs()) {
			if(out!=null)			out = out.getChild(pathSeg);
		}
		return out;
	}

	public static HAPContextDefinitionElement getDescendant(HAPContext context, String path) {
		HAPContextDefinitionElement out = null;
		HAPComplexPath complexPath = new HAPComplexPath(path);
		HAPContextDefinitionRoot root = context.getElement(complexPath.getRootName());
		if(root!=null) {
			out = getDescendant(root.getDefinition(), complexPath.getPath());
		}
		return out;
	}
	
	public static HAPContextDefinitionElement getDescendant(HAPContextGroup contextGroup, String categary, String path) {
		HAPContextDefinitionElement out = null;
		HAPContext context = contextGroup.getContext(categary);
		if(context!=null)   out = getDescendant(context, path);
		return out;
	}

	public static HAPContextDefinitionElement getDescendant(HAPContextGroup contextGroup, String path) {
		HAPContextPath contextPath = new HAPContextPath(path);
		return getDescendant(contextGroup, contextPath.getRootElementId().getCategary(), contextPath.getPath());
	}
	
	public static void updateDataDescendant(HAPContextGroup contextGroup, String categary, String path, HAPContextDefinitionLeafData dataEle) {
		updateDataDescendant(contextGroup.getContext(categary), path, dataEle);
	}

	public static void updateDataDescendant(HAPContext context, String path, HAPContextDefinitionLeafData dataEle) {
		setDescendant(context, path, dataEle);
//		HAPComplexPath cpath = new HAPComplexPath(path);
//		HAPContextDefinitionRoot root = context.getElement(cpath.getRootName());
//		if(cpath.getPathSegs().length==0 && root!=null && root.getDefinition().getType().equals(HAPConstant.CONTEXT_ELEMENTTYPE_DATA)) {
//			//for data root replacement, not replace whole, just replace criteria
//			((HAPContextDefinitionLeafData)root.getDefinition()).setCriteria(dataEle.getCriteria());
//		}
//		else {
//			setDescendant(context, path, dataEle);
//		}
	}

	public static void setDescendant(HAPContextStructure contextStructure, HAPContextPath contextPath, HAPContextDefinitionElement ele) {
		HAPContextDefinitionRoot targetRoot = contextStructure.getElement(contextPath.getRootElementId().getFullName(), true);

		String[] pathSegs = contextPath.getPathSegments();
		if(pathSegs.length==0) {
			if(targetRoot.getDefinition()!=null && !targetRoot.getDefinition().getType().equals(ele.getType()))  HAPErrorUtility.invalid("");  //should be same type
			targetRoot.setDefinition(ele);
		}
		else {
			String seg = pathSegs[0];
			HAPContextDefinitionElement parentEle = targetRoot.getDefinition();
			for(int i=0; i<pathSegs.length-1; i++) {
				if(i==0) {
					if(parentEle==null) {
						parentEle = new HAPContextDefinitionNode();
						targetRoot.setDefinition(parentEle);
					}
				}
				String pathSeg = pathSegs[i]; 
				HAPContextDefinitionElement child = parentEle.getChild(pathSeg);
				if(child==null) {
					child = new HAPContextDefinitionNode();
					((HAPContextDefinitionNode)parentEle).addChild(pathSeg, child);
				}
				parentEle = child;
				seg = pathSegs[i+1];
			}
			if(((HAPContextDefinitionNode)parentEle).getChild(seg)!=null && !((HAPContextDefinitionNode)parentEle).getChild(seg).getType().equals(ele.getType())) 
				HAPErrorUtility.invalid("");  //should be same type
			((HAPContextDefinitionNode)parentEle).addChild(seg, ele);
		}
	}
	
	public static void setDescendant(HAPContextGroup contextGroup, String categary, String path, HAPContextDefinitionElement ele) {
//		setDescendant(contextGroup.getContext(categary), path, ele);
		setDescendant(contextGroup, new HAPContextPath(categary, path), ele);
	}

	public static void setDescendant(HAPContext context, String path, HAPContextDefinitionElement ele) {
		setDescendant(context, new HAPContextPath((String)null, path), ele);
//		HAPComplexPath cpath = new HAPComplexPath(path);
//		HAPContextDefinitionRoot targetRoot = context.getElement(cpath.getRootName());
//		if(targetRoot==null) {
//			targetRoot = new HAPContextDefinitionRoot();
//			context.addElement(cpath.getRootName(), targetRoot);
//		}
//		
//		if(cpath.getPathSegs().length==0) {
//			if(targetRoot.getDefinition()!=null && !targetRoot.getDefinition().getType().equals(ele.getType()))  HAPErrorUtility.invalid("");  //should be same type
//			targetRoot.setDefinition(ele);
//		}
//		else {
//			String seg = cpath.getPathSegs()[0];
//			HAPContextDefinitionElement parentEle = targetRoot.getDefinition();
//			for(int i=0; i<cpath.getPathSegs().length-1; i++) {
//				if(i==0) {
//					if(parentEle==null) {
//						parentEle = new HAPContextDefinitionNode();
//						targetRoot.setDefinition(parentEle);
//					}
//				}
//				String pathSeg = cpath.getPathSegs()[i]; 
//				HAPContextDefinitionElement child = parentEle.getChild(pathSeg);
//				if(child==null) {
//					child = new HAPContextDefinitionNode();
//					((HAPContextDefinitionNode)parentEle).addChild(pathSeg, child);
//				}
//				parentEle = child;
//				seg = cpath.getPathSegs()[i+1];
//			}
//			if(((HAPContextDefinitionNode)parentEle).getChild(seg)!=null && !((HAPContextDefinitionNode)parentEle).getChild(seg).getType().equals(ele.getType())) 
//				HAPErrorUtility.invalid("");  //should be same type
//			((HAPContextDefinitionNode)parentEle).addChild(seg, ele);
//		}
	}
	
	public static boolean isContextDefinitionElementConstant(HAPContextDefinitionElement ele) {   return HAPConstantShared.CONTEXT_ELEMENTTYPE_CONSTANT.equals(ele.getType());   }
	
	//discover all the relative elements in context def element
	public static Map<String, HAPContextDefinitionLeafRelative> isContextDefinitionElementRelative(HAPContextDefinitionElement ele) {
		Map<String, HAPContextDefinitionLeafRelative> out = new LinkedHashMap<String, HAPContextDefinitionLeafRelative>();
		discoverRelative(ele, out, null);
		return out;
	}
	
	private static void discoverRelative(HAPContextDefinitionElement ele, Map<String, HAPContextDefinitionLeafRelative> out, String path) {
		switch(ele.getType()) {
		case HAPConstantShared.CONTEXT_ELEMENTTYPE_RELATIVE:
			out.put(path+"", (HAPContextDefinitionLeafRelative)ele);
			break;
		case HAPConstantShared.CONTEXT_ELEMENTTYPE_NODE:
			HAPContextDefinitionNode nodeEle = (HAPContextDefinitionNode)ele;
			for(String subPath : nodeEle.getChildren().keySet()) {
				discoverRelative(nodeEle.getChildren().get(subPath), out, HAPNamingConversionUtility.buildPath(path, subPath));
			}
			break;
		}
	}
	
	//context root name can be like a.b.c and a.b.d
	//these two root name can be consolidated to one root name with a and child of b.c and b.d
	public static HAPContext consolidateContextRoot(HAPContext context) {
		HAPContext out = new HAPContext();
		
		for(String rootName : context.getElementNames()) {
			HAPContextDefinitionElement def = context.getElement(rootName).getDefinition();
			HAPUtilityContext.setDescendant(out, rootName, def);
		}
		return out;
	}
	
	public static String getContextGroupInheritMode(HAPInfo info) {  
		String out = HAPConstant.INHERITMODE_CHILD;
		if("false".equals(info.getValue(HAPContextGroup.INFO_INHERIT)))  out = HAPConstant.INHERITMODE_NONE;
		return out;				
	}
 
	public static void setContextGroupInheritModeNone(HAPInfo info) {		info.setValue(HAPContextGroup.INFO_INHERIT, "false");	}
	public static void setContextGroupInheritModeChild(HAPInfo info) {		info.setValue(HAPContextGroup.INFO_INHERIT, "true");	}
	
	public static boolean getContextGroupPopupMode(HAPInfo info) {  
		boolean out = true;
		if("false".equals(info.getValue(HAPContextGroup.INFO_POPUP)))  out = false;
		return out;				
	} 
 
	public static boolean getContextGroupEscalateMode(HAPInfo info) {  
		boolean out = false;
		if("true".equals(info.getValue(HAPContextGroup.INFO_ESCALATE)))  out = true;
		return out;				
	} 
	
	public static HAPContextGroup buildContextGroupFromContext(HAPContext context) {
		HAPContextGroup out = new HAPContextGroup();
		for(String rootName : context.getElementNames()) {
			HAPContextDefinitionRoot root = context.getElement(rootName);
			HAPContextDefinitionRootId rootId = new HAPContextDefinitionRootId(rootName);
			out.addElement(rootId.getName(), root, rootId.getCategary());
		}
		return out;
	}

	//find all data variables in context 
	public static Map<String, HAPInfoCriteria> discoverDataVariablesInContext(HAPContext context){
		Map<String, HAPInfoCriteria> out = new LinkedHashMap<String, HAPInfoCriteria>();
		for(String rootName : context.getElements().keySet()){
			HAPContextDefinitionRoot node = context.getElement(rootName);
			if(!node.isConstant()){
				discoverCriteriaInContextNode(rootName, node.getDefinition(), out);
			}
		}
		return out;
	}

	//discover data type criteria defined in context node
	//the purpose is to find variables related with data type criteria
	//the data type criteria name is full name in path, for instance, a.b.c.d
	private static void discoverCriteriaInContextNode(String path, HAPContextDefinitionElement contextDefEle, Map<String, HAPInfoCriteria> criterias){
		switch(contextDefEle.getType()) {
		case HAPConstantShared.CONTEXT_ELEMENTTYPE_RELATIVE:
			HAPContextDefinitionLeafRelative relativeEle = (HAPContextDefinitionLeafRelative)contextDefEle;
			if(relativeEle.getDefinition()!=null)		discoverCriteriaInContextNode(path, relativeEle.getSolidContextDefinitionElement(), criterias);
			break;
		case HAPConstantShared.CONTEXT_ELEMENTTYPE_DATA:
			HAPContextDefinitionLeafData dataEle = (HAPContextDefinitionLeafData)contextDefEle;
			HAPInfoCriteria varInfo = HAPInfoCriteria.buildCriteriaInfo(dataEle.getCriteria());
//			varInfo.setId(path);
			criterias.put(path, varInfo);
			break;
		case HAPConstantShared.CONTEXT_ELEMENTTYPE_NODE:
			HAPContextDefinitionNode nodeEle = (HAPContextDefinitionNode)contextDefEle;
			for(String childName : nodeEle.getChildren().keySet()) {
				String childPath = HAPNamingConversionUtility.cascadeComponentPath(path, childName);
				discoverCriteriaInContextNode(childPath, nodeEle.getChildren().get(childName), criterias);
			}
			break;
		}
	}

	public static HAPContextDefinitionRoot createRelativeContextDefinitionRoot(HAPContextDefinitionRoot parentNode, String parent, String contextCategary, String refPath, Set<String> excludedInfo) {
		HAPContextDefinitionRoot out = null;
		
		if(parentNode.isConstant()) {
			out = parentNode.cloneContextDefinitionRoot();
		}
		else {
			out = new HAPContextDefinitionRoot();
			out.setInfo(parentNode.getInfo().cloneInfo(excludedInfo));
			HAPContextDefinitionLeafRelative relativeEle = new HAPContextDefinitionLeafRelative();
			relativeEle.setParent(parent);
			relativeEle.setPath(contextCategary, refPath);
			if(parentNode.getDefinition().isProcessed()) {
//				relativeEle.setDefinition(parentNode.getDefinition().getSolidContextDefinitionElement());
				relativeEle.setDefinition(HAPUtilityContext.getDescendant(parentNode.getDefinition(), refPath).getSolidContextDefinitionElement());
				relativeEle.processed();
			}
			out.setDefinition(relativeEle);
		}
		return out;
	}
	
	//build interited node from parent
	public static HAPContextDefinitionRoot createRelativeContextDefinitionRoot(HAPContextDefinitionRoot parentNode, String contextCategary, String refPath, Set<String> excludedInfo) {
		return createRelativeContextDefinitionRoot(parentNode, null, contextCategary, refPath, excludedInfo);
	}


	//build interited node from parent
	public static HAPContextDefinitionRoot createRelativeContextDefinitionRoot(HAPContextGroup parentContextGroup, String contextCategary, String refPath, Set<String> excludedInfo) {
		return createRelativeContextDefinitionRoot(parentContextGroup.getElement(contextCategary, refPath), contextCategary, refPath, excludedInfo);
	}

	public static HAPInfoContextElementReferenceResolve resolveReferencedContextElement(HAPContextPath contextPath, HAPContextStructure parentContext){
		if(parentContext==null)   return null;
		HAPInfoContextElementReferenceResolve out = null;
		String contextType = parentContext.getType();
		if(contextType.equals(HAPConstantShared.CONTEXTSTRUCTURE_TYPE_NOTFLAT)) {
			out = resolveReferencedContextElement(contextPath, parentContext);
		}
		else {
			out = ((HAPContext)parentContext).discoverChild(contextPath.getRootElementId().getName(), contextPath.getSubPath());
		}
		
		//process remaining path
		if(out!=null)   out = processContextElementRefResolve(out);
		
		return out;
	}
	
	//go through different context group categaryes to find referenced node in context. 
	public static HAPInfoContextElementReferenceResolve resolveReferencedContextElement(HAPContextPath contextPath, HAPContextGroup parentContext, String[] categaryes, String mode){
		if(parentContext==null)   return null;
		
		HAPContextDefinitionRootId refNodeId = contextPath.getRootElementId(); 
		String refPath = contextPath.getSubPath();
		
		//candidate categary
		List<String> categaryCandidates = new ArrayList<String>();
		if(HAPBasicUtility.isStringNotEmpty(refNodeId.getCategary()))  categaryCandidates.add(refNodeId.getCategary());  //check path first
		else if(categaryes!=null && categaryes.length>0)    categaryCandidates.addAll(Arrays.asList(categaryes));     //input
		else categaryCandidates.addAll(Arrays.asList(HAPContextGroup.getVisibleContextTypes()));               //otherwise, use visible context
		
		//find candidates, path similar
		List<HAPInfoContextElementReferenceResolve> candidates = new ArrayList<HAPInfoContextElementReferenceResolve>();
		for(String contextType : categaryCandidates){
			HAPInfoContextElementReferenceResolve resolved = parentContext.getContext(contextType).discoverChild(refNodeId.getName(), refPath);
			if(resolved.rootNode!=null) {
				resolved.path = new HAPContextPath(contextType, refNodeId.getName(), refPath);
				candidates.add(resolved);
				if(HAPConstant.RESOLVEPARENTMODE_FIRST.equals(mode))   break;
			}
		}

		//find best node from candidate
		//remaining path is shortest
		HAPInfoContextElementReferenceResolve out = null;
		int length = 99999;
		for(HAPInfoContextElementReferenceResolve candidate : candidates) {
			String remainingPath = candidate.remainPath;
			if(remainingPath==null) {
				out = candidate;
				break;
			}
			else {
				if(remainingPath.length()<length) {
					length = remainingPath.length();
					out = candidate;
				}
			}
		}
		
		out = processContextElementRefResolve(out);
		return out;
	}
	
	//process remain path into internal node
	private static HAPInfoContextElementReferenceResolve processContextElementRefResolve(HAPInfoContextElementReferenceResolve out) {
		if(out!=null && !out.rootNode.isConstant()) {
			if(HAPBasicUtility.isStringEmpty(out.remainPath)) {
				//exactly match with path
				out.resolvedNode = out.referedSolidNode;
			}
			else {
				//nof exactly match with path
				HAPContextDefinitionElement candidateNode = out.referedSolidNode.getSolidContextDefinitionElement();
				if(HAPConstantShared.CONTEXT_ELEMENTTYPE_DATA.equals(candidateNode.getType())) {
					//data type node
					HAPContextDefinitionLeafData dataLeafEle = (HAPContextDefinitionLeafData)candidateNode;
					HAPDataTypeCriteria childCriteria = HAPCriteriaUtility.getChildCriteriaByPath(dataLeafEle.getCriteria(), out.remainPath);
					if(childCriteria!=null) {
						out.resolvedNode = new HAPContextDefinitionLeafData(new HAPVariableDataInfo(childCriteria)); 
					}
					else {
//						out.resolvedNode = new HAPContextDefinitionLeafValue();
					}
				}
				else if(HAPConstantShared.CONTEXT_ELEMENTTYPE_VALUE.equals(candidateNode.getType())){
					out.resolvedNode = candidateNode;
				}
			}
		}
		return out;
	}
	
	//find exact physical node
	public static boolean isPhysicallySolved(HAPInfoContextElementReferenceResolve solve) {
		return solve.resolvedNode!=null && HAPBasicUtility.isStringEmpty(solve.remainPath);
	}

	//find node
	public static boolean isLogicallySolved(HAPInfoContextElementReferenceResolve solve) {
		return solve.resolvedNode!=null;
	}

	public static Map<String, HAPMatchers> mergeContextRoot(HAPContextDefinitionRoot origin, HAPContextDefinitionRoot expect, boolean modifyStructure, HAPRuntimeEnvironment runtimeEnv) {
		Map<String, HAPMatchers> matchers = new LinkedHashMap<String, HAPMatchers>();
		mergeContextDefitionElement(origin.getDefinition(), expect.getDefinition(), modifyStructure, matchers, null, runtimeEnv);
		return matchers;
	}

	public static Map<String, HAPMatchers> mergeContextDefitionElement(HAPContextDefinitionElement originDef, HAPContextDefinitionElement expectDef, boolean modifyStructure, String path, HAPRuntimeEnvironment runtimeEnv){
		Map<String, HAPMatchers> matchers = new LinkedHashMap<String, HAPMatchers>();
		mergeContextDefitionElement(originDef, expectDef, modifyStructure, matchers, null, runtimeEnv);
		return matchers;
	}
	
	//merge origin context def with child context def to expect context out
	//also generate matchers from origin to expect
	public static void mergeContextDefitionElement(HAPContextDefinitionElement originDef, HAPContextDefinitionElement expectDef, boolean modifyStructure, Map<String, HAPMatchers> matchers, String path, HAPRuntimeEnvironment runtimeEnv){
		if(path==null)  path = "";
		//merge is about solid
		originDef = originDef.getSolidContextDefinitionElement();
		expectDef = expectDef.getSolidContextDefinitionElement();
		String type = expectDef.getType();
		
		if(originDef.getType().equals(HAPConstantShared.CONTEXT_ELEMENTTYPE_CONSTANT)) {
			switch(type) {
			case HAPConstantShared.CONTEXT_ELEMENTTYPE_DATA:
			{
				HAPContextDefinitionLeafConstant dataOrigin = (HAPContextDefinitionLeafConstant)originDef.getSolidContextDefinitionElement();
				HAPContextDefinitionLeafData dataExpect = (HAPContextDefinitionLeafData)expectDef;
				//cal matchers
				HAPMatchers matcher = HAPCriteriaUtility.mergeVariableInfo(HAPInfoCriteria.buildCriteriaInfo(new HAPDataTypeCriteriaId(dataOrigin.getDataValue().getDataTypeId(), null)), dataExpect.getCriteria(), runtimeEnv.getDataTypeHelper()); 
				if(!matcher.isVoid())  matchers.put(path, matcher);
				break;
			}
			}
		}
		else if(expectDef.getType().equals(HAPConstantShared.CONTEXT_ELEMENTTYPE_CONSTANT)) {  //kkkkk
			switch(originDef.getType()) {
			case HAPConstantShared.CONTEXT_ELEMENTTYPE_DATA:
			{
				HAPContextDefinitionLeafData dataOrigin = (HAPContextDefinitionLeafData)originDef;
				 HAPContextDefinitionLeafConstant dataExpect = (HAPContextDefinitionLeafConstant)expectDef.getSolidContextDefinitionElement();
				//cal matchers
				HAPMatchers matcher = HAPCriteriaUtility.mergeVariableInfo(HAPInfoCriteria.buildCriteriaInfo(dataOrigin.getCriteria()), new HAPDataTypeCriteriaId(dataExpect.getDataValue().getDataTypeId(), null), runtimeEnv.getDataTypeHelper()); 
				if(!matcher.isVoid())  matchers.put(path, matcher);
				break;
			}
			}
		}
		else {
			if(!originDef.getType().equals(type))   HAPErrorUtility.invalid("");   //not same type, error
			switch(type) {
			case HAPConstantShared.CONTEXT_ELEMENTTYPE_DATA:
			{
				HAPContextDefinitionLeafData dataOrigin = (HAPContextDefinitionLeafData)originDef.getSolidContextDefinitionElement();
				HAPContextDefinitionLeafData dataExpect = (HAPContextDefinitionLeafData)expectDef;
				//cal matchers
				HAPMatchers matcher = HAPCriteriaUtility.mergeVariableInfo(HAPInfoCriteria.buildCriteriaInfo(dataOrigin.getCriteria()), dataExpect.getCriteria(), runtimeEnv.getDataTypeHelper()); 
				if(!matcher.isVoid())  matchers.put(path, matcher);
				break;
			}
			case HAPConstantShared.CONTEXT_ELEMENTTYPE_NODE:
			{
				HAPContextDefinitionNode nodeOrigin = (HAPContextDefinitionNode)originDef;
				HAPContextDefinitionNode nodeExpect = (HAPContextDefinitionNode)expectDef;
				for(String nodeName : nodeExpect.getChildren().keySet()) {
					HAPContextDefinitionElement childNodeExpect = nodeExpect.getChildren().get(nodeName);
					HAPContextDefinitionElement childNodeOrigin = nodeOrigin.getChildren().get(nodeName);
					if(childNodeOrigin!=null || modifyStructure) {
						switch(childNodeExpect.getType()) {
						case HAPConstantShared.CONTEXT_ELEMENTTYPE_DATA:
						{
							if(childNodeOrigin==null) {
								childNodeOrigin = new HAPContextDefinitionLeafData();
								nodeOrigin.addChild(nodeName, childNodeOrigin);
							}
							mergeContextDefitionElement(childNodeOrigin, childNodeExpect, modifyStructure, matchers, HAPNamingConversionUtility.cascadePath(path, nodeName), runtimeEnv);
							break;
						}
						case HAPConstantShared.CONTEXT_ELEMENTTYPE_NODE:
						{
							if(childNodeOrigin==null) {
								childNodeOrigin = new HAPContextDefinitionNode();
								nodeOrigin.addChild(nodeName, childNodeOrigin);
							}
							mergeContextDefitionElement(childNodeOrigin, childNodeExpect, modifyStructure, matchers, HAPNamingConversionUtility.cascadePath(path, nodeName), runtimeEnv);
							break;
						}
						default :
						{
							if(childNodeOrigin==null) {
								childNodeOrigin = childNodeExpect.cloneContextDefinitionElement();
								nodeOrigin.addChild(nodeName, childNodeOrigin);
							}
							break;
						}
					}
				}
				break;
				}
			}
			}
		}
	}

	public static HAPContextStructure hardMerge(HAPContextStructure child, HAPContextStructure parent) {
		if(child==null) return parent.cloneContextStructure();
		if(parent==null)  return child.cloneContextStructure();
		
		String type1 = child.getType();
		String type2 = parent.getType();
		if(!type1.equals(type2))  throw new RuntimeException();
		
		HAPContextStructure out = null;
		out = child.cloneContextStructure();
		out.hardMergeWith(parent);
		return out;
	}

	
	public static HAPContextGroup hardMerge(HAPContextGroup child, HAPContextGroup parent) {
		HAPContextGroup out = null;
		if(child==null) out = parent.cloneContextGroup();
		else {
			out = child.cloneContextGroup();
			out.hardMergeWith(parent);;
		}
		return out;
	}
	
}
