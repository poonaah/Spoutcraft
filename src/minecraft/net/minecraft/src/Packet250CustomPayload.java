package net.minecraft.src;

import java.io.*;

public class Packet250CustomPayload extends Packet {
	public String channel;
	public int length;
	public byte data[];

	public Packet250CustomPayload() {
	}

	public void readPacketData(DataInputStream par1DataInputStream) throws IOException {
		channel = readString(par1DataInputStream, 16);
		length = par1DataInputStream.readShort();

		if (length > 0 && length < 32767) {
			data = new byte[length];
			par1DataInputStream.readFully(data);
		}
	}

	public void writePacketData(DataOutputStream par1DataOutputStream) throws IOException {
		writeString(channel, par1DataOutputStream);
		par1DataOutputStream.writeShort((short)length);

		if (data != null) {
			par1DataOutputStream.write(data);
		}
	}

	public void processPacket(NetHandler par1NetHandler) {
		par1NetHandler.handleCustomPayload(this);
	}

	public int getPacketSize() {
		return 2 + channel.length() * 2 + 2 + length;
	}
}
