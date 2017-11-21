package com.nosliw.uiresource.tag;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.nosliw.common.constant.HAPAttribute;
import com.nosliw.common.constant.HAPEntityWithAttribute;
import com.nosliw.common.serialization.HAPSerializableImp;
import com.nosliw.common.serialization.HAPSerializationFormat;
import com.nosliw.common.utils.HAPJsonUtility;
import com.nosliw.data.core.runtime.HAPResourceDependent;
import com.nosliw.data.core.runtime.HAPResourceId;

@HAPEntityWithAttribute
public class HAPUITagDefinition extends HAPSerializableImp{

	@HAPAttribute
	public static final String NAME = "name";
	@HAPAttribute
	public static final String SCRIPT = "script";
	@HAPAttribute
	public static final String ATTRIBUTES = "attributes";
	@HAPAttribute
	public static final String CONTEXT = "context";
	@HAPAttribute
	public static final String REQUIRES = "requires";
	
	//tag id
	private HAPUITagId m_name;
	
	//javascript
	private String m_script;

	//attribute definition
	private Map<String, HAPUITagDefinitionAttribute> m_attributes;
	
	//context definition
	private HAPUITagDefinitionContext m_context;

	//dependency resources
	private List<HAPResourceDependent> m_resourceDependency;
	
	public HAPUITagDefinition(HAPUITagId name, String script){
		this.m_name = name;
		this.m_script = script;
		this.m_attributes = new LinkedHashMap<String, HAPUITagDefinitionAttribute>();
		this.m_context = new HAPUITagDefinitionContext();
		this.m_resourceDependency = new ArrayList<HAPResourceDependent>();
	}
	
	public HAPUITagId getName(){return this.m_name;}
	
	public String getScript(){return this.m_script;}
	
	public HAPUITagDefinitionContext getContext(){  return this.m_context;   }

	public List<HAPResourceDependent> getResourceDependency(){   return this.m_resourceDependency;    }
	public void addResourceDependency(HAPResourceDependent dep){  this.m_resourceDependency.add(dep);  }
	
	@Override
	protected void buildFullJsonMap(Map<String, String> jsonMap, Map<String, Class<?>> typeJsonMap){
		jsonMap.put(NAME, this.m_name.toStringValue(HAPSerializationFormat.LITERATE));
//		jsonMap.put(SCRIPT, this.m_script);
//		typeJsonMap.put(SCRIPT, HAPJsonTypeAsItIs.class);
		jsonMap.put(CONTEXT, this.m_context.toStringValue(HAPSerializationFormat.JSON));
		jsonMap.put(ATTRIBUTES, HAPJsonUtility.buildJson(this.m_attributes, HAPSerializationFormat.JSON));
	}
	
}
