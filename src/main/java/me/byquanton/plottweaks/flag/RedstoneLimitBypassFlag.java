package me.byquanton.plottweaks.flag;

import com.plotsquared.core.configuration.caption.TranslatableCaption;
import com.plotsquared.core.plot.flag.types.BooleanFlag;
import org.jetbrains.annotations.NotNull;

import me.byquanton.plottweaks.PlotTweaks;


public class RedstoneLimitBypassFlag extends BooleanFlag<RedstoneLimitBypassFlag> {


    public static final RedstoneLimitBypassFlag REDSTONE_LIMIT_BYPASS_FLAG_TRUE = new RedstoneLimitBypassFlag(true);
    public static final RedstoneLimitBypassFlag REDSTONE_LIMIT_BYPASS_FLAG_FALSE = new RedstoneLimitBypassFlag(false);

    public RedstoneLimitBypassFlag(boolean value){
        super(value, TranslatableCaption.of(PlotTweaks.PLOT_TWEAKS_NAMESPACE, "flags.flag_description_bypass_redstone_limit"));
    }

    @Override
    protected RedstoneLimitBypassFlag flagOf(@NotNull Boolean value) {
        return value ? REDSTONE_LIMIT_BYPASS_FLAG_TRUE : REDSTONE_LIMIT_BYPASS_FLAG_FALSE;
    }
}
