package ru.mipt.bit.platformer;

import com.badlogic.gdx.math.GridPoint2;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.concurrent.ThreadLocalRandom;
import java.util.Map.Entry;
import java.util.AbstractMap.SimpleEntry;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;


public class TREES {
    public HashSet<Entry<Integer,Integer>> trees_xy = new HashSet<>();
    public ArrayList<TREE> treeslist = new ArrayList<TREE>();
    public TREES(String input_file){ // если вписать строку, будет пытаться найти файл

    }
    public int rnd(int min, int max){
        int randomNum = ThreadLocalRandom.current().nextInt(min, max + 1);
        return randomNum;
    }
    private void create_rnd_xy_list(Integer count_tree) {
        while(trees_xy.size()<count_tree) {
            System.out.println("create xy");
            Entry<Integer,Integer> pair = new SimpleEntry<>(rnd(0, 9), rnd(0, 7));
            trees_xy.add(pair);
        }
    }
    public TREES(Integer count_tree){ //если вписать число, будет генерировать случайные деревья
        create_rnd_xy_list(count_tree);

        for (Entry<Integer,Integer> xy : trees_xy) {
            //System.out.println("create tree");
            //System.out.println(xy);
            TREE new_tree = new TREE(new GridPoint2(xy.getKey(), xy.getValue()));
            treeslist.add(new_tree);
        }
    }

}
