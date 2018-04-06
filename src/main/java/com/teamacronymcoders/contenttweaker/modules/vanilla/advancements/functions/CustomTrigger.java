package com.teamacronymcoders.contenttweaker.modules.vanilla.advancements.functions;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.teamacronymcoders.contenttweaker.modules.vanilla.advancements.triggers.CoTTrigger;
import crafttweaker.api.minecraft.CraftTweakerMC;
import crafttweaker.api.player.IPlayer;
import io.netty.util.internal.ConcurrentSet;
import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.advancements.ICriterionInstance;
import net.minecraft.advancements.ICriterionTrigger;
import net.minecraft.advancements.PlayerAdvancements;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class CustomTrigger implements ICriterionTrigger<CustomTrigger.Instance> {

    /**
     * Static map that contains all registered custom triggers
     */
    public static final Map<String, CustomTrigger> TRIGGERS = new HashMap<>();


    private final ResourceLocation id;
    private final Set<Listener<Instance>> listeners = new ConcurrentSet<>();
    private final Instance instance = new Instance();

    static {
        //createTrigger("contenttweaker:kindlich_custom");
    }

    public static CustomTrigger createTrigger(String id) {
        return createTrigger(new ResourceLocation(id));
    }

    public static CustomTrigger createTrigger(ResourceLocation id) {
        CustomTrigger trigger = new CustomTrigger(id);
        TRIGGERS.put(id.toString(), trigger);
        return trigger;
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

    public void setTestFunction(@Nullable CoTTrigger.TestGrantFunction function) {
        this.instance.setFunction(function);
    }

    public class Instance implements ICriterionInstance {

        private CoTTrigger.TestGrantFunction function;

        public boolean test(IPlayer player) {
            return function == null || function.handle(player);
        }

        @Override
        public ResourceLocation getId() {
            return CustomTrigger.this.id;
        }

        public void trigger(@Nullable EntityPlayerMP player) {
            if (player != null) {
                CustomTrigger.this.listeners.stream().filter(listener -> listener.getCriterionInstance().test(CraftTweakerMC.getIPlayer(player))).forEach(listener -> listener.grantCriterion(player.getAdvancements()));
            }
        }

        public void setFunction(@Nullable CoTTrigger.TestGrantFunction function) {
            this.function = function;
        }
    }
}
