package ru.mipt.bit.platformer;

import com.badlogic.gdx.math.GridPoint2;

import java.io.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.concurrent.ThreadLocalRandom;
import java.util.Map.Entry;
import java.util.AbstractMap.SimpleEntry;
import java.util.Scanner;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;


public class TREES {
    public HashSet<Entry<Integer,Integer>> trees_xy = new HashSet<>();
    public ArrayList<TREE> treeslist = new ArrayList<TREE>();
    public ArrayList<String> level_lines_txt = new ArrayList<String>();
    public TREES(String input_file){ // если вписать строку, будет пытаться найти файл

    }
    public int rnd(int min, int max){
        int randomNum = ThreadLocalRandom.current().nextInt(min, max + 1);
        return randomNum;
    }
    private void create_rnd_xy_list(Integer count_tree) {
        while(trees_xy.size()<count_tree) {
            System.out.println("create rnd xy");
            Entry<Integer,Integer> pair = new SimpleEntry<>(rnd(0, 9), rnd(0, 7));
            trees_xy.add(pair);
        }
    }
    private void create_treeslist_from_xylist() {
        for (Entry<Integer,Integer> xy : trees_xy) {
            TREE new_tree = new TREE(new GridPoint2(xy.getKey(), xy.getValue()));
            treeslist.add(new_tree);
        }
    }
    public TREES(boolean generate_from_file, Integer ... count_tree_for_rnd_way) throws FileNotFoundException { //если вписать число, будет генерировать случайные деревья
        if (generate_from_file) {
            SaveLevelFromTxt("level.txt");
        } else {
            create_rnd_xy_list(count_tree_for_rnd_way[0]);
        }

        create_treeslist_from_xylist();
    }
    public void TREES2(String file_path) throws FileNotFoundException { //если вписать строчку, будет считывать файл
        SaveLevelFromTxt(file_path);

        //create_treeslist_from_xylist();
    }
    public void SaveLevelFromTxt(String file_name) throws FileNotFoundException {

        File global_path = new File(new File(new File(new File(System.getProperty("user.dir"), "src"), "main"), "resources"), file_name);
        System.out.println(global_path.getAbsolutePath());
        if (global_path.exists())
            System.out.println("File is present.");
        else
            System.out.println("File is not present"); // файл отсутствуе
        Scanner scanner = new Scanner(global_path);
        Integer y = 7;
        while (scanner.hasNextLine()) {
            String line_txt = scanner.nextLine();
            for (int x = 0; x < line_txt.length(); x++) {
                if (line_txt.charAt(x) == 'T') {
                    TREE new_tree = new TREE(new GridPoint2(x, y));
                    treeslist.add(new_tree);
                }
            }
            level_lines_txt.add(0, line_txt);
            y -= 1;
            System.out.println(line_txt);
        }
        scanner.close();
        System.out.println(level_lines_txt);
    }
}
