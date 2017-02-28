package org.ai.mechineleanring.comparator;

import java.util.Comparator;

import org.ai.mechineleanring.EnhancedNeuralNetwork;

public class LearningRuleNameComparator implements Comparator<EnhancedNeuralNetwork> {

	@Override
	public int compare(EnhancedNeuralNetwork o1, EnhancedNeuralNetwork o2) {
		return o1.getLearningRuleName().compareTo(o2.getLearningRuleName());
	}
}
