package net.minecraft.src;

import java.io.*;

public class Packet34EntityTeleport extends Packet {
	public int entityId;
	public int xPosition;
	public int yPosition;
	public int zPosition;
	public byte yaw;
	public byte pitch;

	public Packet34EntityTeleport() {
	}

	public Packet34EntityTeleport(Entity par1Entity) {
		entityId = par1Entity.entityId;
		xPosition = MathHelper.floor_double(par1Entity.posX * 32D);
		yPosition = MathHelper.floor_double(par1Entity.posY * 32D);
		zPosition = MathHelper.floor_double(par1Entity.posZ * 32D);
		yaw = (byte)(int)((par1Entity.rotationYaw * 256F) / 360F);
		pitch = (byte)(int)((par1Entity.rotationPitch * 256F) / 360F);
	}

	public void readPacketData(DataInputStream par1DataInputStream) throws IOException {
		entityId = par1DataInputStream.readInt();
		xPosition = par1DataInputStream.readInt();
		yPosition = par1DataInputStream.readInt();
		zPosition = par1DataInputStream.readInt();
		yaw = (byte)par1DataInputStream.read();
		pitch = (byte)par1DataInputStream.read();
	}

	public void writePacketData(DataOutputStream par1DataOutputStream) throws IOException {
		par1DataOutputStream.writeInt(entityId);
		par1DataOutputStream.writeInt(xPosition);
		par1DataOutputStream.writeInt(yPosition);
		par1DataOutputStream.writeInt(zPosition);
		par1DataOutputStream.write(yaw);
		par1DataOutputStream.write(pitch);
	}

	public void processPacket(NetHandler par1NetHandler) {
		par1NetHandler.handleEntityTeleport(this);
	}

	public int getPacketSize() {
		return 34;
	}
}
