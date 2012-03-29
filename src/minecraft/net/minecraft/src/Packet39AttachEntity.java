package net.minecraft.src;

import java.io.*;

public class Packet39AttachEntity extends Packet {
	public int entityId;
	public int vehicleEntityId;

	public Packet39AttachEntity() {
	}

	public int getPacketSize() {
		return 8;
	}

	public void readPacketData(DataInputStream par1DataInputStream) throws IOException {
		entityId = par1DataInputStream.readInt();
		vehicleEntityId = par1DataInputStream.readInt();
	}

	public void writePacketData(DataOutputStream par1DataOutputStream) throws IOException {
		par1DataOutputStream.writeInt(entityId);
		par1DataOutputStream.writeInt(vehicleEntityId);
	}

	public void processPacket(NetHandler par1NetHandler) {
		par1NetHandler.handleAttachEntity(this);
	}
}
