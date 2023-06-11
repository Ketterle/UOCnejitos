package edu.uoc.uocnejitos.model;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


/** 
 * Level class.
 * @author David García Solórzano
 * @version 1.0  
 */
public class Level {
	/**
	 * Size of the board. SIZExSIZE.
	 */
	private final int SIZE;
	/**
	 * Level's difficulty
	 */
	private final LevelDifficulty DIFFICULTY;
	/**
	 * Number minimum of moves that are required in order to beat the level/challenge.
	 */
	private final int MIN_MOVES;
	/**
	 * List of pieces that are on the board.	
	 */
	private final List<Piece> board;
	
	/**
	 * Constructor
	 * @param fileName Name of the configuration file which has all the information of the level.
	 * @throws FileNotFoundException When it is impossible to open the configuration file.
	 * @throws IllegalArgumentException When an error of this type happens, e.g. NumberFormatException.
	 * @throws LevelException When there is an error with the information of configuration file, e.g. incorrect size or min moves, no bunnies, no holes, #bunnies&lt;#holes
	 */
	public Level(String fileName) throws FileNotFoundException, IllegalArgumentException, LevelException {
		String line;
		long numBunnies, numHoles;
		int row, column;
		char pieceSymbol;
		Piece piece = null;
		
		
		try(Scanner sc = new Scanner(new File(fileName))){
			SIZE = Integer.parseInt(sc.nextLine());
			
			if(getSize()<3)
				throw new LevelException(LevelException.ERROR_SIZE);
			
			DIFFICULTY = LevelDifficulty.valueOf(sc.nextLine().toUpperCase());
			
			MIN_MOVES = Integer.parseInt(sc.nextLine());
			
			if(getMinMoves()<1)
				throw new LevelException(LevelException.ERROR_MIN_MOVES);
			
			board = new ArrayList<>(SIZE * SIZE);
			
			//We populate the whole list with Grass pieces.			
			for(int i = 0; i < getSize(); i++) {
				for(int j = 0; j < getSize(); j++) {
					board.add(new Grass(new Coordinate(i, j)));
				}
			}			
			
			
			while(sc.hasNext()) {
				line = sc.nextLine();
				pieceSymbol= line.charAt(0);
				
				if(pieceSymbol != 'b' && pieceSymbol != 'B'
						&&
						pieceSymbol != 'w' && pieceSymbol != 'W'
						&&
						pieceSymbol != 'g') {
					pieceSymbol = Character.toUpperCase(pieceSymbol); 
				}
					
				row = calculateRow(line.toLowerCase().charAt(1)); 
				column = calculateColumn(line.toLowerCase().charAt(2));

				switch (Symbol.getName(pieceSymbol)) {
					case HOLE -> piece = new Hole(new Coordinate(row, column));
					case MUSHROOM -> piece = new Mushroom(new Coordinate(row, column));
					case BUNNY_WHITE, BUNNY_WHITE_HOLE, BUNNY_BROWN, BUNNY_BROWN_HOLE, BUNNY_GRAY, BUNNY_GRAY_HOLE ->
							piece = new Bunny(new Coordinate(row, column), Symbol.getName(pieceSymbol));
					case FOX_HEAD_UP, FOX_HEAD_DOWN, FOX_HEAD_LEFT, FOX_HEAD_RIGHT -> {
						String direction = Symbol.getName(pieceSymbol).getImageSrc().split("-")[2];
						direction = direction.substring(0, direction.indexOf(".")).toUpperCase();
						FoxHead fox = new FoxHead(new Coordinate(row, column), FoxDirection.valueOf(direction));
						piece = fox;
						FoxTail tail = fox.getTail();
						board.set((tail.getCoord().getRow() * getSize()) + tail.getCoord().getColumn(), tail);
					}
					default -> {
					}
				}
				
				board.set((row*getSize())+column,piece);
			}
						
			numBunnies = getBoard1D().stream().filter(p -> p instanceof Bunny).count();
			numHoles = getBoard1D().stream().filter(p -> p instanceof Hole || p.getSymbol().getImageSrc().contains("-hole")).count();
			
			if(numBunnies==0)		
			 throw new LevelException(LevelException.ERROR_NO_BUNNIES);
			
			if(numHoles==0)
				throw new LevelException(LevelException.ERROR_NO_HOLES);
			
			if(numHoles<numBunnies) throw new LevelException(LevelException.ERROR_MORE_BUNNIES_THAN_HOLES);
			             
		}

	}
	
