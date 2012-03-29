package net.minecraft.src;

public class LongHashMap {
	private transient LongHashMapEntry hashArray[];
	private transient int numHashElements;
	private int capacity;
	private final float percentUseable = 0.75F;
	private volatile transient int modCount;

	public LongHashMap() {
		capacity = 12;
		hashArray = new LongHashMapEntry[16];
	}

	private static int getHashedKey(long par0) {
		return hash((int)(par0 ^ par0 >>> 32));
	}

	private static int hash(int par0) {
		par0 ^= par0 >>> 20 ^ par0 >>> 12;
		return par0 ^ par0 >>> 7 ^ par0 >>> 4;
	}

	private static int getHashIndex(int par0, int par1) {
		return par0 & par1 - 1;
	}

	public int getNumHashElements() {
		return numHashElements;
	}

	public Object getValueByKey(long par1) {
		int i = getHashedKey(par1);

		for (LongHashMapEntry longhashmapentry = hashArray[getHashIndex(i, hashArray.length)]; longhashmapentry != null; longhashmapentry = longhashmapentry.nextEntry) {
			if (longhashmapentry.key == par1) {
				return longhashmapentry.value;
			}
		}

		return null;
	}

	public boolean containsItem(long par1) {
		return getEntry(par1) != null;
	}

	final LongHashMapEntry getEntry(long par1) {
		int i = getHashedKey(par1);

		for (LongHashMapEntry longhashmapentry = hashArray[getHashIndex(i, hashArray.length)]; longhashmapentry != null; longhashmapentry = longhashmapentry.nextEntry) {
			if (longhashmapentry.key == par1) {
				return longhashmapentry;
			}
		}

		return null;
	}

	public void add(long par1, Object par3Obj) {
		int i = getHashedKey(par1);
		int j = getHashIndex(i, hashArray.length);

		for (LongHashMapEntry longhashmapentry = hashArray[j]; longhashmapentry != null; longhashmapentry = longhashmapentry.nextEntry) {
			if (longhashmapentry.key == par1) {
				longhashmapentry.value = par3Obj;
			}
		}

		modCount++;
		createKey(i, par1, par3Obj, j);
	}

	private void resizeTable(int par1) {
		LongHashMapEntry alonghashmapentry[] = hashArray;
		int i = alonghashmapentry.length;

		if (i == 0x40000000) {
			capacity = 0x7fffffff;
			return;
		} else {
			LongHashMapEntry alonghashmapentry1[] = new LongHashMapEntry[par1];
			copyHashTableTo(alonghashmapentry1);
			hashArray = alonghashmapentry1;
			capacity = (int)((float)par1 * percentUseable);
			return;
		}
	}

	private void copyHashTableTo(LongHashMapEntry par1ArrayOfLongHashMapEntry[]) {
		LongHashMapEntry alonghashmapentry[] = hashArray;
		int i = par1ArrayOfLongHashMapEntry.length;

		for (int j = 0; j < alonghashmapentry.length; j++) {
			LongHashMapEntry longhashmapentry = alonghashmapentry[j];

			if (longhashmapentry == null) {
				continue;
			}

			alonghashmapentry[j] = null;

			do {
				LongHashMapEntry longhashmapentry1 = longhashmapentry.nextEntry;
				int k = getHashIndex(longhashmapentry.hash, i);
				longhashmapentry.nextEntry = par1ArrayOfLongHashMapEntry[k];
				par1ArrayOfLongHashMapEntry[k] = longhashmapentry;
				longhashmapentry = longhashmapentry1;
			} while (longhashmapentry != null);
		}
	}

	public Object remove(long par1) {
		LongHashMapEntry longhashmapentry = removeKey(par1);
		return longhashmapentry != null ? longhashmapentry.value : null;
	}

	final LongHashMapEntry removeKey(long par1) {
		int i = getHashedKey(par1);
		int j = getHashIndex(i, hashArray.length);
		LongHashMapEntry longhashmapentry = hashArray[j];
		LongHashMapEntry longhashmapentry1;
		LongHashMapEntry longhashmapentry2;

		for (longhashmapentry1 = longhashmapentry; longhashmapentry1 != null; longhashmapentry1 = longhashmapentry2) {
			longhashmapentry2 = longhashmapentry1.nextEntry;

			if (longhashmapentry1.key == par1) {
				modCount++;
				numHashElements--;

				if (longhashmapentry == longhashmapentry1) {
					hashArray[j] = longhashmapentry2;
				} else {
					longhashmapentry.nextEntry = longhashmapentry2;
				}

				return longhashmapentry1;
			}

			longhashmapentry = longhashmapentry1;
		}

		return longhashmapentry1;
	}

	private void createKey(int par1, long par2, Object par4Obj, int par5) {
		LongHashMapEntry longhashmapentry = hashArray[par5];
		hashArray[par5] = new LongHashMapEntry(par1, par2, par4Obj, longhashmapentry);

		if (numHashElements++ >= capacity) {
			resizeTable(2 * hashArray.length);
		}
	}

	static int getHashCode(long par0) {
		return getHashedKey(par0);
	}
}
