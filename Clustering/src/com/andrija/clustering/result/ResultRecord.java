package com.andrija.clustering.result;

import java.util.List;

import com.andrija.clustering.names.LocalSearchTypes;
import com.andrija.clustering.names.MeasureTypes;
import com.andrija.clustering.names.NeighborhoodTypes;
import com.andrija.clustering.names.StoppingConditionTypes;
import com.andrija.clustering.names.VNSTypes;

public class ResultRecord {

	private String id;
	private String dataset;
	private VNSTypes vnsType;
	private LocalSearchTypes localSearchType;
	private NeighborhoodTypes neighborhoodType;
	private NeighborhoodTypes localSearchNeighborhoodType;
	private MeasureTypes mesureType;

	private StoppingConditionTypes stoppingCondition;
	private int neighborhoodMax;
	private int localSearchNeighborhoodMax;

	private long measureCallsNum;
	private int[] neighborhoodSearchNum;
	private int[] neighborhoodSuccessfulSearchNum;
	private int[] localSearchNeighborhoodSearchNum;
	private int[] localSearchNeighborhoodSuccessfulSearchNum;
	private int iteration;
	private int stoppingConditionIteration;
	private double stoppingConditionLimit;
	private long time;

	private double initialValue;
	private double finalValue;
	private double optimalValue;

	private double csEvaluation;
	private double calinskiHarbaszEvaluation;
	private double daviesBouldinEvaluation;
	private double dunnEvaluation;
	private double silhouetteEvaluation;

	private double csEvaluationOptimal;
	private double calinskiHarbaszEvaluationOptimal;
	private double daviesBouldinEvaluationOptimal;
	private double dunnEvaluationOptimal;
	private double silhouetteEvaluationOptimal;

	private int numOfPoints;
	private int numOfClusters;
	private List<Integer> clusterWeights;
	private List<Integer> clusterWeightsOptimal;

