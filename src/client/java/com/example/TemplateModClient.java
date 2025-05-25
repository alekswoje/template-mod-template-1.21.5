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

public class TemplateModClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		// This entrypoint is suitable for setting up client-specific logic, such as rendering.
		ClientCommandRegistrationCallback.EVENT.register((dispatcher, registryAccess) -> {
			dispatcher.register(literal("templateconfig")
				.executes(context -> {
					MinecraftClient.getInstance().execute(() -> openConfigScreen(MinecraftClient.getInstance()));
					return 1;
				})
			);
		});
	}

	public static Screen createConfigScreen(Screen parent) {
		ConfigBuilder builder = ConfigBuilder.create()
			.setParentScreen(parent)
			.setTitle(net.minecraft.text.Text.literal("Template Mod Config"));
		ConfigCategory category = builder.getOrCreateCategory(net.minecraft.text.Text.literal("General"));
		ConfigEntryBuilder entryBuilder = ConfigEntryBuilder.create();

		category.addEntry(entryBuilder.startBooleanToggle(net.minecraft.text.Text.literal("Example Option"), true)
			.setDefaultValue(true)
			.setTooltip(net.minecraft.text.Text.literal("This is an example config option."))
			.setSaveConsumer(newValue -> {
				// Save logic here
			})
			.build());

		builder.setSavingRunnable(() -> {
			// Save config to file here
		});

		return builder.build();
	}

	public static void openConfigScreen(MinecraftClient client) {
		client.setScreen(createConfigScreen(client.currentScreen));
	}
}