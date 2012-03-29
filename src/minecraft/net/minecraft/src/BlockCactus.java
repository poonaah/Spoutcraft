package net.minecraft.src;

import java.util.Random;

public class BlockCactus extends Block {
	protected BlockCactus(int par1, int par2) {
		super(par1, par2, Material.cactus);
		setTickRandomly(true);
	}

	public void updateTick(World par1World, int par2, int par3, int par4, Random par5Random) {
		if (par1World.isAirBlock(par2, par3 + 1, par4)) {
			int i;

			for (i = 1; par1World.getBlockId(par2, par3 - i, par4) == blockID; i++) { }

			if (i < 3) {
				int j = par1World.getBlockMetadata(par2, par3, par4);

				if (j == 15) {
					par1World.setBlockWithNotify(par2, par3 + 1, par4, blockID);
					par1World.setBlockMetadataWithNotify(par2, par3, par4, 0);
				} else {
					par1World.setBlockMetadataWithNotify(par2, par3, par4, j + 1);
				}
			}
		}
	}

	public AxisAlignedBB getCollisionBoundingBoxFromPool(World par1World, int par2, int par3, int par4) {
		float f = 0.0625F;
		return AxisAlignedBB.getBoundingBoxFromPool((float)par2 + f, par3, (float)par4 + f, (float)(par2 + 1) - f, (float)(par3 + 1) - f, (float)(par4 + 1) - f);
	}

	public AxisAlignedBB getSelectedBoundingBoxFromPool(World par1World, int par2, int par3, int par4) {
		float f = 0.0625F;
		return AxisAlignedBB.getBoundingBoxFromPool((float)par2 + f, par3, (float)par4 + f, (float)(par2 + 1) - f, par3 + 1, (float)(par4 + 1) - f);
	}

	public int getBlockTextureFromSide(int par1) {
		if (par1 == 1) {
			return blockIndexInTexture - 1;
		}

		if (par1 == 0) {
			return blockIndexInTexture + 1;
		} else {
			return blockIndexInTexture;
		}
	}

	public boolean renderAsNormalBlock() {
		return false;
	}

	public boolean isOpaqueCube() {
		return false;
	}

	public int getRenderType() {
		return 13;
	}

	public boolean canPlaceBlockAt(World par1World, int par2, int par3, int par4) {
		if (!super.canPlaceBlockAt(par1World, par2, par3, par4)) {
			return false;
		} else {
			return canBlockStay(par1World, par2, par3, par4);
		}
	}

	public void onNeighborBlockChange(World par1World, int par2, int par3, int par4, int par5) {
		if (!canBlockStay(par1World, par2, par3, par4)) {
			dropBlockAsItem(par1World, par2, par3, par4, par1World.getBlockMetadata(par2, par3, par4), 0);
			par1World.setBlockWithNotify(par2, par3, par4, 0);
		}
	}

	public boolean canBlockStay(World par1World, int par2, int par3, int par4) {
		if (par1World.getBlockMaterial(par2 - 1, par3, par4).isSolid()) {
			return false;
		}

		if (par1World.getBlockMaterial(par2 + 1, par3, par4).isSolid()) {
			return false;
		}

		if (par1World.getBlockMaterial(par2, par3, par4 - 1).isSolid()) {
			return false;
		}

		if (par1World.getBlockMaterial(par2, par3, par4 + 1).isSolid()) {
			return false;
		} else {
			int i = par1World.getBlockId(par2, par3 - 1, par4);
			return i == Block.cactus.blockID || i == Block.sand.blockID;
		}
	}

	public void onEntityCollidedWithBlock(World par1World, int par2, int par3, int par4, Entity par5Entity) {
		par5Entity.attackEntityFrom(DamageSource.cactus, 1);
	}
}
