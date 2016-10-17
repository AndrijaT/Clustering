package com.andrija.clustering.evaluation.unknowntruth;

import com.andrija.clustering.evaluation.ClusterEvaluation;
import com.andrija.clustering.evaluation.unknowntruth.internalmeasure.ScatterMeasure;
import com.andrija.clustering.solution.Solution;

/**
 * NOT TESTED!
 */
public class BanfeldRafteryIndexEvaluation implements ClusterEvaluation {

	private ScatterMeasure measure;

	public BanfeldRafteryIndexEvaluation(Solution solution) {
		this.measure = new ScatterMeasure(solution);
	}

	@Override
	public double evaluate() {
		return Math.log(measure.getBGSS() / measure.getWGSS());
	}
}
