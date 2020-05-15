package GUI;

import Graph.*;
import edu.uci.ics.jung.algorithms.layout.CircleLayout;
import edu.uci.ics.jung.algorithms.layout.Layout;
import edu.uci.ics.jung.graph.AbstractGraph;
import edu.uci.ics.jung.graph.DirectedSparseMultigraph;
import edu.uci.ics.jung.graph.SparseMultigraph;
import edu.uci.ics.jung.graph.util.EdgeType;
import edu.uci.ics.jung.visualization.BasicVisualizationServer;
import edu.uci.ics.jung.visualization.decorators.ToStringLabeller;
import edu.uci.ics.jung.visualization.renderers.Renderer;
import edu.uci.ics.jung.visualization.*;
import javafx.scene.shape.Circle;
import org.apache.commons.collections15.Transformer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;

public class OriginalGraphDrawer
{
    AbstractGraph G;

    public OriginalGraphDrawer(int nV, ArrayList<DirectedEdge> edgeList)
    {
        G = new SparseMultigraph<Vertex, DirectedEdge>();
        for (int i = 0; i < nV; ++i)
            G.addVertex(new Vertex(i));

        for (DirectedEdge e : edgeList)
            addEdge(e);
    }

    void addEdge(DirectedEdge e)
    {
        // multiple edges with same u, v and capacity?
        G.addEdge(e, e.getU(), e.getV(), EdgeType.DIRECTED);
    }

    public void draw()
    {
        // For getting dimensions of the window
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice defaultScreen = ge.getDefaultScreenDevice();
        Rectangle rect = defaultScreen.getDefaultConfiguration().getBounds();
        Dimension quarterScreen = new Dimension((int) rect.getMaxX() / 2, (int) rect.getMaxY() / 2);

        // graph layout on screen
        Layout<Vertex, DirectedEdge> layout = new CircleLayout(G);
        layout.setSize(quarterScreen);
        // responsible for drawing the graph on screen
        BasicVisualizationServer<Vertex, DirectedEdge> vs = new BasicVisualizationServer<Vertex, DirectedEdge>(layout);
        vs.setPreferredSize(quarterScreen);
        setDrawingStyle(vs);

        // put graph in a frame
        JFrame frame = new JFrame("Original Graph");
        frame.add(vs);
        frame.pack();
        frame.setVisible(true);
    }

    private void setDrawingStyle(BasicVisualizationServer<Vertex, DirectedEdge> vs)
    {

    }
}
