package com.nosliw.uiresource.module;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.nosliw.common.constant.HAPAttribute;
import com.nosliw.common.constant.HAPEntityWithAttribute;
import com.nosliw.common.serialization.HAPJsonUtility;
import com.nosliw.common.serialization.HAPSerializationFormat;
import com.nosliw.data.core.component.HAPEmbededComponent;
import com.nosliw.uiresource.common.HAPInfoDecoration;

//each module ui is page unit in module that is alive in a module
//as it defined:
//		what it look like
//		where data come from: service provider
//		how to interact with page : page event handler
@HAPEntityWithAttribute
public class HAPDefinitionModuleUI extends HAPEmbededComponent{

	public static String STATUS_DISABLED = "disabled";
	
	@HAPAttribute
	public static String PAGE = "page";
	
	@HAPAttribute
	public static String UIDECORATION = "uiDecoration";
	
	@HAPAttribute
	public static String TYPE = "type";

	@HAPAttribute
	public static String STATUS = "status";
	
	//ui page
	private String m_page;

	private List<HAPInfoDecoration> m_uiDecoration;
	
	//data mapping (from data definition in module to public data definition in page)
//	private HAPDefinitionDataAssociation m_inputMapping;
//	private HAPDefinitionDataAssociation m_outputMapping;

	//provide extra information about this module ui so that container can render it properly
	private String m_type;
	
	private String m_status;
	
	public HAPDefinitionModuleUI() {
		this.m_uiDecoration = new ArrayList<HAPInfoDecoration>();
	}
	
	public String getPage() {   return this.m_page;    }
	public void setPage(String page) {   this.m_page = page;   }

	public String getType() {   return this.m_type;    }
	public void setType(String type) {   this.m_type = type;   }

	public String getStatus() {   return this.m_status;    }
	public void setStatus(String status) {   this.m_status = status;   }

//	public HAPDefinitionDataAssociation getInputMapping() {   return this.m_inputMapping;   }
//	public void setInputMapping(HAPDefinitionDataAssociation contextMapping) {   this.m_inputMapping = contextMapping;   }
//
//	public HAPDefinitionDataAssociation getOutputMapping() {   return this.m_outputMapping;   }
//	public void setOutputMapping(HAPDefinitionDataAssociation contextMapping) {   this.m_outputMapping = contextMapping;   }

	
	public void setUIDecoration(List<HAPInfoDecoration> decs) {  this.m_uiDecoration = decs;    }
	public List<HAPInfoDecoration> getUIDecoration(){  return this.m_uiDecoration;   }
	
	@Override
	protected void buildJsonMap(Map<String, String> jsonMap, Map<String, Class<?>> typeJsonMap) {
		super.buildJsonMap(jsonMap, typeJsonMap);
		jsonMap.put(PAGE, this.m_page);
//		jsonMap.put(INPUTMAPPING, HAPJsonUtility.buildJson(this.m_inputMapping, HAPSerializationFormat.JSON));
//		jsonMap.put(OUTPUTMAPPING, HAPJsonUtility.buildJson(this.m_outputMapping, HAPSerializationFormat.JSON));
		jsonMap.put(UIDECORATION, HAPJsonUtility.buildJson(this.m_uiDecoration, HAPSerializationFormat.JSON));
	}
	
	public HAPDefinitionModuleUI cloneModuleUIDef() {
		HAPDefinitionModuleUI out = new HAPDefinitionModuleUI();
		this.cloneToEmbededComponent(out);
		out.m_page = this.m_page;
		out.m_type = this.m_type;
		out.m_status = this.m_status;
		for(HAPInfoDecoration dec : this.m_uiDecoration) {
			out.m_uiDecoration.add(dec.cloneDecorationInfo());
		}
		return out;
	}
}
