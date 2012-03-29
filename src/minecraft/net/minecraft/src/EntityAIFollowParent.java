package net.minecraft.src;

import java.util.Iterator;
import java.util.List;

public class EntityAIFollowParent extends EntityAIBase {
	EntityAnimal childAnimal;
	EntityAnimal parentAnimal;
	float field_48248_c;
	private int field_48246_d;

	public EntityAIFollowParent(EntityAnimal par1EntityAnimal, float par2) {
		childAnimal = par1EntityAnimal;
		field_48248_c = par2;
	}

	public boolean shouldExecute() {
		if (childAnimal.getGrowingAge() >= 0) {
			return false;
		}

		List list = childAnimal.worldObj.getEntitiesWithinAABB(childAnimal.getClass(), childAnimal.boundingBox.expand(8D, 4D, 8D));
		EntityAnimal entityanimal = null;
		double d = Double.MAX_VALUE;
		Iterator iterator = list.iterator();

		do {
			if (!iterator.hasNext()) {
				break;
			}

			Entity entity = (Entity)iterator.next();
			EntityAnimal entityanimal1 = (EntityAnimal)entity;

			if (entityanimal1.getGrowingAge() >= 0) {
				double d1 = childAnimal.getDistanceSqToEntity(entityanimal1);

				if (d1 <= d) {
					d = d1;
					entityanimal = entityanimal1;
				}
			}
		} while (true);

		if (entityanimal == null) {
			return false;
		}

		if (d < 9D) {
			return false;
		} else {
			parentAnimal = entityanimal;
			return true;
		}
	}

	public boolean continueExecuting() {
		if (!parentAnimal.isEntityAlive()) {
			return false;
		}

		double d = childAnimal.getDistanceSqToEntity(parentAnimal);
		return d >= 9D && d <= 256D;
	}

	public void startExecuting() {
		field_48246_d = 0;
	}

	public void resetTask() {
		parentAnimal = null;
	}

	public void updateTask() {
		if (--field_48246_d > 0) {
			return;
		} else {
			field_48246_d = 10;
			childAnimal.getNavigator().func_48667_a(parentAnimal, field_48248_c);
			return;
		}
	}
}
