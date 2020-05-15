package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class Input extends JPanel
{

    private ArrayList<JTextField> edgeListTF; // TF for TextField
    private JTextField srcTF, snkTF;
    private JPanel edgesPanel; // Edges input From, To, Cap
    JComboBox nVertices;
    JComboBox nEdges;
    public Input()
    {
        this.edgeListTF = new ArrayList<>();
        srcTF = new JTextField();
        snkTF = new JTextField();
        createSetupPanel();
    }

    private JPanel createVEInputPanel()
    {
        JPanel form = new JPanel(new GridLayout(5, 5));

        int MAX_NODES = 100;
        Integer[] numbers = new Integer[MAX_NODES];
        for (int i = 0; i < MAX_NODES; ++i)
            numbers[i] = i + 1;

        JLabel nVerticesLabel = new JLabel("Select number of vertices");
        nVertices = new JComboBox<>(numbers);
        JLabel nEdgesLabel = new JLabel("Select number of edges");
        nEdges = new JComboBox<>(numbers);
        JLabel srcLabel = new JLabel("Enter source node: ");
        JLabel snkLabel = new JLabel("Enter destination node: ");

        form.add(nVerticesLabel);
        form.add(nVertices);
        form.add(nEdgesLabel);
        form.add(nEdges);
        form.add(srcLabel);
        form.add(srcTF);
        form.add(snkLabel);
        form.add(snkTF);


        // Action Listeners
        nEdges.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e)
            {
                reinitializeEdgesPanel();
            }
        });

        return form;
    }

    private void reinitializeEdgesPanel()
    {
        edgesPanel.removeAll();
        edgesPanel.add(createEdgesInput((Integer) nEdges.getSelectedItem()));
        edgesPanel.revalidate();
        edgesPanel.repaint();
    }

    private JPanel createEdgesInput(Integer nEdges)
    {
        edgeListTF.clear();
        JPanel panel = new JPanel(new GridLayout(nEdges + 1, 2));
        panel.add(new JLabel("From"));
        panel.add(new JLabel("To"));
        panel.add(new JLabel("Capacity"));
        Dimension size = new Dimension(50, 20);
        for (int i = 0; i < nEdges; ++i)
        {
            JTextField u = new JTextField();
            JTextField v = new JTextField();
            JTextField cost = new JTextField();
            u.setPreferredSize(size);
            v.setPreferredSize(size);
            cost.setPreferredSize(size);
            panel.add(u);
            panel.add(v);
            panel.add(cost);
            edgeListTF.add(u);
            edgeListTF.add(v);
            edgeListTF.add(cost);
        }
        return panel;
    }

    private JScrollPane createEdgesInputPanel()
    {
        edgesPanel = new JPanel(false);
        edgesPanel.setAutoscrolls(true);
        JScrollPane edgesMain = new JScrollPane(edgesPanel);
        edgesMain.setPreferredSize(new Dimension(600, 450));
        return edgesMain;
    }

    private JPanel createGeneratePanel()
    {
        JPanel generatePanel = new JPanel(new BorderLayout());
        JButton generate = new JButton("Generate!");
        generatePanel.add(generate);
        return generatePanel;
    }

    private void createSetupPanel()
    {
        // form for selection of number of vertices and edges
        JPanel form = createVEInputPanel();

        // input edges panel
        JScrollPane edgesInputPanel = createEdgesInputPanel();

        // generate button panel
        JPanel generatePanel = createGeneratePanel();

        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        add(form);
        add(edgesInputPanel);
        add(generatePanel);
    }
}
