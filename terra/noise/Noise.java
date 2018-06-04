package terra.noise;

import java.awt.image.BufferedImage;

/**
 * The immutable programmatic representation of noise.
 * Objects store the results output by a {@link Noise} {@link Generator} with specific corresponding parameters.
 * 
 * @since 1.0
 * @author ChristopherWMM
 */
public abstract class Noise {
	/** The non-zero integer height of this {@link Noise} object. */
	private int height;

	/** The non-zero integer width of this {@link Noise} object. */
	private int width;

	/** The long seed used to generate this {@link Noise} object. */
	private long seed;

	/** The 2D double array containing the individual mask values of this {@link Noise} object. */
	private double noiseArray[][];

	/** The {@link NoiseMask} being applied to this {@link Noise} object. */
	private NoiseMask noiseMask;

	/** The {@link BufferedImage} visual representation of this {@link Noise} object. */
	private BufferedImage noiseImage;

	/**
	 * Constructs a new {@link Noise} object with the given values.
	 * 
	 * @param height The non-zero integer height of this {@link Noise} object.
	 * @param width The non-zero integer width of this {@link Noise} object.
	 * @param seed The long seed used to generate this {@link Noise} object.
	 * @param noiseArray The 2D double array containing the individual mask values of this {@link Noise} object.
	 * @param noiseMask The {@link NoiseMask} being applied to this {@link Noise} object.
	 * @throws IllegalArgumentException if the given parameters are outside of the valid range.
	 * @since 1.0
	 */
	Noise(int height, int width, long seed, double[][] noiseArray, NoiseMask noiseMask) {
		if (height < 1) {
			throw new IllegalArgumentException("A noise map height must be a positive, non-zero value. " + height + " is too small.");
		} else if (width < 1) {
			throw new IllegalArgumentException("A noise map width must be a positive, non-zero value. " + width + " is too small.");
		} else if (noiseArray == null) {
			throw new IllegalArgumentException("The given noise array cannot be null.");
		} else if (noiseArray.length == 0 || noiseArray.length != height || noiseArray[0].length == 0 || noiseArray[0].length != width) {
			throw new IllegalArgumentException("The dimensions of the provided noise array is invalid!");
		} else if (noiseMask == null) {
			throw new IllegalArgumentException("The given noise mask cannot be null.");
		}

		this.height = height;
		this.width = width;
		this.seed = seed;
		this.noiseArray = new double[this.height][this.width];
		this.noiseMask = noiseMask;
		this.noiseImage = generateNoiseImage(noiseArray);

		copy2DArray(this.noiseArray, noiseArray);
	}

	/**
	 * Constructs a new {@link Noise} object as a deep copy based on the given {@link Noise} object.
	 * 
	 * @param noise The {@link Noise} object being copied.
	 * @since 1.0
	 */
	Noise(Noise noise) {
		this.height = noise.getHeight();
		this.width = noise.getWidth();
		this.seed = noise.getSeed();
		this.noiseArray = new double[this.height][this.width];
		this.noiseMask = noise.getNoiseMask();
		this.noiseImage = noise.getNoiseImage();

		copy2DArray(this.noiseArray, noise.getNoise());
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
	 * Converts the given {@link Noise} value into its corresponding grayscale ARGB color.
	 * 
	 * @param noiseValue The {@link Noise} value within the interval within the interval <b>[0.0 - 1.0]</b>.
	 * @return The grayscale integer ARGB color corresponding to the given {@link Noise} value.
	 * @since 1.0
	 */
	private int getGrayscaleNoiseColor(double noiseValue) {
		int blue = (int)(noiseValue * 0xFF);
		int green = blue * 0x100;
		int red = blue * 0x10000;
		int alpha = 0xFF000000;

		return alpha + red + green + blue;
	}

	/**
	 * Generates the {@link BufferedImage} visual representation of this {@link Noise} object. 
	 * 
	 * @param mask The 2D double array containing the individual noise values of this {@link Noise} object.
	 * @return The grayscale {@link BufferedImage} visual representation of the given 2D double array.
	 * @since 1.0
	 */
	private BufferedImage generateNoiseImage(double[][] noise) {
		BufferedImage noiseImage = new BufferedImage(this.getWidth(), this.getHeight(), BufferedImage.TYPE_INT_ARGB);

		for (int x = 0; x < this.width; x++) {
			for (int y = 0; y < this.height; y++) {
				noiseImage.setRGB(x, y, getGrayscaleNoiseColor(noise[y][x]));
			}
		}

		return noiseImage;
	}

	/** 
	 * Returns the non-zero height of this {@link Noise} object.
	 * 
	 * @return The non-zero integer height of this {@link Noise} object.
	 * @since 1.0
	 */
	public int getHeight() {
		return this.height;
	}

	/** 
	 * Returns the non-zero width of this {@link Noise} object.
	 * 
	 * @return The non-zero integer width of this {@link Noise} object.
	 * @since 1.0
	 */
	public int getWidth() {
		return this.width;
	}

	/**
	 * Returns the seed used to generate this {@link Noise} object.
	 * 
	 * @return The long seed used to generate this {@link Noise} object.
	 * @since 1.0
	 */
	public long getSeed() {
		return this.seed;
	}

	/**
	 * Returns the 2D array containing the individual noise values of this {@link Noise} object. 
	 * Values within the array are within the interval <b>[0.0 - 1.0]</b>.
	 * 
	 * @return The 2D double array containing the individual noise values of this {@link Noise} object.
	 * @since 1.0
	 */
	public double[][] getNoise() {
		return this.noiseArray;
	}

	/**
	 * Returns The noise mask being applied to this {@link Noise} object.
	 * 
	 * @return The {@link NoiseMask} being applied to this {@link Noise} object.
	 * @since 1.0
	 */
	public NoiseMask getNoiseMask() {
		return this.noiseMask;
	}

	/**
	 * Returns the visual representation of this {@link Noise} object.
	 * 
	 * @return The {@link BufferedImage} visual representation of this {@link Noise} object.
	 * @since 1.0
	 */
	public BufferedImage getNoiseImage() {
		return noiseImage;
	}
}
