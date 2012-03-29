package net.minecraft.src;

import java.util.Random;

public class BlockNetherStalk extends BlockFlower {
	protected BlockNetherStalk(int par1) {
		super(par1, 226);
		setTickRandomly(true);
		float f = 0.5F;
		setBlockBounds(0.5F - f, 0.0F, 0.5F - f, 0.5F + f, 0.25F, 0.5F + f);
	}

	protected boolean canThisPlantGrowOnThisBlockID(int par1) {
		return par1 == Block.slowSand.blockID;
	}

	public boolean canBlockStay(World par1World, int par2, int par3, int par4) {
		return canThisPlantGrowOnThisBlockID(par1World.getBlockId(par2, par3 - 1, par4));
	}

	public void updateTick(World par1World, int par2, int par3, int par4, Random par5Random) {
		int i = par1World.getBlockMetadata(par2, par3, par4);

		if (i < 3) {
			BiomeGenBase biomegenbase = par1World.func_48454_a(par2, par4);

			if ((biomegenbase instanceof BiomeGenHell) && par5Random.nextInt(10) == 0) {
				i++;
				par1World.setBlockMetadataWithNotify(par2, par3, par4, i);
			}
		}

		super.updateTick(par1World, par2, par3, par4, par5Random);
	}

	public int getBlockTextureFromSideAndMetadata(int par1, int par2) {
		if (par2 >= 3) {
			return blockIndexInTexture + 2;
		}

		if (par2 > 0) {
			return blockIndexInTexture + 1;
		} else {
			return blockIndexInTexture;
		}
	}

	public int getRenderType() {
		return 6;
	}

	public void dropBlockAsItemWithChance(World par1World, int par2, int par3, int par4, int par5, float par6, int par7) {
		if (par1World.isRemote) {
			return;
		}

		int i = 1;

		if (par5 >= 3) {
			i = 2 + par1World.rand.nextInt(3);

			if (par7 > 0) {
				i += par1World.rand.nextInt(par7 + 1);
			}
		}

		for (int j = 0; j < i; j++) {
			dropBlockAsItem_do(par1World, par2, par3, par4, new ItemStack(Item.netherStalkSeeds));
		}
	}

	public int idDropped(int par1, Random par2Random, int par3) {
		return 0;
	}

	public int quantityDropped(Random par1Random) {
		return 0;
	}
}
