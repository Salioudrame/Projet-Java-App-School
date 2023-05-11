package application.model;

import java.util.Comparator;

public class ApprenantComparator implements Comparator<Apprenant> {
    @Override
    public int compare(Apprenant a1, Apprenant a2) {
        if (a1.getMoyenneannuelle() < a2.getMoyenneannuelle()) {
            return 1;
        } else if (a1.getMoyenneannuelle() > a2.getMoyenneannuelle()) {
            return -1;
        } else {
            return 0;
        }
    }
}

