package terra.gui;

import java.awt.Graphics;

import javax.swing.JPanel;

class ImagePanel extends JPanel {
	private final TerraImage image;

	public ImagePanel(final TerraImage image) {
		this.image = image;
	}

	public void paintComponent(final Graphics graphics) {
		graphics.drawImage(getImage(), 0, 0, null);
	}

	public TerraImage getImage() {
		return this.image;
	}
}
