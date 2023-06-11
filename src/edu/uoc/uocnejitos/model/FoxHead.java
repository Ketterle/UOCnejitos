package edu.uoc.uocnejitos.model;

public class FoxHead extends Fox {
	public FoxHead(Coordinate coord, FoxDirection direction) {
		super(coord, null, direction);
		this.setHeadSymbol(direction);
		FoxTail foxtail = new FoxTail(this);
		this.setOtherHalf(foxtail);
		
		
	}
	
	public void setHeadSymbol(FoxDirection direction) {  /* Setting head symbol depending on foc direction */
		if(direction == FoxDirection.UP) {
			this.setSymbol(Symbol.FOX_HEAD_UP);
		}
		else if(direction == FoxDirection.DOWN) {
			this.setSymbol(Symbol.FOX_HEAD_DOWN);
		}
		else if(direction == FoxDirection.LEFT) {
			this.setSymbol(Symbol.FOX_HEAD_LEFT);
		}
		else {
			this.setSymbol(Symbol.FOX_HEAD_RIGHT);
		}
	}
	
	public FoxTail getTail() {
		return (FoxTail) this.getOtherHalf();
	}
	
	private boolean isValidHorizontalMove(Move move, Level level) {  /* Asserting horizontal move */
		boolean horizontal = false;
		
		if(move!=null && level!=null && move.isValidCoordinate(move, level)) {
			/* Asserting horizontal right move if fox looking right */
			if(move.getHorizontalDistance()>0 && move.getDirection()==MoveDirection.HORIZONTAL && this.getSymbol()==Symbol.FOX_HEAD_RIGHT)
				horizontal = level.getBoard1D().stream().noneMatch(piece -> (piece.getCoord().getRow() == move.getRowStart() && piece.getCoord().getColumn() > move.getColumnStart() && piece.getCoord().getColumn() <= move.getColumnEnd()) && !(piece instanceof Grass)) && move.getColumnEnd() < level.getSize();
			else if(move.getHorizontalDistance()<0 && move.getDirection()==MoveDirection.HORIZONTAL && this.getSymbol()==Symbol.FOX_HEAD_RIGHT) /* Asserting horizontal left move if fox looking right */
				horizontal= level.getBoard1D().stream().noneMatch(piece -> (piece.getCoord().getRow() == move.getRowStart() && piece.getCoord().getColumn() < (move.getColumnStart() - 1) && piece.getCoord().getColumn() > move.getColumnEnd() - 2) && !(piece instanceof Grass)) && move.getColumnEnd() > 0;
			else if(move.getHorizontalDistance()>0 && move.getDirection()==MoveDirection.HORIZONTAL && this.getSymbol()==Symbol.FOX_HEAD_LEFT) /* Asserting horizontal right move if fox looking left */
				horizontal= level.getBoard1D().stream().noneMatch(piece -> (piece.getCoord().getRow() == move.getRowStart() && piece.getCoord().getColumn() > (move.getColumnStart() + 1) && piece.getCoord().getColumn() < move.getColumnEnd() + 2) && !(piece instanceof Grass)) && move.getColumnEnd() < level.getSize() - 1;
			else if(move.getHorizontalDistance()<0 && move.getDirection()==MoveDirection.HORIZONTAL && this.getSymbol()==Symbol.FOX_HEAD_LEFT)  /* Asserting horizontal left move if fox looking left */
				horizontal= level.getBoard1D().stream().noneMatch(piece -> (piece.getCoord().getRow() == move.getRowStart() && piece.getCoord().getColumn() < move.getColumnStart() && piece.getCoord().getColumn() >= move.getColumnEnd()) && !(piece instanceof Grass)) && move.getColumnEnd() >= 0;
	    }
		return horizontal;
	}
	
	private boolean isValidVerticalMove(Move move, Level level) { /* Asserting vertical move */
        boolean vertical = false;
		
		if(move!=null && level!=null && move.isValidCoordinate(move, level)) {
			if(move.getVerticalDistance()>0 && move.getDirection()==MoveDirection.VERTICAL && this.getSymbol()==Symbol.FOX_HEAD_UP) { /* Asserting vertical down move if fox looking up */
				vertical= level.getBoard1D().stream().noneMatch(piece -> (piece.getCoord().getColumn() == move.getColumnStart() && piece.getCoord().getRow() > move.getRowStart() + 1 && piece.getCoord().getRow() < move.getRowEnd() + 2) && !(piece instanceof Grass)) && move.getRowEnd() < level.getSize() - 1;
		    } 
			else if(move.getVerticalDistance()<0 && move.getDirection()==MoveDirection.VERTICAL && this.getSymbol()==Symbol.FOX_HEAD_UP) /* Asserting vertical up move if fox looking up */
				vertical= level.getBoard1D().stream().noneMatch(piece -> (piece.getCoord().getColumn() == move.getColumnStart() && piece.getCoord().getRow() < (move.getRowStart()) && piece.getCoord().getRow() >= move.getRowEnd()) && !(piece instanceof Grass)) && move.getRowEnd() >= 0;
			else if(move.getVerticalDistance()>0 && move.getDirection()==MoveDirection.VERTICAL && this.getSymbol()==Symbol.FOX_HEAD_DOWN) /* Asserting vertical down move if fox looking down */
				vertical= level.getBoard1D().stream().noneMatch(piece -> (piece.getCoord().getColumn() == move.getColumnStart() && piece.getCoord().getRow() > (move.getRowStart()) && piece.getCoord().getRow() < move.getRowEnd()) && !(piece instanceof Grass)) && move.getRowEnd() < level.getSize();
			else if(move.getVerticalDistance()<0 && move.getDirection()==MoveDirection.VERTICAL && this.getSymbol()==Symbol.FOX_HEAD_DOWN) /* Asserting vertical up move if fox looking down */
				vertical= level.getBoard1D().stream().noneMatch(piece -> (piece.getCoord().getColumn() == move.getColumnStart() && piece.getCoord().getRow() < move.getRowStart() - 1 && piece.getCoord().getRow() > move.getRowEnd() - 2) && !(piece instanceof Grass)) && move.getRowEnd() > 0;
	    }
		return vertical;
	}
	