	private int calculateRow(char letter) throws LevelException { /* Transforming row from char (ASCII) to int */
		int auxRow=letter-97;
		if(auxRow>=0 && auxRow<SIZE) {
			return auxRow;
		}
		else {
			throw new LevelException(LevelException.ERROR_INCORRECT_ROW);
		}
	}
	
	private int calculateColumn(char columnChar) throws LevelException { /* Transforming column from char (ASCII) to int */
		int auxColumn=columnChar-49;
		if(auxColumn>=0 && auxColumn<SIZE) {
			return auxColumn;
		}
		else {
			throw new LevelException(LevelException.ERROR_INCORRECT_COLUMN);
		}
	}
	
	public List<Piece> getBoard1D() {
		return board;
	}
	
	public Piece[][] getBoard2D() {
		Piece[][] auxPiece = new Piece[SIZE][SIZE];
		
		try {
		int i;
		int j;
		
		for(i=0; i<SIZE; i++) {
			for(j=0; j<SIZE; j++) { /* Getting rows */
				auxPiece[i][j]=this.getPiece(i, j);
			}
		}
		} catch (LevelException e) {
			e.printStackTrace();
		}
		
		return auxPiece;
	}
	
	public LevelDifficulty getDifficulty() {
		return this.DIFFICULTY;
	}
	
	public int getMinMoves() {
		return this.MIN_MOVES;
	}
	
	public Piece getPiece(int row, int column) throws LevelException { /* Getting piece depending on its coordinate. Row and Column given separately */
		
		if(this.validatePosition(row, column)) {
		    return board.stream().filter(piece -> piece.getCoord().getRow()==row && piece.getCoord().getColumn()==column).toList().get(0);
		}
		else {
			throw new LevelException(LevelException.ERROR_COORDINATE);
		}
	}
	
	public Piece getPiece(Coordinate coord) throws LevelException { /* Getting piece given its coordinate */
		
		if(this.validatePosition(coord.getRow(), coord.getColumn())) {
		    return board.stream().filter(piece -> piece.getCoord().equals(coord)).findAny().get();
		}
		else {
			throw new LevelException(LevelException.ERROR_COORDINATE);
		}
	}
	
	public int getSize() {
		return this.SIZE;
	}
	
	public boolean isFinished() {

		/* If Bunny instances amount are the same that Bunny at hole symbols amount, game is finished */
		return board.stream().filter(piece -> piece.getSymbol() == Symbol.BUNNY_BROWN_HOLE || piece.getSymbol() == Symbol.BUNNY_GRAY_HOLE || piece.getSymbol() == Symbol.BUNNY_WHITE_HOLE).count() == board.stream().filter(piece -> piece instanceof Bunny).count();
	}
	
	public boolean isObstacle(int row, int column) {
		boolean obstacle=false;
		
		try {
		    if(this.getPiece(row, column) instanceof Mushroom || this.getPiece(row, column) instanceof Fox || this.getPiece(row, column) instanceof Bunny) {
			    obstacle=true;
		    }
		} 
	    catch (LevelException e) {
			e.printStackTrace();
		}
		return obstacle;
	}
	
	public boolean isObstacle(Coordinate coord) {
		boolean obstacle=false;
		
		try {
			obstacle= this.getPiece(coord) instanceof Mushroom || this.getPiece(coord) instanceof Fox || this.getPiece(coord) instanceof Bunny;
		}
		catch (LevelException e) {
			e.printStackTrace();
		  }
		return obstacle;
	}
	
	public void setPiece(Coordinate coord, Piece piece) throws LevelException {
		board.set(this.board.indexOf(this.getPiece(coord)), piece);
	}
	
@Override	
	public String toString()  {
		int i;
		int j;
		StringBuilder finalString = new StringBuilder();
		
		finalString.append("  "); /* Appending initial whitespace to the string */
		for(i=0; i<=this.SIZE; i++) {  /* Appending column numbers to the string */
			finalString.append(i+1);
		}
		finalString.deleteCharAt(finalString.length()-1);
		finalString.append("\n");
		
		for(i=0; i<this.SIZE; i++) {
			finalString.append((char) (i + 97)).append("|"); /* Appending row letter */
			for(j=0; j<this.SIZE; j++) {
			    try {
					finalString.append(this.getPiece(i, j).getSymbol().getAscii()); /* Appending row's pieces */
				} catch (LevelException e) {
					e.printStackTrace();
				}
		    }
		    finalString.append("\n");
	    }
	    return finalString.toString();
	}

    private boolean validatePosition(int row, int column) { /* Asserts position depending on row and column */
		return row >= 0 && row < this.SIZE && column >= 0 && column < this.SIZE;
    }
	
}



