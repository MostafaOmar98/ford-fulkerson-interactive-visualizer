package Graph;

import java.util.Objects;

public class DirectedEdge
{
    private Vertex u, v;
    private Integer cap;

    public DirectedEdge(Vertex u, Vertex v, Integer cap)
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

    @Override
    public boolean equals(Object o)
    {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        DirectedEdge edge = (DirectedEdge) o;
        return Objects.equals(getU(), edge.getU()) && Objects.equals(getV(), edge.getV()) && Objects.equals(getCap(), edge.getCap());
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(getU(), getV(), getCap());
    }
}
