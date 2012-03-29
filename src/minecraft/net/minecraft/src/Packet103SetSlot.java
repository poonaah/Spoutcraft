package net.minecraft.src;

import java.io.*;

public class Packet103SetSlot extends Packet {
	public int windowId;
	public int itemSlot;
	public ItemStack myItemStack;

	public Packet103SetSlot() {
	}

	public void processPacket(NetHandler par1NetHandler) {
		par1NetHandler.handleSetSlot(this);
	}

	public void readPacketData(DataInputStream par1DataInputStream) throws IOException {
		windowId = par1DataInputStream.readByte();
		itemSlot = par1DataInputStream.readShort();
		myItemStack = readItemStack(par1DataInputStream);
	}

	public void writePacketData(DataOutputStream par1DataOutputStream) throws IOException {
		par1DataOutputStream.writeByte(windowId);
		par1DataOutputStream.writeShort(itemSlot);
		writeItemStack(myItemStack, par1DataOutputStream);
	}

	public int getPacketSize() {
		return 8;
	}
}
