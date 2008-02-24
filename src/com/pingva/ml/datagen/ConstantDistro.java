package com.pingva.ml.datagen;


public class ConstantDistro implements Distro {

	
	private float[] value;
	
	public ConstantDistro(float []value) {
		this.value = value;
			
	}
	public float[] makeExemplar() {
		return value;
	}

}
