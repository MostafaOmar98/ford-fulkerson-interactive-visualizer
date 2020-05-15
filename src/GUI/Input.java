package GUI;

import Graph.DirectedEdge;
import Graph.Vertex;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Input extends JPanel
{

    private ArrayList<DirectedEdge> edgeList;
    Vertex src, snk;
    public Input()
    {
        this.edgeList = new ArrayList<>();
        createSetupPanel();
    }

    private JPanel createVEInputPanel()
    {
        JPanel form = new JPanel(new GridLayout(3, 3));

        int MAX_NODES = 100;
        Integer[] numbers = new Integer[MAX_NODES];
        for (int i = 0; i < MAX_NODES; ++i)
            numbers[i] = i + 1;

        JLabel nVerticesLabel = new JLabel("Select number of vertices");
        JComboBox nVertices = new JComboBox<>(numbers);
        JLabel nEdgesLabel = new JLabel("Select number of edges");
        JComboBox nEdges = new JComboBox<>(numbers);

        form.add(nVerticesLabel);
        form.add(nVertices);
        form.add(nEdgesLabel);
        form.add(nEdges);
        return form;
    }

    private JScrollPane createEdgesInputPanel()
    {
        JPanel edges = new JPanel(false);
        edges.setAutoscrolls(true);
        JScrollPane edgesMain = new JScrollPane(edges);
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
