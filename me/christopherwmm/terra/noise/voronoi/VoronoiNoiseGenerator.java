package me.christopherwmm.terra.noise.voronoi;

import me.christopherwmm.terra.noise.DistanceFormula;
import me.christopherwmm.terra.noise.NoiseGenerator;
import me.christopherwmm.terra.noise.mask.NoiseMask;
import me.christopherwmm.terra.noise.mask.NoiseMaskGenerator;

public class VoronoiNoiseGenerator extends NoiseGenerator<VoronoiNoise> {
	private int height;
	private int width;
	private long seed;
	private DistanceFormula distanceFormula;
	private int frequency;
	private double[][] noise;
	private double noiseMaskIntensity;
	private NoiseMask noiseMask;

	public VoronoiNoiseGenerator() {
		this.height = 512;
		this.width = 512;
		this.seed = 0;
		this.distanceFormula = DistanceFormula.Euclidean;
		this.frequency = 3;
		this.noise = new double[this.height][this.width];
		this.noiseMaskIntensity = 0;
		this.noiseMask = new NoiseMaskGenerator()
							.height(this.height)
							.width(this.width)
							.intensity(this.noiseMaskIntensity)
							.generate();
	}

	public VoronoiNoiseGenerator height(final int height) throws IllegalArgumentException {
		if (height < 1) {
			throw new IllegalArgumentException("A voronoi noise map height must be a positive, non-zero value. " + height + " is too small.");
		}

		this.height = height;
		return this;
	}

	public VoronoiNoiseGenerator width(final int width) throws IllegalArgumentException {
		if (width < 1) {
			throw new IllegalArgumentException("A voronoi noise map width must be a positive, non-zero value. " + width + " is too small.");
		}

		this.width = width;
		return this;
	}

	public VoronoiNoiseGenerator seed(final long seed) {
		this.seed = seed;
		return this;
	}

	public VoronoiNoiseGenerator noiseMask(final double noiseMaskIntensity) throws IllegalArgumentException {
		if (noiseMaskIntensity < 0 || noiseMaskIntensity > 1) {
			throw new IllegalArgumentException("A voronoi mask intensity must be a positive value between zero and one. " + noiseMaskIntensity + " is outside that interval.");
		}

		this.noiseMaskIntensity = noiseMaskIntensity;
		return this;
	}

	public VoronoiNoiseGenerator distanceFormula(final DistanceFormula distanceMode) throws IllegalArgumentException {
		if (distanceMode == null) {
			throw new IllegalArgumentException("A voronoi noise distance mode cannot be null.");
		}

		this.distanceFormula = distanceMode;
		return this;
	}

	public VoronoiNoiseGenerator frequency(final int frequency) throws IllegalArgumentException {
		if (frequency < 1) {
			throw new IllegalArgumentException("A voronoi noise map initial frequency must be a positive, non-zero value. " + frequency + " is too small.");
		}

		this.frequency = frequency;
		return this;
	}

	@Override
	public VoronoiNoise generate() {
		this.noiseMask = new NoiseMaskGenerator()
				.height(this.height)
				.width(this.width)
				.intensity(this.noiseMaskIntensity)
				.generate();

		this.noise = generateNoiseArray();

		return new VoronoiNoise(this.height, this.width, this.seed, this.noise, this.noiseMask, this.distanceFormula, this.frequency);
	}

	@Override
	protected double generateNoiseValue(final int x, final int y) {
		double adjustedX = (x / (double) this.height) * frequency;
		double adjustedY = (y / (double) this.width) * frequency;

		int flooredX = (int) adjustedX;
		int flooredY = (int) adjustedY;

		double minimumDistance = Double.MAX_VALUE;

		int xNoiseValue = 0;
		int yNoiseValue = 0;

		for(int currentY = flooredY - 2; currentY <= flooredY + 2; currentY++) {
			for(int currentX = flooredX - 2; currentX <= flooredX + 2; currentX++) {
				double currentCellValue = calculateCellValue(currentX, currentY, this.seed);
				double currentXNoise = currentX + currentCellValue;
				double currentYNoise = currentY + currentCellValue;

				double currentDistance = calculateDistance(currentXNoise, currentYNoise, adjustedX, adjustedY, this.distanceFormula);

				if (currentDistance < minimumDistance) {
					minimumDistance = currentDistance;
					xNoiseValue = (int) currentXNoise;
					yNoiseValue = (int) currentYNoise;
				}
			}
		}

		return calculateCellValue(xNoiseValue, yNoiseValue, this.seed);
	}

	@Override
	protected double[][] generateNoiseArray() {
		double[][] noise = new double[this.height][this.width];
		double[][] maskNoise = this.noiseMask.getMask();

		for (int y = 0; y < this.height; y++) {
			for (int x = 0; x < this.width; x++) {
				noise[y][x] = generateNoiseValue(x, y);
				noise[y][x] = Math.max(0, noise[y][x] - maskNoise[y][x]);
			}
		}

		return noise;
	}

	private double calculateDistance(final double x1, final double y1, final double x2, final double y2, final DistanceFormula distanceFormula) {
		return distanceFormula.calculate(x1, y1, x2, y2);
	}

	private double calculateCellValue(final int x, final int y, final long seed) {
		long hash = (0x653 * x + 0x1B3B * y + 0x3F5 * seed);
		return (((hash * (hash * hash * 0xEC4D + 0x131071F) + 0x5208DD0D) & Integer.MAX_VALUE) / (double) 0x40000000) / 2;
	}
}
