package me.christopherwmm.terra.noise;

public enum BlendMode {
	Add((x, y) -> Math.min(x + y, 1)),

	Subtract((x, y) -> Math.max(x - y, 0)),

	Multiply((x, y) -> x * y),

	Divide((x, y) -> (y > 0) ? x / y : x),

	Overlay((x, y) -> (x < 0.5) ? (2 * x * y) : (1 - 2 * (1 - x) * (1 - y))),

	Screen((x, y) -> 1 - (1 - x) * (1 - y)),

	Darken((x, y) -> Math.min(x, y)),

	Lighten((x, y) -> Math.max(x, y));

	private final BlendCalculator calculator;

	private BlendMode(BlendCalculator calculator) {
		this.calculator = calculator;
	}

	public double blend(final double x, final double y) {
		return calculator.blend(x, y);
	}
}

@FunctionalInterface
interface BlendCalculator {
	public abstract double blend(final double x, final double y);
}