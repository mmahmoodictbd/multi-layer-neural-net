package org.ai.util;

import java.util.ArrayList;
import java.util.List;

import org.neuroph.nnet.learning.BackPropagation;
import org.neuroph.nnet.learning.DynamicBackPropagation;
import org.neuroph.nnet.learning.MomentumBackpropagation;
import org.neuroph.nnet.learning.SigmoidDeltaRule;
import org.neuroph.util.TransferFunctionType;

public class Util {

	public static List<Class<? extends SigmoidDeltaRule>> getLearningRuleClassList() {
		List<Class<? extends SigmoidDeltaRule>> learningRuleClassList = new ArrayList<Class<? extends SigmoidDeltaRule>>();
		learningRuleClassList.add(BackPropagation.class);
		learningRuleClassList.add(MomentumBackpropagation.class);
		learningRuleClassList.add(DynamicBackPropagation.class);
		return learningRuleClassList;
	}

	public static List<TransferFunctionType> getTransferFunctionTypeList() {
		List<TransferFunctionType> transferFunctionTypeList = new ArrayList<TransferFunctionType>();
		for (TransferFunctionType tft : TransferFunctionType.values()) {
			transferFunctionTypeList.add(tft);
		}
		return transferFunctionTypeList;
	}

	public static List<String> getLayersStringList(int inputCountNode, int outputCountNode, int minHiddenLayers,
			int maxHiddenLayers, int minHiddenNodeEachLayer, int maxHiddenNodeEachLayer) {

		LayersStringsGenerator lsg = new LayersStringsGenerator(inputCountNode, outputCountNode, minHiddenLayers,
				maxHiddenLayers, minHiddenNodeEachLayer, maxHiddenNodeEachLayer);

		return lsg.getLayersStringList();
	}

	public static List<String> getLayersStrList(int inputCountNode, int outputCountNode, int minHiddenLayers,
			int maxHiddenLayers, int minHiddenNodeEachLayer, int maxHiddenNodeEachLayer) {

		List<String> layersStrList = new ArrayList<String>();

		layersStrList.add(inputCountNode + " 2 " + outputCountNode);
		layersStrList.add(inputCountNode + " 3 " + outputCountNode);
		layersStrList.add(inputCountNode + " 4 " + outputCountNode);
		layersStrList.add(inputCountNode + " 2 3 " + outputCountNode);
		return layersStrList;
	}

	public static String getUniqueNetworkId(String leanringRuleName, double learningRate, String layersStr,
			String transferFunctionName, boolean useBias, boolean connectIO) {

		return layersStr.replace(' ', '-') + " " + leanringRuleName + " " + transferFunctionName + " " + learningRate
				+ " " + (useBias ? "1" : "0") + " " + (connectIO ? "1" : "0");
	}

}

// save trained neural network
// myMlPerceptron.save("myMlPerceptron.nnet");

// load saved neural network
// NeuralNetwork loadedMlPerceptron =
// NeuralNetwork.load("myMlPerceptron.nnet");

// test loaded neural network
// System.out.println("Testing loaded neural network");
// testNeuralNetwork(loadedMlPerceptron, trainingSet);

/*
 * public static TrainingSet<SupervisedTrainingElement> loadTrainingSet(String
 * inputFileName, int inputPatternSize, int outputPatternSize) {
 * 
 * TrainingSet<SupervisedTrainingElement> trainingSet = new
 * TrainingSet<SupervisedTrainingElement>( inputPatternSize, outputPatternSize);
 * 
 * try { File inputFile = new File(inputFileName); if (inputFile.isFile() &&
 * inputFile.canRead()) {
 * 
 * Scanner scanner = new Scanner(inputFile); double[] inputPattern,
 * outputPattern; String[] ioPatternStr = null; int index = 0;
 * 
 * while (scanner.hasNextLine()) { inputPattern = new double[inputPatternSize];
 * outputPattern = new double[outputPatternSize]; index = 0; ioPatternStr =
 * scanner.nextLine().split(" "); for (int i = 0; i < inputPatternSize; i++) {
 * inputPattern[i] = Double.parseDouble(ioPatternStr[index++]); } for (int j =
 * 0; j < outputPatternSize; j++) { outputPattern[j] =
 * Double.parseDouble(ioPatternStr[index++]); } trainingSet.addElement(new
 * SupervisedTrainingElement(inputPattern, outputPattern)); } } } catch
 * (Exception e) { e.printStackTrace(System.out); }
 * 
 * return trainingSet; }
 */

