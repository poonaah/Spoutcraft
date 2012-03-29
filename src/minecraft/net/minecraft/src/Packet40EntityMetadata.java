package net.minecraft.src;

import java.io.*;
import java.util.List;

public class Packet40EntityMetadata extends Packet {
	public int entityId;
	private List metadata;

	public Packet40EntityMetadata() {
	}

	public void readPacketData(DataInputStream par1DataInputStream) throws IOException {
		entityId = par1DataInputStream.readInt();
		metadata = DataWatcher.readWatchableObjects(par1DataInputStream);
	}

	public void writePacketData(DataOutputStream par1DataOutputStream) throws IOException {
		par1DataOutputStream.writeInt(entityId);
		DataWatcher.writeObjectsInListToStream(metadata, par1DataOutputStream);
	}

	public void processPacket(NetHandler par1NetHandler) {
		par1NetHandler.handleEntityMetadata(this);
	}

	public int getPacketSize() {
		return 5;
	}

	public List getMetadata() {
		return metadata;
	}
}
