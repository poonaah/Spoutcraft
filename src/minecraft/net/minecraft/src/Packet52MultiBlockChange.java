package net.minecraft.src;

import java.io.*;

public class Packet52MultiBlockChange extends Packet {
	public int xPosition;
	public int zPosition;
	public byte metadataArray[];
	public int size;
	private static byte field_48168_e[] = new byte[0];

	public Packet52MultiBlockChange() {
		isChunkDataPacket = true;
	}

	public void readPacketData(DataInputStream par1DataInputStream) throws IOException {
		xPosition = par1DataInputStream.readInt();
		zPosition = par1DataInputStream.readInt();
		size = par1DataInputStream.readShort() & 0xffff;
		int i = par1DataInputStream.readInt();

		if (i > 0) {
			metadataArray = new byte[i];
			par1DataInputStream.readFully(metadataArray);
		}
	}

	public void writePacketData(DataOutputStream par1DataOutputStream) throws IOException {
		par1DataOutputStream.writeInt(xPosition);
		par1DataOutputStream.writeInt(zPosition);
		par1DataOutputStream.writeShort((short)size);

		if (metadataArray != null) {
			par1DataOutputStream.writeInt(metadataArray.length);
			par1DataOutputStream.write(metadataArray);
		} else {
			par1DataOutputStream.writeInt(0);
		}
	}

	public void processPacket(NetHandler par1NetHandler) {
		par1NetHandler.handleMultiBlockChange(this);
	}

	public int getPacketSize() {
		return 10 + size * 4;
	}
}
