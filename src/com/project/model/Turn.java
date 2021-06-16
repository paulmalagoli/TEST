package com.project.model;


import java.util.*;

public class Turn {
    static HashMap<String, List<Object>> currentPickedMap = new HashMap<String, List<Object>>() {{
        put("King", new ArrayList<Object>());
        put("Domino", new ArrayList<Object>());
        put("Index", new ArrayList<Object>());
    }};
    static HashMap<String, List<Object>> nextPickedMap = new HashMap<String, List<Object>>() {{
        put("King", new ArrayList<Object>());
        put("Domino", new ArrayList<Object>());
        put("Index", new ArrayList<Object>());
    }};

    public static void firstTurn(Player[] playerTab, King[] kingTab, List<Domino> dominoList, List<Board> boardList) {
        List<Domino> pickList = Pick.createPickList(dominoList);
        Pick.sortPickList(pickList);
        int count = 0;
        while (count < King.numberOfKing) {
            King currentKing = kingTab[count];
            System.out.println();
            System.out.println(currentKing.getKingPlayerName() + ", it's your turn");
            int playerIndex = 0;
            for (int i = 0; i < playerTab.length; i++) {
                if (playerTab[i].getPlayerName() == currentKing.getKingPlayerName()) {
                    playerIndex = i;
                }
            }
            Board board = boardList.get(playerIndex);
            Turn.selectionPhase(currentKing, pickList, board);
            count ++;
        }

    }

    public static void selectionPhase(King currentKing, List<Domino> pickList, Board board) {
        boolean check = false;
        do {
            System.out.println(currentKing.getKingPlayerName() + ", your board:");
            board.printBoard();
            for (Domino domino : pickList) {
                if (nextPickedMap.get("Domino").contains(domino)) {
                    System.out.print("XX ");
                } else {
                    System.out.print(domino.getType1() + domino.getNbCrown1() + "-" + domino.getType2() + domino.getNbCrown2() + " ");
                }
            }
            System.out.println();
            Scanner sc= new Scanner(System.in);
            try {
                System.out.print("Select a domino (enter 1, 2, 3, ...) : ");
                int numberInput;
                if (currentKing.getKingPlayerType() != "IA") {
                    numberInput = Integer.parseInt(sc.nextLine());
                } else {
                    numberInput = IA.iaSelectDomino(pickList);
                }
                if (numberInput >= 1 && numberInput <= pickList.size()) {
                    Domino pickedDomino = pickList.get(numberInput-1);
                    if (!nextPickedMap.get("Domino").contains(pickedDomino)) {
                        nextPickedMap.get("King").add(currentKing);
                        nextPickedMap.get("Domino").add(pickedDomino);
                        nextPickedMap.get("Index").add(pickList.indexOf(pickedDomino));
                        check = true;
                    } else {
                        throw new NumberFormatException();
                    }
                } else {
                    throw new NumberFormatException();
                }
            } catch (NumberFormatException e) {
                System.out.println();
                System.out.println("Please enter a valid number");
            }
        } while (!check);
    }

