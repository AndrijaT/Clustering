package com.andrija.clustering.stoppingcondition;

import com.andrija.clustering.names.StoppingConditionTypes;
import com.andrija.clustering.properties.ConfigProperties;
import com.andrija.clustering.solution.Solution;
import com.andrija.clustering.stoppingcondition.impl.IterationsSmallImprovementStoppingCondition;
import com.andrija.clustering.stoppingcondition.impl.IterationsStoppingCondition;
import com.andrija.clustering.stoppingcondition.impl.IterationsUnchangedStoppingCondition;

public abstract class StoppingCondition {

	private int iterationsMax;
	private int iteration;
	private long startTime;
	private long endTime;

	public StoppingCondition() {
		this.iterationsMax = ConfigProperties.getConfigProperties().getStoppingConditionIterations();
	}

	public abstract void log();

	public abstract StoppingConditionTypes getType();

	public void next(Solution solution) {
		iteration++;
	}

	public boolean isStopppingConditionMet() {
		boolean isStopppingConditionMet = iteration >= iterationsMax;
		if (isStopppingConditionMet && endTime == 0) {
			endTime = System.currentTimeMillis();
		} else if (startTime == 0) {
			startTime = System.currentTimeMillis();
		}
		return isStopppingConditionMet;
	}
	
	public long time() {
		if(endTime == 0 && startTime == 0) return 0;
		return endTime - startTime;
	}

	public static StoppingCondition createStoppingCondition() {

		StoppingConditionTypes iterator = ConfigProperties.getConfigProperties().getStoppingConditionType();

		switch (iterator) {
		case ITERATIONS:
			return new IterationsStoppingCondition();
		case ITERATIONS_SMALL_IMPROVEMENT:
			return new IterationsSmallImprovementStoppingCondition();
		case ITERATIONS_UNCHANGED:
			return new IterationsUnchangedStoppingCondition();
		default:
			return null;
		}
	}

	protected int getIterationsMax() {
		return iterationsMax;
	}

	protected void setIterationsMax(int iterationsMax) {
		this.iterationsMax = iterationsMax;
	}

	public int getIteration() {
		return iteration;
	}

	protected void setIteration(int iteration) {
		this.iteration = iteration;
	}

	protected long getStartTime() {
		return startTime;
	}

	protected void setStartTime(long startTime) {
		this.startTime = startTime;
	}

	protected long getEndTime() {
		return endTime;
	}

	protected void setEndTime(long endTime) {
		this.endTime = endTime;
	}

}
