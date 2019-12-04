import java.util.ArrayList;
import java.util.Comparator;

final class CompareId implements Comparator<ArrayList<String>>
{
    boolean is_Ascend;

    public CompareId(final boolean b) {
        this.is_Ascend = b;
    }

    @Override
    public int compare(final ArrayList<String> o1, final ArrayList<String> o2) {
        int a;
        int b;
        if (this.is_Ascend) {
            a = o1.size();
            b = o2.size();
        }
        else {
            a = o2.size();
            b = o1.size();
        }
        if (a > b) {
            return 1;
        }
        if (a == b) {
            return 0;
        }
        return -1;
    }
}