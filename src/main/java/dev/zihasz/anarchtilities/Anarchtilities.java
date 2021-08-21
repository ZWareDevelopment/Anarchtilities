package dev.zihasz.anarchtilities;

import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.Mod;

@Mod(
		modid = Anarchtilities.MOD_ID,
		name = Anarchtilities.MOD_NAME,
		version = Anarchtilities.VERSION
)
public class Anarchtilities {

	public static final String MOD_ID = "Anarchtilities";
	public static final String MOD_NAME = "Anarchtilities";
	public static final String VERSION = "1.0.0";


	@Mod.Instance(MOD_ID)
	public static Anarchtilities INSTANCE;


	@Mod.EventHandler
	public void preInit(FMLPreInitializationEvent event) {

	}

	@Mod.EventHandler
	public void init(FMLInitializationEvent event) {

	}


	@Mod.EventHandler
	public void postInit(FMLPostInitializationEvent event) {

	}

}
