package edu.uoc.uocnejitos.model;

public class Move {
	private Coordinate coordStart;
	private Coordinate coordEnd;
	
	public Move(int xStart, int yStart, int xEnd, int yEnd) {
		this.coordStart = new Coordinate(xStart, yStart);
		this.coordEnd = new Coordinate(xEnd, yEnd);
	}
	
	public Move(Coordinate coordStart, Coordinate coordEnd) {
		if(coordStart!=null && coordEnd != null) {
		this.coordStart = new Coordinate(coordStart.getRow(), coordStart.getColumn());
		this.coordEnd = new Coordinate(coordEnd.getRow(), coordEnd.getColumn());
		}
	}

	public Coordinate getStart() {
		return coordStart;
	}

	public void setStart(Coordinate coordStart) {
		this.coordStart = coordStart;
	}
	
	public int getRowStart() {
		return this.coordStart.getRow();
	}
	
	public void setRowStart(int rowStart) {
		this.coordStart.setRow(rowStart);
	}
	
	public int getColumnStart() {
		return this.coordStart.getColumn();
	}
	
	public void setColumnStart(int columnStart) {
		this.coordStart.setColumn(columnStart);
	}
	
	public Coordinate getEnd() {
		return coordEnd;
	}

	public void setEnd(Coordinate coordEnd) {
		this.coordEnd = coordEnd;
	}
	
	public int getRowEnd() {
		return this.coordEnd.getRow();
	}
	
	public void setRowEnd(int rowEnd) {
		this.coordEnd.setRow(rowEnd);
	}
	
	public int getColumnEnd() {
		return this.coordEnd.getColumn();
	}
	
	public void setColumnEnd(int columnEnd) {
		this.coordEnd.setColumn(columnEnd);
	}
	
	public MoveDirection getDirection() {
		
		if(coordStart.getRow()==coordEnd.getRow() && coordStart.getColumn()!=coordEnd.getColumn()) {
			return MoveDirection.HORIZONTAL;
		}
		
		else if(coordStart.getRow()!=coordEnd.getRow() && coordStart.getColumn()==coordEnd.getColumn()) {
			return MoveDirection.VERTICAL;
		}
		
		else {
			return MoveDirection.INVALID;
		}
	}
	
	public int getHorizontalDistance() {
		return this.coordEnd.getColumn()-this.coordStart.getColumn();
	}
	
	public int getVerticalDistance() {
		return this.coordEnd.getRow()-this.coordStart.getRow();
	}
	
	public String toString() {
		return "("+this.getRowStart()+","+this.getColumnStart()+") --> ("+this.getRowEnd()+","+this.getColumnEnd()+") : "+this.getDirection();
	}
	
	public boolean isValidCoordinate(Move move, Level level) {
		return move.getRowEnd() < level.getSize() && move.getRowEnd() >= 0 && move.getColumnEnd() < level.getSize() && move.getColumnEnd() >= 0;
	}

}
