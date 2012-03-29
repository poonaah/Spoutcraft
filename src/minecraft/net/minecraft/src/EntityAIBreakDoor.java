package net.minecraft.src;

import java.util.Random;

public class EntityAIBreakDoor extends EntityAIDoorInteract {
	private int field_48329_i;

	public EntityAIBreakDoor(EntityLiving par1EntityLiving) {
		super(par1EntityLiving);
	}

	public boolean shouldExecute() {
		if (!super.shouldExecute()) {
			return false;
		} else {
			return !targetDoor.func_48213_h(theEntity.worldObj, entityPosX, entityPosY, entityPosZ);
		}
	}

	public void startExecuting() {
		super.startExecuting();
		field_48329_i = 240;
	}

	public boolean continueExecuting() {
		double d = theEntity.getDistanceSq(entityPosX, entityPosY, entityPosZ);
		return field_48329_i >= 0 && !targetDoor.func_48213_h(theEntity.worldObj, entityPosX, entityPosY, entityPosZ) && d < 4D;
	}

	public void updateTask() {
		super.updateTask();

		if (theEntity.getRNG().nextInt(20) == 0) {
			theEntity.worldObj.playAuxSFX(1010, entityPosX, entityPosY, entityPosZ, 0);
		}

		if (--field_48329_i == 0 && theEntity.worldObj.difficultySetting == 3) {
			theEntity.worldObj.setBlockWithNotify(entityPosX, entityPosY, entityPosZ, 0);
			theEntity.worldObj.playAuxSFX(1012, entityPosX, entityPosY, entityPosZ, 0);
			theEntity.worldObj.playAuxSFX(2001, entityPosX, entityPosY, entityPosZ, targetDoor.blockID);
		}
	}
}
