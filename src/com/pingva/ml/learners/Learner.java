package com.pingva.ml.learners;

import com.pingva.ml.datagen.LearningData;

public interface Learner {

	public void learn(LearningData data);

	public int classify(float[] sample);

}
