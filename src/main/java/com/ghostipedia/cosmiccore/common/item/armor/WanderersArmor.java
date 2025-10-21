package com.ghostipedia.cosmiccore.common.item.armor;

import com.ghostipedia.cosmiccore.common.data.tag.item.CosmicItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import sfiomn.legendarysurvivaloverhaul.api.temperature.TemperatureUtil;

import java.util.UUID;
import java.util.stream.StreamSupport;

public class WanderersArmor extends ArmorItem {
    public static final UUID thermalResModifierID = UUID.fromString("67ef2a37-917f-4cbb-81b3-c4d7834ab597");
    private boolean hasAppliedRes = false;
    public WanderersArmor(ArmorMaterial material, Type type, Properties properties) {
        super(material, type, properties);
    }

    @Override
    public void onArmorTick(ItemStack stack, Level level, Player player) {
        super.onArmorTick(stack, level, player);

        if (hasFullSet(player, CosmicItemTags.WANDERERS_ARMOR))
        {
            if (!hasAppliedRes)
            {
                TemperatureUtil.internal.addThermalResistanceModifier(player, 500.0, thermalResModifierID);
                hasAppliedRes = true;
            }
        }
        else
        {
            if (hasAppliedRes)
            {
                TemperatureUtil.internal.addThermalResistanceModifier(player, -500.0, thermalResModifierID);
                hasAppliedRes = false;
            }
        }
    }

    static boolean hasFullSet(LivingEntity entity, TagKey<Item> tagKey) {
        return StreamSupport.stream(entity.getArmorSlots().spliterator(), false)
                .allMatch(itemStack -> itemStack.is(tagKey));
    }
}
