package com.nosliw.data.core.process;

import com.nosliw.common.pattern.HAPNamingConversionUtility;
import com.nosliw.common.serialization.HAPSerializableImp;

public class HAPIdProcess extends HAPSerializableImp{

	private String m_processId;
	private String m_suiteId;
	
	public HAPIdProcess(String suiteId, String processId){
		this.m_processId = processId;
		this.m_suiteId = suiteId;
	}

	public HAPIdProcess(String id){
		this.parseId(id);
	}

	public String getId(){  return HAPNamingConversionUtility.cascadeLevel1(m_suiteId, m_processId);  }

	public String getSuiteId() {   return this.m_suiteId;   }
	public String getProcessId() {    return this.m_processId;    }
	
	private void parseId(String id) {
		String[] segs = HAPNamingConversionUtility.parseLevel1(id);
		this.m_suiteId = segs[0];
		this.m_processId = segs[1];
	}
	
	@Override
	protected String buildLiterate(){		return this.getId();	}

	@Override
	protected boolean buildObjectByLiterate(String literateValue){
		this.parseId(literateValue);
		return true;
	}
}