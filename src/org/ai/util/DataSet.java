package org.ai.util;

import org.neuroph.core.learning.TrainingElement;
import org.neuroph.core.learning.TrainingSet;

public class DataSet {

	private TrainingSet<TrainingElement> dataSet = null;
	private TrainingSet<TrainingElement> trainingSet = null;
	private TrainingSet<TrainingElement> testSet = null;

	private int trainSetPercent = 70;
	private int testSetPercent = 30;
	private boolean useFullDataSet = Boolean.FALSE;

	@SuppressWarnings("unchecked")
	public DataSet(String filePath, int inputsCount, int outputsCount, String delimiter, boolean useFullDataSet) {
		dataSet = TrainingSet.createFromFile(filePath, inputsCount, outputsCount, delimiter);
		dataSet.shuffle();
		dataSet.normalize();
		this.useFullDataSet = useFullDataSet;
	}

	public void setUseFullDataSet(boolean useFullDataSet) {
		this.useFullDataSet = useFullDataSet;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void setTrainingAndTestSet() {

		if (this.useFullDataSet == Boolean.TRUE) {
			trainingSet = dataSet;
			testSet = dataSet;
		} else {
			if (trainingSet == null || testSet == null) {
				TrainingSet[] trainingSets = dataSet.createTrainingAndTestSubsets(trainSetPercent, testSetPercent);
				trainingSet = trainingSets[0];
				testSet = trainingSets[1];
			}
		}

	}

	@SuppressWarnings("rawtypes")
	public TrainingSet getTrainingSet() {
		setTrainingAndTestSet();
		return trainingSet;
	}

	@SuppressWarnings("rawtypes")
	public TrainingSet getTestSet() {
		setTrainingAndTestSet();
		return trainingSet;
	}

	public int getTrainSetPercent() {
		return trainSetPercent;
	}

	public void setTrainSetPercent(int trainSetPercent) {
		this.trainSetPercent = trainSetPercent;
		this.testSetPercent = 100 - trainSetPercent;
	}

	public int getTestSetPercent() {
		return testSetPercent;
	}

	public void setTestSetPercent(int testSetPercent) {
		this.testSetPercent = testSetPercent;
		this.trainSetPercent = 100 - testSetPercent;
	}

}
