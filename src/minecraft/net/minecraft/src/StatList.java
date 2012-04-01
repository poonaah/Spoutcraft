package net.minecraft.src;

import java.util.*;

public class StatList {
	protected static Map oneShotStats = new HashMap();
	public static List allStats = new ArrayList();
	public static List generalStats = new ArrayList();
	public static List itemStats = new ArrayList();
	public static List objectMineStats = new ArrayList();
	public static StatBase startGameStat = (new StatBasic(1000, "stat.startGame")).initIndependentStat().registerStat();
	public static StatBase createWorldStat = (new StatBasic(1001, "stat.createWorld")).initIndependentStat().registerStat();
	public static StatBase loadWorldStat = (new StatBasic(1002, "stat.loadWorld")).initIndependentStat().registerStat();
	public static StatBase joinMultiplayerStat = (new StatBasic(1003, "stat.joinMultiplayer")).initIndependentStat().registerStat();
	public static StatBase leaveGameStat = (new StatBasic(1004, "stat.leaveGame")).initIndependentStat().registerStat();
	public static StatBase minutesPlayedStat;
	public static StatBase distanceWalkedStat;
	public static StatBase distanceSwumStat;
	public static StatBase distanceFallenStat;
	public static StatBase distanceClimbedStat;
	public static StatBase distanceFlownStat;
	public static StatBase distanceDoveStat;
	public static StatBase distanceByMinecartStat;
	public static StatBase distanceByBoatStat;
	public static StatBase distanceByPigStat;
	public static StatBase jumpStat = (new StatBasic(2010, "stat.jump")).initIndependentStat().registerStat();
	public static StatBase dropStat = (new StatBasic(2011, "stat.drop")).initIndependentStat().registerStat();
	public static StatBase damageDealtStat = (new StatBasic(2020, "stat.damageDealt")).registerStat();
	public static StatBase damageTakenStat = (new StatBasic(2021, "stat.damageTaken")).registerStat();
	public static StatBase deathsStat = (new StatBasic(2022, "stat.deaths")).registerStat();
	public static StatBase mobKillsStat = (new StatBasic(2023, "stat.mobKills")).registerStat();
	public static StatBase playerKillsStat = (new StatBasic(2024, "stat.playerKills")).registerStat();
	public static StatBase fishCaughtStat = (new StatBasic(2025, "stat.fishCaught")).registerStat();
	public static StatBase mineBlockStatArray[] = initMinableStats("stat.mineBlock", 0x1000000);
	public static StatBase objectCraftStats[];
	public static StatBase objectUseStats[];
	public static StatBase objectBreakStats[];
	private static boolean blockStatsInitialized = false;
	private static boolean itemStatsInitialized = false;

	public StatList() {
	}

	public static void func_27360_a() {
	}

	public static void initBreakableStats() {
		objectUseStats = initUsableStats(objectUseStats, "stat.useItem", 0x1020000, 0, 256);
		objectBreakStats = initBreakStats(objectBreakStats, "stat.breakItem", 0x1030000, 0, 256);
		blockStatsInitialized = true;
		initCraftableStats();
	}

	public static void initStats() {
		objectUseStats = initUsableStats(objectUseStats, "stat.useItem", 0x1020000, 256, 32000);
		objectBreakStats = initBreakStats(objectBreakStats, "stat.breakItem", 0x1030000, 256, 32000);
		itemStatsInitialized = true;
		initCraftableStats();
	}

	public static void initCraftableStats() {
		if (!blockStatsInitialized || !itemStatsInitialized) {
			return;
		}

		HashSet hashset = new HashSet();
		IRecipe irecipe;

		for (Iterator iterator = CraftingManager.getInstance().getRecipeList().iterator(); iterator.hasNext(); hashset.add(Integer.valueOf(irecipe.getRecipeOutput().itemID))) {
			irecipe = (IRecipe)iterator.next();
		}

		ItemStack itemstack;

		for (Iterator iterator1 = FurnaceRecipes.smelting().getSmeltingList().values().iterator(); iterator1.hasNext(); hashset.add(Integer.valueOf(itemstack.itemID))) {
			itemstack = (ItemStack)iterator1.next();
		}

		objectCraftStats = new StatBase[32000];
		Iterator iterator2 = hashset.iterator();

		do {
			if (!iterator2.hasNext()) {
				break;
			}

			Integer integer = (Integer)iterator2.next();

			if (Item.itemsList[integer.intValue()] != null) {
				String s = StatCollector.translateToLocalFormatted("stat.craftItem", new Object[] {
							Item.itemsList[integer.intValue()].getStatName()
						});
				objectCraftStats[integer.intValue()] = (new StatCrafting(0x1010000 + integer.intValue(), s, integer.intValue())).registerStat();
			}
		} while (true);

		replaceAllSimilarBlocks(objectCraftStats);
	}

	private static StatBase[] initMinableStats(String par0Str, int par1) {
		StatBase astatbase[] = new StatBase[256];

		for (int i = 0; i < 256; i++) {
			if (Block.blocksList[i] != null && Block.blocksList[i].getEnableStats()) {
				String s = StatCollector.translateToLocalFormatted(par0Str, new Object[] {
							Block.blocksList[i].translateBlockName()
						});
				astatbase[i] = (new StatCrafting(par1 + i, s, i)).registerStat();
				objectMineStats.add((StatCrafting)astatbase[i]);
			}
		}

		replaceAllSimilarBlocks(astatbase);
		return astatbase;
	}

