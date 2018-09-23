package ru.ragnok123.gtscoreboard;

import cn.nukkit.Player;
import cn.nukkit.scheduler.Task;
import ru.ragnok123.gtscoreboard.scoreboard.Scoreboard;

public class ScoreboardTicker extends Task{
	
	public void onRun(int currentTick) {
		for(Player player : board.getServer().getOnlinePlayers().values()) {
			if(GTScoreboard.getScoreboard(player) != null) {
				Scoreboard b = GTScoreboard.getScoreboard(player);
				board.boards.remove(player);
				b.onUpdate();
				board.boards.put(player, b);
			}
		}
	}
	
	public ScoreboardTicker(GTScoreboard b) {
		board = b;
	}
	
	public GTScoreboard board;

}
