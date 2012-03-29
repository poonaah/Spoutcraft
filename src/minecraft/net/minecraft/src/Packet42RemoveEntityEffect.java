package net.minecraft.src;

import java.io.*;

public class Packet42RemoveEntityEffect extends Packet {
	public int entityId;
	public byte effectId;

	public Packet42RemoveEntityEffect() {
	}

	public void readPacketData(DataInputStream par1DataInputStream) throws IOException {
		entityId = par1DataInputStream.readInt();
		effectId = par1DataInputStream.readByte();
	}

	public void writePacketData(DataOutputStream par1DataOutputStream) throws IOException {
		par1DataOutputStream.writeInt(entityId);
		par1DataOutputStream.writeByte(effectId);
	}

	public void processPacket(NetHandler par1NetHandler) {
		par1NetHandler.handleRemoveEntityEffect(this);
	}

	public int getPacketSize() {
		return 5;
	}
}
