package net.HearthianDev.betterpaths.mixin.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.DirtPathBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import static net.HearthianDev.betterpaths.util.BlockStateUtil.BlockAllowedAboveDirtPathBlock;


@Mixin(DirtPathBlock.class)
public class DirtPathBlockMixin {
    @Inject(method = "canSurvive", at = @At(value = "HEAD"), cancellable = true)
    public void canPlaceAtMixin(BlockState state, LevelReader world, BlockPos pos, CallbackInfoReturnable<Boolean> cir) {
        BlockState blockState = world.getBlockState(pos.above());

        cir.setReturnValue(BlockAllowedAboveDirtPathBlock(blockState));
    }
}
