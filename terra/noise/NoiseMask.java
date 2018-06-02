package terra.noise;

import java.awt.image.BufferedImage;

public class NoiseMask {
	private int height;
	private int width;
	private double intensity;
	private double[][] maskArray;
	private BufferedImage maskImage;

	NoiseMask(int height, int width, double intensity, double[][] maskArray) {
		this.height = height;
		this.width = width;
		this.intensity = intensity;
		this.maskArray = new double[this.height][this.width];
		this.maskImage = generateMaskImage(maskArray);

		copy2DArray(this.maskArray, maskArray);
	}

	private void copy2DArray(double[][] target, double[][] source) {
		for (int x = 0; x < source.length; x++) {
			System.arraycopy(source[x], 0, target[x], 0, source[x].length);
		}
	}

	private int getGreyscaleMaskColor(double maskValue) {
		int blue = (int)(maskValue * 0xFF);
		int green = blue * 0x100;
		int red = blue * 0x10000;
		int alpha = 0xFF000000;

		return alpha + red + green + blue;
	}

	private BufferedImage generateMaskImage(double[][] mask) {
		BufferedImage maskImage = new BufferedImage(this.getWidth(), this.getHeight(), BufferedImage.TYPE_INT_ARGB);

		for (int x = 0; x < this.width; x++) {
			for (int y = 0; y < this.height; y++) {
				maskImage.setRGB(x, y, getGreyscaleMaskColor(mask[y][x]));
			}
		}

		return maskImage;
	}
	
	public int getHeight() {
		return this.height;
	}

	public int getWidth() {
		return this.width;
	}

	public double getIntensity() {
		return this.intensity;
	}

	public double[][] getMask() {
		return this.maskArray;
	}

	public BufferedImage getMaskImage() {
		return maskImage;
	}
}
