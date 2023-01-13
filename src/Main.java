import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Main {

    public static final Map<Integer, Integer> sizeToFreq = new HashMap<>();


    public static String generateRoute(String letters, int length) {
        Random random = new Random();
        StringBuilder route = new StringBuilder();
        for (int i = 0; i < length; i++) {
            route.append(letters.charAt(random.nextInt(letters.length())));
        }
        return route.toString();
    }

    public static void mapPut(Map<Integer, Integer> map, int i) {
        if (map.containsKey(i)) {
            int a = map.get(i);
            map.put(i, ++a);
        } else map.put(i, 1);
    }


    public static void main(String[] args) throws InterruptedException {

        ArrayList<Thread> threadList = new ArrayList<>();

        for (int i = 0; i < 1000; i++) {
            threadList.add(new Thread(() -> {
                int res = (int) generateRoute("RLRFR", 100).codePoints().filter(s -> s == 'R').count();
                synchronized (sizeToFreq) {
                    mapPut(sizeToFreq, res);
                }
            }));
        }
        for (Thread thread : threadList) {
            thread.start();
        }
        for (Thread thread : threadList) {
            thread.join();
        }

        for (Map.Entry<Integer, Integer> entry : sizeToFreq.entrySet()) {
            System.out.println(entry.getKey() + " (" + entry.getValue() + " раз)");
        }

    }
}
