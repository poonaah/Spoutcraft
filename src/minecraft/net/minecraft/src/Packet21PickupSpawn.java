package net.minecraft.src;

import java.io.*;

public class Packet21PickupSpawn extends Packet {
	public int entityId;
	public int xPosition;
	public int yPosition;
	public int zPosition;
	public byte rotation;
	public byte pitch;
	public byte roll;
	public int itemID;
	public int count;
	public int itemDamage;

	public Packet21PickupSpawn() {
	}

	public Packet21PickupSpawn(EntityItem par1EntityItem) {
		entityId = par1EntityItem.entityId;
		itemID = par1EntityItem.item.itemID;
		count = par1EntityItem.item.stackSize;
		itemDamage = par1EntityItem.item.getItemDamage();
		xPosition = MathHelper.floor_double(par1EntityItem.posX * 32D);
		yPosition = MathHelper.floor_double(par1EntityItem.posY * 32D);
		zPosition = MathHelper.floor_double(par1EntityItem.posZ * 32D);
		rotation = (byte)(int)(par1EntityItem.motionX * 128D);
		pitch = (byte)(int)(par1EntityItem.motionY * 128D);
		roll = (byte)(int)(par1EntityItem.motionZ * 128D);
	}

	public void readPacketData(DataInputStream par1DataInputStream) throws IOException {
		entityId = par1DataInputStream.readInt();
		itemID = par1DataInputStream.readShort();
		count = par1DataInputStream.readByte();
		itemDamage = par1DataInputStream.readShort();
		xPosition = par1DataInputStream.readInt();
		yPosition = par1DataInputStream.readInt();
		zPosition = par1DataInputStream.readInt();
		rotation = par1DataInputStream.readByte();
		pitch = par1DataInputStream.readByte();
		roll = par1DataInputStream.readByte();
	}

	public void writePacketData(DataOutputStream par1DataOutputStream) throws IOException {
		par1DataOutputStream.writeInt(entityId);
		par1DataOutputStream.writeShort(itemID);
		par1DataOutputStream.writeByte(count);
		par1DataOutputStream.writeShort(itemDamage);
		par1DataOutputStream.writeInt(xPosition);
		par1DataOutputStream.writeInt(yPosition);
		par1DataOutputStream.writeInt(zPosition);
		par1DataOutputStream.writeByte(rotation);
		par1DataOutputStream.writeByte(pitch);
		par1DataOutputStream.writeByte(roll);
	}

	public void processPacket(NetHandler par1NetHandler) {
		par1NetHandler.handlePickupSpawn(this);
	}

	public int getPacketSize() {
		return 24;
	}
}
