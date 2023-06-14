package com.example.carbon;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.io.FileWriter;
import java.io.IOException;

class CreateInputFile {
    public static void main(String[] args) {
        String inputFileContent = "C - 3 - 4\n" +
                "M - 1 - 1\n" +
                "M - 2 - 2\n" +
                "T - 0 - 3 - 2\n" +
                "T - 1 - 3 - 1\n" +
                "A - Indiana - 1 - 1 - S - AADADA";

        String filePath = "input.txt";

        try (FileWriter writer = new FileWriter(filePath)) {
            writer.write(inputFileContent);
            System.out.println("input.txt file created successfully.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

class Map {
    int width;
    int height;
    List<Mountain> mountains;
    List<Treasure> treasures;
    List<Adventurer> adventurers;

    public Map(int width, int height) {
        this.width = width;
        this.height = height;
        this.mountains = new ArrayList<>();
        this.treasures = new ArrayList<>();
        this.adventurers = new ArrayList<>();
    }
}

class Mountain {
    int x;
    int y;

    public Mountain(int x, int y) {
        this.x = x;
        this.y = y;
    }
}

class Treasure {
    int x;
    int y;
    int count;

    public Treasure(int x, int y, int count) {
        this.x = x;
        this.y = y;
        this.count = count;
    }
}

class Adventurer {
    String name;
    int x;
    int y;
    String orientation;
    String sequence;
    int treasuresCollected;

    public Adventurer(String name, int x, int y, String orientation, String sequence) {
        this.name = name;
        this.x = x;
        this.y = y;
        this.orientation = orientation;
        this.sequence = sequence;
        this.treasuresCollected = 0;
    }
}

class TreasureTracker {
    public static void main(String[] args) {
        String inputFile = "C:\\Users\\User\\Desktop\\carbon\\carbonITExo\\EXO\\MAV\\MAVN\\demo\\src\\main\\java\\com\\example\\carbon\\input.txt";
        String outputFile = "output.txt";

        Map map = readInputFile(inputFile);
        simulateMovements(map);
        writeOutputFile(map, outputFile);
    }

    public static Map readInputFile(String filePath) {
        Map map = null;
        BufferedReader reader = null;

        try {
            reader = new BufferedReader(new FileReader(filePath));
            String line;

            while ((line = reader.readLine()) != null) {
                if (line.startsWith("#")) {
                    continue;
                }

                String[] parts = line.split("-");
                String identifier = parts[0].trim();

                if (identifier.equals("C")) {
                    int width = Integer.parseInt(parts[1].trim());
                    int height = Integer.parseInt(parts[2].trim());
                    map = new Map(width, height);
                } else if (identifier.equals("M")) {
                    int x = Integer.parseInt(parts[1].trim());
                    int y = Integer.parseInt(parts[2].trim());
                    map.mountains.add(new Mountain(x, y));
                } else if (identifier.equals("T")) {
                    int x = Integer.parseInt(parts[1].trim());
                    int y = Integer.parseInt(parts[2].trim());
                    int count = Integer.parseInt(parts[3].trim());
                    map.treasures.add(new Treasure(x, y, count));
                } else if (identifier.equals("A")) {
                    String name = parts[1].trim();
                    int x = Integer.parseInt(parts[2].trim());
                    int y = Integer.parseInt(parts[3].trim());
                    String orientation = parts[4].trim();
                    String sequence = parts[5].trim();
                    map.adventurers.add(new Adventurer(name, x, y, orientation, sequence));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return map;
    }

    public static void simulateMovements(Map map) {
        for (Adventurer adventurer : map.adventurers) {
            for (char action : adventurer.sequence.toCharArray()) {
                switch (action) {
                    case 'A':
                        moveForward(map, adventurer);
                        break;
                    case 'G':
                        turnLeft(adventurer);
                        break;
                    case 'D':
                        turnRight(adventurer);
                        break;
                }

                collectTreasure(map, adventurer);
            }
        }
    }

    public static void moveForward(Map map, Adventurer adventurer) {
        int x = adventurer.x;
        int y = adventurer.y;

        switch (adventurer.orientation) {
            case "N":
                y--;
                break;
            case "S":
                y++;
                break;
            case "E":
                x++;
                break;
            case "W":
                x--;
                break;
        }

        if (isValidPosition(map, x, y)) {
            adventurer.x = x;
            adventurer.y = y;
        }
    }

    public static void turnLeft(Adventurer adventurer) {
        switch (adventurer.orientation) {
            case "N":
                adventurer.orientation = "W";
                break;
            case "S":
                adventurer.orientation = "E";
                break;
            case "E":
                adventurer.orientation = "N";
                break;
            case "W":
                adventurer.orientation = "S";
                break;
        }
    }

    public static void turnRight(Adventurer adventurer) {
        switch (adventurer.orientation) {
            case "N":
                adventurer.orientation = "E";
                break;
            case "S":
                adventurer.orientation = "W";
                break;
            case "E":
                adventurer.orientation = "S";
                break;
            case "W":
                adventurer.orientation = "N";
                break;
        }
    }

    public static void collectTreasure(Map map, Adventurer adventurer) {
        int x = adventurer.x;
        int y = adventurer.y;

        for (Treasure treasure : map.treasures) {
            if (treasure.x == x && treasure.y == y && treasure.count > 0) {
                adventurer.treasuresCollected++;
                treasure.count--;
            }
        }
    }

    public static boolean isValidPosition(Map map, int x, int y) {
        if (x < 0 || x >= map.width || y < 0 || y >= map.height) {
            return false;
        }

        for (Mountain mountain : map.mountains) {
            if (mountain.x == x && mountain.y == y) {
                return false;
            }
        }

        return true;
    }

    public static void writeOutputFile(Map map, String filePath) {
        FileWriter writer = null;

        try {
            writer = new FileWriter(filePath);
            writer.write(String.format("C - %d - %d\n", map.width, map.height));

            for (Mountain mountain : map.mountains) {
                writer.write(String.format("M - %d - %d\n", mountain.x, mountain.y));
            }

            for (Treasure treasure : map.treasures) {
                writer.write(String.format("T - %d - %d - %d\n", treasure.x, treasure.y, treasure.count));
            }

            for (Adventurer adventurer : map.adventurers) {
                writer.write(String.format("A - %s - %d - %d - %s - %d\n", adventurer.name, adventurer.x, adventurer.y,
                        adventurer.orientation, adventurer.treasuresCollected));
            }

            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}