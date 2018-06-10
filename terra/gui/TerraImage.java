package terra.gui;

import java.awt.image.BufferedImage;

/**
 * A improved subclass of {@link BufferedImage}.
 * 
 * @since 1.0
 * @author ChristopherWMM
 */
public class TerraImage extends BufferedImage implements Cloneable {
	/**
	 * Constructs a new {@link NoiseMask} object with the given values.
	 * 
	 * @param width The non-zero integer width of this {@link TerraImage} object.
	 * @param height The non-zero integer height of this {@link TerraImage} object.
	 * @param imageType The integer colorspace used by this {@link TerraImage} object.
	 * @since 1.0
	 */
	public TerraImage(int width, int height, int imageType) {
		super(width, height, imageType);
	}

	/**
	 * Constructs a new {@link TerraImage} object that is a deep copy based on the given {@link terraImage} object.
	 * 
	 * @param terraImage The {@link TerraImage} object being copied.
	 * @since 1.0
	 */
	public TerraImage(TerraImage terraImage) {
		super(terraImage.getWidth(), terraImage.getHeight(), terraImage.getType());
		setData(terraImage.getData());
	}

	/**
	 * Returns a new {@link TerraImage} object that is a deep copy of this {@link TerraImage} object.
	 * 
	 * @return A new {@link TerraImage} object that is a deep copy of this {@link TerraImage} object.
	 * @since 1.0
	 */
	public TerraImage clone() {
		return new TerraImage(this);
	}
}
