package org.ai.mechineleanring;

import java.awt.Color;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.neuroph.core.NeuralNetwork;
import org.neuroph.core.learning.SupervisedTrainingElement;
import org.neuroph.core.learning.TrainingSet;

public class NNTest {

	private final NeuralNetwork nnet;
	private final TrainingSet testSet;
	private final String networkId;

	private double totalMSE = 0;
	private List<NNTestLi> testLiList;

	NNTest(NeuralNetwork nnet, TrainingSet testSet, String networkId) {
		this.nnet = nnet;
		this.testSet = testSet;
		this.networkId = networkId;
	}

	@SuppressWarnings("unchecked")
	public void test() {

		double[] networkInput, networkOutput, desiredOutput, outputError;
		testLiList = new ArrayList<NNTestLi>();

		for (SupervisedTrainingElement testElement : (List<SupervisedTrainingElement>) testSet.elements()) {

			networkInput = testElement.getInput();
			desiredOutput = testElement.getDesiredOutput();

			nnet.setInput(networkInput);
			nnet.calculate();

			networkOutput = nnet.getOutput();
			outputError = new double[networkOutput.length];
			double patternError = 0;

			for (int i = 0; i < networkOutput.length; i++) {
				outputError[i] = networkOutput[i] - desiredOutput[i];
				patternError += outputError[i] * outputError[i];
			}
			patternError = patternError / outputError.length;
			totalMSE += patternError;

			testLiList.add(new NNTestLi(networkInput, networkOutput, desiredOutput, outputError));
		}
		totalMSE = totalMSE / testSet.size();

	}

	@Override
	public String toString() {

		StringBuffer sb = new StringBuffer();

		sb.append("[ NNTest : " + networkId + " ]\n");
		sb.append("  Total Mean Square Error : " + totalMSE + "\n");

		sb.append("  Pattern Output :\n");
		for (NNTestLi testLi : testLiList) {
			sb.append("    ");
			sb.append(testLi.toString());
		}
		return sb.toString();

	}

	public void writeInputOutputGraph() {

		final XYSeries series1 = new XYSeries("output");
		final XYSeries series2 = new XYSeries("desiredOutput");
		final XYSeries series3 = new XYSeries("error");
		int patternCounter = 0;
		for (NNTestLi testLi : testLiList) {
			patternCounter++;
			for (int i = 0; i < testLi.getNetworkOutput().length; i++) {
				series1.add(patternCounter, testLi.getNetworkOutput()[i]);
				series2.add(patternCounter, testLi.getDesiredOutput()[i]);
				series3.add(patternCounter, testLi.getOutputError()[i]);
			}
		}

		XYSeriesCollection dataset = new XYSeriesCollection();
		dataset.addSeries(series1);
		dataset.addSeries(series2);
		dataset.addSeries(series3);

		JFreeChart chart = ChartFactory.createXYLineChart("output/desiredOutput/error", "Pattern No.", "Output",
				dataset, PlotOrientation.VERTICAL, true, true, false);

		chart.setBackgroundPaint(Color.WHITE);

		final XYPlot plot = chart.getXYPlot();
		plot.setBackgroundPaint(Color.WHITE);
		plot.setDomainGridlinePaint(Color.DARK_GRAY);
		plot.setRangeGridlinePaint(Color.DARK_GRAY);

		final XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();
		renderer.setSeriesLinesVisible(0, true);
		renderer.setSeriesShapesVisible(0, true);
		renderer.setSeriesLinesVisible(1, true);
		renderer.setSeriesShapesVisible(1, true);
		renderer.setSeriesLinesVisible(2, true);
		renderer.setSeriesShapesVisible(2, true);
		plot.setRenderer(renderer);

		try {
			ChartUtilities.saveChartAsPNG(new File("inOutGraph/" + networkId), chart, 1024, 768);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public double getTotalMSE() {
		return totalMSE;
	}

	public void setTotalMSE(double totalMSE) {
		this.totalMSE = totalMSE;
	}

	public List<NNTestLi> getTestLiList() {
		return testLiList;
	}

	public void setTestLiList(List<NNTestLi> testLiList) {
		this.testLiList = testLiList;
	}

}
