package net.betterpaths.util;

import net.minecraft.block.*;
import net.minecraft.block.enums.BlockFace;
import net.minecraft.block.enums.BlockHalf;
import net.minecraft.block.enums.SlabType;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.util.math.Direction;

import static net.minecraft.block.SlabBlock.TYPE;
import static net.minecraft.block.StairsBlock.HALF;

public class BlockStateUtil {
	public static boolean BlockAllowedAboveDirtPathBlock(BlockState blockState) {
		Block block = blockState.getBlock();

		return !blockState.isSolid()
      || blockState.isAir()
      || blockState.isIn(BlockTags.FENCE_GATES)
      || blockState.isIn(BlockTags.TRAPDOORS)
      || blockState.isIn(BlockTags.WALL_SIGNS)
      || blockState.isIn(BlockTags.CAVE_VINES)
      || blockState.isIn(BlockTags.ALL_HANGING_SIGNS)
      || block instanceof BellBlock
      || block instanceof PaneBlock
      || block instanceof ConduitBlock
      || block instanceof CobwebBlock
      || block instanceof DeadCoralFanBlock
      || block instanceof WallBannerBlock
      || (block instanceof LanternBlock && blockState.get(LanternBlock.HANGING).equals(true))
      || (block instanceof PointedDripstoneBlock && blockState.get(PointedDripstoneBlock.VERTICAL_DIRECTION).equals(Direction.DOWN))
      || (block instanceof ChainBlock && !blockState.get(ChainBlock.AXIS).equals(Direction.Axis.Y))
      || (block instanceof AmethystClusterBlock && !blockState.get(AmethystClusterBlock.FACING).equals(Direction.UP))
      || (block instanceof GrindstoneBlock && !blockState.get(WallMountedBlock.FACE).equals(BlockFace.FLOOR))
      || (
        block instanceof LightningRodBlock
          && !blockState.get(LightningRodBlock.FACING).equals(Direction.UP)
          && !blockState.get(LightningRodBlock.FACING).equals(Direction.DOWN)
      )
      || (block instanceof HopperBlock && !blockState.get(HopperBlock.FACING).equals(Direction.DOWN))
      || (block instanceof StairsBlock && blockState.get(HALF).equals(BlockHalf.TOP))
      || (block instanceof SlabBlock && blockState.get(TYPE).equals(SlabType.TOP));
	}
}
