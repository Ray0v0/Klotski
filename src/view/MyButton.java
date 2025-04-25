package view;

import javax.swing.*;
import java.awt.*;

public class MyButton extends JButton {

    public static Font btnFont = new Font("SansSerif", Font.BOLD, 16);

    public MyButton(String text) {
        super(text);
        setFont(btnFont);
        setAlignmentX(Component.CENTER_ALIGNMENT);
        setMaximumSize(new Dimension(200, 40));
        setContentAreaFilled(true);   // 确保填充
        setOpaque(true);
    }
}
