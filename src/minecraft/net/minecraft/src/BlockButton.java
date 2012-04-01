package net.minecraft.src;

import java.util.Random;

public class BlockButton extends Block {
	protected BlockButton(int par1, int par2) {
		super(par1, par2, Material.circuits);
		setTickRandomly(true);
	}

	public AxisAlignedBB getCollisionBoundingBoxFromPool(World par1World, int par2, int par3, int i) {
		return null;
	}

	public int tickRate() {
		return 20;
	}

	public boolean isOpaqueCube() {
		return false;
	}

	public boolean renderAsNormalBlock() {
		return false;
	}

	public boolean canPlaceBlockOnSide(World par1World, int par2, int par3, int par4, int par5) {
		if (par5 == 2 && par1World.isBlockNormalCube(par2, par3, par4 + 1)) {
			return true;
		}

		if (par5 == 3 && par1World.isBlockNormalCube(par2, par3, par4 - 1)) {
			return true;
		}

		if (par5 == 4 && par1World.isBlockNormalCube(par2 + 1, par3, par4)) {
			return true;
		}

		return par5 == 5 && par1World.isBlockNormalCube(par2 - 1, par3, par4);
	}

	public boolean canPlaceBlockAt(World par1World, int par2, int par3, int par4) {
		if (par1World.isBlockNormalCube(par2 - 1, par3, par4)) {
			return true;
		}

		if (par1World.isBlockNormalCube(par2 + 1, par3, par4)) {
			return true;
		}

		if (par1World.isBlockNormalCube(par2, par3, par4 - 1)) {
			return true;
		}

		return par1World.isBlockNormalCube(par2, par3, par4 + 1);
	}

	public void onBlockPlaced(World par1World, int par2, int par3, int par4, int par5) {
		int i = par1World.getBlockMetadata(par2, par3, par4);
		int j = i & 8;
		i &= 7;

		if (par5 == 2 && par1World.isBlockNormalCube(par2, par3, par4 + 1)) {
			i = 4;
		} else if (par5 == 3 && par1World.isBlockNormalCube(par2, par3, par4 - 1)) {
			i = 3;
		} else if (par5 == 4 && par1World.isBlockNormalCube(par2 + 1, par3, par4)) {
			i = 2;
		} else if (par5 == 5 && par1World.isBlockNormalCube(par2 - 1, par3, par4)) {
			i = 1;
		} else {
			i = getOrientation(par1World, par2, par3, par4);
		}

		par1World.setBlockMetadataWithNotify(par2, par3, par4, i + j);
	}

	private int getOrientation(World par1World, int par2, int par3, int par4) {
		if (par1World.isBlockNormalCube(par2 - 1, par3, par4)) {
			return 1;
		}

		if (par1World.isBlockNormalCube(par2 + 1, par3, par4)) {
			return 2;
		}

		if (par1World.isBlockNormalCube(par2, par3, par4 - 1)) {
			return 3;
		}

		return !par1World.isBlockNormalCube(par2, par3, par4 + 1) ? 1 : 4;
	}

	public void onNeighborBlockChange(World par1World, int par2, int par3, int par4, int par5) {
		if (redundantCanPlaceBlockAt(par1World, par2, par3, par4)) {
			int i = par1World.getBlockMetadata(par2, par3, par4) & 7;
			boolean flag = false;

			if (!par1World.isBlockNormalCube(par2 - 1, par3, par4) && i == 1) {
				flag = true;
			}

			if (!par1World.isBlockNormalCube(par2 + 1, par3, par4) && i == 2) {
				flag = true;
			}

			if (!par1World.isBlockNormalCube(par2, par3, par4 - 1) && i == 3) {
				flag = true;
			}

			if (!par1World.isBlockNormalCube(par2, par3, par4 + 1) && i == 4) {
				flag = true;
			}

			if (flag) {
				dropBlockAsItem(par1World, par2, par3, par4, par1World.getBlockMetadata(par2, par3, par4), 0);
				par1World.setBlockWithNotify(par2, par3, par4, 0);
			}
		}
	}

	private boolean redundantCanPlaceBlockAt(World par1World, int par2, int par3, int par4) {
		if (!canPlaceBlockAt(par1World, par2, par3, par4)) {
			dropBlockAsItem(par1World, par2, par3, par4, par1World.getBlockMetadata(par2, par3, par4), 0);
			par1World.setBlockWithNotify(par2, par3, par4, 0);
			return false;
		} else {
			return true;
		}
	}

	public void setBlockBoundsBasedOnState(IBlockAccess par1IBlockAccess, int par2, int par3, int par4) {
		int i = par1IBlockAccess.getBlockMetadata(par2, par3, par4);
		int j = i & 7;
		boolean flag = (i & 8) > 0;
		float f = 0.375F;
		float f1 = 0.625F;
		float f2 = 0.1875F;
		float f3 = 0.125F;

		if (flag) {
			f3 = 0.0625F;
		}

		if (j == 1) {
			setBlockBounds(0.0F, f, 0.5F - f2, f3, f1, 0.5F + f2);
		} else if (j == 2) {
			setBlockBounds(1.0F - f3, f, 0.5F - f2, 1.0F, f1, 0.5F + f2);
		} else if (j == 3) {
			setBlockBounds(0.5F - f2, f, 0.0F, 0.5F + f2, f1, f3);
		} else if (j == 4) {
			setBlockBounds(0.5F - f2, f, 1.0F - f3, 0.5F + f2, f1, 1.0F);
		}
	}

