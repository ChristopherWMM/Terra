package terra.noise;

public class PerlinNoiseGenerator implements Generator<PerlinNoise> {
	private int height;
	private int width;
	private long seed;
	private int frequency;
	private int octaves;
	private double persistence;
	private double lacunarity;
	private double[][] noise;
	private double noiseMaskIntensity;
	private NoiseMask noiseMask;

	private double maxNoiseValue;
	private double minNoiseValue;

	public PerlinNoiseGenerator() {
		this.height = 512;
		this.width = 512;
		this.seed = 0;
		this.frequency = 1;
		this.octaves = 1;
		this.persistence = 1;
		this.lacunarity = 1;
		this.noise = new double[this.height][this.width];
		this.noiseMaskIntensity = 0;
		this.noiseMask = new NoiseMaskGenerator()
				.height(this.height)
				.width(this.width)
				.intensity(0)
				.generate();

		this.maxNoiseValue = Double.MIN_VALUE;
		this.minNoiseValue = Double.MAX_VALUE;
	}

	public PerlinNoiseGenerator height(int height) throws IllegalArgumentException {
		if (height < 1) {
			throw new IllegalArgumentException("A perlin noise map height must be a positive, non-zero value. " + height + " is too small.");
		}

		this.height = height;
		return this;
	}

	public PerlinNoiseGenerator width(int width) throws IllegalArgumentException {
		if (width < 1) {
			throw new IllegalArgumentException("A perlin noise map width must be a positive, non-zero value. " + width + " is too small.");
		}

		this.width = width;
		return this;
	}

	public PerlinNoiseGenerator seed(long seed) {
		this.seed = seed;
		return this;
	}

	public PerlinNoiseGenerator noiseMask(double noiseMaskIntensity) throws IllegalArgumentException {
		if (noiseMaskIntensity < 0 || noiseMaskIntensity > 1) {
			throw new IllegalArgumentException("A perlin noise mask intensity must be a positive value between zero and one. " + noiseMaskIntensity + " is outside that interval.");
		}

		this.noiseMaskIntensity = noiseMaskIntensity;
		return this;
	}

	public PerlinNoiseGenerator frequency(int frequency) throws IllegalArgumentException {
		if (frequency < 1) {
			throw new IllegalArgumentException("A perlin noise map initial frequency must be a positive, non-zero value. " + frequency + " is too small.");
		}

		this.frequency = frequency;
		return this;
	}

	public PerlinNoiseGenerator octaves(int octaves) throws IllegalArgumentException {
		if (octaves < 1) {
			throw new IllegalArgumentException("A perlin noise map octave count must be a positive, non-zero value. " + octaves + " is too small.");
		}

		this.octaves = octaves;
		return this;
	}

	public PerlinNoiseGenerator persistence(double persistence) {
		if (persistence < Double.MIN_VALUE) {
			throw new IllegalArgumentException("A perlin noise persistence must be a positive, non-zero value. " + persistence + " is too small.");
		}

		this.persistence = persistence;
		return this;
	}

	public PerlinNoiseGenerator lacunarity(double lacunarity) {
		if (lacunarity < Double.MIN_VALUE) {
			throw new IllegalArgumentException("A perlin noise lacunarity must be a positive, non-zero value. " + lacunarity + " is too small.");
		}

		this.lacunarity = lacunarity;
		return this;
	}

	@Override
	public PerlinNoise generate() {
		this.noiseMask = new NoiseMaskGenerator()
				.height(this.height)
				.width(this.width)
				.intensity(this.noiseMaskIntensity)
				.generate();

		this.noise = generateNoiseArray();
		return new PerlinNoise(this.height, this.width, this.seed, this.noise, this.noiseMask, this.frequency, this.octaves, this.persistence, this.lacunarity);
	}

