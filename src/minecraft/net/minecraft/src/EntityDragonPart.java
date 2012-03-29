package net.minecraft.src;

public class EntityDragonPart extends Entity {
	public final EntityDragonBase entityDragonObj;
	public final String name;

	public EntityDragonPart(EntityDragonBase par1EntityDragonBase, String par2Str, float par3, float par4) {
		super(par1EntityDragonBase.worldObj);
		setSize(par3, par4);
		entityDragonObj = par1EntityDragonBase;
		name = par2Str;
	}

	protected void entityInit() {
	}

	protected void readEntityFromNBT(NBTTagCompound nbttagcompound) {
	}

	protected void writeEntityToNBT(NBTTagCompound nbttagcompound) {
	}

	public boolean canBeCollidedWith() {
		return true;
	}

	public boolean attackEntityFrom(DamageSource par1DamageSource, int par2) {
		return entityDragonObj.attackEntityFromPart(this, par1DamageSource, par2);
	}

	public boolean isEntityEqual(Entity par1Entity) {
		return this == par1Entity || entityDragonObj == par1Entity;
	}
}
