package net.minecraft.src;

public abstract class Enchantment {
	public static final Enchantment enchantmentsList[] = new Enchantment[256];
	public static final Enchantment protection = new EnchantmentProtection(0, 10, 0);
	public static final Enchantment fireProtection = new EnchantmentProtection(1, 5, 1);
	public static final Enchantment featherFalling = new EnchantmentProtection(2, 5, 2);
	public static final Enchantment blastProtection = new EnchantmentProtection(3, 2, 3);
	public static final Enchantment projectileProtection = new EnchantmentProtection(4, 5, 4);
	public static final Enchantment respiration = new EnchantmentOxygen(5, 2);
	public static final Enchantment aquaAffinity = new EnchantmentWaterWorker(6, 2);
	public static final Enchantment sharpness = new EnchantmentDamage(16, 10, 0);
	public static final Enchantment smite = new EnchantmentDamage(17, 5, 1);
	public static final Enchantment baneOfArthropods = new EnchantmentDamage(18, 5, 2);
	public static final Enchantment knockback = new EnchantmentKnockback(19, 5);
	public static final Enchantment fireAspect = new EnchantmentFireAspect(20, 2);
	public static final Enchantment looting;
	public static final Enchantment efficiency = new EnchantmentDigging(32, 10);
	public static final Enchantment silkTouch = new EnchantmentUntouching(33, 1);
	public static final Enchantment unbreaking = new EnchantmentDurability(34, 5);
	public static final Enchantment fortune;
	public static final Enchantment power = new EnchantmentArrowDamage(48, 10);
	public static final Enchantment punch = new EnchantmentArrowKnockback(49, 2);
	public static final Enchantment flame = new EnchantmentArrowFire(50, 2);
	public static final Enchantment infinity = new EnchantmentArrowInfinite(51, 1);
	public final int effectId;
	private final int weight;
	public EnumEnchantmentType type;
	protected String name;

	protected Enchantment(int par1, int par2, EnumEnchantmentType par3EnumEnchantmentType) {
		effectId = par1;
		weight = par2;
		type = par3EnumEnchantmentType;

		if (enchantmentsList[par1] != null) {
			throw new IllegalArgumentException("Duplicate enchantment id!");
		} else {
			enchantmentsList[par1] = this;
			return;
		}
	}

	public int getWeight() {
		return weight;
	}

	public int getMinLevel() {
		return 1;
	}

	public int getMaxLevel() {
		return 1;
	}

	public int getMinEnchantability(int par1) {
		return 1 + par1 * 10;
	}

	public int getMaxEnchantability(int par1) {
		return getMinEnchantability(par1) + 5;
	}

	public int calcModifierDamage(int par1, DamageSource par2DamageSource) {
		return 0;
	}

	public int calcModifierLiving(int par1, EntityLiving par2EntityLiving) {
		return 0;
	}

	public boolean canApplyTogether(Enchantment par1Enchantment) {
		return this != par1Enchantment;
	}

	public Enchantment setName(String par1Str) {
		name = par1Str;
		return this;
	}

	public String getName() {
		return (new StringBuilder()).append("enchantment.").append(name).toString();
	}

	public String getTranslatedName(int par1) {
		String s = StatCollector.translateToLocal(getName());
		return (new StringBuilder()).append(s).append(" ").append(StatCollector.translateToLocal((new StringBuilder()).append("enchantment.level.").append(par1).toString())).toString();
	}

	static {
		looting = new EnchantmentLootBonus(21, 2, EnumEnchantmentType.weapon);
		fortune = new EnchantmentLootBonus(35, 2, EnumEnchantmentType.digger);
	}
}
