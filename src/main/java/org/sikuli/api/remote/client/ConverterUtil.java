package org.sikuli.api.remote.client;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import org.openqa.selenium.internal.Base64Encoder;

public class ConverterUtil {

	static public String encodeImage(BufferedImage image){			
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		try {
			ImageIO.write(image, "png", os);
			String base64 = new Base64Encoder().encode(os.toByteArray());
			return base64;
		} catch (IOException e) {
			throw new RuntimeException(e);			
		}
	}

	static public BufferedImage decodeImage(String valueAsString){			
		byte[] bytes = new Base64Encoder().decode(valueAsString);
		InputStream is = new ByteArrayInputStream(bytes);			
		try {
			return ImageIO.read(is);
		} catch (IOException e) {				
		}	

		return null;
	}
}