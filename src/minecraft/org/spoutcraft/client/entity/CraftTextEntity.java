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
package org.spoutcraft.client.entity;

import org.spoutcraft.client.SpoutcraftWorld;
import org.spoutcraft.spoutcraftapi.entity.TextEntity;
import org.spoutcraft.spoutcraftapi.util.FixedLocation;

public class CraftTextEntity extends CraftEntity implements TextEntity {
	public CraftTextEntity(FixedLocation location) {
		super(location);
		handle = new EntityText(((SpoutcraftWorld)location.getWorld()).getHandle());
		teleport(location);
	}

	public CraftTextEntity(EntityText handle) {
		super(handle);
	}

	public EntityText getHandle() {
		return (EntityText)handle;
	}

	public String getText() {
		return getHandle().getText();
	}

	public void setText(String text) {
		getHandle().setText(text);
	}

	public boolean isRotatingWithPlayer() {
		return getHandle().isRotateWithPlayer();
	}

	public void setRotatingWithPlayer(boolean flag) {
		getHandle().setRotateWithPlayer(flag);
	}

	public float getScale() {
		return getHandle().getScale();
	}

	public void setScale(float s) {
		getHandle().setScale(s);
	}
}
