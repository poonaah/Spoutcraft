package net.minecraft.src;

import java.util.Random;

public class BlockRedstoneRepeater extends BlockDirectional {
	public static final double repeaterTorchOffset[] = {
		-0.0625D, 0.0625D, 0.1875D, 0.3125D
	};
	private static final int repeaterState[] = {
		1, 2, 3, 4
	};
	private final boolean isRepeaterPowered;

	protected BlockRedstoneRepeater(int par1, boolean par2) {
		super(par1, 6, Material.circuits);
		isRepeaterPowered = par2;
		setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.125F, 1.0F);
	}

	public boolean renderAsNormalBlock() {
		return false;
	}

	public boolean canPlaceBlockAt(World par1World, int par2, int par3, int par4) {
		if (!par1World.isBlockNormalCube(par2, par3 - 1, par4)) {
			return false;
		} else {
			return super.canPlaceBlockAt(par1World, par2, par3, par4);
		}
	}

	public boolean canBlockStay(World par1World, int par2, int par3, int par4) {
		if (!par1World.isBlockNormalCube(par2, par3 - 1, par4)) {
			return false;
		} else {
			return super.canBlockStay(par1World, par2, par3, par4);
		}
	}

	public void updateTick(World par1World, int par2, int par3, int par4, Random par5Random) {
		int i = par1World.getBlockMetadata(par2, par3, par4);
		boolean flag = ignoreTick(par1World, par2, par3, par4, i);

		if (isRepeaterPowered && !flag) {
			par1World.setBlockAndMetadataWithNotify(par2, par3, par4, Block.redstoneRepeaterIdle.blockID, i);
		} else if (!isRepeaterPowered) {
			par1World.setBlockAndMetadataWithNotify(par2, par3, par4, Block.redstoneRepeaterActive.blockID, i);

			if (!flag) {
				int j = (i & 0xc) >> 2;
				par1World.scheduleBlockUpdate(par2, par3, par4, Block.redstoneRepeaterActive.blockID, repeaterState[j] * 2);
			}
		}
	}

	public int getBlockTextureFromSideAndMetadata(int par1, int par2) {
		if (par1 == 0) {
			return !isRepeaterPowered ? 115 : 99;
		}

		if (par1 == 1) {
			return !isRepeaterPowered ? 131 : 147;
		} else {
			return 5;
		}
	}

	public boolean shouldSideBeRendered(IBlockAccess par1IBlockAccess, int par2, int par3, int par4, int par5) {
		return par5 != 0 && par5 != 1;
	}

	public int getRenderType() {
		return 15;
	}

	public int getBlockTextureFromSide(int par1) {
		return getBlockTextureFromSideAndMetadata(par1, 0);
	}

	public boolean isIndirectlyPoweringTo(World par1World, int par2, int par3, int par4, int par5) {
		return isPoweringTo(par1World, par2, par3, par4, par5);
	}

	public boolean isPoweringTo(IBlockAccess par1IBlockAccess, int par2, int par3, int par4, int par5) {
		if (!isRepeaterPowered) {
			return false;
		}

		int i = getDirection(par1IBlockAccess.getBlockMetadata(par2, par3, par4));

		if (i == 0 && par5 == 3) {
			return true;
		}

		if (i == 1 && par5 == 4) {
			return true;
		}

		if (i == 2 && par5 == 2) {
			return true;
		}

		return i == 3 && par5 == 5;
	}

	public void onNeighborBlockChange(World par1World, int par2, int par3, int par4, int par5) {
		if (!canBlockStay(par1World, par2, par3, par4)) {
			dropBlockAsItem(par1World, par2, par3, par4, par1World.getBlockMetadata(par2, par3, par4), 0);
			par1World.setBlockWithNotify(par2, par3, par4, 0);
			par1World.notifyBlocksOfNeighborChange(par2 + 1, par3, par4, blockID);
			par1World.notifyBlocksOfNeighborChange(par2 - 1, par3, par4, blockID);
			par1World.notifyBlocksOfNeighborChange(par2, par3, par4 + 1, blockID);
			par1World.notifyBlocksOfNeighborChange(par2, par3, par4 - 1, blockID);
			par1World.notifyBlocksOfNeighborChange(par2, par3 - 1, par4, blockID);
			par1World.notifyBlocksOfNeighborChange(par2, par3 + 1, par4, blockID);
			return;
		}

		int i = par1World.getBlockMetadata(par2, par3, par4);
		boolean flag = ignoreTick(par1World, par2, par3, par4, i);
		int j = (i & 0xc) >> 2;

		if (isRepeaterPowered && !flag) {
			par1World.scheduleBlockUpdate(par2, par3, par4, blockID, repeaterState[j] * 2);
		} else if (!isRepeaterPowered && flag) {
			par1World.scheduleBlockUpdate(par2, par3, par4, blockID, repeaterState[j] * 2);
		}
	}

	private boolean ignoreTick(World par1World, int par2, int par3, int par4, int par5) {
		int i = getDirection(par5);

		switch (i) {
			case 0:
				return par1World.isBlockIndirectlyProvidingPowerTo(par2, par3, par4 + 1, 3) || par1World.getBlockId(par2, par3, par4 + 1) == Block.redstoneWire.blockID && par1World.getBlockMetadata(par2, par3, par4 + 1) > 0;

			case 2:
				return par1World.isBlockIndirectlyProvidingPowerTo(par2, par3, par4 - 1, 2) || par1World.getBlockId(par2, par3, par4 - 1) == Block.redstoneWire.blockID && par1World.getBlockMetadata(par2, par3, par4 - 1) > 0;

			case 3:
				return par1World.isBlockIndirectlyProvidingPowerTo(par2 + 1, par3, par4, 5) || par1World.getBlockId(par2 + 1, par3, par4) == Block.redstoneWire.blockID && par1World.getBlockMetadata(par2 + 1, par3, par4) > 0;

			case 1:
				return par1World.isBlockIndirectlyProvidingPowerTo(par2 - 1, par3, par4, 4) || par1World.getBlockId(par2 - 1, par3, par4) == Block.redstoneWire.blockID && par1World.getBlockMetadata(par2 - 1, par3, par4) > 0;
		}

		return false;
	}

	public boolean blockActivated(World par1World, int par2, int par3, int par4, EntityPlayer par5EntityPlayer) {
		int i = par1World.getBlockMetadata(par2, par3, par4);
		int j = (i & 0xc) >> 2;
		j = j + 1 << 2 & 0xc;
		par1World.setBlockMetadataWithNotify(par2, par3, par4, j | i & 3);
		return true;
	}

	public boolean canProvidePower() {
		return true;
	}

	public void onBlockPlacedBy(World par1World, int par2, int par3, int par4, EntityLiving par5EntityLiving) {
		int i = ((MathHelper.floor_double((double)((par5EntityLiving.rotationYaw * 4F) / 360F) + 0.5D) & 3) + 2) % 4;
		par1World.setBlockMetadataWithNotify(par2, par3, par4, i);
		boolean flag = ignoreTick(par1World, par2, par3, par4, i);

		if (flag) {
			par1World.scheduleBlockUpdate(par2, par3, par4, blockID, 1);
		}
	}

	public void onBlockAdded(World par1World, int par2, int par3, int par4) {
		par1World.notifyBlocksOfNeighborChange(par2 + 1, par3, par4, blockID);
		par1World.notifyBlocksOfNeighborChange(par2 - 1, par3, par4, blockID);
		par1World.notifyBlocksOfNeighborChange(par2, par3, par4 + 1, blockID);
		par1World.notifyBlocksOfNeighborChange(par2, par3, par4 - 1, blockID);
		par1World.notifyBlocksOfNeighborChange(par2, par3 - 1, par4, blockID);
		par1World.notifyBlocksOfNeighborChange(par2, par3 + 1, par4, blockID);
	}

	public void onBlockDestroyedByPlayer(World par1World, int par2, int par3, int par4, int par5) {
		if (isRepeaterPowered) {
			par1World.notifyBlocksOfNeighborChange(par2 + 1, par3, par4, blockID);
			par1World.notifyBlocksOfNeighborChange(par2 - 1, par3, par4, blockID);
			par1World.notifyBlocksOfNeighborChange(par2, par3, par4 + 1, blockID);
			par1World.notifyBlocksOfNeighborChange(par2, par3, par4 - 1, blockID);
			par1World.notifyBlocksOfNeighborChange(par2, par3 - 1, par4, blockID);
			par1World.notifyBlocksOfNeighborChange(par2, par3 + 1, par4, blockID);
		}

		super.onBlockDestroyedByPlayer(par1World, par2, par3, par4, par5);
	}

	public boolean isOpaqueCube() {
		return false;
	}

	public int idDropped(int par1, Random par2Random, int par3) {
		return Item.redstoneRepeater.shiftedIndex;
	}

	public void randomDisplayTick(World par1World, int par2, int par3, int par4, Random par5Random) {
		if (!isRepeaterPowered) {
			return;
		}

		int i = par1World.getBlockMetadata(par2, par3, par4);
		int j = getDirection(i);
		double d = (double)((float)par2 + 0.5F) + (double)(par5Random.nextFloat() - 0.5F) * 0.20000000000000001D;
		double d1 = (double)((float)par3 + 0.4F) + (double)(par5Random.nextFloat() - 0.5F) * 0.20000000000000001D;
		double d2 = (double)((float)par4 + 0.5F) + (double)(par5Random.nextFloat() - 0.5F) * 0.20000000000000001D;
		double d3 = 0.0D;
		double d4 = 0.0D;

		if (par5Random.nextInt(2) == 0) {
			switch (j) {
				case 0:
					d4 = -0.3125D;
					break;

				case 2:
					d4 = 0.3125D;
					break;

				case 3:
					d3 = -0.3125D;
					break;

				case 1:
					d3 = 0.3125D;
					break;
			}
		} else {
			int k = (i & 0xc) >> 2;

			switch (j) {
				case 0:
					d4 = repeaterTorchOffset[k];
					break;

				case 2:
					d4 = -repeaterTorchOffset[k];
					break;

				case 3:
					d3 = repeaterTorchOffset[k];
					break;

				case 1:
					d3 = -repeaterTorchOffset[k];
					break;
			}
		}

		par1World.spawnParticle("reddust", d + d3, d1, d2 + d4, 0.0D, 0.0D, 0.0D);
	}
}
