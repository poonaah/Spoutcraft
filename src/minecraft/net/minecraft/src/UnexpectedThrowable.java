package net.minecraft.src;

public class UnexpectedThrowable {
	public final String description;
	public final Throwable exception;

	public UnexpectedThrowable(String par1Str, Throwable par2Throwable) {
		description = par1Str;
		exception = par2Throwable;
	}
}
