package net.minecraft.src;

import java.io.*;

public class NBTTagFloat extends NBTBase {
	public float data;

	public NBTTagFloat(String par1Str) {
		super(par1Str);
	}

	public NBTTagFloat(String par1Str, float par2) {
		super(par1Str);
		data = par2;
	}

	void write(DataOutput par1DataOutput) throws IOException {
		par1DataOutput.writeFloat(data);
	}

	void load(DataInput par1DataInput) throws IOException {
		data = par1DataInput.readFloat();
	}

	public byte getId() {
		return 5;
	}

	public String toString() {
		return (new StringBuilder()).append("").append(data).toString();
	}

	public NBTBase copy() {
		return new NBTTagFloat(getName(), data);
	}

	public boolean equals(Object par1Obj) {
		if (super.equals(par1Obj)) {
			NBTTagFloat nbttagfloat = (NBTTagFloat)par1Obj;
			return data == nbttagfloat.data;
		} else {
			return false;
		}
	}

	public int hashCode() {
		return super.hashCode() ^ Float.floatToIntBits(data);
	}
}
