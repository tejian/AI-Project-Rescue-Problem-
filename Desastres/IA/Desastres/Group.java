package IA.Desastres;

/**
 * Group represents a group taking part in a helicopter trip.
 * Mainly used during swap and other operations for efficiency.
 */
public class Group
{
    public int m_heli;
    public int m_trip;
    public Group() {}

    public Group clone()
    {
        Group g = new Group();
        g.m_heli = m_heli;
        g.m_trip = m_trip;
        return g;
    }
}
