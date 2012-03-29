package net.minecraft.src;

public class GuiErrorScreen extends GuiScreen {
	private String message1;
	private String message2;

	public void initGui() {
	}

	public void drawScreen(int par1, int par2, float par3) {
		drawGradientRect(0, 0, width, height, 0xff402020, 0xff501010);
		drawCenteredString(fontRenderer, message1, width / 2, 90, 0xffffff);
		drawCenteredString(fontRenderer, message2, width / 2, 110, 0xffffff);
		super.drawScreen(par1, par2, par3);
	}

	protected void keyTyped(char c, int i) {
	}
}