	public void onBlockClicked(World par1World, int par2, int par3, int par4, EntityPlayer par5EntityPlayer) {
		blockActivated(par1World, par2, par3, par4, par5EntityPlayer);
	}

	public boolean blockActivated(World par1World, int par2, int par3, int par4, EntityPlayer par5EntityPlayer) {
		int i = par1World.getBlockMetadata(par2, par3, par4);
		int j = i & 7;
		int k = 8 - (i & 8);

		if (k == 0) {
			return true;
		}

		par1World.setBlockMetadataWithNotify(par2, par3, par4, j + k);
		par1World.markBlocksDirty(par2, par3, par4, par2, par3, par4);
		par1World.playSoundEffect((double)par2 + 0.5D, (double)par3 + 0.5D, (double)par4 + 0.5D, "random.click", 0.3F, 0.6F);
		par1World.notifyBlocksOfNeighborChange(par2, par3, par4, blockID);

		if (j == 1) {
			par1World.notifyBlocksOfNeighborChange(par2 - 1, par3, par4, blockID);
		} else if (j == 2) {
			par1World.notifyBlocksOfNeighborChange(par2 + 1, par3, par4, blockID);
		} else if (j == 3) {
			par1World.notifyBlocksOfNeighborChange(par2, par3, par4 - 1, blockID);
		} else if (j == 4) {
			par1World.notifyBlocksOfNeighborChange(par2, par3, par4 + 1, blockID);
		} else {
			par1World.notifyBlocksOfNeighborChange(par2, par3 - 1, par4, blockID);
		}

		par1World.scheduleBlockUpdate(par2, par3, par4, blockID, tickRate());
		return true;
	}

	public void onBlockRemoval(World par1World, int par2, int par3, int par4) {
		int i = par1World.getBlockMetadata(par2, par3, par4);

		if ((i & 8) > 0) {
			par1World.notifyBlocksOfNeighborChange(par2, par3, par4, blockID);
			int j = i & 7;

			if (j == 1) {
				par1World.notifyBlocksOfNeighborChange(par2 - 1, par3, par4, blockID);
			} else if (j == 2) {
				par1World.notifyBlocksOfNeighborChange(par2 + 1, par3, par4, blockID);
			} else if (j == 3) {
				par1World.notifyBlocksOfNeighborChange(par2, par3, par4 - 1, blockID);
			} else if (j == 4) {
				par1World.notifyBlocksOfNeighborChange(par2, par3, par4 + 1, blockID);
			} else {
				par1World.notifyBlocksOfNeighborChange(par2, par3 - 1, par4, blockID);
			}
		}

		super.onBlockRemoval(par1World, par2, par3, par4);
	}

	public boolean isPoweringTo(IBlockAccess par1IBlockAccess, int par2, int par3, int par4, int par5) {
		return (par1IBlockAccess.getBlockMetadata(par2, par3, par4) & 8) > 0;
	}

	public boolean isIndirectlyPoweringTo(World par1World, int par2, int par3, int par4, int par5) {
		int i = par1World.getBlockMetadata(par2, par3, par4);

		if ((i & 8) == 0) {
			return false;
		}

		int j = i & 7;

		if (j == 5 && par5 == 1) {
			return true;
		}

		if (j == 4 && par5 == 2) {
			return true;
		}

		if (j == 3 && par5 == 3) {
			return true;
		}

		if (j == 2 && par5 == 4) {
			return true;
		}

		return j == 1 && par5 == 5;
	}

	public boolean canProvidePower() {
		return true;
	}

	public void updateTick(World par1World, int par2, int par3, int par4, Random par5Random) {
		if (par1World.isRemote) {
			return;
		}

		int i = par1World.getBlockMetadata(par2, par3, par4);

		if ((i & 8) == 0) {
			return;
		}

		par1World.setBlockMetadataWithNotify(par2, par3, par4, i & 7);
		par1World.notifyBlocksOfNeighborChange(par2, par3, par4, blockID);
		int j = i & 7;

		if (j == 1) {
			par1World.notifyBlocksOfNeighborChange(par2 - 1, par3, par4, blockID);
		} else if (j == 2) {
			par1World.notifyBlocksOfNeighborChange(par2 + 1, par3, par4, blockID);
		} else if (j == 3) {
			par1World.notifyBlocksOfNeighborChange(par2, par3, par4 - 1, blockID);
		} else if (j == 4) {
			par1World.notifyBlocksOfNeighborChange(par2, par3, par4 + 1, blockID);
		} else {
			par1World.notifyBlocksOfNeighborChange(par2, par3 - 1, par4, blockID);
		}

		par1World.playSoundEffect((double)par2 + 0.5D, (double)par3 + 0.5D, (double)par4 + 0.5D, "random.click", 0.3F, 0.5F);
		par1World.markBlocksDirty(par2, par3, par4, par2, par3, par4);
	}

	public void setBlockBoundsForItemRender() {
		float f = 0.1875F;
		float f1 = 0.125F;
		float f2 = 0.125F;
		setBlockBounds(0.5F - f, 0.5F - f1, 0.5F - f2, 0.5F + f, 0.5F + f1, 0.5F + f2);
	}
}