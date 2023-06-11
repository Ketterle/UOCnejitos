package edu.uoc.uocnejitos.model;

/** 
 * Piece Simple Factory class.
 * @author David García Solórzano
 * @version 1.0  
 */
public abstract class PieceFactory {
	
	/**
	 * 
	 * @param coord Coordinate in which the piece is.	 
	 * @param symbol Value of the enumeration called Symbol that corresponds to the piece.
	 * @return Piece object that is related to the "symbol".	 
	 */
	public static Piece getPiece(Coordinate coord, Symbol symbol){

		return switch (symbol) {
			case BUNNY_WHITE, BUNNY_WHITE_HOLE, BUNNY_GRAY, BUNNY_GRAY_HOLE, BUNNY_BROWN, BUNNY_BROWN_HOLE ->
					new Bunny(coord, symbol);
			case MUSHROOM -> new Mushroom(coord);
			case HOLE -> new Hole(coord);
			default -> // Grass and others
					new Grass(coord);
		};
	}
}