package com.pingva.ml.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;

import javax.swing.JPanel;

import com.pingva.ml.datagen.LearningData;
import com.pingva.ml.learners.Learner;
import com.pingva.ml.learners.Validator;



public class LearningDisplayPanel extends JPanel {
	
	public static final int WIDTH = 800;
	public static final int HEIGHT = 600;
	private LearningData data = null;
	private DisplaySettings settings = new DisplaySettings(); // default
	private Learner learner = null;
	
	public LearningDisplayPanel(){
		setPreferredSize(new Dimension(WIDTH,HEIGHT));
	}
	public void setData(LearningData data){
		this.data = data;
	}
	
	public void setDisplaySettings(DisplaySettings settings){
		this.settings = settings;
	}
	
	
	
	protected void paintComponent(Graphics g){
		super.paintComponent(g);
		

		paintLearner(g);
		paintData(g);
		
		g.setColor(Color.white);
		g.setFont(new Font("Arial",Font.PLAIN,14));
		g.drawString("Error rate: "+ ((int)(1000*Validator.errorRate(learner, data))/10.0)+"%" , 20, 20);
		
	}
	private void paintLearner(Graphics g) {

		if(learner==null)
			return;
		
		int width = getWidth();
		int height = getHeight();
		int xind = settings.getXindex();
		int yind = settings.getYindex();
		
		int xgridlines = 200;
		int ygridlines = 200;
		
		int xstep = width/xgridlines;
		if(xstep==0)
			xstep = 1;
		
		int ystep = height/ygridlines;
		if(ystep==0)
			ystep = 1;
		Color []colors = settings.getBackgroundColors();
	
		
		float[] sample = settings.getSectionSample();
		
		for(int y=0;y<height;y+=ystep){
			for(int x=0;x<width;x+=xstep){
			
				sample[xind] = ((float)x)/width;
				sample[yind] = ((float)y)/height;
				
				g.setColor( colors[learner.classify(sample)]);
				g.fillRect(x, y, xstep, ystep);
				
			}
		}
	}
	private void paintData(Graphics g) {
		if(data==null)
			return;
		
		float [][] points = data.getFeautureVectors();
		int [] truth = data.getTruth();
		
		int xind = settings.getXindex();
		int yind = settings.getYindex();
			
		int width = getWidth();
		int height = getHeight();
		int size = settings.getPointSize();
		
		int cNum = data.getNumClasses();
		Color []colors = settings.getClassColors();
		
		
		
		for(int c = 0;c<cNum;c++){

			g.setColor(colors[c]);
			for (int i = 0; i < points.length; i++) {
				
				if(truth[i]==c){
					int x = (int) (width * points[i][xind]);
					int y = (int) (height * points[i][yind]);
					g.fillRect(x, y, size, size);					
										
				}
			}		
		}
	}
	public void setLearner(Learner learner) {

		this.learner = learner;
		
	}
}
