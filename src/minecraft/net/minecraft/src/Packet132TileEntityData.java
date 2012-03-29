package net.minecraft.src;

import java.io.*;

public class Packet132TileEntityData extends Packet {
	public int xPosition;
	public int yPosition;
	public int zPosition;
	public int actionType;
	public int customParam1;
	public int customParam2;
	public int customParam3;

	public Packet132TileEntityData() {
		isChunkDataPacket = true;
	}

	public void readPacketData(DataInputStream par1DataInputStream) throws IOException {
		xPosition = par1DataInputStream.readInt();
		yPosition = par1DataInputStream.readShort();
		zPosition = par1DataInputStream.readInt();
		actionType = par1DataInputStream.readByte();
		customParam1 = par1DataInputStream.readInt();
		customParam2 = par1DataInputStream.readInt();
		customParam3 = par1DataInputStream.readInt();
	}

	public void writePacketData(DataOutputStream par1DataOutputStream) throws IOException {
		par1DataOutputStream.writeInt(xPosition);
		par1DataOutputStream.writeShort(yPosition);
		par1DataOutputStream.writeInt(zPosition);
		par1DataOutputStream.writeByte((byte)actionType);
		par1DataOutputStream.writeInt(customParam1);
		par1DataOutputStream.writeInt(customParam2);
		par1DataOutputStream.writeInt(customParam3);
	}

	public void processPacket(NetHandler par1NetHandler) {
		par1NetHandler.handleTileEntityData(this);
	}

	public int getPacketSize() {
		return 25;
	}
}
