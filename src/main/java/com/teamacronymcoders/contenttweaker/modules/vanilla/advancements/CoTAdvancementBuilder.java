package com.teamacronymcoders.contenttweaker.modules.vanilla.advancements;

import com.teamacronymcoders.contenttweaker.modules.vanilla.advancements.functions.CriterionTesters;
import com.teamacronymcoders.contenttweaker.modules.vanilla.advancements.functions.CriterionTriggers;
import com.teamacronymcoders.contenttweaker.modules.vanilla.advancements.functions.CriterionTriggers.ItemConsumeTrigger;
import com.teamacronymcoders.contenttweaker.modules.vanilla.advancements.functions.RewardFunctions;
import com.teamacronymcoders.contenttweaker.modules.vanilla.advancements.util.CustomAdvancementMap;
import crafttweaker.CraftTweakerAPI;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.entity.IEntityDefinition;
import crafttweaker.api.formatting.IFormattedText;
import crafttweaker.api.item.IIngredient;
import crafttweaker.api.item.IItemStack;
import net.minecraft.advancements.*;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.ReflectionHelper;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;
import stanhebben.zenscript.annotations.ZenProperty;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Arrays;
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
    public int posX = 0;

    @ZenProperty
    public int posY = 0;

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

    private void addCriterionInstance(String identifier, ICriterionInstance trigger) {
        criteria.put(identifier, new Criterion(trigger));
    }

    @ZenMethod
    public void addItemConsumeCriterion(String identifier, IIngredient ingredient) {
        addItemConsumeCriterion(identifier, ingredient::matches);
    }

    @ZenMethod
    public void addItemConsumeCriterion(String identifier, CriterionTesters.ItemConsumeTester tester) {
        addCriterionInstance(identifier, new ItemConsumeTrigger(tester));
    }

    @ZenMethod
    public void addEntityBredTrigger(String identifier, IEntityDefinition child) {
        addEntityBredTrigger(identifier, (player, parent1, parent2, child1) -> child1.getDefinition().getId().equals(child.getId()));
    }

    @ZenMethod
    public void addEntityBredTrigger(String identifier, CriterionTesters.EntityBredTester tester) {
        addCriterionInstance(identifier, new CriterionTriggers.EntityBredTrigger(tester));
    }

    @ZenMethod
    public void addBeaconTrigger(String identifier, int minLevel, int maxLevel) {
        addBeaconTrigger(identifier, level -> minLevel <= level && level <= maxLevel);
    }

    @ZenMethod
    public void addBeaconTrigger(String identifier, CriterionTesters.BeaconTester tester) {
        addCriterionInstance(identifier, new CriterionTriggers.BeaconTrigger(tester));
    }

    @ZenMethod
    public void addPlayerKilledEntityTrigger(String identifier, IEntityDefinition definition) {
        addPlayerKilledEntityTrigger(identifier, (player, entity, killingBlow) -> entity.getDefinition().getId().equals(definition.getId()));
    }

    @ZenMethod
    public void addPlayerKilledEntityTrigger(String identifier, CriterionTesters.PlayerKillTester tester) {
        addCriterionInstance(identifier, new CriterionTriggers.PlayerKilledEntityTrigger(tester));
    }

    @ZenMethod
    public void addEntityKilledPlayerTrigger(String identifier, IEntityDefinition definition) {
        addEntityKilledPlayerTrigger(identifier, (player, entity, killingBlow) -> entity.getDefinition().getId().equals(definition.getId()));
    }

    @ZenMethod
    public void addEntityKilledPlayerTrigger(String identifier, CriterionTesters.PlayerKillTester tester) {
        addCriterionInstance(identifier, new CriterionTriggers.EntityKilledPlayerTrigger(tester));
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
            hackAdvancementList();
            addAdvancements();
        }
    }

    private static void hackAdvancementList() {
        try {
            Field advancements = ReflectionHelper.findField(AdvancementList.class, "advancements", "field_192784_c", "field_192092_b");
            Field modifier = Field.class.getDeclaredField("modifiers");
            modifier.setAccessible(true);
            //No longer final!
            modifier.setInt(advancements, advancements.getModifiers() & ~Modifier.FINAL);
            CustomAdvancementMap map = new CustomAdvancementMap(AdvancementManager.ADVANCEMENT_LIST.advancements);
            advancements.set(AdvancementManager.ADVANCEMENT_LIST, map);
        } catch (NoSuchFieldException | IllegalAccessException | ReflectionHelper.UnableToFindFieldException e) {
            CraftTweakerAPI.logWarning(Arrays.deepToString(AdvancementList.class.getFields()));
            CraftTweakerAPI.logError("Error applying custom advancements", e);
        }
    }
}
