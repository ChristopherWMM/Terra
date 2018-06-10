package terra.gui;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

class ImagePanel extends JPanel {
	private final BufferedImage image;

	public ImagePanel(final BufferedImage image) {
		this.image = image;
	}

	public void paintComponent(final Graphics graphics) {
		graphics.drawImage(getImage(), 0, 0, null);
	}

	public BufferedImage getImage() {
		return this.image;
	}
}
