package net.minecraft.src;

import java.io.*;

public class Packet104WindowItems extends Packet {
	public int windowId;
	public ItemStack itemStack[];

	public Packet104WindowItems() {
	}

	public void readPacketData(DataInputStream par1DataInputStream) throws IOException {
		windowId = par1DataInputStream.readByte();
		short word0 = par1DataInputStream.readShort();
		itemStack = new ItemStack[word0];

		for (int i = 0; i < word0; i++) {
			itemStack[i] = readItemStack(par1DataInputStream);
		}
	}

	public void writePacketData(DataOutputStream par1DataOutputStream) throws IOException {
		par1DataOutputStream.writeByte(windowId);
		par1DataOutputStream.writeShort(itemStack.length);

		for (int i = 0; i < itemStack.length; i++) {
			writeItemStack(itemStack[i], par1DataOutputStream);
		}
	}

	public void processPacket(NetHandler par1NetHandler) {
		par1NetHandler.handleWindowItems(this);
	}

	public int getPacketSize() {
		return 3 + itemStack.length * 5;
	}
}
