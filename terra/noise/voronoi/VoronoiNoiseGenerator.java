package terra.noise.voronoi;

import java.util.Random;

import terra.noise.Generator;
import terra.noise.mask.NoiseMask;
import terra.noise.mask.NoiseMaskGenerator;

public class VoronoiNoiseGenerator extends Generator<VoronoiNoise> {
	private int height;
	private int width;
	private long seed;
	private VoronoiDistance distanceMode;
	private int frequency;
	private double[][] noise;
	private double noiseMaskIntensity;
	private NoiseMask noiseMask;

	private Random random;

	public VoronoiNoiseGenerator() {
		this.height = 512;
		this.width = 512;
		this.seed = 0;
		this.distanceMode = VoronoiDistance.Euclidean;
		this.frequency = 1;
		this.noise = new double[this.height][this.width];
		this.noiseMaskIntensity = 0;
		this.noiseMask = new NoiseMaskGenerator()
							.height(this.height)
							.width(this.width)
							.intensity(this.noiseMaskIntensity)
							.generate();

		this.random = new Random();
	}

	public VoronoiNoiseGenerator height(int height) throws IllegalArgumentException {
		if (height < 1) {
			throw new IllegalArgumentException("A voronoi noise map height must be a positive, non-zero value. " + height + " is too small.");
		}

		this.height = height;
		return this;
	}

	public VoronoiNoiseGenerator width(int width) throws IllegalArgumentException {
		if (width < 1) {
			throw new IllegalArgumentException("A voronoi noise map width must be a positive, non-zero value. " + width + " is too small.");
		}

		this.width = width;
		return this;
	}

	public VoronoiNoiseGenerator seed(long seed) {
		this.seed = seed;
		this.random.setSeed(seed);
		return this;
	}

	public VoronoiNoiseGenerator noiseMask(double noiseMaskIntensity) throws IllegalArgumentException {
		if (noiseMaskIntensity < 0 || noiseMaskIntensity > 1) {
			throw new IllegalArgumentException("A voronoi mask intensity must be a positive value between zero and one. " + noiseMaskIntensity + " is outside that interval.");
		}

		this.noiseMaskIntensity = noiseMaskIntensity;
		return this;
	}

	public VoronoiNoiseGenerator distanceMode(VoronoiDistance distanceMode) throws IllegalArgumentException {
		if (distanceMode == null) {
			throw new IllegalArgumentException("A voronoi noise distance mode cannot be null.");
		}

		this.distanceMode = distanceMode;
		return this;
	}

	public VoronoiNoiseGenerator frequency(int frequency) throws IllegalArgumentException {
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

		return new VoronoiNoise(this.height, this.width, this.seed, this.noise, this.noiseMask, this.distanceMode, this.frequency);
	}

	public double generateNoiseValue(int x, int y) {
		double adjustedX = x * frequency / 100.0;
		double adjustedY = y * frequency / 100.0;

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

				double currentDistance = calculateDistance(currentXNoise, currentYNoise, adjustedX, adjustedY, this.distanceMode);

				if(currentDistance < minimumDistance) {
					minimumDistance = currentDistance;
					xNoiseValue = (int) currentXNoise;
					yNoiseValue = (int) currentYNoise;
				}
			}
		}

		return calculateCellValue(xNoiseValue, yNoiseValue, this.seed);
	}
	
	public double[][] generateNoiseArray() {
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

	private double calculateDistance(double x1, double y1, double x2, double y2, VoronoiDistance distance) {
		switch (distance) {
			case Euclidean:
				return Math.sqrt((x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2));
			case Manhattan:
				return Math.abs(x1 - x2) + Math.abs(y1 - y2);
			case Minkowski:
				double p = 3.0;
				return Math.pow(Math.pow(Math.abs(x1 - x2), p) + Math.pow(Math.abs(y1 - y2), p), (1 / p));
			case Chebyshev:
				return Math.max(Math.abs(x1 - x2),Math.abs(y1 - y2));
			case Equidistant:
				return (x1 + y1) - (x2 + y2);
			default:
				return 0;
		}
	}

	private double calculateCellValue(int x, int y, long seed) {
		long hash = (0x653 * x + 0x1B3B * y + 0x3F5 * seed);
		return (((hash * (hash * hash * 0xEC4D + 0x131071F) + 0x5208DD0D) & Integer.MAX_VALUE) / (0x40000000 / 1.0)) / 2;
	} 
}
