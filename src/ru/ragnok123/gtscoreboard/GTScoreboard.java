package ru.ragnok123.gtscoreboard;

import ru.ragnok123.gtscoreboard.protocol.*;
import ru.ragnok123.gtscoreboard.scoreboard.*;
import cn.nukkit.event.Listener;
import cn.nukkit.event.player.PlayerInteractEvent;
import cn.nukkit.event.player.PlayerJoinEvent;
import cn.nukkit.event.player.PlayerQuitEvent;
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
		this.getServer().getScheduler().scheduleRepeatingTask(new ScoreboardTicker(this), 20);
	}
	
	public String packetToHex(byte packet) {
		return Integer.toString(packet, 16);
	}
	
	@EventHandler
	public void l(PlayerQuitEvent e) {
		boards.remove(e.getPlayer());
	}
	
	public static void sendScoreboard(Player player, Scoreboard scoreboard) {
		
		if(!boards.containsKey(player)) {
			scoreboard.player = player;
			boards.put(player, scoreboard);
		} else {
			boards.remove(player);
			scoreboard.player = player;
			boards.put(player, scoreboard);
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
	
	
/*    @Override
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
			}
		return false;
	}*/
	

}
