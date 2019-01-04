package me.christopherwmm.terra.noise;

/**
 * The accepted methodologies for calculating distance within a {@link NoiseGenerator}.
 * 
 * @since 1.0
 * @author ChristopherWMM
 */
public enum DistanceFormula {
	/** The typical straight-line distance between two points in Euclidean space. */
	Euclidean((x1, y1, x2, y2) -> Math.sqrt(Math.pow((x1 - x2), 2.0) + Math.pow((y1 - y2) , 2.0))),

	/** The non-diagonal distance between two points in a fixed Cartesian coordinate space. */
	Manhattan((x1, y1, x2, y2) -> Math.abs(x1 - x2) + Math.abs(y1 - y2)),

	/** The generalization of both the Euclidean distance and the Manhattan distance. */
	Minkowski((x1, y1, x2, y2) -> {
			double p = 3.0;
			return Math.pow(Math.pow(Math.abs(x1 - x2), p) + Math.pow(Math.abs(y1 - y2), p), (1 / p));
		}),

	/** The maximum differences between two points along all of their coordinate axis. */
	Chebyshev((x1, y1, x2, y2) -> Math.max(Math.abs(x1 - x2), Math.abs(y1 - y2)));

	private final DistanceCalculator calculator;

	private DistanceFormula(DistanceCalculator calculator) {
		this.calculator = calculator;
	}

	/**
	 * Calculates the distance between two points using this {@link DistanceFormula}.
	 * 
	 * @param x1 The x coordinate of the first point.
	 * @param y1 The y coordinate of the first point.
	 * @param x2 The x coordinate of the second point.
	 * @param y2 The y coordinate of the second point.
	 * 
	 * @return The distance between two points using this {@link DistanceFormula}.
	 * @since 1.0
	 */
	public double calculate(final double x1, final double y1, final double x2, final double y2) {
		return calculator.calculate(x1, y1, x2, y2);
	}
}

@FunctionalInterface
interface DistanceCalculator {
	public abstract double calculate(final double x1, final double y1, final double x2, final double y2);
}