package net.minecraft.src;

import java.util.Random;

public class EntityAIEatGrass extends EntityAIBase {
	private EntityLiving theEntity;
	private World theWorld;
	int eatGrassTick;

	public EntityAIEatGrass(EntityLiving par1EntityLiving) {
		eatGrassTick = 0;
		theEntity = par1EntityLiving;
		theWorld = par1EntityLiving.worldObj;
		setMutexBits(7);
	}

	public boolean shouldExecute() {
		if (theEntity.getRNG().nextInt(theEntity.isChild() ? 50 : 1000) != 0) {
			return false;
		}

		int i = MathHelper.floor_double(theEntity.posX);
		int j = MathHelper.floor_double(theEntity.posY);
		int k = MathHelper.floor_double(theEntity.posZ);

		if (theWorld.getBlockId(i, j, k) == Block.tallGrass.blockID && theWorld.getBlockMetadata(i, j, k) == 1) {
			return true;
		}

		return theWorld.getBlockId(i, j - 1, k) == Block.grass.blockID;
	}

	public void startExecuting() {
		eatGrassTick = 40;
		theWorld.setEntityState(theEntity, (byte)10);
		theEntity.getNavigator().clearPathEntity();
	}

	public void resetTask() {
		eatGrassTick = 0;
	}

	public boolean continueExecuting() {
		return eatGrassTick > 0;
	}

	public int func_48396_h() {
		return eatGrassTick;
	}

	public void updateTask() {
		eatGrassTick = Math.max(0, eatGrassTick - 1);

		if (eatGrassTick != 4) {
			return;
		}

		int i = MathHelper.floor_double(theEntity.posX);
		int j = MathHelper.floor_double(theEntity.posY);
		int k = MathHelper.floor_double(theEntity.posZ);

		if (theWorld.getBlockId(i, j, k) == Block.tallGrass.blockID) {
			theWorld.playAuxSFX(2001, i, j, k, Block.tallGrass.blockID + 4096);
			theWorld.setBlockWithNotify(i, j, k, 0);
			theEntity.eatGrassBonus();
		} else if (theWorld.getBlockId(i, j - 1, k) == Block.grass.blockID) {
			theWorld.playAuxSFX(2001, i, j - 1, k, Block.grass.blockID);
			theWorld.setBlockWithNotify(i, j - 1, k, Block.dirt.blockID);
			theEntity.eatGrassBonus();
		}
	}
}
