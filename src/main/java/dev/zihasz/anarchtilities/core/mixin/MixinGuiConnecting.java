package dev.zihasz.anarchtilities.core.mixin;

import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.multiplayer.GuiConnecting;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.client.resources.I18n;
import net.minecraft.network.NetworkManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(GuiConnecting.class)
public class MixinGuiConnecting extends GuiScreen {

	@Shadow
	private NetworkManager networkManager;

	public MixinGuiConnecting() {
		super();
	}

	/**
	 * @author Zihasz
	 */
	@Overwrite
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		this.drawDefaultBackground();

		String host = "unknown";
		String ping = "unknown";
		ServerData data = mc.getCurrentServerData();

		if (data != null && data.serverIP != null)
			host = data.serverIP;


		if (this.networkManager == null) {
			this.drawCenteredString(this.fontRenderer, String.format("Connecting to %s...", host), this.width / 2, this.height / 2 - 50, 16777215);
		} else {
			this.drawCenteredString(this.fontRenderer, String.format("Logging in to %s...", host), this.width / 2, this.height / 2 - 50, 16777215);
		}

		this.drawCenteredString(this.fontRenderer, String.format("Ping to %s: %s", host, ping), this.width / 2, this.height / 2 - 40, 16777215);

		super.drawScreen(mouseX, mouseY, partialTicks);
	}

}
