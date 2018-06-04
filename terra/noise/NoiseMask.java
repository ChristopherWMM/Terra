package terra.noise;

import java.awt.image.BufferedImage;

/**
 * The immutable programmatic representation of a noise mask.
 * Objects store the results output by a {@link NoiseMaskGenerator} with specific corresponding parameters.
 * 
 * @since 1.0
 * @author ChristopherWMM
 */
public class NoiseMask {
	private int height;
	private int width;
	private double intensity;
	private double[][] maskArray;
	private BufferedImage maskImage;

	/**
	 * Constructs a new {@link NoiseMask} with the given values.
	 * 
	 * @param height The non-zero integer height of this {@link NoiseMask}.
	 * @param width The non-zero integer width of this {@link NoiseMask}.
	 * @param intensity The double intensity of this {@link NoiseMask}.
	 * @param maskArray The 2D double array containing the individual mask values of this {@link NoiseMask}.
	 * @throws IllegalArgumentException if the given parameters are outside of the valid range.
	 * @since 1.0
	 */
	NoiseMask(int height, int width, double intensity, double[][] maskArray) {
		if (height < 1) {
			throw new IllegalArgumentException("A noise mask height must be a positive, non-zero value. " + height + " is too small.");
		} else if (width < 1) {
			throw new IllegalArgumentException("A noise mask width must be a positive, non-zero value. " + width + " is too small.");
		} else if (intensity < 0.0 || intensity > 1.0) {
			throw new IllegalArgumentException("A noise mask intensity must be a positive value between zero and one. " + intensity + " is outside that interval.");
		}

		this.height = height;
		this.width = width;
		this.intensity = intensity;
		this.maskArray = new double[this.height][this.width];
		this.maskImage = generateMaskImage(maskArray);

		copy2DArray(this.maskArray, maskArray);
	}

	/**
	 * Constructs a new {@link NoiseMask} as a deep copy based on the given {@link NoiseMask}.
	 * 
	 * @param noiseMask The {@link NoiseMask} being copied.
	 * @since 1.0
	 */
	NoiseMask(NoiseMask noiseMask) {
		this.height = noiseMask.getHeight();
		this.width = noiseMask.getWidth();
		this.intensity = noiseMask.getIntensity();
		this.maskArray = new double[this.height][this.width];
		this.maskImage = noiseMask.getMaskImage();

		copy2DArray(this.maskArray, noiseMask.getMask());
	}

	/**
	 * Performs a deep copy on a each row of a 2D array using {@link System#arraycopy(Object, int, Object, int, int) System.arraycopy()}.
	 * 
	 * @param target The 2D array receiving the information from the given source.
	 * @param source The 2D array being copied into the given target.
	 * @since 1.0
	 */
	private void copy2DArray(double[][] target, double[][] source) {
		for (int x = 0; x < source.length; x++) {
			System.arraycopy(source[x], 0, target[x], 0, source[x].length);
		}
	}

	/**
	 * Converts the given {@link NoiseMask} value into its corresponding grayscale ARGB color.
	 * 
	 * @param maskValue The {@link NoiseMask} value within the interval within the interval <b>[0.0 - 1.0]</b>.
	 * @return The grayscale integer ARGB color corresponding to the given {@link NoiseMask} value.
	 * @since 1.0
	 */
	private int getGrayscaleMaskColor(double maskValue) {
		int blue = (int)(maskValue * 0xFF);
		int green = blue * 0x100;
		int red = blue * 0x10000;
		int alpha = 0xFF000000;

		return alpha + red + green + blue;
	}

	/**
	 * Generates the {@link BufferedImage} visual representation of this {@link NoiseMask}. 
	 * 
	 * @param mask The 2D double array containing the individual mask values of this {@link NoiseMask}.
	 * @return The grayscale {@link BufferedImage} visual representation of the given 2D double array.
	 * @since 1.0
	 */
	private BufferedImage generateMaskImage(double[][] mask) {
		BufferedImage maskImage = new BufferedImage(this.getWidth(), this.getHeight(), BufferedImage.TYPE_INT_ARGB);

		for (int x = 0; x < this.width; x++) {
			for (int y = 0; y < this.height; y++) {
				maskImage.setRGB(x, y, getGrayscaleMaskColor(mask[y][x]));
			}
		}

		return maskImage;
	}

	/** 
	 * Returns the non-zero height of this {@link NoiseMask}.
	 * 
	 * @return The non-zero integer height of this {@link NoiseMask}.
	 * @since 1.0
	 */
	public int getHeight() {
		return this.height;
	}

	/** 
	 * Returns the non-zero width of this {@link NoiseMask}.
	 * 
	 * @return The non-zero integer width of this {@link NoiseMask}.
	 * @since 1.0
	 */
	public int getWidth() {
		return this.width;
	}

	/**
	 * Returns the intensity of this {@link NoiseMask}. 
	 * Intensity is within the interval <b>[0.0 - 1.0]</b>.
	 * 
	 * @return The double intensity of this {@link NoiseMask}.
	 * @since 1.0
	 */
	public double getIntensity() {
		return this.intensity;
	}

	/**
	 * Returns the 2D array containing the individual mask values of this {@link NoiseMask}. 
	 * Values within the array are within the interval <b>[0.0 - 1.0]</b> where 1.0 indicates the corresponding value should be completely masked and 0.0 indicates the value should not be masked at all.
	 * 
	 * @return The 2D double array containing the individual mask values of this {@link NoiseMask}.
	 * @since 1.0
	 */
	public double[][] getMask() {
		return this.maskArray;
	}

	/**
	 * Returns the visual representation of this {@link NoiseMask}.
	 * 
	 * @return The {@link BufferedImage} visual representation of this {@link NoiseMask}.
	 * @since 1.0
	 */
	public BufferedImage getMaskImage() {
		return maskImage;
	}
}
