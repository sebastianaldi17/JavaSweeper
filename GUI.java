import javax.swing.*;
import java.awt.*;

public class GUI extends JFrame {
    JLabel title;
    public GUI() {
        // System.out.println("Calling GUI constructor");
    }
    public void launch() {
        title = new JLabel("JavaSweeper", SwingConstants.CENTER);
        title.setFont(new Font(title.getFont().getName(), Font.PLAIN, 24));

        setSize(700, 700);
        setLayout(new BorderLayout());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        
        add(new Board(), BorderLayout.CENTER);
        add(title, BorderLayout.NORTH);
        
        setVisible(true);
    }
}