package com.example;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TemplateModConfigScreen extends Screen {
    private static final Logger LOGGER = LoggerFactory.getLogger("template-mod-config-screen");
    private final Screen parent;
    private ButtonWidget closeButton;

    public TemplateModConfigScreen(Screen parent) {
        super(Text.literal("Template Mod Config"));
        this.parent = parent;
        LOGGER.info("TemplateModConfigScreen constructor called with parent: {}", parent);
        System.out.println("[TemplateModConfigScreen] Constructor called with parent: " + parent);
    }

    @Override
    protected void init() {
        LOGGER.info("init() called");
        System.out.println("[TemplateModConfigScreen] init() called");
        closeButton = ButtonWidget.builder(Text.literal("Close"), button -> {
            this.close();
        })
        .dimensions(this.width / 2 - 100, this.height / 2, 200, 20)
        .build();
        addDrawableChild(closeButton);
    }

    @Override
    public void close() {
        LOGGER.info("close() called");
        System.out.println("[TemplateModConfigScreen] close() called");
        this.client.setScreen(parent);
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        LOGGER.info("render() called");
        System.out.println("[TemplateModConfigScreen] render() called");
        this.renderBackground(context, mouseX, mouseY, delta);
        context.drawCenteredTextWithShadow(this.textRenderer, this.title, this.width / 2, 40, 0xFFFFFF);
        super.render(context, mouseX, mouseY, delta);
    }
} 