	private static StatBase[] initUsableStats(StatBase par0ArrayOfStatBase[], String par1Str, int par2, int par3, int par4) {
		if (par0ArrayOfStatBase == null) {
			par0ArrayOfStatBase = new StatBase[32000];
		}

		for (int i = par3; i < par4; i++) {
			if (Item.itemsList[i] == null) {
				continue;
			}

			String s = StatCollector.translateToLocalFormatted(par1Str, new Object[] {
						Item.itemsList[i].getStatName()
					});
			par0ArrayOfStatBase[i] = (new StatCrafting(par2 + i, s, i)).registerStat();

			if (i >= 256) {
				itemStats.add((StatCrafting)par0ArrayOfStatBase[i]);
			}
		}

		replaceAllSimilarBlocks(par0ArrayOfStatBase);
		return par0ArrayOfStatBase;
	}

	private static StatBase[] initBreakStats(StatBase par0ArrayOfStatBase[], String par1Str, int par2, int par3, int par4) {
		if (par0ArrayOfStatBase == null) {
			par0ArrayOfStatBase = new StatBase[32000];
		}

		for (int i = par3; i < par4; i++) {
			if (Item.itemsList[i] != null && Item.itemsList[i].isDamageable()) {
				String s = StatCollector.translateToLocalFormatted(par1Str, new Object[] {
							Item.itemsList[i].getStatName()
						});
				par0ArrayOfStatBase[i] = (new StatCrafting(par2 + i, s, i)).registerStat();
			}
		}

		replaceAllSimilarBlocks(par0ArrayOfStatBase);
		return par0ArrayOfStatBase;
	}

	private static void replaceAllSimilarBlocks(StatBase par0ArrayOfStatBase[]) {
		replaceSimilarBlocks(par0ArrayOfStatBase, Block.waterStill.blockID, Block.waterMoving.blockID);
		replaceSimilarBlocks(par0ArrayOfStatBase, Block.lavaStill.blockID, Block.lavaStill.blockID);
		replaceSimilarBlocks(par0ArrayOfStatBase, Block.pumpkinLantern.blockID, Block.pumpkin.blockID);
		replaceSimilarBlocks(par0ArrayOfStatBase, Block.stoneOvenActive.blockID, Block.stoneOvenIdle.blockID);
		replaceSimilarBlocks(par0ArrayOfStatBase, Block.oreRedstoneGlowing.blockID, Block.oreRedstone.blockID);
		replaceSimilarBlocks(par0ArrayOfStatBase, Block.redstoneRepeaterActive.blockID, Block.redstoneRepeaterIdle.blockID);
		replaceSimilarBlocks(par0ArrayOfStatBase, Block.torchRedstoneActive.blockID, Block.torchRedstoneIdle.blockID);
		replaceSimilarBlocks(par0ArrayOfStatBase, Block.mushroomRed.blockID, Block.mushroomBrown.blockID);
		replaceSimilarBlocks(par0ArrayOfStatBase, Block.stairDouble.blockID, Block.stairSingle.blockID);
		replaceSimilarBlocks(par0ArrayOfStatBase, Block.grass.blockID, Block.dirt.blockID);
		replaceSimilarBlocks(par0ArrayOfStatBase, Block.tilledField.blockID, Block.dirt.blockID);
	}

	private static void replaceSimilarBlocks(StatBase par0ArrayOfStatBase[], int par1, int par2) {
		if (par0ArrayOfStatBase[par1] != null && par0ArrayOfStatBase[par2] == null) {
			par0ArrayOfStatBase[par2] = par0ArrayOfStatBase[par1];
			return;
		} else {
			allStats.remove(par0ArrayOfStatBase[par1]);
			objectMineStats.remove(par0ArrayOfStatBase[par1]);
			generalStats.remove(par0ArrayOfStatBase[par1]);
			par0ArrayOfStatBase[par1] = par0ArrayOfStatBase[par2];
			return;
		}
	}

	public static StatBase getOneShotStat(int par0) {
		return (StatBase)oneShotStats.get(Integer.valueOf(par0));
	}

	static {
		minutesPlayedStat = (new StatBasic(1100, "stat.playOneMinute", StatBase.timeStatType)).initIndependentStat().registerStat();
		distanceWalkedStat = (new StatBasic(2000, "stat.walkOneCm", StatBase.distanceStatType)).initIndependentStat().registerStat();
		distanceSwumStat = (new StatBasic(2001, "stat.swimOneCm", StatBase.distanceStatType)).initIndependentStat().registerStat();
		distanceFallenStat = (new StatBasic(2002, "stat.fallOneCm", StatBase.distanceStatType)).initIndependentStat().registerStat();
		distanceClimbedStat = (new StatBasic(2003, "stat.climbOneCm", StatBase.distanceStatType)).initIndependentStat().registerStat();
		distanceFlownStat = (new StatBasic(2004, "stat.flyOneCm", StatBase.distanceStatType)).initIndependentStat().registerStat();
		distanceDoveStat = (new StatBasic(2005, "stat.diveOneCm", StatBase.distanceStatType)).initIndependentStat().registerStat();
		distanceByMinecartStat = (new StatBasic(2006, "stat.minecartOneCm", StatBase.distanceStatType)).initIndependentStat().registerStat();
		distanceByBoatStat = (new StatBasic(2007, "stat.boatOneCm", StatBase.distanceStatType)).initIndependentStat().registerStat();
		distanceByPigStat = (new StatBasic(2008, "stat.pigOneCm", StatBase.distanceStatType)).initIndependentStat().registerStat();
		AchievementList.init();
	}
}