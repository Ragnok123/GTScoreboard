package ru.ragnok123.gtscoreboard.scoreboard;

import java.util.Random;

public class Scoreboard {
	
	public ScoreboardObjective objective;
	public long id;
	
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


}
