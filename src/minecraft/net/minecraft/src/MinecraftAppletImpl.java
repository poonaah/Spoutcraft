package net.minecraft.src;

import java.awt.*;
import net.minecraft.client.Minecraft;
import net.minecraft.client.MinecraftApplet;

public class MinecraftAppletImpl extends Minecraft {
	final MinecraftApplet mainFrame;

	public MinecraftAppletImpl(MinecraftApplet par1MinecraftApplet, Component par2Component, Canvas par3Canvas, MinecraftApplet par4MinecraftApplet, int par5, int par6, boolean par7) {
		super(par2Component, par3Canvas, par4MinecraftApplet, par5, par6, par7);
		mainFrame = par1MinecraftApplet;
	}

	public void displayUnexpectedThrowable(UnexpectedThrowable par1UnexpectedThrowable) {
		mainFrame.removeAll();
		mainFrame.setLayout(new BorderLayout());
		mainFrame.add(new PanelCrashReport(par1UnexpectedThrowable), "Center");
		mainFrame.validate();
	}
}
