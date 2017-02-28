package org.ai.util;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class ImageUtil {

	public static BufferedImage margeBufferedImage(BufferedImage[] buffImages) {

		int size = buffImages.length;
		int rows = 0, cols = 0;

		if (size == 1) {
			rows = 1;
			cols = 1;
		} else if (size == 2) {
			rows = 1;
			cols = 2;
		} else if (size == 3 || size == 4) {
			rows = 2;
			cols = 2;
		} else {
			rows = size / 4;
			cols = 4;
		}

		return margeBufferedImage(rows, cols, buffImages);
	}

	public static BufferedImage margeBufferedImage(int rows, int cols, BufferedImage[] buffImages) {

		int type = buffImages[0].getType();
		int chunkWidth = buffImages[0].getWidth();
		int chunkHeight = buffImages[0].getHeight();

		// Initializing the final image
		BufferedImage finalImg = new BufferedImage(chunkWidth * cols, chunkHeight * rows, type);
		Graphics2D graphics = finalImg.createGraphics();
		graphics.setPaint(Color.WHITE);
		graphics.fillRect(0, 0, finalImg.getWidth(), finalImg.getHeight());

		int num = 0;
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				if (num == buffImages.length) {
					break;
				}
				graphics.drawImage(buffImages[num++], chunkWidth * j, chunkHeight * i, null);
			}
		}

		graphics.dispose();

		return finalImg;

	}
}
