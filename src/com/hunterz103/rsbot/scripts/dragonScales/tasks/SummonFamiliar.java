package com.hunterz103.rsbot.scripts.dragonScales.tasks;

import com.hunterz103.rsbot.scripts.dragonScales.BlueDragonScalePicker;
import com.hunterz103.rsbot.scripts.framework.Task;

/**
 * TODO: Add support for familiars
 * Created by Brian on 2/9/14.
 */
public class SummonFamiliar extends Task<BlueDragonScalePicker> {

    public SummonFamiliar(BlueDragonScalePicker arg0) {
        super(arg0);
    }

    @Override
    public int getPriority() {
        return 2;
    }

    @Override
    public boolean activate() {
        return false;
    }

    @Override
    public void execute() {

    }
}
