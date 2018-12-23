package me.christopherwmm.terra.gui;

import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class ImageFrame extends JFrame {
	private final String TITLE;
	private final Image ICON;
	private final int WIDTH;
	private final int HEIGHT;

	public ImageFrame(final Image image) {
		this("", image, true);
	}

	public ImageFrame(final String title, final Image image) {
		this(title, image, true);
	}

	public ImageFrame(final String title, final Image image, final boolean resizable) {
		this(title, image.getWidth(), image.getHeight(), image, resizable);
	}

	public ImageFrame(final String title, int width, int height, final Image image, final boolean resizable) {
		this.TITLE = title;
		this.ICON = image;
		this.WIDTH = width;
		this.HEIGHT = height;

		setTitle(this.TITLE);
		setIconImage(this.ICON);
		setResizable(resizable);

		if (this.WIDTH != image.getWidth() || this.HEIGHT != image.getHeight()) {
			double scaleFactorX = this.WIDTH / (double) image.getWidth();
			double scaleFactorY = this.HEIGHT / (double) image.getHeight();

			add(new ImagePanel(image.scaleNearestNeighbor(scaleFactorX, scaleFactorY)));
		} else {
			add(new ImagePanel(image));
		}

		pack();
		setSize(getWidth() + (this.WIDTH - getContentPane().getWidth()), getHeight() + (this.HEIGHT - getContentPane().getHeight()));
		getContentPane().setSize(this.WIDTH, this.HEIGHT);

		try {
			setDefaultLookAndFeelDecorated(true);
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}

		setVisible(true);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}
