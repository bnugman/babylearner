package com.pingva.ml.learners;

import java.util.Collection;
import java.util.LinkedList;

import com.pingva.ml.datagen.LearningData;

public class BoostLearner implements Learner {

	private static final int MAX_LEARNERS_NUM = 15;

	private int classNum = -1;
	
	private LearnerFactory factory;
	private Collection<Learner> learners = new LinkedList<Learner>();

	public int classify(float[] sample) {
		
		int votes[] = new int[classNum];
		
		for(Learner learner: learners){
			int klass = learner.classify(sample);
			if(klass >=0)
				votes[klass]++;
		}
		
		int bestClass = -1;
		int maxVotes = 0;
		
		for(int i=0;i<classNum;i++){
			if(votes[i]>maxVotes){
				bestClass = i;
				maxVotes = votes[i];
			}
		}

		return bestClass;
	}
	
	public BoostLearner(LearnerFactory weakLearnerFactory){
		factory = weakLearnerFactory;
	}

	public void learn(LearningData data) {

		classNum = data.getNumClasses();
		
		boolean keepLearning = true;
		
		while(keepLearning){
			Learner learner = factory.createLearner();
			learner.learn(resample(data));
			learners.add(learner);
			keepLearning = learners.size() < MAX_LEARNERS_NUM;
		}
	}

	private LearningData resample(LearningData data) {

		if(learners.isEmpty()) // no need to resample yet
			return data;

		float[][] vecs = data.getFeautureVectors();
		int[] truth = data.getTruth();
		
		// for efficiency reasons, resampling is done like this:
		// correctly classified vectors are removed with (high) probability.
		
		float [][] newvecs = new float[vecs.length][];
		int []newtruth = new int[truth.length];
		
		double probabilityOfRemoval = 0.9;
		
		int currentIndex=0;
		for (int i = 0; i < vecs.length; i++) {
			if(classify(vecs[i])!=truth[i] || Math.random()>probabilityOfRemoval){
				newvecs[currentIndex] = vecs[i];
				newtruth[currentIndex] = truth[i];
				currentIndex++;				
			}
		}

		float keepvecs[][] = new float[currentIndex][];
		int keeptruth[] = new int[currentIndex];
		
		System.arraycopy(newvecs, 0, keepvecs, 0, currentIndex);
		System.arraycopy(newtruth, 0, keeptruth, 0, currentIndex);
		
		return new LearningData(keepvecs,keeptruth);
	}

}
