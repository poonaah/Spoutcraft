package net.minecraft.src;

import java.util.Random;

public class MapGenBase {
	protected int range;
	protected Random rand;
	protected World worldObj;

	public MapGenBase() {
		range = 8;
		rand = new Random();
	}

	public void generate(IChunkProvider par1IChunkProvider, World par2World, int par3, int par4, byte par5ArrayOfByte[]) {
		int i = range;
		worldObj = par2World;
		rand.setSeed(par2World.getSeed());
		long l = rand.nextLong();
		long l1 = rand.nextLong();

		for (int j = par3 - i; j <= par3 + i; j++) {
			for (int k = par4 - i; k <= par4 + i; k++) {
				long l2 = (long)j * l;
				long l3 = (long)k * l1;
				rand.setSeed(l2 ^ l3 ^ par2World.getSeed());
				recursiveGenerate(par2World, j, k, par3, par4, par5ArrayOfByte);
			}
		}
	}

	protected void recursiveGenerate(World world, int i, int j, int k, int l, byte abyte0[]) {
	}
}
