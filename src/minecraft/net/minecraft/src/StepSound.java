package net.minecraft.src;

public class StepSound {
	public final String stepSoundName;
	public final float stepSoundVolume;
	public final float stepSoundPitch;

	public StepSound(String par1Str, float par2, float par3) {
		stepSoundName = par1Str;
		stepSoundVolume = par2;
		stepSoundPitch = par3;
	}

	public float getVolume() {
		return stepSoundVolume;
	}

	public float getPitch() {
		return stepSoundPitch;
	}

	public String getBreakSound() {
		return (new StringBuilder()).append("step.").append(stepSoundName).toString();
	}

	public String getStepSound() {
		return (new StringBuilder()).append("step.").append(stepSoundName).toString();
	}
}
