package com.teamacronymcoders.contenttweaker.modules.vanilla.advancements.functions;

import crafttweaker.api.minecraft.CraftTweakerMC;
import net.minecraft.advancements.critereon.*;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityBeacon;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;

import javax.annotation.ParametersAreNonnullByDefault;

public class CriterionTriggers {
    public static class ItemConsumeTrigger extends ConsumeItemTrigger.Instance {

        private final CriterionTesters.ItemConsumeTester tester;

        public ItemConsumeTrigger(CriterionTesters.ItemConsumeTester tester) {
            super(ItemPredicate.ANY);
            this.tester = tester;
        }

        @Override
        @ParametersAreNonnullByDefault
        public boolean test(ItemStack item) {
            return tester.handle(CraftTweakerMC.getIItemStack(item));
        }
    }

    public static class EntityBredTrigger extends BredAnimalsTrigger.Instance {

        private final CriterionTesters.EntityBredTester tester;

        public EntityBredTrigger(CriterionTesters.EntityBredTester tester) {
            super(EntityPredicate.ANY, EntityPredicate.ANY, EntityPredicate.ANY);
            this.tester = tester;
        }

        @Override
        @ParametersAreNonnullByDefault
        public boolean test(EntityPlayerMP player, EntityAnimal parent1In, EntityAnimal parent2In, EntityAgeable childIn) {
            return tester.handle(CraftTweakerMC.getIPlayer(player), CraftTweakerMC.getIEntityAnimal(parent1In), CraftTweakerMC.getIEntityAnimal(parent2In), CraftTweakerMC.getIEntityAgeable(childIn));
        }
    }

    public static class BeaconTrigger extends ConstructBeaconTrigger.Instance {

        private final CriterionTesters.BeaconTester tester;

        public BeaconTrigger(CriterionTesters.BeaconTester tester) {
            super(MinMaxBounds.UNBOUNDED);
            this.tester = tester;
        }

        @Override
        public boolean test(TileEntityBeacon beacon) {
            return tester.handle(beacon.getLevels());
        }
    }

    public static class PlayerKilledEntityTrigger extends KilledTrigger.Instance {

        private final CriterionTesters.PlayerKillTester tester;

        public PlayerKilledEntityTrigger(CriterionTesters.PlayerKillTester tester) {
            super(new ResourceLocation("player_killed_entity"), EntityPredicate.ANY, DamageSourcePredicate.ANY);
            this.tester = tester;
        }

        @Override
        @ParametersAreNonnullByDefault
        public boolean test(EntityPlayerMP player, Entity entity, DamageSource source) {
            return tester.handle(CraftTweakerMC.getIPlayer(player), CraftTweakerMC.getIEntity(entity), CraftTweakerMC.getIDamageSource(source));
        }
    }

    public static class EntityKilledPlayerTrigger extends KilledTrigger.Instance {
        private final CriterionTesters.PlayerKillTester tester;

        public EntityKilledPlayerTrigger(CriterionTesters.PlayerKillTester tester) {
            super(new ResourceLocation("entity_killed_player"), EntityPredicate.ANY, DamageSourcePredicate.ANY);
            this.tester = tester;
        }

        @Override
        @ParametersAreNonnullByDefault
        public boolean test(EntityPlayerMP player, Entity entity, DamageSource source) {
            return tester.handle(CraftTweakerMC.getIPlayer(player), CraftTweakerMC.getIEntity(entity), CraftTweakerMC.getIDamageSource(source));
        }
    }
}
