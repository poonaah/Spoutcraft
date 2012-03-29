package net.minecraft.src;

public class GuiPlayerInfo {
	public final String name;
	private final String field_50099_c;
	public int responseTime;

	public GuiPlayerInfo(String par1Str) {
		name = par1Str;
		field_50099_c = par1Str.toLowerCase();
	}

	public boolean func_50098_a(String par1Str) {
		return field_50099_c.startsWith(par1Str);
	}
}
