package com.nosliw.data.datatype.importer;

import java.io.InputStream;
import java.util.List;
import java.util.Set;

import com.nosliw.common.clss.HAPClassFilter;
import com.nosliw.common.strvalue.HAPStringableValueEntity;
import com.nosliw.common.strvalue.io.HAPStringableEntityImporterXML;
import com.nosliw.common.strvalue.valueinfo.HAPValueInfoManager;
import com.nosliw.common.utils.HAPConstant;
import com.nosliw.data.HAPOperation;
import com.nosliw.data.HAPRelationship;
import com.nosliw.data.HAPRelationshipPathSegment;
import com.nosliw.data.HAPDataTypeOperation;
import com.nosliw.data.HAPDataTypePicture;
import com.nosliw.data.HAPDataTypeProvider;

public class HAPDataTypeImporterManager {
	
	private HAPDBAccess m_dbAccess; 
	
	public HAPDataTypeImporterManager(){
		this.m_dbAccess = HAPDBAccess.getInstance();
		
		registerValueInfos();
	}
	
	private void registerValueInfos(){
		HAPValueInfoManager.getInstance().importFromXML(HAPDataTypeImporterManager.class, new String[]{
				"datatypedefinition.xml",
				"datatypeid.xml",
				"datatypeinfo.xml",
				"operationinfo.xml",
				"datatypeversion.xml",

				"operation.xml",
				"operationvar.xml",
				"datatypeoperation.xml",

				"datatyperelationship.xml"
		});

		this.m_dbAccess.createDBTable("data.datatypedef");
		this.m_dbAccess.createDBTable("data.operation");
		this.m_dbAccess.createDBTable("data.datatypeoperation");
		this.m_dbAccess.createDBTable("data.operationvar");
		this.m_dbAccess.createDBTable("data.relationship");
	}
	
	public void loadAllDataType(){
		new HAPClassFilter(){
			@Override
			protected void process(Class cls, Object data) {
				loadDataType(cls);
			}

			@Override
			protected boolean isValid(Class cls) {
				Class[] interfaces = cls.getInterfaces();
				for(Class inf : interfaces){
					if(inf.equals(HAPDataTypeProvider.class)){
						return true;
					}
				}
				return false;
			}
		}.process(null);
	}

	public void buildDataTypePictures(){
		List<HAPDataTypeImp> dataTypes = this.m_dbAccess.getAllDataTypes();
		for(HAPDataTypeImp dataType : dataTypes){
			HAPDataTypePictureImp dataTypePic = this.buildDataTypePicture((HAPDataTypeIdImp)dataType.getName());
			this.m_dbAccess.saveDataTypePicture(dataTypePic);
		}
	}
	
	public void buildDataTypeOperations(){
		List<HAPDataTypeImp> dataTypes = this.m_dbAccess.getAllDataTypes();
		for(HAPDataTypeImp dataType : dataTypes){
			
			
		}
	}
	
	private List<HAPDataTypeOperationImp> buildDataTypeOperations(HAPDataTypeImp dataType){
		List<HAPDataTypeOperationImp> out = this.m_dbAccess.getDataTypeOperations(dataType.getName());
		if(out.size()==0){
			//not build yet
			HAPDataTypePictureImp pic = this.m_dbAccess.getDataTypePicture(dataType.getName());
			HAPRelationshipImp parentRelationship = pic.getRelationship(dataType.getParentInfo());
			
			List<HAPDataTypeOperationImp> parentDataTypeOperations = buildDataTypeOperations(parentRelationship);
			for(HAPDataTypeOperationImp dataTypeOp : parentDataTypeOperations){
				HAPDataTypeOperationImp dataTypeOperation = dataTypeOp.extendPathSegment(HAPRelationshipPathSegment.buildPathSegmentForParent(), (HAPDataTypeIdImp)pic.getSourceDataType().getName());
				out.add(dataTypeOperation);
			}
		}
		
		List<HAPOperationImp> ownOperations = this.m_dbAccess.getOperationInfosByDataType((HAPDataTypeIdImp)dataType.getName());
		for(HAPOperationImp ownOperation : ownOperations){
			out.add(new HAPDataTypeOperationImp(ownOperation));
		}
		
		return out;
	}
	
