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
import java.util.Map;

import GTCore.player.GTPlayer;
import cn.nukkit.Player;
import cn.nukkit.Server;
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
	
	
	public static void sendScoreboard(Player player, Scoreboard scoreboard) {
		if(getScoreboard(player) == null) {
			SetDisplayObjectivePacket pk1 = new SetDisplayObjectivePacket();
			pk1.objectiveName = scoreboard.getObjective().objectiveName;
			pk1.displayName = scoreboard.getObjective().displayName;
			pk1.criteriaName = scoreboard.getObjective().criteriaToString();
			pk1.displaySlot = scoreboard.getObjective().slotToString();
			pk1.sortOrder = 1;
			player.dataPacket(pk1);
			boards.put(player, scoreboard);
		}
		boards.remove(player);
		boards.put(player, scoreboard);
		
		HashMap<String, Score> fakeMap = new HashMap<String, Score>();
		for(Map.Entry<String,Score> e :scoreboard.getObjective().scores.entrySet()) {
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
				scoreboard.getObjective().scores.remove(id);
			}
		}
		
	}
	
	public static void removeScoreboard(Player player) {
		if(boards.containsKey(player)) {
			RemoveObjectivePacket packet = new RemoveObjectivePacket();
			packet.objectiveName = getScoreboard(player).getObjective().objectiveName;
			player.dataPacket(packet);
			boards.remove(player);
		}
	}
	
	public static Scoreboard getScoreboard(Player player) {
		try {
			return boards.get(player);
		} catch(NullPointerException e) {
			return null;
		}
	}
	
	
  /*  @Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		Player player = (Player)sender;
		switch(cmd.getName()){
			case "board":
				Scoreboard board = new Scoreboard();
				ScoreboardObjective obj = board.registerNewObjective("gameteam_test", Criteria.DUMMY);
				obj.setDisplaySlot(DisplaySlot.SIDEBAR);
				obj.setDisplayName("GameTeam");
				obj.registerScore("player","§l§e"+player.getName(), 2);
				obj.registerScore("line_1","§l§c----------", 1);
				sendScoreboard(player, board);
				break;
		/*	case "board1":
				Scoreboard board1 = getScoreboard(player);
				ScoreboardObjective ob = board1.getObjective();
				ob.setScore("player", 20);
				ob.setScoreText("player", "§l§a"+player.getName());
				ob.resetScore("line_1");
				sendScoreboard(player, board1);
				break;
			case "board2":
				Scoreboard board2 = getScoreboard(player);
				ScoreboardObjective o1 = board2.getObjective();
				o1.setScore("player", 2);
				o1.setScoreText("player", "§l§e"+player.getName());
				o1.registerScore("line_1","§l§b-----------", 1);
				sendScoreboard(player, board2);
				break;
			}
		return false;
	}*/
	

}
