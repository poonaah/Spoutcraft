package net.minecraft.src;

import java.util.List;
import java.util.Random;

public class BlockPressurePlate extends Block {
	private EnumMobType triggerMobType;

	protected BlockPressurePlate(int par1, int par2, EnumMobType par3EnumMobType, Material par4Material) {
		super(par1, par2, par4Material);
		triggerMobType = par3EnumMobType;
		setTickRandomly(true);
		float f = 0.0625F;
		setBlockBounds(f, 0.0F, f, 1.0F - f, 0.03125F, 1.0F - f);
	}

	public int tickRate() {
		return 20;
	}

	public AxisAlignedBB getCollisionBoundingBoxFromPool(World par1World, int par2, int par3, int i) {
		return null;
	}

	public boolean isOpaqueCube() {
		return false;
	}

	public boolean renderAsNormalBlock() {
		return false;
	}

	public boolean getBlocksMovement(IBlockAccess par1IBlockAccess, int par2, int par3, int i) {
		return true;
	}

	public boolean canPlaceBlockAt(World par1World, int par2, int par3, int par4) {
		return par1World.isBlockNormalCube(par2, par3 - 1, par4) || par1World.getBlockId(par2, par3 - 1, par4) == Block.fence.blockID;
	}

	public void onBlockAdded(World world, int i, int j, int k) {
	}

	public void onNeighborBlockChange(World par1World, int par2, int par3, int par4, int par5) {
		boolean flag = false;

		if (!par1World.isBlockNormalCube(par2, par3 - 1, par4) && par1World.getBlockId(par2, par3 - 1, par4) != Block.fence.blockID) {
			flag = true;
		}

		if (flag) {
			dropBlockAsItem(par1World, par2, par3, par4, par1World.getBlockMetadata(par2, par3, par4), 0);
			par1World.setBlockWithNotify(par2, par3, par4, 0);
		}
	}

	public void updateTick(World par1World, int par2, int par3, int par4, Random par5Random) {
		if (par1World.isRemote) {
			return;
		}

		if (par1World.getBlockMetadata(par2, par3, par4) == 0) {
			return;
		} else {
			setStateIfMobInteractsWithPlate(par1World, par2, par3, par4);
			return;
		}
	}

	public void onEntityCollidedWithBlock(World par1World, int par2, int par3, int par4, Entity par5Entity) {
		if (par1World.isRemote) {
			return;
		}

		if (par1World.getBlockMetadata(par2, par3, par4) == 1) {
			return;
		} else {
			setStateIfMobInteractsWithPlate(par1World, par2, par3, par4);
			return;
		}
	}

	private void setStateIfMobInteractsWithPlate(World par1World, int par2, int par3, int par4) {
		boolean flag = par1World.getBlockMetadata(par2, par3, par4) == 1;
		boolean flag1 = false;
		float f = 0.125F;
		List list = null;

		if (triggerMobType == EnumMobType.everything) {
			list = par1World.getEntitiesWithinAABBExcludingEntity(null, AxisAlignedBB.getBoundingBoxFromPool((float)par2 + f, par3, (float)par4 + f, (float)(par2 + 1) - f, (double)par3 + 0.25D, (float)(par4 + 1) - f));
		}

		if (triggerMobType == EnumMobType.mobs) {
			list = par1World.getEntitiesWithinAABB(net.minecraft.src.EntityLiving.class, AxisAlignedBB.getBoundingBoxFromPool((float)par2 + f, par3, (float)par4 + f, (float)(par2 + 1) - f, (double)par3 + 0.25D, (float)(par4 + 1) - f));
		}

		if (triggerMobType == EnumMobType.players) {
			list = par1World.getEntitiesWithinAABB(net.minecraft.src.EntityPlayer.class, AxisAlignedBB.getBoundingBoxFromPool((float)par2 + f, par3, (float)par4 + f, (float)(par2 + 1) - f, (double)par3 + 0.25D, (float)(par4 + 1) - f));
		}

		if (list.size() > 0) {
			flag1 = true;
		}

		if (flag1 && !flag) {
			par1World.setBlockMetadataWithNotify(par2, par3, par4, 1);
			par1World.notifyBlocksOfNeighborChange(par2, par3, par4, blockID);
			par1World.notifyBlocksOfNeighborChange(par2, par3 - 1, par4, blockID);
			par1World.markBlocksDirty(par2, par3, par4, par2, par3, par4);
			par1World.playSoundEffect((double)par2 + 0.5D, (double)par3 + 0.10000000000000001D, (double)par4 + 0.5D, "random.click", 0.3F, 0.6F);
		}

		if (!flag1 && flag) {
			par1World.setBlockMetadataWithNotify(par2, par3, par4, 0);
			par1World.notifyBlocksOfNeighborChange(par2, par3, par4, blockID);
			par1World.notifyBlocksOfNeighborChange(par2, par3 - 1, par4, blockID);
			par1World.markBlocksDirty(par2, par3, par4, par2, par3, par4);
			par1World.playSoundEffect((double)par2 + 0.5D, (double)par3 + 0.10000000000000001D, (double)par4 + 0.5D, "random.click", 0.3F, 0.5F);
		}

		if (flag1) {
			par1World.scheduleBlockUpdate(par2, par3, par4, blockID, tickRate());
		}
	}

	public void onBlockRemoval(World par1World, int par2, int par3, int par4) {
		int i = par1World.getBlockMetadata(par2, par3, par4);

		if (i > 0) {
			par1World.notifyBlocksOfNeighborChange(par2, par3, par4, blockID);
			par1World.notifyBlocksOfNeighborChange(par2, par3 - 1, par4, blockID);
		}

		super.onBlockRemoval(par1World, par2, par3, par4);
	}

	public void setBlockBoundsBasedOnState(IBlockAccess par1IBlockAccess, int par2, int par3, int par4) {
		boolean flag = par1IBlockAccess.getBlockMetadata(par2, par3, par4) == 1;
		float f = 0.0625F;

		if (flag) {
			setBlockBounds(f, 0.0F, f, 1.0F - f, 0.03125F, 1.0F - f);
		} else {
			setBlockBounds(f, 0.0F, f, 1.0F - f, 0.0625F, 1.0F - f);
		}
	}

	public boolean isPoweringTo(IBlockAccess par1IBlockAccess, int par2, int par3, int par4, int par5) {
		return par1IBlockAccess.getBlockMetadata(par2, par3, par4) > 0;
	}

	public boolean isIndirectlyPoweringTo(World par1World, int par2, int par3, int par4, int par5) {
		if (par1World.getBlockMetadata(par2, par3, par4) == 0) {
			return false;
		} else {
			return par5 == 1;
		}
	}

	public boolean canProvidePower() {
		return true;
	}

	public void setBlockBoundsForItemRender() {
		float f = 0.5F;
		float f1 = 0.125F;
		float f2 = 0.5F;
		setBlockBounds(0.5F - f, 0.5F - f1, 0.5F - f2, 0.5F + f, 0.5F + f1, 0.5F + f2);
	}

	public int getMobilityFlag() {
		return 1;
	}
}
