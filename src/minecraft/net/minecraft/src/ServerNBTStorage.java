package net.minecraft.src;

public class ServerNBTStorage {
	public String name;
	public String host;
	public String playerCount;
	public String motd;
	public long lag;
	public boolean polled;

	public ServerNBTStorage(String par1Str, String par2Str) {
		polled = false;
		name = par1Str;
		host = par2Str;
	}

	public NBTTagCompound getCompoundTag() {
		NBTTagCompound nbttagcompound = new NBTTagCompound();
		nbttagcompound.setString("name", name);
		nbttagcompound.setString("ip", host);
		return nbttagcompound;
	}

	public static ServerNBTStorage createServerNBTStorage(NBTTagCompound par0NBTTagCompound) {
		return new ServerNBTStorage(par0NBTTagCompound.getString("name"), par0NBTTagCompound.getString("ip"));
	}
}
