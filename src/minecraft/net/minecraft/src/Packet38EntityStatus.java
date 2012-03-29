package net.minecraft.src;

import java.io.*;

public class Packet38EntityStatus extends Packet {
	public int entityId;
	public byte entityStatus;

	public Packet38EntityStatus() {
	}

	public void readPacketData(DataInputStream par1DataInputStream) throws IOException {
		entityId = par1DataInputStream.readInt();
		entityStatus = par1DataInputStream.readByte();
	}

	public void writePacketData(DataOutputStream par1DataOutputStream) throws IOException {
		par1DataOutputStream.writeInt(entityId);
		par1DataOutputStream.writeByte(entityStatus);
	}

	public void processPacket(NetHandler par1NetHandler) {
		par1NetHandler.handleEntityStatus(this);
	}

	public int getPacketSize() {
		return 5;
	}
}
