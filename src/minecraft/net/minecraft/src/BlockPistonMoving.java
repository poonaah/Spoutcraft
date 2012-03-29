package net.minecraft.src;

import java.util.Random;

public class BlockPistonMoving extends BlockContainer {
	public BlockPistonMoving(int par1) {
		super(par1, Material.piston);
		setHardness(-1F);
	}

	public TileEntity getBlockEntity() {
		return null;
	}

	public void onBlockAdded(World world, int i, int j, int k) {
	}

	public void onBlockRemoval(World par1World, int par2, int par3, int par4) {
		TileEntity tileentity = par1World.getBlockTileEntity(par2, par3, par4);

		if (tileentity != null && (tileentity instanceof TileEntityPiston)) {
			((TileEntityPiston)tileentity).clearPistonTileEntity();
		} else {
			super.onBlockRemoval(par1World, par2, par3, par4);
		}
	}

	public boolean canPlaceBlockAt(World par1World, int par2, int par3, int i) {
		return false;
	}

	public boolean canPlaceBlockOnSide(World par1World, int par2, int par3, int i, int j) {
		return false;
	}

	public int getRenderType() {
		return -1;
	}

	public boolean isOpaqueCube() {
		return false;
	}

	public boolean renderAsNormalBlock() {
		return false;
	}

	public boolean blockActivated(World par1World, int par2, int par3, int par4, EntityPlayer par5EntityPlayer) {
		if (!par1World.isRemote && par1World.getBlockTileEntity(par2, par3, par4) == null) {
			par1World.setBlockWithNotify(par2, par3, par4, 0);
			return true;
		} else {
			return false;
		}
	}

	public int idDropped(int par1, Random par2Random, int par3) {
		return 0;
	}

	public void dropBlockAsItemWithChance(World par1World, int par2, int par3, int par4, int par5, float par6, int par7) {
		if (par1World.isRemote) {
			return;
		}

		TileEntityPiston tileentitypiston = getTileEntityAtLocation(par1World, par2, par3, par4);

		if (tileentitypiston == null) {
			return;
		} else {
			Block.blocksList[tileentitypiston.getStoredBlockID()].dropBlockAsItem(par1World, par2, par3, par4, tileentitypiston.getBlockMetadata(), 0);
			return;
		}
	}

	public void onNeighborBlockChange(World par1World, int par2, int par3, int par4, int par5) {
		if (!par1World.isRemote) {
			if (par1World.getBlockTileEntity(par2, par3, par4) != null);
		}
	}

	public static TileEntity getTileEntity(int par0, int par1, int par2, boolean par3, boolean par4) {
		return new TileEntityPiston(par0, par1, par2, par3, par4);
	}

	public AxisAlignedBB getCollisionBoundingBoxFromPool(World par1World, int par2, int par3, int par4) {
		TileEntityPiston tileentitypiston = getTileEntityAtLocation(par1World, par2, par3, par4);

		if (tileentitypiston == null) {
			return null;
		}

		float f = tileentitypiston.getProgress(0.0F);

		if (tileentitypiston.isExtending()) {
			f = 1.0F - f;
		}

		return getAxisAlignedBB(par1World, par2, par3, par4, tileentitypiston.getStoredBlockID(), f, tileentitypiston.getPistonOrientation());
	}

	public void setBlockBoundsBasedOnState(IBlockAccess par1IBlockAccess, int par2, int par3, int par4) {
		TileEntityPiston tileentitypiston = getTileEntityAtLocation(par1IBlockAccess, par2, par3, par4);

		if (tileentitypiston != null) {
			Block block = Block.blocksList[tileentitypiston.getStoredBlockID()];

			if (block == null || block == this) {
				return;
			}

			block.setBlockBoundsBasedOnState(par1IBlockAccess, par2, par3, par4);
			float f = tileentitypiston.getProgress(0.0F);

			if (tileentitypiston.isExtending()) {
				f = 1.0F - f;
			}

			int i = tileentitypiston.getPistonOrientation();
			minX = block.minX - (double)((float)Facing.offsetsXForSide[i] * f);
			minY = block.minY - (double)((float)Facing.offsetsYForSide[i] * f);
			minZ = block.minZ - (double)((float)Facing.offsetsZForSide[i] * f);
			maxX = block.maxX - (double)((float)Facing.offsetsXForSide[i] * f);
			maxY = block.maxY - (double)((float)Facing.offsetsYForSide[i] * f);
			maxZ = block.maxZ - (double)((float)Facing.offsetsZForSide[i] * f);
		}
	}

	public AxisAlignedBB getAxisAlignedBB(World par1World, int par2, int par3, int par4, int par5, float par6, int par7) {
		if (par5 == 0 || par5 == blockID) {
			return null;
		}

		AxisAlignedBB axisalignedbb = Block.blocksList[par5].getCollisionBoundingBoxFromPool(par1World, par2, par3, par4);

		if (axisalignedbb == null) {
			return null;
		}

		if (Facing.offsetsXForSide[par7] < 0) {
			axisalignedbb.minX -= (float)Facing.offsetsXForSide[par7] * par6;
		} else {
			axisalignedbb.maxX -= (float)Facing.offsetsXForSide[par7] * par6;
		}

		if (Facing.offsetsYForSide[par7] < 0) {
			axisalignedbb.minY -= (float)Facing.offsetsYForSide[par7] * par6;
		} else {
			axisalignedbb.maxY -= (float)Facing.offsetsYForSide[par7] * par6;
		}

		if (Facing.offsetsZForSide[par7] < 0) {
			axisalignedbb.minZ -= (float)Facing.offsetsZForSide[par7] * par6;
		} else {
			axisalignedbb.maxZ -= (float)Facing.offsetsZForSide[par7] * par6;
		}

		return axisalignedbb;
	}

	private TileEntityPiston getTileEntityAtLocation(IBlockAccess par1IBlockAccess, int par2, int par3, int par4) {
		TileEntity tileentity = par1IBlockAccess.getBlockTileEntity(par2, par3, par4);

		if (tileentity != null && (tileentity instanceof TileEntityPiston)) {
			return (TileEntityPiston)tileentity;
		} else {
			return null;
		}
	}
}
