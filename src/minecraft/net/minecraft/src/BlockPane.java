package net.minecraft.src;

import java.util.ArrayList;
import java.util.Random;

public class BlockPane extends Block {
	private int sideTextureIndex;
	private final boolean canDropItself;

	protected BlockPane(int par1, int par2, int par3, Material par4Material, boolean par5) {
		super(par1, par2, par4Material);
		sideTextureIndex = par3;
		canDropItself = par5;
	}

	public int idDropped(int par1, Random par2Random, int par3) {
		if (!canDropItself) {
			return 0;
		} else {
			return super.idDropped(par1, par2Random, par3);
		}
	}

	public boolean isOpaqueCube() {
		return false;
	}

	public boolean renderAsNormalBlock() {
		return false;
	}

	public int getRenderType() {
		return 18;
	}

	public boolean shouldSideBeRendered(IBlockAccess par1IBlockAccess, int par2, int par3, int par4, int par5) {
		int i = par1IBlockAccess.getBlockId(par2, par3, par4);

		if (i == blockID) {
			return false;
		} else {
			return super.shouldSideBeRendered(par1IBlockAccess, par2, par3, par4, par5);
		}
	}

	public void getCollidingBoundingBoxes(World par1World, int par2, int par3, int par4, AxisAlignedBB par5AxisAlignedBB, ArrayList par6ArrayList) {
		boolean flag = canThisPaneConnectToThisBlockID(par1World.getBlockId(par2, par3, par4 - 1));
		boolean flag1 = canThisPaneConnectToThisBlockID(par1World.getBlockId(par2, par3, par4 + 1));
		boolean flag2 = canThisPaneConnectToThisBlockID(par1World.getBlockId(par2 - 1, par3, par4));
		boolean flag3 = canThisPaneConnectToThisBlockID(par1World.getBlockId(par2 + 1, par3, par4));

		if (flag2 && flag3 || !flag2 && !flag3 && !flag && !flag1) {
			setBlockBounds(0.0F, 0.0F, 0.4375F, 1.0F, 1.0F, 0.5625F);
			super.getCollidingBoundingBoxes(par1World, par2, par3, par4, par5AxisAlignedBB, par6ArrayList);
		} else if (flag2 && !flag3) {
			setBlockBounds(0.0F, 0.0F, 0.4375F, 0.5F, 1.0F, 0.5625F);
			super.getCollidingBoundingBoxes(par1World, par2, par3, par4, par5AxisAlignedBB, par6ArrayList);
		} else if (!flag2 && flag3) {
			setBlockBounds(0.5F, 0.0F, 0.4375F, 1.0F, 1.0F, 0.5625F);
			super.getCollidingBoundingBoxes(par1World, par2, par3, par4, par5AxisAlignedBB, par6ArrayList);
		}

		if (flag && flag1 || !flag2 && !flag3 && !flag && !flag1) {
			setBlockBounds(0.4375F, 0.0F, 0.0F, 0.5625F, 1.0F, 1.0F);
			super.getCollidingBoundingBoxes(par1World, par2, par3, par4, par5AxisAlignedBB, par6ArrayList);
		} else if (flag && !flag1) {
			setBlockBounds(0.4375F, 0.0F, 0.0F, 0.5625F, 1.0F, 0.5F);
			super.getCollidingBoundingBoxes(par1World, par2, par3, par4, par5AxisAlignedBB, par6ArrayList);
		} else if (!flag && flag1) {
			setBlockBounds(0.4375F, 0.0F, 0.5F, 0.5625F, 1.0F, 1.0F);
			super.getCollidingBoundingBoxes(par1World, par2, par3, par4, par5AxisAlignedBB, par6ArrayList);
		}
	}

	public void setBlockBoundsForItemRender() {
		setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
	}

	public void setBlockBoundsBasedOnState(IBlockAccess par1IBlockAccess, int par2, int par3, int par4) {
		float f = 0.4375F;
		float f1 = 0.5625F;
		float f2 = 0.4375F;
		float f3 = 0.5625F;
		boolean flag = canThisPaneConnectToThisBlockID(par1IBlockAccess.getBlockId(par2, par3, par4 - 1));
		boolean flag1 = canThisPaneConnectToThisBlockID(par1IBlockAccess.getBlockId(par2, par3, par4 + 1));
		boolean flag2 = canThisPaneConnectToThisBlockID(par1IBlockAccess.getBlockId(par2 - 1, par3, par4));
		boolean flag3 = canThisPaneConnectToThisBlockID(par1IBlockAccess.getBlockId(par2 + 1, par3, par4));

		if (flag2 && flag3 || !flag2 && !flag3 && !flag && !flag1) {
			f = 0.0F;
			f1 = 1.0F;
		} else if (flag2 && !flag3) {
			f = 0.0F;
		} else if (!flag2 && flag3) {
			f1 = 1.0F;
		}

		if (flag && flag1 || !flag2 && !flag3 && !flag && !flag1) {
			f2 = 0.0F;
			f3 = 1.0F;
		} else if (flag && !flag1) {
			f2 = 0.0F;
		} else if (!flag && flag1) {
			f3 = 1.0F;
		}

		setBlockBounds(f, 0.0F, f2, f1, 1.0F, f3);
	}

	public int getSideTextureIndex() {
		return sideTextureIndex;
	}

	public final boolean canThisPaneConnectToThisBlockID(int par1) {
		return Block.opaqueCubeLookup[par1] || par1 == blockID || par1 == Block.glass.blockID;
	}
}
