package net.minecraft.src;

import java.io.*;

public class Packet54PlayNoteBlock extends Packet {
	public int xLocation;
	public int yLocation;
	public int zLocation;
	public int instrumentType;
	public int pitch;

	public Packet54PlayNoteBlock() {
	}

	public void readPacketData(DataInputStream par1DataInputStream) throws IOException {
		xLocation = par1DataInputStream.readInt();
		yLocation = par1DataInputStream.readShort();
		zLocation = par1DataInputStream.readInt();
		instrumentType = par1DataInputStream.read();
		pitch = par1DataInputStream.read();
	}

	public void writePacketData(DataOutputStream par1DataOutputStream) throws IOException {
		par1DataOutputStream.writeInt(xLocation);
		par1DataOutputStream.writeShort(yLocation);
		par1DataOutputStream.writeInt(zLocation);
		par1DataOutputStream.write(instrumentType);
		par1DataOutputStream.write(pitch);
	}

	public void processPacket(NetHandler par1NetHandler) {
		par1NetHandler.handlePlayNoteBlock(this);
	}

	public int getPacketSize() {
		return 12;
	}
}
