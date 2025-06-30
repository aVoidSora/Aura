package com.avoidsora.aura.init;

import com.avoidsora.aura.Aura;
import com.avoidsora.aura.aura.AuraComponent;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;

import net.neoforged.neoforge.capabilities.EntityCapability;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;

public class ModComponents {
    public static final ResourceLocation AURA_ID = Aura.id("aura");

    public static final EntityCapability<AuraComponent, Void> AURA =
            EntityCapability.create(AURA_ID, AuraComponent.class, Void.class); // ✅ FIXED

    public static void registerCapabilities(RegisterCapabilitiesEvent event) {
        event.registerEntity(AURA, EntityType.PLAYER, (player, ctx) -> new AuraComponent(100));
    }
}