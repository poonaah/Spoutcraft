package net.minecraft.src;

public class ItemArmor extends Item {
	private static final int maxDamageArray[] = {
		11, 16, 15, 13
	};
	public final int armorType;
	public final int damageReduceAmount;
	public final int renderIndex;
	private final EnumArmorMaterial material;

	public ItemArmor(int par1, EnumArmorMaterial par2EnumArmorMaterial, int par3, int par4) {
		super(par1);
		material = par2EnumArmorMaterial;
		armorType = par4;
		renderIndex = par3;
		damageReduceAmount = par2EnumArmorMaterial.getDamageReductionAmount(par4);
		setMaxDamage(par2EnumArmorMaterial.getDurability(par4));
		maxStackSize = 1;
	}

	public int getItemEnchantability() {
		return material.getEnchantability();
	}

	static int[] getMaxDamageArray() {
		return maxDamageArray;
	}
}
