package net.minecraft.src;

import java.util.*;

public class BlockRedstoneTorch extends BlockTorch {
	private boolean torchActive;
	private static List torchUpdates = new ArrayList();

	public int getBlockTextureFromSideAndMetadata(int par1, int par2) {
		if (par1 == 1) {
			return Block.redstoneWire.getBlockTextureFromSideAndMetadata(par1, par2);
		} else {
			return super.getBlockTextureFromSideAndMetadata(par1, par2);
		}
	}

	private boolean checkForBurnout(World par1World, int par2, int par3, int par4, boolean par5) {
		if (par5) {
			torchUpdates.add(new RedstoneUpdateInfo(par2, par3, par4, par1World.getWorldTime()));
		}

		int i = 0;

		for (int j = 0; j < torchUpdates.size(); j++) {
			RedstoneUpdateInfo redstoneupdateinfo = (RedstoneUpdateInfo)torchUpdates.get(j);

			if (redstoneupdateinfo.x == par2 && redstoneupdateinfo.y == par3 && redstoneupdateinfo.z == par4 && ++i >= 8) {
				return true;
			}
		}

		return false;
	}

	protected BlockRedstoneTorch(int par1, int par2, boolean par3) {
		super(par1, par2);
		torchActive = false;
		torchActive = par3;
		setTickRandomly(true);
	}

	public int tickRate() {
		return 2;
	}

	public void onBlockAdded(World par1World, int par2, int par3, int par4) {
		if (par1World.getBlockMetadata(par2, par3, par4) == 0) {
			super.onBlockAdded(par1World, par2, par3, par4);
		}

		if (torchActive) {
			par1World.notifyBlocksOfNeighborChange(par2, par3 - 1, par4, blockID);
			par1World.notifyBlocksOfNeighborChange(par2, par3 + 1, par4, blockID);
			par1World.notifyBlocksOfNeighborChange(par2 - 1, par3, par4, blockID);
			par1World.notifyBlocksOfNeighborChange(par2 + 1, par3, par4, blockID);
			par1World.notifyBlocksOfNeighborChange(par2, par3, par4 - 1, blockID);
			par1World.notifyBlocksOfNeighborChange(par2, par3, par4 + 1, blockID);
		}
	}

	public void onBlockRemoval(World par1World, int par2, int par3, int par4) {
		if (torchActive) {
			par1World.notifyBlocksOfNeighborChange(par2, par3 - 1, par4, blockID);
			par1World.notifyBlocksOfNeighborChange(par2, par3 + 1, par4, blockID);
			par1World.notifyBlocksOfNeighborChange(par2 - 1, par3, par4, blockID);
			par1World.notifyBlocksOfNeighborChange(par2 + 1, par3, par4, blockID);
			par1World.notifyBlocksOfNeighborChange(par2, par3, par4 - 1, blockID);
			par1World.notifyBlocksOfNeighborChange(par2, par3, par4 + 1, blockID);
		}
	}

	public boolean isPoweringTo(IBlockAccess par1IBlockAccess, int par2, int par3, int par4, int par5) {
		if (!torchActive) {
			return false;
		}

		int i = par1IBlockAccess.getBlockMetadata(par2, par3, par4);

		if (i == 5 && par5 == 1) {
			return false;
		}

		if (i == 3 && par5 == 3) {
			return false;
		}

		if (i == 4 && par5 == 2) {
			return false;
		}

		if (i == 1 && par5 == 5) {
			return false;
		}

		return i != 2 || par5 != 4;
	}

	private boolean isIndirectlyPowered(World par1World, int par2, int par3, int par4) {
		int i = par1World.getBlockMetadata(par2, par3, par4);

		if (i == 5 && par1World.isBlockIndirectlyProvidingPowerTo(par2, par3 - 1, par4, 0)) {
			return true;
		}

		if (i == 3 && par1World.isBlockIndirectlyProvidingPowerTo(par2, par3, par4 - 1, 2)) {
			return true;
		}

		if (i == 4 && par1World.isBlockIndirectlyProvidingPowerTo(par2, par3, par4 + 1, 3)) {
			return true;
		}

		if (i == 1 && par1World.isBlockIndirectlyProvidingPowerTo(par2 - 1, par3, par4, 4)) {
			return true;
		}

		return i == 2 && par1World.isBlockIndirectlyProvidingPowerTo(par2 + 1, par3, par4, 5);
	}

