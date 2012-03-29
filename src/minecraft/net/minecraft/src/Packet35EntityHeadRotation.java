package net.minecraft.src;

import java.io.*;

public class Packet35EntityHeadRotation extends Packet {
	public int entityId;
	public byte headRotationYaw;

	public Packet35EntityHeadRotation() {
	}

	public void readPacketData(DataInputStream par1DataInputStream) throws IOException {
		entityId = par1DataInputStream.readInt();
		headRotationYaw = par1DataInputStream.readByte();
	}

	public void writePacketData(DataOutputStream par1DataOutputStream) throws IOException {
		par1DataOutputStream.writeInt(entityId);
		par1DataOutputStream.writeByte(headRotationYaw);
	}

	public void processPacket(NetHandler par1NetHandler) {
		par1NetHandler.handleEntityHeadRotation(this);
	}

	public int getPacketSize() {
		return 5;
	}
}
