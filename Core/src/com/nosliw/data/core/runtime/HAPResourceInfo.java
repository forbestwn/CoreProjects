package com.nosliw.data.core.runtime;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.nosliw.common.constant.HAPAttribute;
import com.nosliw.common.constant.HAPEntityWithAttribute;
import com.nosliw.common.info.HAPInfo;
import com.nosliw.common.info.HAPInfoImpSimple;
import com.nosliw.common.serialization.HAPSerializableImp;
import com.nosliw.common.serialization.HAPSerializationFormat;
import com.nosliw.common.serialization.HAPSerializeManager;
import com.nosliw.common.utils.HAPJsonUtility;

/**
 *  Store all the information related with a resource, including
 *  	Id
 *  	info: configure info, it normally used to control behavior
 *  	dependency: in order to use this resource, dependency has to be avaiable 
 *  	children:  this resource is a container, its children are real resources
 *  Except: 	
 *  	resource data
 */
@HAPEntityWithAttribute
public class HAPResourceInfo extends HAPSerializableImp{

	@HAPAttribute
	public static String ID = "id";
	
	//When we need to load resource according to resource id, sometimes, we need provide more information in order to control how resources are loaded
	@HAPAttribute
	public static String INFO = "info";

	@HAPAttribute
	public static String DEPENDENCY = "dependency";
	
	@HAPAttribute
	public static String CHILDREN = "children";
	
	
	private HAPResourceId m_resourceId;
	private HAPInfo m_info = new HAPInfoImpSimple();;
	private List<HAPResourceDependent> m_dependency = new ArrayList<HAPResourceDependent>();
	private List<HAPResourceDependent> m_children = new ArrayList<HAPResourceDependent>();
	
	public HAPResourceInfo(){	}
	
	public HAPResourceInfo(HAPResourceId resourceId){
		this.m_resourceId = resourceId;
	}
	
	public HAPResourceId getId(){		return this.m_resourceId;	}
	
	public void setInfo(String name, Object value){		this.m_info.setValue(name, value);	}
	
	public HAPResourceInfo withInfo(String name, String value){
		this.setInfo(name, value);
		return this;
	}
	
	public HAPInfo getInfo(){		return this.m_info;	}
	
	public Object getInfoValue(String name){		return this.m_info.getValue(name);	}
	
	public List<HAPResourceDependent> getDependency(){  return this.m_dependency;  }
	public void addDependency(HAPResourceDependent child){		this.m_dependency.add(child);	}
	
	public List<HAPResourceDependent> getChildren(){  return this.m_children;  }
	public void addChild(HAPResourceDependent child){  this.m_children.add(child);  }
	
	@Override
	protected void buildFullJsonMap(Map<String, String> jsonMap, Map<String, Class<?>> typeJsonMap){
		jsonMap.put(ID, this.m_resourceId.toStringValue(HAPSerializationFormat.JSON));
		jsonMap.put(CHILDREN, HAPJsonUtility.buildJson(this.getChildren(), HAPSerializationFormat.JSON));

		Map<String, String> dependencyJsonMap = new LinkedHashMap<String, String>();
		for(HAPResourceDependent dep : this.m_dependency){
			for(String alias : dep.getAlias()){
				dependencyJsonMap.put(alias, dep.getId().toStringValue(HAPSerializationFormat.JSON));
			}
		}
		jsonMap.put(DEPENDENCY, HAPJsonUtility.buildMapJson(dependencyJsonMap));
		
		jsonMap.put(INFO, HAPSerializeManager.getInstance().toStringValue(this.m_info, HAPSerializationFormat.JSON));
	}

	@Override
	protected void buildJsonMap(Map<String, String> jsonMap, Map<String, Class<?>> typeJsonMap){
		this.buildFullJsonMap(jsonMap, typeJsonMap);
	}

	@Override
	protected boolean buildObjectByJson(Object json){		return this.buildObjectByFullJson(json);	}

	@Override
	protected boolean buildObjectByFullJson(Object json){
		JSONObject jsonObj = (JSONObject)json;
		this.m_resourceId = new HAPResourceId();
		this.m_resourceId.buildObject(jsonObj.optJSONObject(ID), HAPSerializationFormat.JSON);
		
		JSONArray childrenArray = jsonObj.optJSONArray(CHILDREN);
		for(int i=0; i<childrenArray.length(); i++){
			JSONObject jsonChild = childrenArray.optJSONObject(i);
			HAPResourceDependent childResourceId = new HAPResourceDependent();
			childResourceId.buildObject(jsonChild, HAPSerializationFormat.JSON_FULL);
			this.addChild(childResourceId);
		}

		Map<String, HAPResourceDependent> dependencyByResourceId = new LinkedHashMap<String, HAPResourceDependent>();
		JSONObject dependencysObj = jsonObj.optJSONObject(DEPENDENCY);
		if(dependencysObj!=null){
			Iterator it = dependencysObj.keys();
			while(it.hasNext()){
				try {
					String alias = (String)it.next();

					HAPResourceId resourceId = new HAPResourceId();
					resourceId.buildObject(dependencysObj.getJSONObject(alias), HAPSerializationFormat.JSON);
					String resourceIdStr = resourceId.toStringValue(HAPSerializationFormat.JSON);
					
					HAPResourceDependent dependency = dependencyByResourceId.get(resourceIdStr);
					if(dependency==null){
						dependency = new HAPResourceDependent(resourceId);
						dependencyByResourceId.put(resourceIdStr, dependency);
						this.addDependency(dependency);
					}
					dependency.addAlias(alias);
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		}
		
		JSONObject infoObj = jsonObj.optJSONObject(INFO);
		if(infoObj!=null){
			Iterator it = infoObj.keys();
			while(it.hasNext()){
				String infoName = (String)it.next();
				this.getInfo().setValue(infoName, infoObj.opt(infoName));
			}
		}
		
		return true; 
	}
	
	public HAPResourceInfo clone(){
		HAPResourceInfo out = new HAPResourceInfo();
		out.cloneFrom(this);
		return out;
	}
	
	protected void cloneFrom(HAPResourceInfo resourceInfo){
		this.m_resourceId = resourceInfo.getId().clone();
		for(HAPResourceDependent dependency : resourceInfo.getDependency())			this.addDependency(dependency.clone());
		for(HAPResourceDependent child : resourceInfo.getChildren())			this.m_children.add(child.clone());
	}
	
	public boolean equals(Object obj){
		boolean out = false;
		if(obj instanceof HAPResourceInfo){
			HAPResourceInfo resourceInfo = (HAPResourceInfo)obj;
			out = this.getId().equals(resourceInfo.getId());
		}
		return out;
	}
}
