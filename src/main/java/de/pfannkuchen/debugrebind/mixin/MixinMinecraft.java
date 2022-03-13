package de.pfannkuchen.debugrebind.mixin;

import org.lwjgl.glfw.GLFW;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import de.pfannkuchen.debugrebind.DebugRebind;
import net.minecraft.client.Minecraft;
import net.minecraft.client.Options;
import net.minecraft.client.gui.screens.Screen;

/**
 * This mixin redirects the F3 debug screen to a keybind
 * @author Pancake
 */
@Mixin(Minecraft.class)
public class MixinMinecraft {

	@Shadow
	private Options options;
	@Unique
	private boolean wasPressedBefore;
	
	@Inject(method = "runTick", at = @At("HEAD"))
	public void onRunTick(CallbackInfo ci) {
		if (((MixinKeyMapping) DebugRebind.debugtoggle).getKey().getValue() == GLFW.GLFW_KEY_F3)
			return;
		boolean isDown = DebugRebind.isKeyDown((Minecraft) (Object) this, DebugRebind.debugtoggle);
		if (isDown && !this.wasPressedBefore) {
            this.options.renderDebug = !this.options.renderDebug;
            this.options.renderDebugCharts = this.options.renderDebug && Screen.hasShiftDown();
            this.options.renderFpsChart = this.options.renderDebug && Screen.hasAltDown();
		}
		this.wasPressedBefore = isDown;
	}
	
}
