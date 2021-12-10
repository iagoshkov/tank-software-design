package ru.mipt.bit.platformer;

import com.badlogic.gdx.math.GridPoint2;
import org.junit.jupiter.api.Test;
import ru.mipt.bit.platformer.generators.FileParser;

import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class FileParserTest {

//    @Test
//    void calculateTreeCoordinates_ShouldGenerateEmptyList() {
//        List<String> lines = new ArrayList<>();
//        lines.add("___X______");
//        lines.add("__________");
//        lines.add("_____XX___");
//        lines.add("X________X");
//
//        List<GridPoint2> result = new ArrayList<>();
//
//        assertEquals(result, FileParser.calculateTreeCoordinates(lines));
//    }
//
//    @Test
//    void calculateTreeCoordinates_ShouldCalculateGivenCoordinates() {
//        List<String> lines = new ArrayList<>();
//        lines.add("___T______");
//        lines.add(0, "__________");
//        lines.add(0, "____TTT___");
//        lines.add(0, "T________T");
//
//        List<GridPoint2> result = new ArrayList<>();
//        result.add(new GridPoint2(0, 0));
//        result.add(new GridPoint2(9, 0));
//        result.add(new GridPoint2(4, 1));
//        result.add(new GridPoint2(5, 1));
//        result.add(new GridPoint2(6, 1));
//        result.add(new GridPoint2(3, 3));
//
//        assertEquals(result, FileParser.calculateTreeCoordinates(lines));
//    }
//
//    @Test
//    void calculateTankCoordinates_ShouldGenerateEmptyList() {
//        List<String> lines = new ArrayList<>();
//        lines.add("___TTTT___");
//        lines.add("__________");
//        lines.add("_____TT___");
//        lines.add("T________T");
//
//        List<GridPoint2> result = new ArrayList<>();
//
//        assertEquals(result, FileParser.calculateTankCoordinates(lines));
//    }
//
//    @Test
//    void calculateTankCoordinates_ShouldCalculateGivenCoordinates(){
//        List<String> lines = new ArrayList<>();
//        lines.add("___X______");
//        lines.add(0, "__________");
//        lines.add(0, "____XXX___");
//        lines.add(0, "X________X");
//
//        List<GridPoint2> result = new ArrayList<>();
//        result.add(new GridPoint2(0, 0));
//        result.add(new GridPoint2(9, 0));
//        result.add(new GridPoint2(4, 1));
//        result.add(new GridPoint2(5, 1));
//        result.add(new GridPoint2(6, 1));
//        result.add(new GridPoint2(3, 3));
//
//        assertEquals(result, FileParser.calculateTankCoordinates(lines));
//    }
}