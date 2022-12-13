import java.util.ArrayList;
import java.util.Random;

public abstract class Piece implements Comparable<Piece> {
	private TypePiece namePiece;
	private Position position;
	private ArrayList<Deplacement> moves;
	private ArrayList<Deplacement> eats;
	private boolean player;
	private boolean repeatable;

	public Piece(TypePiece tp, int x, int y, boolean player, boolean repeatable) {
		this.namePiece = tp;
		this.position = new Position(x, y);
		this.player = player;
		this.repeatable = repeatable;
		this.moves = new ArrayList<Deplacement>();
		this.eats = new ArrayList<Deplacement>();
	}

	public TypePiece getTypePiece() { return namePiece; }
	
	public Position getPosition() { return this.position; }

	public boolean isFree(char c) {
		if (c == '.' || c == '/') {
			return true;
		} else if (c >= 'a' && c <= 'z') {
			return true;
		} else {
			return false;
		}
	}

	public int getX() { return this.getPosition().getX(); }
	public int getY() { return this.getPosition().getY(); }
	public void setX(int x) { this.getPosition().setX(x); }
	public void setY(int y) { this.getPosition().setY(y); }

	public boolean isPlayer(){
		return player;
	}
	public void setPlayer(boolean player){
		this.player = player;
	}


	public int compareTo(Piece p2) {
		return this.getPosition().compareTo(p2.getPosition());
	}
	
	public void setTypePiece(TypePiece tp) { this.namePiece = tp; }
	public ArrayList<Deplacement> getMoves() { return this.moves; }
	public ArrayList<Deplacement> getEats() { return this.eats; }
	private boolean getRepeatable() { return this.repeatable; }
	

	public String toString(){
		if(!player){
			return "\u001B[31m"+ this.namePiece.getCarac() + "\u001B[0m";
		}else{
			return this.namePiece.getCarac()+"";
		}
	}

	public void addMove(Deplacement m) {
		if (m.canGo()) {
			this.moves.add(m);
		}
		if (m.canEat()) {
			this.eats.add(m);
		}
	}
	
	// /!\ ligne 85 : placetook out of range 12 ?
	public ArrayList<Position> getPossibleMoves(Map map) {
		ArrayList<Position> result = new ArrayList<Position>();
			for (Deplacement move: this.getMoves()) {
				// Si la pos n'est pas hors champs et qu'il n'y a pas un pion et qu'il n'y a pas d'obstacle,
				// alors il peut y aller
				if (this.getRepeatable()) {
					int idx = 1;
					while (!map.isOutOfBounds(this.getX()+(move.getX()*idx), this.getY()+(move.getY()*idx))) {
						try{
							if (!map.isOutOfBounds(this.getX()+(move.getX()*idx), this.getY()+(move.getY()*idx)) && !map.placeTook(this.getX()+(move.getX()*idx), this.getY()+(move.getY()*idx)) && move.canGo()) {
								
								result.add(new Position(this.getX()+(move.getX()*idx), this.getY()+(move.getY()*idx)));
							}
						}catch(Exception e){}
						idx++;
					}				
				} else {
					try{
						if (!map.isOutOfBounds(this.getX()+move.getX(), this.getY()+move.getY()) && !map.placeTook(this.getX()+move.getX(), this.getY()+move.getY()) && move.canGo()) {
							result.add(new Position(this.getX()+move.getX(), this.getY()+move.getY()));
						}
					}catch(Exception e){}
				}
			}
		
		return result;
	}
	
	public ArrayList<Position> getPossibleEats(Map map) {
		ArrayList<Position> result = new ArrayList<Position>();
		
		for (Deplacement move: this.getMoves()) {
			// Si la pos n'est pas hors champs et qu'il n'y a pas un pion et qu'il n'y a pas d'obstacle,
			// alors il peut y aller
			if (this.getRepeatable()) {
				int idx = 1;
				while (!map.isOutOfBounds(this.getX()+(move.getX()*idx), this.getY()+(move.getY()*idx))) {
					if (!map.isOutOfBounds(this.getX()+(move.getX()*idx), this.getY()+(move.getY()*idx)) && map.getPiece(this.getX()+(move.getX()*idx), this.getY()+(move.getY()*idx)) != null && move.canEat()) {
						result.add(new Position(this.getX()+(move.getX()*idx), this.getY()+(move.getY()*idx)));
					}
					idx++;
				}				
			} else {
				if (!map.isOutOfBounds(this.getX()+move.getX(), this.getY()+move.getY()) && !map.placeTook(this.getX()+move.getX(), move.getY()+move.getY()) && move.canEat()) {
					result.add(new Position(this.getX()+move.getX(), this.getY()+move.getY()));
				}
			}
		}
		
		return result;
	}

	public void moveAlea(Map map) {
        Random rdm = new Random(); // [0, 1[
        int rdmIdx = 1;
        ArrayList<Position> moves = this.getPossibleMoves(map);
        rdmIdx = rdm.nextInt(moves.size());
        Position move = moves.get(rdmIdx);
        this.setX(move.getX());
        this.setY(move.getY());
    }
}
