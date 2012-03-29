package net.minecraft.src;

import java.util.*;

public class TileEntityPiston extends TileEntity {
	private int storedBlockID;
	private int storedMetadata;
	private int storedOrientation;
	private boolean extending;
	private boolean shouldHeadBeRendered;
	private float progress;
	private float lastProgress;
	private static List pushedObjects = new ArrayList();

	public TileEntityPiston() {
	}

	public TileEntityPiston(int par1, int par2, int par3, boolean par4, boolean par5) {
		storedBlockID = par1;
		storedMetadata = par2;
		storedOrientation = par3;
		extending = par4;
		shouldHeadBeRendered = par5;
	}

	public int getStoredBlockID() {
		return storedBlockID;
	}

	public int getBlockMetadata() {
		return storedMetadata;
	}

	public boolean isExtending() {
		return extending;
	}

	public int getPistonOrientation() {
		return storedOrientation;
	}

	public boolean shouldRenderHead() {
		return shouldHeadBeRendered;
	}

	public float getProgress(float par1) {
		if (par1 > 1.0F) {
			par1 = 1.0F;
		}

		return lastProgress + (progress - lastProgress) * par1;
	}

	public float getOffsetX(float par1) {
		if (extending) {
			return (getProgress(par1) - 1.0F) * (float)Facing.offsetsXForSide[storedOrientation];
		} else {
			return (1.0F - getProgress(par1)) * (float)Facing.offsetsXForSide[storedOrientation];
		}
	}

	public float getOffsetY(float par1) {
		if (extending) {
			return (getProgress(par1) - 1.0F) * (float)Facing.offsetsYForSide[storedOrientation];
		} else {
			return (1.0F - getProgress(par1)) * (float)Facing.offsetsYForSide[storedOrientation];
		}
	}

	public float getOffsetZ(float par1) {
		if (extending) {
			return (getProgress(par1) - 1.0F) * (float)Facing.offsetsZForSide[storedOrientation];
		} else {
			return (1.0F - getProgress(par1)) * (float)Facing.offsetsZForSide[storedOrientation];
		}
	}

	private void updatePushedObjects(float par1, float par2) {
		if (!extending) {
			par1--;
		} else {
			par1 = 1.0F - par1;
		}

		AxisAlignedBB axisalignedbb = Block.pistonMoving.getAxisAlignedBB(worldObj, xCoord, yCoord, zCoord, storedBlockID, par1, storedOrientation);

		if (axisalignedbb != null) {
			List list = worldObj.getEntitiesWithinAABBExcludingEntity(null, axisalignedbb);

			if (!list.isEmpty()) {
				pushedObjects.addAll(list);
				Entity entity;

				for (Iterator iterator = pushedObjects.iterator(); iterator.hasNext(); entity.moveEntity(par2 * (float)Facing.offsetsXForSide[storedOrientation], par2 * (float)Facing.offsetsYForSide[storedOrientation], par2 * (float)Facing.offsetsZForSide[storedOrientation])) {
					entity = (Entity)iterator.next();
				}

				pushedObjects.clear();
			}
		}
	}

	public void clearPistonTileEntity() {
		if (lastProgress < 1.0F && worldObj != null) {
			lastProgress = progress = 1.0F;
			worldObj.removeBlockTileEntity(xCoord, yCoord, zCoord);
			invalidate();

			if (worldObj.getBlockId(xCoord, yCoord, zCoord) == Block.pistonMoving.blockID) {
				worldObj.setBlockAndMetadataWithNotify(xCoord, yCoord, zCoord, storedBlockID, storedMetadata);
			}
		}
	}

	public void updateEntity() {
		lastProgress = progress;

		if (lastProgress >= 1.0F) {
			updatePushedObjects(1.0F, 0.25F);
			worldObj.removeBlockTileEntity(xCoord, yCoord, zCoord);
			invalidate();

			if (worldObj.getBlockId(xCoord, yCoord, zCoord) == Block.pistonMoving.blockID) {
				worldObj.setBlockAndMetadataWithNotify(xCoord, yCoord, zCoord, storedBlockID, storedMetadata);
			}

			return;
		}

		progress += 0.5F;

		if (progress >= 1.0F) {
			progress = 1.0F;
		}

		if (extending) {
			updatePushedObjects(progress, (progress - lastProgress) + 0.0625F);
		}
	}

	public void readFromNBT(NBTTagCompound par1NBTTagCompound) {
		super.readFromNBT(par1NBTTagCompound);
		storedBlockID = par1NBTTagCompound.getInteger("blockId");
		storedMetadata = par1NBTTagCompound.getInteger("blockData");
		storedOrientation = par1NBTTagCompound.getInteger("facing");
		lastProgress = progress = par1NBTTagCompound.getFloat("progress");
		extending = par1NBTTagCompound.getBoolean("extending");
	}

	public void writeToNBT(NBTTagCompound par1NBTTagCompound) {
		super.writeToNBT(par1NBTTagCompound);
		par1NBTTagCompound.setInteger("blockId", storedBlockID);
		par1NBTTagCompound.setInteger("blockData", storedMetadata);
		par1NBTTagCompound.setInteger("facing", storedOrientation);
		par1NBTTagCompound.setFloat("progress", lastProgress);
		par1NBTTagCompound.setBoolean("extending", extending);
	}
}
