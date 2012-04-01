package net.minecraft.src;

import java.util.ArrayList;
import java.util.Random;

public class BlockEndPortalFrame extends Block {
	public BlockEndPortalFrame(int par1) {
		super(par1, 159, Material.glass);
	}

	public int getBlockTextureFromSideAndMetadata(int par1, int par2) {
		if (par1 == 1) {
			return blockIndexInTexture - 1;
		}

		if (par1 == 0) {
			return blockIndexInTexture + 16;
		} else {
			return blockIndexInTexture;
		}
	}

	public boolean isOpaqueCube() {
		return false;
	}

	public int getRenderType() {
		return 26;
	}

	public void setBlockBoundsForItemRender() {
		setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.8125F, 1.0F);
	}

	public void getCollidingBoundingBoxes(World par1World, int par2, int par3, int par4, AxisAlignedBB par5AxisAlignedBB, ArrayList par6ArrayList) {
		setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.8125F, 1.0F);
		super.getCollidingBoundingBoxes(par1World, par2, par3, par4, par5AxisAlignedBB, par6ArrayList);
		int i = par1World.getBlockMetadata(par2, par3, par4);

		if (isEnderEyeInserted(i)) {
			setBlockBounds(0.3125F, 0.8125F, 0.3125F, 0.6875F, 1.0F, 0.6875F);
			super.getCollidingBoundingBoxes(par1World, par2, par3, par4, par5AxisAlignedBB, par6ArrayList);
		}

		setBlockBoundsForItemRender();
	}

	public static boolean isEnderEyeInserted(int par0) {
		return (par0 & 4) != 0;
	}

	public int idDropped(int par1, Random par2Random, int par3) {
		return 0;
	}

	public void onBlockPlacedBy(World par1World, int par2, int par3, int par4, EntityLiving par5EntityLiving) {
		int i = ((MathHelper.floor_double((double)((par5EntityLiving.rotationYaw * 4F) / 360F) + 0.5D) & 3) + 2) % 4;
		par1World.setBlockMetadataWithNotify(par2, par3, par4, i);
	}
}