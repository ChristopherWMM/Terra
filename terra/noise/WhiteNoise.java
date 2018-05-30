package terra.noise;

public class WhiteNoise extends Noise {
	WhiteNoise(int height, int width, long seed, double[][] noise) {
		super(height, width, seed, noise);
	}

	WhiteNoise(WhiteNoise whiteNoise) {
		super(whiteNoise);
	}
}