package net.minecraft.src;

import java.io.*;

public class Packet41EntityEffect extends Packet {
	public int entityId;
	public byte effectId;
	public byte effectAmp;
	public short duration;

	public Packet41EntityEffect() {
	}

	public void readPacketData(DataInputStream par1DataInputStream) throws IOException {
		entityId = par1DataInputStream.readInt();
		effectId = par1DataInputStream.readByte();
		effectAmp = par1DataInputStream.readByte();
		duration = par1DataInputStream.readShort();
	}

	public void writePacketData(DataOutputStream par1DataOutputStream) throws IOException {
		par1DataOutputStream.writeInt(entityId);
		par1DataOutputStream.writeByte(effectId);
		par1DataOutputStream.writeByte(effectAmp);
		par1DataOutputStream.writeShort(duration);
	}

	public void processPacket(NetHandler par1NetHandler) {
		par1NetHandler.handleEntityEffect(this);
	}

	public int getPacketSize() {
		return 8;
	}
}
