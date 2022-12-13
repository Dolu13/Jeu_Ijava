import java.util.ArrayList;

class Main{
    static ArrayList<Map> listMap = new ArrayList<>();

    public static void main(String[] args){
        listMap.add(new Map(Map.loadMap("map1.lvl")));
        for(int i=0;i<listMap.size();i++){
            listMap.get(i).addEnnemisAlea();
        }
        System.out.println(listMap.get(0).showMap());
    }
}