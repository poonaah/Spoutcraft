package net.minecraft.src;

import java.util.List;

public class GuiDownloadTerrain extends GuiScreen {
	private NetClientHandler netHandler;
	private int updateCounter;

	public GuiDownloadTerrain(NetClientHandler par1NetClientHandler) {
		updateCounter = 0;
		netHandler = par1NetClientHandler;
	}

	protected void keyTyped(char c, int i) {
	}

	public void initGui() {
		controlList.clear();
	}

	public void updateScreen() {
		updateCounter++;

		if (updateCounter % 20 == 0) {
			netHandler.addToSendQueue(new Packet0KeepAlive());
		}

		if (netHandler != null) {
			netHandler.processReadPackets();
		}
	}

	protected void actionPerformed(GuiButton guibutton) {
	}

	public void drawScreen(int par1, int par2, float par3) {
		drawBackground(0);
		StringTranslate stringtranslate = StringTranslate.getInstance();
		drawCenteredString(fontRenderer, stringtranslate.translateKey("multiplayer.downloadingTerrain"), width / 2, height / 2 - 50, 0xffffff);
		super.drawScreen(par1, par2, par3);
	}
}
