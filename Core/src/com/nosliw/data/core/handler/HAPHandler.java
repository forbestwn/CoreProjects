package com.nosliw.data.core.handler;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import com.nosliw.common.constant.HAPAttribute;
import com.nosliw.common.constant.HAPEntityWithAttribute;
import com.nosliw.common.info.HAPEntityInfoImp;
import com.nosliw.common.serialization.HAPJsonUtility;
import com.nosliw.common.serialization.HAPSerializationFormat;

@HAPEntityWithAttribute
public class HAPHandler extends HAPEntityInfoImp{

	@HAPAttribute
	public static String STEPS = "steps";

	private List<HAPHandlerStep> m_steps;
	
	public HAPHandler() {
		this.m_steps = new ArrayList<HAPHandlerStep>();
	}
	
	public void addStep(HAPHandlerStep step) {    this.m_steps.add(step);     }
	public List<HAPHandlerStep> getSteps(){   return this.m_steps;  	}

	@Override
	protected void buildJsonMap(Map<String, String> jsonMap, Map<String, Class<?>> typeJsonMap){
		super.buildJsonMap(jsonMap, typeJsonMap);
		jsonMap.put(STEPS, HAPJsonUtility.buildJson(this.m_steps, HAPSerializationFormat.JSON));
	}

	@Override
	protected boolean buildObjectByJson(Object json){
		JSONObject jsonObj = (JSONObject)json;
		this.buildEntityInfoByJson(jsonObj);
		JSONArray stepsArray = jsonObj.getJSONArray(STEPS);
		for(int i=0; i<stepsArray.length(); i++) {
			this.m_steps.add(HAPParserHandlerStep.parse(stepsArray.getJSONObject(i)));
		}
		
		return true;  
	}
	
	public HAPHandler cloneHandler() {
		HAPHandler out = new HAPHandler();
		for(HAPHandlerStep step : this.m_steps) {
			out.addStep(step.cloneHandlerStep());
		}
		return out;
	}
}
