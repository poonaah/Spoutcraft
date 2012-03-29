package net.minecraft.src;

import java.util.*;

public class WeightedRandom {
	public WeightedRandom() {
	}

	public static int getTotalWeight(Collection par0Collection) {
		int i = 0;

		for (Iterator iterator = par0Collection.iterator(); iterator.hasNext();) {
			WeightedRandomChoice weightedrandomchoice = (WeightedRandomChoice)iterator.next();
			i += weightedrandomchoice.itemWeight;
		}

		return i;
	}

	public static WeightedRandomChoice getRandomItem(Random par0Random, Collection par1Collection, int par2) {
		if (par2 <= 0) {
			throw new IllegalArgumentException();
		}

		int i = par0Random.nextInt(par2);

		for (Iterator iterator = par1Collection.iterator(); iterator.hasNext();) {
			WeightedRandomChoice weightedrandomchoice = (WeightedRandomChoice)iterator.next();
			i -= weightedrandomchoice.itemWeight;

			if (i < 0) {
				return weightedrandomchoice;
			}
		}

		return null;
	}

	public static WeightedRandomChoice getRandomItem(Random par0Random, Collection par1Collection) {
		return getRandomItem(par0Random, par1Collection, getTotalWeight(par1Collection));
	}

	public static int getTotalWeight(WeightedRandomChoice par0ArrayOfWeightedRandomChoice[]) {
		int i = 0;
		WeightedRandomChoice aweightedrandomchoice[] = par0ArrayOfWeightedRandomChoice;
		int j = aweightedrandomchoice.length;

		for (int k = 0; k < j; k++) {
			WeightedRandomChoice weightedrandomchoice = aweightedrandomchoice[k];
			i += weightedrandomchoice.itemWeight;
		}

		return i;
	}

	public static WeightedRandomChoice getRandomItem(Random par0Random, WeightedRandomChoice par1ArrayOfWeightedRandomChoice[], int par2) {
		if (par2 <= 0) {
			throw new IllegalArgumentException();
		}

		int i = par0Random.nextInt(par2);
		WeightedRandomChoice aweightedrandomchoice[] = par1ArrayOfWeightedRandomChoice;
		int j = aweightedrandomchoice.length;

		for (int k = 0; k < j; k++) {
			WeightedRandomChoice weightedrandomchoice = aweightedrandomchoice[k];
			i -= weightedrandomchoice.itemWeight;

			if (i < 0) {
				return weightedrandomchoice;
			}
		}

		return null;
	}

	public static WeightedRandomChoice getRandomItem(Random par0Random, WeightedRandomChoice par1ArrayOfWeightedRandomChoice[]) {
		return getRandomItem(par0Random, par1ArrayOfWeightedRandomChoice, getTotalWeight(par1ArrayOfWeightedRandomChoice));
	}
}
