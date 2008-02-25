package com.pingva.ml.learners;

import junit.framework.TestCase;

public class LearnerBaseTestCase extends TestCase {

	private Learner learner;

	protected void setLearner(Learner l) {
		learner = l;
	}

	protected void assertKlass(int klass, double value) {
		assertEquals(klass, learner.classify(new float[] { (float) value }));
	}
}
