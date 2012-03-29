package net.minecraft.src;

import java.io.*;

public class Packet100OpenWindow extends Packet {
	public int windowId;
	public int inventoryType;
	public String windowTitle;
	public int slotsCount;

	public Packet100OpenWindow() {
	}

	public void processPacket(NetHandler par1NetHandler) {
		par1NetHandler.handleOpenWindow(this);
	}

	public void readPacketData(DataInputStream par1DataInputStream) throws IOException {
		windowId = par1DataInputStream.readByte() & 0xff;
		inventoryType = par1DataInputStream.readByte() & 0xff;
		windowTitle = readString(par1DataInputStream, 32);
		slotsCount = par1DataInputStream.readByte() & 0xff;
	}

	public void writePacketData(DataOutputStream par1DataOutputStream) throws IOException {
		par1DataOutputStream.writeByte(windowId & 0xff);
		par1DataOutputStream.writeByte(inventoryType & 0xff);
		writeString(windowTitle, par1DataOutputStream);
		par1DataOutputStream.writeByte(slotsCount & 0xff);
	}

	public int getPacketSize() {
		return 3 + windowTitle.length();
	}
}
