package net.minecraft.src;

import java.util.List;
import net.minecraft.client.Minecraft;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

public class GuiStats extends GuiScreen {
	private static RenderItem renderItem = new RenderItem();
	protected GuiScreen parentGui;
	protected String statsTitle;
	private GuiSlotStatsGeneral slotGeneral;
	private GuiSlotStatsItem slotItem;
	private GuiSlotStatsBlock slotBlock;
	private StatFileWriter statFileWriter;
	private GuiSlot selectedSlot;

	public GuiStats(GuiScreen par1GuiScreen, StatFileWriter par2StatFileWriter) {
		statsTitle = "Select world";
		selectedSlot = null;
		parentGui = par1GuiScreen;
		statFileWriter = par2StatFileWriter;
	}

	public void initGui() {
		statsTitle = StatCollector.translateToLocal("gui.stats");
		slotGeneral = new GuiSlotStatsGeneral(this);
		slotGeneral.registerScrollButtons(controlList, 1, 1);
		slotItem = new GuiSlotStatsItem(this);
		slotItem.registerScrollButtons(controlList, 1, 1);
		slotBlock = new GuiSlotStatsBlock(this);
		slotBlock.registerScrollButtons(controlList, 1, 1);
		selectedSlot = slotGeneral;
		addHeaderButtons();
	}

	public void addHeaderButtons() {
		StringTranslate stringtranslate = StringTranslate.getInstance();
		controlList.add(new GuiButton(0, width / 2 + 4, height - 28, 150, 20, stringtranslate.translateKey("gui.done")));
		controlList.add(new GuiButton(1, width / 2 - 154, height - 52, 100, 20, stringtranslate.translateKey("stat.generalButton")));
		GuiButton guibutton;
		controlList.add(guibutton = new GuiButton(2, width / 2 - 46, height - 52, 100, 20, stringtranslate.translateKey("stat.blocksButton")));
		GuiButton guibutton1;
		controlList.add(guibutton1 = new GuiButton(3, width / 2 + 62, height - 52, 100, 20, stringtranslate.translateKey("stat.itemsButton")));

		if (slotBlock.getSize() == 0) {
			guibutton.enabled = false;
		}

		if (slotItem.getSize() == 0) {
			guibutton1.enabled = false;
		}
	}

	protected void actionPerformed(GuiButton par1GuiButton) {
		if (!par1GuiButton.enabled) {
			return;
		}

		if (par1GuiButton.id == 0) {
			mc.displayGuiScreen(parentGui);
		} else if (par1GuiButton.id == 1) {
			selectedSlot = slotGeneral;
		} else if (par1GuiButton.id == 3) {
			selectedSlot = slotItem;
		} else if (par1GuiButton.id == 2) {
			selectedSlot = slotBlock;
		} else {
			selectedSlot.actionPerformed(par1GuiButton);
		}
	}

	public void drawScreen(int par1, int par2, float par3) {
		selectedSlot.drawScreen(par1, par2, par3);
		drawCenteredString(fontRenderer, statsTitle, width / 2, 20, 0xffffff);
		super.drawScreen(par1, par2, par3);
	}

	private void drawItemSprite(int par1, int par2, int par3) {
		drawButtonBackground(par1 + 1, par2 + 1);
		GL11.glEnable(GL12.GL_RESCALE_NORMAL);
		RenderHelper.enableGUIStandardItemLighting();
		renderItem.drawItemIntoGui(fontRenderer, mc.renderEngine, par3, 0, Item.itemsList[par3].getIconFromDamage(0), par1 + 2, par2 + 2);
		RenderHelper.disableStandardItemLighting();
		GL11.glDisable(GL12.GL_RESCALE_NORMAL);
	}

	private void drawButtonBackground(int par1, int par2) {
		drawSprite(par1, par2, 0, 0);
	}

	private void drawSprite(int par1, int par2, int par3, int par4) {
		int i = mc.renderEngine.getTexture("/gui/slot.png");
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		mc.renderEngine.bindTexture(i);
		Tessellator tessellator = Tessellator.instance;
		tessellator.startDrawingQuads();
		tessellator.addVertexWithUV(par1 + 0, par2 + 18, zLevel, (float)(par3 + 0) * 0.0078125F, (float)(par4 + 18) * 0.0078125F);
		tessellator.addVertexWithUV(par1 + 18, par2 + 18, zLevel, (float)(par3 + 18) * 0.0078125F, (float)(par4 + 18) * 0.0078125F);
		tessellator.addVertexWithUV(par1 + 18, par2 + 0, zLevel, (float)(par3 + 18) * 0.0078125F, (float)(par4 + 0) * 0.0078125F);
		tessellator.addVertexWithUV(par1 + 0, par2 + 0, zLevel, (float)(par3 + 0) * 0.0078125F, (float)(par4 + 0) * 0.0078125F);
		tessellator.draw();
	}

	static Minecraft getMinecraft(GuiStats par0GuiStats) {
		return par0GuiStats.mc;
	}

	static FontRenderer getFontRenderer1(GuiStats par0GuiStats) {
		return par0GuiStats.fontRenderer;
	}

	static StatFileWriter getStatsFileWriter(GuiStats par0GuiStats) {
		return par0GuiStats.statFileWriter;
	}

	static FontRenderer getFontRenderer2(GuiStats par0GuiStats) {
		return par0GuiStats.fontRenderer;
	}

	static FontRenderer getFontRenderer3(GuiStats par0GuiStats) {
		return par0GuiStats.fontRenderer;
	}

	static Minecraft getMinecraft1(GuiStats par0GuiStats) {
		return par0GuiStats.mc;
	}

	static void drawSprite(GuiStats par0GuiStats, int par1, int par2, int par3, int par4) {
		par0GuiStats.drawSprite(par1, par2, par3, par4);
	}

	static Minecraft getMinecraft2(GuiStats par0GuiStats) {
		return par0GuiStats.mc;
	}

	static FontRenderer getFontRenderer4(GuiStats par0GuiStats) {
		return par0GuiStats.fontRenderer;
	}

	static FontRenderer getFontRenderer5(GuiStats par0GuiStats) {
		return par0GuiStats.fontRenderer;
	}

	static FontRenderer getFontRenderer6(GuiStats par0GuiStats) {
		return par0GuiStats.fontRenderer;
	}

	static FontRenderer getFontRenderer7(GuiStats par0GuiStats) {
		return par0GuiStats.fontRenderer;
	}

	static FontRenderer getFontRenderer8(GuiStats par0GuiStats) {
		return par0GuiStats.fontRenderer;
	}

	static void drawGradientRect(GuiStats par0GuiStats, int par1, int par2, int par3, int par4, int par5, int par6) {
		par0GuiStats.drawGradientRect(par1, par2, par3, par4, par5, par6);
	}

	static FontRenderer getFontRenderer9(GuiStats par0GuiStats) {
		return par0GuiStats.fontRenderer;
	}

	static FontRenderer getFontRenderer10(GuiStats par0GuiStats) {
		return par0GuiStats.fontRenderer;
	}

	static void drawGradientRect1(GuiStats par0GuiStats, int par1, int par2, int par3, int par4, int par5, int par6) {
		par0GuiStats.drawGradientRect(par1, par2, par3, par4, par5, par6);
	}

	static FontRenderer getFontRenderer11(GuiStats par0GuiStats) {
		return par0GuiStats.fontRenderer;
	}

	static void drawItemSprite(GuiStats par0GuiStats, int par1, int par2, int par3) {
		par0GuiStats.drawItemSprite(par1, par2, par3);
	}
}
