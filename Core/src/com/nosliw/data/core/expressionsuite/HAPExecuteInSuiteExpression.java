package com.nosliw.data.core.expressionsuite;

import java.util.LinkedHashMap;
import java.util.Map;

import com.nosliw.common.constant.HAPAttribute;
import com.nosliw.common.constant.HAPEntityWithAttribute;
import com.nosliw.common.serialization.HAPJsonUtility;
import com.nosliw.common.serialization.HAPSerializableImp;
import com.nosliw.common.serialization.HAPSerializationFormat;
import com.nosliw.common.serialization.HAPSerializeManager;
import com.nosliw.common.utils.HAPProcessContext;
import com.nosliw.data.core.criteria.HAPDataTypeCriteria;
import com.nosliw.data.core.expression.HAPMatchers;
import com.nosliw.data.core.expression.HAPVariableInfo;
import com.nosliw.data.core.operand.HAPOperand;
import com.nosliw.data.core.operand.HAPOperandUtility;
import com.nosliw.data.core.operand.HAPOperandWrapper;
import com.nosliw.data.core.runtime.HAPExecuteExpression;

@HAPEntityWithAttribute(baseName="EXPRESSION")
public class HAPExecuteInSuiteExpression extends HAPSerializableImp implements HAPExecuteExpression{

	@HAPAttribute
	public static String VARIABLEINFOS = "variableInfos";
	
	private String m_id;
	
	private HAPOperandWrapper m_operand;
	
	private Map<String, HAPVariableInfo> m_localVarsInfo;

	private Map<String, HAPMatchers> m_varsMatchers;
	
	public HAPExecuteInSuiteExpression(String id, HAPOperand operand) {
		this.m_operand = new HAPOperandWrapper(operand);
		this.m_id = id;
		this.m_localVarsInfo = new LinkedHashMap<String, HAPVariableInfo>();
		this.m_varsMatchers = new LinkedHashMap<String, HAPMatchers>();
	}
	
	@Override
	public HAPOperandWrapper getOperand() {		return this.m_operand;	}

	@Override
	public Map<String, HAPMatchers> getVariableMatchers() {		return this.m_varsMatchers;	}

	public void discover(
			Map<String, HAPVariableInfo> parentVariablesInfo, 
			HAPDataTypeCriteria expectOutput,
			HAPProcessContext context) {

		Map<String, HAPVariableInfo> discoveredVarsInf = HAPOperandUtility.discover(new HAPOperand[]{this.getOperand().getOperand()}, parentVariablesInfo, expectOutput, context);
		parentVariablesInfo.clear();
		parentVariablesInfo.putAll(discoveredVarsInf);
		this.m_localVarsInfo.clear();
		this.m_localVarsInfo.putAll(discoveredVarsInf);
		
	}

	@Override
	public String getId() {		return this.m_id;	}
	
	@Override
	protected boolean buildObjectByJson(Object json){
		return true;
	}

	@Override
	protected void buildJsonMap(Map<String, String> jsonMap, Map<String, Class<?>> typeJsonMap) {
		super.buildJsonMap(jsonMap, typeJsonMap);
		HAPExecuteExpression.buildJsonMap(this, jsonMap, typeJsonMap);
		jsonMap.put(VARIABLEINFOS, HAPJsonUtility.buildJson(this.m_localVarsInfo, HAPSerializationFormat.JSON));

	}
}