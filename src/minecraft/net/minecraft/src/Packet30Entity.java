package net.minecraft.src;

import java.io.*;

public class Packet30Entity extends Packet {
	public int entityId;
	public byte xPosition;
	public byte yPosition;
	public byte zPosition;
	public byte yaw;
	public byte pitch;
	public boolean rotating;

	public Packet30Entity() {
		rotating = false;
	}

	public void readPacketData(DataInputStream par1DataInputStream) throws IOException {
		entityId = par1DataInputStream.readInt();
	}

	public void writePacketData(DataOutputStream par1DataOutputStream) throws IOException {
		par1DataOutputStream.writeInt(entityId);
	}

	public void processPacket(NetHandler par1NetHandler) {
		par1NetHandler.handleEntity(this);
	}

	public int getPacketSize() {
		return 4;
	}
}
