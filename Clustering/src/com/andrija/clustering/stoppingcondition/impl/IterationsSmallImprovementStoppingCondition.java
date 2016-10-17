package com.andrija.clustering.stoppingcondition.impl;

import org.apache.log4j.Logger;

import com.andrija.clustering.names.StoppingConditionTypes;
import com.andrija.clustering.properties.ConfigProperties;
import com.andrija.clustering.solution.Solution;
import com.andrija.clustering.stoppingcondition.StoppingCondition;

public class IterationsSmallImprovementStoppingCondition extends StoppingCondition {

	private final StoppingConditionTypes type = StoppingConditionTypes.ITERATIONS_SMALL_IMPROVEMENT;
	private Logger log = Logger.getLogger(this.getClass());
	private double limit;
	private int smallImprovementIteration;
	private double lastValue;

	public IterationsSmallImprovementStoppingCondition() {
		super();
		this.limit = 1 - ConfigProperties.getConfigProperties().getStoppingConditionLimit();
	}

	@Override
	public void next(Solution solution) {
		if (lastValue == 0 || solution.getValue() / lastValue < limit) {
			lastValue = solution.getValue();
			smallImprovementIteration = 0;
		} else {
			smallImprovementIteration++;
		}
		super.next(solution);
	}

	@Override
	public boolean isStopppingConditionMet() {
		boolean isStopppingConditionMet = smallImprovementIteration >= super.getIterationsMax();
		if (isStopppingConditionMet && super.getEndTime() == 0) {
			super.setEndTime(System.currentTimeMillis());
		} else if (super.getStartTime() == 0) {
			super.setStartTime(System.currentTimeMillis());
		}
		return isStopppingConditionMet;
	}

	@Override
	public StoppingConditionTypes getType() {
		return type;
	}

	@Override
	public void log() {
		log.info("############## ITERATION " + super.getIteration() + ", " 
				+ "small improvement iteration " + smallImprovementIteration + ", " 
				+ "Last solution value: " + lastValue);
		if (isStopppingConditionMet())
			log.warn("Stopping condition MET!");
	}
}
