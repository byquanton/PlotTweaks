package me.byquanton.plottweaks;

import com.plotsquared.core.plot.flag.GlobalFlagContainer;
import io.papermc.lib.PaperLib;
import me.byquanton.plottweaks.flag.RedstoneLimitBypassFlag;
import me.byquanton.plottweaks.listener.bukkit.BeaconEffectListener;
import me.byquanton.plottweaks.listener.bukkit.PotentialLagListener;
import me.byquanton.plottweaks.listener.protocollib.SoundsListener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public final class PlotTweaks extends JavaPlugin {

    private Plugin plugin;

    @Override
    public void onLoad(){
        plugin = this;

        PaperLib.suggestPaper(plugin);
    }

    @Override
    public void onEnable() {

        plugin.saveDefaultConfig();

        GlobalFlagContainer.getInstance().addFlag(new RedstoneLimitBypassFlag(false));

        if(plugin.getConfig().getBoolean("sound-blocking.enabled")){
            new SoundsListener(plugin);
        }
        if(plugin.getConfig().getBoolean("redstone-limiting.enabled")){
            getServer().getPluginManager().registerEvents(new PotentialLagListener(plugin), plugin);
        }

        if(PaperLib.isPaper()){
            if(plugin.getConfig().getBoolean("limit-beacon-to-plot")) {
                getServer().getPluginManager().registerEvents(new BeaconEffectListener(), plugin);
            }
        }

    }

}
