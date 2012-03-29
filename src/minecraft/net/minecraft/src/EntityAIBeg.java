package net.minecraft.src;

import java.util.Random;

public class EntityAIBeg extends EntityAIBase {
	private EntityWolf theWolf;
	private EntityPlayer field_48348_b;
	private World field_48349_c;
	private float field_48346_d;
	private int field_48347_e;

	public EntityAIBeg(EntityWolf par1EntityWolf, float par2) {
		theWolf = par1EntityWolf;
		field_48349_c = par1EntityWolf.worldObj;
		field_48346_d = par2;
		setMutexBits(2);
	}

	public boolean shouldExecute() {
		field_48348_b = field_48349_c.getClosestPlayerToEntity(theWolf, field_48346_d);

		if (field_48348_b == null) {
			return false;
		} else {
			return func_48345_a(field_48348_b);
		}
	}

	public boolean continueExecuting() {
		if (!field_48348_b.isEntityAlive()) {
			return false;
		}

		if (theWolf.getDistanceSqToEntity(field_48348_b) > (double)(field_48346_d * field_48346_d)) {
			return false;
		} else {
			return field_48347_e > 0 && func_48345_a(field_48348_b);
		}
	}

	public void startExecuting() {
		theWolf.func_48150_h(true);
		field_48347_e = 40 + theWolf.getRNG().nextInt(40);
	}

	public void resetTask() {
		theWolf.func_48150_h(false);
		field_48348_b = null;
	}

	public void updateTask() {
		theWolf.getLookHelper().setLookPosition(field_48348_b.posX, field_48348_b.posY + (double)field_48348_b.getEyeHeight(), field_48348_b.posZ, 10F, theWolf.getVerticalFaceSpeed());
		field_48347_e--;
	}

	private boolean func_48345_a(EntityPlayer par1EntityPlayer) {
		ItemStack itemstack = par1EntityPlayer.inventory.getCurrentItem();

		if (itemstack == null) {
			return false;
		}

		if (!theWolf.isTamed() && itemstack.itemID == Item.bone.shiftedIndex) {
			return true;
		} else {
			return theWolf.isWheat(itemstack);
		}
	}
}
