package com.andrija.clustering.representation;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import javax.imageio.ImageIO;

import org.apache.log4j.Logger;

import com.andrija.clustering.model.Cluster;
import com.andrija.clustering.model.Point;
import com.andrija.clustering.model.point.PointND;
import com.andrija.clustering.properties.ConfigProperties;
import com.andrija.clustering.solution.Solution;

public class ImageProcessor {

	private static ImageProcessor instance;
	private ConfigProperties config;

	/**
	 * Original dataset image, or problem image. Used for writing to CSV from
	 * this image, so the CSV can be parsed into problem. Another usage is to
	 * hold dimensions (width and height) for writing the result images.
	 * 
	 * Initialized by {@link #writeImageFromCSV()} when dataset format is CSV,
	 * or by constructor when dataset format is JPG.
	 */
	private BufferedImage datasetImage;

	/**
	 * File for dataset.csv.
	 * 
	 * Initialized by {@link #writeCSVFromImage()} when dataset format is JPG,
	 * or by constructor when dataset format is CSV, and then used by
	 * {@link #writeImageFromCSV()}.
	 */
	private File datasetCSVFile;
	private int steps;
	private int dimension;

	/**
	 * Constructor for JPG dataset format
	 * 
	 * @throws IOException
	 */
	private ImageProcessor(BufferedImage datasetImage) throws IOException {
		this();
		this.datasetImage = datasetImage;
		writeCSVFromImage();
	}

	/**
	 * Constructor for CSV dataset format
	 */
	private ImageProcessor(File datasetCSV) {
		this();
		this.datasetCSVFile = datasetCSV;
		writeImageFromCSV();
	}

	/**
	 * constructor for d != 2 or steps == 0
	 */
	public ImageProcessor() {

		this.config = ConfigProperties.getConfigProperties();
		this.dimension = config.getDatasetDimension();
		this.steps = config.getImageReadPrecision();
	}

	public static ImageProcessor getImageProcessor() {
		if (instance == null)
			createImageProcessor();
		return instance;
	}

	private static void createImageProcessor() {
		ConfigProperties config = ConfigProperties.getConfigProperties();
		if (config.getDatasetDimension() == 2 && config.getImageReadPrecision() != 0) {
			try {
				if (config.getDatasetFormat().equalsIgnoreCase("csv")) {
					File datasetCSVFile = new File(config.getDatasetImageFilePath());
					instance = new ImageProcessor(datasetCSVFile);
				} else if (config.getDatasetFormat().equalsIgnoreCase("jpg")) {
					BufferedImage datasetImage = ImageIO.read(new File(config.getDatasetImageFilePath()));
					instance = new ImageProcessor(datasetImage);
				} else {
					throw new IllegalArgumentException("Invalid dataset format");
				}
			} catch (IOException e) {
				Logger.getLogger(ImageProcessor.class).error(e.toString());
			}
		} else {
			instance = new ImageProcessor();
		}
	}
	
	public void writeImage(Solution solution, String imageName) {
		writeImage(solution, imageName, config.getResultImageFilePath());
	}

