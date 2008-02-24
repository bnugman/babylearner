
package com.pingva.ml.learners;

import com.pingva.ml.datagen.ConstantDistro;
import com.pingva.ml.datagen.DataGenerator;
import com.pingva.ml.datagen.Distro;
import com.pingva.ml.datagen.GaussianDistro;
import com.pingva.ml.datagen.LearningData;

import junit.framework.TestCase;

public class Rule1RLearnerTest extends LearnerBaseTestCase {
	
	

	public void testWithConstantDistro(){

		// create an array of "distributions".  
		// These "distributions" in fact produce constant samples, as provided in the constuctors
		
		Distro[] distros = { 
				new ConstantDistro(new float []{0.31f} ),
				new ConstantDistro(new float []{0.750f} )
		};
		
		// produce learning data: 100 samples, with equiprobable classes.  feature vectors 
		// are distributed according to the distros created above
		LearningData data = DataGenerator.makeData(100, 0.5, distros);
		
		Learner learner = new Rule1RLearner();
		learner.learn(data);
		
		setLearner(learner);

		assertKlass(0,0.0);
		assertKlass(0,0.1);
		assertKlass(0,0.2);
		assertKlass(0,0.3);
		assertKlass(0,0.4);

		assertKlass(1,0.56);
		assertKlass(1,0.6);
		assertKlass(1,0.7);
		assertKlass(1,0.9);
	}
	

	

	
}
