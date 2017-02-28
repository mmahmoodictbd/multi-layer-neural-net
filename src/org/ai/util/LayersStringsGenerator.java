package org.ai.util;

import java.util.ArrayList;
import java.util.List;

public class LayersStringsGenerator {

	private List<String> layersStringList;

	private final int inputCountNode;
	private final int outputCountNode;
	private final int minHiddenLayers;
	private final int maxHiddenLayers;
	private final int minHiddenNodeEachLayer;
	private final int maxHiddenNodeEachLayer;

	private int minHiddenLayersStrStartIndex;

	public LayersStringsGenerator(int inputCountNode, int outputCountNode, int minHiddenLayers, int maxHiddenLayers,
			int minHiddenNodeEachLayer, int maxHiddenNodeEachLayer) {

		this.inputCountNode = inputCountNode;
		this.outputCountNode = outputCountNode;
		this.minHiddenLayers = minHiddenLayers;
		this.maxHiddenLayers = maxHiddenLayers;
		this.minHiddenNodeEachLayer = minHiddenNodeEachLayer;
		this.maxHiddenNodeEachLayer = maxHiddenNodeEachLayer;

		generate();
	}

	public List<String> getLayersStringList() {
		return layersStringList.subList(minHiddenLayersStrStartIndex, layersStringList.size());
	}

	private void generate() {

		layersStringList = new ArrayList<String>();
		layersStringList.add(inputCountNode + "");

		List<String> newLayerList;
		newLayerList = layersStringList;

		for (int i = 0; i < maxHiddenLayers; i++) {

			if(i == minHiddenLayers - 1){
				minHiddenLayersStrStartIndex = layersStringList.size();
			}

			newLayerList = addLayerStringList(newLayerList);
			layersStringList.addAll(newLayerList);
		}
		//layersStringList.addAll(newLayerList);

		for (int i = 0; i < layersStringList.size(); i++) {
			layersStringList.set(i, layersStringList.get(i) + " " + outputCountNode);
		}

	}

	private List<String> addLayerStringList(final List<String> prevLayersStringList) {

		List<String> newLayerStringList = new ArrayList<String>();

		for (String prevLayerStr : prevLayersStringList) {
			for (int i = minHiddenNodeEachLayer; i <= maxHiddenNodeEachLayer; i++) {
				newLayerStringList.add(prevLayerStr + " " + i);
			}
		}

		return newLayerStringList;
	}
}
