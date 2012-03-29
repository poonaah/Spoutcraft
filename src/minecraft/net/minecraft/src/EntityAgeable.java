package net.minecraft.src;

public abstract class EntityAgeable extends EntityCreature {
	public EntityAgeable(World par1World) {
		super(par1World);
	}

	protected void entityInit() {
		super.entityInit();
		dataWatcher.addObject(12, new Integer(0));
	}

	public int getGrowingAge() {
		return dataWatcher.getWatchableObjectInt(12);
	}

	public void setGrowingAge(int par1) {
		dataWatcher.updateObject(12, Integer.valueOf(par1));
	}

	public void writeEntityToNBT(NBTTagCompound par1NBTTagCompound) {
		super.writeEntityToNBT(par1NBTTagCompound);
		par1NBTTagCompound.setInteger("Age", getGrowingAge());
	}

	public void readEntityFromNBT(NBTTagCompound par1NBTTagCompound) {
		super.readEntityFromNBT(par1NBTTagCompound);
		setGrowingAge(par1NBTTagCompound.getInteger("Age"));
	}

	public void onLivingUpdate() {
		super.onLivingUpdate();
		int i = getGrowingAge();

		if (i < 0) {
			i++;
			setGrowingAge(i);
		} else if (i > 0) {
			i--;
			setGrowingAge(i);
		}
	}

	public boolean isChild() {
		return getGrowingAge() < 0;
	}
}
