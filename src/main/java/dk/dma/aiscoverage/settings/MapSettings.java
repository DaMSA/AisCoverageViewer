/*
 * Copyright 2012 Danish Maritime Authority. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 *   1. Redistributions of source code must retain the above copyright notice,
 * this list of conditions and the following disclaimer.
 *
 *   2. Redistributions in binary form must reproduce the above copyright notice,
 * this list of conditions and the following disclaimer in the documentation and/or
 * other materials provided with the distribution.
 * 
 * THIS SOFTWARE IS PROVIDED BY Danish Maritime Safety Administration ``AS IS'' 
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL <COPYRIGHT HOLDER> OR CONTRIBUTORS BE LIABLE FOR
 * ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
 * ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.

 * The views and conclusions contained in the software and documentation are those
 * of the authors and should not be interpreted as representing official policies,
 * either expressed or implied, of Danish Maritime Authority.
 * 
 */
package dk.dma.aiscoverage.settings;

import java.io.Serializable;
import java.util.Properties;

import com.bbn.openmap.proj.coords.LatLonPoint;
import com.bbn.openmap.util.PropUtils;

/**
 * Map/chart settings
 */
public class MapSettings implements Serializable {

	private static final long serialVersionUID = 1L;
	private static final String PREFIX = "map.";

	private LatLonPoint center = new LatLonPoint.Double(56, 11);
	private float scale = 10000000;
	private boolean useEnc = true;
	private boolean encVisible = true;
	private int maxScale = 5000;
	
	private boolean s52ShowText = false;
	private boolean s52ShallowPattern = false;
	private int s52ShallowContour = 6;
	private int s52SafetyDepth = 8;
	private int s52SafetyContour = 8;
	private int s52DeepContour = 10;
	private boolean useSimplePointSymbols = true;
	private boolean usePlainAreas = false;
	private boolean s52TwoShades = false;

	public MapSettings() {
	}

	public void readProperties(Properties props) {
		center.setLatitude(PropUtils.doubleFromProperties(props, PREFIX + "center_lat", center.getLatitude()));
		center.setLongitude(PropUtils.doubleFromProperties(props, PREFIX + "center_lon", center.getLongitude()));
		scale = PropUtils.floatFromProperties(props, PREFIX + "scale", scale);
		useEnc = PropUtils.booleanFromProperties(props, PREFIX + "useEnc", useEnc);
		encVisible = PropUtils.booleanFromProperties(props, PREFIX + "encVisible", encVisible);
		maxScale = PropUtils.intFromProperties(props, PREFIX + "maxScale", maxScale);
		
		// settings for S52 layer
		s52ShowText = PropUtils.booleanFromProperties(props, PREFIX + "s52ShowText", s52ShowText);
		s52ShallowPattern = PropUtils.booleanFromProperties(props, PREFIX + "s52ShallowPattern", s52ShallowPattern);
		s52ShallowContour = PropUtils.intFromProperties(props, PREFIX + "s52ShallowContour", s52ShallowContour);
		s52SafetyDepth = PropUtils.intFromProperties(props, PREFIX + "s52SafetyDepth", s52SafetyDepth);
		s52SafetyContour = PropUtils.intFromProperties(props, PREFIX + "s52SafetyContour", s52SafetyContour);
		s52DeepContour = PropUtils.intFromProperties(props, PREFIX + "s52DeepContour", s52DeepContour);
		useSimplePointSymbols = PropUtils.booleanFromProperties(props, PREFIX + "useSimplePointSymbols", useSimplePointSymbols);
		usePlainAreas = PropUtils.booleanFromProperties(props, PREFIX + "usePlainAreas", usePlainAreas);
		s52TwoShades = PropUtils.booleanFromProperties(props, PREFIX + "s52TwoShades", s52TwoShades);
	}

