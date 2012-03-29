package net.minecraft.src;

public class ChatLine {
	public String message;
	public int updateCounter;

	public ChatLine(String par1Str) {
		message = par1Str;
		updateCounter = 0;
	}
}
