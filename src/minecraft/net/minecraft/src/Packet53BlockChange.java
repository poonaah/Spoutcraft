package net.minecraft.src;

import java.io.*;

public class Packet53BlockChange extends Packet {
	public int xPosition;
	public int yPosition;
	public int zPosition;
	public int type;
	public int metadata;

	public Packet53BlockChange() {
		isChunkDataPacket = true;
	}

	public void readPacketData(DataInputStream par1DataInputStream) throws IOException {
		xPosition = par1DataInputStream.readInt();
		yPosition = par1DataInputStream.read();
		zPosition = par1DataInputStream.readInt();
		type = par1DataInputStream.read();
		metadata = par1DataInputStream.read();
	}

	public void writePacketData(DataOutputStream par1DataOutputStream) throws IOException {
		par1DataOutputStream.writeInt(xPosition);
		par1DataOutputStream.write(yPosition);
		par1DataOutputStream.writeInt(zPosition);
		par1DataOutputStream.write(type);
		par1DataOutputStream.write(metadata);
	}

	public void processPacket(NetHandler par1NetHandler) {
		par1NetHandler.handleBlockChange(this);
	}

	public int getPacketSize() {
		return 11;
	}
}
