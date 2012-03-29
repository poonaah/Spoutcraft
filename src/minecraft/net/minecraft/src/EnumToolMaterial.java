package net.minecraft.src;

public enum EnumToolMaterial {
	WOOD(0, 59, 2.0F, 0, 15),
	STONE(1, 131, 4F, 1, 5),
	IRON(2, 250, 6F, 2, 14),
	EMERALD(3, 1561, 8F, 3, 10),
	GOLD(0, 32, 12F, 0, 22);

	private final int harvestLevel;
	private final int maxUses;
	private final float efficiencyOnProperMaterial;
	private final int damageVsEntity;
	private final int enchantability;

	private EnumToolMaterial(int par3, int par4, float par5, int par6, int par7) {
		harvestLevel = par3;
		maxUses = par4;
		efficiencyOnProperMaterial = par5;
		damageVsEntity = par6;
		enchantability = par7;
	}

	public int getMaxUses() {
		return maxUses;
	}

	public float getEfficiencyOnProperMaterial() {
		return efficiencyOnProperMaterial;
	}

	public int getDamageVsEntity() {
		return damageVsEntity;
	}

	public int getHarvestLevel() {
		return harvestLevel;
	}

	public int getEnchantability() {
		return enchantability;
	}
}
