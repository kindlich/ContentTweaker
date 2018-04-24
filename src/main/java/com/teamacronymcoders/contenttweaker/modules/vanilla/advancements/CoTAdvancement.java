package com.teamacronymcoders.contenttweaker.modules.vanilla.advancements;

import com.teamacronymcoders.contenttweaker.modules.vanilla.advancements.representation.AdvancementRepresentation;
import crafttweaker.api.formatting.IFormattedText;
import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementManager;
import net.minecraft.advancements.Criterion;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Map;

@MethodsReturnNonnullByDefault
public class CoTAdvancement extends Advancement {
    private final IFormattedText displayText;

    public CoTAdvancement(ResourceLocation id, @Nullable AdvancementRepresentation parentIn, @Nullable CoTDisplayInfo displayIn, CoTAdvancementRewards rewardsIn, Map<String, Criterion> criteriaIn, String[][] requirementsIn, IFormattedText displayText) {
        super(id, parentIn == null ? null : parentIn.getInternal(), displayIn, rewardsIn, criteriaIn, requirementsIn);
        this.displayText = displayText;
    }

    public CoTAdvancement(ResourceLocation id, @Nullable String parentIn, @Nullable CoTDisplayInfo displayIn, CoTAdvancementRewards rewardsIn, Map<String, Criterion> criteriaIn, String[][] requirementsIn, IFormattedText displayText) {
        super(id, parentIn == null ? null : AdvancementManager.ADVANCEMENT_LIST.getAdvancement(new ResourceLocation(parentIn)), displayIn, rewardsIn, criteriaIn, requirementsIn);
        this.displayText = displayText;
    }

    @Override
    public ITextComponent getDisplayText() {
        if (displayText != null) {
            return new TextComponentString(displayText.getText());
        }
        return super.getDisplayText();
    }

    @Override
    public Builder copy() {
        return new CoTAdvancement.Builder(this);
    }


    @ParametersAreNonnullByDefault
    public static class Builder extends Advancement.Builder {

        private final CoTAdvancement advancement;

        public Builder(CoTAdvancement advancement) {
            super(advancement.getParent() == null ? null : advancement.getParent().getId(), advancement.getDisplay(), advancement.getRewards(), advancement.getCriteria(), advancement.getRequirements());
            this.advancement = advancement;
        }

        @Override
        public Advancement build(ResourceLocation id) {
            return advancement;
        }
    }
}
