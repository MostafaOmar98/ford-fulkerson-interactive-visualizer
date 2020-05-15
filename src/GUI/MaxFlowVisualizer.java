package GUI;

import Graph.*;

import java.util.ArrayList;

public class MaxFlowVisualizer
{
    Integer nV;
    ArrayList<DirectedEdge> edgeList;
    Vertex src, snk;

    public MaxFlowVisualizer(Integer nV, ArrayList<DirectedEdge> edgeList, Vertex src, Vertex snk)
    {
        this.edgeList = edgeList;
        this.nV = nV;
        this.src = src;
        this.snk = snk;
    }

    public void run()
    {
        OriginalGraphDrawer originalGraphDrawer = new OriginalGraphDrawer(nV, edgeList);
        originalGraphDrawer.draw();
    }
}
