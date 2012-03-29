package net.minecraft.src;

import java.io.*;

public class NBTTagByte extends NBTBase {
	public byte data;

	public NBTTagByte(String par1Str) {
		super(par1Str);
	}

	public NBTTagByte(String par1Str, byte par2) {
		super(par1Str);
		data = par2;
	}

	void write(DataOutput par1DataOutput) throws IOException {
		par1DataOutput.writeByte(data);
	}

	void load(DataInput par1DataInput) throws IOException {
		data = par1DataInput.readByte();
	}

	public byte getId() {
		return 1;
	}

	public String toString() {
		return (new StringBuilder()).append("").append(data).toString();
	}

	public NBTBase copy() {
		return new NBTTagByte(getName(), data);
	}

	public boolean equals(Object par1Obj) {
		if (super.equals(par1Obj)) {
			NBTTagByte nbttagbyte = (NBTTagByte)par1Obj;
			return data == nbttagbyte.data;
		} else {
			return false;
		}
	}

	public int hashCode() {
		return super.hashCode() ^ data;
	}
}
