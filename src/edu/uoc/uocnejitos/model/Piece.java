package edu.uoc.uocnejitos.model;

public abstract class Piece {
	private Coordinate coordinate;
	private Symbol symbol;
	
	protected Piece(Coordinate coordinate, Symbol symbol) {
		this.setCoord(coordinate);
		this.setSymbol(symbol);
	}
	
@Override	
	public boolean equals(Object obj) {  /* Overriding equals method */
		Piece auxPiece = (Piece) obj;

	return auxPiece.getCoord().equals(this.getCoord()) && auxPiece.getSymbol().equals(this.getSymbol());
	}
	
	public Coordinate getCoord() {
		return this.coordinate;
	}
	
	public Symbol getSymbol() {
		return this.symbol;
	}
	
	public void setCoord(Coordinate coord) {
		this.coordinate = coord;
	}
	
	public void setCoord(int row, int column) {
		this.coordinate.setRow(row);
		this.coordinate.setColumn(column);
	}
	
	public void setSymbol(Symbol symbol) {
		this.symbol=symbol;
	}
	
	public String toString() {
		return String.valueOf(this.symbol.getAscii());
	}

}
