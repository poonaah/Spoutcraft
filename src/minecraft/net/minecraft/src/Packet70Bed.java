package net.minecraft.src;

import java.io.*;

public class Packet70Bed extends Packet {
	public static final String bedChat[] = {
		"tile.bed.notValid", null, null, "gameMode.changed"
	};
	public int bedState;
	public int gameMode;

	public Packet70Bed() {
	}

	public void readPacketData(DataInputStream par1DataInputStream) throws IOException {
		bedState = par1DataInputStream.readByte();
		gameMode = par1DataInputStream.readByte();
	}

	public void writePacketData(DataOutputStream par1DataOutputStream) throws IOException {
		par1DataOutputStream.writeByte(bedState);
		par1DataOutputStream.writeByte(gameMode);
	}

	public void processPacket(NetHandler par1NetHandler) {
		par1NetHandler.handleBed(this);
	}

	public int getPacketSize() {
		return 2;
	}
}
