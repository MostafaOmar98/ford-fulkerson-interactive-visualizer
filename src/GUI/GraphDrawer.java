package GUI;

import Algorithm.FordFulkerson;
import Graph.*;
import edu.uci.ics.jung.algorithms.layout.CircleLayout;
import edu.uci.ics.jung.algorithms.layout.Layout;
import edu.uci.ics.jung.graph.AbstractGraph;
import edu.uci.ics.jung.graph.SparseMultigraph;
import edu.uci.ics.jung.graph.util.EdgeType;
import edu.uci.ics.jung.visualization.BasicVisualizationServer;
import edu.uci.ics.jung.visualization.decorators.ToStringLabeller;
import edu.uci.ics.jung.visualization.renderers.Renderer;
import org.apache.commons.collections15.Transformer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Ellipse2D;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.Collection;

public class GraphDrawer
{
    private AbstractGraph G;
    private JFrame frame;
    private JLabel flowValue;
    ArrayList<FlowStep> flowSteps;
    Integer clickCount = 1;
    public GraphDrawer(int nV, ArrayList<DirectedEdge> edgeList, Vertex src, Vertex snk)
    {
        G = new SparseMultigraph<Vertex, DirectedEdge>();
        for (int i = 0; i < nV; ++i)
        {
            Vertex u = new Vertex(i);
            if (i == src.getIndex())
                u.setSource(true);
            if (i == snk.getIndex())
                u.setSink(true);
            G.addVertex(u);
        }

        for (DirectedEdge e : edgeList)
            addEdge(e);

        FordFulkerson ff = new FordFulkerson(edgeList, src, snk);
        ff.run();
        flowSteps = ff.getSteps();
    }

    void addEdge(DirectedEdge e)
    {
        // multiple edges with same u, v and capacity?
        G.addEdge(e, e.getU(), e.getV(), EdgeType.DIRECTED);
    }


    private void setInteractiveSolver()
    {
        JButton step = new JButton("Do One Step");
        JPanel infoPanel = new JPanel();

        JLabel flowLabel = new JLabel("Flow So Far: ");
        flowValue = new JLabel("0");
//        flowLabel.setSize(40, 40);
//        flowValue.setSize(40, 40);
        infoPanel.add(step);
        infoPanel.add(flowLabel);
        infoPanel.add(flowValue);
        frame.add(infoPanel);

        // Interactivity


        step.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                if (clickCount >= flowSteps.size())
                    return;
                FlowStep f = flowSteps.get(clickCount++);
                Integer newFlowValue = Integer.valueOf(flowValue.getText()) + f.getFlow();
                flowValue.setText(newFlowValue.toString());

                Collection<DirectedEdge> oldEdges = G.getEdges();
                ArrayList<DirectedEdge> oldEdgesList = new ArrayList<>(oldEdges);
                for (int i = 0; i < oldEdgesList.size(); ++i)
                    G.removeEdge(oldEdgesList.get(i));

                for (DirectedEdge edge : f.getEdgeList())
                    G.addEdge(edge, edge.getU(), edge.getV(), EdgeType.DIRECTED);
                frame.repaint();
            }
        });

    }

    public void draw()
    {
        // graph layout on screen
        Layout<Vertex, DirectedEdge> layout = new CircleLayout(G);
        // responsible for drawing the graph on screen
        BasicVisualizationServer<Vertex, DirectedEdge> vs = new BasicVisualizationServer<Vertex, DirectedEdge>(layout);
        setDrawingStyle(vs);

        // put graph in a frame
        frame = new JFrame("Original Graph");
        JPanel panel = new JPanel();
        panel.add(vs);
        frame.add(panel);
        frame.setExtendedState( frame.getExtendedState()|JFrame.MAXIMIZED_BOTH );

        frame.pack();
        setInteractiveSolver();
        frame.setVisible(true);
    }

    private void setDrawingStyle(BasicVisualizationServer<Vertex, DirectedEdge> vs)
    {
        // vertices
        vs.getRenderContext().setVertexLabelTransformer(new ToStringLabeller());
        Transformer<Vertex, Font> vertexFontTransformer = new Transformer<Vertex, Font>()
        {
            @Override
            public Font transform(Vertex vertex)
            {
                return new Font(vertex.toString(), 0, 20);
            }
        };
        Transformer<Vertex, Shape> vertexShapeTransformer = new Transformer<Vertex, Shape>()
        {
            @Override
            public Shape transform(Vertex vertex)
            {
                return new Ellipse2D.Double(-15, -15, 50, 50);
            }
        };
        Transformer<Vertex,Paint> vertexColor = new Transformer<Vertex,Paint>() {
            @Override
            public Paint transform(Vertex u) {
                if (u.isSource())
                    return Color.GREEN;
                if (u.isSink())
                    return Color.YELLOW;
                return Color.RED;
            }
        };
        vs.getRenderContext().setVertexFontTransformer(vertexFontTransformer);
        vs.getRenderContext().setVertexShapeTransformer(vertexShapeTransformer);
        vs.getRenderContext().setVertexFillPaintTransformer(vertexColor);
        vs.getRenderer().getVertexLabelRenderer().setPosition(Renderer.VertexLabel.Position.CNTR);


        // edges
        Transformer<DirectedEdge, Font> edgeFontTransformer = new Transformer<DirectedEdge, Font>()
        {
            @Override
            public Font transform(DirectedEdge edge)
            {
                return new Font(edge.toString(), 0, 20);
            }
        };

        Transformer<DirectedEdge, Paint> edgePaintTransformer = new Transformer<DirectedEdge, Paint>()
        {
            @Override
            public Paint transform(DirectedEdge directedEdge)
            {
                if (directedEdge.isHighlighted())
                    return Color.GREEN;
                return Color.BLACK;
            }
        };

        vs.getRenderContext().setEdgeLabelTransformer(new ToStringLabeller<>());
        vs.getRenderContext().setEdgeFontTransformer(edgeFontTransformer);
        vs.getRenderContext().setEdgeDrawPaintTransformer(edgePaintTransformer);
    }
}
