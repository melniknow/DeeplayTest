import java.io.IOException;
import java.util.ArrayList;

public class Solution {
    private static final int MAP_SIZE = 4; // Размер квадратной карты

    public static int getResult(String map, String person) throws IOException {
        var gamePerson = getGamePerson(person);
        var gameMap = getGameMapWithWeight(map, gamePerson);
        var weightMatrix = getWeightMatrix(gameMap);

        return getMinimumDistanceByDeikstraAlgorithm(weightMatrix);
    }

    private static int getMinimumDistanceByDeikstraAlgorithm(int[][] gameMap) {
        var result = 0;
        var distArr = new int[MAP_SIZE * MAP_SIZE]; // Массив расстояний

        var startVertex = 0; // Вершина от которой мы будем строить минимальные растояния
        distArr[startVertex] = 0;

        var vertexSet = new ArrayList<Integer>();

        for (int i = 1; i < MAP_SIZE * MAP_SIZE; i++) {
            vertexSet.add(i);
            distArr[i] = gameMap[startVertex][i];
        }

        while (!vertexSet.isEmpty()) {
            var anyVertex = -1;
            var minDistForIter = Integer.MAX_VALUE;
            for (Integer vertex : vertexSet) {
                if (distArr[vertex] < minDistForIter) {
                    minDistForIter = distArr[vertex];
                    anyVertex = vertex;
                }
            }

            vertexSet.remove(vertexSet.lastIndexOf(anyVertex));

            for (Integer vertex : vertexSet) { // Перерасчёт меток и растояний для итерации
                if (!(distArr[anyVertex] == Integer.MAX_VALUE || gameMap[anyVertex][vertex] == Integer.MAX_VALUE) && distArr[anyVertex] + gameMap[anyVertex][vertex] < distArr[vertex]) { // Защита от переполнения int
                    distArr[vertex] = distArr[anyVertex] + gameMap[anyVertex][vertex];
                }
                if (vertex == MAP_SIZE * MAP_SIZE - 1) result = distArr[vertex];
            }
        }

        return result;
    }

    private static int[][] getWeightMatrix(int[] gameMap) { // Строим весовую матрицу
        var weightMatrix = new int[MAP_SIZE * MAP_SIZE][MAP_SIZE * MAP_SIZE];

        for (int i = 0; i < MAP_SIZE * MAP_SIZE; i++) {
            for (int j = 0; j < MAP_SIZE * MAP_SIZE; j++) {
                if (i == j) {
                    weightMatrix[i][j] = Integer.MAX_VALUE;
                } else {
                    if (Math.abs(i - j) == 4 || (i % MAP_SIZE != MAP_SIZE - 1 && j == i + 1) || (i % MAP_SIZE != 0 && j == i - 1)) // Проверка на то, можно ли пеерйти на клетку j с клетки i
                        weightMatrix[i][j] = gameMap[j];
                    else weightMatrix[i][j] = Integer.MAX_VALUE;
                }
            }
        }
        return weightMatrix;
    }

    private static int[] getGameMapWithWeight(String map, Person person) throws IOException {
        if (map == null || map.length() != MAP_SIZE * MAP_SIZE) throw new IOException("Incorrect map size");
        var gameMap = new int[MAP_SIZE * MAP_SIZE];

        for (int i = 0; i < MAP_SIZE * MAP_SIZE; i++) {
            gameMap[i] = getMapValue(map.charAt(i), person);
        }

        return gameMap;
    }

    private static int getMapValue(char charAt, Person person) throws IOException {
        if (charAt == 'S') {
            if (person == Person.HUMAN) return 5;
            else if (person == Person.SWAMPER) return 2;
            else return 3;
        } else if (charAt == 'W') {
            if (person == Person.HUMAN) return 2;
            else if (person == Person.SWAMPER) return 2;
            else return 3;
        } else if (charAt == 'T') {
            if (person == Person.HUMAN) return 3;
            else if (person == Person.SWAMPER) return 5;
            else return 2;
        } else if (charAt == 'P') {
            if (person == Person.HUMAN) return 1;
            else if (person == Person.SWAMPER) return 2;
            else return 2;
        } else {
            throw new IOException("Incorrect map value");
        }
    }

    private static Person getGamePerson(String person) throws IOException {
        if (person == null) throw new IOException("Character can't be null");
        switch (person) {
            case "Human":
                return Person.HUMAN;
            case "Swamper":
                return Person.SWAMPER;
            case "Woodman":
                return Person.WOODMAN;
        }
        throw new IOException("Non-existing character");
    }
}
