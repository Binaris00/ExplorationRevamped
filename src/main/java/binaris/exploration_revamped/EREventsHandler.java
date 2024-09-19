package binaris.exploration_revamped;

import binaris.exploration_revamped.item.RerollItem;
import binaris.exploration_revamped.mixin.accessor.MerchantEntityAccessor;
import binaris.exploration_revamped.mixin.accessor.VillagerEntityInvoker;
import net.fabricmc.fabric.api.event.player.AttackEntityCallback;
import net.fabricmc.fabric.api.event.player.UseEntityCallback;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.entity.passive.WanderingTraderEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.ActionResult;
import net.minecraft.village.TradeOffer;
import net.minecraft.village.TradeOfferList;

public final class EREventsHandler {
    public EREventsHandler() {
    }

    public static void init() {
        UseEntityCallback.EVENT.register((player, world, hand, entity, hitResult) -> {
            if (world.isClient) {
                return ActionResult.PASS;
            }

            if (entity instanceof VillagerEntity villager) {
                for (TradeOffer offer : villager.getOffers()) {
                    offer.uses = 0;
                    offer.maxUses = Integer.MAX_VALUE;
                    offer.demandBonus = 0;
                }
            }
            else if (entity instanceof WanderingTraderEntity wanderingTrader) {
                for (TradeOffer offer : wanderingTrader.getOffers()) {
                    offer.uses = 0;
                    offer.maxUses = Integer.MAX_VALUE;
                    offer.demandBonus = 0;
                }
            }

            return ActionResult.PASS;
        });

        AttackEntityCallback.EVENT.register((player, world, hand, entity, hitResult) -> {

            if(entity instanceof VillagerEntity villager && player.getMainHandStack().getItem() instanceof RerollItem rerollItem && player.getItemCooldownManager().isCoolingDown(rerollItem)){
                if (world.isClient) {
                    villager.produceParticles(ParticleTypes.HAPPY_VILLAGER);
                    return ActionResult.SUCCESS;
                }

                TradeOfferList offer = new TradeOfferList();
                villager.setExperience(0);
                ((MerchantEntityAccessor)villager).setOffers(offer);
                ((VillagerEntityInvoker) villager).invokeFillRecipes();
                ((VillagerEntityInvoker) villager).invokePrepareOffersFor(player);

                ItemStack stack = player.getMainHandStack();
                stack.decrement(1);
                player.getItemCooldownManager().set(stack.getItem(), 140);

                return ActionResult.SUCCESS;
            }
            else if(entity instanceof VillagerEntity villager){
                if(world.isClient) villager.produceParticles(ParticleTypes.ANGRY_VILLAGER);
                return ActionResult.PASS;
            } else {
                return ActionResult.PASS;
            }

        });
    }
}
