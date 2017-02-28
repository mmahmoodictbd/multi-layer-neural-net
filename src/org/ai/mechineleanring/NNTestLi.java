package org.ai.mechineleanring;

import java.util.Arrays;

public class NNTestLi {

	private final double[] networkInput;
	private final double[] networkOutput;
	private final double[] desiredOutput;
	private final double[] outputError;

	NNTestLi(double[] networkInput, double[] networkOutput, double[] desiredOutput, double[] outputError) {

		this.networkInput = networkInput;
		this.networkOutput = networkOutput;
		this.desiredOutput = desiredOutput;
		this.outputError = outputError;

	}

	public double[] getNetworkOutput() {
		return networkOutput;
	}

	public double[] getDesiredOutput() {
		return desiredOutput;
	}

	public double[] getOutputError() {
		return outputError;
	}

	@Override
	public String toString() {

		StringBuffer sb = new StringBuffer();
		sb.append("Input: " + Arrays.toString(networkInput) + " ");
		sb.append("Output: " + Arrays.toString(networkOutput) + " ");
		sb.append("Desired Output: " + Arrays.toString(desiredOutput) + " ");
		sb.append("Error: " + Arrays.toString(outputError) + "\n");

		return sb.toString();
	}

}
