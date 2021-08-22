package dev.zihasz.anarchtilities.core;

import dev.zihasz.anarchtilities.Anarchtilities;
import net.minecraftforge.common.config.Config;

@Config(modid = Anarchtilities.MOD_ID)
public class ModConfig {

	@Config.Name("Name Protect")
	public static boolean name_protect = false;

	@Config.Name("Fake Name")
	@Config.Comment("The name for Name Protect")
	public static String name_protect_fake_name = "";

}
