package com.teamacronymcoders.contenttweaker.modules.materials.parttypes;

import com.teamacronymcoders.base.materialsystem.parts.PartType;

public class CTPartType implements IPartType {
    private PartType partType;

    public CTPartType(PartType partType) {
        this.partType = partType;
    }

    @Override
    public String getName() {
        return this.partType.getName();
    }

    @Override
    public Object getInternal() {
        return this.partType;
    }
}