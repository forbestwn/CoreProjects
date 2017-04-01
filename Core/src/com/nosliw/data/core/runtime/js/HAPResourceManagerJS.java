package com.nosliw.data.core.runtime.js;

import com.nosliw.common.pattern.HAPNamingConversionUtility;
import com.nosliw.common.serialization.HAPSerializationFormat;
import com.nosliw.common.utils.HAPConstant;
import com.nosliw.data.core.HAPDataTypeId;
import com.nosliw.data.core.HAPOperationId;
import com.nosliw.data.core.runtime.HAPResourceId;
import com.nosliw.data.core.runtime.HAPResourceManager;

public abstract class HAPResourceManagerJS  implements HAPResourceManager{

	public static HAPResourceId buildResourceIdObject(String literate){
		HAPResourceId out = new HAPResourceId(literate);
		
		switch(out.getType()){
		case HAPConstant.DATAOPERATION_RESOURCE_TYPE_DATATYPEOPERATION:
			out = new HAPResourceIdOperation(out);
			break;
		case HAPConstant.DATAOPERATION_RESOURCE_TYPE_DATATYPE:
			out = new HAPResourceIdDataType(out);
			break;
		case HAPConstant.DATAOPERATION_RESOURCE_TYPE_LIBRARY:
			out = new HAPResourceIdLibrary(out);
			break;
		case HAPConstant.DATAOPERATION_RESOURCE_TYPE_HELPER:
			out = new HAPResourceIdHelper(out);
			break;
		}
		return out;
	}
	
	public String buildResourceIdLiterate(HAPResourceId resourceId){
		return resourceId.toStringValue(HAPSerializationFormat.LITERATE);
	}
	
	public HAPResourceId buildResourceIdFromIdData(Object resourceIdData, String alias){
		HAPResourceId out = null;
		if(resourceIdData instanceof HAPOperationId){
        	out = new HAPResourceIdOperation((HAPOperationId)resourceIdData, alias);
		}
		else if(resourceIdData instanceof HAPDataTypeId){
        	out = new HAPResourceIdDataType((HAPDataTypeId)resourceIdData, alias);
		}
		else if(resourceIdData instanceof HAPJSLibraryId){
        	out = new HAPResourceIdLibrary((HAPJSLibraryId)resourceIdData, alias);
		}
		else if(resourceIdData instanceof String){
			out = new HAPResourceIdHelper((String)resourceIdData, alias);
		}
		return out;
	}
}
