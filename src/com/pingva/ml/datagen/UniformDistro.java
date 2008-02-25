package com.pingva.ml.datagen;



public class UniformDistro implements Distro {

	
	private float[] starts, widths;
	private int len;
	
	
	public UniformDistro(float starts[], float [] ends){
		
		
		if(starts.length!=ends.length)
			throw new IllegalArgumentException("arrays are of different sizes");
		len = starts.length;
		
		this.starts = new float[len];
		this.widths = new float[len];
		
		for (int i = 0; i < len; i++) {
			widths[i] = ends[i] - starts[i] ;			
		}
		
	}
	public float[] makeExemplar() {

		float []value = new float[len]; 

		for (int i = 0; i < value.length; i++) {
			value[i] = (float) (starts[i] + Math.random()*widths[i]);
		}
		
		return value;
	}

}
