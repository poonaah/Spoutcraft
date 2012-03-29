package net.minecraft.src;

public abstract class EntityGolem extends EntityCreature {
	public EntityGolem(World par1World) {
		super(par1World);
	}

	protected void fall(float f) {
	}

	public void writeEntityToNBT(NBTTagCompound par1NBTTagCompound) {
		super.writeEntityToNBT(par1NBTTagCompound);
	}

	public void readEntityFromNBT(NBTTagCompound par1NBTTagCompound) {
		super.readEntityFromNBT(par1NBTTagCompound);
	}

	protected String getLivingSound() {
		return "none";
	}

	protected String getHurtSound() {
		return "none";
	}

	protected String getDeathSound() {
		return "none";
	}

	public int getTalkInterval() {
		return 120;
	}

	protected boolean canDespawn() {
		return false;
	}
}
