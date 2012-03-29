package net.minecraft.src;

import java.io.*;

public class Packet3Chat extends Packet {
	public String message;

	public Packet3Chat() {
	}

	public Packet3Chat(String par1Str) {
		if (par1Str.length() > 119) {
			par1Str = par1Str.substring(0, 119);
		}

		message = par1Str;
	}

	public void readPacketData(DataInputStream par1DataInputStream) throws IOException {
		message = readString(par1DataInputStream, 119);
	}

	public void writePacketData(DataOutputStream par1DataOutputStream) throws IOException {
		writeString(message, par1DataOutputStream);
	}

	public void processPacket(NetHandler par1NetHandler) {
		par1NetHandler.handleChat(this);
	}

	public int getPacketSize() {
		return 2 + message.length() * 2;
	}
}
