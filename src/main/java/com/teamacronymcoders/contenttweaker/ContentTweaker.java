package com.teamacronymcoders.contenttweaker;

import com.teamacronymcoders.base.BaseModFoundation;
import com.teamacronymcoders.base.subblocksystem.SubBlockSystem;
import com.teamacronymcoders.base.util.OreDictUtils;
import com.teamacronymcoders.contenttweaker.api.ContentTweakerAPI;
import com.teamacronymcoders.contenttweaker.modules.vanilla.advancements.CoTAdvancementBuilder;
import crafttweaker.CraftTweakerAPI;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;

import java.util.Map;

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
        scriptsSuccessful = CraftTweakerAPI.tweaker.loadScript(false, "contenttweaker");
    }

    @EventHandler
    @Override
    public void init(FMLInitializationEvent event) {
        super.init(event);
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
        AdvancementManager.ADVANCEMENT_LIST.advancements.putAll(CoTAdvancementBuilder.map);
        for(Map.Entry<ResourceLocation, Advancement> entry : CoTAdvancementBuilder.map.entrySet()) {
            if(entry.getValue().getParent() == null)
                AdvancementManager.ADVANCEMENT_LIST.roots.add(entry.getValue());
            else
                AdvancementManager.ADVANCEMENT_LIST.nonRoots.add(entry.getValue());
        }
        CraftTweakerAPI.logError("TESTS");
    }
}
