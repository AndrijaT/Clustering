package com.andrija.clustering.properties;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;

public class ConfigInput {

	private final String CSV_FILE_PATH = ConfigProperties.getConfigProperties().getTestingCsvFilePath();
	private final String PROPERTIES_FILE_PATH = ConfigProperties.getConfigProperties().getTestingPropertiesFilePath();
	private BufferedReader bufferReader;
	

	public void open() throws IOException {
		bufferReader = new BufferedReader(new FileReader(CSV_FILE_PATH));
		bufferReader.readLine();
	}

	public void close() throws IOException {
		bufferReader.close();
	}

	public void next() throws IOException {
		if (bufferReader == null)
			throw new IOException();
		String line = bufferReader.readLine();
		String[] configParams = line.trim().split(",");
		String configurationString =  "config.dataset.base.dir=%s\n" +
				"config.dataset.name=%s\n" +
				"config.vns.type=%s\n" +
				"config.vns.neighborhood.size.max=%s\n" +
				"config.vns.neighborhood.type=%s\n" +
				"config.vns.skewedparam=%s\n" +
				"config.localSearch.type=%s\n" +
				"config.localSearch.neighborhood.type=%s\n" +
				"config.localSearch.neighborhood.size.max=%s\n" +
				"config.mesure.type=%s\n" +
				"config.image.precision=%s\n" +
				"config.stopping.type=%s\n" +
				"config.stopping.iterations=%s\n" +
				"config.stopping.limit=%s";

		String configuration = String.format(configurationString, configParams[0], configParams[1], configParams[2],
				configParams[3], configParams[4], configParams[5], configParams[6], configParams[7], configParams[8],
				configParams[9], configParams[10], configParams[11], configParams[12], configParams[13]);
	
		write(configuration);
		ConfigProperties.reset();
	}
	
	private void write(String content) throws IOException {
		PrintWriter out = new PrintWriter(PROPERTIES_FILE_PATH);
		out.print(content);
		out.close();
	}

	public boolean hasNext() {
		try {
			return bufferReader.ready();
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}
}
