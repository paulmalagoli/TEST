package com.project.model;

import java.util.Comparator;

public class RankComparator implements Comparator<Score> {
    @Override
    public int compare(Score a, Score b) {
        int playerScoreSort = b.getPlayerScore() - a.getPlayerScore();
        int scopeScoreSort = b.getScopeScore() - a.getScopeScore();
        int crownScoreSort = b.getCrownScore() - a.getCrownScore();

        if (playerScoreSort != 0) {
            return playerScoreSort;
        } else {
            if (scopeScoreSort != 0) {
                return scopeScoreSort;
            } else {
                if (crownScoreSort == 0) {
                    a.setEquality(true);
                }
                return crownScoreSort;
            }
        }
    }
}
