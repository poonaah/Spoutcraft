package net.minecraft.src;

public class EntityAIOpenDoor extends EntityAIDoorInteract {
	boolean field_48328_i;
	int field_48327_j;

	public EntityAIOpenDoor(EntityLiving par1EntityLiving, boolean par2) {
		super(par1EntityLiving);
		theEntity = par1EntityLiving;
		field_48328_i = par2;
	}

	public boolean continueExecuting() {
		return field_48328_i && field_48327_j > 0 && super.continueExecuting();
	}

	public void startExecuting() {
		field_48327_j = 20;
		targetDoor.onPoweredBlockChange(theEntity.worldObj, entityPosX, entityPosY, entityPosZ, true);
	}

	public void resetTask() {
		if (field_48328_i) {
			targetDoor.onPoweredBlockChange(theEntity.worldObj, entityPosX, entityPosY, entityPosZ, false);
		}
	}

	public void updateTask() {
		field_48327_j--;
		super.updateTask();
	}
}
