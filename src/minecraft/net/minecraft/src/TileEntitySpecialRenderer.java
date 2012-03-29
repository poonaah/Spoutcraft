package net.minecraft.src;

public abstract class TileEntitySpecialRenderer {
	protected TileEntityRenderer tileEntityRenderer;

	public TileEntitySpecialRenderer() {
	}

	public abstract void renderTileEntityAt(TileEntity tileentity, double d, double d1, double d2, float f);

	protected void bindTextureByName(String par1Str) {
		RenderEngine renderengine = tileEntityRenderer.renderEngine;

		if (renderengine != null) {
			renderengine.bindTexture(renderengine.getTexture(par1Str));
		}
	}

	public void setTileEntityRenderer(TileEntityRenderer par1TileEntityRenderer) {
		tileEntityRenderer = par1TileEntityRenderer;
	}

	public void cacheSpecialRenderInfo(World world) {
	}

	public FontRenderer getFontRenderer() {
		return tileEntityRenderer.getFontRenderer();
	}
}
