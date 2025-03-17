package com.CS320.app.CardResources;

public class CardUtils {
    private CardUtils() {

    }
    public static boolean isRemainderEqual(String match, String card) {
        int minLength = Math.min(match.length(), card.length());
        for (int i = 2; i < minLength; ++i) {
            if (match.charAt(i) != card.charAt(i)) {
                return false;
            }
        }
        return true;
      }
  }
