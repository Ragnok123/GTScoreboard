package ru.ragnok123.gtscoreboard.scoreboard;

import java.util.ArrayList;
import java.util.List;

import cn.nukkit.entity.Entity;

public class ScoreboardObjective {
	
	public static int Ascending = 0;
	public static int Descending = 1;
	
	public String objectiveName;
	public DisplaySlot displaySlot;
	public Criteria criteria;
	public String displayName;
	public List<Score> scores = new ArrayList<Score>();
	
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
		scores.add(score);
		for(Score s : new ArrayList<Score>(scores)) {
			if(s.fakePlayer.equals(fake) && s.scoreboardId != score.scoreboardId) {
				scores.remove(s);
			}
		}
	}
	
	public void resetScore(String fake) {
		Score score = new Score(this, fake);
		score.addOrRemove = 1;
		scores.add(score);
		for(Score s : new ArrayList<Score>(scores)) {
			if(s.fakePlayer.equals(fake) && s.scoreboardId != score.scoreboardId) {
				scores.remove(s);
			}
		}
	}
	
	public Score getScore(String fake) {
		Score score = null;
        for(Score s : scores)
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
             case Dummy:
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
            case Sidebar:
                slot = "sidebar";
                break;
            case List:
                slot = "list";
                break;
            case BelowName:
                slot = "belowname";
                break;
        }
        return slot;
    }

}
