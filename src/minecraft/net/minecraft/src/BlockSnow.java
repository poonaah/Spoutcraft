package net.minecraft.src;

import java.util.Random;

public class BlockSnow extends Block {
	protected BlockSnow(int par1, int par2) {
		super(par1, par2, Material.snow);
		setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.125F, 1.0F);
		setTickRandomly(true);
	}

	public AxisAlignedBB getCollisionBoundingBoxFromPool(World par1World, int par2, int par3, int par4) {
		int i = par1World.getBlockMetadata(par2, par3, par4) & 7;

		if (i >= 3) {
			return AxisAlignedBB.getBoundingBoxFromPool((double)par2 + minX, (double)par3 + minY, (double)par4 + minZ, (double)par2 + maxX, (float)par3 + 0.5F, (double)par4 + maxZ);
		} else {
			return null;
		}
	}

	public boolean isOpaqueCube() {
		return false;
	}

	public boolean renderAsNormalBlock() {
		return false;
	}

	public void setBlockBoundsBasedOnState(IBlockAccess par1IBlockAccess, int par2, int par3, int par4) {
		int i = par1IBlockAccess.getBlockMetadata(par2, par3, par4) & 7;
		float f = (float)(2 * (1 + i)) / 16F;
		setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, f, 1.0F);
	}

	public boolean canPlaceBlockAt(World par1World, int par2, int par3, int par4) {
		int i = par1World.getBlockId(par2, par3 - 1, par4);

		if (i == 0 || i != Block.leaves.blockID && !Block.blocksList[i].isOpaqueCube()) {
			return false;
		} else {
			return par1World.getBlockMaterial(par2, par3 - 1, par4).blocksMovement();
		}
	}

	public void onNeighborBlockChange(World par1World, int par2, int par3, int par4, int par5) {
		canSnowStay(par1World, par2, par3, par4);
	}

	private boolean canSnowStay(World par1World, int par2, int par3, int par4) {
		if (!canPlaceBlockAt(par1World, par2, par3, par4)) {
			dropBlockAsItem(par1World, par2, par3, par4, par1World.getBlockMetadata(par2, par3, par4), 0);
			par1World.setBlockWithNotify(par2, par3, par4, 0);
			return false;
		} else {
			return true;
		}
	}

	public void harvestBlock(World par1World, EntityPlayer par2EntityPlayer, int par3, int par4, int par5, int par6) {
		int i = Item.snowball.shiftedIndex;
		float f = 0.7F;
		double d = (double)(par1World.rand.nextFloat() * f) + (double)(1.0F - f) * 0.5D;
		double d1 = (double)(par1World.rand.nextFloat() * f) + (double)(1.0F - f) * 0.5D;
		double d2 = (double)(par1World.rand.nextFloat() * f) + (double)(1.0F - f) * 0.5D;
		EntityItem entityitem = new EntityItem(par1World, (double)par3 + d, (double)par4 + d1, (double)par5 + d2, new ItemStack(i, 1, 0));
		entityitem.delayBeforeCanPickup = 10;
		par1World.spawnEntityInWorld(entityitem);
		par1World.setBlockWithNotify(par3, par4, par5, 0);
		par2EntityPlayer.addStat(StatList.mineBlockStatArray[blockID], 1);
	}

	public int idDropped(int par1, Random par2Random, int par3) {
		return Item.snowball.shiftedIndex;
	}

	public int quantityDropped(Random par1Random) {
		return 0;
	}

	public void updateTick(World par1World, int par2, int par3, int par4, Random par5Random) {
		if (par1World.getSavedLightValue(EnumSkyBlock.Block, par2, par3, par4) > 11) {
			dropBlockAsItem(par1World, par2, par3, par4, par1World.getBlockMetadata(par2, par3, par4), 0);
			par1World.setBlockWithNotify(par2, par3, par4, 0);
		}
	}

	public boolean shouldSideBeRendered(IBlockAccess par1IBlockAccess, int par2, int par3, int par4, int par5) {
		if (par5 == 1) {
			return true;
		} else {
			return super.shouldSideBeRendered(par1IBlockAccess, par2, par3, par4, par5);
		}
	}
}
