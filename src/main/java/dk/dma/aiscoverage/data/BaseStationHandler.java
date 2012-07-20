/* Copyright (c) 2011 Danish Maritime Authority
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this library.  If not, see <http://www.gnu.org/licenses/>.
 */
package dk.dma.aiscoverage.data;

import java.awt.AWTEvent;
import java.awt.Toolkit;
import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import dk.dma.aiscoverage.project.ProjectHandler;


public class BaseStationHandler implements Serializable {

	public ConcurrentHashMap<Long, BaseStation> grids = new ConcurrentHashMap<Long, BaseStation>();
	private double latSize;
	private double lonSize;
	
	public double getLatSize() {
		return latSize;
	}

	public void setLatSize(double latSize) {
		this.latSize = latSize;
	}

	public double getLonSize() {
		return lonSize;
	}

	public void setLonSize(double lonSize) {
		this.lonSize = lonSize;
	}

	
	
	/*
	 * Create grid associated to a specific transponder
	 */
	public BaseStation createGrid(Long bsMmsi){
		BaseStation grid = new BaseStation(bsMmsi, latSize, lonSize);
		grids.put(bsMmsi, grid);
		ProjectHandler.getInstance().basestationAdded(bsMmsi);
		return grid;
	}
	
	public BaseStation getGrid(Long bsMmsi){
		return grids.get(bsMmsi);
	}
	public void setAllVisible(boolean b){
		Collection<BaseStation> basestations = grids.values();
		for (BaseStation baseStation : basestations) {	
			setVisible(baseStation.bsMmsi, b);
		}
	}
	public void setVisible(long mmsi, boolean b){
		BaseStation baseStation = grids.get(mmsi);
		if(baseStation != null){
			baseStation.setVisible(b);
		}
	}

	
	
	
	
	
}
