package net.minecraft.src;

import java.io.*;

public class Packet43Experience extends Packet {
	public float experience;
	public int experienceTotal;
	public int experienceLevel;

	public Packet43Experience() {
	}

	public void readPacketData(DataInputStream par1DataInputStream) throws IOException {
		experience = par1DataInputStream.readFloat();
		experienceLevel = par1DataInputStream.readShort();
		experienceTotal = par1DataInputStream.readShort();
	}

	public void writePacketData(DataOutputStream par1DataOutputStream) throws IOException {
		par1DataOutputStream.writeFloat(experience);
		par1DataOutputStream.writeShort(experienceLevel);
		par1DataOutputStream.writeShort(experienceTotal);
	}

	public void processPacket(NetHandler par1NetHandler) {
		par1NetHandler.handleExperience(this);
	}

	public int getPacketSize() {
		return 4;
	}
}
