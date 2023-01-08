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
    public TREE tree1;
    public HashSet<Entry<Integer,Integer>> trees_xy = new HashSet<>();
    public ArrayList<TREE> treeslist = new ArrayList<TREE>();
    //public ArrayList<String> fruits = new ArrayList<>();
    public TREES(String input_file){ // если вписать строку, будет пытаться найти файл

    }
    public int rnd(int min, int max){
        int randomNum = ThreadLocalRandom.current().nextInt(1, 3 + 1);
        return randomNum;
    }
    public TREES(Integer count_tree){ //если вписать число, будет генерировать случайные деревья
        //Entry<Integer,Integer> pair1 = new SimpleEntry<>(1,2);
        //Entry<Integer,Integer> pair2 = new SimpleEntry<>(3,4);
        //trees.add(pair1);
        //trees.add(pair2);
        while(trees_xy.size()<count_tree) {
            Entry<Integer,Integer> pair = new SimpleEntry<>(rnd(0, 6), rnd(0, 6));
            trees_xy.add(pair);
        }
        for (Entry<Integer,Integer>xy : trees_xy) {
            System.out.println(xy);
            TREE new_tree = new TREE(new GridPoint2(xy.getKey(), xy.getValue()));
            treeslist.add(new_tree);
        }
        treeslist.add(new TREE(new GridPoint2(rnd(0, 6), rnd(0, 6))));
    }

}
