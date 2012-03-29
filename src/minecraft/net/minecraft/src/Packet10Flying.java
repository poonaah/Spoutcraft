package net.minecraft.src;

import java.io.*;

public class Packet10Flying extends Packet {
	public double xPosition;
	public double yPosition;
	public double zPosition;
	public double stance;
	public float yaw;
	public float pitch;
	public boolean onGround;
	public boolean moving;
	public boolean rotating;

	public Packet10Flying() {
	}

	public Packet10Flying(boolean par1) {
		onGround = par1;
	}

	public void processPacket(NetHandler par1NetHandler) {
		par1NetHandler.handleFlying(this);
	}

	public void readPacketData(DataInputStream par1DataInputStream) throws IOException {
		onGround = par1DataInputStream.read() != 0;
	}

	public void writePacketData(DataOutputStream par1DataOutputStream) throws IOException {
		par1DataOutputStream.write(onGround ? 1 : 0);
	}

	public int getPacketSize() {
		return 1;
	}
}
