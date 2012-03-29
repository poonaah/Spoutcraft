package net.minecraft.src;

public class NextTickListEntry implements Comparable {
	private static long nextTickEntryID = 0L;
	public int xCoord;
	public int yCoord;
	public int zCoord;
	public int blockID;
	public long scheduledTime;
	private long tickEntryID;

	public NextTickListEntry(int par1, int par2, int par3, int par4) {
		tickEntryID = nextTickEntryID++;
		xCoord = par1;
		yCoord = par2;
		zCoord = par3;
		blockID = par4;
	}

	public boolean equals(Object par1Obj) {
		if (par1Obj instanceof NextTickListEntry) {
			NextTickListEntry nextticklistentry = (NextTickListEntry)par1Obj;
			return xCoord == nextticklistentry.xCoord && yCoord == nextticklistentry.yCoord && zCoord == nextticklistentry.zCoord && blockID == nextticklistentry.blockID;
		} else {
			return false;
		}
	}

	public int hashCode() {
		return (xCoord * 1024 * 1024 + zCoord * 1024 + yCoord) * 256 + blockID;
	}

	public NextTickListEntry setScheduledTime(long par1) {
		scheduledTime = par1;
		return this;
	}

	public int comparer(NextTickListEntry par1NextTickListEntry) {
		if (scheduledTime < par1NextTickListEntry.scheduledTime) {
			return -1;
		}

		if (scheduledTime > par1NextTickListEntry.scheduledTime) {
			return 1;
		}

		if (tickEntryID < par1NextTickListEntry.tickEntryID) {
			return -1;
		}

		return tickEntryID <= par1NextTickListEntry.tickEntryID ? 0 : 1;
	}

	public int compareTo(Object par1Obj) {
		return comparer((NextTickListEntry)par1Obj);
	}
}
