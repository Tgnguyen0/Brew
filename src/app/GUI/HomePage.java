package app.GUI;

import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import app.InitFont.CustomFont;

import javax.imageio.ImageIO;
import javax.swing.*;

import org.jfree.chart.ui.VerticalAlignment;

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
        // setBackground(new Color(70, 33, 26));
        // setBackground(new Color(164, 56, 32));
        // setBackground(new Color(241, 211, 178));
        // setBackground(new Color(224, 224, 224));
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
                        + "<p style='font-size: 22px'>Dev cafe</p>"
                        + "<p style='width:500px;'>A cozy haven where you can escape the everyday, connect with friends, "
                        + "or simply savor a moment of peace with a delicious cup in hand.</p></div>"
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
                        + "<p style='font-size:22px'>Our Coffee</p>"
                        + "<p style='width:500px;'>We're dedicated to sourcing the finest beans from around the world and "
                        + "expertly crafting each cup to perfection. From the first pour to the last sip, we invite you to "
                        + "experience the rich flavors and nuanced aromas that set our coffee apart.</p></div>"
                        + "</html>");
        introLabel1.setFont(customFont.getRobotoFonts().get(0).deriveFont(Font.PLAIN, 18));
        introLabel1.setHorizontalAlignment(SwingConstants.CENTER);
        introLabel1.setForeground(Color.BLACK);
        right1.add(introLabel1);
        holdPanel1.add(right1);

        add(holdPanel1);

        // String imagePath = "dev_cafe/asset/coffee.gif";
        // // File imageFile = new File(imagePath);

        // // Tạo icon cho ảnh
        // ImageIcon imageIcon = new ImageIcon(imagePath); // image_width: 2560, 1440

        // // Tạo imageLabel cho ảnh
        // JLabel imageLabel = new JLabel(imageIcon);
        // // Thêm imageLabel vào optionMenu
        // add(imageLabel, BorderLayout.NORTH);

        // Thêm thanh chào mừng
        /*
         * JLabel homeBanner = new JLabel("Chào mừng đến Dev_Cafe");
         * homeBanner.setPreferredSize(new Dimension(350, 50));
         * homeBanner.setFont(customFont.getFernandoFont().deriveFont(Font.PLAIN, 20));
         * homePageIntro.add(homeBanner, BorderLayout.SOUTH);
         *
         * homePageContent.add(homePageIntro, BorderLayout.NORTH);
         *
         * JPanel homePageCenter = new JPanel();
         * homePageCenter.setPreferredSize(new Dimension(200, 400));
         * homePageCenter.setBackground(new Color(225, 203, 177));
         *
         * JLabel introductionParagrah1 = new
         * JLabel("<html>Chào mừng bạn đến với Dev_Cafe, nơi hương vị của cà phê tươi mới hòa quyện hoàn hảo với sự náo nhiệt của sự đổi mới công nghệ.Nằm ở ngã tư của công nghệ tiên tiến và bầu không khí ấm áp của một quán cà phê, Dev_Cafe mang đến trải nghiệm độc đáo cho các đối tượng yêu công nghệ, doanh nhân và những người yêu thích cà phê.</html>"
         * );
         * introductionParagrah1.setPreferredSize(new Dimension(700, 75));
         * introductionParagrah1.setFont(customFont.getFernandoFont());
         * homePageCenter.add(introductionParagrah1, BorderLayout.NORTH);
         *
         * JLabel introductionParagrah2 = new
         * JLabel("<html>Tại Dev_Cafe, chúng tôi hiểu rằng công nghệ đóng vai trò quan trọng trong việc hình thành thế giới của chúng ta. Dù bạn là một nhà phát triển có kinh nghiệm, một nhà sáng lập startup, hay chỉ đơn giản là tò mò về những xu hướng công nghệ mới nhất, quán cà phê của chúng tôi cung cấp môi trường hoàn hảo để kích thích sự sáng tạo của bạn và kết nối với những người có cùng sở thích.</html>"
         * );
         * introductionParagrah2.setPreferredSize(new Dimension(700, 75));
         * introductionParagrah2.setFont(customFont.getFernandoFont());
         * homePageCenter.add(introductionParagrah2, BorderLayout.NORTH);
         *
         * JLabel introductionParagrah3 = new
         * JLabel("<html>Hãy tưởng tượng bạn đang thưởng thức ly espresso yêu thích của mình trong khi thảo luận về những tiến bộ mới nhất trong trí tuệ nhân tạo, ý tưởng cho dự án lập trình tiếp theo của bạn, hoặc tham gia một buổi hội thảo về công nghệ blockchain—tất cả đều diễn ra trong không gian chào đón của quán cà phê với trang trí hiện đại và sang trọng.</html>"
         * );
         * introductionParagrah3.setPreferredSize(new Dimension(700, 75));
         * introductionParagrah3.setFont(customFont.getFernandoFont());
         * homePageCenter.add(introductionParagrah3, BorderLayout.NORTH);
         *
         * JLabel introductionParagrah4 = new
         * JLabel("<html>Nhưng Dev_Cafe không chỉ là nơi để thưởng thức cà phê tuyệt vời và trò chuyện về công nghệ. Đó là một trung tâm cộng đồng nơi ý tưởng ra đời, hợp tác phát triển, và tạo ra những mối quan hệ. Nhân viên có kiến thức của chúng tôi luôn sẵn lòng pha một ly cà phê hoàn hảo và tạo điều kiện cho các cuộc thảo luận sôi nổi về mọi thứ từ phát triển phần mềm đến tiếp thị số.</html>"
         * );
         * introductionParagrah4.setPreferredSize(new Dimension(700, 75));
         * introductionParagrah4.setFont(customFont.getFernandoFont());
         * homePageCenter.add(introductionParagrah4, BorderLayout.NORTH);
         *
         * JLabel introductionParagrah5 = new
         * JLabel("<html>Dù bạn đang tìm kiếm nguồn cảm hứng cho dự án lớn tiếp theo của mình, khao khát sự trò chuyện thú vị, hay chỉ đơn giản là tìm kiếm một nơi thoải mái để làm việc từ xa, Dev_Cafe luôn chào đón bạn với tay mở rộng. Hãy đến với chúng tôi và khám phá một thế giới nơi công nghệ và cà phê gặp nhau để tạo ra một trải nghiệm khó quên. Chào mừng bạn đến với Dev_Cafe—nơi sự đổi mới chưa bao giờ ngon như vậy.</html>"
         * );
         * introductionParagrah5.setPreferredSize(new Dimension(700, 75));
         * introductionParagrah5.setFont(customFont.getFernandoFont());
         * homePageCenter.add(introductionParagrah5, BorderLayout.NORTH);
         * homePageContent.add(homePageCenter, BorderLayout.CENTER);
         *
         * JScrollPane homePageScrollPane = new JScrollPane(homePageContent);
         * homePageScrollPane.setPreferredSize(new Dimension(900, 900));
         * homePageScrollPane.setVerticalScrollBarPolicy(JScrollPane.
         * VERTICAL_SCROLLBAR_ALWAYS);
         *
         * add(homePageScrollPane, BorderLayout.CENTER);
         */
    }

    /*
     * @Override
     * protected void paintComponent(Graphics g) {
     * String imagePath = "dev_cafe/asset/coffee.gif"; // Path to your GIF image
     * file
     * File imageFile = new File(imagePath);
     *
     * //Chèn ảnh vào Option menu
     * try {
     * // Đọc ảnh từ file
     * Image image = ImageIO.read(imageFile);
     *
     * // Tạo icon cho ảnh
     * int newWidth = getWidth(); // Get the width of the panel
     * int newHeight = getHeight(); // Get the height of the panel
     * Image scaledImage = image; //getScaledInstance(newWidth, newHeight,
     * Image.SCALE_SMOOTH);
     * g.drawImage(scaledImage, 0, 0, null);
     * } catch (IOException e) {
     * e.printStackTrace();
     * }
     * }
     */
}
