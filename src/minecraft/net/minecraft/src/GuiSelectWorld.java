package net.minecraft.src;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.List;
import net.minecraft.client.Minecraft;

public class GuiSelectWorld extends GuiScreen {
	private final DateFormat dateFormatter = new SimpleDateFormat();
	protected GuiScreen parentScreen;
	protected String screenTitle;
	private boolean selected;
	private int selectedWorld;
	private List saveList;
	private GuiWorldSlot worldSlotContainer;
	private String localizedWorldText;
	private String localizedMustConvertText;
	private String localizedGameModeText[];
	private boolean deleting;
	private GuiButton buttonRename;
	private GuiButton buttonSelect;
	private GuiButton buttonDelete;

	public GuiSelectWorld(GuiScreen par1GuiScreen) {
		screenTitle = "Select world";
		selected = false;
		localizedGameModeText = new String[2];
		parentScreen = par1GuiScreen;
	}

	public void initGui() {
		StringTranslate stringtranslate = StringTranslate.getInstance();
		screenTitle = stringtranslate.translateKey("selectWorld.title");
		localizedWorldText = stringtranslate.translateKey("selectWorld.world");
		localizedMustConvertText = stringtranslate.translateKey("selectWorld.conversion");
		localizedGameModeText[0] = stringtranslate.translateKey("gameMode.survival");
		localizedGameModeText[1] = stringtranslate.translateKey("gameMode.creative");
		loadSaves();
		worldSlotContainer = new GuiWorldSlot(this);
		worldSlotContainer.registerScrollButtons(controlList, 4, 5);
		initButtons();
	}

	private void loadSaves() {
		ISaveFormat isaveformat = mc.getSaveLoader();
		saveList = isaveformat.getSaveList();
		Collections.sort(saveList);
		selectedWorld = -1;
	}

	protected String getSaveFileName(int par1) {
		return ((SaveFormatComparator)saveList.get(par1)).getFileName();
	}

	protected String getSaveName(int par1) {
		String s = ((SaveFormatComparator)saveList.get(par1)).getDisplayName();

		if (s == null || MathHelper.stringNullOrLengthZero(s)) {
			StringTranslate stringtranslate = StringTranslate.getInstance();
			s = (new StringBuilder()).append(stringtranslate.translateKey("selectWorld.world")).append(" ").append(par1 + 1).toString();
		}

		return s;
	}

	public void initButtons() {
		StringTranslate stringtranslate = StringTranslate.getInstance();
		controlList.add(buttonSelect = new GuiButton(1, width / 2 - 154, height - 52, 150, 20, stringtranslate.translateKey("selectWorld.select")));
		controlList.add(buttonDelete = new GuiButton(6, width / 2 - 154, height - 28, 70, 20, stringtranslate.translateKey("selectWorld.rename")));
		controlList.add(buttonRename = new GuiButton(2, width / 2 - 74, height - 28, 70, 20, stringtranslate.translateKey("selectWorld.delete")));
		controlList.add(new GuiButton(3, width / 2 + 4, height - 52, 150, 20, stringtranslate.translateKey("selectWorld.create")));
		controlList.add(new GuiButton(0, width / 2 + 4, height - 28, 150, 20, stringtranslate.translateKey("gui.cancel")));
		buttonSelect.enabled = false;
		buttonRename.enabled = false;
		buttonDelete.enabled = false;
	}

	protected void actionPerformed(GuiButton par1GuiButton) {
		if (!par1GuiButton.enabled) {
			return;
		}

		if (par1GuiButton.id == 2) {
			String s = getSaveName(selectedWorld);

			if (s != null) {
				deleting = true;
				StringTranslate stringtranslate = StringTranslate.getInstance();
				String s1 = stringtranslate.translateKey("selectWorld.deleteQuestion");
				String s2 = (new StringBuilder()).append("'").append(s).append("' ").append(stringtranslate.translateKey("selectWorld.deleteWarning")).toString();
				String s3 = stringtranslate.translateKey("selectWorld.deleteButton");
				String s4 = stringtranslate.translateKey("gui.cancel");
				GuiYesNo guiyesno = new GuiYesNo(this, s1, s2, s3, s4, selectedWorld);
				mc.displayGuiScreen(guiyesno);
			}
		} else if (par1GuiButton.id == 1) {
			selectWorld(selectedWorld);
		} else if (par1GuiButton.id == 3) {
			mc.displayGuiScreen(new GuiCreateWorld(this));
		} else if (par1GuiButton.id == 6) {
			mc.displayGuiScreen(new GuiRenameWorld(this, getSaveFileName(selectedWorld)));
		} else if (par1GuiButton.id == 0) {
			mc.displayGuiScreen(parentScreen);
		} else {
			worldSlotContainer.actionPerformed(par1GuiButton);
		}
	}

	public void selectWorld(int par1) {
		mc.displayGuiScreen(null);

		if (selected) {
			return;
		}

		selected = true;
		int i = ((SaveFormatComparator)saveList.get(par1)).getGameType();

		if (i == 0) {
			mc.playerController = new PlayerControllerSP(mc);
		} else {
			mc.playerController = new PlayerControllerCreative(mc);
		}

		String s = getSaveFileName(par1);

		if (s == null) {
			s = (new StringBuilder()).append("World").append(par1).toString();
		}

		mc.startWorld(s, getSaveName(par1), null);
		mc.displayGuiScreen(null);
	}

	public void deleteWorld(boolean par1, int par2) {
		if (deleting) {
			deleting = false;

			if (par1) {
				ISaveFormat isaveformat = mc.getSaveLoader();
				isaveformat.flushCache();
				isaveformat.deleteWorldDirectory(getSaveFileName(par2));
				loadSaves();
			}

			mc.displayGuiScreen(this);
		}
	}

	public void drawScreen(int par1, int par2, float par3) {
		worldSlotContainer.drawScreen(par1, par2, par3);
		drawCenteredString(fontRenderer, screenTitle, width / 2, 20, 0xffffff);
		super.drawScreen(par1, par2, par3);
	}

	static List getSize(GuiSelectWorld par0GuiSelectWorld) {
		return par0GuiSelectWorld.saveList;
	}

	static int onElementSelected(GuiSelectWorld par0GuiSelectWorld, int par1) {
		return par0GuiSelectWorld.selectedWorld = par1;
	}

	static int getSelectedWorld(GuiSelectWorld par0GuiSelectWorld) {
		return par0GuiSelectWorld.selectedWorld;
	}

	static GuiButton getSelectButton(GuiSelectWorld par0GuiSelectWorld) {
		return par0GuiSelectWorld.buttonSelect;
	}

	static GuiButton getRenameButton(GuiSelectWorld par0GuiSelectWorld) {
		return par0GuiSelectWorld.buttonRename;
	}

	static GuiButton getDeleteButton(GuiSelectWorld par0GuiSelectWorld) {
		return par0GuiSelectWorld.buttonDelete;
	}

	static String getLocalizedWorldName(GuiSelectWorld par0GuiSelectWorld) {
		return par0GuiSelectWorld.localizedWorldText;
	}

	static DateFormat getDateFormatter(GuiSelectWorld par0GuiSelectWorld) {
		return par0GuiSelectWorld.dateFormatter;
	}

	static String getLocalizedMustConvert(GuiSelectWorld par0GuiSelectWorld) {
		return par0GuiSelectWorld.localizedMustConvertText;
	}

	static String[] getLocalizedGameMode(GuiSelectWorld par0GuiSelectWorld) {
		return par0GuiSelectWorld.localizedGameModeText;
	}
}
