package com.nosliw.data.core.service;

import java.util.LinkedHashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import com.nosliw.common.constant.HAPAttribute;
import com.nosliw.common.constant.HAPEntityWithAttribute;
import com.nosliw.common.serialization.HAPSerializableImp;
import com.nosliw.common.serialization.HAPSerializationFormat;

@HAPEntityWithAttribute
public class HAPInterfaceDefinitionService extends HAPSerializableImp{

	@HAPAttribute
	public static String PARM = "parm";
	
	@HAPAttribute
	public static String RESULT = "result";
	
	//service input parms
	private Map<String, HAPDefinitionServiceParm> m_parms;
	
	//service output
	private Map<String, HAPDefinitionServiceResult> m_results;
	

	public HAPInterfaceDefinitionService() {
		this.m_parms = new LinkedHashMap<String, HAPDefinitionServiceParm>();
		this.m_results = new LinkedHashMap<String, HAPDefinitionServiceResult>();
	}

	public Map<String, HAPDefinitionServiceParm> getParms(){  return this.m_parms;   }
	public void addParm(String name, HAPDefinitionServiceParm parm) { this.m_parms.put(name, parm);  }
	
	public Map<String, HAPDefinitionServiceResult> getResults(){ return this.m_results;  }
	public void addResult(String name, HAPDefinitionServiceResult result) {  this.m_results.put(name, result);  }
	
	@Override
	protected boolean buildObjectByJson(Object json){
		try{
			JSONObject objJson = (JSONObject)json;
			
			JSONArray resultArray = objJson.getJSONArray(RESULT);
			for(int i = 0; i<resultArray.length(); i++){
				HAPDefinitionServiceResult resultEle = new HAPDefinitionServiceResult();
				resultEle.buildObject(resultArray.get(i), HAPSerializationFormat.JSON);
				this.m_results.put(resultEle.getName(), resultEle);
			}
			
			JSONArray parmsArray = objJson.getJSONArray(PARM);
			for(int i = 0; i<parmsArray.length(); i++){
				HAPDefinitionServiceParm parm = new HAPDefinitionServiceParm();
				parm.buildObject(parmsArray.get(i), HAPSerializationFormat.JSON);
				this.m_parms.put(parm.getName(), parm);
			}
		}
		catch(Exception e){
			e.printStackTrace();
			return false;
		}
		return true;  
	}
	
}
