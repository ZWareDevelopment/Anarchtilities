package dev.zihasz.anarchtilities.core.mixin;

import de.enzaxd.viaforge.ViaForge;
import de.enzaxd.viaforge.gui.GuiProtocolSelector;
import de.enzaxd.viaforge.protocols.ProtocolCollection;
import net.minecraft.client.gui.*;
import net.minecraft.client.resources.I18n;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GuiMainMenu.class)
public abstract class MixinGuiMainMenu extends GuiScreen {

	@Shadow
	private GuiButton modButton;

	/**
	 * @author Zihasz
	 */
	@Overwrite
	public void addSingleplayerMultiplayerButtons(int p_73969_1_, int p_73969_2_) {
		this.buttonList.add(new GuiButton(1, this.width / 2 - 100, p_73969_1_, I18n.format("menu.singleplayer")));
		this.buttonList.add(new GuiButton(2, this.width / 2 - 100, p_73969_1_ + p_73969_2_, I18n.format("menu.multiplayer")));
		this.buttonList.add(this.modButton = new GuiButton(6, this.width / 2 - 100, p_73969_1_ + p_73969_2_ * 2, 98, 20, I18n.format("fml.menu.mods")));
		this.buttonList.add(new GuiButton(101, this.width / 2 + 2, p_73969_1_ + p_73969_2_ * 2, 98, 20, "Alt Manager"));
		this.buttonList.add(new GuiButton(102, 5, 6, 98, 20, ProtocolCollection.getProtocolById(ViaForge.getInstance().getVersion()).getName()));
	}

	@Inject(method = "actionPerformed(Lnet/minecraft/client/gui/GuiButton;)V", at = @At("TAIL"))
	public void actionPerformed(GuiButton button, CallbackInfo ci) {
		if (button.id == 101) {
			// TODO: Display actual AltManager screen
			mc.displayGuiScreen(new GuiMultiplayer(this));
		}
		if (button.id == 102) {
			mc.displayGuiScreen(new GuiProtocolSelector(this));
		}
	}

}
