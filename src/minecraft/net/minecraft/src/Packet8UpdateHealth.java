package net.minecraft.src;

import java.io.*;

public class Packet8UpdateHealth extends Packet {
	public int healthMP;
	public int food;
	public float foodSaturation;

	public Packet8UpdateHealth() {
	}

	public void readPacketData(DataInputStream par1DataInputStream) throws IOException {
		healthMP = par1DataInputStream.readShort();
		food = par1DataInputStream.readShort();
		foodSaturation = par1DataInputStream.readFloat();
	}

	public void writePacketData(DataOutputStream par1DataOutputStream) throws IOException {
		par1DataOutputStream.writeShort(healthMP);
		par1DataOutputStream.writeShort(food);
		par1DataOutputStream.writeFloat(foodSaturation);
	}

	public void processPacket(NetHandler par1NetHandler) {
		par1NetHandler.handleUpdateHealth(this);
	}

	public int getPacketSize() {
		return 8;
	}
}
