package com.andrija.clustering.properties;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

import com.andrija.clustering.names.LocalSearchTypes;
import com.andrija.clustering.names.MeasureTypes;
import com.andrija.clustering.names.NeighborhoodTypes;
import com.andrija.clustering.names.StoppingConditionTypes;
import com.andrija.clustering.names.VNSTypes;

public class ConfigProperties {

	private static ConfigProperties instance;

	private Properties properties;
	private final String projectDir;
	private final String datasetBaseDir;
	private final String datasetDir;
	private final String datasetName;
	private final String datasetFormat;

	private final int datasetDimension;
	private final int numOfPoints;
	private final int numOfClusters;
	private final int neighborhoodMax;
	private final int neighborhoodLocalSearchMax;
	private final int[] optimalClusterSizes;
	private final int imageReadPrecision;
	private final int stoppingConditionIterations;
	private final double stoppingConditionLimit;
	private final double skewedParameter;

	private final VNSTypes vnsType;
	private final LocalSearchTypes localSearchType;
	private final NeighborhoodTypes vnsNeighborhoodType;
	private final NeighborhoodTypes localSearchNeighborhoodType;
	private final MeasureTypes mesureType;
	private final StoppingConditionTypes stoppingConditionType;

	private ConfigProperties() {
		this.projectDir = System.getProperty("user.dir");
		this.properties = new Properties();
		loadProperties();

		this.datasetBaseDir = properties.getProperty("config.dataset.base.dir");
		this.datasetDir = properties.getProperty("config.dataset.name");
		this.datasetName = properties.getProperty("config.dataset.name");
		this.datasetFormat = properties.getProperty("dataset.format");
		this.datasetDimension = Integer.valueOf(properties.getProperty("dataset.dimension"));
		this.numOfClusters = Integer.valueOf(properties.getProperty("dataset.clusternumber"));
		this.neighborhoodMax = Integer.valueOf(properties.getProperty("config.vns.neighborhood.size.max"));
		this.neighborhoodLocalSearchMax = Integer
				.valueOf(properties.getProperty("config.localSearch.neighborhood.size.max"));
		this.stoppingConditionIterations = Integer.valueOf(properties.getProperty("config.stopping.iterations"));
		this.stoppingConditionLimit = Double.parseDouble(properties.getProperty("config.stopping.limit"));
		this.skewedParameter = Double.parseDouble(properties.getProperty("config.vns.skewedparam"));
		this.vnsType = VNSTypes.fromString(properties.getProperty("config.vns.type"));
		this.vnsNeighborhoodType = NeighborhoodTypes.fromString(properties.getProperty("config.vns.neighborhood.type"));
		this.localSearchType = LocalSearchTypes.fromString(properties.getProperty("config.localSearch.type"));
		this.localSearchNeighborhoodType = NeighborhoodTypes
				.fromString(properties.getProperty("config.localSearch.neighborhood.type"));
		this.mesureType = MeasureTypes.fromString(properties.getProperty("config.mesure.type"));
		this.stoppingConditionType = StoppingConditionTypes.fromString(properties.getProperty("config.stopping.type"));
		String imageReadPrecisionString = properties.getProperty("config.image.precision").trim();
		if (imageReadPrecisionString.isEmpty()) {
			this.imageReadPrecision = 0;
		} else {
			this.imageReadPrecision = Integer.parseInt(imageReadPrecisionString);
		}

		String[] optimalClustersSizeString = properties.getProperty("dataset.optimal.clusters.size").trim().split(",");
		if (optimalClustersSizeString.length == numOfClusters) {
			optimalClusterSizes = new int[numOfClusters];
			for (int i = 0; i < numOfClusters; i++) {
				optimalClusterSizes[i] = Integer.parseInt(optimalClustersSizeString[i].trim());
			}
			int numOfPoints = 0;
			for (int i = 0; i < numOfClusters; i++) {
				numOfPoints += optimalClusterSizes[i];
			}
			this.numOfPoints = numOfPoints;
		} else {
			optimalClusterSizes = null;
			numOfPoints = 0;
		}
	}

	private void loadProperties() {
		try {
			FileReader fileReader = new FileReader(new File(projectDir + "\\properties\\config.properties"));
			properties.load(fileReader);
			properties.putAll(loadDataProperties());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private Properties loadDataProperties() {
		String datasetName = properties.getProperty("config.dataset.name");
		Properties datasetProperties = new Properties();
		try {
			FileReader fileReader = new FileReader(
					new File(projectDir + "\\properties\\datasets\\dataset_" + datasetName + ".properties"));
			datasetProperties.load(fileReader);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return datasetProperties;
	}

	public static ConfigProperties getConfigProperties() {
		return (instance != null) ? instance : new ConfigProperties();
	}

	public static void reset() {
		instance = null;
	}

	public Properties getProperties() {
		return properties;
	}

	public void setProperties(Properties properties) {
		this.properties = properties;
	}

	public String getProjectDir() {
		return projectDir;
	}

	public String getDatasetBaseDir() {
		return datasetBaseDir;
	}

	public String getDatasetDir() {
		return datasetDir;
	}

	public String getDatasetName() {
		return datasetName;
	}

	public String getResultFilePath() {
		return projectDir + "//result//result.csv";
	}

	public String getDatasetFilePath() {
		return projectDir + "//" + datasetBaseDir + "//" + datasetDir + "//" + datasetName + ".csv";
	}

	public String getResultImageFilePath() {
		return projectDir + "//" + datasetBaseDir + "//" + datasetDir + "//";
	}

	public String getDatasetImageFilePath() {
		return projectDir + "//" + datasetBaseDir + "//" + datasetDir + "//" + datasetName + ".jpg";
	}

	public String getTestingCsvFilePath() {
		return projectDir + "//properties//testConfiguration.csv";
	}
	
	public String getTestingPropertiesFilePath() {
		return projectDir + "//properties//config.properties";
	}
	
	public String getDatasetFormat() {
		return datasetFormat;
	}

	public int getDatasetDimension() {
		return datasetDimension;
	}

	public int getNumOfPoints() {
		return numOfPoints;
	}

	public int getNumOfClusters() {
		return numOfClusters;
	}

	public int getNeighborhoodMax() {
		return neighborhoodMax;
	}

	public int getNeighborhoodLocalSearchMax() {
		return neighborhoodLocalSearchMax;
	}

	public int[] getOptimalClusterSizes() {
		return optimalClusterSizes;
	}

	public VNSTypes getVnsType() {
		return vnsType;
	}

	public LocalSearchTypes getLocalSearchType() {
		return localSearchType;
	}

	public NeighborhoodTypes getVnsNeighborhoodType() {
		return vnsNeighborhoodType;
	}

	public NeighborhoodTypes getLocalSearchNeighborhoodType() {
		return localSearchNeighborhoodType;
	}

	public MeasureTypes getMesureType() {
		return mesureType;
	}

	public int getImageReadPrecision() {
		return imageReadPrecision;
	}

	public int getStoppingConditionIterations() {
		return stoppingConditionIterations;
	}

	public double getStoppingConditionLimit() {
		return stoppingConditionLimit;
	}

	public StoppingConditionTypes getStoppingConditionType() {
		return stoppingConditionType;
	}

	public double getSkewedParameter() {
		return skewedParameter;
	}
}
