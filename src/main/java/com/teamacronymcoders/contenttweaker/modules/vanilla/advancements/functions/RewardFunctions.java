package com.teamacronymcoders.contenttweaker.modules.vanilla.advancements.functions;

import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.player.IPlayer;
import stanhebben.zenscript.annotations.ZenClass;

public class RewardFunctions {

    @ZenClass("mods.contenttweaker.advancements.reward.OnCompleted")
    @ZenRegister
    public interface OnCompleted {
        void handle(IPlayer player);
    }
}
