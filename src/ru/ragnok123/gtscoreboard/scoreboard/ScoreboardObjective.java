package ru.ragnok123.gtscoreboard.scoreboard;

import java.util.*;

import cn.nukkit.entity.Entity;

public class ScoreboardObjective {
	
	public static int Ascending = 0;
	public static int Descending = 1;
	
	public String objectiveName;
	public DisplaySlot displaySlot;
	public Criteria criteria;
	public String displayName;
	public HashMap<String, Score> scores = new HashMap<String, Score>();
	
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}
	
	public String getDisplayName() {
		return this.displayName;
	}
	
	public void setDisplaySlot(DisplaySlot displaySlot) {
		this.displaySlot = displaySlot;
	}
	
	public DisplaySlot getDisplaySlot() {
		return this.displaySlot;
	}
	
	public void setScore(String fake, int value) {
		Score score = new Score(this, fake);
		score.setScore(value);
		if(!scores.containsKey(fake)) {
			scores.put(fake, score);
		} else {
			Score modified = scores.get(fake);
			modified.setScore(value);
			scores.remove(fake);
			scores.put(fake, modified);
		}
	}
	
	public void resetScore(String fake) {
		Score score = new Score(this, fake);
		score.addOrRemove = 1;
		if(!scores.containsKey(fake)) {
			scores.put(fake, score);
		} else {
			Score modified = scores.get(fake);
			modified.addOrRemove = 1;
			scores.remove(fake);
			scores.put(fake, modified);
		}
	}
	
	public Score getScore(String fake) {
		Score score = null;
        for(Score s : scores.values())
        {
            if (s.fakePlayer.equals(fake))
            {
                score = s;
            } else {
            	score = new Score(this, fake);
            }
        }
        return score;
	}
	
	public String criteriaToString() {
		String s = "";
		 switch (criteria)
         {
             case DUMMY:
                 s = "dummy";
                 break;
         }
         return s;
	}
	
	public String slotToString()
    {
        String slot = "";
        switch (displaySlot)
        {
            case SIDEBAR:
                slot = "sidebar";
                break;
            case LIST:
                slot = "list";
                break;
            case BELOWNAME:
                slot = "belowname";
                break;
        }
        return slot;
    }

}