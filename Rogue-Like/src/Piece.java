import java.util.ArrayList;
import java.util.Random;

public abstract class Piece implements Comparable<Piece> {
	private TypePiece namePiece;
	private Position position;
	private ArrayList<Deplacement> moves;
	private ArrayList<Deplacement> eats;
	private boolean repeatable;
	private boolean player;

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
	public int getX() { return this.getPosition().getX(); }
	public int getY() { return this.getPosition().getY(); }
	public void setX(int x) { this.getPosition().setX(x); }
	public void setY(int y) { this.getPosition().setY(y); }

	public boolean isPlayer(){ return player; }
	public void setPlayer(boolean player){ this.player = player; }
	public int compareTo(Piece p2) { return this.getPosition().compareTo(p2.getPosition()); }
	public void setTypePiece(TypePiece tp) { this.namePiece = tp; }
	public ArrayList<Deplacement> getMoves() { return this.moves; }
	public ArrayList<Deplacement> getEats() { return this.eats; }
	private boolean getRepeatable() { return this.repeatable; }

	public void addMove(Deplacement m) {
		if (m.canGo()) {
			this.moves.add(m);
		}
		if (m.canEat()) {
			this.eats.add(m);
		}
	}
	
	public boolean isEnnemy(Piece p) { return this.isPlayer() != p.isPlayer(); }
	
	public ArrayList<Position> getPossibleMoves(Map map) {
		ArrayList<Position> result = new ArrayList<Position>();
		for (Deplacement move: this.getMoves()) {
			// Si la pos n'est pas hors champs et qu'il n'y a pas un pion et qu'il n'y a pas d'obstacle,
			// alors il peut y aller
			if (this.getRepeatable()) {
				int idx = 1;
				boolean end = false;
				while (!end) {
					if (map.isOutOfBounds(this.getX()+(move.getX()*idx), this.getY()+(move.getY()*idx))) {
						// Nouvelle position hors map
						end = true;
					} else if (map.isObstacle(this.getX()+(move.getX()*idx), this.getY()+(move.getY()*idx))) {
						// C'est un obstacle
						end = true;
					} else if (map.isWall(this.getX()+(move.getX()*idx), this.getY()+(move.getY()*idx))) {
						// C'est un mur
						end = true;
					} else if (map.getPiece(this.getX()+(move.getX()*idx), this.getY()+(move.getY()*idx)) != null) {
						// Il y a un pion, est-ce un ennemi ?
						if (this.isEnnemy(map.getPiece(this.getX()+(move.getX()*idx), this.getY()+(move.getY()*idx)))) {
							// Oui
							end = true;
						}
					} else {
						// Il n'y a pas de pion
						result.add(new Position(this.getX()+(move.getX()*idx), this.getY()+(move.getY()*idx)));
					}
					idx++;
				}
			} else {
				if (!map.isOutOfBounds(this.getX()+move.getX(), this.getY()+move.getY()) && !map.isObstacle(this.getX()+move.getX(), this.getY()+move.getY()) && !map.isWall(this.getX()+move.getX(), this.getY()+move.getY()) && map.getPiece(this.getX()+move.getX(), this.getY()+move.getY()) == null) {
					// Il n'y a pas de pion
					result.add(new Position(this.getX()+move.getX(), this.getY()+move.getY()));
				}
			}
		}
		return result;
	}
	
	public ArrayList<Position> getPossibleEats(Map map) {
		ArrayList<Position> result = new ArrayList<Position>();
		
		for (Deplacement move: this.getEats()) {
			// Si la pos n'est pas hors champs et qu'il n'y a pas un pion et qu'il n'y a pas d'obstacle,
			// alors il peut y aller
			if (this.getRepeatable()) {
				int idx = 1;
				boolean end = false;
				while (!end) {
					if (map.isOutOfBounds(this.getX()+(move.getX()*idx), this.getY()+(move.getY()*idx))) {
						// Nouvelle position hors map
						end = true;
					} else if (map.isObstacle(this.getX()+(move.getX()*idx), this.getY()+(move.getY()*idx))) {
						// C'est un obstacle
						end = true;
					} else if (map.isWall(this.getX()+(move.getX()*idx), this.getY()+(move.getY()*idx))) {
						// C'est un mur
						end = true;
					} else if (map.getPiece(this.getX()+(move.getX()*idx), this.getY()+(move.getY()*idx)) != null) {
						// Il y a un pion, est-ce un ennemi ?
						if (this.isEnnemy(map.getPiece(this.getX()+(move.getX()*idx), this.getY()+(move.getY()*idx)))) {
							// Oui - on peut le manger
							result.add(new Position(this.getX()+(move.getX()*idx), this.getY()+(move.getY()*idx)));
							end = true;
						}
					}
					idx++;
				}
			} else {				
				if (!map.isOutOfBounds(this.getX()+move.getX(), this.getY()+move.getY()) && !map.isObstacle(this.getX()+move.getX(), this.getY()+move.getY()) && !map.isWall(this.getX()+move.getX(), this.getY()+move.getY()) && map.getPiece(this.getX()+move.getX(), this.getY()+move.getY()) != null && this.isEnnemy(map.getPiece(this.getX()+move.getX(), this.getY()+move.getY()))) {
					// Oui - on peut le manger
					result.add(new Position(this.getX()+move.getX(), this.getY()+move.getY()));
				}
			}
		}
		
		return result;
	}

	public void move(int x, int y) {
		this.setX(x);
		this.setY(y);
	}

	public void moveAlea(Map map) {
        Random rdm = new Random(); // [0, 1[
        int rdmIdx = 1;
        ArrayList<Position> moves = this.getPossibleMoves(map);
		ArrayList<Position> eat = this.getPossibleEats(map);

		if(eat.size() != 0){
			map.eatPiece(this, eat.get(0).getX(), eat.get(0).getY());
		} else {
	        rdmIdx = rdm.nextInt(moves.size());
	        Position move = moves.get(rdmIdx);
	        this.move(move.getX(), move.getY());
		}
    }
}
