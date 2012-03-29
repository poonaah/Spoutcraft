package net.minecraft.src;

public class WorldType {
	public static final WorldType worldTypes[] = new WorldType[16];
	public static final WorldType DEFAULT = (new WorldType(0, "default", 1)).func_48631_f();
	public static final WorldType FLAT = new WorldType(1, "flat");
	public static final WorldType DEFAULT_1_1 = (new WorldType(8, "default_1_1", 0)).setCanBeCreated(false);
	private final String worldType;
	private final int generatorVersion;
	private boolean canBeCreated;
	private boolean field_48638_h;

	private WorldType(int par1, String par2Str) {
		this(par1, par2Str, 0);
	}

	private WorldType(int par1, String par2Str, int par3) {
		worldType = par2Str;
		generatorVersion = par3;
		canBeCreated = true;
		worldTypes[par1] = this;
	}

	public String func_48628_a() {
		return worldType;
	}

	public String getTranslateName() {
		return (new StringBuilder()).append("generator.").append(worldType).toString();
	}

	public int getGeneratorVersion() {
		return generatorVersion;
	}

	public WorldType func_48629_a(int par1) {
		if (this == DEFAULT && par1 == 0) {
			return DEFAULT_1_1;
		} else {
			return this;
		}
	}

	private WorldType setCanBeCreated(boolean par1) {
		canBeCreated = par1;
		return this;
	}

	public boolean getCanBeCreated() {
		return canBeCreated;
	}

	private WorldType func_48631_f() {
		field_48638_h = true;
		return this;
	}

	public boolean func_48626_e() {
		return field_48638_h;
	}

	public static WorldType parseWorldType(String par0Str) {
		for (int i = 0; i < worldTypes.length; i++) {
			if (worldTypes[i] != null && worldTypes[i].worldType.equalsIgnoreCase(par0Str)) {
				return worldTypes[i];
			}
		}

		return null;
	}
}
