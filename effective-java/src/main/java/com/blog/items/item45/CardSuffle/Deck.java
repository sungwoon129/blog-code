package com.blog.items.item45.CardSuffle;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Deck {

    private static List<Card> newDeckByLoopForeach() {
        List<Card> result = new ArrayList<>();
        for(Suit suit : Suit.values())
            for(Rank rank : Rank.values())
                result.add(new Card(suit, rank));

        return result;
    }

    private static List<Card> newDeckByStream() {
        return Stream.of(Suit.values())
                .flatMap(suit ->
                        Stream.of(Rank.values())
                                .map(rank -> new Card(suit, rank)))
                .collect(Collectors.toList());
    }
}
