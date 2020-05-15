package Graph;

import java.util.ArrayList;

public class Residual
{
    ArrayList<Edge> modified, unmodified;
    Integer flow;

    public Residual(Integer flow)
    {
        this.flow = flow;
        modified = new ArrayList<Edge>();
        unmodified = new ArrayList<Edge>();
    }

    public ArrayList<Edge> getModified()
    {
        return modified;
    }

    public void setModified(ArrayList<Edge> modified)
    {
        this.modified = modified;
    }

    public ArrayList<Edge> getUnmodified()
    {
        return unmodified;
    }

    public void setUnmodified(ArrayList<Edge> unmodified)
    {
        this.unmodified = unmodified;
    }

    public Integer getFlow()
    {
        return flow;
    }

    public void setFlow(Integer flow)
    {
        this.flow = flow;
    }

    public void addModifiedEdge(Edge e)
    {
        modified.add(e);
    }

    public void addUnmodifiedEdge(Edge e)
    {
        unmodified.add(e);
    }
}