	public void updateTick(World par1World, int par2, int par3, int par4, Random par5Random) {
		boolean flag = isIndirectlyPowered(par1World, par2, par3, par4);

		for (; torchUpdates.size() > 0 && par1World.getWorldTime() - ((RedstoneUpdateInfo)torchUpdates.get(0)).updateTime > 60L; torchUpdates.remove(0)) { }

		if (torchActive) {
			if (flag) {
				par1World.setBlockAndMetadataWithNotify(par2, par3, par4, Block.torchRedstoneIdle.blockID, par1World.getBlockMetadata(par2, par3, par4));

				if (checkForBurnout(par1World, par2, par3, par4, true)) {
					par1World.playSoundEffect((float)par2 + 0.5F, (float)par3 + 0.5F, (float)par4 + 0.5F, "random.fizz", 0.5F, 2.6F + (par1World.rand.nextFloat() - par1World.rand.nextFloat()) * 0.8F);

					for (int i = 0; i < 5; i++) {
						double d = (double)par2 + par5Random.nextDouble() * 0.59999999999999998D + 0.20000000000000001D;
						double d1 = (double)par3 + par5Random.nextDouble() * 0.59999999999999998D + 0.20000000000000001D;
						double d2 = (double)par4 + par5Random.nextDouble() * 0.59999999999999998D + 0.20000000000000001D;
						par1World.spawnParticle("smoke", d, d1, d2, 0.0D, 0.0D, 0.0D);
					}
				}
			}
		} else if (!flag && !checkForBurnout(par1World, par2, par3, par4, false)) {
			par1World.setBlockAndMetadataWithNotify(par2, par3, par4, Block.torchRedstoneActive.blockID, par1World.getBlockMetadata(par2, par3, par4));
		}
	}

	public void onNeighborBlockChange(World par1World, int par2, int par3, int par4, int par5) {
		super.onNeighborBlockChange(par1World, par2, par3, par4, par5);
		par1World.scheduleBlockUpdate(par2, par3, par4, blockID, tickRate());
	}

	public boolean isIndirectlyPoweringTo(World par1World, int par2, int par3, int par4, int par5) {
		if (par5 == 0) {
			return isPoweringTo(par1World, par2, par3, par4, par5);
		} else {
			return false;
		}
	}

	public int idDropped(int par1, Random par2Random, int par3) {
		return Block.torchRedstoneActive.blockID;
	}

	public boolean canProvidePower() {
		return true;
	}

	public void randomDisplayTick(World par1World, int par2, int par3, int par4, Random par5Random) {
		if (!torchActive) {
			return;
		}

		int i = par1World.getBlockMetadata(par2, par3, par4);
		double d = (double)((float)par2 + 0.5F) + (double)(par5Random.nextFloat() - 0.5F) * 0.20000000000000001D;
		double d1 = (double)((float)par3 + 0.7F) + (double)(par5Random.nextFloat() - 0.5F) * 0.20000000000000001D;
		double d2 = (double)((float)par4 + 0.5F) + (double)(par5Random.nextFloat() - 0.5F) * 0.20000000000000001D;
		double d3 = 0.2199999988079071D;
		double d4 = 0.27000001072883606D;

		if (i == 1) {
			par1World.spawnParticle("reddust", d - d4, d1 + d3, d2, 0.0D, 0.0D, 0.0D);
		} else if (i == 2) {
			par1World.spawnParticle("reddust", d + d4, d1 + d3, d2, 0.0D, 0.0D, 0.0D);
		} else if (i == 3) {
			par1World.spawnParticle("reddust", d, d1 + d3, d2 - d4, 0.0D, 0.0D, 0.0D);
		} else if (i == 4) {
			par1World.spawnParticle("reddust", d, d1 + d3, d2 + d4, 0.0D, 0.0D, 0.0D);
		} else {
			par1World.spawnParticle("reddust", d, d1, d2, 0.0D, 0.0D, 0.0D);
		}
	}
}