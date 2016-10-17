package com.andrija.clustering.utility;

import com.andrija.clustering.properties.ConfigProperties;
import com.andrija.clustering.utility.impl.PointUtility2DImpl;
import com.andrija.clustering.utility.impl.PointUtility3DImpl;
import com.andrija.clustering.utility.impl.PointUtilityNDImp;

public class Utility {

	private static PointUtility pointUtility = null;

	public static PointUtility getPointUtility() {
		return pointUtility;
	}

	public static void setPointUtility(PointUtility pointUtility) {
		Utility.pointUtility = pointUtility;
	}

	public static void setDimension(int dimension) {
		switch (dimension) {
		case 2:
			if (dimension == 2)
				pointUtility = new PointUtility2DImpl();
			break;
		case 3:
			if (dimension == 3)
				pointUtility = new PointUtility3DImpl();
			break;

		default:
			pointUtility = new PointUtilityNDImp();
			break;
		}
	}

	public static void setDimension(String input) {
		if (input.equalsIgnoreCase("N"))
			pointUtility = new PointUtilityNDImp();
	}
	
	public static void setDimension() {
		setDimension(ConfigProperties.getConfigProperties().getDatasetDimension());
	}
	
}
