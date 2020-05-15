package Graph;

import java.util.ArrayList;

public class FlowStep
{
    ArrayList<DirectedEdge> edgeList;
    Integer flow;

    public FlowStep(Integer flow)
    {
        this.flow = flow;
        edgeList = new ArrayList<DirectedEdge>();
    }

    public FlowStep(ArrayList<DirectedEdge> edgeList, Integer flow)
    {
        this.edgeList = new ArrayList<DirectedEdge>();
        setEdgeList(edgeList);
        this.flow = flow;
    }

    public ArrayList<DirectedEdge> getEdgeList()
    {
        return edgeList;
    }

    public void setEdgeList(ArrayList<DirectedEdge> edgeList)
    {
        this.edgeList.clear();
        for (DirectedEdge e : edgeList) // deep copy
            this.edgeList.add(new DirectedEdge(new Vertex(e.getU().getIndex()), new Vertex(e.getV().getIndex()), e.getCap(), e.isHighlighted()));
    }

    public Integer getFlow()
    {
        return flow;
    }

    public void setFlow(Integer flow)
    {
        this.flow = flow;
    }
}
