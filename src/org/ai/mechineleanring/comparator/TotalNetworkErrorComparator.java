package org.ai.mechineleanring.comparator;

import java.util.Comparator;

import org.ai.mechineleanring.EnhancedNeuralNetwork;

public class TotalNetworkErrorComparator implements Comparator<EnhancedNeuralNetwork> {

	@Override
	public int compare(EnhancedNeuralNetwork o1, EnhancedNeuralNetwork o2) {
		return new Double(o1.getTotalNetworkError()).compareTo(new Double(o2.getTotalNetworkError()));
	}
}
