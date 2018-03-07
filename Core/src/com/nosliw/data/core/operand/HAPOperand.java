package com.nosliw.data.core.operand;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.nosliw.common.constant.HAPAttribute;
import com.nosliw.common.constant.HAPEntityWithAttribute;
import com.nosliw.common.serialization.HAPSerializable;
import com.nosliw.data.core.HAPDataTypeConverter;
import com.nosliw.data.core.HAPDataTypeHelper;
import com.nosliw.data.core.criteria.HAPDataTypeCriteria;
import com.nosliw.data.core.expression.HAPProcessExpressionDefinitionContext;
import com.nosliw.data.core.runtime.HAPResourceId;
import com.nosliw.data.core.task.HAPMatchers;
import com.nosliw.data.core.task.HAPProcessTaskContext;
import com.nosliw.data.core.task.HAPVariableInfo;

@HAPEntityWithAttribute(baseName="OPERAND")
public interface HAPOperand extends HAPSerializable{

	@HAPAttribute
	public static final String TYPE = "type"; 

	@HAPAttribute
	public static final String STATUS = "status"; 
	
	@HAPAttribute
	public static final String CHILDREN = "children"; 

	@HAPAttribute
	public static final String DATATYPEINFO = "dataTypeInfo"; 

	@HAPAttribute
	public static final String CONVERTERS = "converters"; 

	@HAPAttribute
	public static final String OUTPUTCRITERIA = "outputCriteria"; 

	//type of operand
	String getType();

	//children operand
	List<HAPOperandWrapper> getChildren();

	/**
	 * Try best to process operand in order to discovery
	 * 		Variables:	narrowest variable criteria
	 * 		Converter for the gap between output criteria and expect criteria
	 * 		Output criteria
	 * @param variablesInfo  all the variables info in context
	 * @param expectCriteria expected output criteria for this operand
	 * @return  matchers from output criteria to expect criteria
	 */
	HAPMatchers discover(Map<String, HAPVariableInfo> variablesInfo,
										HAPDataTypeCriteria expectCriteria,
										HAPProcessTaskContext context, 
										HAPDataTypeHelper dataTypeHelper);

	//operand output data type criteria
	HAPDataTypeCriteria getOutputCriteria();
	
	//status of operand: new, processed, failed
	String getStatus();
	
	List<HAPResourceId> getResources();
	
	//get all the convertor required by this operand
	Set<HAPDataTypeConverter> getConverters();  
	
	HAPOperand cloneOperand();
}
