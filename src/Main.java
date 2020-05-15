import GUI.Input;

import javax.swing.*;
import java.awt.*;
import GUI.*;
public class Main
{
    public static void main(String[] args)
    {
        SwingUtilities.invokeLater(new Runnable()
        {
            public void run()
            {
                JFrame frame = new JFrame("Max Flow Solver");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

                frame.add(new Input(), BorderLayout.CENTER);

                frame.pack();
                frame.setVisible(true);
            }
        });

    }
}
