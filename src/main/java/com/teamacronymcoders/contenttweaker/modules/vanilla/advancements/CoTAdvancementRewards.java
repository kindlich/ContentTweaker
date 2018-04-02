package com.teamacronymcoders.contenttweaker.modules.vanilla.advancements;

import com.teamacronymcoders.contenttweaker.modules.vanilla.advancements.functions.RewardFunctions;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.minecraft.CraftTweakerMC;
import crafttweaker.api.player.IPlayer;
import net.minecraft.advancements.AdvancementRewards;
import net.minecraft.command.FunctionObject;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.ResourceLocation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CoTAdvancementRewards extends AdvancementRewards {
    private final List<IItemStack> loot = new ArrayList<>();
    private final List<ResourceLocation> recipes = new ArrayList<>();
    RewardFunctions.OnCompleted onCompleted = null;

    public CoTAdvancementRewards(int experience) {
        super(experience, new ResourceLocation[0], new ResourceLocation[0], FunctionObject.CacheableFunction.EMPTY);
    }

    public void addLoot(IItemStack... items) {
        loot.addAll(Arrays.asList(items));
    }

    public void addRecipe(ResourceLocation... resourceLocations) {
        recipes.addAll(Arrays.asList(resourceLocations));

    }

    public void addRecipe(IRecipe... recipes) {
        for (IRecipe recipe : recipes) {
            this.recipes.add(recipe.getRegistryName());
        }
    }

    @Override
    public void apply(EntityPlayerMP player) {
        super.apply(player);
        IPlayer iPlayer = CraftTweakerMC.getIPlayer(player);
        loot.forEach(iPlayer::give);
        if (recipes.size() != 0)
            player.unlockRecipes(recipes.toArray(new ResourceLocation[recipes.size()]));
        if (onCompleted != null)
            onCompleted.handle(iPlayer);
    }
}
