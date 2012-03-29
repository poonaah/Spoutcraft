package net.minecraft.src;

import java.io.*;

public class Packet31RelEntityMove extends Packet30Entity {
	public Packet31RelEntityMove() {
	}

	public void readPacketData(DataInputStream par1DataInputStream) throws IOException {
		super.readPacketData(par1DataInputStream);
		xPosition = par1DataInputStream.readByte();
		yPosition = par1DataInputStream.readByte();
		zPosition = par1DataInputStream.readByte();
	}

	public void writePacketData(DataOutputStream par1DataOutputStream) throws IOException {
		super.writePacketData(par1DataOutputStream);
		par1DataOutputStream.writeByte(xPosition);
		par1DataOutputStream.writeByte(yPosition);
		par1DataOutputStream.writeByte(zPosition);
	}

	public int getPacketSize() {
		return 7;
	}
}
