package net.minecraft.src;

public final class ProfilerResult implements Comparable {
	public double sectionPercentage;
	public double globalPercentage;
	public String name;

	public ProfilerResult(String par1Str, double par2, double par4) {
		name = par1Str;
		sectionPercentage = par2;
		globalPercentage = par4;
	}

	public int compareProfilerResult(ProfilerResult par1ProfilerResult) {
		if (par1ProfilerResult.sectionPercentage < sectionPercentage) {
			return -1;
		}

		if (par1ProfilerResult.sectionPercentage > sectionPercentage) {
			return 1;
		} else {
			return par1ProfilerResult.name.compareTo(name);
		}
	}

	public int getDisplayColor() {
		return (name.hashCode() & 0xaaaaaa) + 0x444444;
	}

	public int compareTo(Object par1Obj) {
		return compareProfilerResult((ProfilerResult)par1Obj);
	}
}
