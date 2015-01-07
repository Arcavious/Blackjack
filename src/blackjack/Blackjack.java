package blackjack;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;
import java.util.Scanner;

public class Blackjack {
    //yays
    private double playerBet;
    private Queue<Card> deck = new LinkedList<Card>();
    private LinkedList<Card> dealerHand = new LinkedList<Card>();
    private LinkedList<Card> playerHand = new LinkedList<Card>();
    private Scanner in = new Scanner(System.in);
    private int playerSum = 0;
    private int dealerSum = 0;
    private boolean playerBust;
    private boolean dealerBust;
//    private double playerCash = 10000;

    public static void main(String[] args) {
        //new Blackjack();
    }

    public Blackjack(double playerCash) {
        //generate deck
        deckGenerator();
        String playAgain = "yes";
        System.out.println("Player cash: $" + playerCash);
        //while (playAgain.equals("yes") && playerCash > 0) {
        while(playerCash > 0){
            playerBust = false;
            dealerBust = false;
            playerSum = 0;
            dealerSum = 0;
            playerHand.clear();
            dealerHand.clear();
            if (deck.size() < 30) {
                deckGenerator();
            }
            //establish bet
            System.out.println("Enter your bet:");
            playerBet = in.nextInt();
            playerCash -= playerBet;
            //deal
            deal();

            in.nextLine();

            //display cards
            display();

            //player sequence
            System.out.println("Hit or stay?");
            String playerDecision = in.nextLine();
            while (!playerDecision.equals("stay") && playerSum < 22) {
                hit();
                display();
                if (playerSum < 22) {
                    System.out.println("Hit or stay?");
                    playerDecision = in.nextLine();
                } else {
                    System.out.println("Player Bust");
                    playerBust = true;
                    System.out.println("Dealer wins!");
                }
            }

            if (!playerBust) {
                //dealer sequence
                while (dealerSum < 17) {
                    dealerHit();
                    if (dealerSum > 21) {
                        dealerBust = true;
                    }
                }
                displayAll();

                if (playerSum > dealerSum || dealerBust) {
                    System.out.println("Player Wins!");
                    playerCash += playerBet * 2;
                    if(playerHand.size() == 2 && playerSum == 21){
                        playerCash += (playerBet * .5);
                    }
                } else if (dealerSum > playerSum) {
                    System.out.println("Dealer Wins!");
                } else {
                    System.out.println("Tie!");
                    playerCash += playerBet;
                }
            }
            System.out.println("Player cash: $" + playerCash);
            /*if (playerCash > 0) {
                System.out.println("Would you like to play again?");
                playAgain = in.nextLine();
            }*/

        }
        System.out.println("Game Over!");
    }

    private void display() {
        System.out.print("Player Hand:");
        for (int i = 0; i < playerHand.size(); i++) {
            System.out.print(" " + playerHand.get(i).getName());
        }
        System.out.println(" ( " + playerSum + " )");
        System.out.print("Dealer Hand:");
        for (int i = 0; i < dealerHand.size(); i++) {
            if (!dealerHand.get(i).getHidden()) {
                System.out.print(" " + dealerHand.get(i).getName());
            }
        }
        System.out.println(" ( " + dealerHand.peek().getValue() + " )");
    }

    public void displayAll() {
        System.out.print("Player Hand:");
        for (int i = 0; i < playerHand.size(); i++) {
            System.out.print(" " + playerHand.get(i).getName());
        }
        System.out.println(" ( " + playerSum + " )");
        System.out.print("Dealer Hand:");
        for (int i = 0; i < dealerHand.size(); i++) {
            System.out.print(" " + dealerHand.get(i).getName());
        }
        System.out.println(" ( " + dealerSum + " )");
    }

    public void hit() {
        playerHand.add(deck.poll());
        playerSum += playerHand.peekLast().getValue();
        if (playerSum > 21) {
            for (int i = 0; i < playerHand.size(); i++) {
                if(playerHand.get(i).getAce()){
                    playerSum -= 10;
                    playerHand.get(i).setAce(false);
                    break;
                }
            }
        }
    }

    public void dealerHit() {
        dealerHand.add(deck.poll());
        dealerSum += dealerHand.peekLast().getValue();
        if (dealerSum > 21) {
            for (int i = 0; i < dealerHand.size(); i++) {
                if(dealerHand.get(i).getAce()){
                    dealerSum -= 10;
                    dealerHand.get(i).setAce(false);
                }
            }
        }
    }

    public void deal() {
        dealerHand.add(deck.poll());
        dealerSum += dealerHand.peekLast().getValue();
        playerHand.add(deck.poll());
        playerSum += playerHand.peekLast().getValue();
        deck.peek().setHidden(true);
        dealerHand.add(deck.poll());
        dealerSum += dealerHand.peekLast().getValue();
        playerHand.add(deck.poll());
        playerSum += playerHand.peekLast().getValue();
        if (playerSum > 21) {
            for (int i = 0; i < playerHand.size(); i++) {
                if(playerHand.get(i).getAce()){
                    playerSum -= 10;
                    playerHand.get(i).setAce(false);
                    break;
                }
            }
        }
        if (dealerSum > 21) {
            for (int i = 0; i < dealerHand.size(); i++) {
                if(dealerHand.get(i).getAce()){
                    dealerSum -= 10;
                    dealerHand.get(i).setAce(false);
                }
            }
        }
        
    }

    public void deckGenerator() {
        int count = 0;
        String[] suits = new String[4];
        suits[0] = "C";
        suits[1] = "S";
        suits[2] = "D";
        suits[3] = "H";
        Card[] cards = new Card[52];
        for (int i = 1; i < 14; i++) {
            for (int j = 0; j < 4; j++) {
                if (i == 1) {
                    cards[count] = new Card("A" + suits[j], 11);
                    cards[count].setAce(true);
                } else if (i == 11) {
                    cards[count] = new Card("J" + suits[j], 10);
                } else if (i == 12) {
                    cards[count] = new Card("Q" + suits[j], 10);
                } else if (i == 13) {
                    cards[count] = new Card("K" + suits[j], 10);
                } else {
                    cards[count] = new Card(i + suits[j], i);
                }
                count++;
            }
        }
        String[] deckUsed = new String[52];
        Random rand = new Random();
        int cardsLeft = 52;
        int num;
        while (cardsLeft > 0) {
            num = rand.nextInt(52);
            if (deckUsed[num] != "used") {
                deck.add(cards[num]);
                deckUsed[num] = "used";
                cardsLeft--;
            }
        }

    }

}
