package me.byquanton.plottweaks.listener.bukkit;

import com.comphenix.protocol.collections.ExpireHashMap;
import com.plotsquared.bukkit.player.BukkitPlayer;
import com.plotsquared.core.location.Location;
import com.plotsquared.core.player.PlotPlayer;
import com.plotsquared.core.plot.Plot;
import me.byquanton.plottweaks.flag.RedstoneLimitBypassFlag;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockRedstoneEvent;
import org.bukkit.plugin.Plugin;

import java.util.concurrent.TimeUnit;

public class PotentialLagListener implements Listener {

    private Plugin plugin;
    private final Integer redstone_limit;
    private final Boolean enable_bypass_flag;

    public PotentialLagListener(Plugin plugin){
        this.plugin = plugin;
        this.redstone_limit = plugin.getConfig().getInt("redstone-limiting.max_redstone_operations_limit");
        this.enable_bypass_flag = plugin.getConfig().getBoolean("redstone-limiting.enable_bypass_flag");
    }

    private boolean check_bypass(Plot plot){
        if(this.enable_bypass_flag){
            try{
                return plot.getFlagContainer().getFlag(RedstoneLimitBypassFlag.class).getValue();
            }catch(NullPointerException e){
                return false;
            }
        }
        return false;
    }

    ExpireHashMap<Plot,Integer> redstoneMap = new ExpireHashMap<>();

    @EventHandler
    public void onRedstoneEvent(BlockRedstoneEvent event){
        Location location = new Location(event.getBlock().getWorld().getName(), event.getBlock().getX(), event.getBlock().getY() , event.getBlock().getZ());
        Plot plot = location.getPlot();

        if(plot != null){

            if(check_bypass(plot)){
                return;
            }

            if(redstoneMap.containsKey(plot)){
                redstoneMap.put(plot,redstoneMap.get(plot)+1,60, TimeUnit.SECONDS);

                if(redstoneMap.get(plot) >= this.redstone_limit){
                    event.setNewCurrent(0);
                    for(PlotPlayer<?> plotPlayer:plot.getPlayersInPlot()){
                        this.plugin.getServer().getPlayer(plotPlayer.getUUID()).spigot().sendMessage(ChatMessageType.ACTION_BAR,new TextComponent("Redstone is currently disabled on this Plot"));
                    }
                }

            }else{
                redstoneMap.put(plot,1,60, TimeUnit.SECONDS);
            }


        }

    }
}
