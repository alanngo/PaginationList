
import paginate.PaginatedList;

import java.util.*;
import static java.lang.System.*;
public class Sandbox
{
    public static void main(String[] args) {
        List<String> list = Arrays.asList("z", "y", "x", "a", "b", "c", "d", "e", "f");
        PaginatedList<String> pages = new PaginatedList<>(list, 4, String::compareToIgnoreCase);
        out.println(pages.getList());
        out.println(pages.first());
        out.println(pages.last());

        out.println(pages.flatten());
    }
}
