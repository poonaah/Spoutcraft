package net.minecraft.src;

public class StructurePieceTreasure extends WeightedRandomChoice {
	public int itemID;
	public int itemMetadata;
	public int minItemStack;
	public int maxItemStack;

	public StructurePieceTreasure(int par1, int par2, int par3, int par4, int par5) {
		super(par5);
		itemID = par1;
		itemMetadata = par2;
		minItemStack = par3;
		maxItemStack = par4;
	}
}
