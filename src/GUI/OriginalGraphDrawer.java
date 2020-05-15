package GUI;

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
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;

public class OriginalGraphDrawer
{
    AbstractGraph G;

    public OriginalGraphDrawer(int nV, ArrayList<DirectedEdge> edgeList, Vertex src, Vertex snk)
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
        vs.getRenderContext().setEdgeLabelTransformer(new ToStringLabeller<>());
        vs.getRenderContext().setEdgeFontTransformer(edgeFontTransformer);
    }
}
