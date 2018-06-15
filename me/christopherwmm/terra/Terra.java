package me.christopherwmm.terra;

import me.christopherwmm.terra.gui.ImageFrame;
import me.christopherwmm.terra.gui.NoiseFrame;
import me.christopherwmm.terra.noise.Noise;
import me.christopherwmm.terra.noise.mask.NoiseMask;
import me.christopherwmm.terra.noise.mask.NoiseMaskGenerator;
import me.christopherwmm.terra.noise.perlin.PerlinNoiseGenerator;
import me.christopherwmm.terra.noise.voronoi.VoronoiDistance;
import me.christopherwmm.terra.noise.voronoi.VoronoiNoiseGenerator;
import me.christopherwmm.terra.noise.white.WhiteNoiseGenerator;

public class Terra {
	public static void main(String[] args) {
		Noise perlin = new PerlinNoiseGenerator()
							.height(256)
							.width(256)
							.seed(0)
							.frequency(2)
							.octaves(3)
							.persistence(.5)
							.lacunarity(2)
							.noiseMask(0.0)
							.generate();

		new NoiseFrame(perlin);

		NoiseMask mask = new NoiseMaskGenerator()
							.height(256)
							.width(256)
							.intensity(0.5)
							.generate();

		new ImageFrame("Noise Mask", mask.getMaskImage());

		Noise white = new WhiteNoiseGenerator()
							.height(256)
							.width(256)
							.seed(0)
							.noiseMask(0.5)
							.generate();

		new NoiseFrame(white);

		Noise voronoi = new VoronoiNoiseGenerator()
									.height(256)
									.width(256)
									.seed(0)
									.distanceMode(VoronoiDistance.Euclidean)
									.frequency(1)
									.noiseMask(0.0)
									.generate();

		new NoiseFrame(voronoi);
	}
}