package com.teamacronymcoders.contenttweaker.modules.vanilla.advancements.util;

import com.teamacronymcoders.contenttweaker.api.utils.SetWrapper;
import com.teamacronymcoders.contenttweaker.modules.vanilla.advancements.CoTAdvancementBuilder;
import net.minecraft.advancements.Advancement;

import java.util.Set;

public class CustomAdvancementSet extends SetWrapper<Advancement> {

    public CustomAdvancementSet(Set<Advancement> delegate) {
        super(delegate);
    }

    @Override
    public void clear() {
        super.clear();
        CoTAdvancementBuilder.addAdvancements();
    }
}
