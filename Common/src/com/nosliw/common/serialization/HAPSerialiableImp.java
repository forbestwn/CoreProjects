package com.nosliw.common.serialization;

import java.util.LinkedHashMap;
import java.util.Map;

import com.nosliw.common.utils.HAPJsonUtility;

public abstract class HAPSerialiableImp implements HAPSerializable{

	@Override
	public String toString(){
		String out = "";
		try{
			out = this.toStringValue(HAPSerializationFormat.JSON_FULL);
			out = HAPJsonUtility.formatJson(out);
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return out;
	}
	
	@Override
	public String toStringValue(HAPSerializationFormat format) {
		if(format==null) format = HAPSerializationFormat.JSON_FULL;
		
		String out = null;
		switch(format){
		case JSON_FULL:
			out = this.buildFullJson();
			if(out==null){
				Map<String, String> outJsonMap = new LinkedHashMap<String, String>();
				Map<String, Class<?>> typeJsonMap = new LinkedHashMap<String, Class<?>>();
				this.buildFullJsonMap(outJsonMap, typeJsonMap);
				out = HAPJsonUtility.buildMapJson(outJsonMap, typeJsonMap);
			}
			break;
		case JSON:
			out = this.buildJson();
			if(out==null){
				Map<String, String> outJsonMap = new LinkedHashMap<String, String>();
				Map<String, Class<?>> typeJsonMap = new LinkedHashMap<String, Class<?>>();
				this.buildJsonMap(outJsonMap, typeJsonMap);
				out = HAPJsonUtility.buildMapJson(outJsonMap, typeJsonMap);
			}
			break;
		case XML:
			break;
		case LITERATE:
			break;
		}
		return out;
	}

	protected String buildFullJson(){ return null; }
	protected void buildFullJsonMap(Map<String, String> jsonMap, Map<String, Class<?>> typeJsonMap){}

	protected String buildJson(){ return null; }
	protected void buildJsonMap(Map<String, String> jsonMap, Map<String, Class<?>> typeJsonMap){
		this.buildFullJsonMap(jsonMap, typeJsonMap);
	}	
}
