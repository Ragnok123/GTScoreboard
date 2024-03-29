package ru.ragnok123.gtscoreboard;

import ru.ragnok123.gtscoreboard.protocol.*;
import ru.ragnok123.gtscoreboard.scoreboard.*;
import cn.nukkit.event.Listener;
import cn.nukkit.event.player.PlayerQuitEvent;
import cn.nukkit.event.EventHandler;
import cn.nukkit.plugin.PluginBase;

import java.util.HashMap;

import cn.nukkit.Player;
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


}
