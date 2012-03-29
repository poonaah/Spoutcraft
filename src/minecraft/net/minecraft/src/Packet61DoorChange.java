package net.minecraft.src;

import java.io.*;

public class Packet61DoorChange extends Packet {
	public int sfxID;
	public int auxData;
	public int posX;
	public int posY;
	public int posZ;

	public Packet61DoorChange() {
	}

	public void readPacketData(DataInputStream par1DataInputStream) throws IOException {
		sfxID = par1DataInputStream.readInt();
		posX = par1DataInputStream.readInt();
		posY = par1DataInputStream.readByte() & 0xff;
		posZ = par1DataInputStream.readInt();
		auxData = par1DataInputStream.readInt();
	}

	public void writePacketData(DataOutputStream par1DataOutputStream) throws IOException {
		par1DataOutputStream.writeInt(sfxID);
		par1DataOutputStream.writeInt(posX);
		par1DataOutputStream.writeByte(posY & 0xff);
		par1DataOutputStream.writeInt(posZ);
		par1DataOutputStream.writeInt(auxData);
	}

	public void processPacket(NetHandler par1NetHandler) {
		par1NetHandler.handleDoorChange(this);
	}

	public int getPacketSize() {
		return 20;
	}
}
