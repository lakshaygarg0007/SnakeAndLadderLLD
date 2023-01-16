package com.mycompany.snakesandladderslld;

import java.util.*;
import java.util.stream.Stream;

/**
 *
 * @author garg
 */
public class Game {
    Queue<Player> nextTurn;
    Map<Player, Integer> playerAndCurrentPosition;
    Dice dice;
    List<Snake> snakePositions;
    List<Ladder> ladderPositions;
    int noOfPlayers;
    
    Game(Player... players) {
        playerAndCurrentPosition = new HashMap<>();
        nextTurn = new LinkedList<>();
        snakePositions = new ArrayList<>();
        ladderPositions = new ArrayList<>();
        Stream.of(players).forEach(player -> playerAndCurrentPosition.put(player, 0));
        nextTurn.addAll(Arrays.asList(players));
        dice = new Dice();
        noOfPlayers = players.length;
    }
    
    int checkIfLadder(Integer newPosition) {
        for(Ladder ladder : ladderPositions) {
            if(ladder.startPoint.equals(newPosition)) {
                return ladder.endPoint;
            }
        }
        
        return 0;
    }
    
    int checkIfSnake(Integer newPosition) {
        for(Snake snake : snakePositions) {
            if(snake.endPoint.equals(newPosition)) {
                return snake.startPoint;
            }
        }
        
        return 0;
    }

    void generateLadders() {
        int times = (int)(Math.random() * 3 + 1);
        while(times  > 0) {
            int max = 99;
            int start = (int) (Math.random() * (max));
            int end = (int) (Math.random() * start + start);
            Ladder ladder = new Ladder(start, end);
            ladderPositions.add(ladder);
            times --;
        }
    }

    void generateSnakes() {
        int times = (int)(Math.random() * 3 + 1);
        while(times  > 0) {
            int max = 99;
            int end = (int) (Math.random() * max );
            int start = (int)(Math.random() * (end - 1));
            Snake snake = new Snake(start, end);
            snakePositions.add(snake);
            times --;
        }
    }
   
    
    void startGame() {
        while(nextTurn.size() > noOfPlayers - 1) {
            Player player = nextTurn.peek();
            nextTurn.poll();
            String playerName = player.playerName;
            int diceValue = dice.rollDice();
            Integer currentPosition = playerAndCurrentPosition.get(player);
            int newPosition = currentPosition + diceValue;
            if (newPosition == (100)) {
                System.out.println(playerName + " Has Won The Game");
            } else if (newPosition > 100) {
                System.out.println(playerName + " Could Not Move Forward as Value is greater than 100");
                nextTurn.add(player);
            } else {
                int ladderValue = checkIfLadder(newPosition);
                int snakeValue = checkIfSnake(newPosition);
                if (ladderValue != 0) {
                    System.out.println(playerName + " Got A Ladder");
                    newPosition = ladderValue;
                } else if (snakeValue != 0) {
                    System.out.println(playerName + " Player Got Bitten By Snake");
                    newPosition = snakeValue;
                }
                System.out.println(playerName + " Is at Position " + newPosition);
                if (newPosition == 100) {
                    System.out.println(playerName + " Has Won The Game");
                }

                playerAndCurrentPosition.put(player, newPosition);
                nextTurn.add(player);
            }
        }
    }

    public static void main(String[] args) {
        Player player1 = new Player("1", "Jon Snow");
        Player player2 = new Player("2", "Tyrion Lannister");
        Player player3 = new Player("3", "Arya Stark");
        Game game = new Game(player1, player2, player3);
        game.generateLadders();
        game.generateSnakes();
        game.startGame();
    }
}
