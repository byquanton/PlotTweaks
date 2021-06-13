package me.byquanton.plottweaks.flag;

import com.plotsquared.core.configuration.StaticCaption;
import com.plotsquared.core.plot.flag.types.BooleanFlag;
import org.jetbrains.annotations.NotNull;


public class RedstoneLimitBypassFlag extends BooleanFlag<RedstoneLimitBypassFlag> {

    public RedstoneLimitBypassFlag(boolean value){
        super(value,new StaticCaption("Set to `true` to disable the Redstone Operation Limit in the plot."));
    }

    @Override
    protected RedstoneLimitBypassFlag flagOf(@NotNull Boolean value) {
        return new RedstoneLimitBypassFlag(value);
    }
}
