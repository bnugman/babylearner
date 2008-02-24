package com.pingva.ml.learners;

import com.pingva.ml.datagen.LearningData;

/**
 * Simple 1R rule learner
 * 
 * finds a rule of the form:
 * 	if(v[Component] >= Theshold) then ClassAbove else ClassBelow;
 * 			
 * the algorithm chooses which Component of feature vector to use, and at which Threshold
 * 
 * all components of all feature vectors are assumed to be in the range [0;1)
 * the values are binned.  The default number of bins is 100
 * 
 * 
 * @author pingva
 *
 */
public class Rule1RLearner implements Learner {

	
	int theComponent = -1;
	float theThreshold;
	int theClass;
	
	
	private int[][] bins;
	private int numBins = 100;
	private LearningData data;
	private int numClasses;
	private int bestClassA;
	private int bestBin;
	private int bestComponent;
	private int bestError;

	public int classify(float[] sample) {

		if(sample[theComponent]>=theThreshold)
			return theClass;
		else
			return 1-theClass;
	}

	public void learn(LearningData data) {
		
		this.data = data;
		float[][] training = data.getFeautureVectors();
		int[] truth = data.getTruth();
		numClasses = data.getNumClasses();
		
		if(numClasses!=2)
			throw new IllegalArgumentException("can only learn two-class problems");
		
		/*
		 * for each dimension:
		 * 	bin data
		 * 	find the bin, separating on which produces the best error rate
		 * 	
		 * 		
		 */

		bestClassA = -1;
		bestBin = -1;
		bestComponent = -1;
		bestError = data.getNumSamples();
		
		for(int i=0;i<data.getNumDimensions();i++){
			initBins();
			for(int j=0;j<training.length;j++){
				bin(truth[j],training[j][i]);
			}
			findBestBin(i);
		}
		
		theComponent = bestComponent;
		theClass = bestClassA;
		theThreshold = ((float)bestBin+1) /((float) numBins); 
		
		
	}

	private void findBestBin(int currentDimension) {

		for(int i=0;i<numClasses;i++){
			for (int j = 1; j < bins[i].length; j++) {
				bins[i][j] += bins[i][j-1];
			}						
		}

		
		for(int klass=0;klass<numClasses;klass++){
			int []binA = bins[klass];
			int []binB = bins[1-klass];
			
			int binBTotal = binB[binB.length-1];
		
			int bestBinUntil = -1;
			for (int j = 0; j < binA.length; j++) {
				
				int error = binA[j] + binBTotal - binB[j];

				if(error<bestError){
					bestClassA = klass;
					bestBin = j;
					bestError = error;
					bestComponent = currentDimension;
					
					bestBinUntil  = bestBin;
					
				} else if(error==bestError && bestBinUntil >= 0){
					bestBinUntil = j;
				} else if(bestBinUntil > 0 ){
					bestBin = (bestBin + bestBinUntil)/2;
					bestBinUntil = -1;
				}
			}						
		}
	}

	private void bin(int truth, float f) {
		bins[truth][ (int)(f*numBins)]++;
	}

	private void initBins() {
		bins = new int[numClasses][];
		for(int klass=0;klass<numClasses;klass++){
			bins[klass] = new int[numBins];
			for (int i = 0; i < numBins; i++) {
				bins[klass][i] = 0;
			}
		}
	}

}
