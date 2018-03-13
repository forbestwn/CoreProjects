package com.nosliw.data.core.operand;

import java.util.Map;

import com.nosliw.common.constant.HAPAttribute;
import com.nosliw.common.serialization.HAPSerializationFormat;
import com.nosliw.common.serialization.HAPSerializeManager;
import com.nosliw.common.utils.HAPConstant;
import com.nosliw.common.utils.HAPProcessContext;
import com.nosliw.data.core.HAPData;
import com.nosliw.data.core.HAPDataTypeHelper;
import com.nosliw.data.core.HAPDataUtility;
import com.nosliw.data.core.criteria.HAPDataTypeCriteria;
import com.nosliw.data.core.expression.HAPMatchers;
import com.nosliw.data.core.expression.HAPVariableInfo;

public class HAPOperandConstant extends HAPOperandImp{

	@HAPAttribute
	public final static String NAME = "name"; 

	@HAPAttribute
	public final static String DATA = "data"; 
	
	protected HAPData m_data;

	protected String m_name;
	
	private HAPOperandConstant(){}
	
	public HAPOperandConstant(String constantStr){
		super(HAPConstant.EXPRESSION_OPERAND_CONSTANT);
		HAPData data = HAPDataUtility.buildDataWrapper(constantStr);
		if(data==null){
			//not a valid data literate, then it is a constant name
			this.m_name = constantStr;
		}
		else{
			this.setData(data);
		}
	}
	
	public String getName(){  return this.m_name;  }
	
	public HAPData getData(){  return this.m_data;  }
	
	public void setData(HAPData data){ 
		this.m_data = data;
	}
	
	@Override
	protected void buildJsonMap(Map<String, String> jsonMap, Map<String, Class<?>> typeJsonMap){
		super.buildJsonMap(jsonMap, typeJsonMap);
		jsonMap.put(NAME, m_name);
		jsonMap.put(DATA, HAPSerializeManager.getInstance().toStringValue(this.m_data, HAPSerializationFormat.JSON));
	}

	@Override
	public HAPMatchers discover(
			Map<String, HAPVariableInfo> variablesInfo,
			HAPDataTypeCriteria expectCriteria, 
			HAPProcessContext context,
			HAPDataTypeHelper dataTypeHelper) {
		//set output criteria
		if(this.getOutputCriteria()==null){
			HAPDataTypeCriteria criteria = dataTypeHelper.getDataTypeCriteriaByData(m_data);
			this.setOutputCriteria(criteria);
		}
		return this.isMatchable(this.getOutputCriteria(), expectCriteria, context, dataTypeHelper);
	}
	
	@Override
	public HAPOperand cloneOperand() {
		HAPOperandConstant out = new HAPOperandConstant();
		this.cloneTo(out);
		return out;
	}
	
	protected void cloneTo(HAPOperandConstant operand){
		super.cloneTo(operand);
		operand.m_name = this.m_name;
		operand.m_data = this.m_data;
		
	}
}