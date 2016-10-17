package com.andrija.clustering.stoppingcondition.impl;

import org.apache.log4j.Logger;

import com.andrija.clustering.names.StoppingConditionTypes;
import com.andrija.clustering.solution.Solution;
import com.andrija.clustering.stoppingcondition.StoppingCondition;

public class IterationsUnchangedStoppingCondition extends StoppingCondition {

	private final StoppingConditionTypes type = StoppingConditionTypes.ITERATIONS_UNCHANGED;
	private Logger log = Logger.getLogger(this.getClass());
	private int unchangedIteration;
	private double lastValue;

	public IterationsUnchangedStoppingCondition() {
		super();
		this.unchangedIteration = 0;
	}

	@Override
	public void next(Solution solution) {
		if (lastValue == 0 || solution.getValue() < lastValue) {
			lastValue = solution.getValue();
			unchangedIteration = 0;
		} else {
			unchangedIteration++;
		}
		super.next(solution);
	}

	@Override
	public boolean isStopppingConditionMet() {
		boolean isStopppingConditionMet = unchangedIteration >= super.getIterationsMax();
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
		log.info("############## ITERATION " + super.getIteration() + ", " + "unchanged iteration " + unchangedIteration + ", "
				+ "Last solution value: " + lastValue);
		if (isStopppingConditionMet())
			log.warn("Stopping condition MET!");
	}
}
