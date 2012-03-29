package net.minecraft.src;

import java.io.*;

public class Packet50PreChunk extends Packet {
	public int xPosition;
	public int yPosition;
	public boolean mode;

	public Packet50PreChunk() {
		isChunkDataPacket = false;
	}

	public void readPacketData(DataInputStream par1DataInputStream) throws IOException {
		xPosition = par1DataInputStream.readInt();
		yPosition = par1DataInputStream.readInt();
		mode = par1DataInputStream.read() != 0;
	}

	public void writePacketData(DataOutputStream par1DataOutputStream) throws IOException {
		par1DataOutputStream.writeInt(xPosition);
		par1DataOutputStream.writeInt(yPosition);
		par1DataOutputStream.write(mode ? 1 : 0);
	}

	public void processPacket(NetHandler par1NetHandler) {
		par1NetHandler.handlePreChunk(this);
	}

	public int getPacketSize() {
		return 9;
	}
}
