package org.ai.mechineleanring;

import org.neuroph.nnet.learning.SigmoidDeltaRule;

public class EnhancedLearningRule {

	private Class<? extends SigmoidDeltaRule> lrClass;
	private int maxIterations;
	private double maxError;
	private double learningRate;

	EnhancedLearningRule(Class<? extends SigmoidDeltaRule> lrClass, double learningRate,
			int maxIterations, double maxError) {

		this.lrClass = lrClass;
		this.learningRate = learningRate;
		this.maxIterations = maxIterations;
		this.maxError = maxError;
	}

	public Class<? extends SigmoidDeltaRule> getLrClass() {
		return lrClass;
	}

	public void setLrClass(Class<? extends SigmoidDeltaRule> lrClass) {
		this.lrClass = lrClass;
	}

	public int getMaxIterations() {
		return maxIterations;
	}

	public void setMaxIterations(int maxIterations) {
		this.maxIterations = maxIterations;
	}

	public double getMaxError() {
		return maxError;
	}

	public void setMaxError(double maxError) {
		this.maxError = maxError;
	}

	public double getLearningRate() {
		return learningRate;
	}

	public void setLearningRate(double learningRate) {
		this.learningRate = learningRate;
	}



}