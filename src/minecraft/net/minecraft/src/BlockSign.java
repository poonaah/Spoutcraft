package net.minecraft.src;

import java.util.Random;

public class BlockSign extends BlockContainer {
	private Class signEntityClass;
	private boolean isFreestanding;

	protected BlockSign(int par1, Class par2Class, boolean par3) {
		super(par1, Material.wood);
		isFreestanding = par3;
		blockIndexInTexture = 4;
		signEntityClass = par2Class;
		float f = 0.25F;
		float f1 = 1.0F;
		setBlockBounds(0.5F - f, 0.0F, 0.5F - f, 0.5F + f, f1, 0.5F + f);
	}

	public AxisAlignedBB getCollisionBoundingBoxFromPool(World par1World, int par2, int par3, int i) {
		return null;
	}

	public AxisAlignedBB getSelectedBoundingBoxFromPool(World par1World, int par2, int par3, int par4) {
		setBlockBoundsBasedOnState(par1World, par2, par3, par4);
		return super.getSelectedBoundingBoxFromPool(par1World, par2, par3, par4);
	}

	public void setBlockBoundsBasedOnState(IBlockAccess par1IBlockAccess, int par2, int par3, int par4) {
		if (isFreestanding) {
			return;
		}

		int i = par1IBlockAccess.getBlockMetadata(par2, par3, par4);
		float f = 0.28125F;
		float f1 = 0.78125F;
		float f2 = 0.0F;
		float f3 = 1.0F;
		float f4 = 0.125F;
		setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);

		if (i == 2) {
			setBlockBounds(f2, f, 1.0F - f4, f3, f1, 1.0F);
		}

		if (i == 3) {
			setBlockBounds(f2, f, 0.0F, f3, f1, f4);
		}

		if (i == 4) {
			setBlockBounds(1.0F - f4, f, f2, 1.0F, f1, f3);
		}

		if (i == 5) {
			setBlockBounds(0.0F, f, f2, f4, f1, f3);
		}
	}

	public int getRenderType() {
		return -1;
	}

	public boolean renderAsNormalBlock() {
		return false;
	}

	public boolean getBlocksMovement(IBlockAccess par1IBlockAccess, int par2, int par3, int i) {
		return true;
	}

	public boolean isOpaqueCube() {
		return false;
	}

	public TileEntity getBlockEntity() {
		try {
			return (TileEntity)signEntityClass.newInstance();
		} catch (Exception exception) {
			throw new RuntimeException(exception);
		}
	}

	public int idDropped(int par1, Random par2Random, int par3) {
		return Item.sign.shiftedIndex;
	}

	public void onNeighborBlockChange(World par1World, int par2, int par3, int par4, int par5) {
		boolean flag = false;

		if (isFreestanding) {
			if (!par1World.getBlockMaterial(par2, par3 - 1, par4).isSolid()) {
				flag = true;
			}
		} else {
			int i = par1World.getBlockMetadata(par2, par3, par4);
			flag = true;

			if (i == 2 && par1World.getBlockMaterial(par2, par3, par4 + 1).isSolid()) {
				flag = false;
			}

			if (i == 3 && par1World.getBlockMaterial(par2, par3, par4 - 1).isSolid()) {
				flag = false;
			}

			if (i == 4 && par1World.getBlockMaterial(par2 + 1, par3, par4).isSolid()) {
				flag = false;
			}

			if (i == 5 && par1World.getBlockMaterial(par2 - 1, par3, par4).isSolid()) {
				flag = false;
			}
		}

		if (flag) {
			dropBlockAsItem(par1World, par2, par3, par4, par1World.getBlockMetadata(par2, par3, par4), 0);
			par1World.setBlockWithNotify(par2, par3, par4, 0);
		}

		super.onNeighborBlockChange(par1World, par2, par3, par4, par5);
	}
}
