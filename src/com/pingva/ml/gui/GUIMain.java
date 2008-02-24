package com.pingva.ml.gui;

import javax.swing.JFrame;

import com.pingva.ml.datagen.DataGenerator;
import com.pingva.ml.datagen.LearningData;
import com.pingva.ml.learners.BoostLearner;
import com.pingva.ml.learners.Learner;
import com.pingva.ml.learners.LearnerFactory;
import com.pingva.ml.learners.NaiveBayesLearner;
import com.pingva.ml.learners.Rule1RLearner;

public class GUIMain {

	
	private static void generateAndLearnAndDisplay() {

		

		// generate data using defaults
		LearningData data = DataGenerator.makeData();
		
		// learn data using specified learner
		// the learner can be a meta-learner, which combines simpler learners 
		
		Learner learner =  
			new NaiveBayesLearner();

			// new Rule1RLearner(); 
			
			/*
			new BoostLearner(new LearnerFactory(){
				public Learner createLearner() {
					
					return Math.random()>0.5?
							new Rule1RLearner():
								new NaiveBayesLearner();
			}}) ;
			*/
		
		learner.learn(data);

		
		// simple JPanel that can render the learning data (as bright dots) and
		// results of learning (darker background)
		// display options are configurable through class DisplaySettings (a default is provided)

		
		LearningDisplayPanel dataDisplayPanel = new LearningDisplayPanel();

		dataDisplayPanel.setData(data);		
		dataDisplayPanel.setLearner(learner);

		

		// create main frame of the app
		JFrame frame = new JFrame("Learner Galore");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().add(dataDisplayPanel);
		
		frame.pack();
		frame.setVisible(true);
		
	}


	public static void main(String[] args) {

		
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
		    public void run() {
		        generateAndLearnAndDisplay();
		    }
		});

	}

}
