package net.minecraft.src;

import java.util.Random;

public class EntityAILeapAtTarget extends EntityAIBase {
	EntityLiving leaper;
	EntityLiving leapTarget;
	float leapMotionY;

	public EntityAILeapAtTarget(EntityLiving par1EntityLiving, float par2) {
		leaper = par1EntityLiving;
		leapMotionY = par2;
		setMutexBits(5);
	}

	public boolean shouldExecute() {
		leapTarget = leaper.getAttackTarget();

		if (leapTarget == null) {
			return false;
		}

		double d = leaper.getDistanceSqToEntity(leapTarget);

		if (d < 4D || d > 16D) {
			return false;
		}

		if (!leaper.onGround) {
			return false;
		}

		return leaper.getRNG().nextInt(5) == 0;
	}

	public boolean continueExecuting() {
		return !leaper.onGround;
	}

	public void startExecuting() {
		double d = leapTarget.posX - leaper.posX;
		double d1 = leapTarget.posZ - leaper.posZ;
		float f = MathHelper.sqrt_double(d * d + d1 * d1);
		leaper.motionX += (d / (double)f) * 0.5D * 0.80000001192092896D + leaper.motionX * 0.20000000298023224D;
		leaper.motionZ += (d1 / (double)f) * 0.5D * 0.80000001192092896D + leaper.motionZ * 0.20000000298023224D;
		leaper.motionY = leapMotionY;
	}
}
