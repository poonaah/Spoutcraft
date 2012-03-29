package net.minecraft.src;

import java.util.Random;

public class BlockIce extends BlockBreakable {
	public BlockIce(int par1, int par2) {
		super(par1, par2, Material.ice, false);
		slipperiness = 0.98F;
		setTickRandomly(true);
	}

	public int getRenderBlockPass() {
		return 1;
	}

	public boolean shouldSideBeRendered(IBlockAccess par1IBlockAccess, int par2, int par3, int par4, int par5) {
		return super.shouldSideBeRendered(par1IBlockAccess, par2, par3, par4, 1 - par5);
	}

	public void harvestBlock(World par1World, EntityPlayer par2EntityPlayer, int par3, int par4, int par5, int par6) {
		super.harvestBlock(par1World, par2EntityPlayer, par3, par4, par5, par6);
		Material material = par1World.getBlockMaterial(par3, par4 - 1, par5);

		if (material.blocksMovement() || material.isLiquid()) {
			par1World.setBlockWithNotify(par3, par4, par5, Block.waterMoving.blockID);
		}
	}

	public int quantityDropped(Random par1Random) {
		return 0;
	}

	public void updateTick(World par1World, int par2, int par3, int par4, Random par5Random) {
		if (par1World.getSavedLightValue(EnumSkyBlock.Block, par2, par3, par4) > 11 - Block.lightOpacity[blockID]) {
			dropBlockAsItem(par1World, par2, par3, par4, par1World.getBlockMetadata(par2, par3, par4), 0);
			par1World.setBlockWithNotify(par2, par3, par4, Block.waterStill.blockID);
		}
	}

	public int getMobilityFlag() {
		return 0;
	}

	protected ItemStack createStackedBlock(int par1) {
		return null;
	}
}
