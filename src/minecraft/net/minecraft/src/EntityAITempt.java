package net.minecraft.src;

public class EntityAITempt extends EntityAIBase {
	private EntityCreature temptedEntity;
	private float field_48275_b;
	private double field_48276_c;
	private double field_48273_d;
	private double field_48274_e;
	private double field_48271_f;
	private double field_48272_g;
	private EntityPlayer temptingPlayer;
	private int delayTemptCounter;
	private boolean field_48280_j;
	private int breedingFood;
	private boolean scaredByPlayerMovement;
	private boolean field_48279_m;

	public EntityAITempt(EntityCreature par1EntityCreature, float par2, int par3, boolean par4) {
		delayTemptCounter = 0;
		temptedEntity = par1EntityCreature;
		field_48275_b = par2;
		breedingFood = par3;
		scaredByPlayerMovement = par4;
		setMutexBits(3);
	}

	public boolean shouldExecute() {
		if (delayTemptCounter > 0) {
			delayTemptCounter--;
			return false;
		}

		temptingPlayer = temptedEntity.worldObj.getClosestPlayerToEntity(temptedEntity, 10D);

		if (temptingPlayer == null) {
			return false;
		}

		ItemStack itemstack = temptingPlayer.getCurrentEquippedItem();

		if (itemstack == null) {
			return false;
		}

		return itemstack.itemID == breedingFood;
	}

	public boolean continueExecuting() {
		if (scaredByPlayerMovement) {
			if (temptedEntity.getDistanceSqToEntity(temptingPlayer) < 36D) {
				if (temptingPlayer.getDistanceSq(field_48276_c, field_48273_d, field_48274_e) > 0.010000000000000002D) {
					return false;
				}

				if (Math.abs((double)temptingPlayer.rotationPitch - field_48271_f) > 5D || Math.abs((double)temptingPlayer.rotationYaw - field_48272_g) > 5D) {
					return false;
				}
			} else {
				field_48276_c = temptingPlayer.posX;
				field_48273_d = temptingPlayer.posY;
				field_48274_e = temptingPlayer.posZ;
			}

			field_48271_f = temptingPlayer.rotationPitch;
			field_48272_g = temptingPlayer.rotationYaw;
		}

		return shouldExecute();
	}

	public void startExecuting() {
		field_48276_c = temptingPlayer.posX;
		field_48273_d = temptingPlayer.posY;
		field_48274_e = temptingPlayer.posZ;
		field_48280_j = true;
		field_48279_m = temptedEntity.getNavigator().func_48658_a();
		temptedEntity.getNavigator().func_48664_a(false);
	}

	public void resetTask() {
		temptingPlayer = null;
		temptedEntity.getNavigator().clearPathEntity();
		delayTemptCounter = 100;
		field_48280_j = false;
		temptedEntity.getNavigator().func_48664_a(field_48279_m);
	}

	public void updateTask() {
		temptedEntity.getLookHelper().setLookPositionWithEntity(temptingPlayer, 30F, temptedEntity.getVerticalFaceSpeed());

		if (temptedEntity.getDistanceSqToEntity(temptingPlayer) < 6.25D) {
			temptedEntity.getNavigator().clearPathEntity();
		} else {
			temptedEntity.getNavigator().func_48667_a(temptingPlayer, field_48275_b);
		}
	}

	public boolean func_48270_h() {
		return field_48280_j;
	}
}
