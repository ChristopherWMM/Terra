package me.christopherwmm.terra.gui;

import java.awt.Graphics;

import javax.swing.JPanel;

class ImagePanel extends JPanel {
	private TerraImage image;

	public ImagePanel(final TerraImage image) {
		setSize(image.getWidth(), image.getHeight());
		this.image = image;
	}

	public void paintComponent(final Graphics graphics) {
		TerraImage image = getImage();

		if (getWidth() != image.getWidth() || getHeight() != image.getHeight()) {
			double scaleFactorX = getWidth() / (double) image.getWidth();
			double scaleFactorY = getHeight() / (double) image.getHeight();

			image = this.image.scaleNearestNeighbor(scaleFactorX, scaleFactorY);
			setSize(image.getWidth(), image.getHeight());
		}

		graphics.drawImage(image, 0, 0, null);
	}

	public TerraImage getImage() {
		return this.image;
	}
}
