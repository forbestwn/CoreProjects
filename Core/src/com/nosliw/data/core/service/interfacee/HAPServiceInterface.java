package com.nosliw.data.core.service.interfacee;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import org.json.JSONObject;

import com.nosliw.common.constant.HAPAttribute;
import com.nosliw.common.constant.HAPEntityWithAttribute;
import com.nosliw.common.serialization.HAPJsonUtility;
import com.nosliw.common.serialization.HAPSerializableImp;
import com.nosliw.common.serialization.HAPSerializationFormat;

@HAPEntityWithAttribute
public class HAPServiceInterface extends HAPSerializableImp{

	@HAPAttribute
	public static String PARM = "parm";
	
	@HAPAttribute
	public static String RESULT = "result";
	
	//service input parms
	private Map<String, HAPServiceParm> m_parms;
	
	//service output
	private Map<String, HAPServiceResult> m_results;
	

	public HAPServiceInterface() {
		this.m_parms = new LinkedHashMap<String, HAPServiceParm>();
		this.m_results = new LinkedHashMap<String, HAPServiceResult>();
	}

	public Set<String> getParmNames(){  return this.m_parms.keySet();   }
	public HAPServiceParm getParm(String name) {  return this.m_parms.get(name);    }

	public void addParm(HAPServiceParm parm) { this.m_parms.put(parm.getId(), parm);  }
	
	public Map<String, HAPServiceResult> getResults(){ return this.m_results;  }
	public HAPServiceResult getResult(String result) {   return this.m_results.get(result);  }
	public Map<String, HAPServiceOutput> getResultOutput(String result) {  return this.getResult(result).getOutput();  }
	public void addResult(String name, HAPServiceResult result) {  this.m_results.put(name, result);  }
	
	@Override
	protected boolean buildObjectByJson(Object json){
		try{
			JSONObject objJson = (JSONObject)json;
			
			JSONObject resultObject = objJson.getJSONObject(RESULT);
			for(Object key : resultObject.keySet()) {
				String name = (String)key;
				HAPServiceResult resultEle = new HAPServiceResult();
				resultEle.buildObject(resultObject.get(name), HAPSerializationFormat.JSON);
				resultEle.setName(name);
				this.m_results.put(resultEle.getName(), resultEle);
			}
			
			JSONObject parmsObject = objJson.getJSONObject(PARM);
			for(Object key : parmsObject.keySet()) {
				String name = (String)key;
				HAPServiceParm parm = new HAPServiceParm();
				parm.buildObject(parmsObject.get(name), HAPSerializationFormat.JSON);
				parm.setName(name);
				this.addParm(parm);
			}
		}
		catch(Exception e){
			e.printStackTrace();
			return false;
		}
		return true;  
	}
	
	@Override
	protected void buildJsonMap(Map<String, String> jsonMap, Map<String, Class<?>> typeJsonMap) {
		super.buildJsonMap(jsonMap, typeJsonMap);
		jsonMap.put(PARM, HAPJsonUtility.buildJson(this.m_parms, HAPSerializationFormat.JSON));
		jsonMap.put(RESULT, HAPJsonUtility.buildJson(this.m_results, HAPSerializationFormat.JSON));
	}
}
