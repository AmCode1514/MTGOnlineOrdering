package com.CS320.app.CardResources;

public interface CandidateFunction <T> {
    public boolean isCurrentNodeCandidate(int alternate, T match, T currCandidate);
}
