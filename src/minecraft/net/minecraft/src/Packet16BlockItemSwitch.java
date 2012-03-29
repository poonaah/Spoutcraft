package net.minecraft.src;

import java.io.*;

public class Packet16BlockItemSwitch extends Packet {
	public int id;

	public Packet16BlockItemSwitch() {
	}

	public Packet16BlockItemSwitch(int par1) {
		id = par1;
	}

	public void readPacketData(DataInputStream par1DataInputStream) throws IOException {
		id = par1DataInputStream.readShort();
	}

	public void writePacketData(DataOutputStream par1DataOutputStream) throws IOException {
		par1DataOutputStream.writeShort(id);
	}

	public void processPacket(NetHandler par1NetHandler) {
		par1NetHandler.handleBlockItemSwitch(this);
	}

	public int getPacketSize() {
		return 2;
	}
}
