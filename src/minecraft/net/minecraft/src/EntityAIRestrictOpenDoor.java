package net.minecraft.src;

public class EntityAIRestrictOpenDoor extends EntityAIBase {
	private EntityCreature entityObj;
	private VillageDoorInfo frontDoor;

	public EntityAIRestrictOpenDoor(EntityCreature par1EntityCreature) {
		entityObj = par1EntityCreature;
	}

	public boolean shouldExecute() {
		if (entityObj.worldObj.isDaytime()) {
			return false;
		}

		Village village = entityObj.worldObj.villageCollectionObj.findNearestVillage(MathHelper.floor_double(entityObj.posX), MathHelper.floor_double(entityObj.posY), MathHelper.floor_double(entityObj.posZ), 16);

		if (village == null) {
			return false;
		}

		frontDoor = village.findNearestDoor(MathHelper.floor_double(entityObj.posX), MathHelper.floor_double(entityObj.posY), MathHelper.floor_double(entityObj.posZ));

		if (frontDoor == null) {
			return false;
		} else {
			return (double)frontDoor.getInsideDistanceSquare(MathHelper.floor_double(entityObj.posX), MathHelper.floor_double(entityObj.posY), MathHelper.floor_double(entityObj.posZ)) < 2.25D;
		}
	}

	public boolean continueExecuting() {
		if (entityObj.worldObj.isDaytime()) {
			return false;
		} else {
			return !frontDoor.isDetachedFromVillageFlag && frontDoor.isInside(MathHelper.floor_double(entityObj.posX), MathHelper.floor_double(entityObj.posZ));
		}
	}

	public void startExecuting() {
		entityObj.getNavigator().setBreakDoors(false);
		entityObj.getNavigator().func_48663_c(false);
	}

	public void resetTask() {
		entityObj.getNavigator().setBreakDoors(true);
		entityObj.getNavigator().func_48663_c(true);
		frontDoor = null;
	}

	public void updateTask() {
		frontDoor.incrementDoorOpeningRestrictionCounter();
	}
}
