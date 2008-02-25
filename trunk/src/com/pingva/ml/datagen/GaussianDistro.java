package com.pingva.ml.datagen;

public class GaussianDistro implements Distro {

	private float[] mean;

	private float[][] covariance;

	private boolean saved = false;

	private double bm1;

	private double bm2;

	public GaussianDistro(float[] mean, float[][] covariance) {
		this.mean = mean;
		this.covariance = covariance;
	}

	public float[] makeExemplar() {

		// first, generate zero-mean, unit-variance vector
		float[] unit = new float[mean.length];
		for (int i = 0; i < unit.length; i++) {
			unit[i] = generateBoxMuller();
		}

		// now, multiply covariance matrix by that vector and add mean vector
		float[] result = new float[mean.length];
		for (int i = 0; i < covariance.length; i++) {
			result[i] = mean[i];
			for (int j = 0; j < covariance[i].length; j++) {
				result[i] += covariance[i][j] * unit[j];
			}
		}

		return result;
	}

	/*
	 * Box-Muller algorithm generates two numbers, distributed normally, with
	 * zero mean and unit variance
	 * 
	 * the second generated value is saved, to be used on subsequent invocation.
	 * Hence the ugliness.
	 */
	private float generateBoxMuller() {
		if (saved) {
			saved = false;
			return (float) bm2;
		} else {

			double radius = Math.sqrt(-2 * Math.log(Math.random()));
			double angle = 2 * Math.PI * Math.random();

			bm1 = radius * Math.cos(angle);
			bm2 = radius * Math.sin(angle);
			saved = true;
			return (float) bm1;
		}
	}
}
