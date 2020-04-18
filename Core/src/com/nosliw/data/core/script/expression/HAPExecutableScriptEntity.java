package com.nosliw.data.core.script.expression;

import java.util.ArrayList;
import java.util.List;

import com.nosliw.data.core.runtime.HAPExecutableImpEntityInfo;

public class HAPExecutableScriptEntity extends HAPExecutableImpEntityInfo implements HAPExecutableScriptWithSegment{

	private String m_scriptType;
	
	private List<HAPExecutableScript> m_segs;

	public HAPExecutableScriptEntity(String scriptType, String id) {
		this.m_segs = new ArrayList<HAPExecutableScript>();
		this.m_scriptType = scriptType;
		this.setId(id);
	}
	
	@Override
	public String getScriptType() {  return this.m_scriptType;  }

	@Override
	public void addSegment(HAPExecutableScript segment) {    this.m_segs.add(segment);   }
	@Override
	public void addSegments(List<HAPExecutableScript> segments) {   this.m_segs.addAll(segments);    }
	
	@Override
	public List<HAPExecutableScript> getSegments(){    return this.m_segs;     }
}
