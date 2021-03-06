/**
 * Copyright 2014-2015 Joshua Asbury (@theoriginalbit)
 * <p/>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p/>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p/>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.moarperipherals.block;

import com.moarperipherals.ModInfo;
import com.moarperipherals.tile.TileTurtleTeleport;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

/**
 * @author theoriginalbit
 * @since 13/10/2014
 */
public class BlockTurtleTeleport extends BlockMoarP {
    public BlockTurtleTeleport() {
        super("turtleTeleport");
        setHardness(5f);
        setResistance(2000f);
        setStepSound(soundTypePiston);
    }

    @Override
    public TileEntity createNewTileEntity(World world, int par2) {
        return new TileTurtleTeleport();
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister registry) {
        super.registerBlockIcons(registry);
        icons[1] = registry.registerIcon(ModInfo.RESOURCE_DOMAIN + ":turtleTeleportTop");
    }
}
