package net.minecraft.src;

import java.util.List;
import net.minecraft.client.Minecraft;

public class GuiConflictWarning extends GuiScreen {
	private int updateCounter;

	public GuiConflictWarning() {
		updateCounter = 0;
	}

	public void updateScreen() {
		updateCounter++;
	}

	public void initGui() {
		controlList.clear();
		controlList.add(new GuiButton(0, width / 2 - 100, height / 4 + 120 + 12, "Back to title screen"));
	}

	protected void actionPerformed(GuiButton par1GuiButton) {
		if (!par1GuiButton.enabled) {
			return;
		}

		if (par1GuiButton.id == 0) {
			mc.displayGuiScreen(new GuiMainMenu());
		}
	}

	public void drawScreen(int par1, int par2, float par3) {
		drawDefaultBackground();
		drawCenteredString(fontRenderer, "Level save conflict", width / 2, (height / 4 - 60) + 20, 0xffffff);
		drawString(fontRenderer, "Minecraft detected a conflict in the level save data.", width / 2 - 140, (height / 4 - 60) + 60 + 0, 0xa0a0a0);
		drawString(fontRenderer, "This could be caused by two copies of the game", width / 2 - 140, (height / 4 - 60) + 60 + 18, 0xa0a0a0);
		drawString(fontRenderer, "accessing the same level.", width / 2 - 140, (height / 4 - 60) + 60 + 27, 0xa0a0a0);
		drawString(fontRenderer, "To prevent level corruption, the current game has quit.", width / 2 - 140, (height / 4 - 60) + 60 + 45, 0xa0a0a0);
		super.drawScreen(par1, par2, par3);
	}
}