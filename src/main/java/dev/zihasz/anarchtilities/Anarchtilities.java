package dev.zihasz.anarchtilities;

import dev.tigr.clantags.api.ClanTags;
import dev.tigr.emojiapi.Emojis;
import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.client.event.ClientChatEvent;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

@Mod(
		modid = Anarchtilities.MOD_ID,
		name = Anarchtilities.MOD_NAME,
		version = Anarchtilities.VERSION,
		clientSideOnly = true,
		useMetadata = true,
		updateJSON = "https://anarchtilities.zihasz.dev/update/update.json"
)
public class Anarchtilities {

	public static final String MOD_ID = "anarchtilities";
	public static final String MOD_NAME = "Anarchtilities";
	public static final String VERSION = "1.0.0";

	@Mod.Instance(MOD_ID)
	public static Anarchtilities INSTANCE;

	@Mod.EventHandler
	public void preInit(FMLPreInitializationEvent event) {

	}

	@Mod.EventHandler
	public void init(FMLInitializationEvent event) {
		MinecraftForge.EVENT_BUS.register(this);

		try {
			Method method = Emojis.class.getDeclaredMethod("load");
			method.setAccessible(true);
			method.invoke(null);
		} catch (IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
			e.printStackTrace();
		}

		ClanTags.load();
		ClanTags.PREFIX = "/";
		ClientCommandHandler.instance.registerCommand(ClanTags.INFO_COMMAND);
		ClientCommandHandler.instance.registerCommand(ClanTags.CLANS_COMMAND);

	}


	@Mod.EventHandler
	public void postInit(FMLPostInitializationEvent event) {

	}

	@SubscribeEvent
	public void onChatMessage(ClientChatReceivedEvent event) {
		ClanTags.handleClientChatReceivedEvent(event);
	}

}
