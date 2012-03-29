package net.minecraft.src;

import java.util.Random;

public class BlockLadder extends Block {
	protected BlockLadder(int par1, int par2) {
		super(par1, par2, Material.circuits);
	}

	public AxisAlignedBB getCollisionBoundingBoxFromPool(World par1World, int par2, int par3, int par4) {
		int i = par1World.getBlockMetadata(par2, par3, par4);
		float f = 0.125F;

		if (i == 2) {
			setBlockBounds(0.0F, 0.0F, 1.0F - f, 1.0F, 1.0F, 1.0F);
		}

		if (i == 3) {
			setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, f);
		}

		if (i == 4) {
			setBlockBounds(1.0F - f, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
		}

		if (i == 5) {
			setBlockBounds(0.0F, 0.0F, 0.0F, f, 1.0F, 1.0F);
		}

		return super.getCollisionBoundingBoxFromPool(par1World, par2, par3, par4);
	}

	public AxisAlignedBB getSelectedBoundingBoxFromPool(World par1World, int par2, int par3, int par4) {
		int i = par1World.getBlockMetadata(par2, par3, par4);
		float f = 0.125F;

		if (i == 2) {
			setBlockBounds(0.0F, 0.0F, 1.0F - f, 1.0F, 1.0F, 1.0F);
		}

		if (i == 3) {
			setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, f);
		}

		if (i == 4) {
			setBlockBounds(1.0F - f, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
		}

		if (i == 5) {
			setBlockBounds(0.0F, 0.0F, 0.0F, f, 1.0F, 1.0F);
		}

		return super.getSelectedBoundingBoxFromPool(par1World, par2, par3, par4);
	}

	public boolean isOpaqueCube() {
		return false;
	}

	public boolean renderAsNormalBlock() {
		return false;
	}

	public int getRenderType() {
		return 8;
	}

	public boolean canPlaceBlockAt(World par1World, int par2, int par3, int par4) {
		if (par1World.isBlockNormalCube(par2 - 1, par3, par4)) {
			return true;
		}

		if (par1World.isBlockNormalCube(par2 + 1, par3, par4)) {
			return true;
		}

		if (par1World.isBlockNormalCube(par2, par3, par4 - 1)) {
			return true;
		}

		return par1World.isBlockNormalCube(par2, par3, par4 + 1);
	}

	public void onBlockPlaced(World par1World, int par2, int par3, int par4, int par5) {
		int i = par1World.getBlockMetadata(par2, par3, par4);

		if ((i == 0 || par5 == 2) && par1World.isBlockNormalCube(par2, par3, par4 + 1)) {
			i = 2;
		}

		if ((i == 0 || par5 == 3) && par1World.isBlockNormalCube(par2, par3, par4 - 1)) {
			i = 3;
		}

		if ((i == 0 || par5 == 4) && par1World.isBlockNormalCube(par2 + 1, par3, par4)) {
			i = 4;
		}

		if ((i == 0 || par5 == 5) && par1World.isBlockNormalCube(par2 - 1, par3, par4)) {
			i = 5;
		}

		par1World.setBlockMetadataWithNotify(par2, par3, par4, i);
	}

	public void onNeighborBlockChange(World par1World, int par2, int par3, int par4, int par5) {
		int i = par1World.getBlockMetadata(par2, par3, par4);
		boolean flag = false;

		if (i == 2 && par1World.isBlockNormalCube(par2, par3, par4 + 1)) {
			flag = true;
		}

		if (i == 3 && par1World.isBlockNormalCube(par2, par3, par4 - 1)) {
			flag = true;
		}

		if (i == 4 && par1World.isBlockNormalCube(par2 + 1, par3, par4)) {
			flag = true;
		}

		if (i == 5 && par1World.isBlockNormalCube(par2 - 1, par3, par4)) {
			flag = true;
		}

		if (!flag) {
			dropBlockAsItem(par1World, par2, par3, par4, i, 0);
			par1World.setBlockWithNotify(par2, par3, par4, 0);
		}

		super.onNeighborBlockChange(par1World, par2, par3, par4, par5);
	}

	public int quantityDropped(Random par1Random) {
		return 1;
	}
}
