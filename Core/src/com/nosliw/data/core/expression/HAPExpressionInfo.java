package com.nosliw.data.core.expression;

import java.util.Map;

import com.nosliw.common.constant.HAPAttribute;
import com.nosliw.data.core.HAPData;
import com.nosliw.data.core.HAPDataTypeCriteria;
import com.nosliw.data.core.HAPInfo;

/**
 * ExpressionInfo is the basic unit to define a expression 
 * All information required when describe a expression
 */
public interface HAPExpressionInfo {

	@HAPAttribute
	public static String NAME = "name";

	@HAPAttribute
	public static String INFO = "info";

	@HAPAttribute
	public static String EXPRESSION = "expression";

	@HAPAttribute
	public static String CONSTANTS = "constants";
	
	@HAPAttribute
	public static String VARIABLES = "variables";
	
	@HAPAttribute
	public static String REFERENCES = "references";

	//expression name, should be unique
	String getName();
	
	//related information, for instance, description, 
	HAPInfo getInfo();
	
	//expression as string
	String getExpression();
	
	//constants definition
	Map<String, HAPData> getConstants();
	
	//variables definition
	Map<String, HAPDataTypeCriteria> getVariables();
	
	//references definition
	Map<String, HAPReferenceInfo> getReferences();
}
