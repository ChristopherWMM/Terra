package terra.noise.white;

import terra.noise.Noise;
import terra.noise.mask.NoiseMask;

/**
 * The immutable programmatic representation of white noise.
 * Objects store the results output by a {@link WhiteNoiseGenerator} with the specific corresponding parameters.
 * 
 * @since 1.0
 * @author ChristopherWMM
 */
public class WhiteNoise extends Noise {
	/**
	 * Constructs a new {@link WhiteNoise} object with the given values.
	 * 
	 * @param height The non-zero integer height of this {@link WhiteNoise} object.
	 * @param width The non-zero integer width of this {@link WhiteNoise} object.
	 * @param seed The long seed used to generate this {@link WhiteNoise} object.
	 * @param noise The 2D double array containing the individual mask values of this {@link WhiteNoise} object.
	 * @param noiseMask The {@link NoiseMask} being applied to this {@link WhiteNoise} object.
	 */
	WhiteNoise(final int height, final int width, final long seed, final double[][] noise, final NoiseMask noiseMask) {
		super(height, width, seed, noise, noiseMask);
	}

	/**
	 * Constructs a new {@link WhiteNoise} object that is a deep copy based on the given {@link WhiteNoise} object.
	 * 
	 * @param whiteNoise The {@link WhiteNoise} object being copied.
	 * @since 1.0
	 */
	WhiteNoise(final WhiteNoise whiteNoise) {
		super(whiteNoise);
	}

	/**
	 * Returns a new {@link WhiteNoise} object that is a deep copy of this {@link WhiteNoise} object.
	 * 
	 * @return A new {@link WhiteNoise} object that is a deep copy of this {@link WhiteNoise} object.
	 * @since 1.0
	 */
	public WhiteNoise clone() {
		return new WhiteNoise(this);
	}
}