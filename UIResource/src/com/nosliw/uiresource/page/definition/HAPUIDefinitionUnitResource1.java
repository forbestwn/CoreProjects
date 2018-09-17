package com.nosliw.uiresource.page.definition;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.nosliw.common.constant.HAPAttribute;
import com.nosliw.common.serialization.HAPJsonUtility;
import com.nosliw.common.serialization.HAPSerializationFormat;
import com.nosliw.common.utils.HAPConstant;
import com.nosliw.common.utils.HAPSegmentParser;
import com.nosliw.data.core.runtime.HAPResourceDependent;
import com.nosliw.data.core.script.context.HAPContextEntity;

public class HAPUIDefinitionUnitResource1 extends HAPDefinitionUIUnit{

	@HAPAttribute
	public static final String COMMANDS = "commands";
	
	//source code of resource definition
	private String m_source;
	
	private Map<String, HAPContextEntity> m_commandsDefinition;
	
	private boolean m_processed = false;
	
	//all dependency resources
	private List<HAPResourceDependent> m_resourceDependency;
	
	public HAPUIDefinitionUnitResource1(String id, String source){
		super(id);
		this.m_source = source;
		this.m_resourceDependency = new ArrayList<HAPResourceDependent>();
		this.m_commandsDefinition = new LinkedHashMap<String, HAPContextEntity>();
	}
	
	public String getSource(){   return this.m_source;   }
	
	public boolean isProcessed(){  return this.m_processed;  }
	public void processed(){  this.m_processed = true;  }
	
	public void addCommandDefinition(HAPContextEntity commandDef) {   this.m_commandsDefinition.put(commandDef.getName(), commandDef);   }
	public Map<String, HAPContextEntity> getCommandDefinition() {   return this.m_commandsDefinition;  }

	public List<HAPResourceDependent> getResourceDependency(){  return this.m_resourceDependency;  }

	@Override
	protected void buildFullJsonMap(Map<String, String> jsonMap, Map<String, Class<?>> typeJsonMap){
		super.buildFullJsonMap(jsonMap, typeJsonMap);
	}
	
	@Override
	protected void buildJsonMap(Map<String, String> jsonMap, Map<String, Class<?>> typeJsonMap){
		super.buildJsonMap(jsonMap, typeJsonMap);
		jsonMap.put(COMMANDS, HAPJsonUtility.buildJson(this.m_commandsDefinition, HAPSerializationFormat.JSON));
	}

	@Override
	public String getType() {
		return HAPConstant.UIRESOURCE_TYPE_RESOURCE;
	}

	@Override
	public void addAttribute(String name, String value){
		super.addAttribute(name, value);
		if(HAPConstant.UIRESOURCE_ATTRIBUTE_CONTEXT.equals(name)){
			//process "context" attribute, value are multiple data input seperated by ";"
			HAPSegmentParser contextSegs = new HAPSegmentParser(value, HAPConstant.SEPERATOR_ELEMENT);
			while(contextSegs.hasNext()){
				String varInfo = contextSegs.next();
				HAPSegmentParser varSegs = new HAPSegmentParser(varInfo, HAPConstant.SEPERATOR_DETAIL);
				String varName = varSegs.next();
				String varType = varSegs.next();
//				HAPUIResourceContextInfo contextEleInfo = new HAPUIResourceContextInfo(varName, varType);
//				this.addContextElement(contextEleInfo);
			}
		}
	}
}