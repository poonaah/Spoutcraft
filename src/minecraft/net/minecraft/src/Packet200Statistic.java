package net.minecraft.src;

import java.io.*;

public class Packet200Statistic extends Packet {
	public int statisticId;
	public int amount;

	public Packet200Statistic() {
	}

	public void processPacket(NetHandler par1NetHandler) {
		par1NetHandler.handleStatistic(this);
	}

	public void readPacketData(DataInputStream par1DataInputStream) throws IOException {
		statisticId = par1DataInputStream.readInt();
		amount = par1DataInputStream.readByte();
	}

	public void writePacketData(DataOutputStream par1DataOutputStream) throws IOException {
		par1DataOutputStream.writeInt(statisticId);
		par1DataOutputStream.writeByte(amount);
	}

	public int getPacketSize() {
		return 6;
	}
}
