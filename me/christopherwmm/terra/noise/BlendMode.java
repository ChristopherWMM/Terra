package me.christopherwmm.terra.noise;

/**
 * The accepted methodologies for blending {@link Noise} objects together.
 * 
 * @since 1.0
 * @author ChristopherWMM
 */
public enum BlendMode {
	/** Returns the sum of the noise values at each position on both noise maps. Results in a lighter noise map. */
	Add((x, y) -> Math.min(x + y, 1)),

	/** Returns the difference of the noise values at each position on both noise maps. Results in a darker noise map. */
	Subtract((x, y) -> Math.max(x - y, 0)),

	/** Returns the product of the noise values at each position on both noise maps. Results in a lighter noise map. */
	Multiply((x, y) -> x * y),

	/** Returns the quotient of the noise values at each position on both noise maps. Results in a darker noise map. */
	Divide((x, y) -> (y > 0) ? x / y : x),

	/** Returns an S-curve of the noise values at each position on both noise maps. Lighter values on the first noise map become lighter and darker values on the second noise map become darker. */
	Overlay((x, y) -> (x < 0.5) ? (2 * x * y) : (1 - 2 * (1 - x) * (1 - y))),

	/** Returns the inverse product of the noise values at each position on both noise maps. */
	Screen((x, y) -> 1 - (1 - x) * (1 - y)),

	/** Returns the darker of the noise values at each position on both noise maps. */
	Darken((x, y) -> Math.min(x, y)),

	/** Returns the lighter of the noise values at each position on both noise maps. */
	Lighten((x, y) -> Math.max(x, y));

	private final BlendCalculator calculator;

	private BlendMode(BlendCalculator calculator) {
		this.calculator = calculator;
	}

	/**
	 * Calculates the blended value between two points using this {@link BlendMode}.
	 * 
	 * @param x The first point.
	 * @param y The second point.
	 * 
	 * @return The blended value between two points using this {@link BlendMode}.
	 * @since 1.0
	 */
	public double blend(final double x, final double y) {
		return calculator.blend(x, y);
	}
}

@FunctionalInterface
interface BlendCalculator {
	public abstract double blend(final double x, final double y);
}