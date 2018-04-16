package com.teamacronymcoders.contenttweaker.modules.vanilla.advancements.triggers;


import com.teamacronymcoders.contenttweaker.modules.vanilla.advancements.functions.CustomTrigger;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.player.IPlayer;
import stanhebben.zenscript.annotations.Optional;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenClass("mods.contenttweaker.advancements.CustomTrigger")
@ZenRegister
public class CoTTrigger {

    @ZenMethod
    public static void create(String id, @Optional TestGrantFunction function) {
        CustomTrigger.createTrigger(id).setTestFunction(function);
    }


    @ZenClass("mods.contenttweaker.advancements.CustomTriggerGrantTest")
    @ZenRegister
    public interface TestGrantFunction {
        boolean handle(IPlayer player);
    }
}
