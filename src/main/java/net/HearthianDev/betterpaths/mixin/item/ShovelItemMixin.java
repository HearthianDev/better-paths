package net.HearthianDev.betterpaths.mixin.item;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ShovelItem;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.CampfireBlock;
import net.minecraft.world.level.block.LevelEvent;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Map;

import static net.HearthianDev.betterpaths.util.BlockStateUtil.BlockAllowedAboveDirtPathBlock;

@Mixin(ShovelItem.class)
public class ShovelItemMixin {
    @Final
    @Shadow
    protected static Map<Block, BlockState> FLATTENABLES;

    @Inject(method = "useOn", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/context/UseOnContext;getLevel()Lnet/minecraft/world/level/Level;"), cancellable = true)
    public void useOnBlockMixin(UseOnContext context, CallbackInfoReturnable<InteractionResult> cir) {
        Level world = context.getLevel();
        BlockPos blockPos = context.getClickedPos();
        BlockState blockState = world.getBlockState(blockPos);

        if (context.getClickedFace() != Direction.DOWN) {
            Player playerEntity = context.getPlayer();
            BlockState blockState2 = FLATTENABLES.get(blockState.getBlock());
            BlockState blockState3 = null;
            BlockState blockStateBlockUp = world.getBlockState(blockPos.above());

            if (blockState2 != null && BlockAllowedAboveDirtPathBlock(blockStateBlockUp)) {
                world.playSound(playerEntity, blockPos, SoundEvents.SHOVEL_FLATTEN, SoundSource.BLOCKS, 1.0f, 1.0f);
                blockState3 = blockState2;

                if (playerEntity != null) {
                    playerEntity.swing(context.getHand());
                }
            } else if (blockState.getBlock() instanceof CampfireBlock && blockState.getValue(CampfireBlock.LIT)) {
                if (!world.isClientSide()) {
                    world.levelEvent(null, LevelEvent.SOUND_EXTINGUISH_FIRE, blockPos, 0);
                }
                CampfireBlock.dowse(context.getPlayer(), world, blockPos, blockState);
                blockState3 = blockState.setValue(CampfireBlock.LIT, false);

                if (playerEntity != null) {
                    playerEntity.swing(context.getHand());
                }
            }

            if (blockState3 != null) {
                if (!world.isClientSide()) {
                    world.setBlock(blockPos, blockState3, Block.UPDATE_ALL | Block.UPDATE_IMMEDIATE);
                    world.gameEvent(GameEvent.BLOCK_CHANGE, blockPos, GameEvent.Context.of(playerEntity, blockState3));
                    if (playerEntity != null) {
                        context.getItemInHand().hurtAndBreak(1, playerEntity, context.getHand().asEquipmentSlot());
                        playerEntity.swing(context.getHand());
                    }
                }
                cir.setReturnValue(InteractionResult.SUCCESS);
            }
        }
    }
}
