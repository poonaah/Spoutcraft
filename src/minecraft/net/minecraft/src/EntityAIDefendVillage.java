package net.minecraft.src;

public class EntityAIDefendVillage extends EntityAITarget {
	EntityIronGolem irongolem;
	EntityLiving villageAgressorTarget;

	public EntityAIDefendVillage(EntityIronGolem par1EntityIronGolem) {
		super(par1EntityIronGolem, 16F, false, true);
		irongolem = par1EntityIronGolem;
		setMutexBits(1);
	}

	public boolean shouldExecute() {
		Village village = irongolem.getVillage();

		if (village == null) {
			return false;
		} else {
			villageAgressorTarget = village.findNearestVillageAggressor(irongolem);
			return func_48376_a(villageAgressorTarget, false);
		}
	}

	public void startExecuting() {
		irongolem.setAttackTarget(villageAgressorTarget);
		super.startExecuting();
	}
}
