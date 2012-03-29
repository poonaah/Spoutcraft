package net.minecraft.src;

import java.util.Random;

public class BlockTallGrass extends BlockFlower {
	protected BlockTallGrass(int par1, int par2) {
		super(par1, par2, Material.vine);
		float f = 0.4F;
		setBlockBounds(0.5F - f, 0.0F, 0.5F - f, 0.5F + f, 0.8F, 0.5F + f);
	}

	public int getBlockTextureFromSideAndMetadata(int par1, int par2) {
		if (par2 == 1) {
			return blockIndexInTexture;
		}

		if (par2 == 2) {
			return blockIndexInTexture + 16 + 1;
		}

		if (par2 == 0) {
			return blockIndexInTexture + 16;
		} else {
			return blockIndexInTexture;
		}
	}

	public int getBlockColor() {
		double d = 0.5D;
		double d1 = 1.0D;
		return ColorizerGrass.getGrassColor(d, d1);
	}

	public int getRenderColor(int par1) {
		if (par1 == 0) {
			return 0xffffff;
		} else {
			return ColorizerFoliage.getFoliageColorBasic();
		}
	}

	public int colorMultiplier(IBlockAccess par1IBlockAccess, int par2, int par3, int par4) {
		int i = par1IBlockAccess.getBlockMetadata(par2, par3, par4);

		if (i == 0) {
			return 0xffffff;
		} else {
			return par1IBlockAccess.func_48454_a(par2, par4).func_48415_j();
		}
	}

	public int idDropped(int par1, Random par2Random, int par3) {
		if (par2Random.nextInt(8) == 0) {
			return Item.seeds.shiftedIndex;
		} else {
			return -1;
		}
	}

	public int quantityDroppedWithBonus(int par1, Random par2Random) {
		return 1 + par2Random.nextInt(par1 * 2 + 1);
	}

	public void harvestBlock(World par1World, EntityPlayer par2EntityPlayer, int par3, int par4, int par5, int par6) {
		if (!par1World.isRemote && par2EntityPlayer.getCurrentEquippedItem() != null && par2EntityPlayer.getCurrentEquippedItem().itemID == Item.shears.shiftedIndex) {
			par2EntityPlayer.addStat(StatList.mineBlockStatArray[blockID], 1);
			dropBlockAsItem_do(par1World, par3, par4, par5, new ItemStack(Block.tallGrass, 1, par6));
		} else {
			super.harvestBlock(par1World, par2EntityPlayer, par3, par4, par5, par6);
		}
	}
}
