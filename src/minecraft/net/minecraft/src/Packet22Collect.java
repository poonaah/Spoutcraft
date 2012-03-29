package net.minecraft.src;

import java.io.*;

public class Packet22Collect extends Packet {
	public int collectedEntityId;
	public int collectorEntityId;

	public Packet22Collect() {
	}

	public void readPacketData(DataInputStream par1DataInputStream) throws IOException {
		collectedEntityId = par1DataInputStream.readInt();
		collectorEntityId = par1DataInputStream.readInt();
	}

	public void writePacketData(DataOutputStream par1DataOutputStream) throws IOException {
		par1DataOutputStream.writeInt(collectedEntityId);
		par1DataOutputStream.writeInt(collectorEntityId);
	}

	public void processPacket(NetHandler par1NetHandler) {
		par1NetHandler.handleCollect(this);
	}

	public int getPacketSize() {
		return 8;
	}
}
