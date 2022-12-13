import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;
import java.util.Set;
import java.io.BufferedReader;
import java.io.FileReader;

public class Map {

	ArrayList<String> map;
	ArrayList<Piece> listePieces; // Passer en HashSet si possible
	private Piece selected;
	private ArrayList<Position> possibleMoves;
	private ArrayList<Position> possibleEats;
	private int nivMap;
	
	public Map(String map) {
		Scanner sc = new Scanner(map);
		sc.useDelimiter(";");
		
		this.map = new ArrayList<String>();
		if (sc.hasNext()) {
			this.nivMap = Map.recupereNombre(sc.next());
		}
		while (sc.hasNext()) {
			this.map.add(sc.next());
		}
		this.listePieces = new ArrayList<Piece>();
		this.possibleMoves = new ArrayList<Position>();
		this.possibleEats = new ArrayList<Position>();
	}

	public ArrayList<Piece> getListePieces(){
		return this.listePieces;
	}

	static int recupereNombre(String text){
		int i=0;
		boolean cherch = true;
		boolean trouve = false;
		String res = "";
		while(cherch && i < text.length()){
			if(text.charAt(i) >= '0' && text.charAt(i) <= '9'){
				trouve = true;
				res += text.charAt(i);
			}
			else if(trouve){
				cherch = false;
			}
			i++;
		}
		return Integer.parseInt(res);
	}
	
	public Piece getSelected() { return this.selected; }
	
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
	public void setSelected(Piece p) { this.selected = p;}
	public void setPossibleMoves(ArrayList<Position> moves) {
		this.possibleMoves = moves;
	}
	public void setPossibleEats(ArrayList<Position> eats) {
		this.possibleEats = eats;
	}
	
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

	public boolean placeTook(int x, int y) {
		boolean result = false;
		if (!this.isOutOfBounds(x, y)) {
			if (map.get(y).charAt(x) != '.') {
				result = true;
				// Il y a un obstacle
			}
			if (!result) {
				// Vérifions si il y a un pion
				Collections.sort(this.listePieces);
				boolean end = false;
				for (int i=0; i<this.listePieces.size(); i++) {
					if (this.listePieces.get(i).getX() == x && this.listePieces.get(i).getY() == y) {
						// Place occupée par une piece
						result = true;
					}
				}
			}
		}
		return result;
	}
	
	public boolean posValid(int x, int y) {
		return !(isOutOfBounds(x, y) || placeTook(x, y));
	}
	
	public boolean addPiece(Piece p) {
		boolean result = posValid(p.getX(), p.getY());
		if (result) {
			this.listePieces.add(p);
		}
		return result;
	}
	
	public String showMap() {
		StringBuilder sb = new StringBuilder();
		int piecesIdx = 0;
		int movesIdx = 0;
		int eatsIdx = 0;
		Collections.sort(this.listePieces);
		Collections.sort(this.possibleMoves);
		Collections.sort(this.possibleEats);
		System.out.println(this.possibleEats);
		System.out.println(this.possibleMoves);

		for (int lig=0; lig<this.map.size(); lig++) {
			for (int col=0; col<this.map.get(lig).length(); col++) {
				if (this.possibleEats.size() > eatsIdx && this.possibleEats.get(eatsIdx).getX() == col && this.possibleEats.get(eatsIdx).getY() == lig && this.getPiece(col, lig) != null) {
					// Une possibilité est à cet endroit
					System.out.println(lig+" "+col);
					if (this.listePieces.size() > piecesIdx && this.listePieces.get(piecesIdx).getX() == col && this.listePieces.get(piecesIdx).getY() == lig) {
						piecesIdx++;
					}
					sb.append("x");
					eatsIdx++;
				} else if (this.possibleMoves.size() > movesIdx && this.possibleMoves.get(movesIdx).getX() == col && this.possibleMoves.get(movesIdx).getY() == lig) {
					// Une possibilité est à cet endroit
					if (this.listePieces.size() > piecesIdx && this.listePieces.get(piecesIdx).getX() == col && this.listePieces.get(piecesIdx).getY() == lig) {
						piecesIdx++;
					}
					sb.append("o");
					movesIdx++;
				} else if (this.listePieces.size() > piecesIdx && this.listePieces.get(piecesIdx).getX() == col && this.listePieces.get(piecesIdx).getY() == lig) {
					// Une pièce est à cet endroit
					sb.append(this.listePieces.get(piecesIdx));
					piecesIdx++;
				} else {
					sb.append(this.map.get(lig).charAt(col));
				}
			}
			sb.append("\n");
		}
		
		return sb.toString();
	}

	public static String loadMap(String filename){
		String res = "";
		try (BufferedReader bufferedreader = new BufferedReader(new FileReader(filename))) {
			String strCurrentLine;
			while ((strCurrentLine = bufferedreader.readLine()) != null) {
				res += strCurrentLine;
			}
		} catch (Exception e) {
			System.out.println("Erreur surement dû au Bug de l'an 2000...");
		}
		return res;
	}

	//test avec enum pour mettre un type d'ennemi aléatoire mais j'avais des problemes
	// public static <T extends Enum<?>> T randomEnum(Class<T> ListEnum){
	// 	int x = (int)Math.floor(Math.random()*ListEnum.getEnumConstants().length);
    //     return ListEnum.getEnumConstants()[x];
    // }

	public Piece CreePiece(int X , int Y, TypePiece aleaPiece){
		Piece p = new Pion(X, Y, false);
		//choisir piece
		if(aleaPiece == TypePiece.Fou){
			p = new Fou(X, Y, false);
		}
		else if(aleaPiece == TypePiece.Reine){
			p = new Reine(X, Y, false);
		}
		else if(aleaPiece == TypePiece.Tour){
			p = new Tour(X, Y, false);
		}
		else if(aleaPiece == TypePiece.Cavalier){
			p = new Cavalier(X, Y, false);
		}
		return p;
	}

	public void addEnnemisAlea(){
		for(int i=0;i<3;i++){
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
			this.getPiece(xDest, yDest).setPlayer(true);
			boolean mange = false;
			while(mange == false){
				int y = 1 +(int)(Math.random() * 2);
				int x = 1 + (int)(Math.random() * 2);
				if(posValid(x, y)){
					this.getPiece(xDest, yDest).setX(x);
					mange = true;
				//this.getPiece(xDest, yDest).setY(y);
				}
				// this.getPiece(xDest, yDest).setY(yDest - 1);

				//listePieces.remove(getPiece(xDest, yDest));
				// bouger la piece
				pMove.setX(xDest);
				pMove.setY(yDest);
			}
			}
		else{
			System.out.println("Attaque d'un allier");
		}
	}

	public void choosePiece() {
		boolean cherch = true;
		int alea = 0;
		int i=0;
		while(cherch && i<listePieces.size()){
        	alea = (int)(Math.random() * (listePieces.size() - 1 - i));
			if(!listePieces.get(alea).isPlayer()){
				cherch = false;
				//Récupere une piece aléatoirement sur le plateau puis effectue un moveAlea disponible a celle ci
				listePieces.get(alea).moveAlea(this);
			}
			listePieces.add(listePieces.get(alea));
			listePieces.remove(alea);
			i++;
		}
    }

	public boolean autorized(Piece pMove, int xDest, int yDest){
		boolean valid = false;
		if(pMove != null){
			for(Position p : pMove.getPossibleMoves(this)){
				if(p.getX() == xDest && p.getY() == yDest){
					valid = true;
				}
			}
		}
		return valid;
	}
}
