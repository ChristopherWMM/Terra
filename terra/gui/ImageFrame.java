package terra.gui;

import java.awt.image.BufferedImage;

import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class ImageFrame extends JFrame {
	private final String TITLE;
	private BufferedImage ICON;
	private final int WIDTH;
	private final int HEIGHT;

	public ImageFrame(String title, BufferedImage image, boolean resizable) {
		this.TITLE = title;
		this.ICON = image;
		this.HEIGHT = image.getHeight();
		this.WIDTH = image.getWidth();

		setTitle(this.TITLE);
		setIconImage(this.ICON);
		add(new ImagePanel(image));

		setResizable(resizable);

		pack();
		pack();
		setSize(getWidth() + (WIDTH - getContentPane().getWidth()), getHeight() + (HEIGHT - getContentPane().getHeight()));
		getContentPane().setSize(WIDTH, HEIGHT);

		try {
			setDefaultLookAndFeelDecorated(true);
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
			
		}

		setVisible(true);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}
