package icones;

import javax.swing.*;
import java.awt.*;
import javax.swing.ImageIcon;
import java.awt.image.BufferedImage;



public class Icones {
	
	public static ImageIcon removerFundoBranco(ImageIcon icon) {
	    Image image = icon.getImage();
	    int width = image.getWidth(null);
	    int height = image.getHeight(null);

	    BufferedImage buffered = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
	    Graphics2D g2 = buffered.createGraphics();
	    g2.drawImage(image, 0, 0, null);
	    g2.dispose();

	    for (int y = 0; y < height; y++) {
	        for (int x = 0; x < width; x++) {
	            int rgba = buffered.getRGB(x, y);
	            Color col = new Color(rgba, true);
	            if (col.getRed() > 240 && col.getGreen() > 240 && col.getBlue() > 240) {
	                buffered.setRGB(x, y, 0x00FFFFFF); // torna transparente
	            }
	        }
	    }

	    return new ImageIcon(buffered);
	}
	
	
	
	public static final Image SUCESSO_PIX = new ImageIcon(Icones.class.getResource("6f021c64-c13c-4cf9-8ce7-4e18bf41d9a9.png")).getImage();
	
	public static final Image QR_CODE = new ImageIcon(Icones.class.getResource("Captura de tela 2025-10-11 115324.png")).getImage();
	
	public static final Image BOLETO = new ImageIcon(Icones.class.getResource("Captura de tela 2025-10-11 140621.png")).getImage();
	
	public Icones() {
		
		
	}
	


}
