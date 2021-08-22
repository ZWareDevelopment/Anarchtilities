package dev.zihasz.anarchtilities.core;

import dev.zihasz.anarchtilities.Anarchtilities;
import net.minecraftforge.fml.relauncher.IFMLLoadingPlugin;
import org.spongepowered.asm.launch.MixinBootstrap;
import org.spongepowered.asm.mixin.Mixins;

import javax.annotation.Nullable;
import java.util.Map;

@IFMLLoadingPlugin.Name(Anarchtilities.MOD_ID)
@IFMLLoadingPlugin.MCVersion("1.12.2")
public class Loader implements IFMLLoadingPlugin {

	public Loader() {
		MixinBootstrap.init(); // Init mixins
		Mixins.addConfiguration("mixins.anarchtilities.json");  // Add our own mixins
	}

	@Override
	public String[] getASMTransformerClass() {
		return new String[0];
	}

	@Override
	public String getModContainerClass() {
		return null;
	}

	@Nullable
	@Override
	public String getSetupClass() {
		return null;
	}

	@Override
	public void injectData(Map<String, Object> data) {

	}

	@Override
	public String getAccessTransformerClass() {
		return null;
	}
}
