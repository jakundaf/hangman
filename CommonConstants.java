import java.awt.*;

public class CommonConstants {
    // file paths
    public static final String DATA_PATH = "resources/data.txt";
    public static final String IMAGE_PATH = "resources/1.png";

    // window size config
    public static final Dimension FRAME_SIZE = new Dimension(1024, 768);
    public static final Dimension BUTTON_PANEL_SIZE = new Dimension ((int) (FRAME_SIZE.width * 0.42), (int) (FRAME_SIZE.height * 0.42));
    public static final Dimension RESULT_DIALOG_SIZE = new Dimension ((int) (FRAME_SIZE.width/2), (int) (FRAME_SIZE.height/6));


    public static final Color PRIMARY_COLOR = Color.decode("#123456");
    public static final Color SECONDARY_COLOR = Color.decode("#FCA311");
    public static final Color BACKGROUND_COLOR = Color.decode("#101820");


}
