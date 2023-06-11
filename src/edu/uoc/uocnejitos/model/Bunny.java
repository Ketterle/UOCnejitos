package edu.uoc.uocnejitos.model;

public class Bunny extends Piece implements Movable {
	
	public Bunny(Coordinate coord) {
		super(coord, Symbol.BUNNY_BROWN);
	}
	
	public Bunny(Coordinate coord, Symbol symbol) {
		super(coord, symbol);
	}
	
	public boolean isInHole() {
		if(this.getSymbol().getAscii()=='W' || this.getSymbol().getAscii()=='B' || this.getSymbol().getAscii()=='G') {
		    return true;
	    }
	    else {
		    return false;
	    }
    }
	
	public boolean move(Coordinate destination, Level level) throws LevelException {
		boolean moveDone = false;
		if(destination != null && this.isValidMove(destination, level)) {
			if(this.isInHole()) {
				level.setPiece(this.getCoord(), new Hole(this.getCoord()));
				}
			else {
				level.setPiece(this.getCoord(), new Grass(this.getCoord()));
			}
			if(level.getPiece(destination) instanceof Hole) {
				
				if(this.getSymbol() == Symbol.BUNNY_BROWN || this.getSymbol()==Symbol.BUNNY_BROWN_HOLE) {
				this.setCoord(destination);
				this.setSymbol(Symbol.BUNNY_BROWN_HOLE);
				level.setPiece(destination, this);
				}
			
				else if(this.getSymbol() == Symbol.BUNNY_WHITE || this.getSymbol()==Symbol.BUNNY_WHITE_HOLE) {
				this.setCoord(destination);
				this.setSymbol(Symbol.BUNNY_WHITE_HOLE);
				level.setPiece(destination, this);
				}
			
				else if(this.getSymbol() == Symbol.BUNNY_GRAY || this.getSymbol()==Symbol.BUNNY_GRAY_HOLE) {
				this.setCoord(destination);
				this.setSymbol(Symbol.BUNNY_GRAY_HOLE);
				level.setPiece(destination, this);
				}
			}
			
			else {
				if(this.getSymbol() == Symbol.BUNNY_BROWN || this.getSymbol()==Symbol.BUNNY_BROWN_HOLE) {
					this.setCoord(destination);
					this.setSymbol(Symbol.BUNNY_BROWN);
					level.setPiece(destination, this);
					}
				
					else if(this.getSymbol() == Symbol.BUNNY_WHITE || this.getSymbol()==Symbol.BUNNY_WHITE_HOLE) {
					this.setCoord(destination);
					this.setSymbol(Symbol.BUNNY_WHITE);
					level.setPiece(destination, this);
					}
				
					else if(this.getSymbol() == Symbol.BUNNY_GRAY || this.getSymbol()==Symbol.BUNNY_GRAY_HOLE) {
					this.setCoord(destination);
					this.setSymbol(Symbol.BUNNY_GRAY);
					level.setPiece(destination, this);
					}
			}
			moveDone=true;
		 }
		return moveDone;
		}
	
	private boolean isValidHorizontalMove(Move move, Level level) {   /* Asserting horizontal move */
		boolean horizontal = false;
		
		if(move != null && level !=null && move.isValidCoordinate(move, level)) {
			if(move.getHorizontalDistance()>1 && move.getDirection()==MoveDirection.HORIZONTAL) { /* Asserting right move */
				try {
					if((level.getBoard1D().stream().filter(piece -> piece.getCoord().getRow()==move.getRowStart() && piece.getCoord().getColumn()>move.getColumnStart() && piece.getCoord().getColumn()<move.getColumnEnd() && (piece instanceof Grass || piece instanceof Hole))).count()==0 && (level.getPiece(move.getEnd()) instanceof Grass || level.getPiece(move.getEnd()) instanceof Hole)) { 
						horizontal=true;
					}
					else {
						horizontal=false;
					}
				} catch (LevelException e) {
					e.printStackTrace();
				}
				
			} else if(move.getHorizontalDistance()<-1 && move.getDirection()==MoveDirection.HORIZONTAL) /* Asserting left move */
				try {
					if((level.getBoard1D().stream().filter(piece -> piece.getCoord().getRow()==move.getRowStart() && piece.getCoord().getColumn()<move.getColumnStart() && piece.getCoord().getColumn()>move.getColumnEnd() && (piece instanceof Grass || piece instanceof Hole))).count()==0 && (level.getPiece(move.getEnd()) instanceof Grass || level.getPiece(move.getEnd()) instanceof Hole)) {
						horizontal=true;
					}
					else {
						horizontal=false;
					}
				} catch (LevelException e) {
					e.printStackTrace();
				}
	    }
		return horizontal;
	}
	
	private boolean isValidVerticalMove(Move move, Level level) { /* Asserting vertical move */
		boolean vertical = false;
		
		if(move!=null && level!=null && move.isValidCoordinate(move, level)) {
			if(move.getVerticalDistance()>1 && move.getDirection()==MoveDirection.VERTICAL) { /* Asserting down move */
				try {
					if((level.getBoard1D().stream().filter(piece -> piece.getCoord().getColumn()==move.getColumnStart() && piece.getCoord().getRow()>move.getRowStart() && piece.getCoord().getRow()<move.getRowEnd() && (piece instanceof Grass || piece instanceof Hole))).count()==0 && (level.getPiece(move.getEnd()) instanceof Grass || level.getPiece(move.getEnd()) instanceof Hole)) {
						vertical=true;
					}
					else {
						vertical=false;
					}
				} catch (LevelException e) {
					e.printStackTrace();
				}
			} else if(move.getVerticalDistance()<-1 && move.getDirection()==MoveDirection.VERTICAL) /* Asserting up move */
				try {
					if((level.getBoard1D().stream().filter(piece -> piece.getCoord().getColumn()==move.getColumnStart() && piece.getCoord().getRow()<move.getRowStart() && piece.getCoord().getRow()>move.getRowEnd() && (piece instanceof Grass || piece instanceof Hole))).count()==0 && (level.getPiece(move.getEnd()) instanceof Grass || level.getPiece(move.getEnd()) instanceof Hole)) {
						vertical=true;
					}
					else {
						vertical=false;
					}
				} catch (LevelException e) {
					e.printStackTrace();
				}
	    }
		return vertical;
	}
	
	public boolean isValidMove(Coordinate destination, Level level) { /* Asserting Bunny move */
		Move auxMove = new Move(this.getCoord(), destination);
		if(this.isValidHorizontalMove(auxMove,level) || this.isValidVerticalMove(auxMove,level)) {
		    return true;	
		}
		else {
			return false;
		}
	}
	
}
