import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Scanner;

public class Textured {

	public HashMap<Character, String[]> textures;
	public ArrayList<String> map;
	
	public Textured() {
		this.textures = new HashMap<Character, String[]>();
		this.loadTextures();
	}
	
	public void loadTextures() {
		String res = "";
		try (BufferedReader bufferedreader = new BufferedReader(new FileReader("../data/textures/textures2.ascii"))) {
			String strCurrentLine;
			while ((strCurrentLine = bufferedreader.readLine()) != null) {
				res += strCurrentLine;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		Scanner sc1 = new Scanner(res);
		sc1.useDelimiter(";");
		while (sc1.hasNext()) {
			Scanner sc2 = new Scanner(sc1.next());
			sc2.useDelimiter("=");
			char key = sc2.next().charAt(0);
			Scanner sc3 = new Scanner(sc2.next());
			sc3.useDelimiter(",");
			this.textures.put(key, new String[]{sc3.next(), sc3.next(), sc3.next()});
		}
	}
	
	public static ArrayList<String> loadMap(String mapName) {
		ArrayList<String> map = new ArrayList<String>();
		try (BufferedReader bufferedreader = new BufferedReader(new FileReader("../data/maps/"+mapName+".lvl"))) {
			String strCurrentLine;
			while ((strCurrentLine = bufferedreader.readLine()) != null) {
				map.add(strCurrentLine);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}
	
	public HashMap<Character, String[]> getTextures() { return this.textures; }
	
	public static ArrayList<String> getMap(Map map) {
		Textured tx = new Textured();
		HashMap<Character, String[]> textures = tx.getTextures();
		
		ArrayList<String> texturedMap = new ArrayList<String>();
		for (int i=0; i < map.getMap().size(); i++) {
			texturedMap.add("");
			texturedMap.add("");
			texturedMap.add("");
		}
		
		ArrayList<String> xHeader = new ArrayList<String>();
		xHeader.add(" ");
		xHeader.add(" ");
		xHeader.add(" ");
		
		ArrayList<Piece> pieces = map.getListPieces();
		Collections.sort(pieces);
		int pieceIdx = 0;
		
		ArrayList<Position> moves = map.getPossibleMoves();
		Collections.sort(moves);
		int moveIdx = 0;
		
		ArrayList<Position> eats = map.getPossibleEats();
		Collections.sort(eats);
		int eatIdx = 0;
		
		for (int lig=0; lig<map.getMap().size(); lig++) {
			// Affichage du Y
				texturedMap.set(0+3*lig, "    ");
				texturedMap.set(1+3*lig, " "+(lig==0 || lig == map.getMap().size()-1?"  ":(lig>9?lig:" "+lig))+" ");
				texturedMap.set(2+3*lig, "    ");
			//
			for (int col=0; col<map.getMap().get(lig).length(); col++) {
				// Affichage du X
					if (lig == 0) {
						int tempCol = col-2;
						if (tempCol < 0) { tempCol = 0; }
						xHeader.set(0, xHeader.get(0)+" "+(tempCol/10==0 || tempCol == map.getMap().get(lig).length()-3?" ":tempCol/10)+" ");
						xHeader.set(1, xHeader.get(1)+" "+((tempCol/10==0 && tempCol%10==0) || tempCol == map.getMap().get(lig).length()-3?" ":tempCol%10)+" ");
						xHeader.set(2, xHeader.get(2));
					}
				//
				String before = "";
				String after = "";
				if (map.getPiece(col, lig) != null && !map.getPiece(col, lig).isPlayer()) {
					before = "\u001B[31m";
					after = "\u001B[0m";
				}
				if (pieces.size() > pieceIdx && pieces.get(pieceIdx).getX() == col && pieces.get(pieceIdx).getY() == lig) {
					// Il y a une pièce sur la case
					boolean eatable = false;
					String[] pieceSave = null;
					if (eats.size() > eatIdx && eats.get(eatIdx).getX() == col && eats.get(eatIdx).getY() == lig) {
						// Possibilité de manger
						eatable = true;
						char middleChar = textures.get(pieces.get(pieceIdx).getTypePiece().getCarac())[1].charAt(1);
						pieceSave = textures.get(pieces.get(pieceIdx).getTypePiece().getCarac());
						String[] piece = pieceSave.clone();
						piece[1] = piece[1].charAt(0)+"x"+piece[1].charAt(2);						
						textures.put((Character) pieces.get(pieceIdx).getTypePiece().getCarac(), piece);
					}
					for (int rowIdx=0; rowIdx<3; rowIdx++) {
						texturedMap.set(
							rowIdx+3*lig,
							texturedMap.get(rowIdx+3*lig)+
							before+
							textures.get(pieces.get(pieceIdx).getTypePiece().getCarac())[rowIdx]
							+after
						);
					}
					if (eatable) {
						textures.put((Character) pieces.get(pieceIdx).getTypePiece().getCarac(), pieceSave);
					}
					pieceIdx++;
				} else if (moves.size() > moveIdx && moves.get(moveIdx).getX() == col && moves.get(moveIdx).getY() == lig) {
					// Il y a un déplacement possible
					for (int rowIdx=0; rowIdx<3; rowIdx++) {
						texturedMap.set(
							rowIdx+3*lig,
							texturedMap.get(rowIdx+3*lig)+
							textures.get('o')[rowIdx]
						);
					}
					moveIdx++;
				} else {
					for (int rowIdx=0; rowIdx<3; rowIdx++) {
						texturedMap.set(
							rowIdx+3*lig,
							texturedMap.get(rowIdx+3*lig)+
							textures.get(map.getMap().get(lig).charAt(col))[rowIdx]
						);
					}
				}
			}
		}
		
		ArrayList<String> finalMap = new ArrayList<String>();
		finalMap.addAll(xHeader);
		finalMap.addAll(texturedMap);
		return finalMap;
	}	
}
