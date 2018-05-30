package terra.gui;

import terra.noise.Noise;

public class NoiseFrame extends ImageFrame {
	public NoiseFrame(Noise noise) {
		super(new String(), noise.getNoiseImage(), true);
	}

	public NoiseFrame(String title, Noise noise) {
		super(title, noise.getNoiseImage(), true);
	}

	public NoiseFrame(String title, Noise noise, boolean resizable) {
		super(new String(), noise.getNoiseImage(), resizable);
	}
}