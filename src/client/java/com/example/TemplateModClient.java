package com.example;

import net.fabricmc.api.ClientModInitializer;
import me.shedaniel.clothconfig2.api.ConfigBuilder;
import me.shedaniel.clothconfig2.api.ConfigCategory;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import com.mojang.brigadier.arguments.StringArgumentType;
import static net.fabricmc.fabric.api.client.command.v2.ClientCommandManager.literal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;

public class TemplateModClient implements ClientModInitializer {
	private static final Logger LOGGER = LoggerFactory.getLogger("template-mod-client");
	private static KeyBinding openConfigKeyBinding;
	private static int scheduledOpenTicks = -1;

	@Override
	public void onInitializeClient() {
		LOGGER.info("onInitializeClient called");
		// Register keybind
		openConfigKeyBinding = KeyBindingHelper.registerKeyBinding(new KeyBinding(
			"key.template_mod.open_config",
			InputUtil.Type.KEYSYM,
			GLFW.GLFW_KEY_K,
			"category.template_mod"
		));
		ClientTickEvents.END_CLIENT_TICK.register(client -> {
			if (openConfigKeyBinding.wasPressed()) {
				LOGGER.info("Keybind pressed, opening config screen");
				openConfigScreen(client);
			}
			if (scheduledOpenTicks >= 0) {
				scheduledOpenTicks--;
				if (scheduledOpenTicks == 0) {
					LOGGER.info("Scheduled openConfigScreen from command");
					openConfigScreen(client);
				}
			}
		});
		ClientCommandRegistrationCallback.EVENT.register((dispatcher, registryAccess) -> {
			LOGGER.info("Registering /templateconfig command");
			dispatcher.register(literal("templateconfig")
				.executes(context -> {
					LOGGER.info("/templateconfig command executed");
					MinecraftClient client = MinecraftClient.getInstance();
					if (client == null) {
						LOGGER.error("MinecraftClient.getInstance() is null!");
						return 0;
					}
					// Schedule the screen to open in 5 ticks
					LOGGER.info("Scheduling config screen to open in 5 ticks");
					scheduledOpenTicks = 5;
					return 1;
				})
			);
		});
	}

	public static Screen createConfigScreen(Screen parent) {
		LOGGER.info("createConfigScreen called with parent: {}", parent);
		return new TemplateModConfigScreen(parent);
	}

	public static void openConfigScreen(MinecraftClient client) {
		LOGGER.info("openConfigScreen called");
		client.setScreen(createConfigScreen(null));
	}
}