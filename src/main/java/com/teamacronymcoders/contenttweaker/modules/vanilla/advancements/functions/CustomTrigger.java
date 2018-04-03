package com.teamacronymcoders.contenttweaker.modules.vanilla.advancements.functions;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import crafttweaker.api.minecraft.CraftTweakerMC;
import crafttweaker.api.player.IPlayer;
import net.minecraft.advancements.ICriterionInstance;
import net.minecraft.advancements.ICriterionTrigger;
import net.minecraft.advancements.PlayerAdvancements;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.ResourceLocation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CustomTrigger implements ICriterionTrigger<CustomTrigger.Instance> {

    /**
     * Static map that contains all registered custom triggers
     */
    public static final Map<String, CustomTrigger> TRIGGERS = new HashMap<>();


    private final ResourceLocation id;
    private final List<Listener<Instance>> listeners = new ArrayList<>();
    private final Instance instance = new Instance();

    static {
        //createTrigger("contenttweaker:kindlich_custom");
    }

    public static void createTrigger(String id) {
        createTrigger(new ResourceLocation(id));
    }

    public static void createTrigger(ResourceLocation id) {
        CustomTrigger trigger = new CustomTrigger(id);
        TRIGGERS.put(id.toString(), trigger);
    }

    public CustomTrigger(ResourceLocation id) {
        this.id = id;
    }


    @Override
    public ResourceLocation getId() {
        return id;
    }

    @Override
    public void addListener(PlayerAdvancements playerAdvancementsIn, Listener<Instance> listener) {
        listeners.add(listener);
    }

    @Override
    public void removeListener(PlayerAdvancements playerAdvancementsIn, Listener<Instance> listener) {
        listeners.remove(listener);
    }

    @Override
    public void removeAllListeners(PlayerAdvancements playerAdvancementsIn) {
        listeners.clear();
    }

    @Override
    public Instance deserializeInstance(JsonObject json, JsonDeserializationContext context) {
        return getInstance();
    }

    public Instance getInstance() {
        return instance;
    }

    public class Instance implements ICriterionInstance {

        public boolean test(IPlayer player) {
            return true;
        }

        @Override
        public ResourceLocation getId() {
            return CustomTrigger.this.id;
        }

        public void trigger(EntityPlayerMP player) {
            for (Listener<Instance> listener : CustomTrigger.this.listeners) {
                if (listener.getCriterionInstance().test(CraftTweakerMC.getIPlayer(player))) {
                    listener.grantCriterion(player.getAdvancements());
                }
            }
        }
    }
}
