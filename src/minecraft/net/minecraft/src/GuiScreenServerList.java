package net.minecraft.src;

import java.util.List;
import org.lwjgl.input.Keyboard;

public class GuiScreenServerList extends GuiScreen {
	private GuiScreen guiScreen;
	private GuiTextField serverTextField;
	private ServerNBTStorage serverListStorage;

	public GuiScreenServerList(GuiScreen par1GuiScreen, ServerNBTStorage par2ServerNBTStorage) {
		guiScreen = par1GuiScreen;
		serverListStorage = par2ServerNBTStorage;
	}

	public void updateScreen() {
		serverTextField.updateCursorCounter();
	}

	public void initGui() {
		StringTranslate stringtranslate = StringTranslate.getInstance();
		Keyboard.enableRepeatEvents(true);
		controlList.clear();
		controlList.add(new GuiButton(0, width / 2 - 100, height / 4 + 96 + 12, stringtranslate.translateKey("selectServer.select")));
		controlList.add(new GuiButton(1, width / 2 - 100, height / 4 + 120 + 12, stringtranslate.translateKey("gui.cancel")));
		serverTextField = new GuiTextField(fontRenderer, width / 2 - 100, 116, 200, 20);
		serverTextField.setMaxStringLength(128);
		serverTextField.func_50033_b(true);
		serverTextField.setText(serverListStorage.host);
		((GuiButton)controlList.get(0)).enabled = serverTextField.getText().length() > 0 && serverTextField.getText().split(":").length > 0;
	}

	public void onGuiClosed() {
		Keyboard.enableRepeatEvents(false);
	}

	protected void actionPerformed(GuiButton par1GuiButton) {
		if (!par1GuiButton.enabled) {
			return;
		}

		if (par1GuiButton.id == 1) {
			guiScreen.deleteWorld(false, 0);
		} else if (par1GuiButton.id == 0) {
			serverListStorage.host = serverTextField.getText();
			guiScreen.deleteWorld(true, 0);
		}
	}

	protected void keyTyped(char par1, int par2) {
		serverTextField.func_50037_a(par1, par2);

		if (par1 == '\r') {
			actionPerformed((GuiButton)controlList.get(0));
		}

		((GuiButton)controlList.get(0)).enabled = serverTextField.getText().length() > 0 && serverTextField.getText().split(":").length > 0;
	}

	protected void mouseClicked(int par1, int par2, int par3) {
		super.mouseClicked(par1, par2, par3);
		serverTextField.mouseClicked(par1, par2, par3);
	}

	public void drawScreen(int par1, int par2, float par3) {
		StringTranslate stringtranslate = StringTranslate.getInstance();
		drawDefaultBackground();
		drawCenteredString(fontRenderer, stringtranslate.translateKey("selectServer.direct"), width / 2, (height / 4 - 60) + 20, 0xffffff);
		drawString(fontRenderer, stringtranslate.translateKey("addServer.enterIp"), width / 2 - 100, 100, 0xa0a0a0);
		serverTextField.drawTextBox();
		super.drawScreen(par1, par2, par3);
	}
}
