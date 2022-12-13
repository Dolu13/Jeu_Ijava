import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Scanner;
import java.io.Console;
import java.util.Timer;
import java.util.TimerTask;

public class Scenario {

	public static int cols;
	public static int rows;
	static ArrayList<Map> listMap = new ArrayList<>();
	
	public static void main(String[] args) {
		/*
		int titleClicked = 0;

		if (args.length > 1) {
			cols = Integer.parseInt(args[0]);
			rows = Integer.parseInt(args[1]) - 1;
		} else {
			//System.exit(0);
		} 
		while (titleClicked == 0) {
			titleClicked = 1;
            terminal.nextLine().sleep(1000);
			System.out.println("TITLE");
		}*/
		
		/*
		Pion p1 = new Pion(1, 1);
		while (true) {
			Scenario.showMap(5, p1);
			Scanner sc = new Scanner(System.in);
			System.out.print("Mouvement : ");
			switch(sc.next()) {
				case "up":
					System.out.println("up-");
					p1.setY(p1.getY()-1);
					break;
				case "down":
					System.out.println("down-");
					p1.setY(p1.getY()+1);
					break;
				case "left":
					System.out.println("left-");
					p1.setX(p1.getX()-1);
					break;
				case "right":
					System.out.println("right-");
					p1.setX(p1.getX()+1);
					break;
				default:
					System.out.println("Mouvement inconnu");
					break;
			}
		}
		
		*/
		/*
		
		String mapString = "Level 1,10,6;"
				+ "##########;"
				+ "#........#;"
				+ "#..#.....#;"
				+ "#........#;"
				+ "#.....#..#;"
				+ "##########;";
		
		Scanner sc = new Scanner(mapString);
		sc.useDelimiter(";");
		ArrayList<String> map2 = new ArrayList<String>();
		String firstline = "";
		if (sc.hasNext()) {
			firstline = sc.next();
		}
		while (sc.hasNext()) {
			map2.add(sc.next());
		}
		// x = 2
		// y = 3
		int x = 3;
		int y = 5;
		map2.set(y, map2.get(y).substring(0, x)+"a"+map2.get(y).substring(x+1, map2.get(y).length()));
		
		
		
		
		for (int i=0; i<map2.size(); i++) {
			for (int ii=0; ii<map2.get(i).length(); ii++) {
				System.out.print(map2.get(i).charAt(ii));
			}
			System.out.println("\n");
		}
		System.out.println(firstline);
		
		*/



		
		//Piece p1 = new Tour(1, 2, true);
		/*Piece p2 = new Pion(1, 1, false);
		Piece p3 = new Pion(6, 2, false);*/
		

		listMap.add(new Map(Map.loadMap("map1.lvl")));
		listMap.add(new Map(Map.loadMap("map2.lvl")));
		Map map = listMap.get(0);

		Piece joueur = new Reine(1, 2, true);
		map.addPiece(joueur);

        for(int i=0;i<listMap.size();i++){
            listMap.get(i).addEnnemisAlea();
        }
		
		boolean run = true;
		while (run) {
			System.out.println(map.showMap());
			System.out.println("Quel pion souhaitez vous choisir ?");
			Scanner sc = new Scanner(System.in);
			Integer x = null;
			Integer y = null;
			Integer xDest = null;
			Integer yDest = null;
	
			do {
				if (x != null && y != null) {
					if (map.isOutOfBounds(x, y)) {
						System.out.println("Hors de la carte, veuillez réessayer.");
					} else {
						System.out.println("Aucun pion à cet endroit, veuillez réessayer.");
					}
					x = null;
					y = null;
				}
				
				while (x == null) {
					try {
						System.out.print("Position x : ");
						x = Integer.valueOf(sc.next());
					} catch (NumberFormatException e) {
						System.out.println("Ce n'est pas une position correcte, veuillez réessayer");
					}
				}
				while (y == null) {
					try {
						System.out.print("Position y : ");
						y = Integer.valueOf(sc.next());
					} catch (NumberFormatException e) {
						System.out.println("Ce n'est pas une position correcte, veuillez réessayer");
					}
				}
			} while (map.isOutOfBounds(x, y) || !map.placeTook(x, y));


			// Il faut etre dans la map et qu'il y ait bien un pion
			boolean moved = false;
			boolean error = false;
			if (map.getPiece(x, y) != null) {

				map.setPossibleMoves(map.getPiece(x, y).getPossibleMoves(map));
				map.setPossibleEats(map.getPiece(x, y).getPossibleEats(map));
				System.out.println(map.showMap());

				// Demande de la destination
				do {
					if (x != null && yDest != null) {
						if (map.isOutOfBounds(xDest, yDest)) {
							System.out.println("Hors de la carte, veuillez réessayer.");
						}
						xDest = null;
						yDest = null;
					}
					
					while (xDest == null) {
						try {
							System.out.print("Deplacement xDest : ");
							xDest = Integer.valueOf(sc.next());
						} catch (NumberFormatException e) {
							System.out.println("Ce n'est pas un déplacement correcte, veuillez réessayer");
						}
					}
					while (yDest == null) {
						try {
							System.out.print("Deplacement yDest : ");
							yDest = Integer.valueOf(sc.next());
						} catch (NumberFormatException e) {
							System.out.println("Ce n'est pas un déplacement correcte, veuillez réessayer");
						}
					}
				} while (map.isOutOfBounds(xDest, yDest));
				
				map.setSelected(map.getPiece(x, y));
				while (!moved && !error) {/*
					System.out.println("Quel mouvement voulez vous faire ?");
					System.out.print("Mouvement : ");
					String move = sc.next();
					switch(move) {
						case "up":
							if (map.isOutOfBounds(map.getSelected().getX(), map.getSelected().getY()-1)) {
								System.out.println("Hors de la carte, veuillez réessayer.");
							} else if (map.placeTook(map.getSelected().getX(), map.getSelected().getY()-1)) {
								map.eatPiece(map.getSelected(), map.getSelected().getX(), map.getSelected().getY()-1);
								moved = true;
							} else {
								map.getSelected().setY(map.getSelected().getY()-1);
								moved = true;
							}
							break;
						case "down":
							if (map.isOutOfBounds(map.getSelected().getX(), map.getSelected().getY()+1)) {
								System.out.println("Hors de la carte, veuillez réessayer.");
							} else if (map.placeTook(map.getSelected().getX(), map.getSelected().getY()+1)) {
								map.eatPiece(map.getSelected(), map.getSelected().getX(), map.getSelected().getY()+1);
								moved = true;
							} else {
								map.getSelected().setY(map.getSelected().getY()+1);
								moved = true;
							}
							break;
						case "left":
							if (map.isOutOfBounds(map.getSelected().getX()-1, map.getSelected().getY())) {
								System.out.println("Hors de la carte, veuillez réessayer.");
							} else if (map.placeTook(map.getSelected().getX()-1, map.getSelected().getY())) {
								map.eatPiece(map.getSelected(), map.getSelected().getX()-1, map.getSelected().getY());
								moved = true;
							} else {
								map.getSelected().setX(map.getSelected().getX()-1);
								moved = true;
							}
							break;
						case "right":
							if (map.isOutOfBounds(map.getSelected().getX()+1, map.getSelected().getY())) {
								System.out.println("Hors de la carte, veuillez réessayer.");
							} else if (map.placeTook(map.getSelected().getX()+1, map.getSelected().getY())) {
								map.eatPiece(map.getSelected(), map.getSelected().getX()+1, map.getSelected().getY());
								moved = true;
							} else {
								map.getSelected().setX(map.getSelected().getX()+1);
								moved = true;
							}
							break;
						default:
							System.out.println("Mouvement inconnu");
							break;*/
						if (map.isOutOfBounds(xDest, yDest)) {
							System.out.println("Hors de la carte, veuillez réessayer.");
							error = true;
						} else if (map.placeTook(xDest, yDest)) {
							if(map.map.get(yDest).charAt(xDest) == 'E'){
								System.out.println("Sortie par la porte");
								moved = true;
								Map tmpMap = listMap.get(listMap.indexOf(map)+1);
								for(Piece p : map.listePieces){
									if(p.isPlayer()){
										tmpMap.listePieces.add(p);
									}
								}
								map = tmpMap;
							}else{
								map.eatPiece(map.getSelected(), xDest, yDest);
								moved = true;
							}
						} else if(map.autorized(map.getSelected(), xDest, yDest)){
							map.getSelected().setX(xDest);
							map.getSelected().setY(yDest);
							moved = true;
						} else {
							System.out.println("Ce déplacement vous est impossible ^_^");
							error = true;
						}
						
					
				
				map.setSelected(null);
				map.setPossibleMoves(new ArrayList<Position>());
				map.setPossibleEats(new ArrayList<Position>());
			}/* else (){
				System.out.println("Pièce null");
				x = null;
				y = null;*/
			}
			if(moved){
				map.choosePiece();
			}
		}
	}
}
