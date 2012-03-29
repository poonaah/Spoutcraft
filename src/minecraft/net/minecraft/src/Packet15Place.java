package net.minecraft.src;

import java.io.*;

public class Packet15Place extends Packet {
	public int xPosition;
	public int yPosition;
	public int zPosition;
	public int direction;
	public ItemStack itemStack;

	public Packet15Place() {
	}

	public Packet15Place(int par1, int par2, int par3, int par4, ItemStack par5ItemStack) {
		xPosition = par1;
		yPosition = par2;
		zPosition = par3;
		direction = par4;
		itemStack = par5ItemStack;
	}

	public void readPacketData(DataInputStream par1DataInputStream) throws IOException {
		xPosition = par1DataInputStream.readInt();
		yPosition = par1DataInputStream.read();
		zPosition = par1DataInputStream.readInt();
		direction = par1DataInputStream.read();
		itemStack = readItemStack(par1DataInputStream);
	}

	public void writePacketData(DataOutputStream par1DataOutputStream) throws IOException {
		par1DataOutputStream.writeInt(xPosition);
		par1DataOutputStream.write(yPosition);
		par1DataOutputStream.writeInt(zPosition);
		par1DataOutputStream.write(direction);
		writeItemStack(itemStack, par1DataOutputStream);
	}

	public void processPacket(NetHandler par1NetHandler) {
		par1NetHandler.handlePlace(this);
	}

	public int getPacketSize() {
		return 15;
	}
}
