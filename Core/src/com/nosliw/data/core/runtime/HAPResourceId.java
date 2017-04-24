package com.nosliw.data.core.runtime;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.json.JSONArray;
import org.json.JSONObject;

import com.nosliw.common.constant.HAPAttribute;
import com.nosliw.common.constant.HAPEntityWithAttribute;
import com.nosliw.common.literate.HAPLiterateManager;
import com.nosliw.common.pattern.HAPNamingConversionUtility;
import com.nosliw.common.serialization.HAPSerializableImp;
import com.nosliw.common.utils.HAPBasicUtility;
import com.nosliw.common.utils.HAPConstant;
import com.nosliw.common.utils.HAPJsonUtility;

/**
 * Resource Id to identify resource 
 */
@HAPEntityWithAttribute
public class HAPResourceId extends HAPSerializableImp{

	@HAPAttribute
	public static String ID = "id";

	@HAPAttribute
	public static String TYPE = "type";
	
	@HAPAttribute
	public static String ALIAS = "alias";
	
	protected String m_type;
	protected String m_id;
	protected Set<String> m_alias;
	
	public HAPResourceId(){
		this.m_alias = new HashSet<String>();
	}
	
	public HAPResourceId(String literate){
		this.buildObjectByLiterate(literate);
	}
	
	public HAPResourceId(String type, String id, String alias){
		this.init(type, id, alias);
	}
	
	protected void init(String type, String id, String alias){
		this.m_type = type;
		this.setAlias(alias);
		if(id!=null)		this.setId(id);
	}
	
	public String getId() {		return this.m_id;	}
	
	public String getType() {  return this.m_type;  }

	public Set<String> getAlias(){  return this.m_alias;  }
	public void addAlias(String alias){
		this.m_alias.add(alias);
	}
	private void setAlias(String aliasLiterate){
		this.m_alias = new HashSet<String>((List<String>)HAPLiterateManager.getInstance().stringToValue(aliasLiterate, HAPConstant.STRINGABLE_ATOMICVALUETYPE_ARRAY, HAPConstant.STRINGABLE_ATOMICVALUETYPE_STRING));
	}
	
	public void addAlias(Collection alias){
		this.m_alias.addAll(alias);
	}
	
	public void removeAlias(String alias){
		this.m_alias.remove(alias);
	}
	
	protected void setId(String id){  this.m_id = id; }
	
	@Override
	protected void buildFullJsonMap(Map<String, String> jsonMap, Map<String, Class<?>> typeJsonMap){
		jsonMap.put(ID, this.getId());
		jsonMap.put(TYPE, this.getType());
		jsonMap.put(ALIAS, HAPJsonUtility.buildArrayJson(this.getAlias().toArray(new String[0])));
	}

	@Override
	protected void buildJsonMap(Map<String, String> jsonMap, Map<String, Class<?>> typeJsonMap){
		this.buildFullJsonMap(jsonMap, typeJsonMap);
	}

	@Override
	protected boolean buildObjectByFullJson(Object json){
		JSONObject jsonObj = (JSONObject)json;
		this.setId(jsonObj.optString(ID));
		this.m_type = jsonObj.optString(TYPE);
		JSONArray alaisArray = jsonObj.optJSONArray(ALIAS);
		for(int i=0; i<alaisArray.length(); i++){
			String aliais = alaisArray.optString(i);
			this.m_alias.add(aliais);
		}
		return true; 
	}

	@Override
	protected boolean buildObjectByJson(Object json){
		this.buildObjectByFullJson(json);
		return true; 
	}
	
	@Override
	protected String buildLiterate(){
		String aliasLiterate = HAPLiterateManager.getInstance().valueToString(this.m_alias);
		return HAPNamingConversionUtility.cascadeDetail(new String[]{this.getType(), this.getId(), aliasLiterate});
	}
	
	@Override
	protected boolean buildObjectByLiterate(String literateValue){	
		String[] segs = HAPNamingConversionUtility.parseDetails(literateValue);
		this.m_type = segs[0];
		this.m_id = segs[1];
		if(segs.length>=3)   this.setAlias(segs[2]);
		return true;  
	}
	

	@Override
	public boolean equals(Object o){
		if(o instanceof HAPResourceId){
			HAPResourceId resourceId = (HAPResourceId)o;
			return HAPBasicUtility.isEquals(this.getType(), resourceId.getType()) &&
					HAPBasicUtility.isEquals(this.getId(), resourceId.getId());
		}
		else{
			return false;
		}
	}
	
	public HAPResourceId clone(){
		HAPResourceId out = new HAPResourceId();
		out.cloneFrom(this);
		return out;
	}
	
	protected void cloneFrom(HAPResourceId resourceId){
		this.setId(resourceId.m_id);
		this.m_type = resourceId.m_type;
		this.m_alias.addAll(resourceId.m_alias);
	}
}