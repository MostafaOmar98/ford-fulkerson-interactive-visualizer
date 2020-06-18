import GUI.Input;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import GUI.*;
public class Main
{
    public static void main(String[] args)
    {
        SwingUtilities.invokeLater(new Runnable()
        {
            public void run()
            {
                JFrame introFrame = new JFrame("Cover Page");
                JPanel introPanel = new JPanel();
                introPanel.setLayout(new BoxLayout(introPanel, BoxLayout.PAGE_AXIS));
                JTextArea introText = new JTextArea("Faculty of Computers and Artificial Intelligence,\n" +
                        "Cairo University\n\n\n" +
                        "CS316\n\n\n" +
                        "Algorithms: Analysis and Design\n" +
                        "2nd Semester 2020 Research\n" +
                        "1st Topic: Maximum Flow and Dijkstra\n" +
                        "PID23868166\n\n\n" +
                        "Created By:\n" +
                        "1- Mostafa Omar Mahmoud - 20170292\n" +
                        "2- Abdelrahman Ibrahim Ibrahim - 20170139\n" +
                        "3- Ahmad Khaled Fawzy - 20170377\n" +
                        "4- Ahmed Mohamed Hanafy - 20170357\n" +
                        "5- Ahmed Wessam Fathy - 20170372\n\n\n" +
                        "Under the supervision of: \n" +
                        "Dr. Moustafa Reda El-Tantawy\n");
                introText.setFont(new Font("Arial", Font.PLAIN, 20));
                introText.setEditable(false);
                JButton startProgram = new JButton("Start Program");
                introFrame.add(introPanel);
                introPanel.add(introText);
                introPanel.add(startProgram);
                introFrame.pack();
                introFrame.setSize(800, 800);
                introFrame.setVisible(true);
                introFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                startProgram.addActionListener(new ActionListener()
                {
                    @Override
                    public void actionPerformed(ActionEvent event)
                    {
                        introFrame.dispose();
                        JFrame frame = new JFrame("Max Flow Solver");
                        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

                        frame.add(new Input(), BorderLayout.CENTER);

                        frame.pack();
                        frame.setVisible(true);
                    }
                });
            }
        });

    }
}
