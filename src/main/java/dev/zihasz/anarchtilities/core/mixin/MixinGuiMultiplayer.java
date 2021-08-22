package dev.zihasz.anarchtilities.core.mixin;

import de.enzaxd.viaforge.gui.GuiProtocolSelector;
import dev.zihasz.anarchtilities.ui.thealtening.GuiTheAltening;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiMultiplayer;
import net.minecraft.client.gui.GuiScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GuiMultiplayer.class)
public abstract class MixinGuiMultiplayer extends GuiScreen {

	@Inject(method = "initGui", at = @At("RETURN"))
	public void initGui(CallbackInfo callbackInfo) {
		this.buttonList.add(new GuiButton(101, 7, 7, 100, 20, "ViaForge"));
		this.buttonList.add(new GuiButton(102, 114, 7, 100, 20, "TheAltening"));
		this.buttonList.add(new GuiButton(103, this.width - 107, 7, 100, 20, "PingBypass"));
		this.buttonList.add(new GuiButton(104, this.width - 214, 7, 100, 20, "Proxy / VPN"));
	}

	@Inject(method = "actionPerformed", at = @At("RETURN"))
	protected void actionPerformed(GuiButton button, CallbackInfo callbackInfo) {
		if (button.id == 101) mc.displayGuiScreen(new GuiProtocolSelector(this));
		if (button.id == 102) mc.displayGuiScreen(new GuiTheAltening(this));
		if (button.id == 103) mc.displayGuiScreen(new GuiMultiplayer(this));
		if (button.id == 104) mc.displayGuiScreen(new GuiMultiplayer(this));
	}

}
