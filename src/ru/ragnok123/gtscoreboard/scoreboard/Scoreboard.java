package ru.ragnok123.gtscoreboard.scoreboard;

import cn.nukkit.Player;
import ru.ragnok123.gtscoreboard.protocol.RemoveObjectivePacket;
import ru.ragnok123.gtscoreboard.protocol.ScorePacketInfo;
import ru.ragnok123.gtscoreboard.protocol.SetDisplayObjectivePacket;
import ru.ragnok123.gtscoreboard.protocol.SetScorePacket;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class Scoreboard {
	
	public ScoreboardObjective objective;
	public long id;
	public Player player = null;
	
	public static long randomId() {
		Random rnd = new Random();
		long randomLong = rnd.nextLong();
        return randomLong;
	}
	
	public Scoreboard() {
		id = -randomId();
	}
	
	public ScoreboardObjective registerNewObjective(String objectiveName, Criteria criteria) {
		ScoreboardObjective obj = new ScoreboardObjective();
		obj.objectiveName = objectiveName;
		obj.criteria = criteria;
		objective = obj;
		return objective;
	}
	
	public ScoreboardObjective getObjective() {
		try {
			return objective;
		} catch (NullPointerException e) {
			return null;
		}
	}
	
	public void onUpdate() {
		
		SetDisplayObjectivePacket pk1 = new SetDisplayObjectivePacket();
		pk1.objectiveName = getObjective().objectiveName;
		pk1.displayName = getObjective().displayName;
		pk1.criteriaName = getObjective().criteriaToString();
		pk1.displaySlot = getObjective().slotToString();
		pk1.sortOrder = 1;
		player.dataPacket(pk1);
		
		HashMap<String, Score> fakeMap = new HashMap<String, Score>();
		for(Map.Entry<String,Score> e : getObjective().scores.entrySet()) {
			fakeMap.put(e.getKey(), e.getValue());
		}
		
		for(Score score : fakeMap.values()) {
			
			ScorePacketInfo info = new ScorePacketInfo();
			info.scoreboardId = score.scoreboardId;
			info.objectiveName = score.objective.objectiveName;
			info.score = score.scoreId;
			info.addType = 3;
			info.fakePlayer = score.fakePlayer;
			
			List<ScorePacketInfo> list = new ArrayList<ScorePacketInfo>();
			list.add(info);
			
			
			SetScorePacket pk2 = new SetScorePacket();
			pk2.type = (byte) score.addOrRemove;
			pk2.entries = list;
			player.dataPacket(pk2);
			
			if(score.addOrRemove == 1) {
				String id = score.fakeId;
				getObjective().scores.remove(id);
			}
		}
	}


}
