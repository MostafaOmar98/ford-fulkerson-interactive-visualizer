package Graph;

import java.util.Objects;

public class Vertex
{
    private Integer index;
    private Boolean isSrc = false, isSnk = false;

    public Vertex(Integer index)
    {
        this.index = index;
    }

    public Integer getIndex()
    {
        return index;
    }

    public void setIndex(Integer index)
    {
        this.index = index;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Vertex vertex = (Vertex) o;
        return Objects.equals(getIndex(), vertex.getIndex());
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(getIndex());
    }

    @Override
    public String toString()
    {
        return "V" + index;
    }

    public Boolean isSource()
    {
        return isSrc;
    }

    public void setSource(Boolean src)
    {
        isSrc = src;
    }

    public Boolean isSink()
    {
        return isSnk;
    }

    public void setSink(Boolean snk)
    {
        isSnk = snk;
    }
}