	public void setProperties(Properties props) {
		props.put(PREFIX + "center_lat", Double.toString(center.getLatitude()));
		props.put(PREFIX + "center_lon", Double.toString(center.getLongitude()));
		props.put(PREFIX + "scale", Double.toString(scale));
		props.put(PREFIX + "useEnc", Boolean.toString(useEnc));
		props.put(PREFIX + "encVisible", Boolean.toString(encVisible));
		props.put(PREFIX + "maxScale", Integer.toString(maxScale));
		
		// settings for S52 layer
		props.put(PREFIX + "s52ShowText", Boolean.toString(s52ShowText));
		props.put(PREFIX + "s52ShallowPattern", Boolean.toString(s52ShallowPattern));
		props.put(PREFIX + "s52ShallowContour", Integer.toString(s52ShallowContour));
		props.put(PREFIX + "s52SafetyDepth", Integer.toString(s52SafetyDepth));
		props.put(PREFIX + "s52SafetyContour", Integer.toString(s52SafetyContour));
		props.put(PREFIX + "s52DeepContour", Integer.toString(s52DeepContour));
		props.put(PREFIX + "useSimplePointSymbols", Boolean.toString(useSimplePointSymbols));
		props.put(PREFIX + "usePlainAreas", Boolean.toString(usePlainAreas));
		props.put(PREFIX + "s52TwoShades", Boolean.toString(s52TwoShades));
	}

	public LatLonPoint getCenter() {
		return center;
	}

	public void setCenter(LatLonPoint center) {
		this.center = center;
	}

	public float getScale() {
		return scale;
	}

	public void setScale(float scale) {
		this.scale = scale;
	}

	public boolean isUseEnc() {
		return useEnc;
	}

	public void setUseEnc(boolean useEnc) {
		this.useEnc = useEnc;
	}
	
	public boolean isEncVisible() {
		return encVisible;
	}
	
	public void setEncVisible(boolean encVisible) {
		this.encVisible = encVisible;
	}
	
	public int getMaxScale() {
		return maxScale;
	}
	
	public void setMaxScale(int maxScale) {
		this.maxScale = maxScale;
	}

	public boolean isS52ShowText() {
		return s52ShowText;
	}

	public void setS52ShowText(boolean s52ShowText) {
		this.s52ShowText = s52ShowText;
	}

	public boolean isS52ShallowPattern() {
		return s52ShallowPattern;
	}

	public void setS52ShallowPattern(boolean s52ShallowPattern) {
		this.s52ShallowPattern = s52ShallowPattern;
	}

	public int getS52ShallowContour() {
		return s52ShallowContour;
	}

	public void setS52ShallowContour(int s52ShallowContour) {
		this.s52ShallowContour = s52ShallowContour;
	}

	public int getS52SafetyDepth() {
		return s52SafetyDepth;
	}

	public void setS52SafetyDepth(int s52SafetyDepth) {
		this.s52SafetyDepth = s52SafetyDepth;
	}

	public int getS52SafetyContour() {
		return s52SafetyContour;
	}

	public void setS52SafetyContour(int s52SafetyContour) {
		this.s52SafetyContour = s52SafetyContour;
	}

	public int getS52DeepContour() {
		return s52DeepContour;
	}

	public void setS52DeepContour(int s52DeepContour) {
		this.s52DeepContour = s52DeepContour;
	}

	public boolean isUseSimplePointSymbols() {
		return useSimplePointSymbols;
	}

	public void setUseSimplePointSymbols(boolean useSimplePointSymbols) {
		this.useSimplePointSymbols = useSimplePointSymbols;
	}

	public boolean isUsePlainAreas() {
		return usePlainAreas;
	}

	public void setUsePlainAreas(boolean usePlainAreas) {
		this.usePlainAreas = usePlainAreas;
	}

	public boolean isS52TwoShades() {
		return s52TwoShades;
	}

	public void setS52TwoShades(boolean s52TwoShades) {
		this.s52TwoShades = s52TwoShades;
	}
	
}
