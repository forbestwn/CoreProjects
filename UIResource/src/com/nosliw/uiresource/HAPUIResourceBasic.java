package com.nosliw.uiresource;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringEscapeUtils;

import com.nosliw.common.constant.HAPAttribute;
import com.nosliw.common.constant.HAPEntityWithAttribute;
import com.nosliw.common.serialization.HAPSerializableImp;
import com.nosliw.common.serialization.HAPSerializationFormat;
import com.nosliw.common.utils.HAPJsonUtility;
import com.nosliw.data.core.expression.HAPExpressionDefinition;

/*
 * ui resource basic class for both ui resource and custom tag
 * it contains all the information after ui resource is compiled
 * it contains all the information within its domain
 * 		that means, for ui resource instance, it does not contains infor within customer tag
 */
@HAPEntityWithAttribute(baseName="UIRESOURCE")
public abstract class HAPUIResourceBasic extends HAPSerializableImp{

	@HAPAttribute
	public static final String ID = "id";
	@HAPAttribute
	public static final String TYPE = "type";
	@HAPAttribute
	public static final String CONTEXT = "context";
	@HAPAttribute
	public static final String EXPRESSIONCONTENTS = "expressionContents";
	@HAPAttribute
	public static final String EXPRESSIONATTRIBUTES = "expressionAttributes";
	@HAPAttribute
	public static final String EXPRESSIONTAGATTRIBUTES = "expressionTagAttributes";
	@HAPAttribute
	public static final String ELEMENTEVENTS = "elementEvents";
	@HAPAttribute
	public static final String TAGEVENTS = "tagEvents";
	@HAPAttribute
	public static final String UITAGS = "uiTags";
	@HAPAttribute
	public static final String ATTRIBUTES = "attributes";
	@HAPAttribute
	public static final String HTML = "html";
	@HAPAttribute
	public static final String SCRIPTFACTORYNAME = "scriptFactoryName";
	@HAPAttribute
	public static final String TAGNAME = "tagName";
	@HAPAttribute
	public static final String DATABINDINGS = "dataBindings";
	@HAPAttribute
	public static final String UITAGLIBS = "uiTagLibs";
	@HAPAttribute
	public static final String CONSTANTS = "constants";
	@HAPAttribute
	public static final String SCRIPTS = "scripts";
	@HAPAttribute
	public static final String EXPRESSIONS = "expressions";

	
	//for tag, it is tag id within resource
	//for resource, it is resource name
	private String m_id;
	
	//input data context information
	private Map<String, HAPContextElement> m_context;
	
	//all the expressions within content under this domain
	private Set<HAPUIExpressionContent> m_expressionContents;
	//all the attribute expressions in regular tag under this domain 
	private Set<HAPUIExpressionAttribute> m_expressionAttributes;
	//all the attribute expressions in customer tag under this domain
	private Set<HAPUIExpressionAttribute> m_expressionTagAttributes;
	
	//all the events related with regular tag
	private Set<HAPElementEvent> m_elementEvents;
	//all the events related with customer tag
	private Set<HAPElementEvent> m_tagEvents;
	
	//store all the attribute for this domain
	//for customer tag, they are the tag's attribute
	//for resource, they are the attribute of body
	private Map<String, String> m_attributes;
	
	//all java script blocks within this domain
	private List<HAPScript> m_scripts;
	
	//a set of named data that can be used as constants
	private Map<String, HAPConstantDef> m_constants;
	
	//all the customer tag within the domain
	private Map<String, HAPUITag> m_uiTags; 
	
	//html content after compilation
	private String m_content; 

	//the script factory name for creating script object for ui resource view
	private String m_scriptFactoryName;
	
	private Map<String, HAPExpressionDefinition> m_expressionDefinitions;
	
	public HAPUIResourceBasic(String id){
		this.m_id = id;
		this.m_context = new LinkedHashMap<String, HAPContextElement>();
		this.m_expressionAttributes = new HashSet<HAPUIExpressionAttribute>();
		this.m_expressionTagAttributes = new HashSet<HAPUIExpressionAttribute>();
		this.m_expressionContents = new HashSet<HAPUIExpressionContent>();
		this.m_uiTags = new LinkedHashMap<String, HAPUITag>();
		this.m_scripts = new ArrayList<HAPScript>();
		this.m_elementEvents = new HashSet<HAPElementEvent>();
		this.m_tagEvents = new HashSet<HAPElementEvent>();
		this.m_attributes = new LinkedHashMap<String, String>();
		this.m_constants = new LinkedHashMap<String, HAPConstantDef>();
		this.m_expressionDefinitions = new LinkedHashMap<String, HAPExpressionDefinition>();
	}
	
	abstract public String getType(); 
	
	
	
	protected void processConstants(){
		
	}
	
	protected void processExpressions(){
		
	}
	
	
	@Override
	public String toString(){
		return HAPJsonUtility.formatJson(this.toStringValue(HAPSerializationFormat.JSON));
	}

