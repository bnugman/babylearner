package com.pingva.ml.datagen;

public class LearningData {
	
	private float [][] feature_vectors;
	private int truth [];
	
	public LearningData(float [][] vecs, int[] truth){
		if(vecs.length!=truth.length)
			throw new IllegalArgumentException("Length of feature vector array doesn't match length of truth array");
		feature_vectors = vecs;
		this.truth = truth;
	
	}

	public float[][] getFeautureVectors() {
		return feature_vectors;		
	}
	public int[] getTruth(){
		return truth;
	}

	public int getNumClasses() {
		return 2;
	}

	public int getNumSamples() {
		return feature_vectors.length;
	}

	public int getNumDimensions() {
		return feature_vectors[0].length;
	}

}
