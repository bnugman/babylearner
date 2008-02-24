package com.pingva.ml.learners;

import com.pingva.ml.datagen.LearningData;

public class NaiveBayesLearner implements Learner {

	
	private static final float EPS = 1e-6f;
	private float means[][];
	private float stddevs[][];
	private int totals[];
	private int numClasses;

	public int classify(float[] sample) {

		int bestClass = -1;
		
		float bestR = Float.MAX_VALUE;
		
		for (int klass = 0; klass < numClasses; klass++) {
		
			if(totals[klass]==0)
				continue;
			float r = 0;
			for (int i = 0; i < sample.length; i++) {
				r+= sq((sample[i] - means[klass][i])/stddevs[klass][i]);
			}
			
			if(r<bestR){
				bestClass = klass;
				bestR = r;
			}
		}
		return bestClass;

	}

	private float sq(float f) {
		return f*f;
	}

	public void learn(LearningData data) {

		float[][] vecs = data.getFeautureVectors();
		int[] truth = data.getTruth();
		
		
		numClasses = data.getNumClasses();
		int numDims = data.getNumDimensions();
		
		means = new float[numClasses][data.getNumDimensions()];
		stddevs = new float[numClasses][data.getNumDimensions()];
		totals = new int[numClasses];
		
		for (int i = 0; i < vecs.length; i++) {
			
			int klass = truth[i];
			float []vec = vecs[i];
			
			totals[klass]++;
			
			for(int dim=0;dim<numDims;dim++){
				means[klass][dim]+=vec[dim];
				stddevs[klass][dim]+= sq(vec[dim]);
			}
		}
		
		for(int klass=0;klass<numClasses;klass++){
			if(totals[klass]==0)
				continue;
			for(int dim=0;dim<numDims;dim++){
			
				stddevs[klass][dim] -= sq(means[klass][dim]) / totals[klass];
				stddevs[klass][dim] = (float) Math.sqrt(stddevs[klass][dim]/totals[klass]) + EPS;
				
				means[klass][dim]/=totals[klass];
			}
		}		
	}
}
