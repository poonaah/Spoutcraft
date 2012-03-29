package net.minecraft.src;

import java.io.*;

public class Packet6SpawnPosition extends Packet {
	public int xPosition;
	public int yPosition;
	public int zPosition;

	public Packet6SpawnPosition() {
	}

	public void readPacketData(DataInputStream par1DataInputStream) throws IOException {
		xPosition = par1DataInputStream.readInt();
		yPosition = par1DataInputStream.readInt();
		zPosition = par1DataInputStream.readInt();
	}

	public void writePacketData(DataOutputStream par1DataOutputStream) throws IOException {
		par1DataOutputStream.writeInt(xPosition);
		par1DataOutputStream.writeInt(yPosition);
		par1DataOutputStream.writeInt(zPosition);
	}

	public void processPacket(NetHandler par1NetHandler) {
		par1NetHandler.handleSpawnPosition(this);
	}

	public int getPacketSize() {
		return 12;
	}
}
