package Graph;

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
    public String toString()
    {
        return "V" + index;
    }
}
