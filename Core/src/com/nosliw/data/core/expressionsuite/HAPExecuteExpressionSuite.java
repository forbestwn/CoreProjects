package com.nosliw.data.core.expressionsuite;

import java.util.LinkedHashMap;
import java.util.Map;

import com.nosliw.common.constant.HAPAttribute;
import com.nosliw.common.constant.HAPEntityWithAttribute;
import com.nosliw.common.serialization.HAPJsonUtility;
import com.nosliw.common.serialization.HAPSerializableImp;
import com.nosliw.common.serialization.HAPSerializationFormat;
import com.nosliw.common.serialization.HAPSerializeManager;
import com.nosliw.data.core.HAPDataTypeHelper;
import com.nosliw.data.core.criteria.HAPDataTypeCriteria;
import com.nosliw.data.core.expression.HAPMatchers;
import com.nosliw.data.core.expression.HAPVariableInfo;
import com.nosliw.data.core.operand.HAPOperand;
import com.nosliw.data.core.operand.HAPOperandUtility;
import com.nosliw.data.core.operand.HAPOperandWrapper;
import com.nosliw.data.core.runtime.HAPExecuteExpression;

@HAPEntityWithAttribute(baseName="EXPRESSION")
public class HAPExecuteExpressionSuite extends HAPSerializableImp implements HAPExecuteExpression{

	@HAPAttribute
	public static String ID = "id";
	
	@HAPAttribute
	public static String OPERAND = "operand";
	
	@HAPAttribute
	public static String VARIABLEINFOS = "variableInfos";
	
	@HAPAttribute
	public static String VARIABLESMATCHERS = "variablesMatchers";
	
	private String m_id;
	
	private HAPOperandWrapper m_operand;
	
	private Map<String, HAPVariableInfo> m_localVarsInfo;

	private Map<String, HAPMatchers> m_varsMatchers;
	
	public HAPExecuteExpressionSuite(String id, HAPOperand operand) {
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
			HAPDataTypeCriteria expectOutput) {

		Map<String, HAPVariableInfo> discoveredVarsInf = HAPOperandUtility.discover(this.getOperand().getOperand(), parentVariablesInfo, expectOutput);
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
		jsonMap.put(ID, this.getId());
		jsonMap.put(OPERAND, HAPSerializeManager.getInstance().toStringValue(this.m_operand, HAPSerializationFormat.JSON));
		jsonMap.put(VARIABLESMATCHERS, HAPJsonUtility.buildJson(this.m_varsMatchers, HAPSerializationFormat.JSON));
		jsonMap.put(VARIABLEINFOS, HAPJsonUtility.buildJson(this.m_localVarsInfo, HAPSerializationFormat.JSON));

	}
}