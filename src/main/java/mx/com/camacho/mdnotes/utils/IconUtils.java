package mx.com.camacho.mdnotes.utils;

import javafx.scene.image.Image;
import mx.com.camacho.mdnotes.MarkdownNotes;
import mx.com.camacho.mdnotes.constants.ApplicationIcon;
import java.util.Objects;

public class IconUtils {

    public static Image loadIcon (ApplicationIcon icon) {
        return new Image(Objects.requireNonNull(MarkdownNotes.class.getResourceAsStream(icon.path)));
    }

    private IconUtils () {}
}
