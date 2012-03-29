package net.minecraft.src;

public class EnchantmentData extends WeightedRandomChoice {
	public final Enchantment enchantmentobj;
	public final int enchantmentLevel;

	public EnchantmentData(Enchantment par1Enchantment, int par2) {
		super(par1Enchantment.getWeight());
		enchantmentobj = par1Enchantment;
		enchantmentLevel = par2;
	}
}
