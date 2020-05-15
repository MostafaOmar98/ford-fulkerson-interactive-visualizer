package Graph;

import java.util.Objects;

public class DirectedEdge
{
    private Vertex u, v;
    private Integer cap;
    private boolean isHighlighted = false;
    public DirectedEdge(Vertex u, Vertex v, Integer cap)
    {
        this.u = u;
        this.v = v;
        this.cap = cap;
    }

    public DirectedEdge(Vertex u, Vertex v, Integer cap, Boolean isHighlighted)
    {
        this.u = u;
        this.v = v;
        this.cap = cap;
        this.isHighlighted = isHighlighted;
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

    public Boolean isHighlighted()
    {
        return isHighlighted;
    }

    public void setHighlighted(Boolean highlighted)
    {
        isHighlighted = highlighted;
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(getU(), getV(), getCap());
    }

    public boolean isReverse(DirectedEdge r)
    {
        if (this.getU().equals(r.getV()) && this.getV().equals(r.getU()))
            return true;
        return false;
    }
}
