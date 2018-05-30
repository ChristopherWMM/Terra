package terra;

import terra.gui.NoiseFrame;
import terra.noise.Noise;
import terra.noise.PerlinNoiseGenerator;
import terra.noise.WhiteNoiseGenerator;

public class Terra {
	public static void main(String[] args) {
		Noise perlin = new PerlinNoiseGenerator()
							.height(512)
							.width(512)
							.seed(0)
							.frequency(1)
							.octaves(10)
							.persistence(0.5)
							.lacunarity(2.8)
							.generate();

		new NoiseFrame(perlin);

		Noise white = new WhiteNoiseGenerator()
							.height(512)
							.width(512)
							.seed(0)
							.generate();

		new NoiseFrame(white);
	}
}
