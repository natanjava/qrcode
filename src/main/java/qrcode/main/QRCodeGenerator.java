package qrcode.main;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class QRCodeGenerator {
    public static void main(String[] args) {
        String text = "http://www.mywebsite.com/"; // redirect any link through the generated image
        int width = 300; // Image width
        int height = 300; // Image height
        String fileType = "png"; // Image format

        try {
            // Configure the QR code parameters
            Map<EncodeHintType, Object> hints = new HashMap();
            hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);

            // Generates the QR code bit matrix
            BitMatrix bitMatrix = new MultiFormatWriter().encode(text, BarcodeFormat.QR_CODE, width, height, hints);

            // Create the QR code image
            BufferedImage qrCodeImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            qrCodeImage.createGraphics();

            Graphics2D graphics = (Graphics2D) qrCodeImage.getGraphics();
            graphics.setColor(Color.WHITE);
            graphics.fillRect(0, 0, width, height);
            graphics.setColor(Color.BLACK);

            for (int i = 0; i < width; i++) {
                for (int j = 0; j < height; j++) {
                    if (bitMatrix.get(i, j)) {
                        graphics.fillRect(i, j, 1, 1);
                    }
                }
            }

            // Saves the QR code image to a file, within the project folder
            File qrCodeFile = new File("qrcode." + fileType);
            ImageIO.write(qrCodeImage, fileType, qrCodeFile);

            System.out.println("QR code generated successfully!");
        } catch (Exception e) {
            System.err.println("Error generating QR code: " + e.getMessage());
        }
    }
}