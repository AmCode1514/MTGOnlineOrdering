package com.CS320.app.requests.Requests;

import java.util.ArrayList;
import java.util.List;

import com.CS320.app.CardResources.Card;
import com.CS320.app.CardResources.CardValidationException;
import com.CS320.app.misc.Order;
import com.CS320.app.misc.ServerResourcePackage;
import com.CS320.app.requests.Responses.CreateOrderResponse;
import com.CS320.app.requests.Responses.Response;
import com.CS320.app.server.SessionManager;

import io.javalin.http.Context;

public class CreateOrderRequest extends AuthenticationRequest {
    private List<String> tcgIDs;
    //status can likely be replaced with boolean or int flag
    private boolean[] foilFlags;

    private transient String status;

    @Override
    public Response buildResponse(Context ctx) throws Exception {
        if (token == null) {
            status = "RedirectLogIn";
            return new CreateOrderResponse(status, isAuthenticated, 0, token, requestType);
        }
        checkAuthenticationStatus();
            if(isAuthenticated) {
                //note, on the front end the foil flags and subsequent card list should be symmetrical. If they are not, it likely indicates that a user is manually creating a json. 
                //this is because the tcgids are sent from the server itself, and should always map back to the correct card.
                //if they don't, they likely have been tampered with, I will resolve this issue by discarding requests with asymmetry.
                List<Card> fetchedCardsFromIDs = new ArrayList<>();
                for (String tcgId : tcgIDs) {
                    try {
                        Card fetchedCard = pkg.getCardListAccessor().getValidatedCard(tcgId);
                        if (fetchedCard != null) {
                            fetchedCardsFromIDs.add(fetchedCard);
                        }
                    }
                    catch(CardValidationException e) {
                        throw e;
                    }
                }
                //symmetry check
                //just realized if a tcg id fails to find it also trips this branch
                if (foilFlags.length != fetchedCardsFromIDs.size()) {
                    return new CreateOrderResponse("ValidationFailed", isAuthenticated, 0, token, requestType);
                }
                pkg.getOrdersList().add(new Order(fetchedCardsFromIDs, fetchedCardsFromIDs.size(), associatedSession.getEmail(), foilFlags));
                return new CreateOrderResponse(status, isAuthenticated, 0, token, requestType);
            }
            else {
                status = "TokenInvalid";
                return new CreateOrderResponse(status, isAuthenticated, 0, token, requestType);
            }
        }

    

}
