package me.christopherwmm.terra.noise;

/**
 * The accepted methodologies for blending {@link Noise} objects together.
 * 
 * @since 1.0
 * @author ChristopherWMM
 */
public enum BlendMode {
	Darken((x, y) -> Math.min(x, y)),
	Multiply((x, y) -> x * y),
	ColorBurn((x, y) -> (y > 0) ? Math.max(1 - (1 - x) / y, 0) : 0),
	LinearBurn((x, y) -> Math.max(x + y - 1, 0)),

	Lighten((x, y) -> Math.max(x, y)),
	// ColorDodge((x, y) -> (x < 1) ? y / (1 - x) : 1),
	// LinearDodge((x, y) -> Math.min(x + y, 1)),
	Screen((x, y) -> 1 - (1 - x) * (1 - y)),
	Overlay((x, y) -> (x <= 0.5) ? (2 * x * y) : (1 - 2 * (1 - x) * (1 - y))),

	SoftLight((x, y) -> (y <= 0.5) ? x * (y + 0.5) : 1 - (1 - x) * (1 - (y - 0.5))),
	// HardLight((x, y) -> (y <= 0.5) ? 2 * x * y : 1 - (1 - x) * (1 - 2 * (y - 0.5))),
	// VividLight((x, y) -> (y <= 0.5) ? (y > 0) ? Math.max(1 - (1 - x) / (2 * y), 0) : 0 : (x < 1) ? y / (2 *(1 - x)) : 1),
	// LinearLight((x, y) -> (y <= 0.5) ? 1 - 2 * y + x : x + 2 * (y - 0.5)),
	// PinLight((x, y) -> (y <= 0.5) ? Math.min(x, 2 * y) : Math.max(x, 2 * (y - 0.5))),

	Difference((x, y) -> Math.abs(x - y)),
	Exclusion((x, y) -> 0.5 - 2 * (x - 0.5) * (y - 0.5));

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