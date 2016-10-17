package com.andrija.clustering.result;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.andrija.clustering.evaluation.unknowntruth.AvargeSilhouettesEvaluation;
import com.andrija.clustering.evaluation.unknowntruth.CSMeasureEvaluation;
import com.andrija.clustering.evaluation.unknowntruth.CalinskiHarabaszIndexEvaluation;
import com.andrija.clustering.evaluation.unknowntruth.DaviesBouldinIndexEvaluation;
import com.andrija.clustering.evaluation.unknowntruth.DunnIndexEvaluation;
import com.andrija.clustering.model.Cluster;
import com.andrija.clustering.properties.ConfigProperties;
import com.andrija.clustering.solution.Solution;
import com.andrija.clustering.vnsvariants.VNS;

public class ResultWriter {

	private ConfigProperties config;
	private VNS vns;
	private File csvResultFile;
	private final Solution initialSolution;
	private final Solution optimalSolution;
	private final Solution finalSolution;
	private ResultRecord result;
	private int maxNeighborhoodArrayLength = 8;

	public ResultWriter(Solution initialSolution, Solution finalSolution, Solution optimalSolution) {
		this.config = ConfigProperties.getConfigProperties();
		this.vns = VNS.getVns();
		this.csvResultFile = new File(config.getResultFilePath());
		this.initialSolution = initialSolution.copySolution();
		this.finalSolution = finalSolution.copySolution();
		if (optimalSolution != null) {
			this.optimalSolution = optimalSolution.copySolution();
		} else {
			this.optimalSolution = null;
		}
		this.createResult();
	}

	public ResultRecord getResult() {
		return result;
	}

	public void write() throws IOException {
		FileWriter fileWriter = new FileWriter(csvResultFile, true);
		fileWriter.write(result.toString() + "\n");
		fileWriter.close();
	}

	private ResultRecord createResult() {
		result = new ResultRecord();
		result.setId(Long.toString(System.currentTimeMillis()));
		result.setDataset(config.getDatasetName());
		result.setVnsType(config.getVnsType());
		result.setLocalSearchType(config.getLocalSearchType());
		result.setNeighborhoodType(config.getVnsNeighborhoodType());
		result.setLocalSearchNeighborhoodType(config.getLocalSearchNeighborhoodType());
		result.setMesureType(config.getMesureType());
		result.setStoppingCondition(config.getStoppingConditionType());
		result.setNeighborhoodMax(config.getNeighborhoodMax());
		result.setLocalSearchNeighborhoodMax(config.getNeighborhoodLocalSearchMax());
		result.setMeasureCallsNum(finalSolution.getMeasure().getCallCounter());
		result.setStoppingConditionLimit(config.getStoppingConditionLimit());

		int[] neighborhoodSearchNum = new int[maxNeighborhoodArrayLength];
		int[] neighborhoodSuccessfulSearchNum = new int[maxNeighborhoodArrayLength];
		int[] localSearchNeighborhoodSearchNum = new int[maxNeighborhoodArrayLength];
		int[] localSearchNeighborhoodSuccessfulSearchNum = new int[maxNeighborhoodArrayLength];

		for (int i = 0; i < maxNeighborhoodArrayLength; i++) {
			if (vns.getLocalSearch().getNeighborhoodSuccessfulSearchNum().length > i) {
				neighborhoodSearchNum[i] = vns.getLocalSearch().getNeighborhoodSearchNum()[i];
			}
			if (vns.getLocalSearch().getNeighborhoodSuccessfulSearchNum().length > i) {
				neighborhoodSuccessfulSearchNum[i] = vns.getLocalSearch().getNeighborhoodSuccessfulSearchNum()[i];
			}
			if (vns.getLocalSearch().getLocalSearchNeighborhoodSearchNum().length > i) {
				localSearchNeighborhoodSearchNum[i] = vns.getLocalSearch().getLocalSearchNeighborhoodSearchNum()[i];
			}
			if (vns.getLocalSearch().getLocalSearchNeighborhoodSuccessfulSearchNum().length > i) {
				localSearchNeighborhoodSuccessfulSearchNum[i] = vns.getLocalSearch()
						.getLocalSearchNeighborhoodSuccessfulSearchNum()[i];
			}
		}
		result.setNeighborhoodSearchNum(neighborhoodSearchNum);
		result.setNeighborhoodSuccessfulSearchNum(neighborhoodSuccessfulSearchNum);
		result.setLocalSearchNeighborhoodSearchNum(localSearchNeighborhoodSearchNum);
		result.setLocalSearchNeighborhoodSuccessfulSearchNum(localSearchNeighborhoodSuccessfulSearchNum);
		result.setIteration(vns.getStoppingCondition().getIteration());
		result.setStoppingConditionIteration(config.getStoppingConditionIterations());
		result.setTime(vns.getStoppingCondition().time());
		result.setInitialValue(initialSolution.getValue());
		result.setFinalValue(finalSolution.getValue());
		List<Integer> clusterWeights = new ArrayList<>();
		for (Cluster cluster : finalSolution.getClusters()) {
			clusterWeights.add(cluster.getPoints().size());
		}
		Collections.sort(clusterWeights);	
		result.setClusterWeights(clusterWeights);
		if (optimalSolution != null) {
			result.setOptimalValue(optimalSolution.getValue());
			result.setCsEvaluationOptimal(new CSMeasureEvaluation(optimalSolution).evaluate());
			result.setCalinskiHarbaszEvaluationOptimal(new CalinskiHarabaszIndexEvaluation(optimalSolution).evaluate());
			result.setDaviesBouldinEvaluationOptimal(new DaviesBouldinIndexEvaluation(optimalSolution).evaluate());
			result.setDunnEvaluationOptimal(new DunnIndexEvaluation(optimalSolution).evaluate());
			result.setSilhouetteEvaluationOptimal(new AvargeSilhouettesEvaluation(optimalSolution).evaluate());
			result.setNumOfPoints(finalSolution.getNumOfPoints());
			result.setNumOfClusters(finalSolution.getNumOfClusters());
			List<Integer> clusterWeightsOptimal = new ArrayList<>();
			for (Cluster cluster : optimalSolution.getClusters()) {
				clusterWeightsOptimal.add(cluster.getPoints().size());
			}
			Collections.sort(clusterWeightsOptimal);
			result.setClusterWeightsOptimal(clusterWeightsOptimal);
		} else {
			result.setOptimalValue(0);
			result.setOptimalValue(0);
			result.setCsEvaluation(0);
			result.setCalinskiHarbaszEvaluation(0);
			result.setDaviesBouldinEvaluation(0);
			result.setDunnEvaluation(0);
			result.setSilhouetteEvaluation(0);
		}
		result.setCsEvaluation(new CSMeasureEvaluation(finalSolution).evaluate());
		result.setCalinskiHarbaszEvaluation(new CalinskiHarabaszIndexEvaluation(finalSolution).evaluate());
		result.setDaviesBouldinEvaluation(new DaviesBouldinIndexEvaluation(finalSolution).evaluate());
		result.setDunnEvaluation(new DunnIndexEvaluation(finalSolution).evaluate());
		result.setSilhouetteEvaluation(new AvargeSilhouettesEvaluation(finalSolution).evaluate());
		return result;
	}
}