	public void writeImage(Solution solution, String imageName, String imageDir) {
		if (dimension == 2) {
			List<List<Pixel>> clusters = new ArrayList<>();
			BufferedImage solutionImage;
			if(config.getDatasetFormat().equalsIgnoreCase("jpg") && datasetImage != null) {
				for (Cluster cluster : solution.getClusters()) {
					List<Pixel> pixels = new ArrayList<>();
					for (Point point : cluster.getPoints()) {
						PointND p = (PointND) point;
						int x = new Double(p.getAttribute(0)).intValue();
						int y = new Double(p.getAttribute(1)).intValue();
						pixels.add(new Pixel(x, y));
					}
					clusters.add(pixels);
				}
				solutionImage = new BufferedImage(datasetImage.getWidth(), datasetImage.getHeight(),
						BufferedImage.TYPE_INT_RGB);
			} else {
				double maxWidth = 0;
				double minWidth = 0;
				double maxHeight = 0;
				double minHeight = 0;
				for (Point point : solution.getPoints()) {
					PointND p = (PointND) point;
					if(p.getAttribute(0) > maxWidth) maxWidth = p.getAttribute(0);
					if(p.getAttribute(0) < minWidth) minWidth = p.getAttribute(0);
					if(p.getAttribute(0) > maxHeight) maxHeight = p.getAttribute(1);
					if(p.getAttribute(0) < minHeight) minHeight = p.getAttribute(1);
				}
				double width = Math.abs(maxWidth - minWidth);
				double height = Math.abs(maxHeight - minHeight);
				double scale = 1000 / Math.max(width, height);

				for (Cluster cluster : solution.getClusters()) {
					List<Pixel> pixels = new ArrayList<>();
					for (Point point : cluster.getPoints()) {
						PointND p = (PointND) point;
						int x = new Double(p.getAttribute(0)).intValue();
						int y = new Double(p.getAttribute(1)).intValue();
						pixels.add(new Pixel((int) ((x - minWidth) * scale + 50), (int) ((y - minHeight) * scale + 50)));
					}
					clusters.add(pixels);
				}
				
				solutionImage = new BufferedImage(1100, 1100,
						BufferedImage.TYPE_INT_RGB);
			}
			File file = new File(imageDir + imageName + ".bmp");
			try {
				this.writeImage(clusters, solutionImage, file);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	private void writeImage(List<List<Pixel>> clusters, BufferedImage solutionImage, File file) throws IOException {
		if(solutionImage == null) return;
		
		int r = (255 << 16) & 0x00FF0000;
		int g = (255 << 8) & 0x0000FF00;
		int b = 255 & 0x000000FF;
		int white = 0xFF000000 | r | g | b;

		for (int i = 0; i < solutionImage.getWidth(); i++) {
			for (int j = 0; j < solutionImage.getHeight(); j++) {
				solutionImage.setRGB(i, j, white);
			}
		}

		for (int i = 0; i < clusters.size(); i++) {
			int red = Pixel.COLOR[i % Pixel.COLOR.length][0];
			int green = Pixel.COLOR[i % Pixel.COLOR.length][1];
			int blue = Pixel.COLOR[i % Pixel.COLOR.length][2];

			int redB = (red << 16) & 0x00FF0000;
			int greenB = (green << 8) & 0x0000FF00;
			int blueB = blue & 0x000000FF;

			int colorB = 0xFF000000 | redB | greenB | blueB;
			for (Pixel pixel : clusters.get(i)) {
				solutionImage.setRGB(pixel.getX(), pixel.getY(), colorB);
				solutionImage.setRGB(pixel.getX(), pixel.getY() + 1, colorB);
				solutionImage.setRGB(pixel.getX() + 1, pixel.getY(), colorB);
				solutionImage.setRGB(pixel.getX() + 1, pixel.getY() + 1, colorB);
			}
		}

		ImageIO.write(solutionImage, "bmp", file);
	}

	private List<Pixel> readDatasetImage() throws IOException {
		List<Pixel> pixels = new ArrayList<Pixel>();
		
		/** 
		 * Getting pixel color by position x and y
		 */
		for (int i = 0; i < datasetImage.getWidth(); i += steps) {
			for (int j = 0; j < datasetImage.getHeight(); j += steps) {
				int clr = datasetImage.getRGB(i, j);
				int red = (clr & 0x00ff0000) >> 16;
				int green = (clr & 0x0000ff00) >> 8;
				int blue = clr & 0x000000ff;
				if (red < 240 || green < 240 || blue < 240) {
					pixels.add(new Pixel(i, j, red, green, blue));
				}
			}
		}
		return pixels;
	}

	private void writeCSVFromImage() throws IOException {
		StringBuilder stringBuilder = new StringBuilder();
		List<Pixel> pixels = readDatasetImage();
		long seed = System.nanoTime();
		Collections.shuffle(pixels, new Random(seed));
		for (Pixel pixel : pixels) {
			stringBuilder.append(pixel.getX()).append(",").append(pixel.getY()).append("\n");
		}
		this.datasetCSVFile = new File(config.getDatasetFilePath());
		OutputStream outputStream = new FileOutputStream(datasetCSVFile);
		outputStream.write(stringBuilder.toString().getBytes(StandardCharsets.UTF_8));
		outputStream.close();
	}

	private void writeImageFromCSV() {
		Logger.getLogger(this.getClass()).warn("Method writeImageFromCSV not implemented");
	}

	public void setDatasetImage(BufferedImage datasetImage) {
		this.datasetImage = datasetImage;
	}

	public void setDatasetCSV(File datasetCSV) {
		this.datasetCSVFile = datasetCSV;
	}
}
