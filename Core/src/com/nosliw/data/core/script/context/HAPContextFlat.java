package com.nosliw.data.core.script.context;

import java.util.LinkedHashMap;
import java.util.Map;

import com.nosliw.common.constant.HAPAttribute;
import com.nosliw.common.constant.HAPEntityWithAttribute;
import com.nosliw.common.serialization.HAPJsonUtility;
import com.nosliw.common.serialization.HAPSerializableImp;
import com.nosliw.common.serialization.HAPSerializationFormat;
import com.nosliw.common.updatename.HAPUpdateName;
import com.nosliw.common.utils.HAPConstant;

//flat context is 
@HAPEntityWithAttribute
public class HAPContextFlat extends HAPSerializableImp{

	@HAPAttribute
	public static final String CONTEXT = "context";

	//used to map from local (abc) to global name (public_abc)
	@HAPAttribute
	public static final String NAMEMAPPING = "nameMapping";
	
	private HAPContext m_context;
	
	private Map<String, String> m_nameMapping;
	
	
	public HAPContextFlat() {
		this.m_context = new HAPContext();
		this.m_nameMapping = new LinkedHashMap<String, String>();
	}

	public HAPContextFlat(HAPContext context) {
		this.m_context = context;
		this.m_nameMapping = new LinkedHashMap<String, String>();
	}
	
	public void updateRootName(HAPUpdateName nameUpdate) {
		HAPContext newContext = new HAPContext();
		//update context
		for(String eleName : this.m_context.getElementNames()) {
			HAPContextDefinitionRoot root = this.m_context.getElement(eleName);
			if(root.getDefinition() instanceof HAPContextDefinitionLeafRelative) {
				HAPContextDefinitionLeafRelative relative = (HAPContextDefinitionLeafRelative)root.getDefinition();
				if(!relative.isRelativeToParent()) {
					HAPContextPath path = relative.getPath();
					relative.setPath(new HAPContextPath(new HAPContextDefinitionRootId(path.getRootElementId().getCategary(), nameUpdate.getUpdatedName(path.getRootElementId().getName())), path.getSubPath()));
				}
			}
			newContext.addElement(nameUpdate.getUpdatedName(eleName), root);
		}
		
		Map<String, String> newMapping = new LinkedHashMap<String, String>();
		for(String name : this.m_nameMapping.keySet()) {
			newMapping.put(nameUpdate.getUpdatedName(name), nameUpdate.getUpdatedName(this.m_nameMapping.get(name)));
		}
		this.m_nameMapping = newMapping;
	}
	
	//solid variable name
	//if item does not exist, then return null
	public String getSolidName(String name) {
		String out = this.m_nameMapping.get(name);
		if(out==null) {
			if(this.m_context.getElement(name)!=null)	out = name;
		}
		return out;
	}
	
	public HAPContext getContext() {  return this.m_context;  }
	
	public Map<String, HAPContextDefinitionRoot> getSolidRoots(){
		Map<String, HAPContextDefinitionRoot> out = new LinkedHashMap<String, HAPContextDefinitionRoot>();
		for(String elementName : this.m_context.getElementNames()) {
			if(this.m_nameMapping.get(elementName)==null) {
				out.put(elementName, this.m_context.getElement(elementName));
			}
		}
		return out;
	}
	
	public void addElement(String name, HAPContextDefinitionRoot rootEle){		
		this.m_context.addElement(name, rootEle);	
		
		HAPContextDefinitionElement contextDefEle = rootEle.getDefinition();
		if(HAPConstant.CONTEXT_ELEMENTTYPE_RELATIVE.equals(contextDefEle.getType())) {
			HAPContextDefinitionLeafRelative relativeEle = (HAPContextDefinitionLeafRelative)contextDefEle;
			if(!relativeEle.isRelativeToParent()) {
				//for element that relative to another element in this flat context, add mapping for it
				this.addNameMapping(name, relativeEle.getPath().getRootElementId().getName());
				//resolve relative
				relativeEle.setDefinition(this.getContext().getElement(relativeEle.getPath().getRootElementId().getName()).getDefinition());
			}
		}
	}
	
	public void addNameMapping(String name, String nameMapping) {		this.m_nameMapping.put(name, nameMapping);	}
	
	public Map<String, Object> getConstantValue(){		return this.m_context.getConstantValue();	}
	
	public HAPContextFlat getVariableContext() {   
		HAPContextFlat out = new HAPContextFlat();
		out.m_context = this.m_context.getVariableContext();
		out.m_nameMapping.putAll(this.m_nameMapping);
		return out;
	}

	public HAPContextFlat cloneContextFlat() {
		return null;
	}
	
	
	@Override
	protected void buildJsonMap(Map<String, String> jsonMap, Map<String, Class<?>> typeJsonMap){
		super.buildJsonMap(jsonMap, typeJsonMap);
		jsonMap.put(NAMEMAPPING, HAPJsonUtility.buildMapJson(this.m_nameMapping));
		jsonMap.put(CONTEXT, this.m_context.toStringValue(HAPSerializationFormat.JSON));
	}
}
