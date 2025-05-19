package com.CS320.app.requests.Requests;

import com.CS320.app.CardResources.Card;
import com.CS320.app.misc.ServerResourcePackage;
import com.CS320.app.requests.Responses.Response;
import com.CS320.app.requests.Responses.SearchCardsRequestResponse;

import io.javalin.http.Context;

public class SearchCardsRequest extends Request{
    private String name;
    private String set;
    private boolean isFoil;
    private boolean isFullArt;
    private boolean isPromo;
    private int depth;
    private boolean desiredSearch;
    @Override
    public Response buildResponse(Context ctx) {
        try {
            if (name.length() < 2) {
                return new SearchCardsRequestResponse(new Card[0], 0, requestType);
            }
            int representation = 0;
            if (isFoil) {
                representation = representation | (1 << 0);
            }
            if(isFullArt) {
                representation = representation | (1 << 1);
            }
            if (isPromo) {
                representation = representation | (1 << 2);
            }
            Card[] fetchedCards = pkg.getCardListAccessor().getCards(name, set, (byte) representation, depth);
            return new SearchCardsRequestResponse(fetchedCards, fetchedCards.length, requestType);
        }
        catch(Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    
}
