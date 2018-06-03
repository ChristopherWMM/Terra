package terra.noise;

import java.util.Arrays;

public class NoiseMaskGenerator implements Generator<NoiseMask> {
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

	public NoiseMaskGenerator height(int height) {
		this.height = height;
		return this;
	}

	public NoiseMaskGenerator width(int width) {
		this.width = width;
		return this;
	}

	public NoiseMaskGenerator intensity(double intensity) {
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
				mask[y][x] = Math.abs(generateMaskValue(x, y, 1) - 1);
			}
		}

		return mask;
	}

	private double generateMaskValue(int x, int y, double value) {
		double minVal = (((this.height + this.width) / 2) / 100) / (intensity * 100);
		double maxVal = (((this.height + this.width) / 2) / 100) * (intensity * 100);

		if (intensity <= .99 && intensity > 0) {
			if (calculateDistanceToEdge(x, y) <= minVal) {
				return 0;
			}
			else if (calculateDistanceToEdge(x, y) >= maxVal) {
				return interpolate(value);
			} else {
				double possibleMax = maxVal - minVal;
				double currentValue = calculateDistanceToEdge(x, y) - minVal;
				double fadeFactor = currentValue / possibleMax;

				return interpolate(value * fadeFactor);
			}
		} else if (intensity > .99) {
			return 0;
		} else {
			return value;
		}
	}

	private double interpolate(double noiseValue) {
		return noiseValue * noiseValue * noiseValue * (noiseValue * (noiseValue * 6 - 15) + 10); 
	}

	private int calculateDistanceToEdge(int x, int y) {
		int[] distances = new int[] {x, y, (this.width), (this.height), (this.width - x), (this.height - y)};
		Arrays.sort(distances);

		return distances[0];
	}
}
