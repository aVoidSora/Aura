package com.avoidsora.aura.command;

import com.avoidsora.aura.aura.AuraComponent;
import com.avoidsora.aura.init.ModComponents;

import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.RegisterCommandsEvent;

@EventBusSubscriber(modid = "aura") // Make sure this matches your mod ID
public class AuraCommand {

    @SubscribeEvent
    public static void onCommandRegister(RegisterCommandsEvent event) {
        register(event.getDispatcher());
    }

    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("aura")
                .then(Commands.literal("get")
                        .executes(context -> {
                            CommandSourceStack source = context.getSource();

                            if (source.getEntity() instanceof ServerPlayer player) {
                                AuraComponent aura = player.getCapability(ModComponents.AURA);
                                if (aura != null) {
                                    int current = aura.getAura();
                                    int max = aura.getMaxAura();
                                    player.sendSystemMessage(Component.literal("Aura: " + current + " / " + max));
                                } else {
                                    player.sendSystemMessage(Component.literal("Aura capability not found."));
                                }
                                return 1;
                            }

                            source.sendFailure(Component.literal("Must be a player"));
                            return 0;
                        }))
        );
    }
}