package ru.ragnok123.gtscoreboard.scoreboard;

import cn.nukkit.entity.Entity;

public class Score {
	
	public long scoreboardId;
	public ScoreboardObjective objective;
	public Entity entity;
	public String fakePlayer;
	public int scoreId;
	public long id;
	public boolean isFake;
	public int addOrRemove = 0;
	
	public Score(ScoreboardObjective o, String f) {
		scoreboardId = -Scoreboard.randomId();
		objective = o;
		fakePlayer = f;
		isFake = true;
	}
	
	public Score(ScoreboardObjective o, Entity e) {
		scoreboardId = -Scoreboard.randomId();
		objective = o;
		entity = e;
		isFake = false;
	}
	
	public void setScore(int id) {
		this.scoreId = id;
	}
	
	public int getScore() {
		return this.scoreId;
	}

}