	public boolean isValidMove(Coordinate destination, Level level) { /* Asserting valid move */
		Move auxMove = new Move(this.getCoord(), destination);
		return this.isValidHorizontalMove(auxMove, level) || this.isValidVerticalMove(auxMove, level);
	}
	
	public boolean move(Coordinate destination, Level level) {  /* Implementing move. Setting origin and destination pieces if move is valid */
		if(destination != null && level !=null && this.isValidMove(destination, level)) {
			if(this.getSymbol()==Symbol.FOX_HEAD_UP) {  /* Implementing move when fox is looking up */
				try {
				Coordinate auxCoordinateOrigin = new Coordinate(this.getCoord().getRow()+1, this.getCoord().getColumn());
				Coordinate auxCoordinateDestination = new Coordinate(destination.getRow()+1,destination.getColumn());
				level.setPiece(this.getCoord(), new Grass(this.getCoord()));
				level.setPiece(auxCoordinateOrigin, new Grass(auxCoordinateOrigin));
				this.setCoord(destination);
				this.getOtherHalf().setCoord(auxCoordinateDestination);
				level.setPiece(destination, this);
				level.setPiece(auxCoordinateDestination, getTail());
				} catch (LevelException e) {
					e.printStackTrace();
				}
			}
				
			else if(this.getSymbol()==Symbol.FOX_HEAD_DOWN) { /* Implementing move when fox is looking down */
				try {
				Coordinate auxCoordinateOrigin = new Coordinate(this.getCoord().getRow()-1, this.getCoord().getColumn());
				Coordinate auxCoordinateDestination = new Coordinate(destination.getRow()-1,destination.getColumn());
				level.setPiece(this.getCoord(), new Grass(this.getCoord()));
				level.setPiece(auxCoordinateOrigin, new Grass(auxCoordinateOrigin));
				this.setCoord(destination);
				this.getOtherHalf().setCoord(auxCoordinateDestination);
				level.setPiece(destination, this);
				level.setPiece(auxCoordinateDestination, getTail());
				} catch (LevelException e) {
					e.printStackTrace();
				}
			}
			else if(this.getSymbol()==Symbol.FOX_HEAD_RIGHT) { /* Implementing move when fox is looking right */
				try {
				Coordinate auxCoordinateOrigin = new Coordinate(this.getCoord().getRow(), this.getCoord().getColumn()-1);
				Coordinate auxCoordinateDestination = new Coordinate(destination.getRow(),destination.getColumn()-1);
				level.setPiece(this.getCoord(), new Grass(this.getCoord()));
				level.setPiece(auxCoordinateOrigin, new Grass(auxCoordinateOrigin));
				this.setCoord(destination);
				this.getOtherHalf().setCoord(auxCoordinateDestination);
				level.setPiece(destination, this);
				level.setPiece(auxCoordinateDestination, getTail());
				} catch (LevelException e) {
					e.printStackTrace();
				}
			}
			else if(this.getSymbol()==Symbol.FOX_HEAD_LEFT) { /* Implementing move when fox is looking left */
				try {
				Coordinate auxCoordinateOrigin = new Coordinate(this.getCoord().getRow(), this.getCoord().getColumn()+1);
				Coordinate auxCoordinateDestination = new Coordinate(destination.getRow(),destination.getColumn()+1);
				level.setPiece(this.getCoord(), new Grass(this.getCoord()));
				level.setPiece(auxCoordinateOrigin, new Grass(auxCoordinateOrigin));
				this.setCoord(destination);
				this.getOtherHalf().setCoord(auxCoordinateDestination);
				level.setPiece(destination, this);
				level.setPiece(auxCoordinateDestination, getTail());
				} catch (LevelException e) {
					e.printStackTrace();
				}
			}
			return true;
		}
		else {
			return false;
		}
	}
}
