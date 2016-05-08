package com.bigxplosion.shieldhider;

import java.io.File;

import net.minecraft.client.renderer.ItemMeshDefinition;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

import net.minecraftforge.client.event.RenderHandEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;

@Mod(modid = "ShieldHider", name = "Shield Hider", version = "@VERSION@")
public class ShieldHider {

	@Mod.Instance
	public static ShieldHider INSTANCE;

	public static Configuration config;

	public static int hidingMode;

	@Mod.EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		initConfig(event.getSuggestedConfigurationFile());

		//MinecraftForge.EVENT_BUS.register(this);

		if (event.getSide() == Side.CLIENT && hidingMode > 0 && hidingMode < 3) {
			final ModelResourceLocation shield1 = new ModelResourceLocation("shieldhider:shield1");
			final ModelResourceLocation shield2 = new ModelResourceLocation("shieldhider:shield2");

			ModelLoader.registerItemVariants(Items.shield, shield1, shield2);

			ModelLoader.setCustomMeshDefinition(Items.shield, new ItemMeshDefinition() {
				@Override
				public ModelResourceLocation getModelLocation(ItemStack stack) {
					return hidingMode == 1 ? shield1 : shield2;
				}
			});
		}
	}

	private static void initConfig(File file) {
		config = new Configuration(file);

		config.load();

		hidingMode = config.get("shieldHiding", "hidingMode", 1, "0 = just like vanilla MC, 1 = lower the shield when in hand, 2 = lower the shield when in hand and while blocking (more will come)", 0, 2).getInt();

		if (config.hasChanged()) {
			config.save();
		}
	}

	@SubscribeEvent
	public void onHandRender(RenderHandEvent event) {
		//TODO: implement when this PR get's merged https://github.com/MinecraftForge/MinecraftForge/pull/2817
	}
}
