package org.ai.mechineleanring;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;

import org.neuroph.core.learning.SupervisedLearning;

public class NNErrorObserver implements Observer {

	private final Map<String, List<LearningErrorInfo>> learningErrorInfoListMap;

	NNErrorObserver() {
		learningErrorInfoListMap = new HashMap<String, List<LearningErrorInfo>>();
	}

	@Override
	public void update(Observable arg0, Object arg1) {

		SupervisedLearning rule = (SupervisedLearning) arg0;
		String testId = rule.getNeuralNetwork().getLabel();

		List<LearningErrorInfo> learningErrorInfoList = learningErrorInfoListMap.get(testId);
		if (learningErrorInfoList == null) {
			learningErrorInfoList = new ArrayList<LearningErrorInfo>();
		}

		learningErrorInfoList.add(new LearningErrorInfo(rule.getCurrentIteration(), rule.getTotalNetworkError()));
		learningErrorInfoListMap.put(testId, learningErrorInfoList);
	}

	public List<LearningErrorInfo> getLearningErrorInfoList(int testId) {

		List<LearningErrorInfo> leiList = learningErrorInfoListMap.get(String.valueOf(testId));
		if (leiList == null) {
			return new ArrayList<LearningErrorInfo>();
		} else {
			return leiList;
		}
	}

}
