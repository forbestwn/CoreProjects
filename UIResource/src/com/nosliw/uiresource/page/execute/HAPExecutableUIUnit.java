package com.nosliw.uiresource.page.execute;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringEscapeUtils;

import com.nosliw.common.constant.HAPAttribute;
import com.nosliw.common.constant.HAPEntityWithAttribute;
import com.nosliw.common.serialization.HAPJsonUtility;
import com.nosliw.common.serialization.HAPScript;
import com.nosliw.common.serialization.HAPSerializableImp;
import com.nosliw.common.serialization.HAPSerializationFormat;
import com.nosliw.data.core.script.context.HAPContextEntity;
import com.nosliw.data.core.script.context.HAPContextGroup;
import com.nosliw.data.core.script.expressionscript.HAPContextScriptExpressionProcess;
import com.nosliw.uiresource.page.definition.HAPDefinitionUIUnit;
import com.nosliw.uiresource.page.definition.HAPDefinitionUIUnitTag;
import com.nosliw.uiresource.page.definition.HAPElementEvent;

@HAPEntityWithAttribute(baseName="UIRESOURCEDEFINITION")
public class HAPExecutableUIUnit extends HAPSerializableImp{

	@HAPAttribute
	public static final String ID = "id";
	@HAPAttribute
	public static final String CONTEXT = "context";
	@HAPAttribute
	public static final String TYPE = "type";
	@HAPAttribute
	public static final String SCRIPTEXPRESSIONSINCONTENT = "scriptExpressionsInContent";
	@HAPAttribute
	public static final String SCRIPTEXPRESSIONINATTRIBUTES = "scriptExpressionInAttributes";
	@HAPAttribute
	public static final String SCRIPTEXPRESSIONINTAGATTRIBUTES = "scriptExpressionTagAttributes";
	@HAPAttribute
	public static final String SCRIPT = "script";
	@HAPAttribute
	public static final String HTML = "html";

	
	@HAPAttribute
	public static final String ELEMENTEVENTS = "elementEvents";
	@HAPAttribute
	public static final String TAGEVENTS = "tagEvents";
	@HAPAttribute
	public static final String UITAGS = "uiTags";
	@HAPAttribute
	public static final String ATTRIBUTES = "attributes";
	@HAPAttribute
	public static final String UITAGLIBS = "uiTagLibs";
	@HAPAttribute
	public static final String CONSTANTS = "constants";
	@HAPAttribute
	public static final String EXPRESSIONS = "expressions";
	@HAPAttribute
	public static final String EVENTS = "events";
	@HAPAttribute
	public static final String SERVICES = "services";
	
	private HAPDefinitionUIUnit m_uiUnitDefinition;

	//context after processed
	private HAPContextGroup m_context;

	//store all the constant attribute for this domain
	//for customer tag, they are the tag's attribute
	//for resource, they are the attribute of body
	private Map<String, String> m_attributes;
	
	//expression unit
	private HAPContextScriptExpressionProcess m_expressionContext;
	
	//all the customer tag within the domain
	private Map<String, HAPExecutableUIUnitTag> m_uiTags; 
	
	//all the expressions within content under this domain
	private Set<HAPUIEmbededScriptExpressionInContent> m_scriptExpressionsInContent;
	//all the attribute expressions in regular tag under this domain 
	private Set<HAPUIEmbededScriptExpressionInAttribute> m_scriptExpressionsInAttribute;
	//all the attribute expressions in customer tag under this domain
	private Set<HAPUIEmbededScriptExpressionInAttribute> m_scriptExpressionsInTagAttribute;

	//event definition 
	private Map<String, HAPContextEntity> m_eventsDefinition;
	//service requirment definition
	private Map<String, HAPContextEntity> m_servicesDefinition;
	
	public HAPExecutableUIUnit(HAPDefinitionUIUnit uiUnitDefinition) {
		this.m_uiUnitDefinition = uiUnitDefinition;
		this.m_expressionContext = new HAPContextScriptExpressionProcess();
		this.m_uiTags = new LinkedHashMap<String, HAPExecutableUIUnitTag>();
		this.m_context = new HAPContextGroup();
		this.m_attributes = new LinkedHashMap<String, String>();
		
		//build tag trees according to definition
		for(HAPDefinitionUIUnitTag tag : uiUnitDefinition.getUITags()) {
			this.m_uiTags.put(tag.getId(), new HAPExecutableUIUnitTag(tag));
		}
	}
	
	public String getType() {  return this.m_uiUnitDefinition.getType();  }
	public HAPContextGroup getContext(){  return this.m_context;   }
	public void setContext(HAPContextGroup context) {  this.m_context = context;   }
	
	public Map<String, String> getAttributes(){   return this.m_attributes;    }
	public void addAttribute(String name, String value) {   this.m_attributes.put(name, value);   }

	public HAPDefinitionUIUnit getUIUnitDefinition() {	return this.m_uiUnitDefinition;	}
	
	public HAPContextScriptExpressionProcess getExpressionContext(){   return this.m_expressionContext;   }
	public void setExpressionContext(HAPContextScriptExpressionProcess context){  this.m_expressionContext = context;   }

	public Collection<HAPExecutableUIUnitTag> getUITags(){return this.m_uiTags.values();} 
	public HAPExecutableUIUnitTag getUITag(String id){return this.m_uiTags.get(id);} 
	
