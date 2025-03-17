package com.CS320.app.CardResources;

public class CardCandidateClass implements CandidateFunction<Card> {

    @Override
    public boolean isCurrentNodeCandidate(int alternate, Card match, Card currCandidate) {
        if (CardUtils.isRemainderEqual(match.getName(), currCandidate.getName()) && match.getset_name().equals(currCandidate.getset_name())) {
            return true;
        }
        return false;
    }
}
