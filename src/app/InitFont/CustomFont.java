package app.InitFont;

import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CustomFont {
    private Font fernandoFont;
    private Font twistyPixelFont;
    private final ArrayList<Font> robotoFont = new ArrayList<>();

    public CustomFont() {
        this.fernandoFont = loadFontFromResource("font/FVF Fernando 08.ttf");
        this.twistyPixelFont = loadFontFromResource("font/CSFONT-TwistyPixel.ttf");

        List<String> paths = Arrays.asList(
                "font/RobotoMono-Regular.ttf",
                "font/RobotoMono-Bold.ttf",
                "font/RobotoMono-BoldItalic.ttf",
                "font/RobotoMono-Italic.ttf");

        robotoFont.addAll(loadFontsFromResources(paths));
    }

    private Font loadFontFromResource(String path) {
        try {
            File fontFile = new File(path);
            // InputStream fontFile = getClass().getResourceAsStream(path);

            Font font = Font.createFont(Font.TRUETYPE_FONT, fontFile);
            GraphicsEnvironment.getLocalGraphicsEnvironment().registerFont(font);
            return font;
        } catch (Exception e) {
            e.printStackTrace();
            return new Font("Arial", Font.PLAIN, 12);
        }
    }

    private List<Font> loadFontsFromResources(List<String> paths) {
        List<Font> fonts = new ArrayList<>();
        for (String path : paths) {
            fonts.add(loadFontFromResource(path));
        }
        return fonts;
    }

    public Font getFernandoFont(float size) {
        return this.fernandoFont.deriveFont(size);
    }

    public Font getTwistyPixelFont(float size) {
        return this.twistyPixelFont.deriveFont(size);
    }

    public List<Font> getRobotoFonts() {
        return this.robotoFont;
    }
}

