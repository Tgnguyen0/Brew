package app.GUI;

import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import app.InitFont.CustomFont;

import javax.imageio.ImageIO;
import javax.swing.*;

public class HomePage extends JPanel {
    private CustomFont customFont = new CustomFont();

    public static BufferedImage makeRoundedCorner(BufferedImage image, int cornerRadius) {
        int w = image.getWidth();
        int h = image.getHeight();
        BufferedImage output = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);

        Graphics2D g2 = output.createGraphics();

        // This is what we want, but it only does hard-clipping, i.e. aliasing
        // g2.setClip(new RoundRectangle2D ...)

        // so instead fake soft-clipping by first drawing the desired clip shape
        // in fully opaque white with antialiasing enabled...
        g2.setComposite(AlphaComposite.Src);
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(Color.WHITE);
        g2.fill(new RoundRectangle2D.Float(0, 0, w, h, cornerRadius, cornerRadius));

        // ... then compositing the image on top,
        // using the white shape from above as alpha source
        g2.setComposite(AlphaComposite.SrcAtop);
        g2.drawImage(image, 0, 0, null);

        g2.dispose();

        return output;
    }

    public HomePage() {
        setPreferredSize(new Dimension(1100, 200)); // 800, 500
        setLayout(null);
        setVisible(true);
        setBackground(Color.white);
        setFont(this.customFont.getFernandoFont(9));

        JPanel holdPanel = new JPanel();
        holdPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        holdPanel.setBackground(Color.white);
        holdPanel.setBounds(250, 50, 1000, 320);

        JPanel left = new JPanel();
        left.setLayout(new GridLayout(2, 0));
        left.setBackground(Color.white);

        JLabel introLabel = new JLabel(
                "<html>"
                        + "<div>"
                        + "<p style='font-size: 22px'>Brew</p>"
                        + "<p style='width:500px;'>Một nơi ấm cúng, nơi bạn có thể trốn khỏi cuộc sống thường nhật và kết nối với bạn bè, "
                        + "hoặc đơn giản là tận hưởng một khoảnh khắc bình yên với tách đồ uống thơm ngon trên tay.</p></div>"
                        + "</html>");
        introLabel.setFont(customFont.getRobotoFonts().get(0).deriveFont(Font.PLAIN, 18));
        introLabel.setHorizontalAlignment(SwingConstants.CENTER);
        introLabel.setForeground(Color.BLACK);
        left.add(introLabel);
        holdPanel.add(left);

        JPanel right = new JPanel();
        right.setBackground(Color.white);

        try {
            BufferedImage icon = ImageIO.read(new File("asset/unnamed.png"));

            // Resize ảnh trước
            int targetWidth = (int) (1024 * 0.3);
            int targetHeight = (int) (1024 * 0.3);

            Image scaled = icon.getScaledInstance(targetWidth, targetHeight, Image.SCALE_SMOOTH);

            // Chuyển lại về BufferedImage để bo góc
            BufferedImage resized = new BufferedImage(targetWidth, targetHeight, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2 = resized.createGraphics();
            g2.drawImage(scaled, 0, 0, null);
            g2.dispose();

            // Tạo ảnh bo góc
            BufferedImage rounded = makeRoundedCorner(resized, 20);

            // Tạo JLabel với ảnh
            JLabel imageHolder = new JLabel(new ImageIcon(rounded));

            right.add(imageHolder);
            holdPanel.add(right);
        } catch (IOException e) {
            e.printStackTrace();
        }

        add(holdPanel);

        JPanel holdPanel1 = new JPanel();
        holdPanel1.setLayout(new FlowLayout(FlowLayout.CENTER));
        holdPanel1.setBackground(Color.white);
        holdPanel1.setBounds(250, 380, 1000, 320);

        JPanel left1 = new JPanel();
        left1.setBackground(Color.white);

        try {
            BufferedImage icon = ImageIO.read(new File("asset/unnamed (3).png"));

            // Resize ảnh trước
            int targetWidth = (int) (1024 * 0.3);
            int targetHeight = (int) (1024 * 0.3);

            Image scaled = icon.getScaledInstance(targetWidth, targetHeight, Image.SCALE_SMOOTH);

            // Chuyển lại về BufferedImage để bo góc
            BufferedImage resized = new BufferedImage(targetWidth, targetHeight, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2 = resized.createGraphics();
            g2.drawImage(scaled, 0, 0, null);
            g2.dispose();

            // Tạo ảnh bo góc
            BufferedImage rounded = makeRoundedCorner(resized, 20);

            // Tạo JLabel với ảnh
            JLabel imageHolder = new JLabel(new ImageIcon(rounded));

            left1.add(imageHolder);
            holdPanel1.add(left1);
        } catch (IOException e) {
            e.printStackTrace();
        }

        JPanel right1 = new JPanel();
        right1.setLayout(new GridLayout(2, 0));
        right1.setBackground(Color.white);

        JLabel introLabel1 = new JLabel(
                "<html>"
                        + "<div>"
                        + "<p style='font-size:22px'>Cà phê của chúng tôi</p>"
                        + "<p style='width:500px;'>Chúng tôi tận tâm chọn lựa những hạt cà phê hảo hạng nhất từ khắp nơi trên thế giới và "
                        + "tỉ mỉ pha chế từng tách cà phê đến độ hoàn hảo. Từ giọt đầu tiên đến ngụm cuối cùng, chúng tôi mời bạn hãy "
                        + "trải nghiệm hương vị đậm đà và hương thơm tinh tế — những điều tạo nên sự khác biệt cho cà phê của chúng tôi.</p></div>"
                        + "</html>");
        introLabel1.setFont(customFont.getRobotoFonts().get(0).deriveFont(Font.PLAIN, 18));
        introLabel1.setHorizontalAlignment(SwingConstants.CENTER);
        introLabel1.setForeground(Color.BLACK);
        right1.add(introLabel1);
        holdPanel1.add(right1);

        add(holdPanel1);
    }
}
