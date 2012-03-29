package net.minecraft.src;

import net.minecraft.client.Minecraft;

public class StatStringFormatKeyInv implements IStatStringFormat {
	final Minecraft mc;

	public StatStringFormatKeyInv(Minecraft par1Minecraft) {
		mc = par1Minecraft;
	}

	public String formatString(String par1Str) {
		try {
			return String.format(par1Str, new Object[] {
						GameSettings.getKeyDisplayString(mc.gameSettings.keyBindInventory.keyCode)
					});
		} catch (Exception exception) {
			return (new StringBuilder()).append("Error: ").append(exception.getLocalizedMessage()).toString();
		}
	}
}
