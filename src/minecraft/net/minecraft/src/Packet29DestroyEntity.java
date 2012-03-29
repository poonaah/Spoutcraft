package net.minecraft.src;

import java.io.*;

public class Packet29DestroyEntity extends Packet {
	public int entityId;

	public Packet29DestroyEntity() {
	}

	public void readPacketData(DataInputStream par1DataInputStream) throws IOException {
		entityId = par1DataInputStream.readInt();
	}

	public void writePacketData(DataOutputStream par1DataOutputStream) throws IOException {
		par1DataOutputStream.writeInt(entityId);
	}

	public void processPacket(NetHandler par1NetHandler) {
		par1NetHandler.handleDestroyEntity(this);
	}

	public int getPacketSize() {
		return 4;
	}
}
