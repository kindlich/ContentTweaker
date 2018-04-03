package com.teamacronymcoders.contenttweaker.modules.vanilla.advancements.util;

import com.teamacronymcoders.contenttweaker.api.utils.MapWrapper;
import com.teamacronymcoders.contenttweaker.modules.vanilla.advancements.CoTAdvancementBuilder;
import net.minecraft.advancements.Advancement;
import net.minecraft.util.ResourceLocation;

import java.util.Map;

/**
 * A Map that will wrap around the old advancement Map to keep CoT advancements in there all the time!
 */

public class CustomAdvancementMap extends MapWrapper<ResourceLocation, Advancement> {

    public CustomAdvancementMap(Map<ResourceLocation, Advancement> wrappedMap) {
        super(wrappedMap);
    }

    @Override
    public void clear() {
        super.clear();
        CoTAdvancementBuilder.addAdvancements();
    }
}
