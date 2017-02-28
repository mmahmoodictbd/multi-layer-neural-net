package org.ai.mechineleanring.comparator;

import java.util.Comparator;

import org.ai.mechineleanring.EnhancedNeuralNetwork;

public class TransferFunctionNameComparator implements Comparator<EnhancedNeuralNetwork> {

	@Override
	public int compare(EnhancedNeuralNetwork o1, EnhancedNeuralNetwork o2) {
		return o1.getTransferFunctionName().compareTo(o2.getTransferFunctionName());
	}
}
