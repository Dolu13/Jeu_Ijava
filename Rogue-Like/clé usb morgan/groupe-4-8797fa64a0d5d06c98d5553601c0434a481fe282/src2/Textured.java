import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
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
		try (BufferedReader bufferedreader = new BufferedReader(new FileReader("../data/textures/textures.ascii"))) {
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
	
	public static String loadMap(String mapName) {
		String res = "";
		try (BufferedReader bufferedreader = new BufferedReader(new FileReader("../data/maps/"+mapName+".lvl"))) {
			String strCurrentLine;
			while ((strCurrentLine = bufferedreader.readLine()) != null) {
				res += strCurrentLine;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		Scanner sc = new Scanner(res);
		sc.useDelimiter(";");
		String title = sc.next();
		return sc.next();
	}
	
	public HashMap<Character, String[]> getTextures() { return this.textures; }
	
	public static ArrayList<String> getMap(String mapName) {
		Textured tx = new Textured();
		HashMap<Character, String[]> textures = tx.getTextures();
		String map = Textured.loadMap(mapName);
		
		ArrayList<String> texturedMap = new ArrayList<String>();
		for (int i=0; i < map.length(); i++) {
			if (map.charAt(i) == '-') {
				texturedMap.add("");
				texturedMap.add("");
				texturedMap.add("");
			}
		}
		
		int idx = 0;		
		for (int i=0; i<map.length(); i++) {
			if (map.charAt(i) == '-') {
				idx++;
			}
			for (int rowIdx=0; rowIdx<3; rowIdx++) {
				if (map.charAt(i) != '-') {
					texturedMap.set(
						rowIdx+3*idx,
						texturedMap.get(rowIdx+3*idx)+
						textures.get(map.charAt(i))[rowIdx]
					);
				}
			}
		}
		/*int x = 9;
		int y = 2;
		texturedMap.set(y*3+1, texturedMap.get(y*3+1).substring(0, x*3+1)+"x"+texturedMap.get(y*3+1).substring(x*3+1+1));*/
		return texturedMap;
	}
	
	public static void main(String[] args) {
		System.out.println(String.join("\n", Textured.getMap("map1")));
	}
	
}
