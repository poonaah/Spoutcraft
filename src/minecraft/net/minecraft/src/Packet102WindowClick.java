package net.minecraft.src;

import java.io.*;

public class Packet102WindowClick extends Packet {
	public int window_Id;
	public int inventorySlot;
	public int mouseClick;
	public short action;
	public ItemStack itemStack;
	public boolean holdingShift;

	public Packet102WindowClick() {
	}

	public Packet102WindowClick(int par1, int par2, int par3, boolean par4, ItemStack par5ItemStack, short par6) {
		window_Id = par1;
		inventorySlot = par2;
		mouseClick = par3;
		itemStack = par5ItemStack;
		action = par6;
		holdingShift = par4;
	}

	public void processPacket(NetHandler par1NetHandler) {
		par1NetHandler.handleWindowClick(this);
	}

	public void readPacketData(DataInputStream par1DataInputStream) throws IOException {
		window_Id = par1DataInputStream.readByte();
		inventorySlot = par1DataInputStream.readShort();
		mouseClick = par1DataInputStream.readByte();
		action = par1DataInputStream.readShort();
		holdingShift = par1DataInputStream.readBoolean();
		itemStack = readItemStack(par1DataInputStream);
	}

	public void writePacketData(DataOutputStream par1DataOutputStream) throws IOException {
		par1DataOutputStream.writeByte(window_Id);
		par1DataOutputStream.writeShort(inventorySlot);
		par1DataOutputStream.writeByte(mouseClick);
		par1DataOutputStream.writeShort(action);
		par1DataOutputStream.writeBoolean(holdingShift);
		writeItemStack(itemStack, par1DataOutputStream);
	}

	public int getPacketSize() {
		return 11;
	}
}
