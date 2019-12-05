import java.util.ArrayList;
import java.util.Comparator;

class CompareSize implements Comparator<ArrayList<String>>
{
    boolean isAscend;

    public CompareSize(boolean b) {
        this.isAscend = b;
    }

    @Override
    public int compare(ArrayList<String> l, ArrayList<String> r) {
        int a;
        int b;

        if (this.isAscend) {
            a = l.size();
            b = r.size();
        }
        else {
            a = r.size();
            b = l.size();
        }

        return Integer.compare(a, b);
    }
}