	@Override
	public String toString() {
		StringBuffer s = new StringBuffer();
		s.append(id).append(",");
		s.append(dataset).append(",");
		s.append(vnsType).append(",");
		s.append(localSearchType).append(",");
		s.append(neighborhoodType).append(",");
		s.append(localSearchNeighborhoodType).append(",");
		s.append(mesureType).append(",");

		s.append(stoppingCondition).append(",");
		s.append(iteration).append(",");
		s.append(stoppingConditionIteration).append(",");
		s.append(stoppingConditionLimit).append(",");
		s.append(time).append(",");
		s.append(measureCallsNum).append(",");

		s.append(initialValue).append(",");
		s.append(finalValue).append(",");
		s.append(optimalValue).append(",");
		s.append(numOfPoints).append(",");
		s.append(numOfClusters).append(",");

		s.append("[");
		for (Integer clusterWeight : clusterWeights) {
			s.append(clusterWeight).append(".");
		}
		s.append("],");

		if (clusterWeightsOptimal != null) {
			s.append("[");
			for (Integer clusterWeight : clusterWeightsOptimal) {
				s.append(clusterWeight).append(".");
			}
			s.append("],");
		} else {
			s.append("[");
			for (int i = 0; i < numOfClusters; i++) {
				s.append("-").append(".");
			}
			s.append("],");
		}

		s.append(csEvaluation).append(",");
		s.append(csEvaluationOptimal).append(",");
		s.append(calinskiHarbaszEvaluation).append(",");
		s.append(calinskiHarbaszEvaluationOptimal).append(",");
		s.append(daviesBouldinEvaluation).append(",");
		s.append(daviesBouldinEvaluationOptimal).append(",");
		s.append(dunnEvaluation).append(",");
		s.append(dunnEvaluationOptimal).append(",");
		s.append(silhouetteEvaluation).append(",");
		s.append(silhouetteEvaluationOptimal).append(",");

		s.append(neighborhoodMax).append(",");
		s.append(localSearchNeighborhoodMax).append(",");

		for (int i : neighborhoodSearchNum) {
			s.append(i).append(",");
		}
		for (int i : neighborhoodSuccessfulSearchNum) {
			s.append(i).append(",");
		}
		for (int i : localSearchNeighborhoodSearchNum) {
			s.append(i).append(",");
		}
		for (int i : localSearchNeighborhoodSuccessfulSearchNum) {
			s.append(i).append(",");
		}
		return s.toString();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDataset() {
		return dataset;
	}

	public void setDataset(String dataset) {
		this.dataset = dataset;
	}

	public VNSTypes getVnsType() {
		return vnsType;
	}

	public void setVnsType(VNSTypes vnsType) {
		this.vnsType = vnsType;
	}

	public StoppingConditionTypes getStoppingCondition() {
		return stoppingCondition;
	}

	public void setStoppingCondition(StoppingConditionTypes stoppingCondition) {
		this.stoppingCondition = stoppingCondition;
	}

	public int getNeighborhoodMax() {
		return neighborhoodMax;
	}

	public void setNeighborhoodMax(int neighborhoodMax) {
		this.neighborhoodMax = neighborhoodMax;
	}

	public int getLocalSearchNeighborhoodMax() {
		return localSearchNeighborhoodMax;
	}

	public void setLocalSearchNeighborhoodMax(int localSearchNeighborhoodMax) {
		this.localSearchNeighborhoodMax = localSearchNeighborhoodMax;
	}

	public long getMeasureCallsNum() {
		return measureCallsNum;
	}

	public void setMeasureCallsNum(long measureCallsNum) {
		this.measureCallsNum = measureCallsNum;
	}

	public int[] getNeighborhoodSearchNum() {
		return neighborhoodSearchNum;
	}

	public void setNeighborhoodSearchNum(int[] neighborhoodSearchNum) {
		this.neighborhoodSearchNum = neighborhoodSearchNum;
	}

	public int[] getNeighborhoodSuccessfulSearchNum() {
		return neighborhoodSuccessfulSearchNum;
	}

	public void setNeighborhoodSuccessfulSearchNum(int[] neighborhoodSuccessfulSearchNum) {
		this.neighborhoodSuccessfulSearchNum = neighborhoodSuccessfulSearchNum;
	}

	public int[] getLocalSearchNeighborhoodSearchNum() {
		return localSearchNeighborhoodSearchNum;
	}

	public void setLocalSearchNeighborhoodSearchNum(int[] localSearchNeighborhoodSearchNum) {
		this.localSearchNeighborhoodSearchNum = localSearchNeighborhoodSearchNum;
	}

	public int[] getLocalSearchNeighborhoodSuccessfulSearchNum() {
		return localSearchNeighborhoodSuccessfulSearchNum;
	}

	public void setLocalSearchNeighborhoodSuccessfulSearchNum(int[] localSearchNeighborhoodSuccessfulSearchNum) {
		this.localSearchNeighborhoodSuccessfulSearchNum = localSearchNeighborhoodSuccessfulSearchNum;
	}

	public int getIteration() {
		return iteration;
	}

	public void setIteration(int iteration) {
		this.iteration = iteration;
	}

	public int getStoppingConditionIteration() {
		return stoppingConditionIteration;
	}

	public void setStoppingConditionIteration(int stoppingConditionIteration) {
		this.stoppingConditionIteration = stoppingConditionIteration;
	}

	public long getTime() {
		return time;
	}

	public void setTime(long time) {
		this.time = time;
	}

	public double getInitialValue() {
		return initialValue;
	}

	public void setInitialValue(double initialValue) {
		this.initialValue = initialValue;
	}

	public double getFinalValue() {
		return finalValue;
	}

	public void setFinalValue(double finalValue) {
		this.finalValue = finalValue;
	}

	public double getOptimalValue() {
		return optimalValue;
	}

	public void setOptimalValue(double optimalValue) {
		this.optimalValue = optimalValue;
	}

	public LocalSearchTypes getLocalSearchType() {
		return localSearchType;
	}

	public void setLocalSearchType(LocalSearchTypes localSearchType) {
		this.localSearchType = localSearchType;
	}

	public NeighborhoodTypes getNeighborhoodType() {
		return neighborhoodType;
	}

	public void setNeighborhoodType(NeighborhoodTypes neighborhoodType) {
		this.neighborhoodType = neighborhoodType;
	}

	public NeighborhoodTypes getLocalSearchNeighborhoodType() {
		return localSearchNeighborhoodType;
	}

	public void setLocalSearchNeighborhoodType(NeighborhoodTypes localSearchNeighborhoodType) {
		this.localSearchNeighborhoodType = localSearchNeighborhoodType;
	}

	public MeasureTypes getMesureType() {
		return mesureType;
	}

	public void setMesureType(MeasureTypes mesureType) {
		this.mesureType = mesureType;
	}

	public double getCsMeasure() {
		return csEvaluation;
	}

	public double getCsEvaluation() {
		return csEvaluation;
	}

	public void setCsEvaluation(double csEvaluation) {
		this.csEvaluation = csEvaluation;
	}

	public double getCalinskiHarbaszEvaluation() {
		return calinskiHarbaszEvaluation;
	}

	public void setCalinskiHarbaszEvaluation(double calinskiHarbaszEvaluation) {
		this.calinskiHarbaszEvaluation = calinskiHarbaszEvaluation;
	}

	public double getDaviesBouldinEvaluation() {
		return daviesBouldinEvaluation;
	}

	public void setDaviesBouldinEvaluation(double daviesBouldinEvaluation) {
		this.daviesBouldinEvaluation = daviesBouldinEvaluation;
	}

	public double getDunnEvaluation() {
		return dunnEvaluation;
	}

	public void setDunnEvaluation(double dunnEvaluation) {
		this.dunnEvaluation = dunnEvaluation;
	}

	public double getSilhouetteEvaluation() {
		return silhouetteEvaluation;
	}

	public void setSilhouetteEvaluation(double silhouetteEvaluation) {
		this.silhouetteEvaluation = silhouetteEvaluation;
	}

	public double getCsEvaluationOptimal() {
		return csEvaluationOptimal;
	}

	public void setCsEvaluationOptimal(double csEvaluationOptimal) {
		this.csEvaluationOptimal = csEvaluationOptimal;
	}

	public double getCalinskiHarbaszEvaluationOptimal() {
		return calinskiHarbaszEvaluationOptimal;
	}

	public void setCalinskiHarbaszEvaluationOptimal(double calinskiHarbaszEvaluationOptimal) {
		this.calinskiHarbaszEvaluationOptimal = calinskiHarbaszEvaluationOptimal;
	}

	public double getDaviesBouldinEvaluationOptimal() {
		return daviesBouldinEvaluationOptimal;
	}

	public void setDaviesBouldinEvaluationOptimal(double daviesBouldinEvaluationOptimal) {
		this.daviesBouldinEvaluationOptimal = daviesBouldinEvaluationOptimal;
	}

	public double getDunnEvaluationOptimal() {
		return dunnEvaluationOptimal;
	}

	public void setDunnEvaluationOptimal(double dunnEvaluationOptimal) {
		this.dunnEvaluationOptimal = dunnEvaluationOptimal;
	}

	public double getSilhouetteEvaluationOptimal() {
		return silhouetteEvaluationOptimal;
	}

	public void setSilhouetteEvaluationOptimal(double silhouetteEvaluationOptimal) {
		this.silhouetteEvaluationOptimal = silhouetteEvaluationOptimal;
	}

	public List<Integer> getClusterWeights() {
		return clusterWeights;
	}

	public void setClusterWeights(List<Integer> clusterWeights) {
		this.clusterWeights = clusterWeights;
	}

	public List<Integer> getClusterWeightsOptimal() {
		return clusterWeightsOptimal;
	}

	public void setClusterWeightsOptimal(List<Integer> clusterWeightsOptimal) {
		this.clusterWeightsOptimal = clusterWeightsOptimal;
	}

	public int getNumOfPoints() {
		return numOfPoints;
	}

	public void setNumOfPoints(int numOfPoints) {
		this.numOfPoints = numOfPoints;
	}

	public int getNumOfClusters() {
		return numOfClusters;
	}

	public void setNumOfClusters(int numOfClusters) {
		this.numOfClusters = numOfClusters;
	}

	public double getStoppingConditionLimit() {
		return stoppingConditionLimit;
	}

	public void setStoppingConditionLimit(double stoppingConditionLimit) {
		this.stoppingConditionLimit = stoppingConditionLimit;
	}
}
