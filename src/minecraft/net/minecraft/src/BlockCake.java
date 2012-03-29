package net.minecraft.src;

import java.util.Random;

public class BlockCake extends Block {
	protected BlockCake(int par1, int par2) {
		super(par1, par2, Material.cake);
		setTickRandomly(true);
	}

	public void setBlockBoundsBasedOnState(IBlockAccess par1IBlockAccess, int par2, int par3, int par4) {
		int i = par1IBlockAccess.getBlockMetadata(par2, par3, par4);
		float f = 0.0625F;
		float f1 = (float)(1 + i * 2) / 16F;
		float f2 = 0.5F;
		setBlockBounds(f1, 0.0F, f, 1.0F - f, f2, 1.0F - f);
	}

	public void setBlockBoundsForItemRender() {
		float f = 0.0625F;
		float f1 = 0.5F;
		setBlockBounds(f, 0.0F, f, 1.0F - f, f1, 1.0F - f);
	}

	public AxisAlignedBB getCollisionBoundingBoxFromPool(World par1World, int par2, int par3, int par4) {
		int i = par1World.getBlockMetadata(par2, par3, par4);
		float f = 0.0625F;
		float f1 = (float)(1 + i * 2) / 16F;
		float f2 = 0.5F;
		return AxisAlignedBB.getBoundingBoxFromPool((float)par2 + f1, par3, (float)par4 + f, (float)(par2 + 1) - f, ((float)par3 + f2) - f, (float)(par4 + 1) - f);
	}

	public AxisAlignedBB getSelectedBoundingBoxFromPool(World par1World, int par2, int par3, int par4) {
		int i = par1World.getBlockMetadata(par2, par3, par4);
		float f = 0.0625F;
		float f1 = (float)(1 + i * 2) / 16F;
		float f2 = 0.5F;
		return AxisAlignedBB.getBoundingBoxFromPool((float)par2 + f1, par3, (float)par4 + f, (float)(par2 + 1) - f, (float)par3 + f2, (float)(par4 + 1) - f);
	}

	public int getBlockTextureFromSideAndMetadata(int par1, int par2) {
		if (par1 == 1) {
			return blockIndexInTexture;
		}

		if (par1 == 0) {
			return blockIndexInTexture + 3;
		}

		if (par2 > 0 && par1 == 4) {
			return blockIndexInTexture + 2;
		} else {
			return blockIndexInTexture + 1;
		}
	}

	public int getBlockTextureFromSide(int par1) {
		if (par1 == 1) {
			return blockIndexInTexture;
		}

		if (par1 == 0) {
			return blockIndexInTexture + 3;
		} else {
			return blockIndexInTexture + 1;
		}
	}

	public boolean renderAsNormalBlock() {
		return false;
	}

	public boolean isOpaqueCube() {
		return false;
	}

	public boolean blockActivated(World par1World, int par2, int par3, int par4, EntityPlayer par5EntityPlayer) {
		eatCakeSlice(par1World, par2, par3, par4, par5EntityPlayer);
		return true;
	}

	public void onBlockClicked(World par1World, int par2, int par3, int par4, EntityPlayer par5EntityPlayer) {
		eatCakeSlice(par1World, par2, par3, par4, par5EntityPlayer);
	}

	private void eatCakeSlice(World par1World, int par2, int par3, int par4, EntityPlayer par5EntityPlayer) {
		if (par5EntityPlayer.canEat(false)) {
			par5EntityPlayer.getFoodStats().addStats(2, 0.1F);
			int i = par1World.getBlockMetadata(par2, par3, par4) + 1;

			if (i >= 6) {
				par1World.setBlockWithNotify(par2, par3, par4, 0);
			} else {
				par1World.setBlockMetadataWithNotify(par2, par3, par4, i);
				par1World.markBlockAsNeedsUpdate(par2, par3, par4);
			}
		}
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
		return par1World.getBlockMaterial(par2, par3 - 1, par4).isSolid();
	}

	public int quantityDropped(Random par1Random) {
		return 0;
	}

	public int idDropped(int par1, Random par2Random, int par3) {
		return 0;
	}
}
