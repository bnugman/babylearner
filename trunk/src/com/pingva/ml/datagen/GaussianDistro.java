package com.pingva.ml.datagen;


public class GaussianDistro implements Distro {

	private float[] center;
	private float[][] covariance;
	private boolean saved = false;
	private double bm1;
	private double bm2;

	public GaussianDistro(float[] center, float[][] covariance) {
		this.center = center;
		this.covariance = covariance;
	}

	public float[] makeExemplar() {

		float [] result = new float[center.length];
		for (int i = 0; i < result.length; i++) {
			
			// for now, ignore non-diagonal (covariance) components
			result[i] = center[i] + generateBoxMuller()*covariance[i][i];
		}
		return result;
	}

	private float generateBoxMuller() {
		if(saved){
			saved = false;
			return (float)bm2;			
		} else {
			
			double radius = Math.sqrt(-2*Math.log(Math.random()));
			double angle = 2*Math.PI*Math.random();
			
			bm1 = radius*Math.cos(angle);
			bm2 = radius*Math.sin(angle);
			saved = true;
			return (float)bm1;
		}
	}
}
