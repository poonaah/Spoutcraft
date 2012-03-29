package net.minecraft.src;

import java.util.Random;

public class BlockDeadBush extends BlockFlower {
	protected BlockDeadBush(int par1, int par2) {
		super(par1, par2, Material.vine);
		float f = 0.4F;
		setBlockBounds(0.5F - f, 0.0F, 0.5F - f, 0.5F + f, 0.8F, 0.5F + f);
	}

	protected boolean canThisPlantGrowOnThisBlockID(int par1) {
		return par1 == Block.sand.blockID;
	}

	public int getBlockTextureFromSideAndMetadata(int par1, int par2) {
		return blockIndexInTexture;
	}

	public int idDropped(int par1, Random par2Random, int par3) {
		return -1;
	}

	public void harvestBlock(World par1World, EntityPlayer par2EntityPlayer, int par3, int par4, int par5, int par6) {
		if (!par1World.isRemote && par2EntityPlayer.getCurrentEquippedItem() != null && par2EntityPlayer.getCurrentEquippedItem().itemID == Item.shears.shiftedIndex) {
			par2EntityPlayer.addStat(StatList.mineBlockStatArray[blockID], 1);
			dropBlockAsItem_do(par1World, par3, par4, par5, new ItemStack(Block.deadBush, 1, par6));
		} else {
			super.harvestBlock(par1World, par2EntityPlayer, par3, par4, par5, par6);
		}
	}
}
