package net.minecraft.src;

public class Material {
	public static final Material air;
	public static final Material grass;
	public static final Material ground;
	public static final Material wood;
	public static final Material rock;
	public static final Material iron;
	public static final Material water;
	public static final Material lava;
	public static final Material leaves;
	public static final Material plants;
	public static final Material vine;
	public static final Material sponge;
	public static final Material cloth;
	public static final Material fire;
	public static final Material sand;
	public static final Material circuits;
	public static final Material glass;
	public static final Material redstoneLight;
	public static final Material tnt;
	public static final Material unused;
	public static final Material ice;
	public static final Material snow;
	public static final Material craftedSnow;
	public static final Material cactus;
	public static final Material clay;
	public static final Material pumpkin;
	public static final Material dragonEgg;
	public static final Material portal;
	public static final Material cake;
	public static final Material web;
	public static final Material piston;
	private boolean canBurn;
	private boolean groundCover;
	private boolean isTranslucent;
	public final MapColor materialMapColor;
	private boolean canHarvest;
	private int mobilityFlag;

	public Material(MapColor par1MapColor) {
		canHarvest = true;
		materialMapColor = par1MapColor;
	}

	public boolean isLiquid() {
		return false;
	}

	public boolean isSolid() {
		return true;
	}

	public boolean getCanBlockGrass() {
		return true;
	}

	public boolean blocksMovement() {
		return true;
	}

	private Material setTranslucent() {
		isTranslucent = true;
		return this;
	}

	protected Material setNoHarvest() {
		canHarvest = false;
		return this;
	}

	protected Material setBurning() {
		canBurn = true;
		return this;
	}

	public boolean getCanBurn() {
		return canBurn;
	}

	public Material setGroundCover() {
		groundCover = true;
		return this;
	}

	public boolean isGroundCover() {
		return groundCover;
	}

	public boolean isOpaque() {
		if (isTranslucent) {
			return false;
		} else {
			return blocksMovement();
		}
	}

	public boolean isHarvestable() {
		return canHarvest;
	}

	public int getMaterialMobility() {
		return mobilityFlag;
	}

	protected Material setNoPushMobility() {
		mobilityFlag = 1;
		return this;
	}

	protected Material setImmovableMobility() {
		mobilityFlag = 2;
		return this;
	}

	static {
		air = new MaterialTransparent(MapColor.airColor);
		grass = new Material(MapColor.grassColor);
		ground = new Material(MapColor.dirtColor);
		wood = (new Material(MapColor.woodColor)).setBurning();
		rock = (new Material(MapColor.stoneColor)).setNoHarvest();
		iron = (new Material(MapColor.ironColor)).setNoHarvest();
		water = (new MaterialLiquid(MapColor.waterColor)).setNoPushMobility();
		lava = (new MaterialLiquid(MapColor.tntColor)).setNoPushMobility();
		leaves = (new Material(MapColor.foliageColor)).setBurning().setTranslucent().setNoPushMobility();
		plants = (new MaterialLogic(MapColor.foliageColor)).setNoPushMobility();
		vine = (new MaterialLogic(MapColor.foliageColor)).setBurning().setNoPushMobility().setGroundCover();
		sponge = new Material(MapColor.clothColor);
		cloth = (new Material(MapColor.clothColor)).setBurning();
		fire = (new MaterialTransparent(MapColor.airColor)).setNoPushMobility();
		sand = new Material(MapColor.sandColor);
		circuits = (new MaterialLogic(MapColor.airColor)).setNoPushMobility();
		glass = (new Material(MapColor.airColor)).setTranslucent();
		redstoneLight = new Material(MapColor.airColor);
		tnt = (new Material(MapColor.tntColor)).setBurning().setTranslucent();
		unused = (new Material(MapColor.foliageColor)).setNoPushMobility();
		ice = (new Material(MapColor.iceColor)).setTranslucent();
		snow = (new MaterialLogic(MapColor.snowColor)).setGroundCover().setTranslucent().setNoHarvest().setNoPushMobility();
		craftedSnow = (new Material(MapColor.snowColor)).setNoHarvest();
		cactus = (new Material(MapColor.foliageColor)).setTranslucent().setNoPushMobility();
		clay = new Material(MapColor.clayColor);
		pumpkin = (new Material(MapColor.foliageColor)).setNoPushMobility();
		dragonEgg = (new Material(MapColor.foliageColor)).setNoPushMobility();
		portal = (new MaterialPortal(MapColor.airColor)).setImmovableMobility();
		cake = (new Material(MapColor.airColor)).setNoPushMobility();
		web = (new MaterialWeb(MapColor.clothColor)).setNoHarvest().setNoPushMobility();
		piston = (new Material(MapColor.stoneColor)).setImmovableMobility();
	}
}