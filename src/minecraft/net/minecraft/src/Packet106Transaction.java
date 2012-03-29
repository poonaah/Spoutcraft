package net.minecraft.src;

import java.io.*;

public class Packet106Transaction extends Packet {
	public int windowId;
	public short shortWindowId;
	public boolean accepted;

	public Packet106Transaction() {
	}

	public Packet106Transaction(int par1, short par2, boolean par3) {
		windowId = par1;
		shortWindowId = par2;
		accepted = par3;
	}

	public void processPacket(NetHandler par1NetHandler) {
		par1NetHandler.handleTransaction(this);
	}

	public void readPacketData(DataInputStream par1DataInputStream) throws IOException {
		windowId = par1DataInputStream.readByte();
		shortWindowId = par1DataInputStream.readShort();
		accepted = par1DataInputStream.readByte() != 0;
	}

	public void writePacketData(DataOutputStream par1DataOutputStream) throws IOException {
		par1DataOutputStream.writeByte(windowId);
		par1DataOutputStream.writeShort(shortWindowId);
		par1DataOutputStream.writeByte(accepted ? 1 : 0);
	}

	public int getPacketSize() {
		return 4;
	}
}
