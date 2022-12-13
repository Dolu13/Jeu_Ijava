import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;
import java.util.Set;

import java.io.BufferedReader;
import java.io.FileReader;

public class Map {

	private ArrayList<String> map;
	private ArrayList<Piece> listePieces; // Passer en HashSet si possible
	private ArrayList<Position> possibleMoves;
	private ArrayList<Position> possibleEats;
	public int nivMap;
	
	public Map(String map, int lvl) {		
		this.map = Textured.loadMap(map);
		this.nivMap = lvl;
		this.map.remove(0); // Entete
		
		this.listePieces = new ArrayList<Piece>();
		this.possibleMoves = new ArrayList<Position>();
		this.possibleEats = new ArrayList<Position>();
	}

	public ArrayList<Piece> getListPieces() { return this.listePieces; }
	public ArrayList<Position> getPossibleMoves() { return this.possibleMoves; }
	public ArrayList<Position> getPossibleEats() { return this.possibleEats; }
	public ArrayList<String> getMap() { return this.map; }
	
	public Piece getPiece(int x, int y) {
		
		Piece result = null;
		for (int idx=0; idx<this.listePieces.size(); idx++) {
			if (this.listePieces.get(idx).getX() == x && this.listePieces.get(idx).getY() == y) {
				// Trouvé
				result = this.listePieces.get(idx);
			}
		}
		if (result == null) {
			// Throw error
		}
		return result;
	}
	
	public void setPossibleMoves(ArrayList<Position> moves) { this.possibleMoves = moves; }
	public void setPossibleEats(ArrayList<Position> eats) { this.possibleEats = eats; }
	
	public boolean isOutOfBounds(int x, int y) {
		// Trouver la bordure maximum
		int maxLineLength = 0;
		for (String i: this.map) {
			if (i.length() > maxLineLength) {
				maxLineLength = i.length();
			}
		}
		return (x < 0 || y < 0 || x >= maxLineLength || y >= this.map.size());
	}
	
	public boolean isObstacle(int x, int y) {
		return map.get(y).charAt(x) == '#';
	}
	public boolean isWall(int x, int y) {
		return map.get(y).charAt(x) == '+';
	}
	public boolean isExit(int x, int y) {
		return map.get(y).charAt(x) == 'E';
	}
	
	public boolean posValid(int x, int y) {
		return this.getPiece(x, y) == null && !this.isObstacle(x, y) && !this.isOutOfBounds(x, y) && !this.isWall(x, y);
	}
	
	public ArrayList<Position> getPossiblePlaces() {
		ArrayList<Position> result = new ArrayList<Position>();
		for (int lig=0; lig<this.map.size(); lig++) {
			for (int col=0; col<this.map.get(lig).length(); col++) {
				if (!this.isWall(col, lig) && !this.isObstacle(col, lig) && !this.isOutOfBounds(col, lig) && this.getPiece(col, lig) == null) {
					result.add(new Position(col, lig));
				}
			}
		}
		return result;
	}
	
	public boolean addPiece(Piece p) {
		boolean result = posValid(p.getX(), p.getY());
		if (result) {
			this.listePieces.add(p);
		}
		return result;
	}

	public Piece CreePiece(int X , int Y, TypePiece aleaPiece){
		Piece p = null;
		if (aleaPiece == TypePiece.Pion) {
			p = new Pion(X, Y, false);
		} else if (aleaPiece == TypePiece.Reine) {
			p = new Reine(X, Y, false);
		} else if (aleaPiece == TypePiece.Roi) {
			p = new Roi(X, Y, false);
		} else if (aleaPiece == TypePiece.Fou) {
			p = new Fou(X, Y, false);
		} else if (aleaPiece == TypePiece.Cavalier) {
			p = new Cavalier(X, Y, false);
		} else if (aleaPiece == TypePiece.Tour) {
			p = new Tour(X, Y, false);
		}
		return p;
	}

	public void addEnnemisAlea(){
		boolean valid = false;
		while(!valid){
			// Position Pièce
			int y = 1 +(int)(Math.random() * (map.size() - 1));
			int x = 1 + (int)(Math.random() * (map.get(0).length() - 1));
			// Type pièce
			TypePiece type = getTypeAlea();
			// Cree le pion
			valid = addPiece(CreePiece(x, y, type));
		}
	}
	
	private TypePiece getTypeAlea(){
		if(nivMap < 5){
			// Le -1 empéche la reine et le roi
			int alea = (int)(Math.random() * (TypePiece.values().length-2));
			return TypePiece.values()[alea];
		}
		else{
			// Le +2 empéche le pion et le cavalier
			int alea = 2 + (int)(Math.random() * (TypePiece.values().length - 2));
			return TypePiece.values()[alea];
		}
	}

	public void eatPiece(Piece pMove, int xDest, int yDest){
		// verifier si la piece est different de la team pMove
		if(pMove.isPlayer() != this.getPiece(xDest, yDest).isPlayer()){
			// suppr la piece manger
			listePieces.remove(getPiece(xDest, yDest));
			// bouger la piece
			pMove.setX(xDest);
			pMove.setY(yDest);
		}
		else{
			System.out.println("On ne peut pas attaquer un alli� !");
		}
	}
	
	public ArrayList<Piece> getAllies() {
		ArrayList<Piece> result = new ArrayList<Piece>();
		for (Piece p: this.getListPieces()) {
			if (p.isPlayer()) {
				result.add(p);
			}
		}
		return result;
	}
	
	public ArrayList<Piece> getEnnemies() {
		ArrayList<Piece> result = new ArrayList<Piece>();
		for (Piece p: this.getListPieces()) {
			if (!p.isPlayer()) {
				result.add(p);
			}
		}
		return result;
	}
	
	public boolean randomEat(ArrayList<Piece> toEat, ArrayList<Piece> eater) {
		boolean done = false;
		for (Piece p: eater) {
			// Pour toutes les pieces :
			if (!done) {
				if (p.getPossibleEats(this).size() > 0) {
					this.eatPiece(p, p.getPossibleEats(this).get(0).getX(), p.getPossibleEats(this).get(0).getY()); // On prend le premier pion qui vient
					done = true;
				}
			}
		}
		return done;
	}
	
	public Piece randomEater(ArrayList<Piece> toEat, ArrayList<Piece> eater) {
		Piece done = null;
		for (Piece p: eater) {
			// Pour toutes les pieces :
			if (done == null) {
				if (p.getPossibleEats(this).size() > 0) {
					done = p;
				}
			}
		}
		return done;
	}
}
