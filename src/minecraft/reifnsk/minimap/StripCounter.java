package reifnsk.minimap;

import java.awt.Point;

class StripCounter
{
    private int count;
    private Point points[];

    StripCounter(int i)
    {
        points = new Point[i];
        int j = 0;
        int k = 0;
        int l = 0;
        int i1 = 0;
        int j1 = 0;
        points[0] = new Point(j, k);

        for (int k1 = 1; k1 < i; k1++)
        {
            switch (l)
            {
                case 0:
                    k--;
                    break;

                case 1:
                    j++;
                    break;

                case 2:
                    k++;
                    break;

                case 3:
                    j--;
                    break;
            }

            if (++i1 > j1)
            {
                l = l + 1 & 3;
                i1 = 0;

                if (l == 0 || l == 2)
                {
                    j1++;
                }
            }

            points[k1] = new Point(j, k);
        }
    }

    Point next()
    {
        return points[count++];
    }

    int count()
    {
        return count;
    }

    void reset()
    {
        count = 0;
    }
}
