package org.ai.mechineleanring;

import java.util.ArrayList;
import java.util.List;

import org.ai.util.ConfigurationFilter;
import org.ai.util.DataSet;
import org.ai.util.Util;
import org.neuroph.nnet.Neuroph;
import org.neuroph.nnet.learning.SigmoidDeltaRule;
import org.neuroph.util.TransferFunctionType;

public class DynamicNNGenerator {

	public void run() {

		Neuroph.getInstance().setFlattenNetworks(false);

		String filePath = "testset";
		String delimiter = " ";
		int inputsCount = 2;
		int outputsCount = 1;
		int minHiddenLayers = 1;
		int maxHiddenLayers = 2;
		int minHiddenNodeEachLayer = 2;
		int maxHiddenNodeEachLayer = 3;

		int maxIterations = 1000;
		double maxError = 0.001;
		double learningRate = 0.4;
		boolean connectIO = Boolean.FALSE;
		boolean useBias = Boolean.TRUE;
		String networkId;
		int totalTest = 5;

		DataSet dataSet = new DataSet(filePath, inputsCount, outputsCount, delimiter, true);
		List<Class<? extends SigmoidDeltaRule>> learningRuleClassList = Util.getLearningRuleClassList();
		List<TransferFunctionType> transferFunctionTypeList = Util.getTransferFunctionTypeList();
		List<String> layersStrList = Util.getLayersStringList(inputsCount, outputsCount, minHiddenLayers,
				maxHiddenLayers, minHiddenNodeEachLayer, maxHiddenNodeEachLayer);

		ConfigurationFilter.filterLearningRuleClassList(learningRuleClassList);
		ConfigurationFilter.filterTransferFunctionTypeList(transferFunctionTypeList);
		ConfigurationFilter.filterLayersStringList(layersStrList);

		EnhancedLearningRule elr = null;
		EnhancedNeuralNetwork enn = null;
		NNTest nnTest = null;
		List<EnhancedNeuralNetwork> ennList = new ArrayList<EnhancedNeuralNetwork>();

		for (Class<? extends SigmoidDeltaRule> lrClass : learningRuleClassList) {
			for (TransferFunctionType tft : transferFunctionTypeList) {
				for (String layersStr : layersStrList) {

					networkId = Util.getUniqueNetworkId(lrClass.getSimpleName(), learningRate, layersStr,
							tft.getTypeLabel(), useBias, connectIO);
					elr = new EnhancedLearningRule(lrClass, learningRate, maxIterations, maxError);
					enn = new EnhancedNeuralNetwork(totalTest, networkId, dataSet.getTrainingSet(), layersStr, tft,
							useBias, connectIO, elr);
					enn.learn();

					nnTest = new NNTest(enn.getNeuralNetwork(), dataSet.getTestSet(), networkId);
					nnTest.test();

					if (!Double.isNaN(enn.getTotalNetworkError())) {
						ennList.add(enn);
					}
				}
			}
		}

		NNAnalyzer analyzer = new NNAnalyzer(ennList);
		// analyzer.writeLearningErrorGraph();
		analyzer.writeLearningErrorGraphByTransferFunction();
		analyzer.writeCombinedLearningErrorGraphByTransferFunction();
		analyzer.writeLearningErrorGraphByLayerString();
		analyzer.writeCombinedLearningErrorGraphByLayerString();
		// Collections.sort(ennList, new TotalNetworkErrorComparator());
		// Collections.sort(ennList, new LayersStrComparator());
		// Collections.sort(ennList, new TransferFunctionNameComparator());
		// Collections.sort(ennList, new LearningRuleNameComparator());

		Neuroph.getInstance().shutdown();
	}
}
