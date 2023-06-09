import java.util.*;

public class Robot {
    public static final Map<Integer, Integer> sizeToFreq = new HashMap<>();
    static List<Thread> threads = new ArrayList<>();
    static final int MAX = 1000;

    public static void main(String[] args) throws InterruptedException {
        for (int i = 0; i < MAX; i++) {
            Runnable runnable = () -> {
                String texts = generateRoute("RLRFR", 100);
                int maxSize = 0;
                for (int j = 0; j < texts.length(); j++) {
                    if (texts.charAt(j) == 'R') {
                        maxSize++;
                    }
                }
                synchronized (sizeToFreq) {
                    if (sizeToFreq.containsKey(maxSize)) {
                        int count = 1 + sizeToFreq.get(maxSize);
                        sizeToFreq.replace(maxSize, count);
                    } else {
                        sizeToFreq.put(maxSize, 1);
                    }
                }
            };
            Thread myThread = new Thread(runnable);
            threads.add(myThread);
            myThread.start();
        }
        for (Thread thread : threads) {
            thread.join();
        }
        conclusion();
    }

    public static void conclusion (){
            System.out.println("Самое частое количество повторений "
                    + sizeToFreq.keySet().stream().max(Comparator.comparing(sizeToFreq::get)).orElse(null)
                    + " (встретилось " + Collections.max(sizeToFreq.values()) + " раз)");

            System.out.println("Другие размеры:");
            for (Map.Entry<Integer, Integer> value : sizeToFreq.entrySet()) {
                System.out.println("- " + value.getKey() + " (" + value.getValue() + " раз)");
            }
        }

    public static String generateRoute(String letters, int length) {
        Random random = new Random();
        StringBuilder route = new StringBuilder();
        for (int i = 0; i < length; i++) {
            route.append(letters.charAt(random.nextInt(letters.length())));
        }
        return route.toString();
    }

}
