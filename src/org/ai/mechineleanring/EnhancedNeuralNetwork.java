package org.ai.mechineleanring;

import java.util.List;

import org.neuroph.core.NeuralNetwork;
import org.neuroph.core.learning.TrainingSet;
import org.neuroph.nnet.learning.LMS;
import org.neuroph.nnet.learning.SigmoidDeltaRule;
import org.neuroph.util.NeuralNetworkFactory;
import org.neuroph.util.TransferFunctionType;

public class EnhancedNeuralNetwork {

	private final int totalTest;
	private String networkId;

	private final TrainingSet trainingSet;
	private final String layersStr;
	private final TransferFunctionType tft;
	private final Class<? extends SigmoidDeltaRule> lrClass;
	private final boolean useBias;
	private final boolean connectIO;
	private final double learningRate;
	private final int maxIterations;
	private final double maxError;

	private NeuralNetwork nnet;
	private final NNErrorObserver networkErrorObserver;
	private double totalNetworkError;

	EnhancedNeuralNetwork(int totalTest, String networkId, TrainingSet trainingSet, String layersStr,
			TransferFunctionType tft, boolean useBias, boolean connectIO, EnhancedLearningRule elr) {

		this.totalTest = totalTest;
		this.networkId = networkId;

		this.trainingSet = trainingSet;
		this.layersStr = layersStr;
		this.tft = tft;
		this.useBias = useBias;
		this.connectIO = connectIO;

		this.lrClass = elr.getLrClass();
		this.learningRate = elr.getLearningRate();
		this.maxIterations = elr.getMaxIterations();
		this.maxError = elr.getMaxError();

		networkErrorObserver = new NNErrorObserver();
	}

	public void learn() {

		nnet = NeuralNetworkFactory.createMLPerceptron(layersStr, tft, lrClass, useBias, connectIO);
		nnet.getLearningRule().addObserver(getNetworkErrorObserver());

		((LMS) nnet.getLearningRule()).setMaxError(maxError);// 0-1
		((LMS) nnet.getLearningRule()).setLearningRate(learningRate);// 0-1
		((LMS) nnet.getLearningRule()).setMaxIterations(maxIterations);

		for (int testId = 1; testId <= getTotalTest(); testId++) {
			// Observer class store info based on network label
			nnet.setLabel(String.valueOf(testId));
			nnet.randomizeWeights();
			nnet.learn(trainingSet);

			if (!Double.isNaN(this.totalNetworkError)
					&& !Double.isNaN(((LMS) nnet.getLearningRule()).getTotalNetworkError())) {
				this.totalNetworkError += ((LMS) nnet.getLearningRule()).getTotalNetworkError();
			} else {
				this.totalNetworkError = Double.NaN;
			}

		}

	}

	public List<LearningErrorInfo> getLearningErrorInfoList() {
		return getNetworkErrorObserver().getLearningErrorInfoList(1);
	}

	public List<LearningErrorInfo> getLearningErrorInfoList(int testId) {
		return getNetworkErrorObserver().getLearningErrorInfoList(testId);
	}

	public String getNetworkId() {
		return this.networkId;
	}

	public void setNetworkId(String networkId) {
		this.networkId = networkId;
	}

	public double getTotalNetworkError() {
		return this.totalNetworkError;
	}

	public String getLayersStr() {
		return this.layersStr;
	}

	public String getTransferFunctionName() {
		return tft.getTypeLabel();
	}

	public String getLearningRuleName() {
		return nnet.getLearningRule().getClass().getSimpleName();
	}

	public NeuralNetwork getNeuralNetwork() {
		return nnet;
	}

	public int getTotalTest() {
		return totalTest;
	}

	public NNErrorObserver getNetworkErrorObserver() {
		return networkErrorObserver;
	}

}
