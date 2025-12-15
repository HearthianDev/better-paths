package net.HearthianDev.betterpaths.util;

import net.minecraft.core.Direction;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.AttachFace;
import net.minecraft.world.level.block.state.properties.Half;
import net.minecraft.world.level.block.state.properties.SlabType;

import static net.minecraft.world.level.block.SlabBlock.TYPE;
import static net.minecraft.world.level.block.StairBlock.HALF;

public class BlockStateUtil {
    public static boolean BlockAllowedAboveDirtPathBlock(BlockState blockState) {
        Block block = blockState.getBlock();

        return !blockState.isSolid()
                || blockState.isAir()
                || blockState.is(BlockTags.FENCE_GATES)
                || blockState.is(BlockTags.TRAPDOORS)
                || blockState.is(BlockTags.WALL_SIGNS)
                || blockState.is(BlockTags.CAVE_VINES)
                || blockState.is(BlockTags.ALL_HANGING_SIGNS)
                || block instanceof BellBlock
                || block instanceof IronBarsBlock
                || block instanceof ConduitBlock
                || block instanceof WebBlock
                || block instanceof BaseCoralFanBlock
                || block instanceof WallBannerBlock
                || (block instanceof LanternBlock && blockState.getValue(LanternBlock.HANGING).equals(true))
                || (block instanceof PointedDripstoneBlock && blockState.getValue(PointedDripstoneBlock.TIP_DIRECTION).equals(Direction.DOWN))
                || (block instanceof ChainBlock && !blockState.getValue(ChainBlock.AXIS).equals(Direction.Axis.Y))
                || (block instanceof AmethystClusterBlock && !blockState.getValue(AmethystClusterBlock.FACING).equals(Direction.UP))
                || (block instanceof GrindstoneBlock && !blockState.getValue(FaceAttachedHorizontalDirectionalBlock.FACE).equals(AttachFace.FLOOR))
                || (block instanceof LightningRodBlock && !blockState.getValue(LightningRodBlock.FACING).equals(Direction.UP) && !blockState.getValue(LightningRodBlock.FACING).equals(Direction.DOWN))
                || (block instanceof HopperBlock && !blockState.getValue(HopperBlock.FACING).equals(Direction.DOWN))
                || (block instanceof StairBlock && blockState.getValue(HALF).equals(Half.TOP))
                || (block instanceof SlabBlock && blockState.getValue(TYPE).equals(SlabType.TOP));
    }
}
