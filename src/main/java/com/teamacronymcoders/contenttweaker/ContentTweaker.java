package com.teamacronymcoders.contenttweaker;

import com.teamacronymcoders.base.BaseModFoundation;
import com.teamacronymcoders.base.subblocksystem.SubBlockSystem;
import com.teamacronymcoders.base.util.OreDictUtils;
import com.teamacronymcoders.contenttweaker.api.ContentTweakerAPI;
import com.teamacronymcoders.contenttweaker.modules.vanilla.advancements.CoTAdvancementBuilder;
import com.teamacronymcoders.contenttweaker.modules.vanilla.advancements.functions.CustomTrigger;
import crafttweaker.CraftTweakerAPI;
import crafttweaker.mc1120.brackets.BracketHandlerEntity;
import crafttweaker.mc1120.brackets.BracketHandlerItem;
import crafttweaker.mc1120.brackets.BracketHandlerLiquid;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;

import static com.teamacronymcoders.contenttweaker.ContentTweaker.*;

@Mod(modid = MOD_ID, name = MOD_NAME, version = VERSION, dependencies = DEPENDS)
public class ContentTweaker extends BaseModFoundation<ContentTweaker> {
    public static final String MOD_ID = "contenttweaker";
    public static final String MOD_NAME = "ContentTweaker";
    public static final String VERSION = "@VERSION@";
    public static final String DEPENDS = "required-after:base@[0.0.0,);required-after:crafttweaker;";

    @Instance(MOD_ID)
    public static ContentTweaker instance;

    public static boolean scriptsSuccessful;

    public ContentTweaker() {
        super(MOD_ID, MOD_NAME, VERSION, null, false);
        ContentTweakerAPI.setInstance(new ContentTweakerAPI(new ModWrapper()));
        this.subBlockSystem = new SubBlockSystem(this);
        this.materialUser = new CTMaterialUser(this);
        OreDictUtils.addDefaultModId(MOD_ID);
    }

    @EventHandler
    @Override
    public void preInit(FMLPreInitializationEvent event) {
        super.preInit(event);
    }

    @Override
    public void afterModuleHandlerInit(FMLPreInitializationEvent event) {
        BracketHandlerItem.rebuildItemRegistry();
        BracketHandlerLiquid.rebuildLiquidRegistry();
        BracketHandlerEntity.rebuildEntityRegistry();
        scriptsSuccessful = CraftTweakerAPI.tweaker.loadScript(false, "contenttweaker");
    }

    @EventHandler
    @Override
    public void init(FMLInitializationEvent event) {
        super.init(event);
        CustomTrigger.TRIGGERS.values().forEach(CriteriaTriggers::register);
    }

    @EventHandler
    @Override
    public void postInit(FMLPostInitializationEvent event) {
        super.postInit(event);
    }

    @Override
    public boolean addOBJDomain() {
        return true;
    }

    @Override
    public boolean hasExternalResources() {
        return true;
    }

    @Override
    public ContentTweaker getInstance() {
        return this;
    }

    @EventHandler
    public void serverAboutToStart(FMLServerStartingEvent event) {
        CoTAdvancementBuilder.handleAdvancements();
    }
}
