package net.minecraft.src;

public class EntityAIRestrictSun extends EntityAIBase {
	private EntityCreature theEntity;

	public EntityAIRestrictSun(EntityCreature par1EntityCreature) {
		theEntity = par1EntityCreature;
	}

	public boolean shouldExecute() {
		return theEntity.worldObj.isDaytime();
	}

	public void startExecuting() {
		theEntity.getNavigator().func_48680_d(true);
	}

	public void resetTask() {
		theEntity.getNavigator().func_48680_d(false);
	}
}
