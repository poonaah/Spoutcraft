package net.minecraft.src;

public class BlockFence extends Block {
	public BlockFence(int par1, int par2) {
		super(par1, par2, Material.wood);
	}

	public BlockFence(int par1, int par2, Material par3Material) {
		super(par1, par2, par3Material);
	}

	public boolean canPlaceBlockAt(World par1World, int par2, int par3, int par4) {
		return super.canPlaceBlockAt(par1World, par2, par3, par4);
	}

	public AxisAlignedBB getCollisionBoundingBoxFromPool(World par1World, int par2, int par3, int par4) {
		boolean flag = canConnectFenceTo(par1World, par2, par3, par4 - 1);
		boolean flag1 = canConnectFenceTo(par1World, par2, par3, par4 + 1);
		boolean flag2 = canConnectFenceTo(par1World, par2 - 1, par3, par4);
		boolean flag3 = canConnectFenceTo(par1World, par2 + 1, par3, par4);
		float f = 0.375F;
		float f1 = 0.625F;
		float f2 = 0.375F;
		float f3 = 0.625F;

		if (flag) {
			f2 = 0.0F;
		}

		if (flag1) {
			f3 = 1.0F;
		}

		if (flag2) {
			f = 0.0F;
		}

		if (flag3) {
			f1 = 1.0F;
		}

		return AxisAlignedBB.getBoundingBoxFromPool((float)par2 + f, par3, (float)par4 + f2, (float)par2 + f1, (float)par3 + 1.5F, (float)par4 + f3);
	}

	public void setBlockBoundsBasedOnState(IBlockAccess par1IBlockAccess, int par2, int par3, int par4) {
		boolean flag = canConnectFenceTo(par1IBlockAccess, par2, par3, par4 - 1);
		boolean flag1 = canConnectFenceTo(par1IBlockAccess, par2, par3, par4 + 1);
		boolean flag2 = canConnectFenceTo(par1IBlockAccess, par2 - 1, par3, par4);
		boolean flag3 = canConnectFenceTo(par1IBlockAccess, par2 + 1, par3, par4);
		float f = 0.375F;
		float f1 = 0.625F;
		float f2 = 0.375F;
		float f3 = 0.625F;

		if (flag) {
			f2 = 0.0F;
		}

		if (flag1) {
			f3 = 1.0F;
		}

		if (flag2) {
			f = 0.0F;
		}

		if (flag3) {
			f1 = 1.0F;
		}

		setBlockBounds(f, 0.0F, f2, f1, 1.0F, f3);
	}

	public boolean isOpaqueCube() {
		return false;
	}

	public boolean renderAsNormalBlock() {
		return false;
	}

	public boolean getBlocksMovement(IBlockAccess par1IBlockAccess, int par2, int par3, int i) {
		return false;
	}

	public int getRenderType() {
		return 11;
	}

	public boolean canConnectFenceTo(IBlockAccess par1IBlockAccess, int par2, int par3, int par4) {
		int i = par1IBlockAccess.getBlockId(par2, par3, par4);

		if (i == blockID || i == Block.fenceGate.blockID) {
			return true;
		}

		Block block = Block.blocksList[i];

		if (block != null && block.blockMaterial.isOpaque() && block.renderAsNormalBlock()) {
			return block.blockMaterial != Material.pumpkin;
		} else {
			return false;
		}
	}
}
