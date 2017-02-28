package org.ai.mechineleanring;

public class LearningErrorInfo {

	Integer iteration;
	Double error;

	public LearningErrorInfo(Integer iteration, Double error) {
		this.iteration = iteration;
		this.error = error;
	}

	public Double getError() {
		return error;
	}

	public Integer getIteration() {
		return iteration;
	}

	@Override
	public String toString() {
		return "LearningErrorInfo [iteration=" + iteration + ", error=" + error + "]";
	}

}
