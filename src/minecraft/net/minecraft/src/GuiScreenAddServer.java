package net.minecraft.src;

import java.util.List;
import org.lwjgl.input.Keyboard;

public class GuiScreenAddServer extends GuiScreen {
	private GuiScreen parentGui;
	private GuiTextField serverAddress;
	private GuiTextField serverName;
	private ServerNBTStorage serverNBTStorage;

	public GuiScreenAddServer(GuiScreen par1GuiScreen, ServerNBTStorage par2ServerNBTStorage) {
		parentGui = par1GuiScreen;
		serverNBTStorage = par2ServerNBTStorage;
	}

	public void updateScreen() {
		serverName.updateCursorCounter();
		serverAddress.updateCursorCounter();
	}

	public void initGui() {
		StringTranslate stringtranslate = StringTranslate.getInstance();
		Keyboard.enableRepeatEvents(true);
		controlList.clear();
		controlList.add(new GuiButton(0, width / 2 - 100, height / 4 + 96 + 12, stringtranslate.translateKey("addServer.add")));
		controlList.add(new GuiButton(1, width / 2 - 100, height / 4 + 120 + 12, stringtranslate.translateKey("gui.cancel")));
		serverName = new GuiTextField(fontRenderer, width / 2 - 100, 76, 200, 20);
		serverName.func_50033_b(true);
		serverName.setText(serverNBTStorage.name);
		serverAddress = new GuiTextField(fontRenderer, width / 2 - 100, 116, 200, 20);
		serverAddress.setMaxStringLength(128);
		serverAddress.setText(serverNBTStorage.host);
		((GuiButton)controlList.get(0)).enabled = serverAddress.getText().length() > 0 && serverAddress.getText().split(":").length > 0 && serverName.getText().length() > 0;
	}

	public void onGuiClosed() {
		Keyboard.enableRepeatEvents(false);
	}

	protected void actionPerformed(GuiButton par1GuiButton) {
		if (!par1GuiButton.enabled) {
			return;
		}

		if (par1GuiButton.id == 1) {
			parentGui.deleteWorld(false, 0);
		} else if (par1GuiButton.id == 0) {
			serverNBTStorage.name = serverName.getText();
			serverNBTStorage.host = serverAddress.getText();
			parentGui.deleteWorld(true, 0);
		}
	}

	protected void keyTyped(char par1, int par2) {
		serverName.func_50037_a(par1, par2);
		serverAddress.func_50037_a(par1, par2);

		if (par1 == '\t') {
			if (serverName.func_50025_j()) {
				serverName.func_50033_b(false);
				serverAddress.func_50033_b(true);
			} else {
				serverName.func_50033_b(true);
				serverAddress.func_50033_b(false);
			}
		}

		if (par1 == '\r') {
			actionPerformed((GuiButton)controlList.get(0));
		}

		((GuiButton)controlList.get(0)).enabled = serverAddress.getText().length() > 0 && serverAddress.getText().split(":").length > 0 && serverName.getText().length() > 0;

		if (((GuiButton)controlList.get(0)).enabled) {
			String s = serverAddress.getText().trim();
			String as[] = s.split(":");

			if (as.length > 2) {
				((GuiButton)controlList.get(0)).enabled = false;
			}
		}
	}

	protected void mouseClicked(int par1, int par2, int par3) {
		super.mouseClicked(par1, par2, par3);
		serverAddress.mouseClicked(par1, par2, par3);
		serverName.mouseClicked(par1, par2, par3);
	}

	public void drawScreen(int par1, int par2, float par3) {
		StringTranslate stringtranslate = StringTranslate.getInstance();
		drawDefaultBackground();
		drawCenteredString(fontRenderer, stringtranslate.translateKey("addServer.title"), width / 2, (height / 4 - 60) + 20, 0xffffff);
		drawString(fontRenderer, stringtranslate.translateKey("addServer.enterName"), width / 2 - 100, 63, 0xa0a0a0);
		drawString(fontRenderer, stringtranslate.translateKey("addServer.enterIp"), width / 2 - 100, 104, 0xa0a0a0);
		serverName.drawTextBox();
		serverAddress.drawTextBox();
		super.drawScreen(par1, par2, par3);
	}
}
