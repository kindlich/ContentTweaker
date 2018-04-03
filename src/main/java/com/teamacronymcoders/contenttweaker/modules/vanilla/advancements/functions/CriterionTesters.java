package com.teamacronymcoders.contenttweaker.modules.vanilla.advancements.functions;

import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.damage.IDamageSource;
import crafttweaker.api.entity.IEntity;
import crafttweaker.api.entity.IEntityAgeable;
import crafttweaker.api.entity.IEntityAnimal;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.player.IPlayer;
import stanhebben.zenscript.annotations.ZenClass;

public class CriterionTesters {
    @ZenClass("mods.contenttweaker.advancements.ItemConsumeTester")
    @ZenRegister
    public interface ItemConsumeTester {
        boolean handle(IItemStack itemStack);
    }

    @ZenClass("mods.contenttweaker.advancements.EntityBredTester")
    @ZenRegister
    public interface EntityBredTester {
        boolean handle(IPlayer player, IEntityAnimal parent1, IEntityAnimal parent2, IEntityAgeable child);
    }

    @ZenClass("mods.contenttweaker.advancements.BeaconTester")
    @ZenRegister
    public interface BeaconTester {
        boolean handle(int level);
    }

    @ZenClass("mods.contenttweaker.advancements.PlayerKillTester")
    @ZenRegister
    public interface PlayerKillTester {
        boolean handle(IPlayer player, IEntity entity, IDamageSource killingBlow);
    }
}
