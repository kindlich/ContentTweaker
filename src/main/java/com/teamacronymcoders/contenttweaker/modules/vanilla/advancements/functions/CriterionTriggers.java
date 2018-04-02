package com.teamacronymcoders.contenttweaker.modules.vanilla.advancements.functions;

import crafttweaker.api.item.IIngredient;
import crafttweaker.api.minecraft.CraftTweakerMC;
import net.minecraft.advancements.critereon.ConsumeItemTrigger;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.item.ItemStack;

public class CriterionTriggers {
    public static class ItemConsumeTrigger extends ConsumeItemTrigger.Instance {

        private final IIngredient item;

        public ItemConsumeTrigger(IIngredient item) {
            super(ItemPredicate.ANY);
            this.item = item;
        }

        @Override
        public boolean test(ItemStack item) {
            return this.item.matches(CraftTweakerMC.getIItemStack(item));
        }
    }
}
