package Graph;

import java.util.Objects;

public class Vertex
{
    private Integer index;

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
}
