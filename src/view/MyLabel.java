package view;

import javax.swing.*;

public class MyLabel extends JLabel {
    public MyLabel(String text) {
        super(text);
        setAlignmentX(CENTER_ALIGNMENT);
        setFont(MyButton.btnFont);
    }
}
