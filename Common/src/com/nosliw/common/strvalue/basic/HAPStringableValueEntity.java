package com.nosliw.common.strvalue.basic;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import com.nosliw.common.strvalue.valueinfo.HAPValueInfoEntity;
import com.nosliw.common.strvalue.valueinfo.HAPValueInfoManager;
import com.nosliw.common.utils.HAPBasicUtility;
import com.nosliw.common.utils.HAPConstant;
import com.nosliw.common.utils.HAPJsonUtility;

/*
 * container for stringable value
 */
public class HAPStringableValueEntity extends HAPStringableValueComplex{

	private Map<String, HAPStringableValue> m_childrens;

	public HAPStringableValueEntity(){
		this.m_childrens = new LinkedHashMap<String, HAPStringableValue>();
	}

	@Override
	public Iterator<HAPStringableValue> iterate(){		return this.m_childrens.values().iterator();	}
	
	@Override
	public String getStringableCategary(){		return HAPConstant.STRINGABLE_VALUECATEGARY_ENTITY;	}
	
	public HAPStringableValueList getListChild(String name){
		HAPStringableValueList out = (HAPStringableValueList)this.getChild(name);
		if(out==null){
			out = new HAPStringableValueList();
			out = (HAPStringableValueList)this.updateChild(name, out);
		}
		return out;
	}
	
	public HAPStringableValueMap getMapChild(String name){
		HAPStringableValueMap out = (HAPStringableValueMap)this.getChild(name);
		if(out==null){
			out = new HAPStringableValueMap();
			out = (HAPStringableValueMap)this.updateChild(name, out);
		}
		return out;
	}
	
	@Override
	public HAPStringableValue getChild(String name){  return this.m_childrens.get(name);  }
	
	public HAPStringableValue updateChild(String name, HAPStringableValue entity){
		if(entity==null)    this.m_childrens.remove(name);
		else		this.m_childrens.put(name, entity);
		return entity;
	}

	public HAPStringableValueComplex updateComplexChild(String name, String type){
		HAPStringableValueComplex out = (HAPStringableValueComplex)this.getChild(name);
		if(out==null){
			out = HAPStringableValueUtility.newStringableValueComplex(type);
			if(out!=null){
				this.updateChild(name, out);
			}
		}
		return out;
	}
	
	public HAPStringableValueBasic updateBasicChild(String name, String strValue, String type){
		HAPStringableValueBasic out = null; 
		HAPStringableValue child = this.getChild(name);
		if(child==null || child.getStringableCategary().equals(HAPConstant.STRINGABLE_VALUECATEGARY_BASIC)){
			out = new HAPStringableValueBasic(strValue, type);
			this.m_childrens.put(name, out);
		}
		return out;
	}
	
	public void updateBasicChild(String name, String strValue){
		this.updateBasicChild(name, strValue, null);
	}

	public HAPStringableValueBasic updateBasicChildValue(String name, Object value){
		HAPStringableValueBasic out = null; 
		HAPStringableValue child = this.getChild(name);
		if(child==null || child.getStringableCategary().equals(HAPConstant.STRINGABLE_VALUECATEGARY_BASIC)){
			out = new HAPStringableValueBasic();
			out.setValue(value);
			this.m_childrens.put(name, out);
		}
		return out;
	}
	
	public void updateBasicChildrens(Map<String, String> values){
		for(String name : values.keySet()){
			this.updateBasicChild(name, values.get(name));
		}
	}
	
	public Set<String> getProperties(){		return this.m_childrens.keySet();	}

	@Override
	public HAPStringableValueEntity clone(){
		HAPStringableValueEntity out = new HAPStringableValueEntity();
		out.cloneFrom(this);
		return out;
	}
	
	protected void cloneFrom(HAPStringableValueEntity entity){
		for(String name : entity.m_childrens.keySet()){
			this.m_childrens.put(name, entity.m_childrens.get(name).clone());
		}
	}
	
	public HAPStringableValueEntity hardMerge(HAPStringableValueEntity entity){
		HAPStringableValueEntity out = this.clone();
		for(String attr : entity.m_childrens.keySet()){
			out.m_childrens.put(attr, entity.m_childrens.get(attr).clone());
		}
		return out;
	}
	
	public HAPStringableValueEntity hardMergeWith(HAPStringableValueEntity entity, Set<String> attrs){
		HAPStringableValueEntity out = this.clone();
		for(String attr : attrs){
			HAPStringableValue value = entity.m_childrens.get(attr);
			if(!HAPStringableValueUtility.isStringableValueEmpty(value)){
				out.m_childrens.put(attr, value.clone());
			}
		}
		return out;
	}

	public HAPStringableValueEntity hardMergeExcept(HAPStringableValueEntity entity, Set<String> attrs){
		HAPStringableValueEntity out = this.clone();
		for(String attr : entity.m_childrens.keySet()){
			if(!attrs.contains(attr)){
				out.m_childrens.put(attr, entity.m_childrens.get(attr).clone());
			}
		}
		return out;
	}
	
	@Override
	protected void buildFullJsonMap(Map<String, String> jsonMap, Map<String, Class> typeJsonMap, String format) {
		super.buildFullJsonMap(jsonMap, typeJsonMap, format);
		jsonMap.put(HAPAttributeConstant.STRINGABLEVALUE_PROPERTIES, HAPJsonUtility.getMapObjectJson(this.m_childrens, format));
	}

	@Override
	protected void buildJsonMap(Map<String, String> jsonMap, Map<String, Class> typeJsonMap, String format) {
		super.buildJsonMap(jsonMap, typeJsonMap, format);
		for(String child : this.m_childrens.keySet()){
			jsonMap.put(child, this.m_childrens.get(child).toStringValue(format));
		}
	}
	
	@Override
	public boolean equals(Object obj){
		boolean out = false;
		if(obj instanceof HAPStringableValueEntity){
			HAPStringableValueEntity value = (HAPStringableValueEntity)obj;
			out = HAPBasicUtility.isEqualMaps(value.m_childrens, value.m_childrens);
		}
		return out;
	}

	public static <T> T buildDefault(Class<T> c) {
		HAPValueInfoEntity valueInfoEntity = HAPValueInfoManager.getEntityValueInfo(c);
		T out = (T)valueInfoEntity.buildDefault();
		return out;
	}
}
