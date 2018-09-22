package ru.ragnok123.gtscoreboard;

import ru.ragnok123.gtscoreboard.protocol.*;
import ru.ragnok123.gtscoreboard.scoreboard.*;
import cn.nukkit.event.Listener;
import cn.nukkit.event.player.PlayerJoinEvent;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.server.*;
import cn.nukkit.network.protocol.*;
import cn.nukkit.plugin.PluginBase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import GTCore.player.GTPlayer;
import cn.nukkit.Player;
import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
public class GTScoreboard extends PluginBase implements Listener{
	
	public static HashMap<Player, Scoreboard> boards = new HashMap<Player, Scoreboard>();
	
	public void onEnable() {
		this.getServer().getPluginManager().registerEvents(this, this);
	}
	
	public String packetToHex(byte packet) {
		return Integer.toString(packet, 16);
	}
	
	
	public static void setScoreboard(Player player, Scoreboard scoreboard) {
		if(!boards.containsKey(player)) {
			boards.put(player, scoreboard);
		} else {
			boards.remove(player);
			boards.put(player, scoreboard);
		}
		
		SetDisplayObjectivePacket pk1 = new SetDisplayObjectivePacket();
		pk1.objectiveName = scoreboard.getObjective().objectiveName;
		pk1.displayName = scoreboard.getObjective().displayName;
		pk1.criteriaName = scoreboard.getObjective().criteriaToString();
		pk1.displaySlot = scoreboard.getObjective().slotToString();
		pk1.sortOrder = 1;
		player.dataPacket(pk1);
		
		for(Score score : scoreboard.objective.scores) {
			
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
		}
		
	}
	
	public static Scoreboard getScoreboard(Player player) {
		try {
			return boards.get(player);
		} catch(NullPointerException e) {
			return null;
		}
	}
	
	
/*	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		Player player = (Player)sender;
		switch(cmd.getName()){
			case "board":
				Scoreboard board1 = getScoreboard(player);
				board1.objective.setScore("§l§e"+player.getName(), 8);
				board1.objective.resetScore("§l§c-----------");
				setScoreboard(player, board1);
				break;
			}
		return false;
	}*/
	

}
