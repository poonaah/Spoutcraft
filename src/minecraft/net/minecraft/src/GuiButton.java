package net.minecraft.src;

import net.minecraft.client.Minecraft;
import org.lwjgl.opengl.GL11;

public class GuiButton extends Gui {
	protected int width;
	protected int height;
	public int xPosition;
	public int yPosition;
	public String displayString;
	public int id;
	public boolean enabled;
	public boolean drawButton;

	public GuiButton(int par1, int par2, int par3, String par4Str) {
		this(par1, par2, par3, 200, 20, par4Str);
	}

	public GuiButton(int par1, int par2, int par3, int par4, int par5, String par6Str) {
		width = 200;
		height = 20;
		enabled = true;
		drawButton = true;
		id = par1;
		xPosition = par2;
		yPosition = par3;
		width = par4;
		height = par5;
		displayString = par6Str;
	}

	protected int getHoverState(boolean par1) {
		byte byte0 = 1;

		if (!enabled) {
			byte0 = 0;
		} else if (par1) {
			byte0 = 2;
		}

		return byte0;
	}

	public void drawButton(Minecraft par1Minecraft, int par2, int par3) {
		if (!drawButton) {
			return;
		}

		FontRenderer fontrenderer = par1Minecraft.fontRenderer;
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, par1Minecraft.renderEngine.getTexture("/gui/gui.png"));
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		boolean flag = par2 >= xPosition && par3 >= yPosition && par2 < xPosition + width && par3 < yPosition + height;
		int i = getHoverState(flag);
		drawTexturedModalRect(xPosition, yPosition, 0, 46 + i * 20, width / 2, height);
		drawTexturedModalRect(xPosition + width / 2, yPosition, 200 - width / 2, 46 + i * 20, width / 2, height);
		mouseDragged(par1Minecraft, par2, par3);

		if (!enabled) {
			drawCenteredString(fontrenderer, displayString, xPosition + width / 2, yPosition + (height - 8) / 2, 0xffa0a0a0);
		} else if (flag) {
			drawCenteredString(fontrenderer, displayString, xPosition + width / 2, yPosition + (height - 8) / 2, 0xffffa0);
		} else {
			drawCenteredString(fontrenderer, displayString, xPosition + width / 2, yPosition + (height - 8) / 2, 0xe0e0e0);
		}
	}

	protected void mouseDragged(Minecraft minecraft, int i, int j) {
	}

	public void mouseReleased(int i, int j) {
	}

	public boolean mousePressed(Minecraft par1Minecraft, int par2, int par3) {
		return enabled && drawButton && par2 >= xPosition && par3 >= yPosition && par2 < xPosition + width && par3 < yPosition + height;
	}
}
