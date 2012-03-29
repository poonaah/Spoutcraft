package net.minecraft.src;

public class DamageSource {
	public static DamageSource inFire = (new DamageSource("inFire")).setFireDamage();
	public static DamageSource onFire = (new DamageSource("onFire")).setDamageBypassesArmor().setFireDamage();
	public static DamageSource lava = (new DamageSource("lava")).setFireDamage();
	public static DamageSource inWall = (new DamageSource("inWall")).setDamageBypassesArmor();
	public static DamageSource drown = (new DamageSource("drown")).setDamageBypassesArmor();
	public static DamageSource starve = (new DamageSource("starve")).setDamageBypassesArmor();
	public static DamageSource cactus = new DamageSource("cactus");
	public static DamageSource fall = (new DamageSource("fall")).setDamageBypassesArmor();
	public static DamageSource outOfWorld = (new DamageSource("outOfWorld")).setDamageBypassesArmor().setDamageAllowedInCreativeMode();
	public static DamageSource generic = (new DamageSource("generic")).setDamageBypassesArmor();
	public static DamageSource explosion = new DamageSource("explosion");
	public static DamageSource magic = (new DamageSource("magic")).setDamageBypassesArmor();
	private boolean isUnblockable;
	private boolean isDamageAllowedInCreativeMode;
	private float hungerDamage;
	private boolean fireDamage;
	private boolean projectile;
	public String damageType;

	public static DamageSource causeMobDamage(EntityLiving par0EntityLiving) {
		return new EntityDamageSource("mob", par0EntityLiving);
	}

	public static DamageSource causePlayerDamage(EntityPlayer par0EntityPlayer) {
		return new EntityDamageSource("player", par0EntityPlayer);
	}

	public static DamageSource causeArrowDamage(EntityArrow par0EntityArrow, Entity par1Entity) {
		return (new EntityDamageSourceIndirect("arrow", par0EntityArrow, par1Entity)).setProjectile();
	}

	public static DamageSource causeFireballDamage(EntityFireball par0EntityFireball, Entity par1Entity) {
		return (new EntityDamageSourceIndirect("fireball", par0EntityFireball, par1Entity)).setFireDamage().setProjectile();
	}

	public static DamageSource causeThrownDamage(Entity par0Entity, Entity par1Entity) {
		return (new EntityDamageSourceIndirect("thrown", par0Entity, par1Entity)).setProjectile();
	}

	public static DamageSource causeIndirectMagicDamage(Entity par0Entity, Entity par1Entity) {
		return (new EntityDamageSourceIndirect("indirectMagic", par0Entity, par1Entity)).setDamageBypassesArmor();
	}

	public boolean isProjectile() {
		return projectile;
	}

	public DamageSource setProjectile() {
		projectile = true;
		return this;
	}

	public boolean isUnblockable() {
		return isUnblockable;
	}

	public float getHungerDamage() {
		return hungerDamage;
	}

	public boolean canHarmInCreative() {
		return isDamageAllowedInCreativeMode;
	}

	protected DamageSource(String par1Str) {
		isUnblockable = false;
		isDamageAllowedInCreativeMode = false;
		hungerDamage = 0.3F;
		damageType = par1Str;
	}

	public Entity getSourceOfDamage() {
		return getEntity();
	}

	public Entity getEntity() {
		return null;
	}

	protected DamageSource setDamageBypassesArmor() {
		isUnblockable = true;
		hungerDamage = 0.0F;
		return this;
	}

	protected DamageSource setDamageAllowedInCreativeMode() {
		isDamageAllowedInCreativeMode = true;
		return this;
	}

	protected DamageSource setFireDamage() {
		fireDamage = true;
		return this;
	}

	public boolean fireDamage() {
		return fireDamage;
	}

	public String getDamageType() {
		return damageType;
	}
}
