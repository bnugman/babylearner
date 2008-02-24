package com.pingva.ml.gui;

import java.awt.Color;

public class DisplaySettings {

	private static final int ALPHA = 100;
	int xIndex = 0;
	int yIndex = 1;
	
	public int getXindex() {
		return xIndex;
	}
	public int getYindex() {
		return yIndex;
	}
	public Color[] getClassColors() {
		return new Color[]{transparentize(Color.BLUE) , transparentize(Color.RED)};
	}
	
	private Color transparentize(Color c) {
		return new Color(c.getRed(), c.getGreen(), c.getBlue(), ALPHA);
		
//		return c;
	}
	public int getPointSize(){
		return 2;
	}

	
	private static float[] sample = new float[2]; 
	/**
	 * when displaying data of dimensionality higher than two,
	 * we project it onto 2D
	 * 
	 * in order to invoke the classifier when traversing the section,
	 * we need to know the coordinates that are fixed,
	 * while the coordinates returned by getXindex and getYindex
	 * are changing.
	 * 
	 * For a two-dimensional case, we simply return a two-element array
	 * 
	 *  
	 */
	public float[] getSectionSample() {
		return sample;		
	}
	public Color[] getBackgroundColors() {

		Color[] colors = getClassColors();
		
		for (int j = 0; j < colors.length; j++) {
			Color c = colors[j];
			colors[j] = new Color(c.getRed(),c.getGreen(),c.getBlue()).darker().darker().darker().darker();
		}
		return colors;
	}

}
