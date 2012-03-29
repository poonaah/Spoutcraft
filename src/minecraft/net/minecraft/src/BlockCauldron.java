package net.minecraft.src;

import java.util.ArrayList;
import java.util.Random;

public class BlockCauldron extends Block {
	public BlockCauldron(int par1) {
		super(par1, Material.iron);
		blockIndexInTexture = 154;
	}

	public int getBlockTextureFromSideAndMetadata(int par1, int par2) {
		if (par1 == 1) {
			return 138;
		}

		return par1 != 0 ? 154 : 155;
	}

	public void getCollidingBoundingBoxes(World par1World, int par2, int par3, int par4, AxisAlignedBB par5AxisAlignedBB, ArrayList par6ArrayList) {
		setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.3125F, 1.0F);
		super.getCollidingBoundingBoxes(par1World, par2, par3, par4, par5AxisAlignedBB, par6ArrayList);
		float f = 0.125F;
		setBlockBounds(0.0F, 0.0F, 0.0F, f, 1.0F, 1.0F);
		super.getCollidingBoundingBoxes(par1World, par2, par3, par4, par5AxisAlignedBB, par6ArrayList);
		setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, f);
		super.getCollidingBoundingBoxes(par1World, par2, par3, par4, par5AxisAlignedBB, par6ArrayList);
		setBlockBounds(1.0F - f, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
		super.getCollidingBoundingBoxes(par1World, par2, par3, par4, par5AxisAlignedBB, par6ArrayList);
		setBlockBounds(0.0F, 0.0F, 1.0F - f, 1.0F, 1.0F, 1.0F);
		super.getCollidingBoundingBoxes(par1World, par2, par3, par4, par5AxisAlignedBB, par6ArrayList);
		setBlockBoundsForItemRender();
	}

	public void setBlockBoundsForItemRender() {
		setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
	}

	public boolean isOpaqueCube() {
		return false;
	}

	public int getRenderType() {
		return 24;
	}

	public boolean renderAsNormalBlock() {
		return false;
	}

	public boolean blockActivated(World par1World, int par2, int par3, int par4, EntityPlayer par5EntityPlayer) {
		if (par1World.isRemote) {
			return true;
		}

		ItemStack itemstack = par5EntityPlayer.inventory.getCurrentItem();

		if (itemstack == null) {
			return true;
		}

		int i = par1World.getBlockMetadata(par2, par3, par4);

		if (itemstack.itemID == Item.bucketWater.shiftedIndex) {
			if (i < 3) {
				if (!par5EntityPlayer.capabilities.isCreativeMode) {
					par5EntityPlayer.inventory.setInventorySlotContents(par5EntityPlayer.inventory.currentItem, new ItemStack(Item.bucketEmpty));
				}

				par1World.setBlockMetadataWithNotify(par2, par3, par4, 3);
			}

			return true;
		}

		if (itemstack.itemID == Item.glassBottle.shiftedIndex && i > 0) {
			ItemStack itemstack1 = new ItemStack(Item.potion, 1, 0);

			if (!par5EntityPlayer.inventory.addItemStackToInventory(itemstack1)) {
				par1World.spawnEntityInWorld(new EntityItem(par1World, (double)par2 + 0.5D, (double)par3 + 1.5D, (double)par4 + 0.5D, itemstack1));
			}

			itemstack.stackSize--;

			if (itemstack.stackSize <= 0) {
				par5EntityPlayer.inventory.setInventorySlotContents(par5EntityPlayer.inventory.currentItem, null);
			}

			par1World.setBlockMetadataWithNotify(par2, par3, par4, i - 1);
		}

		return true;
	}

	public int idDropped(int par1, Random par2Random, int par3) {
		return Item.cauldron.shiftedIndex;
	}
}
