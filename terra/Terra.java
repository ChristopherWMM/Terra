package terra;

import terra.gui.ImageFrame;
import terra.gui.NoiseFrame;
import terra.noise.Noise;
import terra.noise.NoiseMask;
import terra.noise.NoiseMaskGenerator;
import terra.noise.PerlinNoiseGenerator;
import terra.noise.WhiteNoiseGenerator;

public class Terra {
	public static void main(String[] args) {
		Noise perlin = new PerlinNoiseGenerator()
							.height(512)
							.width(512)
							.seed(0)
							.frequency(1)
							.octaves(1)
							.persistence(1)
							.lacunarity(1)
							.noiseMask(0)
							.generate();

		new NoiseFrame(perlin);

		NoiseMask mask = new NoiseMaskGenerator()
							.height(256)
							.width(256)
							.intensity(0.25)
							.generate();

		new ImageFrame("", mask.getMaskImage(), false);

		Noise white = new WhiteNoiseGenerator()
							.height(512)
							.width(512)
							.seed(0)
							.noiseMask(0.5)
							.generate();

		new NoiseFrame(white);
	}
}
