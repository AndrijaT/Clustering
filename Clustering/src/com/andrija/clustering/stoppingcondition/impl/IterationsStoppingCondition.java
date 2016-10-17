package com.andrija.clustering.stoppingcondition.impl;

import org.apache.log4j.Logger;

import com.andrija.clustering.names.StoppingConditionTypes;
import com.andrija.clustering.stoppingcondition.StoppingCondition;

public class IterationsStoppingCondition extends StoppingCondition {

	private final StoppingConditionTypes type = StoppingConditionTypes.ITERATIONS; 
	private Logger log = Logger.getLogger(this.getClass());
	
	@Override
	public StoppingConditionTypes getType() {
		return type;
	}

	@Override
	public void log() {
		log.info("############## ITERATION " + super.getIteration());
		if (!isStopppingConditionMet())
			log.warn("Stopping condition MET!");
	}
}
