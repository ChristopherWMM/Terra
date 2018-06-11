package me.christopherwmm.terra.gui;

import me.christopherwmm.terra.noise.Noise;

public class NoiseFrame extends ImageFrame {
	public NoiseFrame(final Noise noise) {
		super(noise.getClass().getSimpleName().replaceAll("([a-z])([A-Z])", "$1 $2") + " - Seed: " + noise.getSeed(), noise.getNoiseImage(), true);
	}

	public NoiseFrame(final int width, final int height, final Noise noise) {
		super(noise.getClass().getSimpleName().replaceAll("([a-z])([A-Z])", "$1 $2") + " - Seed: " + noise.getSeed(), width, height, noise.getNoiseImage(), true);
	}

	public NoiseFrame(final String title, final Noise noise) {
		super(title, noise.getNoiseImage(), true);
	}

	public NoiseFrame(final String title, final Noise noise, final boolean resizable) {
		super(noise.getClass().getSimpleName().replaceAll("([a-z])([A-Z])", "$1 $2") + " - Seed: " + noise.getSeed(), noise.getNoiseImage(), resizable);
	}

	public NoiseFrame(final String title, final int width, final int height, final Noise noise, final boolean resizable) {
		super(noise.getClass().getSimpleName().replaceAll("([a-z])([A-Z])", "$1 $2") + " - Seed: " + noise.getSeed(), width, height, noise.getNoiseImage(), resizable);
	}
}