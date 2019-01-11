package com.nosliw.data.core.service;

import java.util.LinkedHashMap;
import java.util.Map;

import org.json.JSONObject;

import com.nosliw.common.constant.HAPAttribute;
import com.nosliw.common.constant.HAPEntityWithAttribute;
import com.nosliw.common.info.HAPEntityInfoWritableImp;
import com.nosliw.common.serialization.HAPSerializeUtility;

@HAPEntityWithAttribute
public class HAPDefinitionServiceResult extends HAPEntityInfoWritableImp{

	@HAPAttribute
	public static String OUTPUT = "output";
	
	private Map<String, HAPDefinitionServiceOutput> m_output;
	
	public HAPDefinitionServiceResult(){
		this.m_output = new LinkedHashMap<String, HAPDefinitionServiceOutput>();
	}

	public void addOutput(String name, HAPDefinitionServiceOutput output) {   this.m_output.put(name, output);   }
	
	@Override
	protected boolean buildObjectByJson(Object json){
		try{
			JSONObject objJson = (JSONObject)json;
			super.buildObjectByJson(objJson);
			
			this.m_output.putAll(HAPSerializeUtility.buildMapFromJsonObject(HAPDefinitionServiceOutput.class.getName(), objJson.optJSONObject(OUTPUT)));
		}
		catch(Exception e){
			e.printStackTrace();
		}
		
		return true;  
	}
}
