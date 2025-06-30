package com.avoidsora.aura.aura;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.core.HolderLookup;
import net.neoforged.neoforge.common.util.INBTSerializable;

public class AuraComponent implements INBTSerializable<CompoundTag> {
    private int aura;         // Current aura
    private int maxAura;      // Max aura
    private float discount;   // Aura cost reduction (0.0 - 1.0)
    private float regenRate;  // Aura regen per second (controlled manually)

    public AuraComponent(int baseMaxAura) {
        this.maxAura = baseMaxAura;
        this.aura = baseMaxAura;
        this.discount = 0.0f;
        this.regenRate = 0.0f;
    }

    // === Getters ===
    public int getAura() { return aura; }
    public int getMaxAura() { return maxAura; }
    public float getDiscount() { return discount; }
    public float getRegenRate() { return regenRate; }

    // === Setters ===
    public void setAura(int value) {
        aura = Math.min(value, maxAura);
    }

    public void setMaxAura(int value) {
        maxAura = value;
        if (aura > maxAura) aura = maxAura;
    }

    public void setDiscount(float value) {
        discount = Math.max(0f, Math.min(value, 1f));
    }

    public void setRegenRate(float value) {
        regenRate = Math.max(0f, value);
    }

    // === Aura Logic ===
    public boolean consumeAura(int baseCost) {
        int actualCost = Math.max(1, Math.round(baseCost * (1.0f - discount)));
        if (aura >= actualCost) {
            aura -= actualCost;
            return true;
        }
        return false;
    }

    public void addAura(int amount) {
        aura = Math.min(aura + amount, maxAura);
    }

    public void reset() {
        this.aura = maxAura;
        this.discount = 0.0f;
        this.regenRate = 0.0f;
    }

    // === Save/Load NBT ===
    @Override
    public CompoundTag serializeNBT(HolderLookup.Provider provider) {
        CompoundTag tag = new CompoundTag();
        tag.putInt("Aura", aura);
        tag.putInt("MaxAura", maxAura);
        tag.putFloat("Discount", discount);
        tag.putFloat("RegenRate", regenRate);
        return tag;
    }

    @Override
    public void deserializeNBT(HolderLookup.Provider provider, CompoundTag tag) {
        aura = tag.getInt("Aura");
        maxAura = tag.getInt("MaxAura");
        discount = tag.getFloat("Discount");
        regenRate = tag.getFloat("RegenRate");
    }
}