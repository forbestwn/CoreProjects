package com.nosliw.data;

import java.util.Arrays;

import com.nosliw.common.exception.HAPServiceData;
import com.nosliw.common.utils.HAPBasicUtility;
import com.nosliw.data.info.HAPDataTypeInfo;

/*
 * utility class for data type operation object
 */
public class HAPDataOperation {

	private HAPDataTypeManager m_dataTypeMan;
	private HAPDataType m_dataType;
	
	public HAPDataOperation(HAPDataTypeManager man, HAPDataType dataType){
		this.m_dataTypeMan = man;
		this.m_dataType = dataType;
	}
	
	protected HAPDataType getDataType(String categary, String type){
		return this.m_dataTypeMan.getDataType(new HAPDataTypeInfo(categary, type));
	}
	
	protected HAPDataType getDataType(){
		return this.m_dataType;
	}

	protected HAPDataTypeManager getDataTypeManager(){
		return this.m_dataTypeMan;
	}
	
	protected HAPServiceData dataOperate(HAPData masterData, String operation, HAPData[] parms){
		return this.dataOperate(masterData.getDataType(), operation, parms);
	}
	
	protected HAPServiceData dataOperate(String categary, String type, String operation, HAPData[] parms){
		return this.dataOperate(this.getDataType(categary, type), operation, parms);
	}
	
	protected HAPServiceData dataOperate(HAPDataType dataType, String operation, HAPData[] parms){
		HAPServiceData serviceData = dataType.operate(operation, parms);
		return serviceData;
	}
}
