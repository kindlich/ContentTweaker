package com.teamacronymcoders.contenttweaker.modules.vanilla.advancements.representation;

import crafttweaker.annotations.ZenRegister;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.DisplayInfo;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenGetter;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenClass("mods.contenttweaker.advancements.Advancement")
@ZenRegister
public class AdvancementRepresentation {
    private final Advancement advancement;

    public AdvancementRepresentation(Advancement advancement) {
        this.advancement = advancement;
    }

    @ZenMethod
    public void addChild(AdvancementRepresentation other) {
        advancement.addChild(other.getInternal());
    }

    @ZenGetter("parent")
    public AdvancementRepresentation getParent() {
        Advancement parent = advancement.getParent();
        return parent == null ? null : new AdvancementRepresentation(parent);
    }

    @ZenGetter("display")
    public DisplayInfoRepresentation getDisplay() {
        DisplayInfo info = advancement.getDisplay();
        return info == null ? null : new DisplayInfoRepresentation(info);
    }





    public Advancement getInternal() {
        return advancement;
    }
}
