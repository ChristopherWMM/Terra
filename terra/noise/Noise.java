package terra.noise;

import java.awt.image.BufferedImage;

public abstract class Noise {
	private int height;
	private int width;
	private long seed;
	private double noiseArray[][]; // Should convert to a Grid once implemented. TODO
	private NoiseMask noiseMask;
	private BufferedImage noiseImage;

	Noise(int height, int width, long seed, double[][] noise, NoiseMask noiseMask) {
		this.height = height;
		this.width = width;
		this.seed = seed;
		this.noiseArray = new double[this.height][this.width];
		this.noiseMask = noiseMask;
		this.noiseImage = generateNoiseImage(noise);

		// Perform an arraycopy on each row of the 2D array.
		copy2DArray(this.noiseArray, noise);
	}

	Noise(Noise noise) {
		this.height = noise.getHeight();
		this.width = noise.getWidth();
		this.seed = noise.getSeed();
		this.noiseArray = new double[this.height][this.width];
		this.noiseMask = noise.getNoiseMask();
		this.noiseImage = noise.getNoiseImage();

		// Perform an arraycopy on each row of the 2D array.
		copy2DArray(this.noiseArray, noise.getNoise());
	}

	private void copy2DArray(double[][] target, double[][] source) {
		for (int x = 0; x < source.length; x++) {
			System.arraycopy(source[x], 0, target[x], 0, source[x].length);
		}
	}

	private BufferedImage generateNoiseImage(double[][] noise) {
		BufferedImage noiseImage = new BufferedImage(this.getWidth(), this.getHeight(), BufferedImage.TYPE_INT_ARGB);

		for (int x = 0; x < this.width; x++) {
			for (int y = 0; y < this.height; y++) {
				noiseImage.setRGB(x, y, getGrayscaleNoiseColor(noise[y][x]));
			}
		}

		return noiseImage;
	}

	private int getGrayscaleNoiseColor(double noiseValue) {
		int blue = (int)(noiseValue * 0xFF);
		int green = blue * 0x100;
		int red = blue * 0x10000;
		int alpha = 0xFF000000;

		return alpha + red + green + blue;
	}

	public int getHeight() {
		return this.height;
	}

	public int getWidth() {
		return this.width;
	}

	public long getSeed() {
		return this.seed;
	}

	public double[][] getNoise() {
		return this.noiseArray;
	}

	public NoiseMask getNoiseMask() {
		return this.noiseMask;
	}

	public BufferedImage getNoiseImage() {
		return noiseImage;
	}
}
