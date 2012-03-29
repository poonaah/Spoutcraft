package net.minecraft.src;

import java.util.Random;

public class EnchantmentNameParts {
	public static final EnchantmentNameParts instance = new EnchantmentNameParts();
	private Random rand;
	private String wordList[];

	private EnchantmentNameParts() {
		rand = new Random();
		wordList = "the elder scrolls klaatu berata niktu xyzzy bless curse light darkness fire air earth water hot dry cold wet ignite snuff embiggen twist shorten stretch fiddle destroy imbue galvanize enchant free limited range of towards inside sphere cube self other ball mental physical grow shrink demon elemental spirit animal creature beast humanoid undead fresh stale ".split(" ");
	}

	public String generateRandomEnchantName() {
		int i = rand.nextInt(2) + 3;
		String s = "";

		for (int j = 0; j < i; j++) {
			if (j > 0) {
				s = (new StringBuilder()).append(s).append(" ").toString();
			}

			s = (new StringBuilder()).append(s).append(wordList[rand.nextInt(wordList.length)]).toString();
		}

		return s;
	}

	public void setRandSeed(long par1) {
		rand.setSeed(par1);
	}
}
