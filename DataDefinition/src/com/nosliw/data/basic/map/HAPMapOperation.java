package com.nosliw.data.basic.map;

import com.nosliw.data.HAPData;
import com.nosliw.data.HAPDataOperation;
import com.nosliw.data.HAPDataType;
import com.nosliw.data.HAPDataTypeManager;
import com.nosliw.data.HAPOperationContext;
import com.nosliw.data.HAPOperationInfoAnnotation;
import com.nosliw.data.basic.string.HAPStringData;

public class HAPMapOperation extends HAPDataOperation{

	public HAPMapOperation(HAPDataTypeManager man, HAPDataType dataType) {
		super(man, dataType);
	}

	@HAPOperationInfoAnnotation(in = { "map:simple", "string:simple", "any" }, out = "map:simple", description = "")
	public HAPData put(HAPData[] parms, HAPOperationContext opContext){
		HAPMapData mapData = (HAPMapData)parms[0];
		HAPStringData nameData = (HAPStringData)parms[1];
		mapData.put(nameData.getValue(), parms[2]);
		return mapData;
	}

	@HAPOperationInfoAnnotation(in = {  }, out = "map:simple", description = "")
	public HAPData newMap(HAPData[] parms, HAPOperationContext opContext){
		HAPMap mapDataType = (HAPMap)this.getDataType();
		return mapDataType.newMap();
	}
}
