package edu.uoc.uocnejitos.model;

public class Coordinate {
	private int row;
	private int column;
	
	public Coordinate(int row, int column) {
		this.setRow(row);
		this.setColumn(column);
	}
	
	public int compareTo(Coordinate other) {  /* Comparing coordinates */
		if(this.row<other.row) {
			return -1;
		}
		else if(this.row>other.row) {
			return 1;
		}
		else return Integer.compare(this.column, other.column);
	}
@Override	
	public boolean equals(Object obj) {  /* Asserting equal coordinates */
		Coordinate auxCoordinate = (Coordinate) obj;

	return auxCoordinate.row == this.row && auxCoordinate.column == this.column;
	}
	
	public int getRow() {
		return row;
	}

	public void setRow(int row) {
		this.row = row;
	}

	public int getColumn() {
		return column;
	}

	public void setColumn(int column) {
		this.column = column;
	}
	
	public String toString() {
		return ("("+row+","+column+")");
	}
	
	public boolean validCoordinate(Level level) {  /* Helper method. Asserting valid coordinate */
		return this.getColumn() >= 0 && this.getColumn() < level.getSize() && this.getRow() >= 0 && this.getRow() < level.getSize();
	}
	

}