	public void addScriptExpressionsInContent(HAPUIEmbededScriptExpressionInContent se) {this.m_scriptExpressionsInContent.add(se);}
	public void addScriptExpressionsInAttribute(HAPUIEmbededScriptExpressionInAttribute se) { this.m_scriptExpressionsInAttribute.add(se);   }
	public void addScriptExpressionsInTagAttribute(HAPUIEmbededScriptExpressionInAttribute se) { this.m_scriptExpressionsInTagAttribute.add(se);   }

	public Set<HAPUIEmbededScriptExpressionInContent> getScriptExpressionsInContent() {  return this.m_scriptExpressionsInContent;  }
	public Set<HAPUIEmbededScriptExpressionInAttribute> getScriptExpressionsInAttribute() {  return this.m_scriptExpressionsInAttribute;   }
	public Set<HAPUIEmbededScriptExpressionInAttribute> getScriptExpressionsInTagAttribute() {  return this.m_scriptExpressionsInTagAttribute;   }

	@Override
	protected void buildJsonMap(Map<String, String> jsonMap, Map<String, Class<?>> typeJsonMap){
		this.buildMyJsonMap(jsonMap, typeJsonMap, HAPSerializationFormat.JSON);
	}
	
	@Override
	protected void buildFullJsonMap(Map<String, String> jsonMap, Map<String, Class<?>> typeJsonMap){
		this.buildMyJsonMap(jsonMap, typeJsonMap, HAPSerializationFormat.JSON_FULL);
		HAPScript script = this.m_uiUnitDefinition.getScriptBlock();
		if(script!=null){
			jsonMap.put(SCRIPT, script.toStringValue(HAPSerializationFormat.JSON_FULL));
			typeJsonMap.put(SCRIPT, script.getClass());
		}
	}
	
	protected void buildMyJsonMap(Map<String, String> jsonMap, Map<String, Class<?>> typeJsonMap, HAPSerializationFormat format){
		jsonMap.put(ID, this.m_uiUnitDefinition.getId());
		jsonMap.put(TYPE, String.valueOf(this.getType()));

		jsonMap.put(CONTEXT, this.getContext().toStringValue(format));
		
		List<String> expressionContentJsons = new ArrayList<String>();
		for(HAPUIEmbededScriptExpressionInContent expressionContent : this.m_scriptExpressionsInContent)  expressionContentJsons.add(expressionContent.toStringValue(format));
		jsonMap.put(SCRIPTEXPRESSIONSINCONTENT, HAPJsonUtility.buildArrayJson(expressionContentJsons.toArray(new String[0])));
		
		List<String> expressionAttributeJsons = new ArrayList<String>();
		for(HAPUIEmbededScriptExpressionInAttribute expressionAttr : this.m_scriptExpressionsInAttribute)  expressionAttributeJsons.add(expressionAttr.toStringValue(format));
		jsonMap.put(SCRIPTEXPRESSIONINATTRIBUTES, HAPJsonUtility.buildArrayJson(expressionAttributeJsons.toArray(new String[0])));

		List<String> expressionTagAttributeJsons = new ArrayList<String>();
		for(HAPUIEmbededScriptExpressionInAttribute expressionTagAttr : this.m_scriptExpressionsInTagAttribute)  expressionTagAttributeJsons.add(expressionTagAttr.toStringValue(format));
		jsonMap.put(SCRIPTEXPRESSIONINTAGATTRIBUTES, HAPJsonUtility.buildArrayJson(expressionTagAttributeJsons.toArray(new String[0])));

		
		List<String> eleEventsJsons = new ArrayList<String>();
		for(HAPElementEvent elementEvent : this.m_uiUnitDefinition.getNormalTagEvents())  eleEventsJsons.add(elementEvent.toStringValue(HAPSerializationFormat.JSON));
		jsonMap.put(ELEMENTEVENTS, HAPJsonUtility.buildArrayJson(eleEventsJsons.toArray(new String[0])));
		
		List<String> tagEvents = new ArrayList<String>();
		for(HAPElementEvent tagEvent : this.m_uiUnitDefinition.getCustomTagEvents())		tagEvents.add(tagEvent.toStringValue(HAPSerializationFormat.JSON));
		jsonMap.put(TAGEVENTS, HAPJsonUtility.buildArrayJson(tagEvents.toArray(new String[0])));
		
		Map<String, String> uiTagJsons = new LinkedHashMap<String, String>();
		for(String uiId : this.m_uiTags.keySet())	uiTagJsons.put(uiId, this.m_uiTags.get(uiId).toStringValue(format));
		jsonMap.put(UITAGS, HAPJsonUtility.buildMapJson(uiTagJsons));
		
		jsonMap.put(ATTRIBUTES, HAPJsonUtility.buildMapJson(this.m_uiUnitDefinition.getAttributes()));
		
		String htmlContent = StringEscapeUtils.escapeHtml(this.m_uiUnitDefinition.getContent());
		htmlContent = htmlContent.replaceAll("(\\r|\\n)", "");
		jsonMap.put(HTML, htmlContent);
		
		Map<String, String> constantsJsons = new LinkedHashMap<String, String>();
		for(String name : this.m_constantDefs.keySet()){
			constantsJsons.put(name, this.m_constantDefs.get(name).toStringValue(HAPSerializationFormat.JSON));
		}
		jsonMap.put(CONSTANTS, HAPJsonUtility.buildMapJson(constantsJsons));
	
		jsonMap.put(EVENTS, HAPJsonUtility.buildJson(this.m_eventsDefinition, HAPSerializationFormat.JSON));
		jsonMap.put(SERVICES, HAPJsonUtility.buildJson(this.m_servicesDefinition, HAPSerializationFormat.JSON));
	}

}
