package IA.Desastres;

public class CountTripVisitor implements ITripVisitor
{
    private final Grupos grupos;
    public int count;
    public CountTripVisitor (final Grupos p_grupos)
    {
        grupos = p_grupos;
        count = 0;
    }
    public void visit (final Trip p_trip)
    {
        for (Integer i : p_trip)
        {
            count += grupos.get (i).getNPersonas();
        }
    }

    public int getCount ()
    {
        return count;
    }

    public void setCount (int p_count)
    {
        count = p_count;
    }
}
