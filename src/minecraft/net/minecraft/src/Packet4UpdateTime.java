package net.minecraft.src;

import java.io.*;

public class Packet4UpdateTime extends Packet {
	public long time;

	public Packet4UpdateTime() {
	}

	public void readPacketData(DataInputStream par1DataInputStream) throws IOException {
		time = par1DataInputStream.readLong();
	}

	public void writePacketData(DataOutputStream par1DataOutputStream) throws IOException {
		par1DataOutputStream.writeLong(time);
	}

	public void processPacket(NetHandler par1NetHandler) {
		par1NetHandler.handleUpdateTime(this);
	}

	public int getPacketSize() {
		return 8;
	}
}
