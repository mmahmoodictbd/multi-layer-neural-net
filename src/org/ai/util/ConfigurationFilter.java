package org.ai.util;

import java.util.List;

import org.neuroph.nnet.learning.DynamicBackPropagation;
import org.neuroph.nnet.learning.MomentumBackpropagation;
import org.neuroph.nnet.learning.SigmoidDeltaRule;
import org.neuroph.util.TransferFunctionType;

public class ConfigurationFilter {

	public static void filterLearningRuleClassList(List<Class<? extends SigmoidDeltaRule>> learningRuleClassList) {
		// learningRuleClassList.remove(BackPropagation.class);
		learningRuleClassList.remove(MomentumBackpropagation.class);
		learningRuleClassList.remove(DynamicBackPropagation.class);
	}

	public static void filterTransferFunctionTypeList(List<TransferFunctionType> transferFunctionTypeList) {

		//transferFunctionTypeList.remove(TransferFunctionType.SGN);

		// transferFunctionTypeList.remove(TransferFunctionType.STEP);
		// transferFunctionTypeList.remove(TransferFunctionType.GAUSSIAN);
	}

	public static void filterLayersStringList(List<String> layersStringList) {
		layersStringList.remove("2 2 1");
		layersStringList.remove("2 3 2 1");
	}
}
