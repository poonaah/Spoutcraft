package net.minecraft.src;

import java.util.List;
import net.minecraft.client.Minecraft;

public class GuiLanguage extends GuiScreen {
	protected GuiScreen parentGui;
	private int updateTimer;
	private GuiSlotLanguage languageList;
	private final GameSettings field_44006_d;
	private GuiSmallButton doneButton;

	public GuiLanguage(GuiScreen par1GuiScreen, GameSettings par2GameSettings) {
		updateTimer = -1;
		parentGui = par1GuiScreen;
		field_44006_d = par2GameSettings;
	}

	public void initGui() {
		StringTranslate stringtranslate = StringTranslate.getInstance();
		controlList.add(doneButton = new GuiSmallButton(6, width / 2 - 75, height - 38, stringtranslate.translateKey("gui.done")));
		languageList = new GuiSlotLanguage(this);
		languageList.registerScrollButtons(controlList, 7, 8);
	}

	protected void actionPerformed(GuiButton par1GuiButton) {
		if (!par1GuiButton.enabled) {
			return;
		}

		if (par1GuiButton.id != 5) {
			if (par1GuiButton.id == 6) {
				field_44006_d.saveOptions();
				mc.displayGuiScreen(parentGui);
			} else {
				languageList.actionPerformed(par1GuiButton);
			}
		}
	}

	protected void mouseClicked(int par1, int par2, int par3) {
		super.mouseClicked(par1, par2, par3);
	}

	protected void mouseMovedOrUp(int par1, int par2, int par3) {
		super.mouseMovedOrUp(par1, par2, par3);
	}

	public void drawScreen(int par1, int par2, float par3) {
		languageList.drawScreen(par1, par2, par3);

		if (updateTimer <= 0) {
			mc.texturePackList.updateAvaliableTexturePacks();
			updateTimer += 20;
		}

		StringTranslate stringtranslate = StringTranslate.getInstance();
		drawCenteredString(fontRenderer, stringtranslate.translateKey("options.language"), width / 2, 16, 0xffffff);
		drawCenteredString(fontRenderer, (new StringBuilder()).append("(").append(stringtranslate.translateKey("options.languageWarning")).append(")").toString(), width / 2, height - 56, 0x808080);
		super.drawScreen(par1, par2, par3);
	}

	public void updateScreen() {
		super.updateScreen();
		updateTimer--;
	}

	static GameSettings func_44005_a(GuiLanguage par0GuiLanguage) {
		return par0GuiLanguage.field_44006_d;
	}

	static GuiSmallButton func_46028_b(GuiLanguage par0GuiLanguage) {
		return par0GuiLanguage.doneButton;
	}
}
