package terra.noise.perlin;

import terra.noise.Noise;
import terra.noise.mask.NoiseMask;

/**
 * The immutable programmatic representation of perlin noise.
 * Objects store the results output by a {@link PerlinNoiseGenerator} with the specific corresponding parameters.
 * 
 * @since 1.0
 * @author ChristopherWMM
 */
public class PerlinNoise extends Noise {
	/** The non-zero integer initial frequency of this {@link PerlinNoise} object. */
	private final int frequency;

	/** The non-zero integer number of octaves present in this {@link PerlinNoise} object. */
	private final int octaves;

	/** The non-zero double persistence of this {@link PerlinNoise} object. */
	private final double persistence;

	/** The non-zero double lacunarity of this {@link PerlinNoise} object. */
	private final double lacunarity;

	/**
	 * Constructs a new {@link PerlinNoise} object with the given values.
	 * 
	 * @param height The non-zero integer height of this {@link PerlinNoise} object.
	 * @param width The non-zero integer width of this {@link PerlinNoise} object.
	 * @param seed The long seed used to generate this {@link PerlinNoise} object.
	 * @param noise The 2D double array containing the individual mask values of this {@link PerlinNoise} object.
	 * @param noiseMask The {@link NoiseMask} being applied to this {@link PerlinNoise} object.
	 * @param frequency The non-zero integer initial frequency of this {@link PerlinNoise} object.
	 * @param octaves The non-zero integer number of octaves present in this {@link PerlinNoise} object.
	 * @param persistence The non-zero double persistence of this {@link PerlinNoise} object.
	 * @param lacunarity The non-zero double lacunarity of this {@link PerlinNoise} object.
	 * @throws IllegalArgumentException if the given parameters are outside of the valid range.
	 * @since 1.0
	 */
	PerlinNoise(final int height, final int width, final long seed, final double[][] noise, final NoiseMask noiseMask, final int frequency, final int octaves, final double persistence, final double lacunarity) {
		super(height, width, seed, noise, noiseMask);

		if (frequency < 1) {
			throw new IllegalArgumentException("A perlin noise map initial frequency must be a positive, non-zero value. " + frequency + " is too small.");
		} else if (octaves < 1) {
			throw new IllegalArgumentException("A perlin noise map octave count must be a positive, non-zero value. " + octaves + " is too small.");
		} else if (persistence < Double.MIN_VALUE) {
			throw new IllegalArgumentException("A perlin noise persistence must be a positive, non-zero value. " + persistence + " is too small.");
		} else if (lacunarity < Double.MIN_VALUE) {
			throw new IllegalArgumentException("A perlin noise lacunarity must be a positive, non-zero value. " + lacunarity + " is too small.");
		}

		this.frequency = frequency;
		this.octaves = octaves;
		this.persistence = persistence;
		this.lacunarity = lacunarity;
	}

	/**
	 * Constructs a new {@link PerlinNoise} object that is a deep copy based on the given {@link PerlinNoise} object.
	 * 
	 * @param perlinNoise The {@link PerlinNoise} object being copied.
	 * @since 1.0
	 */
	PerlinNoise(final PerlinNoise perlinNoise) {
		super(perlinNoise);

		this.frequency = perlinNoise.getFrequency();
		this.octaves = perlinNoise.getOctaves();
		this.persistence = perlinNoise.getPersistence();
		this.lacunarity = perlinNoise.getLacunarity();
	}

	/**
	 * Returns the non-zero initial frequency of this {@link PerlinNoise} object.
	 * 
	 * @return The non-zero integer initial frequency of this {@link PerlinNoise} object.
	 * @since 1.0
	 */
	public int getFrequency() {
		return this.frequency;
	}

	/** 
	 * Returns the non-zero number of octaves present in this {@link PerlinNoise} object.
	 * 
	 * @return The non-zero integer number of octaves present in this {@link PerlinNoise} object.
	 * @since 1.0
	 */
	public int getOctaves() {
		return this.octaves;
	}

	/** 
	 * Returns the non-zero persistence of this {@link PerlinNoise} object.
	 * 
	 * @return The non-zero double persistence of this {@link PerlinNoise} object.
	 * @since 1.0
	 */
	public double getPersistence() {
		return this.persistence;
	}

	/** 
	 * Returns the non-zero lacunarity of this {@link PerlinNoise} object.
	 * 
	 * @return The non-zero double lacunarity of this {@link PerlinNoise} object.
	 * @since 1.0
	 */
	public double getLacunarity() {
		return this.lacunarity;
	}
	

	/**
	 * Returns a new {@link PerlinNoise} object that is a deep copy of this {@link PerlinNoise} object.
	 * 
	 * @return A new {@link PerlinNoise} object that is a deep copy of this {@link PerlinNoise} object.
	 * @since 1.0
	 */
	public PerlinNoise clone() {
		return new PerlinNoise(this);
	}
}
