package edu.uoc.uocnejitos.model;

public class FoxTail extends Fox{
	
    public FoxTail(FoxHead foxHead) {
    	super(null,null,null);
    	this.setOtherHalf(foxHead);
    	this.setCoord(this.calculateCoord(foxHead.getCoord()));
    	this.setSymbol(this.tailSymbol(foxHead));
    	this.setDirection(foxHead.getDirection());
    }
    
    public Coordinate calculateCoord(Coordinate coordHead) {  /* Calculating fox tail depending on fox head */
    	Coordinate auxCoord = new Coordinate(0, 0);
    	
    	if(this.getOtherHalf().getSymbol()==Symbol.FOX_HEAD_LEFT) {
    		auxCoord.setRow(coordHead.getRow());
    		auxCoord.setColumn(coordHead.getColumn()+1);
    	}
    	else if(this.getOtherHalf().getSymbol()==Symbol.FOX_HEAD_RIGHT) {
    		auxCoord.setRow(coordHead.getRow());
    		auxCoord.setColumn(coordHead.getColumn()-1);
    	}
    	else if(this.getOtherHalf().getSymbol()==Symbol.FOX_HEAD_UP) {
    		auxCoord.setRow(coordHead.getRow()+1);
    		auxCoord.setColumn(coordHead.getColumn());
    	}
    	else if(this.getOtherHalf().getSymbol()==Symbol.FOX_HEAD_DOWN) {
    		auxCoord.setRow(coordHead.getRow()-1);
    		auxCoord.setColumn(coordHead.getColumn());
    	}
    	return auxCoord;
    	
    }
    
    public Symbol tailSymbol(FoxHead foxHead) { /* Getting tail symbol depending on fo head orientation */
    	if(foxHead.getSymbol() == Symbol.FOX_HEAD_UP) {
    		return Symbol.FOX_TAIL_UP;
    	}
    	else if(foxHead.getSymbol() == Symbol.FOX_HEAD_DOWN) {
    		return Symbol.FOX_TAIL_DOWN;
    	}
    	else if(foxHead.getSymbol() == Symbol.FOX_HEAD_RIGHT) {
    		return Symbol.FOX_TAIL_RIGHT;
    	}
    	else {
    		return Symbol.FOX_TAIL_LEFT;
    	}
    }
    
    private Coordinate getHeadEndCoordinate(Coordinate tailDestination, int sizeBoard) { /* Getting head destination coordinate when move is done */
    	Coordinate auxCoord = new Coordinate(0,0);
    	
    	if(tailDestination.getRow()<0 || tailDestination.getRow()>=sizeBoard || tailDestination.getColumn()<0 || tailDestination.getColumn()>=sizeBoard) {
    		return null;
    	}
    	else {
    		if(this.getDirection()==FoxDirection.UP) {
    			auxCoord.setRow(tailDestination.getRow()-1);
    			auxCoord.setColumn(tailDestination.getColumn());
    		}
    		else if(this.getDirection()==FoxDirection.DOWN) {
    			auxCoord.setRow(tailDestination.getRow()+1);
    			auxCoord.setColumn(tailDestination.getColumn());
    		}
    		else if(this.getDirection()==FoxDirection.RIGHT) {
    			auxCoord.setRow(tailDestination.getRow());
    			auxCoord.setColumn(tailDestination.getColumn()+1);
    		}
    		else {
    			auxCoord.setRow(tailDestination.getRow());
    			auxCoord.setColumn(tailDestination.getColumn()-1);
    		}
    		return auxCoord;
    	}
    }
    
    public boolean isValidMove(Coordinate destination, Level level) { /* Asserting valid move depending on tail destination coordinate */
    	boolean isValid=false;
    	if(destination != null && level != null) {
    	    if(destination.validCoordinate(level)) {
    	        try {
					isValid= this.getOtherHalf().isValidMove(this.getHeadEndCoordinate(destination, level.getSize()), level);
		        } catch (LevelException e) {
			        e.printStackTrace();
		       }
    	    }
        }
    	return isValid;
    }
    
    public boolean move(Coordinate destination, Level level) {  /* Implementing move depending on tail end coordinate */
        if(destination != null && level != null) {
        	if(this.isValidMove(destination, level)) {
        		try {
					this.getOtherHalf().move(this.calculateCoord(destination), level);
				} catch (LevelException e) {
					e.printStackTrace();
				}
        		return true;
        	}
        	else return false;
        }
        else return false;
    }
    
}