	private double[][] generateNoiseArray() {
		double[][] noise = new double[this.height][this.width];

		for (int y = 0; y < this.height; y++) {
			for (int x = 0; x < this.width; x++) {
				noise[y][x] = generateOctaveNoiseValue(x, y);

				if (noise[y][x] > this.maxNoiseValue)  {
					this.maxNoiseValue = noise[y][x];
				} else if (noise[y][x] < this.minNoiseValue) {
					this.minNoiseValue = noise[y][x];
				}
			}
		}

		noise = smoothNoiseArray(noise);

		return noise;
	}

	protected double generateOctaveNoiseValue(double x, double y) {
		double value = 0;
		double amplitude = 1;
		double frequency = this.frequency;

		for(int i = 0; i < this.octaves; i++) {
			double perlinValue = generatePerlinValue(x, y, frequency);
			value += perlinValue * amplitude;

			amplitude *= this.persistence;
			frequency *= this.lacunarity;
		}

		return value;
	}

	private double generatePerlinValue(double x, double y) {
		return generatePerlinValue(x, y, 1);
	}

	private double generatePerlinValue(double x, double y, double frequency) {
		double doubleX = (double) x / this.width;
		double doubleY = (double) y / this.height;

		double frequencyX = (doubleX * frequency) + this.seed;
		double frequencyY = (doubleY * frequency) + this.seed;

		int flooredX = (int) Math.floor(frequencyX) % 256;
		int flooredY = (int) Math.floor(frequencyY) % 256;

		int corner1 = PerlinNoise.PERMUTATION_TABLE[PerlinNoise.PERMUTATION_TABLE[flooredX] + flooredY];
		int corner2 = PerlinNoise.PERMUTATION_TABLE[PerlinNoise.PERMUTATION_TABLE[flooredX + 1] + flooredY];
		int corner3 = PerlinNoise.PERMUTATION_TABLE[PerlinNoise.PERMUTATION_TABLE[flooredX] + flooredY + 1];
		int corner4 = PerlinNoise.PERMUTATION_TABLE[PerlinNoise.PERMUTATION_TABLE[flooredX + 1] + flooredY + 1];

		double adjustedX = frequencyX - Math.floor(frequencyX);
		double adjustedY = frequencyY - Math.floor(frequencyY);

		double dotCorner1 = calculateDotProduct(corner1, adjustedX, adjustedY);
		double dotCorner2 = calculateDotProduct(corner2, adjustedX - 1, adjustedY);
		double dotCorner3 = calculateDotProduct(corner3, adjustedX, adjustedY - 1);
		double dotCorner4 = calculateDotProduct(corner4, adjustedX - 1, adjustedY - 1);

		double interpolatedX = fade(adjustedX);
		double interpolatedY = fade(adjustedY);

		double lerpedX1 = lerp(interpolatedX, dotCorner1, dotCorner2);
		double lerpedX2 = lerp(interpolatedX, dotCorner3, dotCorner4);
		double lerpedY = lerp(interpolatedY, lerpedX1, lerpedX2);

		return (lerpedY + 1) / 2;
	}

	private double[][] smoothNoiseArray(double[][] noise) {
		double[][] smoothNoise = new double[this.height][this.width];
		double[][] maskNoise = this.noiseMask.getMask();

		for (int y = 0; y < this.height; y++) {
			for (int x = 0; x < this.width; x++) {
				smoothNoise[y][x] = inverseLerp(noise[y][x], this.minNoiseValue, this.maxNoiseValue);
				smoothNoise[y][x] = Math.max(0, smoothNoise[y][x] - maskNoise[y][x]);
			}
		}

		return smoothNoise;
	}

	private double fade(double noiseValue) {
		return noiseValue * noiseValue * noiseValue * (noiseValue * (noiseValue * 6 - 15) + 10); 
	}

	private double lerp(double amount, double low, double high) {
		return low + amount * (high - low);
	}

	private double inverseLerp(double amount, double low, double high) {
		return ((amount - low) / (high - low));
	}

	private double calculateDotProduct(int corner, double x, double y) {
		switch(corner % 4) {
			case 0:
				return x + y;
			case 1:
				return -x + y;
			case 2:
				return x - y;
			case 3:
				return -x - y;
			default:
				return 0;
		}
	}
}
