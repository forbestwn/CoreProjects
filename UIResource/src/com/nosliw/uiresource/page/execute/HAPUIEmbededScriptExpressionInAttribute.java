package com.nosliw.uiresource.page.execute;

import java.util.List;
import java.util.Map;

import com.nosliw.common.constant.HAPAttribute;

public class HAPUIEmbededScriptExpressionInAttribute extends HAPUIEmbededScriptExpression{

	@HAPAttribute
	public static final String ATTRIBUTE = "attribute";

	//attribute name
	private String m_attribute;

	public HAPUIEmbededScriptExpressionInAttribute(String attr, String uiId, List<Object> elements){
		super(uiId, elements);
		this.m_attribute = attr;
	}

	public HAPUIEmbededScriptExpressionInAttribute(String attr, String uiId, String content){
		super(uiId, content);
		this.m_attribute = attr;
	}
	
	public void setAttribute(String attr){this.m_attribute=attr;}
	public String getAttribute(){return this.m_attribute;}

	@Override
	protected void buildFullJsonMap(Map<String, String> jsonMap, Map<String, Class<?>> typeJsonMap){
		super.buildFullJsonMap(jsonMap, typeJsonMap);
		jsonMap.put(ATTRIBUTE, this.m_attribute);
	}

	@Override
	protected void buildJsonMap(Map<String, String> jsonMap, Map<String, Class<?>> typeJsonMap){
		super.buildJsonMap(jsonMap, typeJsonMap);
		jsonMap.put(ATTRIBUTE, this.m_attribute);
	}
}
