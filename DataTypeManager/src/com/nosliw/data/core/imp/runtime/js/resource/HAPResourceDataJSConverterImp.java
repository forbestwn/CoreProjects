package com.nosliw.data.core.imp.runtime.js.resource;

import com.nosliw.common.strvalue.HAPStringableValueEntityWithID;
import com.nosliw.data.core.HAPDataTypeId;
import com.nosliw.data.core.runtime.js.resource.HAPResourceDataJSConverter;

public class HAPResourceDataJSConverterImp extends HAPStringableValueEntityWithID implements HAPResourceDataJSConverter{

	public static String _VALUEINFO_NAME;
	
	public HAPResourceDataJSConverterImp(){}
	
	public HAPResourceDataJSConverterImp(String script, HAPDataTypeId dataTypeName, String conerterType){
		this.setValue(script);
		this.setConverterType(conerterType);
		this.setDataTypeName(dataTypeName);
	}
	
	@Override
	public HAPDataTypeId getDataTypeName() {	return (HAPDataTypeId)this.getAtomicAncestorValueObject(DATATYPENAME, HAPDataTypeId.class);	}
	public void setDataTypeName(HAPDataTypeId dataTypeName){ this.updateAtomicChildObjectValue(DATATYPENAME, dataTypeName); }

	@Override
	public String getConverterType() {  return this.getAtomicAncestorValueString(CONVERTERTYPE); }
	public void setConverterType(String type){  this.updateAtomicChildStrValue(CONVERTERTYPE, type);  }

	@Override
	public String getValue(){  return this.getAtomicAncestorValueString(VALUE);  }
	public void setValue(String value){  this.updateAtomicChildStrValue(VALUE, value);  }
}