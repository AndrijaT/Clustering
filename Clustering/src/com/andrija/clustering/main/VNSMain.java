package com.andrija.clustering.main;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Logger;

import com.andrija.clustering.model.Problem;
import com.andrija.clustering.properties.ConfigInput;
import com.andrija.clustering.representation.ImageProcessor;
import com.andrija.clustering.result.ResultWriter;
import com.andrija.clustering.solution.Solution;
import com.andrija.clustering.utility.Utility;
import com.andrija.clustering.vnsvariants.VNS;

public class VNSMain {

	private static Logger log = Logger.getLogger(VNSMain.class);
	private static int i = 0;

	public static void main(String[] args) {
		try {
			go();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
//		try {
//		for(int i = 0; i < 20; i++)
//			go();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
		
//		test();
	}
	
	@SuppressWarnings("unused")
	private static void test() {
		int runs = 10;
		long starTime = System.currentTimeMillis();
		ConfigInput input = new ConfigInput();
		try {
			input.open();
			int row = 1;
			while (input.hasNext()) {
				input.next();
				row++;
				long now = System.currentTimeMillis();
				System.out.print("\n");
				System.out.print((now - starTime) / (3600 * 1000) % 60 + ":" + (now - starTime) / (60 * 1000) % 60 + ":" + (now - starTime) / (1000) % 60);
				System.out.print(" | row;" + row + ". ");
				int run = 0;
				while (run < runs) {
					log.info("RUN: " + run);
					go();
					System.out.print(run + ",");
					run++;
				}
			}
			input.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	private static void go() throws IOException {
		log.info("-----------------------------------");
		log.info("POCETAK");
		Solution initialSolution = generateInitialSolution();
		Solution finalSolution = findFinalSolution(initialSolution);
		Solution optimalSolution = findOptimalSolution();
		log.info("KRAJ");
		ResultWriter writer = new ResultWriter(initialSolution, finalSolution, optimalSolution);
		writer.write();
		
	}

	private static Solution generateInitialSolution(/* ResultWriter result */) {
		ImageProcessor imageProcessor = ImageProcessor.getImageProcessor();
		Utility.setDimension("N");
		Problem problem = Utility.getPointUtility().generateProblem();
		log.info("Generating problem");
		Solution initialSolution = Solution.generateInitialSolution(problem);
		DateFormat dateFormat = new SimpleDateFormat("yy-MM-dd_HH-mm-ss-SSS");
	    Date now = new Date();
		imageProcessor.writeImage(initialSolution, "in_" + dateFormat.format(now) + "_" + i, "C:\\image_results\\");
		initialSolution.log();
		return initialSolution;
	}

	private static Solution findFinalSolution(Solution initialSolution) {
		ImageProcessor imageProcessor = ImageProcessor.getImageProcessor();
		VNS vns = VNS.createVNS(initialSolution);
		Solution finalSolution = vns.execute();
		log.info("FINAL SOLUTION:");
		finalSolution.log();
		DateFormat dateFormat = new SimpleDateFormat("yy-MM-dd_HH-mm-ss-SSS");
	    Date now = new Date();
		imageProcessor.writeImage(finalSolution, "fin_" + dateFormat.format(now) + "_" + i++, "C:\\image_results\\");
		return finalSolution;
	}

	private static Solution findOptimalSolution() {
		Utility.setDimension("N");
		log.info("Generating problem");
		Problem problem = Utility.getPointUtility().generateProblem();
		log.info("Generating optimal solution");
		Solution optimalSolution = Solution.generateOptimalSolution(problem);
		if (optimalSolution == null) {
			log.warn("CAN'T FIND OPTIMAL SOLUTION");
		} else {
			log.info("OPTIMAL SOLUTION:");
			optimalSolution.log();
		}
		return optimalSolution;
	}
	
}
