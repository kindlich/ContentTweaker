package com.teamacronymcoders.contenttweaker.modules.vanilla.advancements;

import crafttweaker.api.formatting.IFormattedText;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.minecraft.CraftTweakerMC;
import net.minecraft.advancements.DisplayInfo;
import net.minecraft.advancements.FrameType;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextComponentString;

import javax.annotation.Nullable;

public class CoTDisplayInfo extends DisplayInfo {
    private final IItemStack icon;
    private final ResourceLocation background;
    private final boolean showToast;
    private final int posX;
    private final int posY;

    public CoTDisplayInfo(IItemStack icon, IFormattedText title, IFormattedText description, @Nullable ResourceLocation background, FrameType frame, boolean showToast, boolean announceToChat, boolean hidden, int posX, int posY) {
        super(ItemStack.EMPTY,
                new TextComponentString(title == null ? "" : title.getText()),
                new TextComponentString(description == null ? "" : description.getText()),
                background,
                frame,
                showToast,
                announceToChat,
                hidden);
        this.background = background;
        this.showToast = showToast;
        this.posX = posX;
        this.posY = posY;

        setPosition(posX, posY);

        this.icon = icon;
    }

    @Override
    public ItemStack getIcon() {
        return CraftTweakerMC.getItemStack(icon);
    }

    @Override
    public void write(PacketBuffer buf) {
        buf.writeTextComponent(this.getTitle());
        buf.writeTextComponent(this.getDescription());
        buf.writeItemStack(this.getIcon());
        buf.writeEnumValue(this.getFrame());
        int i = 0;

        if (this.background != null)
        {
            i |= 1;
        }

        if (this.showToast)
        {
            i |= 2;
        }

        if (this.isHidden())
        {
            i |= 4;
        }

        buf.writeInt(i);

        if (this.background != null)
        {
            buf.writeResourceLocation(this.background);
        }

        buf.writeFloat(this.posX);
        buf.writeFloat(this.posY);
    }
}