	@Override
	protected void buildFullJsonMap(Map<String, String> jsonMap, Map<String, Class<?>> typeJsonMap){
		jsonMap.put(ID, this.m_id);
		jsonMap.put(TYPE, String.valueOf(this.getType()));
		jsonMap.put(CONTEXT, HAPJsonUtility.buildJson(m_context, HAPSerializationFormat.JSON_FULL));

		List<String> expressionContentJsons = new ArrayList<String>();
		for(HAPUIExpressionContent expressionContent : this.m_expressionContents)  expressionContentJsons.add(expressionContent.toStringValue(HAPSerializationFormat.JSON_FULL));
		jsonMap.put(EXPRESSIONCONTENTS, HAPJsonUtility.buildArrayJson(expressionContentJsons.toArray(new String[0])));
		
		List<String> expressionAttributeJsons = new ArrayList<String>();
		for(HAPUIExpressionAttribute expressionAttr : this.m_expressionAttributes)  expressionAttributeJsons.add(expressionAttr.toStringValue(HAPSerializationFormat.JSON_FULL));
		jsonMap.put(EXPRESSIONATTRIBUTES, HAPJsonUtility.buildArrayJson(expressionAttributeJsons.toArray(new String[0])));

		List<String> expressionTagAttributeJsons = new ArrayList<String>();
		for(HAPUIExpressionAttribute expressionTagAttr : this.m_expressionTagAttributes)  expressionTagAttributeJsons.add(expressionTagAttr.toStringValue(HAPSerializationFormat.JSON_FULL));
		jsonMap.put(EXPRESSIONTAGATTRIBUTES, HAPJsonUtility.buildArrayJson(expressionTagAttributeJsons.toArray(new String[0])));

		List<String> eleEventsJsons = new ArrayList<String>();
		for(HAPElementEvent elementEvent : this.m_elementEvents)  eleEventsJsons.add(elementEvent.toStringValue(HAPSerializationFormat.JSON_FULL));
		jsonMap.put(ELEMENTEVENTS, HAPJsonUtility.buildArrayJson(eleEventsJsons.toArray(new String[0])));
		
		List<String> tagEvents = new ArrayList<String>();
		for(HAPElementEvent tagEvent : m_tagEvents)		tagEvents.add(tagEvent.toStringValue(HAPSerializationFormat.JSON_FULL));
		jsonMap.put(TAGEVENTS, HAPJsonUtility.buildArrayJson(tagEvents.toArray(new String[0])));
		
		Map<String, String> uiTagJsons = new LinkedHashMap<String, String>();
		for(String uiId : this.m_uiTags.keySet())	uiTagJsons.put(uiId, this.m_uiTags.get(uiId).toStringValue(HAPSerializationFormat.JSON_FULL));
		jsonMap.put(UITAGS, HAPJsonUtility.buildMapJson(uiTagJsons));
		
		jsonMap.put(ATTRIBUTES, HAPJsonUtility.buildMapJson(this.m_attributes));
		
		String htmlContent = StringEscapeUtils.escapeHtml4(this.getContent());
		jsonMap.put(HTML, htmlContent);
		
		jsonMap.put(SCRIPTFACTORYNAME, this.m_scriptFactoryName);

		Map<String, String> constantsJsons = new LinkedHashMap<String, String>();
		for(String name : this.m_constants.keySet()){
			constantsJsons.put(name, this.m_constants.get(name).toStringValue(HAPSerializationFormat.JSON_FULL));
		}
		jsonMap.put(CONSTANTS, HAPJsonUtility.buildMapJson(constantsJsons));
	
	}
	
	
	public String getId(){return this.m_id;}
	public String getContent(){return this.m_content;}
	public void setContent(String content){	this.m_content = content;	}
	
	public void addContextElement(HAPContextElement contextElement){this.m_context.put(contextElement.getName(), contextElement);	}
	public void addExpressionAttribute(HAPUIExpressionAttribute eAttr){	this.m_expressionAttributes.add(eAttr);	}
	public void addExpressionTagAttribute(HAPUIExpressionAttribute eAttr){	this.m_expressionTagAttributes.add(eAttr);	}
	public void addExpressionContent(HAPUIExpressionContent expressionContent){	this.m_expressionContents.add(expressionContent);	}
	public void addUITag(HAPUITag uiTag){	this.m_uiTags.put(uiTag.getId(), uiTag);	}
	public void addElementEvent(HAPElementEvent event){this.m_elementEvents.add(event);}
	public void addTagEvent(HAPElementEvent event){this.m_tagEvents.add(event);}
	public void addJSBlock(HAPScript jsBlock){this.m_scripts.add(jsBlock);}
	public void setScriptFactoryName(String name){this.m_scriptFactoryName=name;}
	public void addConstant(String name, HAPConstantDef data){this.m_constants.put(name, data);}
	public Map<String, HAPConstantDef> getConstants(){return this.m_constants;}
	
	public List<HAPScript> getJSBlocks(){return this.m_scripts;}
	public Collection<HAPUITag> getUITags(){return this.m_uiTags.values();} 
	public Set<HAPUIExpressionContent> getExpressionContents(){return this.m_expressionContents;}
	public Set<HAPUIExpressionAttribute> getExpressionAttributes(){return this.m_expressionAttributes;}
	public Set<HAPUIExpressionAttribute> getExpressionTagAttributes(){return this.m_expressionTagAttributes;}

	public void addExpressionDefinition(HAPExpressionDefinition expressionDef){		this.m_expressionDefinitions.put(expressionDef.getName(), expressionDef);	}
	
	/*
	 * process attributes
	 */
	public void addAttribute(String name, String value){
		this.m_attributes.put(name, value);
	}

	public void postRead(){
	}
}
