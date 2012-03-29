package net.minecraft.src;

import java.util.Random;

public class EntityAILookAtVillager extends EntityAIBase {
	private EntityIronGolem theGolem;
	private EntityVillager theVillager;
	private int field_48405_c;

	public EntityAILookAtVillager(EntityIronGolem par1EntityIronGolem) {
		theGolem = par1EntityIronGolem;
		setMutexBits(3);
	}

	public boolean shouldExecute() {
		if (!theGolem.worldObj.isDaytime()) {
			return false;
		}

		if (theGolem.getRNG().nextInt(8000) != 0) {
			return false;
		} else {
			theVillager = (EntityVillager)theGolem.worldObj.findNearestEntityWithinAABB(net.minecraft.src.EntityVillager.class, theGolem.boundingBox.expand(6D, 2D, 6D), theGolem);
			return theVillager != null;
		}
	}

	public boolean continueExecuting() {
		return field_48405_c > 0;
	}

	public void startExecuting() {
		field_48405_c = 400;
		theGolem.func_48116_a(true);
	}

	public void resetTask() {
		theGolem.func_48116_a(false);
		theVillager = null;
	}

	public void updateTask() {
		theGolem.getLookHelper().setLookPositionWithEntity(theVillager, 30F, 30F);
		field_48405_c--;
	}
}
