package net.minecraft.src;

import java.io.*;
import java.util.*;

public class NBTTagCompound extends NBTBase {
	private Map tagMap;

	public NBTTagCompound() {
		super("");
		tagMap = new HashMap();
	}

	public NBTTagCompound(String par1Str) {
		super(par1Str);
		tagMap = new HashMap();
	}

	void write(DataOutput par1DataOutput) throws IOException {
		NBTBase nbtbase;

		for (Iterator iterator = tagMap.values().iterator(); iterator.hasNext(); NBTBase.writeNamedTag(nbtbase, par1DataOutput)) {
			nbtbase = (NBTBase)iterator.next();
		}

		par1DataOutput.writeByte(0);
	}

	void load(DataInput par1DataInput) throws IOException {
		tagMap.clear();
		NBTBase nbtbase;

		for (; (nbtbase = NBTBase.readNamedTag(par1DataInput)).getId() != 0; tagMap.put(nbtbase.getName(), nbtbase)) { }
	}

	public Collection getTags() {
		return tagMap.values();
	}

	public byte getId() {
		return 10;
	}

	public void setTag(String par1Str, NBTBase par2NBTBase) {
		tagMap.put(par1Str, par2NBTBase.setName(par1Str));
	}

	public void setByte(String par1Str, byte par2) {
		tagMap.put(par1Str, new NBTTagByte(par1Str, par2));
	}

	public void setShort(String par1Str, short par2) {
		tagMap.put(par1Str, new NBTTagShort(par1Str, par2));
	}

	public void setInteger(String par1Str, int par2) {
		tagMap.put(par1Str, new NBTTagInt(par1Str, par2));
	}

	public void setLong(String par1Str, long par2) {
		tagMap.put(par1Str, new NBTTagLong(par1Str, par2));
	}

	public void setFloat(String par1Str, float par2) {
		tagMap.put(par1Str, new NBTTagFloat(par1Str, par2));
	}

	public void setDouble(String par1Str, double par2) {
		tagMap.put(par1Str, new NBTTagDouble(par1Str, par2));
	}

	public void setString(String par1Str, String par2Str) {
		tagMap.put(par1Str, new NBTTagString(par1Str, par2Str));
	}

	public void setByteArray(String par1Str, byte par2ArrayOfByte[]) {
		tagMap.put(par1Str, new NBTTagByteArray(par1Str, par2ArrayOfByte));
	}

	public void func_48183_a(String par1Str, int par2ArrayOfInteger[]) {
		tagMap.put(par1Str, new NBTTagIntArray(par1Str, par2ArrayOfInteger));
	}

	public void setCompoundTag(String par1Str, NBTTagCompound par2NBTTagCompound) {
		tagMap.put(par1Str, par2NBTTagCompound.setName(par1Str));
	}

	public void setBoolean(String par1Str, boolean par2) {
		setByte(par1Str, ((byte)(par2 ? 1 : 0)));
	}

	public NBTBase getTag(String par1Str) {
		return (NBTBase)tagMap.get(par1Str);
	}

	public boolean hasKey(String par1Str) {
		return tagMap.containsKey(par1Str);
	}

	public byte getByte(String par1Str) {
		if (!tagMap.containsKey(par1Str)) {
			return 0;
		} else {
			return ((NBTTagByte)tagMap.get(par1Str)).data;
		}
	}

	public short getShort(String par1Str) {
		if (!tagMap.containsKey(par1Str)) {
			return 0;
		} else {
			return ((NBTTagShort)tagMap.get(par1Str)).data;
		}
	}

	public int getInteger(String par1Str) {
		if (!tagMap.containsKey(par1Str)) {
			return 0;
		} else {
			return ((NBTTagInt)tagMap.get(par1Str)).data;
		}
	}

	public long getLong(String par1Str) {
		if (!tagMap.containsKey(par1Str)) {
			return 0L;
		} else {
			return ((NBTTagLong)tagMap.get(par1Str)).data;
		}
	}

	public float getFloat(String par1Str) {
		if (!tagMap.containsKey(par1Str)) {
			return 0.0F;
		} else {
			return ((NBTTagFloat)tagMap.get(par1Str)).data;
		}
	}

	public double getDouble(String par1Str) {
		if (!tagMap.containsKey(par1Str)) {
			return 0.0D;
		} else {
			return ((NBTTagDouble)tagMap.get(par1Str)).data;
		}
	}

	public String getString(String par1Str) {
		if (!tagMap.containsKey(par1Str)) {
			return "";
		} else {
			return ((NBTTagString)tagMap.get(par1Str)).data;
		}
	}

	public byte[] getByteArray(String par1Str) {
		if (!tagMap.containsKey(par1Str)) {
			return new byte[0];
		} else {
			return ((NBTTagByteArray)tagMap.get(par1Str)).byteArray;
		}
	}

	public int[] func_48182_l(String par1Str) {
		if (!tagMap.containsKey(par1Str)) {
			return new int[0];
		} else {
			return ((NBTTagIntArray)tagMap.get(par1Str)).field_48181_a;
		}
	}

	public NBTTagCompound getCompoundTag(String par1Str) {
		if (!tagMap.containsKey(par1Str)) {
			return new NBTTagCompound(par1Str);
		} else {
			return (NBTTagCompound)tagMap.get(par1Str);
		}
	}

	public NBTTagList getTagList(String par1Str) {
		if (!tagMap.containsKey(par1Str)) {
			return new NBTTagList(par1Str);
		} else {
			return (NBTTagList)tagMap.get(par1Str);
		}
	}

	public boolean getBoolean(String par1Str) {
		return getByte(par1Str) != 0;
	}

	public String toString() {
		return (new StringBuilder()).append("").append(tagMap.size()).append(" entries").toString();
	}

	public NBTBase copy() {
		NBTTagCompound nbttagcompound = new NBTTagCompound(getName());
		String s;

		for (Iterator iterator = tagMap.keySet().iterator(); iterator.hasNext(); nbttagcompound.setTag(s, ((NBTBase)tagMap.get(s)).copy())) {
			s = (String)iterator.next();
		}

		return nbttagcompound;
	}

	public boolean equals(Object par1Obj) {
		if (super.equals(par1Obj)) {
			NBTTagCompound nbttagcompound = (NBTTagCompound)par1Obj;
			return tagMap.entrySet().equals(nbttagcompound.tagMap.entrySet());
		} else {
			return false;
		}
	}

	public int hashCode() {
		return super.hashCode() ^ tagMap.hashCode();
	}
}
