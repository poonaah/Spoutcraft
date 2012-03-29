package net.minecraft.src;

import java.io.*;

public class Packet0KeepAlive extends Packet {
	public int randomId;

	public Packet0KeepAlive() {
	}

	public Packet0KeepAlive(int par1) {
		randomId = par1;
	}

	public void processPacket(NetHandler par1NetHandler) {
		par1NetHandler.handleKeepAlive(this);
	}

	public void readPacketData(DataInputStream par1DataInputStream) throws IOException {
		randomId = par1DataInputStream.readInt();
	}

	public void writePacketData(DataOutputStream par1DataOutputStream) throws IOException {
		par1DataOutputStream.writeInt(randomId);
	}

	public int getPacketSize() {
		return 4;
	}
}
