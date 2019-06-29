package com.nosliw.service.soccer;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.nosliw.data.core.HAPData;
import com.nosliw.data.core.HAPDataTypeId;
import com.nosliw.data.core.HAPDataWrapper;
import com.nosliw.data.core.service.provide.HAPExecutableService;
import com.nosliw.data.core.service.provide.HAPProviderService;
import com.nosliw.data.core.service.provide.HAPResultService;
import com.nosliw.data.core.service.provide.HAPUtilityService;

public class HAPServiceGetLineup implements HAPExecutableService, HAPProviderService{

	@Override
	public HAPResultService execute(Map<String, HAPData> parms){
		Map<String, HAPData> output = new LinkedHashMap<String, HAPData>();
		
		HAPPlayerLineup lineUp = HAPPlayerLineupManager.getInstance().getLineup();
		
		HAPResponsePlayerLineup response = new HAPResponsePlayerLineup();
		response.setWaitingList(lineUp.getWaitingList());
		
		List<HAPResponseSpot> spots = new ArrayList<HAPResponseSpot>();
		for(HAPSpot s : lineUp.getLineUp()) {
			HAPResponseSpot spot = new HAPResponseSpot();
			List<String> players = s.getPlayers();
			spot.addPlayer(players.get(0));
			if(players.size()>1) {
				String player1 = players.get(players.size()-1);
				if(!players.get(0).equals(player1)) {
					spot.addPlayer(player1);
				}
				if(spot.getVacant())  spot.addPlayer("????");
			}
			spots.add(spot);
		}
		for(int i : lineUp.getVacant()) spots.get(i).setVacant();
		response.setSpots(spots);
		
		output.put("lineup", new HAPDataWrapper(new HAPDataTypeId("test.data;1.0.0"), response));
		return HAPUtilityService.generateSuccessResult(output);
	}

}