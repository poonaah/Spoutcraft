package net.minecraft.src;

import java.io.*;

public class Packet19EntityAction extends Packet {
	public int entityId;
	public int state;

	public Packet19EntityAction() {
	}

	public Packet19EntityAction(Entity par1Entity, int par2) {
		entityId = par1Entity.entityId;
		state = par2;
	}

	public void readPacketData(DataInputStream par1DataInputStream) throws IOException {
		entityId = par1DataInputStream.readInt();
		state = par1DataInputStream.readByte();
	}

	public void writePacketData(DataOutputStream par1DataOutputStream) throws IOException {
		par1DataOutputStream.writeInt(entityId);
		par1DataOutputStream.writeByte(state);
	}

	public void processPacket(NetHandler par1NetHandler) {
		par1NetHandler.handleEntityAction(this);
	}

	public int getPacketSize() {
		return 5;
	}
}
