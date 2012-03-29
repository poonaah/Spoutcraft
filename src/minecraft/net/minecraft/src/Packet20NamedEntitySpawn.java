package net.minecraft.src;

import java.io.*;

public class Packet20NamedEntitySpawn extends Packet {
	public int entityId;
	public String name;
	public int xPosition;
	public int yPosition;
	public int zPosition;
	public byte rotation;
	public byte pitch;
	public int currentItem;

	public Packet20NamedEntitySpawn() {
	}

	public Packet20NamedEntitySpawn(EntityPlayer par1EntityPlayer) {
		entityId = par1EntityPlayer.entityId;
		name = par1EntityPlayer.username;
		xPosition = MathHelper.floor_double(par1EntityPlayer.posX * 32D);
		yPosition = MathHelper.floor_double(par1EntityPlayer.posY * 32D);
		zPosition = MathHelper.floor_double(par1EntityPlayer.posZ * 32D);
		rotation = (byte)(int)((par1EntityPlayer.rotationYaw * 256F) / 360F);
		pitch = (byte)(int)((par1EntityPlayer.rotationPitch * 256F) / 360F);
		ItemStack itemstack = par1EntityPlayer.inventory.getCurrentItem();
		currentItem = itemstack != null ? itemstack.itemID : 0;
	}

	public void readPacketData(DataInputStream par1DataInputStream) throws IOException {
		entityId = par1DataInputStream.readInt();
		name = readString(par1DataInputStream, 16);
		xPosition = par1DataInputStream.readInt();
		yPosition = par1DataInputStream.readInt();
		zPosition = par1DataInputStream.readInt();
		rotation = par1DataInputStream.readByte();
		pitch = par1DataInputStream.readByte();
		currentItem = par1DataInputStream.readShort();
	}

	public void writePacketData(DataOutputStream par1DataOutputStream) throws IOException {
		par1DataOutputStream.writeInt(entityId);
		writeString(name, par1DataOutputStream);
		par1DataOutputStream.writeInt(xPosition);
		par1DataOutputStream.writeInt(yPosition);
		par1DataOutputStream.writeInt(zPosition);
		par1DataOutputStream.writeByte(rotation);
		par1DataOutputStream.writeByte(pitch);
		par1DataOutputStream.writeShort(currentItem);
	}

	public void processPacket(NetHandler par1NetHandler) {
		par1NetHandler.handleNamedEntitySpawn(this);
	}

	public int getPacketSize() {
		return 28;
	}
}
