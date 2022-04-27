package com.nosliw.data.core.structure.reference;

import com.nosliw.common.path.HAPComplexPath;
import com.nosliw.data.core.structure.HAPElementStructure;
import com.nosliw.data.core.structure.HAPReferenceRootInStrucutre;
import com.nosliw.data.core.structure.HAPRootStructure;

//store result for resolve structure element reference path
//this can be used:
//    variable using
//    variable definition
//    data association
public class HAPInfoReferenceResolve{
	
	//resolved structure runtime id  resultStructureId
	public String structureId;

	//resolved root name in structure
	public String rootName;
	
	//solving result through original structure 
	public HAPInfoDesendantResolve elementInfoOriginal;
	
	//solving result through solid structure
	public HAPInfoDesendantResolve elementInfoSolid;
	
	//final element, solid (maybe logic element which embeded in real element)
	public HAPElementStructure finalElement;
	
	
/////////// below part may not need	
	//path to element (root id + path)
	public HAPComplexPath path;

	//resolved root node
	public HAPRootStructure referredRoot;
	
	//unique reference to root
	public HAPReferenceRootInStrucutre rootReference;

	
	
	
}
