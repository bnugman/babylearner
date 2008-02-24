package com.pingva.ml.datagen;


public class DataGenerator {

	
	
	public static LearningData makeData() {

		int N = 10000;
		double prior = 0.5; // prior probability of class 0;
		
		
		
		return makeData(
				N,
				prior,
				sampleDistroSimpleBayesian()
				);
		
	}
	
	static private Distro[] sampleDistroSimpleBayesian(){
		return new Distro[]{
				new GaussianDistro(new float[]{0.35f,0.35f}, new float [][]{{0.08f,0.0f},{0.0f,0.04f}}),
				new GaussianDistro(new float[]{0.65f,0.65f}, new float [][]{{0.05f,0.0f},{0.0f,0.07f}}),
		};
	}
	
	static private Distro [] sampleDistroComplex(){
		ComboDistro classA = new ComboDistro(new Distro[]{
				new GaussianDistro(new float[]{0.35f,0.35f}, new float [][]{{0.05f,0.0f},{0.0f,0.05f}}),
				new UniformDistro(new float[]{0.15f,0.45f}, new float[]{0.85f,0.65f})
		});
		
		ComboDistro classB = new ComboDistro(new Distro[]{
				new GaussianDistro(new float[]{0.65f,0.35f}, new float [][]{{0.08f,0.0f},{0.0f,0.06f}}),
				new UniformDistro(new float[]{0.30f,0.68f}, new float[]{0.55f,0.78f})
		});
		
		Distro distro[] = new Distro[]{  classA, classB};
		return distro;
		
	}
	
	
	
	public static LearningData makeData(int n, double prior, Distro []distro) {
				
		float[][] vecs = new float [n][];
		int[] truth = new int[n];
		
		for (int i = 0; i < truth.length; i++) {

			int c = Math.random()<prior?0:1;
			
			do {
				vecs[i] = distro[c].makeExemplar();
			} while(!isWithinBounds(vecs[i])); 
			truth[i] = c;
		}
		
		LearningData data = new LearningData(vecs, truth);
		return data;
		
	}

	private static boolean isWithinBounds(float[] vec) {

		for (int i = 0; i < vec.length; i++) {
			if(vec[i]<0.0 || vec[i]>=1.0-0.000000001)
				return false;
		}
		return true;
		
	}
	
	

}
