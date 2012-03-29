package net.minecraft.src;

import java.io.*;

public class Packet255KickDisconnect extends Packet {
	public String reason;

	public Packet255KickDisconnect() {
	}

	public Packet255KickDisconnect(String par1Str) {
		reason = par1Str;
	}

	public void readPacketData(DataInputStream par1DataInputStream) throws IOException {
		reason = readString(par1DataInputStream, 256);
	}

	public void writePacketData(DataOutputStream par1DataOutputStream) throws IOException {
		writeString(reason, par1DataOutputStream);
	}

	public void processPacket(NetHandler par1NetHandler) {
		par1NetHandler.handleKickDisconnect(this);
	}

	public int getPacketSize() {
		return reason.length();
	}
}
