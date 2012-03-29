package net.minecraft.src;

import java.util.ArrayList;
import java.util.Random;

public class ComponentVillageStartPiece extends ComponentVillageWell {
	public WorldChunkManager worldChunkMngr;
	public int terrainType;
	public StructureVillagePieceWeight structVillagePieceWeight;
	public ArrayList structureVillageWeightedPieceList;
	public ArrayList field_35108_e;
	public ArrayList field_35106_f;

	public ComponentVillageStartPiece(WorldChunkManager par1WorldChunkManager, int par2, Random par3Random, int par4, int par5, ArrayList par6ArrayList, int par7) {
		super(0, par3Random, par4, par5);
		field_35108_e = new ArrayList();
		field_35106_f = new ArrayList();
		worldChunkMngr = par1WorldChunkManager;
		structureVillageWeightedPieceList = par6ArrayList;
		terrainType = par7;
	}

	public WorldChunkManager getWorldChunkManager() {
		return worldChunkMngr;
	}
}
