package com.nosliw.uiresource.definition;

import java.util.LinkedHashMap;
import java.util.Map;

import com.nosliw.common.constant.HAPAttribute;
import com.nosliw.common.serialization.HAPJsonUtility;
import com.nosliw.common.serialization.HAPSerializationFormat;
import com.nosliw.common.utils.HAPConstant;
import com.nosliw.uiresource.HAPDataBinding;

public class HAPUIDefinitionUnitTag extends HAPUIDefinitionUnit{

	@HAPAttribute
	public static final String TAGNAME = "tagName";
	
	//name of this customer tag
	private String m_tagName;

	//data bindings related with this customer tag
	private Map<String, HAPDataBinding> m_dataBindings;

	public HAPUIDefinitionUnitTag(String tagName, String id){
		super(id);
		this.m_tagName = tagName;
		this.m_dataBindings = new LinkedHashMap<String, HAPDataBinding>();
	}
	
	public String getTagName(){	return this.m_tagName;}
	
	public void addDataBinding(HAPDataBinding dataBinding){	this.m_dataBindings.put(dataBinding.getName(), dataBinding);	}
	
	@Override
	public String getType() {
		return HAPConstant.UIRESOURCE_TYPE_TAG;
	}

	@Override
	public void addAttribute(String name, String value){
		super.addAttribute(name, value);
	}

	@Override
	protected void buildFullJsonMap(Map<String, String> jsonMap, Map<String, Class<?>> typeJsonMap){
		super.buildFullJsonMap(jsonMap, typeJsonMap);
		jsonMap.put(TAGNAME, this.m_tagName);
	}
	
	@Override
	protected void buildJsonMap(Map<String, String> jsonMap, Map<String, Class<?>> typeJsonMap){
		super.buildJsonMap(jsonMap, typeJsonMap);
		jsonMap.put(TAGNAME, this.m_tagName);
		
		Map<String, String> dataBindingJsons = new LinkedHashMap<String, String>();
		for(String name : this.m_dataBindings.keySet()){
			HAPDataBinding dataBinding = this.m_dataBindings.get(name);
			dataBindingJsons.put(name, dataBinding.toStringValue(HAPSerializationFormat.JSON_FULL));
		}
		jsonMap.put(DATABINDINGS, HAPJsonUtility.buildMapJson(dataBindingJsons));
	}

}
