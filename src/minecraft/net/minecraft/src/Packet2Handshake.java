package net.minecraft.src;

import java.io.*;

public class Packet2Handshake extends Packet {
	public String username;

	public Packet2Handshake() {
	}

	public Packet2Handshake(String par1Str) {
		username = par1Str;
	}

	public Packet2Handshake(String par1Str, String par2Str, int par3) {
		username = (new StringBuilder()).append(par1Str).append(";").append(par2Str).append(":").append(par3).toString();
	}

	public void readPacketData(DataInputStream par1DataInputStream) throws IOException {
		username = readString(par1DataInputStream, 64);
	}

	public void writePacketData(DataOutputStream par1DataOutputStream) throws IOException {
		writeString(username, par1DataOutputStream);
	}

	public void processPacket(NetHandler par1NetHandler) {
		par1NetHandler.handleHandshake(this);
	}

	public int getPacketSize() {
		return 4 + username.length() + 4;
	}
}
