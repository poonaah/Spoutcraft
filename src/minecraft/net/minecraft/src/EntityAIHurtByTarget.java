package net.minecraft.src;

import java.util.Iterator;
import java.util.List;

public class EntityAIHurtByTarget extends EntityAITarget {
	boolean field_48395_a;

	public EntityAIHurtByTarget(EntityLiving par1EntityLiving, boolean par2) {
		super(par1EntityLiving, 16F, false);
		field_48395_a = par2;
		setMutexBits(1);
	}

	public boolean shouldExecute() {
		return func_48376_a(taskOwner.getAITarget(), true);
	}

	public void startExecuting() {
		taskOwner.setAttackTarget(taskOwner.getAITarget());

		if (field_48395_a) {
			List list = taskOwner.worldObj.getEntitiesWithinAABB(taskOwner.getClass(), AxisAlignedBB.getBoundingBoxFromPool(taskOwner.posX, taskOwner.posY, taskOwner.posZ, taskOwner.posX + 1.0D, taskOwner.posY + 1.0D, taskOwner.posZ + 1.0D).expand(field_48379_d, 4D, field_48379_d));
			Iterator iterator = list.iterator();

			do {
				if (!iterator.hasNext()) {
					break;
				}

				Entity entity = (Entity)iterator.next();
				EntityLiving entityliving = (EntityLiving)entity;

				if (taskOwner != entityliving && entityliving.getAttackTarget() == null) {
					entityliving.setAttackTarget(taskOwner.getAITarget());
				}
			} while (true);
		}

		super.startExecuting();
	}
}
