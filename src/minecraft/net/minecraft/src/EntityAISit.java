package net.minecraft.src;

public class EntityAISit extends EntityAIBase {
	private EntityTameable theEntity;
	private boolean field_48408_b;

	public EntityAISit(EntityTameable par1EntityTameable) {
		field_48408_b = false;
		theEntity = par1EntityTameable;
		setMutexBits(5);
	}

	public boolean shouldExecute() {
		if (!theEntity.isTamed()) {
			return false;
		}

		if (theEntity.isInWater()) {
			return false;
		}

		if (!theEntity.onGround) {
			return false;
		}

		EntityLiving entityliving = theEntity.getOwner();

		if (entityliving == null) {
			return true;
		}

		if (theEntity.getDistanceSqToEntity(entityliving) < 144D && entityliving.getAITarget() != null) {
			return false;
		} else {
			return field_48408_b;
		}
	}

	public void startExecuting() {
		theEntity.getNavigator().clearPathEntity();
		theEntity.func_48140_f(true);
	}

	public void resetTask() {
		theEntity.func_48140_f(false);
	}

	public void func_48407_a(boolean par1) {
		field_48408_b = par1;
	}
}
