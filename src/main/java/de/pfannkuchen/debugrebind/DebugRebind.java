package de.pfannkuchen.debugrebind;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.ArrayUtils;
import org.lwjgl.glfw.GLFW;

import de.pfannkuchen.debugrebind.mixin.MixinKeyMapping;
import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;

/**
 * Initializes the mod on the client
 * @author Pancake
 */
public class DebugRebind implements ClientModInitializer {

	/**
	 * Key binding for opening and closing the debug menu
	 */
	public static KeyMapping debugtoggle = new KeyMapping("Secondary Debug Menu", GLFW.GLFW_KEY_F3, "F3 Rebinder");
	
	/**
	 * Map of pressed/non-pressed Keys.
	 */
	private static Map<KeyMapping, Boolean> keys = new HashMap<>();
	
	/**
	 * Categories for all key binds used.
	 */
	private static String[] keybindCategories = {"F3 Rebinder"};
	
	/*
	 * Initializes a whole lot of nothing
	 */
	@Override public void onInitializeClient() {}
	
	/**
	 * Initializes the key bind Manager, registers categories and key binds.
	 */
	public static KeyMapping[] onKeybindInitialize(KeyMapping[] keyMappings) {
		// Initialize Categories first
		final Map<String, Integer> categories = MixinKeyMapping.getCategorySorting();
		for (int i = 0; i < DebugRebind.keybindCategories.length; i++) categories.put(DebugRebind.keybindCategories[i], i+8);
		// Finish by adding Keybinds
		return ArrayUtils.addAll(keyMappings, DebugRebind.debugtoggle);
	}

	/**
	 * Checks whether a key has been pressed recently.
	 * @param mc Instance of minecraft
	 * @param map Key mappings to check
	 * @return Key has been pressed recently
	 */
	public static boolean isKeyDown(Minecraft mc, KeyMapping map) {
		boolean wasPressed = DebugRebind.keys.containsKey(map) ? DebugRebind.keys.get(map) : false;
		boolean isPressed = GLFW.glfwGetKey(mc.getWindow().getWindow(), ((MixinKeyMapping) map).getKey().getValue()) == GLFW.GLFW_PRESS;
		DebugRebind.keys.put(map, isPressed);
		return !wasPressed && isPressed;
	}

}
