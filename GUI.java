import javax.swing.*;
import java.awt.*;

public class GUI extends JFrame {
    public GUI() {
        System.out.println("Calling GUI constructor");
    }
    public void launch() {
        setSize(700, 700);
        setLayout(new BorderLayout());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        
        add(new Board(), BorderLayout.CENTER);
        add(new JLabel("JavaSweeper"), BorderLayout.NORTH);
        
        setVisible(true);
    }
}