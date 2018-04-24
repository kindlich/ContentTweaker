package com.teamacronymcoders.contenttweaker.modules.vanilla.advancements;

import com.teamacronymcoders.contenttweaker.modules.vanilla.advancements.functions.CustomTrigger;
import com.teamacronymcoders.contenttweaker.modules.vanilla.advancements.functions.RewardFunctions;
import com.teamacronymcoders.contenttweaker.modules.vanilla.advancements.util.CustomAdvancementSet;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.formatting.IFormattedText;
import crafttweaker.api.item.IItemStack;
import net.minecraft.advancements.*;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.ResourceLocation;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;
import stanhebben.zenscript.annotations.ZenProperty;

import java.util.HashMap;
import java.util.Map;

@ZenClass("mods.contenttweaker.advancements.Builder")
@ZenRegister
public class CoTAdvancementBuilder {

    @ZenProperty
    public String identifier;

    @ZenProperty
    public IFormattedText title = null;

    @ZenProperty
    public IFormattedText description = null;

    @ZenProperty
    public IFormattedText displayText = null;

    @ZenProperty
    public IItemStack icon = null;

    @ZenProperty
    public String background = null;

    @ZenProperty
    public boolean showToast = false;

    @ZenProperty
    public boolean announceToChat = false;

    @ZenProperty
    public boolean hidden = false;

    @ZenProperty
    public float posX = 0.0F;

    @ZenProperty
    public float posY = 0.0F;

    @ZenProperty
    public String[][] requirements = new String[0][];

    @ZenProperty
    public String parent = null;

    @ZenProperty
    public IItemStack[] loot = new IItemStack[0];

    @ZenProperty
    public String[] recipesNames = new String[0];

    @ZenProperty
    public IRecipe[] recipes = new IRecipe[0];

    @ZenProperty
    public int experience = 0;

    @ZenProperty
    public RewardFunctions.OnCompleted onCompleted = null;


    private FrameType frameType = FrameType.TASK;
    private Map<String, Criterion> criteria = new HashMap<>();

    public CoTAdvancementBuilder(String identifier) {
        this.identifier = identifier;
    }


    @ZenMethod
    public static CoTAdvancementBuilder create(String identifier) {
        return new CoTAdvancementBuilder(identifier);
    }

    @ZenMethod
    public void removeTrigger(String identifier) {
        criteria.remove(identifier);
    }

    private void addCriterionInstance(String identifier, ICriterionInstance trigger) {
        criteria.put(identifier, new Criterion(trigger));
    }

    @ZenMethod
    public void addCustomTrigger(String identifier, String triggerName) {
        addCriterionInstance(identifier, CustomTrigger.TRIGGERS.get(triggerName).getInstance());
    }

    @ZenMethod
    public void setAsTask() {
        frameType = FrameType.TASK;
    }

    @ZenMethod
    public void setAsChallenge() {
        frameType = FrameType.CHALLENGE;
    }

    @ZenMethod
    public void setAsGoal() {
        frameType = FrameType.GOAL;
    }


    @ZenMethod
    public void register() {
        ResourceLocation id = new ResourceLocation(identifier);
        CoTDisplayInfo displayInfo = new CoTDisplayInfo(icon, title, description, background == null ? null : new ResourceLocation(background), frameType, showToast, announceToChat, hidden, posX, posY);

        CoTAdvancementRewards rewards = new CoTAdvancementRewards(experience);
        rewards.addLoot(loot);
        rewards.addRecipe(recipes);
        for (String name : recipesNames) {
            rewards.addRecipe(new ResourceLocation(name));
        }
        rewards.onCompleted = this.onCompleted;

        CoTAdvancement advancement = new CoTAdvancement(id, parent, displayInfo, rewards, criteria, requirements, displayText);


        AdvancementManager.ADVANCEMENT_LIST.advancements.put(id, advancement);
        if (parent == null) {
            AdvancementManager.ADVANCEMENT_LIST.roots.add(advancement);
        } else {
            AdvancementManager.ADVANCEMENT_LIST.nonRoots.add(advancement);
        }
        MAP.put(id, advancement);
    }


    //########################################
    //###  Minecraft advancement handling  ###
    //########################################

    private static final Map<ResourceLocation, Advancement> MAP = new HashMap<>();

    public static void addAdvancements() {
        AdvancementManager.ADVANCEMENT_LIST.advancements.putAll(MAP);
        for (Advancement advancement : MAP.values()) {
            if (advancement.getParent() == null) {
                AdvancementManager.ADVANCEMENT_LIST.roots.add(advancement);
            } else {
                AdvancementManager.ADVANCEMENT_LIST.nonRoots.add(advancement);
            }
        }

    }

    public static void handleAdvancements() {
        if (!MAP.isEmpty()) {
            AdvancementManager.ADVANCEMENT_LIST.nonRoots = new CustomAdvancementSet(AdvancementManager.ADVANCEMENT_LIST.nonRoots);
            addAdvancements();
        }
    }
}
