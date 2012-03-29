package net.minecraft.src;

public class PlayerCapabilities {
	public boolean disableDamage;
	public boolean isFlying;
	public boolean allowFlying;
	public boolean isCreativeMode;

	public PlayerCapabilities() {
		disableDamage = false;
		isFlying = false;
		allowFlying = false;
		isCreativeMode = false;
	}

	public void writeCapabilitiesToNBT(NBTTagCompound par1NBTTagCompound) {
		NBTTagCompound nbttagcompound = new NBTTagCompound();
		nbttagcompound.setBoolean("invulnerable", disableDamage);
		nbttagcompound.setBoolean("flying", isFlying);
		nbttagcompound.setBoolean("mayfly", allowFlying);
		nbttagcompound.setBoolean("instabuild", isCreativeMode);
		par1NBTTagCompound.setTag("abilities", nbttagcompound);
	}

	public void readCapabilitiesFromNBT(NBTTagCompound par1NBTTagCompound) {
		if (par1NBTTagCompound.hasKey("abilities")) {
			NBTTagCompound nbttagcompound = par1NBTTagCompound.getCompoundTag("abilities");
			disableDamage = nbttagcompound.getBoolean("invulnerable");
			isFlying = nbttagcompound.getBoolean("flying");
			allowFlying = nbttagcompound.getBoolean("mayfly");
			isCreativeMode = nbttagcompound.getBoolean("instabuild");
		}
	}
}
