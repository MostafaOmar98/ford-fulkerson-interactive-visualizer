package Graph;

public class Edge
{
    private Vertex u, v;
    private Integer cap;

    public Edge(Vertex u, Vertex v, Integer cap)
    {
        this.u = u;
        this.v = v;
        this.cap = cap;
    }

    public Vertex getU()
    {
        return u;
    }

    public void setU(Vertex u)
    {
        this.u = u;
    }

    public Vertex getV()
    {
        return v;
    }

    public void setV(Vertex v)
    {
        this.v = v;
    }

    public Integer getCap()
    {
        return cap;
    }

    public void setCap(Integer cap)
    {
        this.cap = cap;
    }

    @Override
    public String toString()
    {
        return cap.toString();
    }
}
