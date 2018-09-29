package me.christopherwmm.terra.noise;

import me.christopherwmm.terra.Generator;

/**
 * The abstract superclass for {@link Noise} generators.
 * 
 * @param <T extends Noise>
 * @since 1.0
 * @author ChristopherWMM
 */
public abstract class NoiseGenerator<T extends Noise> extends Generator<T> {
	/**
	 * Generates the T value for the specified x and y coordinates based on the specific parameters entered into this {@link NoiseGenerator} object.
	 * 
	 * @param x The non-zero integer x coordinate of the T value being generated.
	 * @param y The non-zero integer x coordinate of the T value being generated.
	 * @return The T value for the specified x and y coordinates.
	 * @since 1.0
	 */
	protected abstract double generateNoiseValue(final int x, final int y);

	/**
	 * Generates the 2D array of T values corresponding to the parameters entered into this {@link NoiseGenerator} object.
	 * 
	 * @return The 2D array of T values corresponding to the entered parameters.
	 * @since 1.0
	 */
	protected abstract double[][] generateNoiseArray();
}
