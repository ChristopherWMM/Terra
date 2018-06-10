package terra.noise.mask;

import java.util.Arrays;

import terra.noise.Generator;

public class NoiseMaskGenerator extends Generator<NoiseMask> {
	private int height;
	private int width;
	private double intensity;
	private double[][] mask;

	public NoiseMaskGenerator() {
		this.height = 512;
		this.width = 512;
		this.intensity = 0;
		this.mask = new double[this.height][this.width];
	}

	public NoiseMaskGenerator height(int height) throws IllegalArgumentException {
		if (height < 1) {
			throw new IllegalArgumentException("A noise mask height must be a positive, non-zero value. " + height + " is too small.");
		}

		this.height = height;
		return this;
	}

	public NoiseMaskGenerator width(int width) throws IllegalArgumentException {
		if (width < 1) {
			throw new IllegalArgumentException("A noise mask width must be a positive, non-zero value. " + width + " is too small.");
		}

		this.width = width;
		return this;
	}

	public NoiseMaskGenerator intensity(double intensity) throws IllegalArgumentException {
		if (intensity < 0.0 || intensity > 1.0) {
			throw new IllegalArgumentException("A noise mask intensity must be a positive value between zero and one. " + intensity + " is outside that interval.");
		}

		this.intensity = intensity;
		return this;
	}

	@Override
	public NoiseMask generate() {
		this.mask = generateMaskArray();
		return new NoiseMask(this.height, this.width, this.intensity, this.mask);
	}

	private double[][] generateMaskArray() {
		double[][] mask = new double[height][width];

		for (int y = 0; y < this.height; y++) {
			for (int x = 0; x < this.width; x++) {
				mask[y][x] = Math.abs(generateMaskValue(x, y) - 1);
			}
		}

		return mask;
	}

	private double generateMaskValue(int x, int y) {
		double value = 1.0;
		double minVal = (((this.height + this.width) / 2) / 100) / (intensity * 100);
		double maxVal = (((this.height + this.width) / 2) / 100) * (intensity * 100);

		if (intensity <= .99 && intensity > 0) {
			double distanceToNearestEdge = calculateDistanceToNearestEdge(x, y);

			if (distanceToNearestEdge <= minVal) {
				return 0;
			} else if (distanceToNearestEdge >= maxVal) {
				return value;
			} else {
				double possibleMax = maxVal - minVal;
				double currentValue = calculateDistanceToNearestEdge(x, y) - minVal;
				double fadeFactor = currentValue / possibleMax;
				double adjustedValue = fade(value * fadeFactor);

				return adjustedValue;
			}
		} else if (intensity > .99) {
			return 0;
		} else {
			return value;
		}
	}

	private double fade(double noiseValue) {
		return noiseValue * noiseValue * noiseValue * (noiseValue * (noiseValue * 6 - 15) + 10); 
	}

	private int calculateDistanceToNearestEdge(int x, int y) {
		int[] distances = new int[] {x, y, (this.width), (this.height), (this.width - x), (this.height - y)};
		Arrays.sort(distances);

		return distances[0];
	}
}
