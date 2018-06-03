package terra.noise;

import java.util.Random;

public class WhiteNoiseGenerator implements Generator<WhiteNoise> {
	private int height;
	private int width;
	private long seed;
	private double[][] noise;
	private double noiseMaskIntensity;
	private NoiseMask noiseMask;

	private Random random;

	public WhiteNoiseGenerator() {
		this.height = 512;
		this.width = 512;
		this.seed = 0;
		this.noise = new double[this.height][this.width];
		this.noiseMaskIntensity = 0;
		this.noiseMask = new NoiseMaskGenerator()
							.height(this.height)
							.width(this.width)
							.intensity(this.noiseMaskIntensity)
							.generate();

		this.random = new Random();
	}

	public WhiteNoiseGenerator height(int height) {
		this.height = height;
		return this;
	}

	public WhiteNoiseGenerator width(int width) {
		this.width = width;
		return this;
	}

	public WhiteNoiseGenerator seed(int seed) {
		this.seed = seed;
		this.random.setSeed(seed);
		return this;
	}

	public WhiteNoiseGenerator noiseMask(double noiseMaskIntensity) {
		this.noiseMaskIntensity = noiseMaskIntensity;
		return this;
	}

	@Override
	public WhiteNoise generate() {
		this.noiseMask = new NoiseMaskGenerator()
				.height(this.height)
				.width(this.width)
				.intensity(this.noiseMaskIntensity)
				.generate();

		this.noise = generateNoiseArray();

		return new WhiteNoise(this.height, this.width, this.seed, this.noise, this.noiseMask);
	}

	private double[][] generateNoiseArray() {
		double[][] noise = new double[this.height][this.width];
		double[][] maskNoise = this.noiseMask.getMask();

		for (int y = 0; y < this.height; y++) {
			for (int x = 0; x < this.width; x++) {
				noise[y][x] = this.random.nextDouble();
				noise[y][x] = Math.max(0, noise[y][x] - maskNoise[y][x]);
			}
		}

		return noise;
	}
}
