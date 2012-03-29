package net.minecraft.src;

import java.io.*;

public class NBTTagString extends NBTBase {
	public String data;

	public NBTTagString(String par1Str) {
		super(par1Str);
	}

	public NBTTagString(String par1Str, String par2Str) {
		super(par1Str);
		data = par2Str;

		if (par2Str == null) {
			throw new IllegalArgumentException("Empty string not allowed");
		} else {
			return;
		}
	}

	void write(DataOutput par1DataOutput) throws IOException {
		par1DataOutput.writeUTF(data);
	}

	void load(DataInput par1DataInput) throws IOException {
		data = par1DataInput.readUTF();
	}

	public byte getId() {
		return 8;
	}

	public String toString() {
		return (new StringBuilder()).append("").append(data).toString();
	}

	public NBTBase copy() {
		return new NBTTagString(getName(), data);
	}

	public boolean equals(Object par1Obj) {
		if (super.equals(par1Obj)) {
			NBTTagString nbttagstring = (NBTTagString)par1Obj;
			return data == null && nbttagstring.data == null || data != null && data.equals(nbttagstring.data);
		} else {
			return false;
		}
	}

	public int hashCode() {
		return super.hashCode() ^ data.hashCode();
	}
}
