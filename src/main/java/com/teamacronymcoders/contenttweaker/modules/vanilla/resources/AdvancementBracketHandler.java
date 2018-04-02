package com.teamacronymcoders.contenttweaker.modules.vanilla.resources;

import com.teamacronymcoders.contenttweaker.modules.vanilla.advancements.representation.AdvancementRepresentation;
import crafttweaker.annotations.BracketHandler;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.zenscript.GlobalRegistry;
import crafttweaker.zenscript.IBracketHandler;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementManager;
import net.minecraft.util.ResourceLocation;
import stanhebben.zenscript.compiler.IEnvironmentGlobal;
import stanhebben.zenscript.expression.ExpressionCallStatic;
import stanhebben.zenscript.expression.ExpressionString;
import stanhebben.zenscript.parser.Token;
import stanhebben.zenscript.symbols.IZenSymbol;
import stanhebben.zenscript.type.natives.IJavaMethod;
import stanhebben.zenscript.type.natives.JavaMethod;

import java.util.List;
import java.util.stream.Collectors;

@BracketHandler
@ZenRegister
public class AdvancementBracketHandler implements IBracketHandler {

    private static final IJavaMethod method = JavaMethod.get(GlobalRegistry.getTypes(), AdvancementBracketHandler.class, "getAdvancement", String.class);

    @Override
    public IZenSymbol resolve(IEnvironmentGlobal environment, List<Token> tokens) {
        if (tokens.size() < 5 || !tokens.get(0).getValue().equalsIgnoreCase("advancement"))
            return null;
        return position -> new ExpressionCallStatic(position, environment, method, new ExpressionString(position, String.join("", tokens.subList(2, tokens.size()).stream().map(Token::getValue).collect(Collectors.toList()))));
    }

    public static AdvancementRepresentation getAdvancement(String name) {
        Advancement advancement = AdvancementManager.ADVANCEMENT_LIST.advancements.get(new ResourceLocation(name));
        return advancement == null ? null : new AdvancementRepresentation(advancement);
    }
}
