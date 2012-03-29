package net.minecraft.src;

import java.io.*;

public class Packet32EntityLook extends Packet30Entity {
	public Packet32EntityLook() {
		rotating = true;
	}

	public void readPacketData(DataInputStream par1DataInputStream) throws IOException {
		super.readPacketData(par1DataInputStream);
		yaw = par1DataInputStream.readByte();
		pitch = par1DataInputStream.readByte();
	}

	public void writePacketData(DataOutputStream par1DataOutputStream) throws IOException {
		super.writePacketData(par1DataOutputStream);
		par1DataOutputStream.writeByte(yaw);
		par1DataOutputStream.writeByte(pitch);
	}

	public int getPacketSize() {
		return 6;
	}
}
