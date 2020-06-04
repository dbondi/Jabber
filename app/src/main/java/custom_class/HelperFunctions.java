package custom_class;

import android.location.Location;

import java.util.ArrayList;
import java.util.Random;

public class HelperFunctions
{
    static int INF = 10000;

    // Given three colinear points p, q, r,
    // the function checks if point q lies
    // on line segment 'pr'
    public static boolean onSegment(PointMap p, PointMap q, PointMap r)
    {
        return q.x <= Math.max(p.x, r.x) &&
                q.x >= Math.min(p.x, r.x) &&
                q.y <= Math.max(p.y, r.y) &&
                q.y >= Math.min(p.y, r.y);
    }

    // To find orientation of ordered triplet (p, q, r).
    // The function returns following values
    // 0 --> p, q and r are colinear
    // 1 --> Clockwise
    // 2 --> Counterclockwise
    public static int orientation(PointMap p, PointMap q, PointMap r)
    {
        double val = (q.y - p.y) * (r.x - q.x)
                - (q.x - p.x) * (r.y - q.y);

        if (val == 0)
        {
            return 0; // colinear
        }
        return (val > 0) ? 1 : 2; // clock or counterclock wise
    }

    // The function that returns true if
    // line segment 'p1q1' and 'p2q2' intersect.
    public static boolean doIntersect(PointMap p1, PointMap q1,
                                      PointMap p2, PointMap q2)
    {
        // Find the four orientations needed for
        // general and special cases
        int o1 = orientation(p1, q1, p2);
        int o2 = orientation(p1, q1, q2);
        int o3 = orientation(p2, q2, p1);
        int o4 = orientation(p2, q2, q1);

        // General case
        if (o1 != o2 && o3 != o4)
        {
            return true;
        }

        // Special Cases
        // p1, q1 and p2 are colinear and
        // p2 lies on segment p1q1
        if (o1 == 0 && onSegment(p1, p2, q1))
        {
            return true;
        }

        // p1, q1 and p2 are colinear and
        // q2 lies on segment p1q1
        if (o2 == 0 && onSegment(p1, q2, q1))
        {
            return true;
        }

        // p2, q2 and p1 are colinear and
        // p1 lies on segment p2q2
        if (o3 == 0 && onSegment(p2, p1, q2))
        {
            return true;
        }

        // p2, q2 and q1 are colinear and
        // q1 lies on segment p2q2
        return o4 == 0 && onSegment(p2, q1, q2);

        // Doesn't fall in any of the above cases
    }

    // Returns true if the point p lies
    // inside the polygon[] with n vertices
    public static boolean isInside(ArrayList<PointMap> polygon, PointMap p)
    {
        // There must be at least 3 vertices in polygon[]
        int n = polygon.size();
        if (n < 3)
        {
            return false;
        }

        // Create a point for line segment from p to infinite
        PointMap extreme = new PointMap(INF, p.y);

        // Count intersections of the above line
        // with sides of polygon
        int count = 0, i = 0;
        do
        {
            int next = (i + 1) % n;

            // Check if the line segment from 'p' to
            // 'extreme' intersects with the line
            // segment from 'polygon[i]' to 'polygon[next]'
            if (doIntersect(polygon.get(i), polygon.get(next), p, extreme))
            {
                // If the point 'p' is colinear with line
                // segment 'i-next', then check if it lies
                // on segment. If it lies, return true, otherwise false
                if (orientation(polygon.get(i), p, polygon.get(next)) == 0)
                {
                    return onSegment(polygon.get(i), p,
                            polygon.get(next));
                }

                count++;
            }
            i = next;
        } while (i != 0);

        // Return true if count is odd, false otherwise
        return (count % 2 == 1); // Same as (count%2 == 1)
    }

    // Returns true if the point p lies
    // inside the polygon[] with n vertices
    public static boolean isInside(ArrayList<PointMap> polygon, Location pLoc)
    {
        PointMap p = new PointMap(pLoc.getLatitude(),pLoc.getLongitude());
        // There must be at least 3 vertices in polygon[]
        int n = polygon.size();
        if (n < 3)
        {
            return false;
        }

        // Create a point for line segment from p to infinite
        PointMap extreme = new PointMap(INF, p.y);

        // Count intersections of the above line
        // with sides of polygon
        int count = 0, i = 0;
        do
        {
            int next = (i + 1) % n;

            // Check if the line segment from 'p' to
            // 'extreme' intersects with the line
            // segment from 'polygon[i]' to 'polygon[next]'
            if (doIntersect(polygon.get(i), polygon.get(i), p, extreme))
            {
                // If the point 'p' is colinear with line
                // segment 'i-next', then check if it lies
                // on segment. If it lies, return true, otherwise false
                if (orientation(polygon.get(i), p, polygon.get(i)) == 0)
                {
                    return onSegment(polygon.get(i), p,
                            polygon.get(i));
                }

                count++;
            }
            i = next;
        } while (i != 0);

        // Return true if count is odd, false otherwise
        return (count % 2 == 1); // Same as (count%2 == 1)
    }
    public static String[] random_color(int max){
        Random r = new Random();
        int random_int = r.nextInt(max);
        String[] arr1 = new String[]{"#a60a02","#377801","#027c8a","#5902a1","#a60293","#d17e02","#01470d","#0247d1","#ff8e03","#1fad66","#010161","#b80266"};
        String[] arr2 = new String[]{"#fa7d35","#c1d604","#47ff6c","#ff57ee","#bf40ff","#fa9455","#6ed433","#b67fff","#ffc003","#7dff5c","#03e6ff","#ff75ff"};
        String var1 = arr1[random_int];
        String var2 = arr2[random_int];
        return new String[]{var1,var2};
    }


    public static double distanceAway(double lat1, double lat2, double lon1,
                                  double lon2, double el1, double el2) {

        final int R = 6371; // Radius of the earth

        double latDistance = Math.toRadians(lat2 - lat1);
        double lonDistance = Math.toRadians(lon2 - lon1);
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double distance = R * c * 1000; // convert to meters

        double height = el1 - el2;

        distance = Math.pow(distance, 2) + Math.pow(height, 2);

        return Math.sqrt(distance)*(0.000621371);
    }






}
