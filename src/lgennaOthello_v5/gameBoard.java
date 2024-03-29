package lgennaOthello_v5;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class gameBoard {

	private static boolean choseColors, receivedValidMove, printedBoard, gameOver, myTurn;
	private static int opponentColor = 1, myColor = 1, moveCounter = 0;

	private static String[] letterArray = { "a", "b", "c", "d", "e", "f", "g", "h" };

	private static ArrayList<Integer> myPieceIds = new ArrayList<Integer>();
	private static ArrayList<Integer> opponentPieceIds = new ArrayList<Integer>();
	private static ArrayList<Integer> emptySpaces = new ArrayList<Integer>();
	private static ArrayList<Integer> blackPieceIds = new ArrayList<Integer>();
	private static ArrayList<Integer> whitePieceIds = new ArrayList<Integer>();
	private static ArrayList<MoveReturnCode> validMoves = new ArrayList<MoveReturnCode>();
//	private static ArrayList<Integer> numOfPiecesFlips = new ArrayList<Integer>();
//	private static ArrayList<Integer> finalCellsList = new ArrayList<Integer>();
	
	
	static String arguments;
	
	static int printMode = 0;
	/**
	 * 0 : Do not print board or moves (DEFAULT)
	 * 1 : Print board
	 * 2 : Print moves
	 * 3 : Print board & moves
	 */
	
	static int playMode = 0;
	/** 
	 * 0 : First move generated (DEFAULT)
	 * 1 : Random
	 * 2 : Simple maximum disk strategy
	 * 3 : Complex maximum disk strategy
	 * 4 : Weighted board
	 * 5 : Complex maximum disk strategy & weighted board
	 * 6 : Alpha/beta
	 */


//		A clean board			    a   b   c   d   e   f   g   h
	static int[] gameBoard =  { 2,  2,  2,  2,  2,  2,  2,  2,  2,  2, 
								2,  0,  0,  0,  0,  0,  0,  0,  0,  2,  // 1
		   						2,  0,  0,  0,  0,  0,  0,  0,  0,  2,  // 2
	   							2,  0,  0,  0,  0,  0,  0,  0,  0,  2,  // 3
	   							2,  0,  0,  0, -1,  1,  0,  0,  0,  2,  // 4
	   							2,  0,  0,  0,  1, -1,  0,  0,  0,  2,  // 5
	   							2,  0,  0,  0,  0,  0,  0,  0,  0,  2,  // 6
	   							2,  0,  0,  0,  0,  0,  0,  0,  0,  2,  // 7
			   					2,  0,  0,  0,  0,  0,  0,  0,  0,  2,  // 8
			   					2,  2,  2,  2,  2,  2,  2,  2,  2,  2 };

////		A testing board			    a   b   c   d   e   f   g   h
//	static int[] gameBoard =  { 2,  2,  2,  2,  2,  2,  2,  2,  2,  2, 
//								2,  0,  0,  0,  1, -1, -1, -1,  0,  2,  // 1
//								2,  0,  0, -1, -1, -1,  1,  0, -1,  2,  // 2
//								2,  0,  0,  0,  0,  0,  0,  0,  1,  2,  // 3
//								2,  0, -1, -1, -1, -1,  1,  0,  0,  2,  // 4
//								2,  0,  0,  0,  0,  0,  0,  0,  0,  2,  // 5
//								2,  0,  0,  0, -1, -1,  1,  0,  0,  2,  // 6
//								2,  0,  0,  0,  0,  0,  0,  0,  0,  2,  // 7
//								2,  0,  0,  0,  0, -1,  1,  0,  0,  2,  // 8
//								2,  2,  2,  2,  2,  2,  2,  2,  2,  2 };
	
////A game breaking board	    		a   b   c   d   e   f   g   h
//	static int[] gameBoard =  { 2,  2,  2,  2,  2,  2,  2,  2,  2,  2,
//								2, -1,  0,  0, -1,  0,  0,  0,  0,  2,  // 1
//								2,  0, -1,  0, -1,  0,  0,  0, -1,  2,  // 2
//								2,  0,  0,  1, -1,  0,  0,  0,  1,  2,  // 3
//								2,  0,  1, -1, -1, -1,  0,  0,  0,  2,  // 4
//								2,  0, -1,  1, -1,  1, -1,  0, -1,  2,  // 5
//								2,  0,  0,  1,  1,  1,  1,  0,  1,  2,  // 6
//								2,  0,  0,  1,  1,  1,  0,  1,  1,  2,  // 7
//								2,  0,  0,  0,  0,  0,  0,  0,  0,  2,  // 8
//								2,  2,  2,  2,  2,  2,  2,  2,  2,  2 };
	
////	A crashing board   ?	    a   b   c   d   e   f   g   h
//	static int[] gameBoard =   {2,  2,  2,  2,  2,  2,  2,  2,  2,  2,
//								2,  1,  1,  1,  1,  1,  1,  1, -1,  2,  // 1
//								2,  1,  1,  1,  1,  1, -1, -1, -1,  2,  // 2
//								2,  1,  1,  1, -1,  1, -1, -1, -1,  2,  // 3
//								2,  1,  1,  1, -1,  1, -1, -1, -1,  2,  // 4
//								2,  1,  1,  1, -1,  1,  1, -1, -1,  2,  // 5
//								2, -1,  1,  1,  1, -1, -1,  1, -1,  2,  // 6
//								2, -1, -1,  1,  1,  1,  1,  1,  1,  2,  // 7
//								2, -1, -1, -1,  0,  0,  0,  0,  0,  2,  // 8
//								2,  2,  2,  2,  2,  2,  2,  2,  2,  2};

	/**
	 * This method starts the game, it then keeps looping till 
	 *  the game ends, hopefully this wont stab me in the back
	 *  later on.
	 *  
	 *  It does initially take the arguments that are passed through the 
	 *   script file or terminal and use them to set what the game prints
	 *   in the console and what play style it is currently running.
	 */
	
	public static void startGame() {
		
		String printModeText = "";
		String playModeText = "";
		
		switch (printMode) {
			default :
				printModeText = "only required information";
				break;
			case 1 :
				printModeText = "only the board and required information";
				break;
			case 2 :
				printModeText = "only the valid moves & required information";
				break;
			case 3:
				printModeText = "everything";
				break;
		}
		
		switch (playMode) {
			default :
				playModeText = "first move generated";
				break;
			case 1 :
				playModeText = "random moves";
				break;
			case 2 :
				playModeText = "simple maximum disk strategy";
				break;
			case 3 :
				playModeText = "complex maximum disk strategy";
				break;
			case 4 :
				playModeText = "weighted board";
				break;	
			case 5 :
				playModeText = "complex maximum disk strategy & weighted board";
				break;
			case 6 :
				playModeText = "alpha/beta";
				break;	
		}
		
		System.out.println("C Currently printing " + printModeText + ".");
		
		System.out.println("C Current playstyle is " + playModeText + ".");
		
		while (!gameOver) {
			userInput();
		}

		System.out.println("C This is the end game *snap*.");

	}

	/**
	 * This method prints the board so the user can see it
	 * 
	 * This is largely useless when running against another program,
	 *  especially when it prints out all the valid moves it can make.
	 * 
	 * Though I am unsure how the logic of this works, it does what I want it to, so its 
	 * fine and I will never touch it other than removing the periodic comments...
	 * 
	 * The initial "if" statement is to check whether the user wishes to have the board
	 *  printed along side the game itself, if disabled, it can make a user playing the
	 *  program rather difficult.
	 */
	
	public static void printBoard() {
		
		if (printMode == 1 || printMode == 3) {
			if (choseColors) {
	
				int counter = 0;
				int columnNumbers = 1;
	
				System.out.println("C      a b c d e f g h \nC    *******************");
				for (int i = 10; i <= 88; i++) {
					if (counter == 0) {
						System.out.print("C  " + columnNumbers + " * ");
						columnNumbers++;
						counter++;
					} else if (counter == 8) {
						if (gameBoard[i] == 1) {
							System.out.print("B *\n");
//							System.out.print(" B *\n");
						} else if (gameBoard[i] == -1)
							System.out.print("W *\n");
//							System.out.print(" W *\n");
						else {
							System.out.print("- *\n");
//							System.out.print( i + " *\n");
						}
						counter = 0;
					} else if (gameBoard[i] != 2) {
						if (gameBoard[i] == 1) {
							System.out.print("B ");
//							System.out.print(" B ");
						} else if (gameBoard[i] == -1)
							System.out.print("W ");
//							System.out.print(" W ");
						else {
							System.out.print("- ");
//							System.out.print(i + " ");
						}
						counter++;
					}
				}
				System.out.println("C    *******************");
			}
		}
	}

	/**
	 * The reason why there are two methods that do very similar tasks is the result of reducing the usage
	 *  of an "if" statement. Due to the recursion nature of these statements it can help reduce overall
	 *  computational time if it checks who its calculating moves for before it starts rather than each time
	 *  it runs an  iteration.
	 * 
	 * Both of these methods are nearly identical however the if statements are inverted
	 * 
	 * These methods check to see where the empty spaces are and adds an offset to that space, then it uses
	 *  recursion to check if that newly added offset space is another opponent piece, it keeps doing the 
	 *  recursion till it either runs into a player piece or the edge of the board.
	 *  
	 * @param emptySpace The empty cell which is currently being checked
	 * @param offset This is the offset + the original offset so we can see if there are multiple
	 * 	pieces in a row
	 * @param origOffset This is the original offset, the reason this is passed is because the offset
	 *  is added to and the method needs to remember what the offset was before adding anything.
	 */
	


//	private static boolean validMoveFound = false;
	
//	private static MoveReturnCode checkEmptySpaces(int emptySpace, int offset, int origOffset) {
//		if (opponentPieceIds.contains(emptySpace + offset)) {
//			
//			ArrayList<Integer> piecesToFlip = new ArrayList<Integer>();
//			int newOffset = offset + origOffset;
//			int addedOffset = emptySpace + newOffset;
//			
//			if (!opponentPieceIds.contains(addedOffset) && myPieceIds.contains(addedOffset)) {
//				if (gameBoard[addedOffset] != 2) {
//					
//					while (newOffset > origOffset || newOffset < origOffset) {	
//						piecesToFlip.add(emptySpace + newOffset - origOffset);
//						newOffset -= origOffset;
//					}					
//					if (newOffset == origOffset) {
//						piecesToFlip.add(emptySpace + newOffset - origOffset);
//					}
//					
//					MoveReturnCode validMove = new MoveReturnCode(addedOffset, emptySpace, origOffset, piecesToFlip);
//
//					return validMove;
//				}			
//			}
//			checkEmptySpaces(emptySpace, newOffset, origOffset);
//		}
//		return null;
//	}

//	private static void origCheckEmptySpaces(int emptySpace, int offset, int origOffset) {		
//		if (opponentPieceIds.contains(emptySpace + offset)) {
//			offset = offset + origOffset;
//			int addedOffset = emptySpace + offset;
//			if (!opponentPieceIds.contains(addedOffset) && myPieceIds.contains(addedOffset)) {
//				if (gameBoard[addedOffset] != 2) {
//					
//					MoveReturnCode validMove = new MoveReturnCode(addedOffset, emptySpace, origOffset, null);
//					validMoves.add(validMove);
//				}
//			}
//			origCheckEmptySpaces(emptySpace, offset, origOffset);
//		}
//	}

	/**
	 * WORKING AS OF OCTOBER 23, 2019 12:08 PM
	 */
	
//	private static void checkEmptySpaces(int emptySpace, int offset, int origOffset) {
//		if (opponentPieceIds.contains(emptySpace + offset)) {
//			
//			ArrayList<Integer> piecesToFlip = new ArrayList<Integer>();
//			offset = offset + origOffset;
//			int addedOffset = emptySpace + offset;
//			
//			if (!opponentPieceIds.contains(addedOffset) && myPieceIds.contains(addedOffset)) {
//				if (gameBoard[addedOffset] != 2) {
//					
//					int piecesOffset = offset;
//					
//					if (piecesOffset > origOffset) {
//						while (piecesOffset > origOffset) {	
//							piecesToFlip.add(emptySpace + piecesOffset - origOffset);
//							piecesOffset -= origOffset;
//						}
//					} else if (piecesOffset < origOffset) {
//						while (piecesOffset < origOffset) {
//							piecesToFlip.add(emptySpace + piecesOffset - origOffset);
//							piecesOffset -= origOffset;
//						}
//					} 
//					if (piecesOffset == origOffset) {
//						piecesToFlip.add(emptySpace + piecesOffset - origOffset);
//					}
//					
//					MoveReturnCode validMove = new MoveReturnCode(addedOffset, emptySpace, origOffset, piecesToFlip);
//					validMoves.add(validMove);
//				}			
//			}
//			checkEmptySpaces(emptySpace, offset, origOffset);
//		}
//	}
	
	private static void checkEmptySpaces (int emptySpace, int offset, int origOffset) {
		if (opponentPieceIds.contains(emptySpace + offset)) {
			
			ArrayList<Integer> piecesToFlip = new ArrayList<Integer>();
			offset = offset + origOffset;
			int addedOffset = emptySpace + offset;
			
			if (!opponentPieceIds.contains(addedOffset) && myPieceIds.contains(addedOffset)) {
				addMoveReturnCodeToList(offset, addedOffset, emptySpace, origOffset, piecesToFlip);
			}
			checkEmptySpaces(emptySpace, offset, origOffset);
		}
	}
	
	private static void checkEmptySpacesForOpponent (int emptySpace, int offset, int origOffset) {
		if (myPieceIds.contains(emptySpace + offset)) {

			ArrayList<Integer> piecesToFlip = new ArrayList<Integer>();
			offset = offset + origOffset;
			int addedOffset = emptySpace + offset;
			
			if (opponentPieceIds.contains(addedOffset) && !myPieceIds.contains(addedOffset)) {
				addMoveReturnCodeToList(offset, addedOffset, emptySpace, origOffset, piecesToFlip);
			}
			checkEmptySpacesForOpponent(emptySpace, offset, origOffset);  
		}
	}
	
	/**
	 * This method purely exists to reduce the amount of duplicated code within the
	 *  checkEmptySpace's methods. However now looking at this, it takes up more space than if 
	 *  the code was part of each respective method.
	 * 
	 * @param offset This is an incremented value of the offset, it has the origOffset added to it with
	 * 	each iteration of the method
	 * @param addedOffset This is the value of a piece that can be flipped
	 * @param emptySpace This is the value of the final cell, being the empty space
	 * @param origOffset The value of the original offset, this remains untouched to preserve its value.
	 * @param piecesToFlip An ArrayList of each of the pieces the move will flip.
	 */
	
	private static void addMoveReturnCodeToList (int offset, int addedOffset, int emptySpace, int origOffset, ArrayList<Integer> piecesToFlip) {
		if (gameBoard[addedOffset] != 2) {
			
			int piecesOffset = offset;
			
			if (piecesOffset > origOffset) {
				while (piecesOffset > origOffset) {	
					piecesToFlip.add(emptySpace + piecesOffset - origOffset);
					piecesOffset -= origOffset;
				}
			} else if (piecesOffset < origOffset) {
				while (piecesOffset < origOffset) {
					piecesToFlip.add(emptySpace + piecesOffset - origOffset);
					piecesOffset -= origOffset;
				}
			} 
			if (piecesOffset == origOffset) {
				if (piecesToFlip.isEmpty()) {
					piecesToFlip.add(emptySpace + piecesOffset - origOffset);
				}
			}
			
			MoveReturnCode validMove = new MoveReturnCode(addedOffset, emptySpace, origOffset, piecesToFlip);
			validMoves.add(validMove);
		}	
	}
	
	/**
	 * Why is this method called validMoveMaster? no one knows
	 * 
	 * My guess might be because it runs the specific methods with the various
	 *  offsets applied, along with creating the list of current pieces/ empty
	 *  spaces.
	 */
	
	private static void validMoveMaster() {
		
		turnBoardIntoPieces();

		int[] offsets = {-11, -10, -9, -1, 1, 9, 10, 11};
		
		/**
		 * Checks to see who's turn it is, then for every empty space, do every offset check
		 *  iterating multiple times if need be.
		 */
		
		if (myTurn) {
//			System.out.println("C Using checkEmptySpaces");
			for (int i = 0; i < emptySpaces.size(); i++) {
				for (int j = 0; j < offsets.length; j++) {

					checkEmptySpaces(emptySpaces.get(i), offsets[j], offsets[j]);
//					origCheckEmptySpaces(emptySpaces.get(i), offsets[j], offsets[j]);
				}
			}
		} else {
//			System.out.println("C Using checkEmptySpacesForOpponent");
			for (int i = 0; i < emptySpaces.size(); i++) {
				for (int j = 0; j < offsets.length; j++) {
					checkEmptySpacesForOpponent(emptySpaces.get(i), offsets[j], offsets[j]);
				}
			}
		}
	}

	/**
	 * This method does what it says, it turns the board into pieces,
	 *  well not literally though. It takes each position on the gameBoard
	 *  and depending on who is playing as who and puts the piece into the
	 *  respective arrayList. If the piece doesn't meet the criteria, it means
	 *  its an empty space.
	 *  
	 * 
	 * And just to make sure it doesn't add border pieces as empty spaces it only
	 *  runs the actual size of the playing field being between 11 and 88
	 *  
	 * Also it clears the current lists of pieces, this is because it is mainly
	 *  called by validMoveMaster and we don't really care what the moves are for
	 *  old boards
	 *  
	 *  HOWEVER, it is / might be ENTIRELY USELESS to add to the black and white piece id 
	 *  lists if it's not end game
	 */
	
	private static void turnBoardIntoPieces() {
		
		validMoves.clear();
		myPieceIds.clear();
		opponentPieceIds.clear();
		emptySpaces.clear();
		blackPieceIds.clear();
		whitePieceIds.clear();
//		numOfPiecesFlips.clear();
//		finalCellsList.clear();
		
		for (int i = 11; i <= 88; i++) {
			if (myColor == 1 && gameBoard[i] == 1) {
				myPieceIds.add(i);
				blackPieceIds.add(i);
			} else if (myColor == -1 && gameBoard[i] == -1) {
				myPieceIds.add(i);
				whitePieceIds.add(i);
			} else if (opponentColor == 1 && gameBoard[i] == 1) {
				opponentPieceIds.add(i);
				blackPieceIds.add(i);
			} else if (opponentColor == -1 && gameBoard[i] == -1) {
				opponentPieceIds.add(i);
				whitePieceIds.add(i);
			} else if (gameBoard[i] == 0) {
				emptySpaces.add(i);
			}
		}
	}
	
	/**
	 * Prints all the valid moves that each player can make,
	 * 	also only prints out the valid moves for who's turn 
	 *  it currently is. Mainly implemented for testing purposes
	 *  but at this point its more useful than anything.
	 * 
	 * @param myColor this is so the method knows who's calling it
	 * and only print out that colors moves.
	 */
	
	public static void printValidMoves(int myColor) {
		
		if (printMode == 2 || printMode == 3) {
			for (int i = 0; i < validMoves.size(); i++) {
				
				String flipsList = "";
				MoveReturnCode temp = validMoves.get(i);
			
				int initialCell = temp.getInitialCell();
				int finalCell = temp.getFinalCell();

				ArrayList<Integer> flips = temp.getFlips();
				
				for (int j = 0; j < flips.size(); j++) {
					if (j + 1 != flips.size()) {
						flipsList += "(" + integerToRowColumn(flips.get(j)) + "), ";
					} else {
						flipsList += "(" + integerToRowColumn(flips.get(j)) + ")";
					}
				}
				
				/**
				 * ♫ Everywhere I look, I see duplicate code! ♫
				 * ♫ But don't worry... the code works. ♫ 
				 * 
				 * ♫ Is it efficient? ♫ 
				 * ♫ No sir'e ♫
				 * ♫ But does it work effectively? ♫
				 * ♫ ~ No. (but with pizazz) ♫
				 */
				
				if (myColor == 1) {
					System.out.println("C Black : ("
							+ integerToRowColumn(initialCell)
							+ ") to (" + integerToRowColumn(finalCell)
							+ ") : " + flips.size() + " Flipable disks : " + flipsList);
				} else {
					System.out.println("C White : (" 
						+ integerToRowColumn(initialCell)
						+ ") to (" + integerToRowColumn(finalCell)
						+ ") : " + flips.size() + " Flipable Disks : " + flipsList);
				}
			}
		}
	}
	
	/**
	 * Takes a row and column from the user in a letter and integer format, it then
	 * converts it to two integers so other parts of the code can read it
	 * 
	 * @return moveToMake This is a combination of a row and a column, each are
	 *         represented as integers
	 */

	private static int rowColumnToInteger(String rawRow, int rawColumn) {
		int cellValue = 0;

		for (int i = 0; i < 8; i++) {
			if (letterArray[i].contentEquals(rawRow)) {
				cellValue = Integer.parseInt(rawColumn + "" + (i + 1));
			}
		}

		return cellValue;
	}

	/**
	 * This method returns a human readable letter number combination
	 *  that is generated by the program like when it generates the
	 *  list of all possible moves.
	 * 
	 * @param cellId The cell id in an integer format
	 * @return Returns a human readable format of the cell id that
	 * 	was passed
	 */
	
	private static String integerToRowColumn(int cellId) {

		int cellIdTens = cellId / 10;
		int cellIdOnes = cellId % 10;

		return (letterArray[cellIdOnes - 1] + " " + cellIdTens);
	}

	/**
	 * This method takes the various information passed to it by choosing moves for
	 *  both the player and the opponent. It then takes this information and
	 *  inputs it into the overall gameBoard
	 * 
	 * @param initialCell The starting cell, usually an empty cell
	 * @param finalCell The last cell, usually the color that is making the jump
	 * @param origOffset The original offset, this is subtracted from the initial 
	 *  cell till it reaches the final cell
	 * @param whosColor What number to put into the gameBoard, either a 1 or a -1
	 *  depending on who's playing what color
	 */
	
	public static void updateBoard(int initialCell, int finalCell, int origOffset, int whosColor) {

		// put that thing where moveToMake says or so help me codd.
		
		if (finalCell > initialCell) {
			while (finalCell >= initialCell) {
				gameBoard[initialCell] = whosColor;
				initialCell -= origOffset;
			}
		} else {
			while (initialCell >= finalCell) {
				gameBoard[finalCell] = whosColor;
				finalCell += origOffset;
			}
		}
		
		// should it return that it made a successful update otherwise die?
	}
	
	/**
	 * What i was originally thinking that this method would do is whenever its called
	 *  it would determine if the game is over or not, it does this by checking to see if
	 *  each player has no moves
	 * 
	 * OR
	 * 
	 * I guess if the player inputs a number then do the check to see if there are no moves
	 *  left?
	 * 
	 * @return Returns a boolean basically saying if the game is over or not. I guess this
	 *  would be the master check to see if the game is over, if true break everything maybe.
	 */
	
	public static boolean determineEndGame() {
		boolean isItEndGame = false;
		// if true, panic.

		
		//this might be salvageable
		
//		whosTurn *= -1;
//		validMoveMaster();
//		if (validMoves.size() == 0) {
//			turnBoardIntoPieces();
//			System.out.println(blackPieceIds.size() - whitePieceIds.size());
//			gameOver = true;
//		} else {
			
			
			
		
		// do some checks
		
		
		return isItEndGame; // this better be false or so help me. If this is true, AHHHHHH!!
	}
	
	/**
	 * UHHHHHHH
	 * Through a bunch of if statements and for loops, we can determine what the user gave us
	 * 
	 * This mainly checks to see if what they input is actually valid and that
	 * 	it is in the current list of moves
	 * 
	 * There is a bug that if somehow the current color changes, you can make enemy moves which
	 *  in short, deletes enemy pieces.
	 * 
	 * @param enteredValue This is what was given to the program though the scanner
	 */
	
	public static void determineValidUserInput(String enteredValue) {

		String regexValue = "[BW]\\s[a-h]\\s[1-9]";

		if (enteredValue.matches(regexValue)) {

			String[] splitEnteredValue = enteredValue.split("\\s+");

			String moveLetter = splitEnteredValue[1];
			int moveNumber = Integer.parseInt(splitEnteredValue[2]);

			if (validMoves.size() > 0) {
				for (int i = 0; i < validMoves.size(); i++) {
					MoveReturnCode temp = validMoves.get(i);
	
					int initialCell = temp.getInitialCell();
					int finalCell = temp.getFinalCell();
					int origOffset = temp.getOrigOffset();
	
					int returnValue = rowColumnToInteger(moveLetter, moveNumber);

					if (finalCell == returnValue) {
						
						receivedValidMove = true;
						updateBoard(initialCell, finalCell, origOffset, opponentColor);
					}
				}
			}
			if (!receivedValidMove) {
				System.out.println("C Not a valid move!");
			}
		} else {
			
			if (enteredValue.equals("B")) {
				// They think they are smart, lets see if there are really no moves on the board
				validMoveMaster();
				if (validMoves.size() == 0) {
					// well they were smart, lets break this method
					receivedValidMove = true;
				}
			} else if (enteredValue.equals("W")) {
				// They think they are smart, lets see if there are really no moves on the board
				validMoveMaster();
				if (validMoves.size() == 0) {
					// well they were smart, lets break this method
					receivedValidMove = true;
				}
			} else {
				System.out.println("C Invalid input! (Example of input: W a 2)");
			}
		}
	}

	/**
	 * This method implements a crude version of the maximum disk strategy. currently
	 *  its split into three parts. The first part being to find the maximum number
	 *  of possible disks gained through each possible move. The second part then finds
	 *  which moves match that maximum number of disks gained. The reason these methods
	 *  are split is so then it doesn't add smaller values prior to finding the true
	 *  max. The third part picks a random value from the list that the second part
	 *  generated to remove the possibility of it always picking the first value.
	 *  
	 * Currently this method does not take into account if there are multiple options
	 *  for a single move. It only checks to see which move has the greatest number
	 *  of pieces to flip
	 *  
	 *  ONLY TAKES INTO ACCOUNT OF HOW MANY PIECES ARE GAINED FROM A SINGLE 
	 *  MOVE, MEAINING IF THERE ARE MULTIPLE OPTIONS FOR A SPACE, THIS 
	 *  WILL  N O T  TAKE THAT INTO ACCOUNT!!!!!
	 *  
	 * @return The value returned is the id of the cell from the validMoveList which
	 * 	has the maximum number of pieces that intend to be flipped
	 */
	
	private static int simpleMaxStrat() { // Simple Maximum Disk Strategy
		
		int maxPiecesFlips = 0;
		MoveReturnCode temp;
		ArrayList<Integer> flips;
		ArrayList<Integer> sameNumOfFlippablePieces = new ArrayList<Integer>();
		
		/**
		 * Get the MoveReturnCode, then take that and get the Flips from that return code. Then find the
		 *  maximum flips list size and use that as the maximum number of gained flips.
		 */
		for (int i = 0; i < validMoves.size(); i++) {
			
			temp = validMoves.get(i);
			flips = temp.getFlips();
			int piecesFlips = flips.size();
			
			if (piecesFlips >= maxPiecesFlips) {
				maxPiecesFlips = piecesFlips;
			}
		}
		
		/**
		 * Find out which lists have the same size as maxPiecesGained and add that to a list of moves that all 
		 *  have that same size length.
		 */
		for (int i = 0; i < validMoves.size(); i++) {
			
			temp = validMoves.get(i);
			flips = temp.getFlips();
			int piecesGained = flips.size();
			
			if (piecesGained == maxPiecesFlips) {
				sameNumOfFlippablePieces.add(i);
			}
		}
		
		//Then pick a random move from sameNumOfGainablePieces and add that to the board
		
		Random rand = new Random();
		int num = rand.nextInt(sameNumOfFlippablePieces.size());
			
		int aMove = sameNumOfFlippablePieces.get(num);
		
		return aMove;
	}
	
	/**
	 * Talk about complexity, because this method is full of it. It even has the same pieces of 
	 *  code 3 times. THREE TIMES.
	 * 
	 * This is the more complex cousin of the simple maximum disk strategy. This is because it takes
	 *  into account multiple moves that can move to the same cell rather than just the maximum number
	 *  of disks gained by one move.
	 * 
	 * @return This returns an integer which lines up with the value of which valid move the program
	 *  should make based on the complex maximum disk strategy
	 */
	
	private static int complexMaxStrat() { // Complex Maximum Disk Strategy
		
		/**
		 * ♫   E V E R Y W H E R E   I   L O O K,   I   S E E   D U P L I C A T E   C O D E   !   ♫
		 * ♫   B U T   D O N ' T   W O R R Y   ,   T H E   C O D E   W O R K S   .   ♫ 
		 * 
		 * 
		 * ♫   I S   I T   E F F I C I E N T   ?   ♫ 
		 * ♫   N O   S I R ' E   ♫
		 * ♫   B U T   D O E S   I T   W O R K   E F F E C T I V E L Y   ?   ♫
		 * ♫   ~   N O P E  .   ♫
		 */
		
		int maxPiecesFlipped = 0, firstFinalCell, secondFinalCell;
		MoveReturnCode firstCode, secondCode;
		ArrayList<Integer> firstFlips, secondFlips;
		ArrayList<Integer> sameNumOfFlippablePieces = new ArrayList<Integer>();
		ArrayList<Integer> duplicateFinder = new ArrayList<Integer>();
		
		/**
		 * This loop finds the maximum number of Pieces gained through complex moves
		 *  Complex moves are moves that have multiple possible moves to a single spot. This comes up with 
		 *  a single value of the maximum number of pieces flips.
		 */
		
		for (int i = 0; i < validMoves.size(); i++) {

			firstCode = validMoves.get(i);
			firstFlips = firstCode.getFlips();
			firstFinalCell = firstCode.getFinalCell();
			int firstPiecesFlipped = firstFlips.size();
			int piecesFlipped = firstPiecesFlipped;
			
			for (int j = 0; j < validMoves.size(); j++) {

				secondCode = validMoves.get(j);
				secondFlips = secondCode.getFlips();
				secondFinalCell = secondCode.getFinalCell();
				int secondPiecesFlipped = secondFlips.size();
				
				if (firstFinalCell == secondFinalCell) {
					if (j != i) { // we don't want the comparison cell to be added to itself
						piecesFlipped += secondPiecesFlipped;
					}
				}
			}
			
			if (piecesFlipped > maxPiecesFlipped) {	
				maxPiecesFlipped = piecesFlipped;
				
			}
		}
		
		/**
		 * This loop goes through all of the available complex moves with how many pieces they gain to try and find
		 *  values that are equal to the maximum number of Pieces gained. It then adds these moves to a list.
		 */
		
		for (int i = 0; i < validMoves.size(); i++) {

			firstCode = validMoves.get(i);
			firstFlips = firstCode.getFlips();
			firstFinalCell = firstCode.getFinalCell();
			int firstPiecesFlipped = firstFlips.size();
			int piecesFlipped = firstPiecesFlipped;
			
			for (int j = 0; j < validMoves.size(); j++) {
				secondCode = validMoves.get(j);
				secondFlips = secondCode.getFlips();
				secondFinalCell = secondCode.getFinalCell();
				int secondPiecesFlips = secondFlips.size();
				
				if (firstFinalCell == secondFinalCell) {
					if (j != i) { // we don't want the comparison cell to be added to itself
						piecesFlipped += secondPiecesFlips;
					}
				}
			}
			
			if (piecesFlipped == maxPiecesFlipped) {	
				if (!duplicateFinder.contains(firstFinalCell)) {  // Value was not a duplicate, add it to the list
					sameNumOfFlippablePieces.add(i);
					duplicateFinder.add(firstFinalCell);
				}
			}
		}
		
		/**
		 * A final for loop to add any simple max disk moves that might be lying around to the list of maximum
		 * moves possible
		 */
		
		for (int i = 0; i < validMoves.size(); i++) {
			firstCode = validMoves.get(i);
			firstFlips = firstCode.getFlips();
			int piecesFlipped = firstFlips.size();
			
			if (piecesFlipped == maxPiecesFlipped) {
				sameNumOfFlippablePieces.add(i);
			}
		}

		/**
		 * Lastly pick a random cell from sameNumOfGainablePieces and return that move
		 * 
		 * There should  A L W A Y S  be at least  O N E  value in the sameNumOfGainablePieces
		 *  list, otherwise this  W I L L  die, crash, and burn killing itself along with the other
		 *  methods and thousands of print statements. We don't want that now do we?
		 * 
		 *  ... unless there's no moves, then it dies rather easily.
		 *  
		 *  Including a fail safe for this statement will cover up any bugs and it would only rely on the first
		 *   move rather than picking one at random. So it is out of the best interest to not include one even 
		 *   though this is likely to be going against standard coding conventions. Thats the way it has to be.
		 *   
		 *   Like how Edna Mode never said, NO FAILSAFES!
		 */
		
		int aMove = -1;
		
		// if this statement can't come up with a random number, its taking the whole program with it
		if (sameNumOfFlippablePieces.size() != 0) { 
			Random rand = new Random();
			int num = rand.nextInt(sameNumOfFlippablePieces.size());
			aMove = sameNumOfFlippablePieces.get(num);
		} else {
			// if true, die.
			System.out.println("C Uhhhh... haha... that wasn't supposed to happen... thats it for me folks.");
		}
		
		return aMove;
	}
	
	
	/**
	 * Do I really have to explain this... I don't want to because its a mess...
	 * 
	 * This method does W A Y Y Y too much.
	 */
	
	public static void userInput() {
		Scanner scan = new Scanner(System.in);
		
		while (!choseColors) {
			System.out.println("C Enter StartColor: ");
			String enteredValue = scan.nextLine();
//			String enteredValue = "I B";
			if (enteredValue.contentEquals("I W")) {
				myColor *= -1;
				choseColors = true;
				System.out.println("C Playing as \"I W\"\nR W");
			} else if (enteredValue.contentEquals("I B")) {
				opponentColor *= -1;
				choseColors = true;
				myTurn = !myTurn;
				System.out.println("C Playing as \"I B\" \nR B");
			} else if (enteredValue.startsWith("C")) {
				// do nothing with the input because this is a comment
			} else {
				System.out.println("C Incorrect format! (Example of input: I W)");
			}
		}
		
		// Because I hate myself and do this check every time this method is re-run and I
		//  don't know where to put this print statement so it only does an initial print
		//  once players choose their colors
		
		if (!printedBoard) {
			
			printBoard();
			
			printedBoard = true;
		}

		if (choseColors && !gameOver) {
			if (myTurn) {
				
				System.out.println("C The program thinks confidently...");

				validMoveMaster();
				
				if (validMoves.size() > 0) {
					
					// for testing, print out all of the valid moves
					printValidMoves(myColor);
					
					String humanReadableFormat = "";
					
					MoveReturnCode temp = null; // if this continues being null, bad things will happen.
					
					// lets modernize the method in which we play
					switch (playMode) {
					
						/** 
						 * 0 : First move generated (DEFAULT) 					(Working)
						 * 1 : Random 											(Working)
						 * 2 : Simple maximum disk strategy						(Working)
						 * 3 : Complex maximum disk strategy					(Working)
						 * 4 : Weighted board									(Not Implemented)
						 * 5 : Complex maximum disk strategy & weighted board	(Not Implemented) (Time?)
						 * 6 : Alpha/beta										(Not Implemented)
						 */
					
						default :
							temp = validMoves.get(0);
							break;
						case 1 :
							Random rand = new Random();
							int randy = rand.nextInt(validMoves.size());
							temp = validMoves.get(randy);
							break;
						case 2 :
							temp = validMoves.get(simpleMaxStrat());
							break;
						case 3 :
							temp = validMoves.get(complexMaxStrat());
							break;
						case 4 :  
							System.out.println("This strategy is not currently implemented yet, using default.");
							playMode = 0;
							temp = validMoves.get(0);
							break;
						case 5 :  
							System.out.println("This strategy is not currently implemented yet, using default.");
							playMode = 0;
							temp = validMoves.get(0);
							break;
						case 6 :  
							System.out.println("This strategy is not currently implemented yet, using default.");
							playMode = 0;
							temp = validMoves.get(0);
							break;
					}
					
					// get the values from the MoveReturnCode type
					int initialCell = temp.getInitialCell();
					int finalCell = temp.getFinalCell();
					int origOffset = temp.getOrigOffset();
					
					// check to see if there are multiple moves for an empty space
					if (validMoves.size() >= 1) { // there's at least two or more valid moves
						for (int i = 0; i < validMoves.size(); i++) {
							MoveReturnCode temp2 = validMoves.get(i);
							
							// get new versions of the values, because I don't know a better way to implement this
							int initialCell2 = temp2.getInitialCell();
							int finalCell2 = temp2.getFinalCell();
							int origOffset2 = temp2.getOrigOffset();
							
							if (finalCell2 == finalCell) { //the two final cells originated at the same cell
								updateBoard(initialCell2, finalCell2, origOffset2, myColor);
							}
						}
					}
	
					// turn the integer location code to a "lower_case_letter space number" format
					humanReadableFormat = integerToRowColumn(finalCell);
					
					updateBoard(initialCell, finalCell, origOffset, myColor);
			
					// state where the program made a move
					if (myColor == 1) {
						System.out.println("B " + humanReadableFormat);
					} else {
						System.out.println("W " + humanReadableFormat);
					}
					
					printBoard();
					
				} else {
					
					// Turns out I have no moves, lets check the opponent...
					myTurn = !myTurn;
					validMoveMaster();
					if (validMoves.size() == 0) {
						// Wait they have no moves either? Activate the alarms
						//  because this is the end game!
						turnBoardIntoPieces();
						int numOfBlackPieces = (blackPieceIds.size() - whitePieceIds.size());
						System.out.println(numOfBlackPieces);
//						System.out.println(myPieceIds.size() - opponentPieceIds.size());
						myTurn = !myTurn;
						gameOver = true;
					} else {
						// Turns out they have more moves than us, but that will
						//  only be for this round, throw a pass, for now.
						if (myColor == 1) {
							System.out.println("B");
						} else {
							System.out.println("W");
						}
						myTurn = !myTurn;
					}
					
				}
				


				// state that the program logic is finished and change who's move it is
				System.out.println("C The program has made their move.");

				// state what move number it is and after that, increment the moveCounter
				System.out.println("C Move #" + moveCounter);
				moveCounter++;
				
				myTurn = !myTurn;
				
			}
			if (!myTurn && !gameOver) {
				System.out.println("C The opponent should think about their options...");
				
				validMoveMaster();

				printValidMoves(opponentColor);
				
				while (!receivedValidMove) {

					System.out.println("C Enter a Move:");
					String enteredValue = scan.nextLine();

					String blackPieces = "^(?:100|[1-9]?[0-9])$";

					if (!enteredValue.startsWith("C")) {
						if (!enteredValue.matches(blackPieces)) {
							determineValidUserInput(enteredValue);
						} else {
						
							/**
							 * This check is terribly written
							 * 
							 * I agree, but I also refuse to fix it.
							 */

							if (validMoves.size() > 0 && !myTurn) {
								System.out.println("C There are still moves you can make.");
								myTurn = !myTurn;
								validMoveMaster();
								myTurn = !myTurn;
								validMoveMaster();
							}
							else if (validMoves.size() > 0 && myTurn) {
								System.out.println("C There are still moves the program can make.");	
							} else {
								System.out.println(blackPieceIds.size() - whitePieceIds.size());
								gameOver = true;
								break;
							}
						}
					}

					if (receivedValidMove) {
						myTurn = !myTurn;
						receivedValidMove = false;
						System.out.println("C Recieved : " + enteredValue);
						printBoard();
						System.out.println("C The opponent finally finished making up their mind.");
						System.out.println("C Move #" + moveCounter);
						moveCounter++;
						break;
					}
				}
			}
		}
	}

	public static void main(String[] args) {
		
		/**
		 * Well this is where it all starts.
		 * 
		 * Regarding the comments, they may be extremely formal or literal screaming, there is no in-between.
		 * 
		 * Strangely the more hectic I get with the comments (screaming) the more efficient a portion of the 
		 *  code might get...
		 */
		
		if (args.length == 2) {
			printMode = Integer.parseInt(args[0]);
			playMode = Integer.parseInt(args[1]);
		}

		startGame();
	}

}
