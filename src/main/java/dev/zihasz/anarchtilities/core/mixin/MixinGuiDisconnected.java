package dev.zihasz.anarchtilities.core.mixin;

import de.enzaxd.viaforge.ViaForge;
import de.enzaxd.viaforge.gui.GuiProtocolSelector;
import de.enzaxd.viaforge.protocols.ProtocolCollection;
import net.minecraft.client.gui.*;
import net.minecraft.client.multiplayer.GuiConnecting;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.client.resources.I18n;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GuiDisconnected.class)
public abstract class MixinGuiDisconnected extends GuiScreen {

	@Shadow
	private int textHeight;

	@Inject(method = "initGui", at = @At("RETURN"))
	public void injectInitGui(CallbackInfo ci) {
		buttonList.add(new GuiButton(101, 5, 6, 98, 20, ProtocolCollection.getProtocolById(ViaForge.getInstance().getVersion()).getName()));
		this.buttonList.add(new GuiButton(102, this.width / 2 - 100, Math.min(this.height / 2 + this.textHeight / 2 + this.fontRenderer.FONT_HEIGHT, this.height - 30) + 25, "Reconnect"));
	}

	@Inject(method = "actionPerformed", at = @At("RETURN"))
	public void injectActionPerformed(GuiButton button, CallbackInfo ci) {
		if (button.id == 101)
			mc.displayGuiScreen(new GuiProtocolSelector(this));
		if (button.id == 102)
			mc.displayGuiScreen(new GuiConnecting(new GuiMultiplayer(new GuiMainMenu()), mc, new ServerData(ViaForge.getInstance().getLastServer(), ViaForge.getInstance().getLastServer(), false)));
	}

}