    public static void placementPhase(Player[] playerTab, King[] kingTab, List<Domino> pickList, List<Board> boardList, boolean chekPick) {
        int count = 0;
        while (count < King.numberOfKing) {
            int index = 0;
            int min = (int) currentPickedMap.get("Index").get(0);
            for (int i = 0; i < currentPickedMap.get("Index").size(); i++){
                if ((int) currentPickedMap.get("Index").get(i) < min) {
                    min = (int) currentPickedMap.get("Index").get(i);
                    index = i;
                }
            }
            King currentKing = (King) currentPickedMap.get("King").get(index);
            kingTab[min] = currentKing;
            Domino currentDomino = (Domino) currentPickedMap.get("Domino").get(index);
            currentPickedMap.get("King").remove(index);
            currentPickedMap.get("Domino").remove(index);
            currentPickedMap.get("Index").remove(index);

            Player currentPlayer = new Player();
            int playerIndex = 0;
            for (int i = 0; i < playerTab.length; i++) {
                if (playerTab[i].getPlayerName() == currentKing.getKingPlayerName()) {
                    currentPlayer = playerTab[i];
                    playerIndex = i;
                }
            }


            Board board = boardList.get(playerIndex);
            board.editAvailableRangeList();
            System.out.println();
            System.out.println(currentPlayer.getPlayerName() + ", it's your turn");
            System.out.println("Your board :");
            board.printBoard();
            Scanner sc= new Scanner(System.in);
            boolean check = false;
            int rotation = 0;
            int row1;
            int column1;
            int row2;
            int column2;
            String location;
            do {
                try {
                    System.out.println("Place your domino");
                    System.out.println("1: " + currentDomino.getType1() + ConsoleColors.RED_UNDERLINED + currentDomino.getNbCrown1() + ConsoleColors.RESET + "-" + currentDomino.getType2() + currentDomino.getNbCrown2());

                    System.out.println("2: ");
                    System.out.println(" " + currentDomino.getType1() + ConsoleColors.RED_UNDERLINED + currentDomino.getNbCrown1() + ConsoleColors.RESET);
                    System.out.println(" |");
                    System.out.println(" " + currentDomino.getType2() + currentDomino.getNbCrown2());

                    System.out.println("3: " + currentDomino.getType2() + currentDomino.getNbCrown2() + "-" + currentDomino.getType1() + ConsoleColors.RED_UNDERLINED + currentDomino.getNbCrown1() + ConsoleColors.RESET);

                    System.out.println("4:");
                    System.out.println(" " + currentDomino.getType2() + currentDomino.getNbCrown2());
                    System.out.println(" |");
                    System.out.println(" " + currentDomino.getType1() + ConsoleColors.RED_UNDERLINED + currentDomino.getNbCrown1() + ConsoleColors.RESET);

                    System.out.println("5: Throw the domino if you can't place it");

                    System.out.print("Select the rotation : ");
                    int numberInput;
                    int iaRow = 0;
                    int iaColumn = 0;
                    if (currentPlayer.getType() != "IA") {
                        numberInput = Integer.parseInt(sc.nextLine());
                    } else {
                        int[] result = IA.iaPlaceDomino (board, currentDomino);
                        numberInput = result[0];
                        iaRow = result[1];
                        iaColumn = result[2];
                    }
                    if (numberInput >= 1 && numberInput <= 4) {
                        rotation = numberInput;
                        board.printBoard();
                        System.out.println("Place your domino :");
                        switch(rotation) {
                            case 1:
                                System.out.println(currentDomino.getType1() + ConsoleColors.RED_UNDERLINED + currentDomino.getNbCrown1() + ConsoleColors.RESET + "-" + currentDomino.getType2() + currentDomino.getNbCrown2());
                                break;
                            case 2:
                                System.out.println(" " + currentDomino.getType1() + ConsoleColors.RED_UNDERLINED + currentDomino.getNbCrown1() + ConsoleColors.RESET);
                                System.out.println(" |");
                                System.out.println(" " + currentDomino.getType2() + currentDomino.getNbCrown2());
                                break;
                            case 3:
                                System.out.println(currentDomino.getType2() + currentDomino.getNbCrown2() + "-" + currentDomino.getType1() + ConsoleColors.RED_UNDERLINED + currentDomino.getNbCrown1() + ConsoleColors.RESET);
                                break;
                            case 4:
                                System.out.println(" " + currentDomino.getType2() + currentDomino.getNbCrown2());
                                System.out.println(" |");
                                System.out.println(" " + currentDomino.getType1() + ConsoleColors.RED_UNDERLINED + currentDomino.getNbCrown1() + ConsoleColors.RESET);
                                break;
                        }
                        System.out.println("Set the location of the first part : " + currentDomino.getType1() + ConsoleColors.RED_UNDERLINED + currentDomino.getNbCrown1() + ConsoleColors.RESET);
                        System.out.print("Enter the row : ");
                        int numberInput1;
                        if (currentPlayer.getType() != "IA") {
                            numberInput1 = Integer.parseInt(sc.nextLine());
                        } else {
                            numberInput1 = iaRow;
                            System.out.println(ConsoleColors.RED + numberInput1 + ConsoleColors.RESET);
                        }
                        if (numberInput1 >= 0 && numberInput1 <= 8) {
                            row1 = numberInput1;
                            System.out.print("Enter the column : ");
                            int numberInput2;
                            if (currentPlayer.getType() != "IA") {
                                numberInput2 = Integer.parseInt(sc.nextLine());
                            } else {
                                numberInput2 = iaColumn;
                                System.out.println(ConsoleColors.RED + numberInput2 + ConsoleColors.RESET);
                            }
                            if (numberInput2 >= 0 && numberInput2 <= 8) {
                                column1 = numberInput2;
                                location = String.valueOf(row1) + String.valueOf(column1);
                                if (board.checkRange(location)) {
                                    switch(rotation) {
                                        case 1:
                                            row2 = row1;
                                            column2 = column1 + 1;
                                            location = String.valueOf(row2) + String.valueOf(column2);
                                            if (board.checkRange(location)) {
                                                if (board.checkConnection(currentDomino, row1, column1, row2, column2)) {
                                                    board.insertDomino(currentDomino, row1, column1, row2, column2);
                                                    check = true;
                                                } else {
                                                    System.out.println();
                                                    System.out.println("Sorry, but there are no connections");
                                                }
                                            } else {
                                                System.out.println();
                                                System.out.println("Sorry, but this placement is not valid");
                                            }
                                            break;
                                        case 2:
                                            row2 = row1 + 1;
                                            column2 = column1;
                                            location = String.valueOf(row2) + String.valueOf(column2);
                                            if (board.checkRange(location)) {
                                                if (board.checkConnection(currentDomino, row1, column1, row2, column2)) {
                                                    board.insertDomino(currentDomino, row1, column1, row2, column2);
                                                    check = true;
                                                } else {
                                                    System.out.println();
                                                    System.out.println("Sorry, but there are no connections");
                                                }
                                            } else {
                                                System.out.println();
                                                System.out.println("Sorry, but this placement is not valid");
                                            }
                                            break;
                                        case 3:
                                            row2 = row1;
                                            column2 = column1 - 1;
                                            location = String.valueOf(row2) + String.valueOf(column2);
                                            if (board.checkRange(location)) {
                                                if (board.checkConnection(currentDomino, row1, column1, row2, column2)) {
                                                    board.insertDomino(currentDomino, row1, column1, row2, column2);
                                                    check = true;
                                                } else {
                                                    System.out.println();
                                                    System.out.println("Sorry, but this placement is not valid");
                                                }
                                            } else {
                                                System.out.println();
                                                System.out.println("Sorry, but this placement is not valid");
                                            }
                                            break;
                                        case 4:
                                            row2 = row1 - 1;
                                            column2 = column1;
                                            location = String.valueOf(row2) + String.valueOf(column2);
                                            if (board.checkRange(location)) {
                                                if (board.checkConnection(currentDomino, row1, column1, row2, column2)) {
                                                    board.insertDomino(currentDomino, row1, column1, row2, column2);
                                                    check = true;
                                                } else {
                                                    System.out.println();
                                                    System.out.println("Sorry, but there are no connections");
                                                }
                                            } else {
                                                System.out.println();
                                                System.out.println("Sorry, but this placement is not valid");
                                            }
                                            break;
                                    }
                                } else {
                                    System.out.println();
                                    System.out.println("Sorry, but this placement is not valid");
                                }
                            } else {
                                throw new NumberFormatException();
                            }
                        } else {
                            throw new NumberFormatException();
                        }
                    } else if (numberInput == 5){
                        check = true;
                    } else {
                    throw new NumberFormatException();
                    }
                } catch (NumberFormatException e) {
                    System.out.println();
                    System.out.println("Please select a valid number");
                }
            } while (!check);
            System.out.println();
            if (chekPick) {
                selectionPhase(currentKing, pickList, board);
            }
            count++;
        }
    }

    public static void turnLooper(Player[] playerTab, King[] kingTab, List<Domino> dominoList, List<Board> boardList) {
        boolean checkPick = true;
        while (checkPick) {
            List<Domino> pickList = Pick.createPickList(dominoList);
            if (pickList != null) {
                Pick.sortPickList(pickList);
            } else {
                checkPick = false;
            }
            currentPickedMap.get("King").addAll(nextPickedMap.get("King"));
            nextPickedMap.get("King").clear();

            currentPickedMap.get("Domino").addAll(nextPickedMap.get("Domino"));
            nextPickedMap.get("Domino").clear();

            currentPickedMap.get("Index").addAll(nextPickedMap.get("Index"));
            nextPickedMap.get("Index").clear();
            Turn.placementPhase(playerTab, kingTab, pickList, boardList, checkPick);
        }
    }
}
