package net.minecraft.src;

import java.util.Random;

public class BlockFarmland extends Block {
	protected BlockFarmland(int par1) {
		super(par1, Material.ground);
		blockIndexInTexture = 87;
		setTickRandomly(true);
		setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.9375F, 1.0F);
		setLightOpacity(255);
	}

	public AxisAlignedBB getCollisionBoundingBoxFromPool(World par1World, int par2, int par3, int par4) {
		return AxisAlignedBB.getBoundingBoxFromPool(par2 + 0, par3 + 0, par4 + 0, par2 + 1, par3 + 1, par4 + 1);
	}

	public boolean isOpaqueCube() {
		return false;
	}

	public boolean renderAsNormalBlock() {
		return false;
	}

	public int getBlockTextureFromSideAndMetadata(int par1, int par2) {
		if (par1 == 1 && par2 > 0) {
			return blockIndexInTexture - 1;
		}

		if (par1 == 1) {
			return blockIndexInTexture;
		} else {
			return 2;
		}
	}

	public void updateTick(World par1World, int par2, int par3, int par4, Random par5Random) {
		if (isWaterNearby(par1World, par2, par3, par4) || par1World.canLightningStrikeAt(par2, par3 + 1, par4)) {
			par1World.setBlockMetadataWithNotify(par2, par3, par4, 7);
		} else {
			int i = par1World.getBlockMetadata(par2, par3, par4);

			if (i > 0) {
				par1World.setBlockMetadataWithNotify(par2, par3, par4, i - 1);
			} else if (!isCropsNearby(par1World, par2, par3, par4)) {
				par1World.setBlockWithNotify(par2, par3, par4, Block.dirt.blockID);
			}
		}
	}

	public void onFallenUpon(World par1World, int par2, int par3, int par4, Entity par5Entity, float par6) {
		if (par1World.rand.nextFloat() < par6 - 0.5F) {
			par1World.setBlockWithNotify(par2, par3, par4, Block.dirt.blockID);
		}
	}

	private boolean isCropsNearby(World par1World, int par2, int par3, int par4) {
		int i = 0;

		for (int j = par2 - i; j <= par2 + i; j++) {
			for (int k = par4 - i; k <= par4 + i; k++) {
				int l = par1World.getBlockId(j, par3 + 1, k);

				if (l == Block.crops.blockID || l == Block.melonStem.blockID || l == Block.pumpkinStem.blockID) {
					return true;
				}
			}
		}

		return false;
	}

	private boolean isWaterNearby(World par1World, int par2, int par3, int par4) {
		for (int i = par2 - 4; i <= par2 + 4; i++) {
			for (int j = par3; j <= par3 + 1; j++) {
				for (int k = par4 - 4; k <= par4 + 4; k++) {
					if (par1World.getBlockMaterial(i, j, k) == Material.water) {
						return true;
					}
				}
			}
		}

		return false;
	}

	public void onNeighborBlockChange(World par1World, int par2, int par3, int par4, int par5) {
		super.onNeighborBlockChange(par1World, par2, par3, par4, par5);
		Material material = par1World.getBlockMaterial(par2, par3 + 1, par4);

		if (material.isSolid()) {
			par1World.setBlockWithNotify(par2, par3, par4, Block.dirt.blockID);
		}
	}

	public int idDropped(int par1, Random par2Random, int par3) {
		return Block.dirt.idDropped(0, par2Random, par3);
	}
}
