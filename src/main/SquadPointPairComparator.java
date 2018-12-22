package main;

import java.util.Comparator;

public class SquadPointPairComparator {


    public static final Comparator<SquadPointPair> ASCENDING = new Comparator<SquadPointPair>() {
        public int compare(SquadPointPair l1, SquadPointPair l2) {
            return l1.getPoints() - l2.getPoints();
            }
        };

}
