package terra.noise;

public class WhiteNoise extends Noise {
	WhiteNoise(int height, int width, long seed, double[][] noise, NoiseMask noiseMask) {
		super(height, width, seed, noise, noiseMask);
	}

	WhiteNoise(WhiteNoise whiteNoise) {
		super(whiteNoise);
	}
}