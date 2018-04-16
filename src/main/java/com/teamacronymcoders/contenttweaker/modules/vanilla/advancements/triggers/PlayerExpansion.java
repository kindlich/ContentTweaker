package com.teamacronymcoders.contenttweaker.modules.vanilla.advancements.triggers;

import com.teamacronymcoders.contenttweaker.modules.vanilla.advancements.functions.CustomTrigger;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.minecraft.CraftTweakerMC;
import crafttweaker.api.player.IPlayer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import stanhebben.zenscript.annotations.ZenExpansion;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenExpansion("crafttweaker.player.IPlayer")
@ZenRegister
public class PlayerExpansion {

    @ZenMethod
    public static void grantCustomCriterion(IPlayer player, String name) {
        CustomTrigger trigger = CustomTrigger.TRIGGERS.get(name);
        EntityPlayer ePlayer = CraftTweakerMC.getPlayer(player);
        if (trigger != null) {
            if (ePlayer instanceof EntityPlayerMP) {
                trigger.getInstance().trigger((EntityPlayerMP) ePlayer);
            } else {
                MinecraftServer server = ePlayer.getServer();
                if (server != null) {
                    trigger.getInstance().trigger(server.getPlayerList().getPlayerByUUID(ePlayer.getUniqueID()));
                }

            }
        }


    }
}
