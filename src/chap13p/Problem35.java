package chap13p;

import utilities.StdOut;

/**
 * client for 1.3.35 RandomQueue
 */
public class Problem35 {

    public static void main(String[] args) {
        RandomQueue<Card> cardRandomQueue = new RandomQueue<>();
        fillQueueWithBridgeHandsCards(cardRandomQueue);

        for (int i = 0; i < 2; i++) {
            int count = 0;
            StdOut.println("Hand " + (i + 1));
            while (count < 13) {
                StdOut.println(cardRandomQueue.dequeue());
                count++;
            }
            StdOut.println();
        }

        Card sample = cardRandomQueue.sample();
        StdOut.println("Size before sample: " + cardRandomQueue.size() + " Expected: 26");
        StdOut.println("Random item: " + sample);
        StdOut.println("Size after sample: " + cardRandomQueue.size() + " Expected: 26");

        StdOut.println("Cards:\n");

        // Problem 1.3.36 Random Iteration test
        for (int i = 2; i < 4; i++) {
            int count = 0;
            StdOut.println("Hand " + (i + 1));
            for (Card card : cardRandomQueue) {
                if (count == 13) break;
                StdOut.println(card);
                count++;
            }
            StdOut.println();
        }
    }

    private static class Card {
        String value;
        String suit;

        Card(String value, String suit) {
            this.value = value;
            this.suit = suit;
        }

        @Override
        public String toString() {
            return value + "-" + suit;
        }
    }

    private static void fillQueueWithBridgeHandsCards(RandomQueue<Card> randomQueue) {
        String[] suits = {"Spades", "Hearts", "Diamonds", "Clubs"};

        for (int i = 0; i < suits.length; i++) {
            randomQueue.enqueue(new Card("A", suits[i]));
            randomQueue.enqueue(new Card("2", suits[i]));
            randomQueue.enqueue(new Card("3", suits[i]));
            randomQueue.enqueue(new Card("4", suits[i]));
            randomQueue.enqueue(new Card("5", suits[i]));
            randomQueue.enqueue(new Card("6", suits[i]));
            randomQueue.enqueue(new Card("7", suits[i]));
            randomQueue.enqueue(new Card("8", suits[i]));
            randomQueue.enqueue(new Card("9", suits[i]));
            randomQueue.enqueue(new Card("10", suits[i]));
            randomQueue.enqueue(new Card("J", suits[i]));
            randomQueue.enqueue(new Card("Q", suits[i]));
            randomQueue.enqueue(new Card("K", suits[i]));
        }
    }

}
