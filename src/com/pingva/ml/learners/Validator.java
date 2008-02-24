package com.pingva.ml.learners;

import com.pingva.ml.datagen.LearningData;

public class Validator {
	
	public static float errorRate(Learner learner, LearningData data){
		
		
		float[][] v = data.getFeautureVectors();
		int[] t = data.getTruth();
		
		int errors = 0;
		
		for (int i = 0; i < v.length; i++) {
		
			if(learner.classify(v[i])!=t[i])
				errors++;
		}
		
		return ((float)errors)/t.length;
		
	}

}
