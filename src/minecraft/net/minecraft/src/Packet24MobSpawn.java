package net.minecraft.src;

import java.io.*;
import java.util.List;

public class Packet24MobSpawn extends Packet {
	public int entityId;
	public int type;
	public int xPosition;
	public int yPosition;
	public int zPosition;
	public byte yaw;
	public byte pitch;
	public byte field_48169_h;
	private DataWatcher metaData;
	private List receivedMetadata;

	public Packet24MobSpawn() {
	}

	public Packet24MobSpawn(EntityLiving par1EntityLiving) {
		entityId = par1EntityLiving.entityId;
		type = (byte)EntityList.getEntityID(par1EntityLiving);
		xPosition = MathHelper.floor_double(par1EntityLiving.posX * 32D);
		yPosition = MathHelper.floor_double(par1EntityLiving.posY * 32D);
		zPosition = MathHelper.floor_double(par1EntityLiving.posZ * 32D);
		yaw = (byte)(int)((par1EntityLiving.rotationYaw * 256F) / 360F);
		pitch = (byte)(int)((par1EntityLiving.rotationPitch * 256F) / 360F);
		field_48169_h = (byte)(int)((par1EntityLiving.prevRotationYaw2 * 256F) / 360F);
		metaData = par1EntityLiving.getDataWatcher();
	}

	public void readPacketData(DataInputStream par1DataInputStream) throws IOException {
		entityId = par1DataInputStream.readInt();
		type = par1DataInputStream.readByte() & 0xff;
		xPosition = par1DataInputStream.readInt();
		yPosition = par1DataInputStream.readInt();
		zPosition = par1DataInputStream.readInt();
		yaw = par1DataInputStream.readByte();
		pitch = par1DataInputStream.readByte();
		field_48169_h = par1DataInputStream.readByte();
		receivedMetadata = DataWatcher.readWatchableObjects(par1DataInputStream);
	}

	public void writePacketData(DataOutputStream par1DataOutputStream) throws IOException {
		par1DataOutputStream.writeInt(entityId);
		par1DataOutputStream.writeByte(type & 0xff);
		par1DataOutputStream.writeInt(xPosition);
		par1DataOutputStream.writeInt(yPosition);
		par1DataOutputStream.writeInt(zPosition);
		par1DataOutputStream.writeByte(yaw);
		par1DataOutputStream.writeByte(pitch);
		par1DataOutputStream.writeByte(field_48169_h);
		metaData.writeWatchableObjects(par1DataOutputStream);
	}

	public void processPacket(NetHandler par1NetHandler) {
		par1NetHandler.handleMobSpawn(this);
	}

	public int getPacketSize() {
		return 20;
	}

	public List getMetadata() {
		return receivedMetadata;
	}
}
