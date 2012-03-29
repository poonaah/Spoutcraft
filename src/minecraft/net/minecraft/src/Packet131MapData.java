package net.minecraft.src;

import java.io.*;

public class Packet131MapData extends Packet {
	public short itemID;
	public short uniqueID;
	public byte itemData[];

	public Packet131MapData() {
		isChunkDataPacket = true;
	}

	public void readPacketData(DataInputStream par1DataInputStream) throws IOException {
		itemID = par1DataInputStream.readShort();
		uniqueID = par1DataInputStream.readShort();
		itemData = new byte[par1DataInputStream.readByte() & 0xff];
		par1DataInputStream.readFully(itemData);
	}

	public void writePacketData(DataOutputStream par1DataOutputStream) throws IOException {
		par1DataOutputStream.writeShort(itemID);
		par1DataOutputStream.writeShort(uniqueID);
		par1DataOutputStream.writeByte(itemData.length);
		par1DataOutputStream.write(itemData);
	}

	public void processPacket(NetHandler par1NetHandler) {
		par1NetHandler.handleMapData(this);
	}

	public int getPacketSize() {
		return 4 + itemData.length;
	}
}
