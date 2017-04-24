package com.nosliw.data.core.runtime;

import com.nosliw.common.constant.HAPAttribute;
import com.nosliw.data.core.HAPDataTypeId;
import com.nosliw.data.core.runtime.js.HAPResourceDataJSValue;

public interface HAPResourceDataOperation extends HAPResourceDataJSValue{

	@HAPAttribute
	public static String OPERATIONID = "operationId";

	@HAPAttribute
	public static String OPERATIONNAME = "operationName";
	
	@HAPAttribute
	public static String DATATYPENAME = "dataTypeName";
	
	String getOperationId();
	
	String getOperationName();
	
	HAPDataTypeId getDataTypeName();
	
}