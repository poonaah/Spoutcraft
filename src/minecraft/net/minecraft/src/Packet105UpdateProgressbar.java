package net.minecraft.src;

import java.io.*;

public class Packet105UpdateProgressbar extends Packet {
	public int windowId;
	public int progressBar;
	public int progressBarValue;

	public Packet105UpdateProgressbar() {
	}

	public void processPacket(NetHandler par1NetHandler) {
		par1NetHandler.handleUpdateProgressbar(this);
	}

	public void readPacketData(DataInputStream par1DataInputStream) throws IOException {
		windowId = par1DataInputStream.readByte();
		progressBar = par1DataInputStream.readShort();
		progressBarValue = par1DataInputStream.readShort();
	}

	public void writePacketData(DataOutputStream par1DataOutputStream) throws IOException {
		par1DataOutputStream.writeByte(windowId);
		par1DataOutputStream.writeShort(progressBar);
		par1DataOutputStream.writeShort(progressBarValue);
	}

	public int getPacketSize() {
		return 5;
	}
}
