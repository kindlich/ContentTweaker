package com.teamacronymcoders.contenttweaker.modules.vanilla.advancements;

import crafttweaker.api.formatting.IFormattedText;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.minecraft.CraftTweakerMC;
import net.minecraft.advancements.DisplayInfo;
import net.minecraft.advancements.FrameType;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextComponentString;

import javax.annotation.Nullable;

public class CoTDisplayInfo extends DisplayInfo {
    private final IItemStack icon;

    public CoTDisplayInfo(IItemStack icon, IFormattedText title, IFormattedText description, @Nullable ResourceLocation background, FrameType frame, boolean showToast, boolean announceToChat, boolean hidden, int posX, int posY) {
        super(ItemStack.EMPTY,
                new TextComponentString(title == null ? "" : title.getText()),
                new TextComponentString(description == null ? "" : description.getText()),
                background,
                frame,
                showToast,
                announceToChat,
                hidden);

        setPosition(posX, posY);

        this.icon = icon;
    }

    @Override
    public ItemStack getIcon() {
        return CraftTweakerMC.getItemStack(icon);
    }
}
