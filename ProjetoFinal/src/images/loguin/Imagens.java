package images.loguin;

import javax.swing.*;
import java.awt.*;

public class Imagens {

    public static ImageIcon carregarIcone(String path, int width, int height) {
        try {
            ImageIcon icon = new ImageIcon(Imagens.class.getResource(path));
            Image img = icon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
            return new ImageIcon(img);
        } catch (Exception e) {
            System.err.println("Não foi possível carregar a imagem: " + path);
            return null;
        }
    }

    // Exemplo de uso fixo para o carro
    public static final ImageIcon CARRO_GRANDE = carregarIcone("carro.png",340, 219);

}