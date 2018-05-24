package com.nosliw.app.servlet;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.nosliw.data.core.imp.io.HAPDBSource;

public class HAPDataAccess {

	private HAPDBSource m_dbSource;

	private int i = 0;
	
	public HAPDataAccess(HAPDBSource dbSource) {
		this.m_dbSource = dbSource;
	}

	public void createSampleDataForUser(String userId) {
		try {
			HAPGroup soccerGroup = new HAPGroup(this.generateId(), "Soccer");
			HAPGroup schoolGroup = new HAPGroup(this.generateId(), "School");
			this.getConnection().prepareStatement("INSERT INTO MINIAPP_GROUP (ID, NAME) VALUES ('"+soccerGroup.getId()+"', '"+soccerGroup.getName()+"');").execute();
			this.getConnection().prepareStatement("INSERT INTO MINIAPP_GROUP (ID, NAME) VALUES ('"+schoolGroup.getId()+"', '"+schoolGroup.getName()+"');").execute();

			this.getConnection().prepareStatement("INSERT INTO MINIAPP_USERGROUP (ID, USERID, GROUPID) VALUES ('"+this.generateId()+"', '"+userId+"', '"+soccerGroup.getId()+"');").execute();
			this.getConnection().prepareStatement("INSERT INTO MINIAPP_USERGROUP (ID, USERID, GROUPID) VALUES ('"+this.generateId()+"', '"+userId+"', '"+schoolGroup.getId()+"');").execute();

			this.getConnection().prepareStatement("INSERT INTO MINIAPP_USERAPP (ID, USERID, APPID, APPNAME) VALUES ('"+this.generateId()+"', '"+userId+"', '"+"MyRealtor"+"', '"+"MyRealtor"+"');").execute();
			this.getConnection().prepareStatement("INSERT INTO MINIAPP_USERAPP (ID, USERID, APPID, APPNAME) VALUES ('"+this.generateId()+"', '"+userId+"', '"+"Airplane Arrival"+"', '"+"Airplane Arrival"+"');").execute();

			this.getConnection().prepareStatement("INSERT INTO MINIAPP_USERAPP (ID, USERID, APPID, APPNAME, GROUPID) VALUES ('"+this.generateId()+"', '"+userId+"', '"+"SoccerApp1"+"', '"+"SoccerApp1"+"', '"+soccerGroup.getId()+"');").execute();
			this.getConnection().prepareStatement("INSERT INTO MINIAPP_USERAPP (ID, USERID, APPID, APPNAME, GROUPID) VALUES ('"+this.generateId()+"', '"+userId+"', '"+"SoccerApp2"+"', '"+"SoccerApp2"+"', '"+soccerGroup.getId()+"');").execute();
			this.getConnection().prepareStatement("INSERT INTO MINIAPP_USERAPP (ID, USERID, APPID, APPNAME, GROUPID) VALUES ('"+this.generateId()+"', '"+userId+"', '"+"SchoolApp1"+"', '"+"SchoolApp1"+"', '"+schoolGroup.getId()+"');").execute();
			this.getConnection().prepareStatement("INSERT INTO MINIAPP_USERAPP (ID, USERID, APPID, APPNAME, GROUPID) VALUES ('"+this.generateId()+"', '"+userId+"', '"+"SchoolApp2"+"', '"+"SchoolApp2"+"', '"+schoolGroup.getId()+"');").execute();
			
			
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	
	
	public HAPUser createUser() {
		HAPUser out = new HAPUser();
		out.setId(this.generateId());
		try {
			PreparedStatement statement = this.getConnection().prepareStatement("INSERT INTO MINIAPP_USER (NAME, ID) VALUES ('"+out.getId()+"', '"+out.getId()+"');");
			statement.execute();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return out;
	}

	public HAPUser getUserById(String id) {
		HAPUser out = null;
		try {
			PreparedStatement statement = this.getConnection().prepareStatement("SELECT * FROM miniapp_user where id='"+id+"';");
			ResultSet resultSet = statement.executeQuery();
			while(resultSet.next()) {
				out = new HAPUser();
				out.setId((String)resultSet.getObject(HAPUser.ID));
				out.setName((String)resultSet.getObject(HAPUser.NAME));
				break;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return out;
	}

	public HAPGroup getGroupById(String id) {
		HAPGroup out = null;
		try {
			PreparedStatement statement = this.getConnection().prepareStatement("SELECT * FROM miniapp_group where id='"+id+"';");
			ResultSet resultSet = statement.executeQuery();
			while(resultSet.next()) {
				out = new HAPGroup();
				out.setId((String)resultSet.getObject(HAPGroup.ID));
				out.setName((String)resultSet.getObject(HAPGroup.NAME));
				break;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return out;
	}
	
	public List<HAPGroup> getUserGroups(String userId){
		List<HAPGroup> out = new ArrayList<HAPGroup>();
		try {
			List<String> groupIds = new ArrayList<String>();
			PreparedStatement statement = this.getConnection().prepareStatement("SELECT * FROM miniapp_usergroup where userid='"+userId+"';");
			ResultSet resultSet = statement.executeQuery();
			while(resultSet.next()) {
				groupIds.add((String)resultSet.getString("groupid"));
			}
			
			for(String groupId : groupIds) {
				HAPGroup group = this.getGroupById(groupId);
				out.add(group);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return out;
	}

	public void updateUserInfoWithMiniApp(HAPUserInfo userInfo) {
		
		Map<String, HAPUserGroupMiniAppInstances> groupById = new LinkedHashMap<String, HAPUserGroupMiniAppInstances>();
		for(HAPGroup group : userInfo.getUser().getGroups()) {
			groupById.put(group.getId(), new HAPUserGroupMiniAppInstances(group));
		}
		
		PreparedStatement statement;
		try {
			statement = this.getConnection().prepareStatement("SELECT * FROM miniapp_userapp where userid='"+userInfo.getUser().getId()+"';");
			ResultSet resultSet = statement.executeQuery();
			while(resultSet.next()) {
				String miniAppInstanceId = resultSet.getString("appid");
				HAPMiniAppInstance appInstance = new HAPMiniAppInstance(miniAppInstanceId, resultSet.getString("appname"));
				String groupId = resultSet.getString("groupid");
				if(groupId==null) {
					userInfo.addMiniAppInstance(appInstance);
				}
				else {
					groupById.get(groupId).addMiniAppInstance(appInstance);
				}
			}
			for(String groupId : groupById.keySet()) {
				userInfo.addGroupMiniAppInstances(groupById.get(groupId));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	private String generateId() {  return i+++"";   }
	
	protected Connection getConnection(){		return this.m_dbSource.getConnection();	}

	public HAPDBSource getDBSource(){  return this.m_dbSource;  }

}
