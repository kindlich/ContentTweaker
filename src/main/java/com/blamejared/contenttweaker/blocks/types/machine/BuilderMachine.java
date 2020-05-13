package com.blamejared.contenttweaker.blocks.types.machine;

import com.blamejared.contenttweaker.*;
import com.blamejared.contenttweaker.api.blocks.*;
import com.blamejared.contenttweaker.blocks.*;
import com.blamejared.contenttweaker.blocks.types.machine.capability.*;
import com.blamejared.contenttweaker.blocks.types.machine.gui.capability.builder.*;
import com.blamejared.crafttweaker.api.*;
import com.blamejared.crafttweaker.api.annotations.*;
import com.blamejared.crafttweaker.impl.util.*;
import org.openzen.zencode.java.*;

import java.util.function.*;

@ZenRegister
@ZenCodeType.Name("mods.contenttweaker.block.machine.BuilderMachine")
public class BuilderMachine extends BlockTypeBuilder {
    
    private final CoTCapabilityConfigurationManager capabilities;
    
    public BuilderMachine(BlockBuilder blockBuilder) {
        super(blockBuilder);
        this.capabilities = new CoTCapabilityConfigurationManager();
    }
    
    
    public CoTCapabilityConfigurationManager getCapabilities() {
        return capabilities;
    }
    
    @ZenCodeType.Method
    public <T extends IIsCoTCapabilityBuilder> BuilderMachine buildCapability(Class<T> typeofT, Function<T, T> fun) {
        try {
            final T instance = typeofT.newInstance();
            final T apply = fun.apply(instance);
            apply.applyToBuilder(this);
        } catch(InstantiationException | IllegalAccessException e) {
            CraftTweakerAPI.logThrowing("Error applying capability: ", e);
        }
        return this;
    }
    
    @ZenCodeType.Method
    public BuilderMachine configureGui(Function<GuiCapabilityBuilder, GuiCapabilityBuilder> fun) {
        return buildCapability(GuiCapabilityBuilder.class, fun);
    }
    
    
    @Override
    public void build(MCResourceLocation location) {
        final CoTBlockTile coTBlockTile = new CoTBlockTile(this, location);
        VanillaFactory.registerBlock(coTBlockTile);
    }
    
    public boolean hasCapability(ICotCapability capability) {
        return capabilities.hasCapability(capability);
    }
    
    public void addCapability(ICotCapability capability, ICoTCapabilityConfiguration configuration) {
        this.capabilities.addCapability(capability, configuration);
    }
}
