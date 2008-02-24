package com.pingva.ml.datagen;


public class ComboDistro implements Distro {

	private Distro[] distros;

	public ComboDistro(Distro[] distros){
		this.distros = distros;
	}

	public float[] makeExemplar() {
		return distros[(int)(Math.random()*distros.length)].makeExemplar();
	}

}
