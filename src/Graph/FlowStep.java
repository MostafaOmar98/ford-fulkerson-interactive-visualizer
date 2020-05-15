package Graph;

import java.util.ArrayList;

public class FlowStep
{
    ArrayList<DirectedEdge> highlighted, unhighlighted;
    Integer flow;

    public FlowStep(Integer flow)
    {
        this.flow = flow;
        highlighted = new ArrayList<DirectedEdge>();
        unhighlighted = new ArrayList<DirectedEdge>();
    }

    public FlowStep(ArrayList<DirectedEdge> highlighted, ArrayList<DirectedEdge> unhighlighted, Integer flow)
    {
        this.highlighted = highlighted;
        this.unhighlighted = unhighlighted;
        this.flow = flow;
    }

    public ArrayList<DirectedEdge> getHighlighted()
    {
        return highlighted;
    }

    public void setHighlighted(ArrayList<DirectedEdge> highlighted)
    {
        this.highlighted = highlighted;
    }

    public ArrayList<DirectedEdge> getUnhighlighted()
    {
        return unhighlighted;
    }

    public void setUnhighlighted(ArrayList<DirectedEdge> unhighlighted)
    {
        this.unhighlighted = unhighlighted;
    }

    public Integer getFlow()
    {
        return flow;
    }

    public void setFlow(Integer flow)
    {
        this.flow = flow;
    }

    public void addModifiedEdge(DirectedEdge e)
    {
        highlighted.add(e);
    }

    public void addUnmodifiedEdge(DirectedEdge e)
    {
        unhighlighted.add(e);
    }

    public ArrayList<DirectedEdge> getResidualGraph()
    {
        ArrayList<DirectedEdge> edgeList = highlighted;
        highlighted.addAll(unhighlighted);
        return edgeList;
    }
}
