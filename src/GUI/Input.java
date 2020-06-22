package GUI;

import Graph.DirectedEdge;
import Graph.Vertex;

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

        // Action listeners
        generate.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent event)
            {
                generateGraph();
            }
        });

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

    private void generateGraph()
    {
        ArrayList<DirectedEdge> edgesArr = new ArrayList<>();
        Integer nV = (Integer) nVertices.getSelectedItem();
        int error = 0;
        // Loop over inputs
        for (int i = 0; i < edgeListTF.size(); i += 3)
        {
            JTextField u = edgeListTF.get(i);
            JTextField v = edgeListTF.get(i + 1);
            JTextField cap = edgeListTF.get(i + 2);
            try
            {
                Integer uid = Integer.valueOf(u.getText());
                uid--;
                Integer vid = Integer.valueOf(v.getText());
                vid--;
                Integer capValue = Integer.valueOf(cap.getText());
                DirectedEdge e = new DirectedEdge(new Vertex(uid), new Vertex(vid), capValue);
                // Check that there is no out of bounds vertices
                if (uid >= nV || vid >= nV || uid < 0 || vid < 0)
                {
                    error = 1;
                    break;
                }
                // positive capacites
                if (capValue < 0)
                {
                    error = 3;
                    break;
                }
                edgesArr.add(e);
            } catch (NumberFormatException e)
            {
                error = 2;
                break;
            }
        }
        Integer srcId = null;
        Integer snkId = null;
        try
        {
            srcId = Integer.valueOf(srcTF.getText());
            srcId--;
            snkId = Integer.valueOf(snkTF.getText());
            snkId--;
            if (srcId.equals(snkId))
                error = 4;
            if (srcId >= nV || snkId >= nV || srcId < 0 || snkId < 0)
                error = 1;
        } catch(NumberFormatException e)
        {
            error = 2;
        }

        if (error == 0)
        {
            ArrayList<DirectedEdge> toAddEdges = new ArrayList<>();
            for (DirectedEdge e : edgesArr)
            {
                boolean ok = false;
                for (DirectedEdge r : edgesArr)
                {
                    if (e.isReverse(r))
                    {
                        ok = true;
                        break;
                    }
                }
                if (!ok)
                    toAddEdges.add(new DirectedEdge(new Vertex(e.getV().getIndex()), new Vertex(e.getU().getIndex()), 0));
            }
            edgesArr.addAll(toAddEdges);
            MaxFlowVisualizer visualizer = new MaxFlowVisualizer(nV, edgesArr, new Vertex(srcId), new Vertex(snkId));
            visualizer.run();
        }
        else if (error == 1)
            JOptionPane.showMessageDialog(null, "Node Numbers must be between [1, n] inclusive");
        else if (error == 2)
            JOptionPane.showMessageDialog(null, "Please enter all fields in numeric format (All entires must be integer numbers)");
        else if (error == 3)
            JOptionPane.showMessageDialog(null, "Please enter positive capacities values");
        else if (error == 4)
            JOptionPane.showMessageDialog(null, "Please enter different source and destination");


    }

}
