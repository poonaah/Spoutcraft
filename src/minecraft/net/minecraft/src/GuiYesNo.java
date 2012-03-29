package net.minecraft.src;

import java.util.List;

public class GuiYesNo extends GuiScreen {
	private GuiScreen parentScreen;
	private String message1;
	private String message2;
	protected String buttonText1;
	protected String buttonText2;
	private int worldNumber;

	public GuiYesNo(GuiScreen par1GuiScreen, String par2Str, String par3Str, int par4) {
		parentScreen = par1GuiScreen;
		message1 = par2Str;
		message2 = par3Str;
		worldNumber = par4;
		StringTranslate stringtranslate = StringTranslate.getInstance();
		buttonText1 = stringtranslate.translateKey("gui.yes");
		buttonText2 = stringtranslate.translateKey("gui.no");
	}

	public GuiYesNo(GuiScreen par1GuiScreen, String par2Str, String par3Str, String par4Str, String par5Str, int par6) {
		parentScreen = par1GuiScreen;
		message1 = par2Str;
		message2 = par3Str;
		buttonText1 = par4Str;
		buttonText2 = par5Str;
		worldNumber = par6;
	}

	public void initGui() {
		controlList.add(new GuiSmallButton(0, (width / 2 - 155) + 0, height / 6 + 96, buttonText1));
		controlList.add(new GuiSmallButton(1, (width / 2 - 155) + 160, height / 6 + 96, buttonText2));
	}

	protected void actionPerformed(GuiButton par1GuiButton) {
		parentScreen.deleteWorld(par1GuiButton.id == 0, worldNumber);
	}

	public void drawScreen(int par1, int par2, float par3) {
		drawDefaultBackground();
		drawCenteredString(fontRenderer, message1, width / 2, 70, 0xffffff);
		drawCenteredString(fontRenderer, message2, width / 2, 90, 0xffffff);
		super.drawScreen(par1, par2, par3);
	}
}
