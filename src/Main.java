import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        File file = new File("resources/task_sber.csv");
        List<City> cities = new ArrayList<>();
        try {
            Scanner scanner = new Scanner(file);
            while (scanner.hasNext()) {
                String temp = scanner.nextLine();
                String[] city = temp.split(";", -1);
                cities.add(new City(city[0], city[1], city[2], city[3], Integer.parseInt(city[4]), city[5]));
            }
        } catch (FileNotFoundException e) {
            System.out.println("File is not found");
        }


        System.out.println("Сортировка списка городов по наименованию в алфавитном порядке по убыванию без учета регистра");
        cities.stream()
                .sorted(Comparator.comparing(City::getName, String::compareToIgnoreCase).reversed())
                .forEach(System.out::println);
        System.out.println("- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -");
        System.out.println("Сортировка списка городов по федеральному округу и наименованию города внутри каждого "
                + " федерального округа в алфавитном порядке по убыванию с учетом регистра");
        cities.stream()
                .sorted(
                        Comparator.comparing(City::getDistrict, String::compareTo)
                                .thenComparing(City::getName).reversed())
                .forEach(System.out::println);

        System.out.println("- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - ");

        System.out.println(" необходимо преобразовать список городов в массив." +
                "А затем путем перебора массива найти индекс элемента и значение с наибольшим количеством жителей города");
        City[] arrCities = cities.toArray(new City[0]);

        int index = -1;
        int maxPopulation = Integer.MIN_VALUE;

        for (int i = 0; i < arrCities.length; i++) {
            int population = arrCities[i].getPopulation();
            if (population > maxPopulation) {
                maxPopulation = population;
                index = i;
            }
        }
        System.out.printf("[%d] = %d", index, maxPopulation);
        System.out.println("");

        System.out.println("- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - ");

        System.out.println("реализовать поиск количества городов в разрезе регионов." +
                "Необходимо определить количество городов в каждом регионе");
        var map = cities.stream().collect(Collectors.groupingBy(City::getRegion, Collectors.counting()));
        for (Map.Entry<String, Long> entry : map.entrySet()) {
            System.out.println(entry.getKey() + " - " + entry.getValue());
        }

    }

    public static class City {

        private final String ID;

        private String name;

        private String region;

        private String district;

        private int population;

        private String date;

        public City(String ID, String name, String region, String district, int population, String date) {
            this.ID = ID;
            this.name = name;
            this.region = region;
            this.district = district;
            this.population = population;
            this.date = date;
        }

        public String getName() {
            return name;
        }

        public String getRegion() {
            return region;
        }

        public String getDistrict() {
            return district;
        }

        public int getPopulation() {
            return population;
        }

        @Override
        public String toString() {
            return "City{" +
                    "ID='" + ID + '\'' +
                    ", name='" + name + '\'' +
                    ", region='" + region + '\'' +
                    ", district='" + district + '\'' +
                    ", population=" + population +
                    ", date='" + date + '\'' +
                    '}';
        }
    }
}
