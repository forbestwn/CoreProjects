package com.nosliw.common.strvalue.valueinfo;

import com.nosliw.common.path.HAPComplexName;
import com.nosliw.common.pattern.HAPNamingConversionUtility;
import com.nosliw.common.strvalue.HAPStringableValue;
import com.nosliw.common.strvalue.HAPStringableValueEntityBasic;
import com.nosliw.common.strvalue.HAPStringableValueList;
import com.nosliw.common.utils.HAPBasicUtility;
import com.nosliw.common.utils.HAPConstant;

public abstract class HAPValueInfo extends HAPStringableValueEntityBasic{

	public static final String TYPE = "type";

	public static final String DBCOLUMNINFOS = "dbColumnInfos";

	public static final String DBCOLUMNLINFO = "dbColumnInfo";
	
	abstract public HAPValueInfo clone();
	
	abstract public HAPStringableValue newValue();
	
	public HAPValueInfo(){
	}
	
	public HAPValueInfo getChildByPath(String path){
		HAPValueInfo out = this;
		String[] pathSegs = HAPNamingConversionUtility.parsePaths(path);
		for(String pathSeg: pathSegs){
			out = this.getElement(pathSeg);
		}
		return out;
	}
	
	protected HAPValueInfo getElement(String name){		return this;	}
	
	public HAPDBColumnsInfo getDBColumnsInfo(){		return (HAPDBColumnsInfo)this.getEntityAncestorByPath(DBCOLUMNINFOS);	}
	public void setDBColumnsInfo(HAPDBColumnsInfo columnsInfo){		this.updateChild(DBCOLUMNINFOS, columnsInfo);	}
	
	public String getValueInfoType(){	return this.getAtomicAncestorValueString(HAPValueInfo.TYPE);	}

	public HAPValueInfo getSolidValueInfo(){		return this;	}
	public String getSolidValueInfoType(){		return this.getValueInfoType();	}
	
	@Override
	public void init(){		super.init();	}

	protected HAPValueInfoManager getValueInfoManager(){ return HAPValueInfoManager.getInstance(); }
	
	protected void cloneFrom(HAPValueInfo valueInfo){		super.cloneFrom(valueInfo);	}
}
