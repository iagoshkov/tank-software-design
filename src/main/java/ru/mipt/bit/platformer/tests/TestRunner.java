package ru.mipt.bit.platformer.tests;

public class TestRunner {
    public static void main(String[] args) {
        System.out.println("Running tests...");
        
        int passed = 0;
        int failed = 0;
        
        // LevelTest
        try {
            new LevelTest().testIsPositionValid();
            System.out.println("✓ LevelTest.testIsPositionValid passed");
            passed++;
        } catch (AssertionError e) {
            System.out.println("✗ LevelTest.testIsPositionValid failed: " + e.getMessage());
            failed++;
        }
        
        try {
            new LevelTest().testPositionOccupancy();
            System.out.println("✓ LevelTest.testPositionOccupancy passed");
            passed++;
        } catch (AssertionError e) {
            System.out.println("✗ LevelTest.testPositionOccupancy failed: " + e.getMessage());
            failed++;
        }
        
        // DirectionTest
        try {
            new DirectionTest().testGetNextPosition();
            System.out.println("✓ DirectionTest.testGetNextPosition passed");
            passed++;
        } catch (AssertionError e) {
            System.out.println("✗ DirectionTest.testGetNextPosition failed: " + e.getMessage());
            failed++;
        }
        
        try {
            new DirectionTest().testGetRotation();
            System.out.println("✓ DirectionTest.testGetRotation passed");
            passed++;
        } catch (AssertionError e) {
            System.out.println("✗ DirectionTest.testGetRotation failed: " + e.getMessage());
            failed++;
        }
        
        // ConfigsTest
        try {
            new ConfigsTest().testPlayerConfigCreation();
            System.out.println("✓ ConfigsTest.testPlayerConfigCreation passed");
            passed++;
        } catch (AssertionError e) {
            System.out.println("✗ ConfigsTest.testPlayerConfigCreation failed: " + e.getMessage());
            failed++;
        }
        
        try {
            new ConfigsTest().testTreeConfigCreation();
            System.out.println("✓ ConfigsTest.testTreeConfigCreation passed");
            passed++;
        } catch (AssertionError e) {
            System.out.println("✗ ConfigsTest.testTreeConfigCreation failed: " + e.getMessage());
            failed++;
        }
        
        // GdxGameUtilsTest
        try {
            new GdxGameUtilsTest().testContinueProgress();
            System.out.println("✓ GdxGameUtilsTest.testContinueProgress passed");
            passed++;
        } catch (AssertionError e) {
            System.out.println("✗ GdxGameUtilsTest.testContinueProgress failed: " + e.getMessage());
            failed++;
        }
        
        // InputControllerTest
        try {
            new InputControllerTest().testPlayerInputController();
            System.out.println("✓ InputControllerTest.testPlayerInputController passed");
            passed++;
        } catch (AssertionError e) {
            System.out.println("✗ InputControllerTest.testPlayerInputController failed: " + e.getMessage());
            failed++;
        }
        
        try {
            new InputControllerTest().testAiInputController();
            System.out.println("✓ InputControllerTest.testAiInputController passed");
            passed++;
        } catch (AssertionError e) {
            System.out.println("✗ InputControllerTest.testAiInputController failed: " + e.getMessage());
            failed++;
        }
        
        // PlayerTest
        try {
            new PlayerTest().testPlayerMoveToNewDirection();
            System.out.println("✓ PlayerTest.testPlayerMoveToNewDirection passed");
            passed++;
        } catch (AssertionError e) {
            System.out.println("✗ PlayerTest.testPlayerMoveToNewDirection failed: " + e.getMessage());
            failed++;
        }
        
        // TankTest
        try {
            new TankTest().testTankMoveToNewDirection();
            System.out.println("✓ TankTest.testTankMoveToNewDirection passed");
            passed++;
        } catch (AssertionError e) {
            System.out.println("✗ TankTest.testTankMoveToNewDirection failed: " + e.getMessage());
            failed++;
        }
        
        System.out.println("\nTest Results:");
        System.out.println("Passed: " + passed);
        System.out.println("Failed: " + failed);
        System.out.println("Total: " + (passed + failed));
        
        if (failed == 0) {
            System.out.println("🎉 All tests passed!");
        } else {
            System.out.println("❌ Some tests failed!");
            System.exit(1);
        }
    }
}