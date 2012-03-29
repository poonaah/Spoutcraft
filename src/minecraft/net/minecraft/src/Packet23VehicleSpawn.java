package net.minecraft.src;

import java.io.*;

public class Packet23VehicleSpawn extends Packet {
	public int entityId;
	public int xPosition;
	public int yPosition;
	public int zPosition;
	public int speedX;
	public int speedY;
	public int speedZ;
	public int type;
	public int throwerEntityId;

	public Packet23VehicleSpawn() {
	}

	public void readPacketData(DataInputStream par1DataInputStream) throws IOException {
		entityId = par1DataInputStream.readInt();
		type = par1DataInputStream.readByte();
		xPosition = par1DataInputStream.readInt();
		yPosition = par1DataInputStream.readInt();
		zPosition = par1DataInputStream.readInt();
		throwerEntityId = par1DataInputStream.readInt();

		if (throwerEntityId > 0) {
			speedX = par1DataInputStream.readShort();
			speedY = par1DataInputStream.readShort();
			speedZ = par1DataInputStream.readShort();
		}
	}

	public void writePacketData(DataOutputStream par1DataOutputStream) throws IOException {
		par1DataOutputStream.writeInt(entityId);
		par1DataOutputStream.writeByte(type);
		par1DataOutputStream.writeInt(xPosition);
		par1DataOutputStream.writeInt(yPosition);
		par1DataOutputStream.writeInt(zPosition);
		par1DataOutputStream.writeInt(throwerEntityId);

		if (throwerEntityId > 0) {
			par1DataOutputStream.writeShort(speedX);
			par1DataOutputStream.writeShort(speedY);
			par1DataOutputStream.writeShort(speedZ);
		}
	}

	public void processPacket(NetHandler par1NetHandler) {
		par1NetHandler.handleVehicleSpawn(this);
	}

	public int getPacketSize() {
		return 21 + throwerEntityId <= 0 ? 0 : 6;
	}
}
