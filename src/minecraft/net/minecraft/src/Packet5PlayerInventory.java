package net.minecraft.src;

import java.io.*;

public class Packet5PlayerInventory extends Packet {
	public int entityID;
	public int slot;
	public int itemID;
	public int itemDamage;

	public Packet5PlayerInventory() {
	}

	public void readPacketData(DataInputStream par1DataInputStream) throws IOException {
		entityID = par1DataInputStream.readInt();
		slot = par1DataInputStream.readShort();
		itemID = par1DataInputStream.readShort();
		itemDamage = par1DataInputStream.readShort();
	}

	public void writePacketData(DataOutputStream par1DataOutputStream) throws IOException {
		par1DataOutputStream.writeInt(entityID);
		par1DataOutputStream.writeShort(slot);
		par1DataOutputStream.writeShort(itemID);
		par1DataOutputStream.writeShort(itemDamage);
	}

	public void processPacket(NetHandler par1NetHandler) {
		par1NetHandler.handlePlayerInventory(this);
	}

	public int getPacketSize() {
		return 8;
	}
}
