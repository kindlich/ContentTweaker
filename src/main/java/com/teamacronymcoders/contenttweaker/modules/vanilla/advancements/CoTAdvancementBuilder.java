package com.teamacronymcoders.contenttweaker.modules.vanilla.advancements;

import com.teamacronymcoders.contenttweaker.modules.vanilla.advancements.functions.CriterionTriggers;
import com.teamacronymcoders.contenttweaker.modules.vanilla.advancements.functions.RewardFunctions;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.formatting.IFormattedText;
import crafttweaker.api.item.IIngredient;
import crafttweaker.api.item.IItemStack;
import net.minecraft.advancements.*;
import net.minecraft.advancements.critereon.ConsumeItemTrigger;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.item.ItemStack;
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

    private void addItemTriggerCriterion(String identifier, ICriterionInstance trigger) {
        criteria.put(identifier, new Criterion(trigger));
    }

    @ZenMethod
    public void addItemConsumeCriterion(String identifier, IIngredient ingredient) {
        addItemTriggerCriterion(identifier, new CriterionTriggers.ItemConsumeTrigger(ingredient));
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


        //TODO criteria
        Criterion criterion = new Criterion(new ConsumeItemTrigger.Instance(new ItemPredicate(){
            @Override
            public boolean test(ItemStack item) {
                return true;
            }
        }));

        CoTAdvancement advancement = new CoTAdvancement(id, parent, displayInfo, rewards, criteria, requirements, displayText);



        AdvancementManager.ADVANCEMENT_LIST.advancements.put(id, advancement);
        if(parent == null)
            AdvancementManager.ADVANCEMENT_LIST.roots.add(advancement);
        else
            AdvancementManager.ADVANCEMENT_LIST.nonRoots.add(advancement);
        map.put(id, advancement);
    }

    public static final Map<ResourceLocation, Advancement> map = new HashMap<>();
}
