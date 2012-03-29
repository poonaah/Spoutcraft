package net.minecraft.src;

import java.io.*;

public class NBTTagInt extends NBTBase {
	public int data;

	public NBTTagInt(String par1Str) {
		super(par1Str);
	}

	public NBTTagInt(String par1Str, int par2) {
		super(par1Str);
		data = par2;
	}

	void write(DataOutput par1DataOutput) throws IOException {
		par1DataOutput.writeInt(data);
	}

	void load(DataInput par1DataInput) throws IOException {
		data = par1DataInput.readInt();
	}

	public byte getId() {
		return 3;
	}

	public String toString() {
		return (new StringBuilder()).append("").append(data).toString();
	}

	public NBTBase copy() {
		return new NBTTagInt(getName(), data);
	}

	public boolean equals(Object par1Obj) {
		if (super.equals(par1Obj)) {
			NBTTagInt nbttagint = (NBTTagInt)par1Obj;
			return data == nbttagint.data;
		} else {
			return false;
		}
	}

	public int hashCode() {
		return super.hashCode() ^ data;
	}
}
