/*
 * This file is part of Spoutcraft (http://www.spout.org/).
 *
 * Spoutcraft is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Spoutcraft is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.spoutcraft.client.packet;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import org.spoutcraft.client.gui.ScreenUtil;
import org.spoutcraft.spoutcraftapi.gui.ScreenType;

public class PacketOpenScreen implements SpoutPacket {
	ScreenType type = null;
	public PacketOpenScreen() {

	}

	public int getNumBytes() {
		return 4;
	}

	public void readData(DataInputStream input) throws IOException {
		type = ScreenType.getType(input.readInt());
	}

	public void writeData(DataOutputStream output) throws IOException {
		output.writeInt(type.getCode());
	}

	public void run(int playerId) {
		ScreenUtil.open(type);
	}

	public void failure(int playerId) {

	}

	public PacketType getPacketType() {
		return PacketType.PacketOpenScreen;
	}

	public int getVersion() {
		return 0;
	}
}
