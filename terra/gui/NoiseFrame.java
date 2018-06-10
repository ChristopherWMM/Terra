package terra.gui;

import terra.noise.Noise;

public class NoiseFrame extends ImageFrame {
	public NoiseFrame(final Noise noise) {
		super(new String(), noise.getNoiseImage(), true);
	}

	public NoiseFrame(final String title, final Noise noise) {
		super(title, noise.getNoiseImage(), true);
	}

	public NoiseFrame(final String title, final Noise noise, final boolean resizable) {
		super(new String(), noise.getNoiseImage(), resizable);
	}
}