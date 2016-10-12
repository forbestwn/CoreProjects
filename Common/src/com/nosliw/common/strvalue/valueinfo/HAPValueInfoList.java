package com.nosliw.common.strvalue.valueinfo;

import com.nosliw.common.strvalue.HAPStringableValue;
import com.nosliw.common.strvalue.HAPStringableValueList;
import com.nosliw.common.utils.HAPConstant;

public class HAPValueInfoList extends HAPValueInfoContainer{

	private HAPValueInfoList(){}
	
	public static HAPValueInfoList build(){
		HAPValueInfoList out = new HAPValueInfoList();
		out.init();
		return out;
	}
	
	@Override
	public HAPValueInfoList clone(){
		HAPValueInfoList out = new HAPValueInfoList();
		out.cloneFrom(this);
		return out;
	}
	
	@Override
	public String getCategary() {		return HAPConstant.STRINGALBE_VALUEINFO_LIST;	}

	@Override
	public HAPStringableValue buildDefault() {
		return new HAPStringableValueList();
	}

}
