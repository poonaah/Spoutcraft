package net.minecraft.src;

public class EntityAICreeperSwell extends EntityAIBase {
	EntityCreeper swellingCreeper;
	EntityLiving creeperAttackTarget;

	public EntityAICreeperSwell(EntityCreeper par1EntityCreeper) {
		swellingCreeper = par1EntityCreeper;
		setMutexBits(1);
	}

	public boolean shouldExecute() {
		EntityLiving entityliving = swellingCreeper.getAttackTarget();
		return swellingCreeper.getCreeperState() > 0 || entityliving != null && swellingCreeper.getDistanceSqToEntity(entityliving) < 9D;
	}

	public void startExecuting() {
		swellingCreeper.getNavigator().clearPathEntity();
		creeperAttackTarget = swellingCreeper.getAttackTarget();
	}

	public void resetTask() {
		creeperAttackTarget = null;
	}

	public void updateTask() {
		if (creeperAttackTarget == null) {
			swellingCreeper.setCreeperState(-1);
			return;
		}

		if (swellingCreeper.getDistanceSqToEntity(creeperAttackTarget) > 49D) {
			swellingCreeper.setCreeperState(-1);
			return;
		}

		if (!swellingCreeper.func_48090_aM().canSee(creeperAttackTarget)) {
			swellingCreeper.setCreeperState(-1);
			return;
		} else {
			swellingCreeper.setCreeperState(1);
			return;
		}
	}
}
