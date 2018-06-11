package me.christopherwmm.terra.gui;

import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
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

	/**
	 * Returns a new {@link TerraImage} object that is scaled up or down using Bilinear interpolation.
	 * 
	 * @param scaleFactorX The positive, non-zero double percentage the new {@link TerraImage} will be scaled in the x direction.
	 * @param scaleFactorY The positive, non-zero double percentage the new {@link TerraImage} will be scaled in the y direction.
	 * @return A new {@link TerraImage} object that is a is scaled up or down version of this {@link TerraImage} object.
	 * @throws IllegalArgumentException if the given parameters are outside of the valid range.
	 * @since 1.0
	 */
	public TerraImage scaleBilinear(final double scaleFactorX, final double scaleFactorY) {
		if (scaleFactorX < Double.MIN_VALUE) {
			throw new IllegalArgumentException("An image's x scale factor must be a positive, non-zero value. " + scaleFactorX + " is too small.");
		} else if (scaleFactorY < Double.MIN_VALUE) {
			throw new IllegalArgumentException("An image's y scale factor must be a positive, non-zero value. " + scaleFactorY + " is too small.");
		}

		final int interpolation = AffineTransformOp.TYPE_BILINEAR;
		return scale(scaleFactorX, scaleFactorY, interpolation);
	}

	/**
	 * Returns a new {@link TerraImage} object that is scaled up or down using Bicubic interpolation.
	 * 
	 * @param scaleFactorX The positive, non-zero double percentage the new {@link TerraImage} will be scaled in the x direction.
	 * @param scaleFactorY The positive, non-zero double percentage the new {@link TerraImage} will be scaled in the y direction.
	 * @return A new {@link TerraImage} object that is a is scaled up or down version of this {@link TerraImage} object.
	 * @throws IllegalArgumentException if the given parameters are outside of the valid range.
	 * @since 1.0
	 */
	public TerraImage scaleBicubic(final double scaleFactorX, final double scaleFactorY) {
		if (scaleFactorX < Double.MIN_VALUE) {
			throw new IllegalArgumentException("An image's x scale factor must be a positive, non-zero value. " + scaleFactorX + " is too small.");
		} else if (scaleFactorY < Double.MIN_VALUE) {
			throw new IllegalArgumentException("An image's y scale factor must be a positive, non-zero value. " + scaleFactorY + " is too small.");
		}

		final int interpolation = AffineTransformOp.TYPE_BICUBIC;
		return scale(scaleFactorX, scaleFactorY, interpolation);
	}

	/**
	 * Returns a new {@link TerraImage} object that is scaled up or down using Nearest Neighbor interpolation.
	 * 
	 * @param scaleFactorX The positive, non-zero double percentage the new {@link TerraImage} will be scaled in the x direction.
	 * @param scaleFactorY The positive, non-zero double percentage the new {@link TerraImage} will be scaled in the y direction.
	 * @return A new {@link TerraImage} object that is a is scaled up or down version of this {@link TerraImage} object.
	 * @throws IllegalArgumentException if the given parameters are outside of the valid range.
	 * @since 1.0
	 */
	public TerraImage scaleNearestNeighbor(final double scaleFactorX, final double scaleFactorY) {
		if (scaleFactorX < Double.MIN_VALUE) {
			throw new IllegalArgumentException("An image's x scale factor must be a positive, non-zero value. " + scaleFactorX + " is too small.");
		} else if (scaleFactorY < Double.MIN_VALUE) {
			throw new IllegalArgumentException("An image's y scale factor must be a positive, non-zero value. " + scaleFactorY + " is too small.");
		}

		final int interpolation = AffineTransformOp.TYPE_NEAREST_NEIGHBOR;
		return scale(scaleFactorX, scaleFactorY, interpolation);
	}

	/**
	 * Returns a new {@link TerraImage} object that is scaled up or down using the given form of interpolation.
	 * 
	 * @param scaleFactorX The positive, non-zero double percentage the new {@link TerraImage} will be scaled in the x direction.
	 * @param scaleFactorY The positive, non-zero double percentage the new {@link TerraImage} will be scaled in the y direction.
	 * @param type The positive, non-zero integer corresponding to the {@link AffineTransformOp} interpolation mode that will be used to scale.
	 * @return A new {@link TerraImage} object that is a is scaled up or down version of this {@link TerraImage} object.
	 * @throws IllegalArgumentException if the given parameters are outside of the valid range.
	 * @since 1.0
	 */
	private TerraImage scale(final double scaleFactorX, final double scaleFactorY, final int type) {
		if (scaleFactorX < Double.MIN_VALUE) {
			throw new IllegalArgumentException("An image's x scale factor must be a positive, non-zero value. " + scaleFactorX + " is too small.");
		} else if (scaleFactorY < Double.MIN_VALUE) {
			throw new IllegalArgumentException("An image's y scale factor must be a positive, non-zero value. " + scaleFactorY + " is too small.");
		} else if (type != AffineTransformOp.TYPE_BILINEAR && type != AffineTransformOp.TYPE_BICUBIC && type != AffineTransformOp.TYPE_NEAREST_NEIGHBOR) {
			throw new IllegalArgumentException("An image's scaleinterpolation mode must correspond to one of the AffineTransformOp constants. " + type + " is invalid.");
		}

		int height = this.getHeight();
		int width = this.getWidth();
		int scaledHeight = (int) (height * scaleFactorY);
		int scaledWidth = (int) (width * scaleFactorX);

		TerraImage scaledImage = new TerraImage(scaledWidth, scaledHeight, this.getType());
		AffineTransform scaler = AffineTransform.getScaleInstance(scaleFactorX, scaleFactorY);
		AffineTransformOp scaleOperation = new AffineTransformOp(scaler, type);
		scaleOperation.filter(this, scaledImage);
		return scaledImage;
	}
}
