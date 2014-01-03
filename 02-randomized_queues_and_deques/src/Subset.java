public class Subset {
    public static void main(String[] args) {
        if (args.length != 1) {
            return;
        }

        int k = Integer.parseInt(args[0]);

        RandomizedQueue<String> rq = new RandomizedQueue<String>();
        String[] strings = StdIn.readAllStrings();

        for (String s : strings) {
            rq.enqueue(s);
        }

        while (!rq.isEmpty() && --k >= 0) {
            StdOut.println(rq.dequeue());
        }
    }
}
