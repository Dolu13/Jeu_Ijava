import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.FileReader;

public class Display {

    public int rows;
    public int cols;

    public Display(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
	}

    public static ArrayList<String> cropImage(ArrayList<String> image, int left, int right, int top, int bottom, char c) {
        int hImage = image.size();
        int wImage = image.get(0).length();
        int i;
        String newLine = "";

        for (i = 0; i < wImage; i++) {
            newLine += c;
        }
        if (top < 0) {
            for (i = 0; i < (0 - top) && i < hImage; i++) {
                image.remove(i);
            }
        } else if (top > 0) {
            for (i = 0; i < top; i++) {
                image.add(0, newLine);
            }
        }
        hImage += top;

        if (bottom < 0) {
            for (i = hImage + bottom - 1;i < hImage; i++) {
                image.remove(i);
            }
        } else if (bottom > 0) {
            for (i = 0; i < bottom; i++) {
                image.add(image.size(), newLine);
            }
        }
        hImage += bottom;

        if (left < 0) {
            for (i = 0;i < hImage; i++) {
                String tmp = image.get(i).substring(0 - left);
                image.remove(i);
                image.add(i, tmp);
            }
        } else if (left > 0) {
            for (i = 0; i < hImage; i++) {
                for (int j = 0; i < left; j++) {
                    String tmp = c + image.get(i);
                    image.remove(i);
                    image.add(i, tmp);
                }
            }
        }
        wImage += left;

        if (right < 0) {
            for (i = 0;i < hImage; i++) {
                String tmp = image.get(i).substring(0, wImage - right - 1);
                image.remove(i);
                image.add(i, tmp);
            }
        } else if (right > 0) {
            for (i = 0; i < hImage; i++) {
                for (int j = 0; i < right; j++) {
                    String tmp = image.get(i) + c;
                    image.remove(i);
                    image.add(i, tmp);
                }
            }
        }
        wImage += right;
        return image;
    }

    public static ArrayList<String> loadImage(String filename){
		ArrayList<String> result = new ArrayList<String>();
		try (BufferedReader bufferedreader = new BufferedReader(new FileReader(filename))) {
			String strCurrentLine;
			while ((strCurrentLine = bufferedreader.readLine()) != null) {
				result.add(strCurrentLine);
			}
		} catch (Exception e) {
			System.out.println("Erreur surement dû au Bug de l'an 2000...");
		}
		return result;
	}

}