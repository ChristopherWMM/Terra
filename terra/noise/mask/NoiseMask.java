package terra.noise.mask;

import java.awt.image.BufferedImage;

/**
 * The immutable programmatic representation of a noise mask.
 * Objects store the results output by a {@link NoiseMaskGenerator} with the specific corresponding parameters.
 * 
 * @since 1.0
 * @author ChristopherWMM
 */
public class NoiseMask {
	/** The non-zero integer height of this {@link NoiseMask} object. */
	private final int height;

	/** The non-zero integer width of this {@link NoiseMask} object. */
	private final int width;

	/** The double intensity of this {@link NoiseMask} object. */
	private final double intensity;

	/** The 2D double array containing the individual mask values of this {@link NoiseMask} object. */
	private final double[][] maskArray;

	/** The {@link BufferedImage} visual representation of this {@link NoiseMask} object. */
	private final BufferedImage maskImage;

	/**
	 * Constructs a new {@link NoiseMask} object with the given values.
	 * 
	 * @param height The non-zero integer height of this {@link NoiseMask} object.
	 * @param width The non-zero integer width of this {@link NoiseMask} object.
	 * @param intensity The double intensity of this {@link NoiseMask} object.
	 * @param maskArray The 2D double array containing the individual mask values of this {@link NoiseMask} object.
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
		} else if (maskArray == null) {
			throw new IllegalArgumentException("The given noise array cannot be null.");
		} else if (maskArray.length == 0 || maskArray.length != height || maskArray[0].length == 0 || maskArray[0].length != width) {
			throw new IllegalArgumentException("The dimensions of the provided noise array is invalid!");
		}

		this.height = height;
		this.width = width;
		this.intensity = intensity;
		this.maskArray = new double[this.height][this.width];
		this.maskImage = generateMaskImage(maskArray);

		copy2DArray(this.maskArray, maskArray);
	}

	/**
	 * Constructs a new {@link NoiseMask} object that is a deep copy based on the given {@link NoiseMask} object.
	 * 
	 * @param noiseMask The {@link NoiseMask} object being copied.
	 * @since 1.0
	 */
	NoiseMask(NoiseMask noiseMask) {
		this.height = noiseMask.getHeight();
		this.width = noiseMask.getWidth();
		this.intensity = noiseMask.getIntensity();
		this.maskArray = new double[this.height][this.width];
		this.maskImage = generateMaskImage(noiseMask.getMask());

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
	 * Generates the {@link BufferedImage} visual representation of this {@link NoiseMask} object. 
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
	 * Returns the non-zero height of this {@link NoiseMask} object.
	 * 
	 * @return The non-zero integer height of this {@link NoiseMask} object.
	 * @since 1.0
	 */
	public int getHeight() {
		return this.height;
	}

	/** 
	 * Returns the non-zero width of this {@link NoiseMask} object.
	 * 
	 * @return The non-zero integer width of this {@link NoiseMask} object.
	 * @since 1.0
	 */
	public int getWidth() {
		return this.width;
	}

	/**
	 * Returns the intensity of this {@link NoiseMask} object. 
	 * Intensity is within the interval <b>[0.0 - 1.0]</b>.
	 * 
	 * @return The double intensity of this {@link NoiseMask} object.
	 * @since 1.0
	 */
	public double getIntensity() {
		return this.intensity;
	}

	/**
	 * Returns the 2D array containing the individual mask values of this {@link NoiseMask} object. 
	 * Values within the array are within the interval <b>[0.0 - 1.0]</b> where 1.0 indicates the corresponding value should be completely masked and 0.0 indicates the value should not be masked at all.
	 * 
	 * @return The 2D double array containing the individual mask values of this {@link NoiseMask} object.
	 * @since 1.0
	 */
	public double[][] getMask() {
		return this.maskArray;
	}

	/**
	 * Returns the visual representation of this {@link NoiseMask} object.
	 * 
	 * @return The {@link BufferedImage} visual representation of this {@link NoiseMask} object.
	 * @since 1.0
	 */
	public BufferedImage getMaskImage() {
		return this.maskImage;
	}

	/**
	 * Returns a new {@link NoiseMask} object that is a deep copy of this {@link NoiseMask} object.
	 * 
	 * @return A new {@link NoiseMask} object that is a deep copy of this {@link NoiseMask} object.
	 * @since 1.0
	 */
	public NoiseMask clone() {
		return new NoiseMask(this);
	}
}
