import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class A4_2019EE30564 {
    public static void main(String[] args) throws Exception {
        if (args.length < 2) {
            throw new Exception("Insufficient Arguments !!!");
        }
        String nodefile = args[0], edgesfile = args[1], function = args[2];

        HashMap<String,HashMap<String,Integer>> g = getGraph(nodefile);
        fillEdges(edgesfile, g);

        if (function.equals("average")) average(g);
        else if (function.equals("rank")) rank(g);
        else if (function.equals("independent_storylines_dfs")) independent_storylines_dfs(g);
        else throw new Exception("No such function exists");

//        HashMap<String,HashMap<String,Integer>> g = getGraph("nodes.csv");
//        fillEdges("edges.csv", g);
//        average(g);
//        rank(g);
//        independent_storylines_dfs(g);

    }

    private static HashMap<String,HashMap<String,Integer>> getGraph(String filepathname) {
        HashMap<String,HashMap<String,Integer>> graph = new HashMap<String,HashMap<String,Integer>>();
        String line = "";
        String splitBycomma = ",", splitbydoublequote = "\"";
        boolean firstline = true;
        try
        {
            BufferedReader br = new BufferedReader(new FileReader(filepathname));
            while ((line = br.readLine()) != null)   //returns a Boolean value
            {
                if (firstline) {
                    firstline = false;
                    continue;
                }
                String[] name = line.split(splitbydoublequote);
                if (name.length > 2) {
//                    System.out.println(name[3]);
                    graph.put(name[3],new HashMap<String,Integer>());
                }
                else {
                    name = line.split(splitBycomma);
//                    System.out.println(name[1]);
                    graph.put(name[1],new HashMap<String,Integer>());

                }
                //if (name.length > 2) System.out.println(name[2] + "," + name[3]);
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        return graph;
    }

    private static void fillEdges(String filename, HashMap<String,HashMap<String,Integer>> graph) {
        String line = "";
        String splitBycomma = ",", splitbydoublequote = "\"";
        String from = "", to = "";
        int weight = 0, count = 0;
        boolean firstline = true;
        try
        {
            BufferedReader br = new BufferedReader(new FileReader(filename));
            while ((line = br.readLine()) != null)   //returns a Boolean value
            {
                count += 1;
                if (firstline) {
                    firstline = false;
                    continue;
                }
                String[] name = line.split(splitbydoublequote);

                if (name.length == 1) {
                    name = line.split(splitBycomma);
                    from = name[0];
                    to = name[1];
                    weight = Integer.parseInt(name[2]);
//                    System.out.println(from);
//                    System.out.println(to);
//                    System.out.println(weight);

                    graph.get(from).put(to,weight);
                    graph.get(to).put(from,weight);
                }
                else if (name.length == 3) {
                    if (name[0].equals("")) {
                        from = name[1];
                        to = name[2].split(splitBycomma)[1];
                        weight = Integer.parseInt(name[2].split(splitBycomma)[2]);
//                        System.out.println(from);
//                        System.out.println(to);
//                        System.out.println(weight);
                        graph.get(from).put(to,weight);
                        graph.get(to).put(from,weight);
                    } else {
                        from = name[0].split(splitBycomma)[0];
                        to = name[1];
                        weight = Integer.parseInt(name[2].split(splitBycomma)[1]);
//                        System.out.println(from);
//                        System.out.println(to);
//                        System.out.println(weight);
                        graph.get(from).put(to,weight);
                        graph.get(to).put(from,weight);
                    }
                } else if (name.length == 5) {
                    from = name[1];
                    to = name[3];
                    weight = Integer.parseInt(name[4].split(splitBycomma)[1]);
//                    System.out.println(from);
//                    System.out.println(to);
//                    System.out.println(weight);
                    graph.get(from).put(to,weight);
                    graph.get(to).put(from,weight);
                }
            }
            //System.out.println(count + " lines");
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    private static void average(HashMap<String,HashMap<String,Integer>> graph) {
        double value = 0.0, avg = 0.0;
        for (Map.Entry<String, HashMap<String, Integer>> entry : graph.entrySet()) {
            String k = entry.getKey();
            HashMap<String, Integer> v = entry.getValue();
            value += v.size();
        }
        //System.out.println(graph.size() + " " + value);
        avg = value / graph.size();
        avg = Math.round(avg * 100);
        int k= (int) avg;
        System.out.println(k/100 +"."+k%100);
//        System.out.println(avg);
    }

    private static void rank(HashMap<String,HashMap<String,Integer>> graph) {
        tuple[] ord = new tuple[graph.size()];
        int i = 0;

        for (Map.Entry<String, HashMap<String, Integer>> entry : graph.entrySet()) {
            String k = entry.getKey();
            HashMap<String, Integer> v = entry.getValue();
            int co_occur = 0;
            for (Map.Entry<String, Integer> edge : v.entrySet()) {
                String to = edge.getKey();
                Integer co_oc = edge.getValue();
                co_occur += co_oc;
            }
            //System.out.println(k);
            ord[i] = new tuple(co_occur,k);
            ++i;
        }
        MergeSort.sort(ord, 0, ord.length-1);
        for (int j = 0; j < ord.length-1; j++) {
            System.out.print(ord[j].key + ",");
        }
        System.out.print(ord[ord.length-1].key);

    }

    private static void independent_storylines_dfs(HashMap<String,HashMap<String,Integer>> graph) {
        HashMap<String,Integer> visited = new HashMap<>();

        for (Map.Entry<String, HashMap<String, Integer>> entry : graph.entrySet()) {
            String k = entry.getKey();
            HashMap<String, Integer> v = entry.getValue();
            visited.put(k,0);
        }

        int i = 1;
        for (Map.Entry<String,Integer> entry : visited.entrySet()) {
            String k = entry.getKey();
            Integer v = entry.getValue();
            if (visited.get(k) == 0) {
                dfs(graph, visited, k, i);
                i += 1;
            }
        }

        process_connected_components(i-1, visited);
    }

//    static int j = 1;

    private static void dfs(HashMap<String,HashMap<String,Integer>> graph, HashMap<String,Integer> visited, String key, Integer i) {
        visited.replace(key, i);
//        System.out.println(key + " " + i + " " + j);
//        j++;

        for (Map.Entry<String,Integer> entry : graph.get(key).entrySet()) {
            String k = entry.getKey();
            Integer v = entry.getValue();
            if (visited.get(k) == 0) {
                dfs(graph, visited, k, i);
            }
        }

    }

    private static void process_connected_components(Integer i, HashMap<String,Integer> visited) {
        ArrayList<ArrayList<tuple>> cc = new ArrayList<>();

        for (int j = 0; j <= i; j++) {
            cc.add(new ArrayList<>());
        }

        for (Map.Entry<String,Integer> entry : visited.entrySet()) {
            String k = entry.getKey();
            Integer v = entry.getValue();
            cc.get(v).add(new tuple(0,k));
        }

        for (int j = 1; j <= i; j++) {
            MergeSort.sort2(cc.get(j),0, cc.get(j).size()-1);
        }


        ArrayList<tuple> final_ccc = new ArrayList<tuple>();
        for (int j = 1; j <= i; j++) {
            final_ccc.add(new tuple(cc.get(j).size() , cc.get(j).get(0).key));
        }

        MergeSort.sort2(final_ccc, 0, final_ccc.size()-1);

        for (int j = 0; j < i; j++) {
            int ind = visited.get(final_ccc.get(j).key);
            int k = 0;
            for (; k < cc.get(ind).size() - 1; k++) {
                System.out.print(cc.get(ind).get(k).key + ",");
            }
            System.out.println(cc.get(ind).get(k).key);
        }
    }

    private  static class tuple {
        public int value;
        public String key;

        public tuple(int value, String key) {
            this.value = value;
            this.key = key;
        }

        public static tuple compare(tuple t1, tuple t2) {
            if (t1.value > t2.value) return t1;
            else if (t1.value < t2.value) return t2;
            else if (t1.key.compareTo(t2.key) >= 0) return t1;
            else return t2;
        }


    }

    private static class MergeSort {
        // Merges two subarrays of arr[].
        // First subarray is arr[l..m]
        // Second subarray is arr[m+1..r]

        public static void merge(tuple arr[], int l, int m, int r) {
            // Find sizes of two subarrays to be merged
            int n1 = m - l + 1;
            int n2 = r - m;

            /* Create temp arrays */
            tuple L[] = new tuple[n1];
            tuple R[] = new tuple[n2];

            /*Copy data to temp arrays*/
            for (int i = 0; i < n1; ++i)
                L[i] = arr[l + i];
            for (int j = 0; j < n2; ++j)
                R[j] = arr[m + 1 + j];

            /* Merge the temp arrays */

            // Initial indexes of first and second subarrays
            int i = 0, j = 0;

            // Initial index of merged subarry array
            int k = l;
            while (i < n1 && j < n2) {
                if (L[i] == tuple.compare(L[i], R[j])) {
                    arr[k] = L[i];
                    i++;
                } else {
                    arr[k] = R[j];
                    j++;
                }
                k++;
            }

            /* Copy remaining elements of L[] if any */
            while (i < n1) {
                arr[k] = L[i];
                i++;
                k++;
            }

            /* Copy remaining elements of R[] if any */
            while (j < n2) {
                arr[k] = R[j];
                j++;
                k++;
            }
        }

        // Main function that sorts arr[l..r] using
        // merge()
        public static void sort(tuple arr[], int l, int r) {
            if (l < r) {
                // Find the middle point
                int m = (l + r) / 2;

                // Sort first and second halves
                sort(arr, l, m);
                sort(arr, m + 1, r);

                // Merge the sorted halves
                merge(arr, l, m, r);
            }
        }


        public static void merge2(ArrayList<tuple> arr, int l, int m, int r) {
            // Find sizes of two subarrays to be merged
            int n1 = m - l + 1;
            int n2 = r - m;

            /* Create temp arrays */
            ArrayList<tuple> L = new ArrayList<>();
            ArrayList<tuple> R = new ArrayList<>();

            /*Copy data to temp arrays*/
            for (int i = 0; i < n1; ++i)
                L.add(i, arr.get(l + i));
            for (int j = 0; j < n2; ++j)
                R.add(j, arr.get(m + 1 + j));

            /* Merge the temp arrays */

            // Initial indexes of first and second subarrays
            int i = 0, j = 0;

            // Initial index of merged subarry array
            int k = l;
            while (i < n1 && j < n2) {
                if (L.get(i) == tuple.compare(L.get(i), R.get(j))) {
                    arr.set(k, L.get(i)); //[k] = L[i];
                    i++;
                } else {
                    arr.set(k, R.get(j)); //arr[k] = R[j];
                    j++;
                }
                k++;
            }

            /* Copy remaining elements of L[] if any */
            while (i < n1) {
                arr.set(k, L.get(i)); //arr[k] = L[i];
                i++;
                k++;
            }

            /* Copy remaining elements of R[] if any */
            while (j < n2) {
                arr.set(k, R.get(j)); //arr[k] = R[j];
                j++;
                k++;
            }
        }

        // Main function that sorts arr[l..r] using
        // merge()
        public static void sort2(ArrayList<tuple> arr, int l, int r) {
            if (l < r) {
                // Find the middle point
                int m = (l + r) / 2;

                // Sort first and second halves
                sort2(arr, l, m);
                sort2(arr, m + 1, r);

                // Merge the sorted halves
                merge2(arr, l, m, r);
            }
        }

    }
}
