package account;

import java.util.Objects;

public class Interval implements IntervalInterface
{
    private float start;
    private float end;

    /**
     * constructor
     * @param start - the start of the interval
     * @param end - the end of the interval
     */
    public Interval(float start, float end)
    {
        if(start<end)
        {
            this.start = start;
            this.end = end;
        }
        else
        {
            this.start = end;
            this.end = start;
        }
    }

    /**
     * getter
     * @return the start of the interval
     */
    @Override
    public float getStart()
    {
        return start;
    }

    /**
     * getter
     * @return the end of the interval
     */
    @Override
    public float getEnd()
    {
        return end;
    }

    /**
     * equals function
     * @param o - object to be compared to
     * @return true if their start and end match, false otherwise
     */
    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Interval interval = (Interval) o;
        return Float.compare(interval.start, start) == 0 &&
                Float.compare(interval.end, end) == 0;
    }

    /**
     * hashcode function
     * @return the hash of the class taking into consideration the start and the end of the interval
     */
    @Override
    public int hashCode()
    {
        return Objects.hash(start, end);
    }
}
