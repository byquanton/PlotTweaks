package me.byquanton.plottweaks;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Locale;
import java.util.logging.Level;
import java.util.regex.Pattern;

import com.plotsquared.core.PlotSquared;
import com.plotsquared.core.configuration.Settings;
import com.plotsquared.core.configuration.caption.CaptionMap;
import com.plotsquared.core.configuration.caption.load.CaptionLoader;
import com.plotsquared.core.configuration.caption.load.DefaultCaptionProvider;
import com.plotsquared.core.plot.flag.GlobalFlagContainer;
import io.papermc.lib.PaperLib;
import me.byquanton.plottweaks.flag.RedstoneLimitBypassFlag;
import me.byquanton.plottweaks.listener.bukkit.BeaconEffectListener;
import me.byquanton.plottweaks.listener.bukkit.PotentialLagListener;
import me.byquanton.plottweaks.listener.protocollib.SoundsListener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;


public final class PlotTweaks extends JavaPlugin {

    public static final String PLOT_TWEAKS_NAMESPACE = "plottweaks";
    private Plugin plugin;

    @Override
    public void onLoad(){
        plugin = this;

        PaperLib.suggestPaper(plugin);
    }

    @Override
    public void onEnable() {

        plugin.saveDefaultConfig();
        
        GlobalFlagContainer.getInstance().addFlag(RedstoneLimitBypassFlag.REDSTONE_LIMIT_BYPASS_FLAG_FALSE);

        try {
            loadCaptions();
        } catch (IOException e) {
            getLogger().log(Level.SEVERE, "Failed to load captions", e);
        }
        

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

    private void loadCaptions() throws IOException {
        Path msgFilePath = getDataFolder().toPath().resolve("lang").resolve("messages_en.json");
        if (!Files.exists(msgFilePath)) {
            this.saveResource("lang/messages_en.json", false);
        }
        CaptionLoader captionLoader = CaptionLoader.of(
                Locale.ENGLISH,
                CaptionLoader.patternExtractor(Pattern.compile("messages_(.*)\\.json")),
                DefaultCaptionProvider.forClassLoaderFormatString(
                        this.getClass().getClassLoader(),
                        "lang/messages_%s.json"
                ),
                PLOT_TWEAKS_NAMESPACE
        );
        CaptionMap captionMap;
        if (Settings.Enabled_Components.PER_USER_LOCALE) {
            captionMap = captionLoader.loadAll(getDataFolder().toPath().resolve("lang"));
        } else {
            String fileName = "messages_" + Settings.Enabled_Components.DEFAULT_LOCALE + ".json";
            captionMap = captionLoader.loadSingle(getDataFolder().toPath().resolve("lang").resolve(fileName));
        }
        PlotSquared.get().registerCaptionMap(PLOT_TWEAKS_NAMESPACE, captionMap);
        getLogger().info("Loaded caption map for namespace '" + PLOT_TWEAKS_NAMESPACE + "': "
                + captionMap.getClass().getCanonicalName());
    }

}
