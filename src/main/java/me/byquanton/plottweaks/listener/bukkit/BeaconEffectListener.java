package me.byquanton.plottweaks.listener.bukkit;

import com.destroystokyo.paper.event.block.BeaconEffectEvent;
import com.plotsquared.core.api.PlotAPI;
import com.plotsquared.core.location.Location;
import com.plotsquared.core.plot.Plot;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

public class BeaconEffectListener implements Listener {
    private final PlotAPI api = new PlotAPI();

    @EventHandler(priority = EventPriority.HIGH)
    public void onBeaconEffect(BeaconEffectEvent event){
        Player player = event.getPlayer();
        Block beacon = event.getBlock();
        Location location = new Location(beacon.getWorld().getName(), beacon.getX(), beacon.getY() , beacon.getZ());

        Plot plot = api.wrapPlayer(player.getUniqueId()).getCurrentPlot();

        if(plot != null){
            if (!plot.getArea().contains(location)){
                event.setCancelled(true);
            }
        }else{
            event.setCancelled(true);
        }

    }

}
