package com.nosliw.data.core.process;

import java.util.Map;

import org.json.JSONObject;

import com.nosliw.common.constant.HAPAttribute;
import com.nosliw.common.constant.HAPEntityWithAttribute;
import com.nosliw.common.info.HAPEntityInfoWritableImp;
import com.nosliw.common.serialization.HAPSerializationFormat;
import com.nosliw.data.core.script.context.HAPUtilityContext;

@HAPEntityWithAttribute
public class HAPDefinitionResultActivityNormal extends HAPEntityInfoWritableImp{

	@HAPAttribute
	public static String FLOW = "flow";

	@HAPAttribute
	public static String OUTPUT = "output";
	
	//associate variable in process to input required by activity 
	private HAPDefinitionDataAssociationGroup m_output;
	
	//next activity
	private HAPDefinitionSequenceFlow m_flow;
	
	
	public HAPDefinitionSequenceFlow getFlow() {  return this.m_flow;  }
	
	public HAPDefinitionDataAssociationGroup getOutputDataAssociation() {   return this.m_output;   }
	
	@Override
	protected boolean buildObjectByJson(Object json){
		try{
			super.buildObjectByJson(json);
			JSONObject jsonObj = (JSONObject)json;

			this.m_flow = new HAPDefinitionSequenceFlow();
			this.m_flow.buildObject(jsonObj.optJSONObject(FLOW), HAPSerializationFormat.JSON);
			
			this.m_output = new HAPDefinitionDataAssociationGroup();
			this.m_output.buildObject(jsonObj.optJSONObject(OUTPUT), HAPSerializationFormat.JSON);
			
			//data association output should not be flat
			this.m_output.setIsFlatOutput(false);
			
			//no inherit
			HAPUtilityContext.setContextGroupInheritModeNone(this.m_output.getInfo());

			return true;  
		}
		catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}
	
	@Override
	protected void buildJsonMap(Map<String, String> jsonMap, Map<String, Class<?>> typeJsonMap) {
		super.buildJsonMap(jsonMap, typeJsonMap);
		jsonMap.put(FLOW, m_flow.toStringValue(HAPSerializationFormat.JSON));
		jsonMap.put(OUTPUT, m_output.toStringValue(HAPSerializationFormat.JSON));
	}
}
