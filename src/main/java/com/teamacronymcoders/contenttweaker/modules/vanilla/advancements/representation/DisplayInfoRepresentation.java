package com.teamacronymcoders.contenttweaker.modules.vanilla.advancements.representation;

import crafttweaker.CraftTweakerAPI;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.formatting.IFormattedText;
import net.minecraft.advancements.DisplayInfo;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenGetter;

@ZenClass("mods.contenttweaker.advancements.DisplayInfo")
@ZenRegister
public class DisplayInfoRepresentation {
    private final DisplayInfo info;

    public DisplayInfoRepresentation(DisplayInfo info) {
        this.info = info;
    }

    @ZenGetter("title")
    public IFormattedText getTitle() {
        return CraftTweakerAPI.format.string(info.getTitle().getFormattedText());
    }

    @ZenGetter("description")
    public IFormattedText getDescription() {
        return CraftTweakerAPI.format.string(info.getDescription().getFormattedText());
    }

    @ZenGetter("frameType")
    public String getFrameType() {
        return info.getFrame().getName();
    }

    @ZenGetter("announceToChat")
    public boolean getAnnounceToChat() {
        return info.shouldAnnounceToChat();
    }

    @ZenGetter("hidden")
    public boolean getHidden() {
        return info.isHidden();
    }



}
