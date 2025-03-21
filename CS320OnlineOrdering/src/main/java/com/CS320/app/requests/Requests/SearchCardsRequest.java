package com.CS320.app.requests.Requests;

import com.CS320.app.CardResources.Card;
import com.CS320.app.misc.ServerResourcePackage;
import com.CS320.app.requests.Responses.Response;
import com.CS320.app.requests.Responses.SearchCardsRequestResponse;

public class SearchCardsRequest extends Request{
    private String name;
    @Override
    public Response buildResponse() {
        try {
            if (name.length() < 2) {
                return new SearchCardsRequestResponse(new Card[0], 0);
            }
            Card[] fetchedCards = pkg.getCardListAccessor().getCards(name);
            return new SearchCardsRequestResponse(fetchedCards, fetchedCards.length);
        }
        catch(Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    
}
