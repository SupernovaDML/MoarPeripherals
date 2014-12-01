/**
 * Copyright 2014 Joshua Asbury (@theoriginalbit)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.theoriginalbit.moarperipherals.common.tile;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import com.theoriginalbit.moarperipherals.api.peripheral.annotation.function.LuaFunction;
import com.theoriginalbit.moarperipherals.api.peripheral.annotation.LuaPeripheral;
import com.theoriginalbit.moarperipherals.common.config.ConfigHandler;
import com.theoriginalbit.moarperipherals.common.network.packet.PacketFxIronNote;
import com.theoriginalbit.moarperipherals.common.tile.abstracts.TileMoarP;
import com.theoriginalbit.moarperipherals.common.utils.PacketUtils;

@LuaPeripheral("iron_note")
public class TileIronNote extends TileMoarP {
    private static ImmutableList<String> INSTRUMENTS = ImmutableList.of("harp", "bd", "snare", "hat", "bassattack");
    private static final int MIN_INST = 0;
    private static final int MAX_INST = 4;
    private static final int MIN_PITCH = 0;
    private static final int MAX_PITCH = 24;
    private static final int MAX_NOTES = 5; // this is 5 notes per tick, allowing for 5 note chords
    private int notesCount = 0;
    private PacketFxIronNote packet;

    @LuaFunction
    public void playNote(int instrument, int pitch) throws Exception {
        Preconditions.checkArgument(instrument >= MIN_INST && instrument <= MAX_INST, "Expected instrument %d-%d", MIN_INST, MAX_INST);
        Preconditions.checkArgument(pitch >= MIN_PITCH && pitch <= MAX_PITCH, "Expected pitch %d-%d", MIN_PITCH, MAX_PITCH);
        Preconditions.checkArgument(notesCount++ < MAX_NOTES, "Too many notes (over %d per tick)", MAX_NOTES);
        Preconditions.checkArgument(ConfigHandler.noteRange > 0, "The Iron Note blocks range has been disabled, please contact your server owner");

        if (packet == null) {
            packet = new PacketFxIronNote();
        }

        packet.addNote("note." + INSTRUMENTS.get(instrument), pitch);
    }

    @Override
    public void updateEntity() {
        notesCount = 0;

        // if there is a message, send it
        if (packet != null) {
            // packs all the notes into the message
            packet.pack(worldObj.provider.dimensionId, xCoord, yCoord, zCoord);
            PacketUtils.sendToPlayersAround(packet, xCoord, yCoord, zCoord,
                    ConfigHandler.noteRange,
                    worldObj.provider.dimensionId
            );
            packet = null;
        }
    }
}