package com.nosliw.data.core.dataassociation;

import java.util.LinkedHashMap;
import java.util.Map;

import org.json.JSONObject;

import com.nosliw.common.info.HAPUtilityEntityInfo;
import com.nosliw.common.serialization.HAPJsonUtility;
import com.nosliw.common.serialization.HAPSerializationFormat;
import com.nosliw.data.core.runtime.HAPExecutableImpEntityInfo;
import com.nosliw.data.core.runtime.HAPRuntimeInfo;
import com.nosliw.data.core.valuestructure.HAPContainerStructure;

public abstract class HAPExecutableDataAssociationImp  extends HAPExecutableImpEntityInfo implements HAPExecutableDataAssociation{

	private HAPContainerStructure m_input;

	private String m_type;

	public HAPExecutableDataAssociationImp() {}
	
	public HAPExecutableDataAssociationImp(HAPDefinitionDataAssociation definition, HAPContainerStructure input) {
		super(definition);
		this.m_input = input;
		this.m_type = definition.getType();
	}
	
	@Override
	public String getType() {  return this.m_type;  }

	@Override
	public HAPContainerStructure getInput() {	return this.m_input;	}
	public void setInput(HAPContainerStructure input) {    this.m_input = input;    }

	@Override
	protected boolean buildObjectByJson(Object json){
		JSONObject jsonObj = (JSONObject)json;
		super.buildObjectByJson(json);
		this.m_type = jsonObj.getString(TYPE);
		this.m_input = new HAPContainerStructure();
		this.m_input.buildObject(jsonObj.getJSONObject(INPUT), HAPSerializationFormat.JSON);
		return true;  
	}

	@Override
	protected void buildJsonMap(Map<String, String> jsonMap, Map<String, Class<?>> typeJsonMap) {
		super.buildJsonMap(jsonMap, typeJsonMap);
		jsonMap.put(TYPE, this.getType());
		HAPUtilityEntityInfo.buildJsonMap(jsonMap, this);

		Map<String, String> outputFlatMap = new LinkedHashMap<String, String>();
		Map<String, Class<?>> outputFlatTypeMap = new LinkedHashMap<String, Class<?>>();
		for(String name : this.getOutput().getNames()) {
			outputFlatMap.put(name, this.getOutput().getOutputStructure(name).isFlat()+"");
			outputFlatTypeMap.put(name, Boolean.class);
		}
		jsonMap.put(OUTPUT, HAPJsonUtility.buildMapJson(outputFlatMap, outputFlatTypeMap));


		Map<String, String> inputFlatMap = new LinkedHashMap<String, String>();
		Map<String, Class<?>> inputFlatTypeMap = new LinkedHashMap<String, Class<?>>();
		for(String name : this.getInput().getStructureNames()) {
			inputFlatMap.put(name, this.m_input.getStructure(name).isFlat()+"");
			inputFlatTypeMap.put(name, Boolean.class);
		}
		jsonMap.put(INPUT, HAPJsonUtility.buildMapJson(inputFlatMap, inputFlatTypeMap));
	}

	@Override
	protected void buildResourceJsonMap(Map<String, String> jsonMap, Map<String, Class<?>> typeJsonMap, HAPRuntimeInfo runtimeInfo) {
		super.buildResourceJsonMap(jsonMap, typeJsonMap, runtimeInfo);
	}

}