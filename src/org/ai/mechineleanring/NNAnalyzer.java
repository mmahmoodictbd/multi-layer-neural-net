package org.ai.mechineleanring;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.ai.util.ImageUtil;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.encoders.EncoderUtil;
import org.jfree.chart.encoders.ImageFormat;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

public class NNAnalyzer {

	private final List<EnhancedNeuralNetwork> ennList;

	NNAnalyzer(List<EnhancedNeuralNetwork> ennList) {
		this.ennList = ennList;
	}

	public void writeLearningErrorGraph() {

		for (EnhancedNeuralNetwork enn : ennList) {

			NNErrorObserver errorObserver = enn.getNetworkErrorObserver();
			XYSeriesCollection dataset = new XYSeriesCollection();
			XYSeries series;
			for (int testId = 1; testId <= enn.getTotalTest(); testId++) {
				series = new XYSeries("test" + testId);
				for (LearningErrorInfo lef : errorObserver.getLearningErrorInfoList(testId)) {
					series.add(lef.getIteration(), lef.getError());
				}
				dataset.addSeries(series);
			}

			JFreeChart chart = ChartFactory.createXYLineChart("Epoch Errors", "Epoch", "Error", dataset,
					PlotOrientation.VERTICAL, true, true, false);

			chart.setBackgroundPaint(Color.WHITE);

			final XYPlot plot = chart.getXYPlot();
			plot.setBackgroundPaint(Color.WHITE);
			plot.setDomainGridlinePaint(Color.DARK_GRAY);
			plot.setRangeGridlinePaint(Color.DARK_GRAY);

			final XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();
			renderer.setSeriesLinesVisible(0, true);
			renderer.setSeriesShapesVisible(1, true);
			plot.setRenderer(renderer);

			try {
				ChartUtilities.saveChartAsPNG(new File("errorGraph/" + enn.getNetworkId()), chart, 1024, 768);
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
	}

	public void writeCombinedLearningErrorGraphByTransferFunction() {

		Map<String, List<EnhancedNeuralNetwork>> keyWiseListMap = new HashMap<String, List<EnhancedNeuralNetwork>>();
		List<EnhancedNeuralNetwork> keyWiseList;

		for (EnhancedNeuralNetwork enn : ennList) {

			String ennKey = enn.getTransferFunctionName() + " " + enn.getLearningRuleName();

			keyWiseList = keyWiseListMap.get(ennKey);
			keyWiseList = (keyWiseList == null) ? new ArrayList<EnhancedNeuralNetwork>() : keyWiseList;
			keyWiseList.add(enn);
			keyWiseListMap.put(ennKey, keyWiseList);
		}

		for (String ennKey : keyWiseListMap.keySet()) {

			keyWiseList = keyWiseListMap.get(ennKey);
			XYSeriesCollection dataset = new XYSeriesCollection();
			XYSeries series;

			for (EnhancedNeuralNetwork enn : keyWiseList) {
				series = new XYSeries(enn.getNetworkId());
				for (LearningErrorInfo lef : enn.getLearningErrorInfoList()) {
					series.add(lef.getIteration(), lef.getError());
				}
				dataset.addSeries(series);
			}

			JFreeChart chart = ChartFactory.createXYLineChart("Epoch Errors", "Epoch", "Error", dataset,
					PlotOrientation.VERTICAL, true, true, false);

			chart.setBackgroundPaint(Color.WHITE);

			final XYPlot plot = chart.getXYPlot();
			plot.setBackgroundPaint(Color.WHITE);
			plot.setDomainGridlinePaint(Color.DARK_GRAY);
			plot.setRangeGridlinePaint(Color.DARK_GRAY);

			final XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();
			renderer.setSeriesLinesVisible(0, true);
			renderer.setSeriesShapesVisible(1, true);
			plot.setRenderer(renderer);

			try {
				File dir = new File("errorGraph/");
				if (!dir.exists()) {
					dir.mkdirs();
				}
				ChartUtilities.saveChartAsPNG(new File(dir.getPath() + "/" + ennKey), chart, 1024, 768);
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
	}

	public void writeLearningErrorGraphByTransferFunction() {

		Map<String, List<EnhancedNeuralNetwork>> keyWiseListMap = new HashMap<String, List<EnhancedNeuralNetwork>>();
		List<EnhancedNeuralNetwork> keyWiseList;

		for (EnhancedNeuralNetwork enn : ennList) {

			String ennKey = enn.getTransferFunctionName() + " " + enn.getLearningRuleName();

			keyWiseList = keyWiseListMap.get(ennKey);
			keyWiseList = (keyWiseList == null) ? new ArrayList<EnhancedNeuralNetwork>() : keyWiseList;
			keyWiseList.add(enn);
			keyWiseListMap.put(ennKey, keyWiseList);
		}

		for (String ennKey : keyWiseListMap.keySet()) {

			keyWiseList = keyWiseListMap.get(ennKey);
			XYSeriesCollection dataset;
			XYSeries series;

			BufferedImage[] buffImages = new BufferedImage[keyWiseList.size()];
			int counter = 0;
			for (EnhancedNeuralNetwork enn : keyWiseList) {
				dataset = new XYSeriesCollection();
				series = new XYSeries(enn.getNetworkId());
				for (LearningErrorInfo lef : enn.getLearningErrorInfoList()) {
					series.add(lef.getIteration(), lef.getError());
				}
				dataset.addSeries(series);

				JFreeChart chart = ChartFactory.createXYLineChart("Epoch Errors", "Epoch", "Error", dataset,
						PlotOrientation.VERTICAL, true, true, false);

				chart.setBackgroundPaint(Color.WHITE);

				final XYPlot plot = chart.getXYPlot();
				plot.setBackgroundPaint(Color.WHITE);
				plot.setDomainGridlinePaint(Color.DARK_GRAY);
				plot.setRangeGridlinePaint(Color.DARK_GRAY);

				final XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();
				renderer.setSeriesLinesVisible(0, true);
				renderer.setSeriesShapesVisible(1, true);
				plot.setRenderer(renderer);

				buffImages[counter++] = chart.createBufferedImage(1024, 768);

			}
			BufferedImage combinedBufferedImage = ImageUtil.margeBufferedImage(buffImages);

			try {
				File dir = new File("errorGraph/");
				if (!dir.exists()) {
					dir.mkdirs();
				}
				OutputStream out = new BufferedOutputStream(
						new FileOutputStream(new File(dir.getPath() + "/" + ennKey + "-C")));
				EncoderUtil.writeBufferedImage(combinedBufferedImage, ImageFormat.PNG, out);

				out.close();

			} catch (IOException e) {
				e.printStackTrace();
			}

		}
	}

	public void writeCombinedLearningErrorGraphByLayerString() {

		Map<String, List<EnhancedNeuralNetwork>> keyWiseListMap = new HashMap<String, List<EnhancedNeuralNetwork>>();
		List<EnhancedNeuralNetwork> keyWiseList;

		for (EnhancedNeuralNetwork enn : ennList) {

			String ennKey = enn.getLayersStr() + " " + enn.getLearningRuleName();

			keyWiseList = keyWiseListMap.get(ennKey);
			keyWiseList = (keyWiseList == null) ? new ArrayList<EnhancedNeuralNetwork>() : keyWiseList;
			keyWiseList.add(enn);
			keyWiseListMap.put(ennKey, keyWiseList);
		}

		for (String ennKey : keyWiseListMap.keySet()) {

			keyWiseList = keyWiseListMap.get(ennKey);
			XYSeriesCollection dataset = new XYSeriesCollection();
			XYSeries series;

			for (EnhancedNeuralNetwork enn : keyWiseList) {
				series = new XYSeries(enn.getNetworkId());
				for (LearningErrorInfo lef : enn.getLearningErrorInfoList()) {
					series.add(lef.getIteration(), lef.getError());
				}
				dataset.addSeries(series);
			}

			JFreeChart chart = ChartFactory.createXYLineChart("Epoch Errors", "Epoch", "Error", dataset,
					PlotOrientation.VERTICAL, true, true, false);

			chart.setBackgroundPaint(Color.WHITE);

			final XYPlot plot = chart.getXYPlot();
			plot.setBackgroundPaint(Color.WHITE);
			plot.setDomainGridlinePaint(Color.DARK_GRAY);
			plot.setRangeGridlinePaint(Color.DARK_GRAY);

			final XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();
			renderer.setSeriesLinesVisible(0, true);
			renderer.setSeriesShapesVisible(1, true);
			plot.setRenderer(renderer);

			try {
				File dir = new File("errorGraph/");
				if (!dir.exists()) {
					dir.mkdirs();
				}
				ChartUtilities.saveChartAsPNG(new File(dir.getPath() + "/" + ennKey), chart, 1024, 768);
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
	}

	public void writeLearningErrorGraphByLayerString() {

		Map<String, List<EnhancedNeuralNetwork>> keyWiseListMap = new HashMap<String, List<EnhancedNeuralNetwork>>();
		List<EnhancedNeuralNetwork> keyWiseList;

		for (EnhancedNeuralNetwork enn : ennList) {

			String ennKey = enn.getLayersStr() + " " + enn.getLearningRuleName();

			keyWiseList = keyWiseListMap.get(ennKey);
			keyWiseList = (keyWiseList == null) ? new ArrayList<EnhancedNeuralNetwork>() : keyWiseList;
			keyWiseList.add(enn);
			keyWiseListMap.put(ennKey, keyWiseList);
		}

		for (String ennKey : keyWiseListMap.keySet()) {

			keyWiseList = keyWiseListMap.get(ennKey);
			XYSeriesCollection dataset;
			XYSeries series;

			BufferedImage[] buffImages = new BufferedImage[keyWiseList.size()];
			int counter = 0;
			for (EnhancedNeuralNetwork enn : keyWiseList) {
				dataset = new XYSeriesCollection();
				series = new XYSeries(enn.getNetworkId());
				for (LearningErrorInfo lef : enn.getLearningErrorInfoList()) {
					series.add(lef.getIteration(), lef.getError());
				}
				dataset.addSeries(series);

				JFreeChart chart = ChartFactory.createXYLineChart("Epoch Errors", "Epoch", "Error", dataset,
						PlotOrientation.VERTICAL, true, true, false);

				chart.setBackgroundPaint(Color.WHITE);

				final XYPlot plot = chart.getXYPlot();
				plot.setBackgroundPaint(Color.WHITE);
				plot.setDomainGridlinePaint(Color.DARK_GRAY);
				plot.setRangeGridlinePaint(Color.DARK_GRAY);

				final XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();
				renderer.setSeriesLinesVisible(0, true);
				renderer.setSeriesShapesVisible(1, true);
				plot.setRenderer(renderer);

				buffImages[counter++] = chart.createBufferedImage(1024, 768);

			}
			BufferedImage combinedBufferedImage = ImageUtil.margeBufferedImage(buffImages);

			try {
				File dir = new File("errorGraph/");
				if (!dir.exists()) {
					dir.mkdirs();
				}
				OutputStream out = new BufferedOutputStream(
						new FileOutputStream(new File(dir.getPath() + "/" + ennKey + "-C")));
				EncoderUtil.writeBufferedImage(combinedBufferedImage, ImageFormat.PNG, out);

				out.close();

			} catch (IOException e) {
				e.printStackTrace();
			}

		}
	}

}
