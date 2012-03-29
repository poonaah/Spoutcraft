package net.minecraft.src;

import java.io.*;

public class Packet7UseEntity extends Packet {
	public int playerEntityId;
	public int targetEntity;
	public int isLeftClick;

	public Packet7UseEntity() {
	}

	public Packet7UseEntity(int par1, int par2, int par3) {
		playerEntityId = par1;
		targetEntity = par2;
		isLeftClick = par3;
	}

	public void readPacketData(DataInputStream par1DataInputStream) throws IOException {
		playerEntityId = par1DataInputStream.readInt();
		targetEntity = par1DataInputStream.readInt();
		isLeftClick = par1DataInputStream.readByte();
	}

	public void writePacketData(DataOutputStream par1DataOutputStream) throws IOException {
		par1DataOutputStream.writeInt(playerEntityId);
		par1DataOutputStream.writeInt(targetEntity);
		par1DataOutputStream.writeByte(isLeftClick);
	}

	public void processPacket(NetHandler par1NetHandler) {
		par1NetHandler.handleUseEntity(this);
	}

	public int getPacketSize() {
		return 9;
	}
}