	private void loadDataType(Class cls){
		InputStream dataTypeStream = cls.getResourceAsStream("datatype.xml");
		HAPDataTypeImpLoad dataType = (HAPDataTypeImpLoad)HAPStringableEntityImporterXML.readRootEntity(dataTypeStream, "data.datatypedef");
		dataType.resolveByConfigure(null);
		m_dbAccess.saveDataType(dataType);

		List<HAPOperation> ops = dataType.getDataOperationInfos();
		InputStream opsStream = cls.getResourceAsStream("operations.xml");
		if(opsStream!=null){
			List<HAPStringableValueEntity> ops1 = HAPStringableEntityImporterXML.readMutipleEntitys(opsStream, "data.operation");
			for(HAPStringableValueEntity op : ops1){
				ops.add((HAPOperation)op);
			}
		}
		
		for(HAPOperation op : ops){
			m_dbAccess.saveOperation((HAPOperationImp)op, dataType);
		}
	}

	private HAPDataTypePictureImp buildDataTypePicture(HAPDataTypeIdImp dataTypeId){
		HAPDataTypeImp dataType = this.getDataType(dataTypeId);
		HAPDataTypePictureImp out = new HAPDataTypePictureImp(dataType);
		
		//self as a relationship as well
		HAPRelationshipImp self = new HAPRelationshipImp(dataType);
		self.setSource((HAPDataTypeIdImp)dataType.getName());
		out.addRelationship(self);
		
		this.buildDataTypePictureFromConntectedDataType(dataType, out, HAPConstant.DATATYPE_PATHSEGMENT_PARENT);
		this.buildDataTypePictureFromConntectedDataType(dataType, out, HAPConstant.DATATYPE_PATHSEGMENT_LINKED);
		return out;
	}
	
	private void buildDataTypePictureFromConntectedDataType(HAPDataTypeImp dataType, HAPDataTypePictureImp out, int connectType){
		HAPDataTypeIdImp connectDataTypeId = dataType.getConntectedDataTypeId(connectType);
		if(connectDataTypeId!=null){
			HAPDataTypeImp connectDataType = (HAPDataTypeImp)this.getDataType(connectDataTypeId);
			HAPDataTypePicture connectDataTypePic = this.getDataTypePicture(connectDataTypeId);
			if(connectDataTypePic==null){
				connectDataTypePic = this.buildDataTypePicture(connectDataTypeId);
			}
			Set<? extends HAPRelationship> connectRelationships = connectDataTypePic.getRelationships();
			for(HAPRelationship connectRelationship : connectRelationships){
				out.addRelationship(((HAPRelationshipImp)connectRelationship).extendPathSegment(HAPRelationshipPathSegment.buildPathSegment(connectType), (HAPDataTypeIdImp)dataType.getName()));
			}
		}
	}
	
	private HAPDataTypePicture getDataTypePicture(HAPDataTypeIdImp dataTypeId){
		return this.m_dbAccess.getDataTypePicture(dataTypeId);
	}
	
	private HAPDataTypeImp getDataType(HAPDataTypeIdImp dataTypeId) {
		return this.m_dbAccess.getDataType(dataTypeId);
	}
	
	
	public static void main(String[] args){
		HAPDataTypeImporterManager man = new HAPDataTypeImporterManager();
		man.loadAllDataType();
		man.buildDataTypePictures();
		man.buildDataTypeOperations();
//		
//		HAPJSImporter jsImporter = new HAPJSImporter();
//		jsImporter.loadFromFolder("C:\\Users\\ewaniwa\\Desktop\\MyWork\\CoreProjects\\DataType");
//		
//		HAPDBAccess.getInstance().close();
	}
}
