package com.theoriginalbit.minecraft.moarperipherals.render;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBase;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.IItemRenderer;

public abstract class CustomItemRenderer implements IItemRenderer {
	
	protected ModelBase modelItem;
	
	public CustomItemRenderer(ModelBase model) {
		modelItem = model;
	}

	@Override
	public boolean handleRenderType(ItemStack stack, ItemRenderType type) {
		switch (type) {
			case ENTITY: return true; // item on the ground
			case EQUIPPED: return true; // item being seen in 3rd person
			case EQUIPPED_FIRST_PERSON: return true; // item being seen in 1st person
			case INVENTORY: return true; // item being seen in the inventory
			default: return false;
		}
	}

	@Override
	public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item, ItemRendererHelper helper) {
		switch (helper) {
			case INVENTORY_BLOCK: return true;
			case ENTITY_BOBBING: return true; // this makes the item bob when on the ground
			case ENTITY_ROTATION: return true; // this makes the item rotate when on the ground
			case EQUIPPED_BLOCK: return true;
			default: return false;
		}
	}

	@Override
	public void renderItem(ItemRenderType type, ItemStack stack, Object... data) {
		GL11.glPushMatrix();
		Minecraft.getMinecraft().getTextureManager().bindTexture(getTexture(stack));
		
		switch (type) {
			case ENTITY:
				manipulateEntityRender(stack);
				modelItem.render(null, 0f, 0f, 0f, 0f, 0f, 0.0625f);
				break;
			case EQUIPPED:
				manipulateThirdPersonRender(stack);
				modelItem.render((Entity) data[1], 0f, 0f, 0f, 0f, 0f, 0.0625f);
				break;
			case EQUIPPED_FIRST_PERSON:
				manipulateFirstPersonRender(stack);
				modelItem.render((Entity) data[1], 0f, 0f, 0f, 0f, 0f, 0.0625f);
				break;
			case INVENTORY:
				manipulateInventoryRender(stack);
				modelItem.render(null, 0f, 0f, 0f, 0f, 0f, 0.0625f);
				break;
			default: break;
		}
		
		GL11.glPopMatrix();
	}
	
	protected abstract ResourceLocation getTexture(ItemStack stack);
	protected abstract void manipulateEntityRender(ItemStack stack);
	protected abstract void manipulateInventoryRender(ItemStack stack);
	protected abstract void manipulateThirdPersonRender(ItemStack stack);
	protected abstract void manipulateFirstPersonRender(ItemStack stack);
}