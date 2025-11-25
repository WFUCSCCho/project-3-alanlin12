/*∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗*
 * @file: Proj3.java
 * @description: This program implements various sorting algorithms, 
 *               takes in a unsorted array and will sort them using 
 *               various methods and record the time.
 * @author: Alan Lin
 * @date: November 11, 2025
 *∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗*/
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class Proj3 {
    // Sorting Method declarations
    // Merge Sort
    public static <T extends Comparable> void mergeSort(ArrayList<T> a, int left, int right) {
        if(left < right) {
            int mid = (left + right) / 2;
            mergeSort(a, left, mid); // Left half of subarray
            mergeSort(a, mid + 1, right); // Right half of subarray
            merge(a, left, mid, right); // Final merge.
        }
    }

    public static <T extends Comparable> void merge(ArrayList<T> a, int left, int mid, int right) {
        ArrayList<T> temp = new ArrayList<>();
        int i = left; int j = mid + 1;
        
        while(i <= mid && j <= right){
            if(a.get(i).compareTo(a.get(j)) <= 0){ //If left is less than right.
                temp.add(a.get(i)); 
                i++;
            } else {
                temp.add(a.get(j)); // Right > Left
                j++;
            }
        }
        
        // Get remaining values
        while(i <= mid) {
            temp.add(a.get(i++));
        }

        while(j <= right){
            temp.add(a.get(j++));
        }

        for(int index = 0; index < temp.size(); index++){
            a.set(left + index, temp.get(index)); // Write sorted values to the array.
        }
    }

    // Quick Sort
    public static <T extends Comparable> void quickSort(ArrayList<T> a, int left, int right) {
        if (left < right){
            int pivot_i = partition(a, left, right);
            quickSort(a, left, pivot_i-1);
            quickSort(a, pivot_i + 1, right);
        }
    }

    public static <T extends Comparable> int partition (ArrayList<T> a, int left, int right) {
        T pivot = a.get(right);
        int i = left - 1;

        for(int j = left; j < right; j++){
            if(a.get(j).compareTo(pivot) <= 0) { //Compare val to pivot
                i++;
                swap(a, i, j);
            }
        }
        swap(a, i + 1, right);
        return i + 1;
    }

    static <T> void swap(ArrayList<T> a, int i, int j) {
        T temp = a.get(i);
        a.set(i, a.get(j));
        a.set(j, temp);
    }

    // Heap Sort
    public static <T extends Comparable> void heapSort(ArrayList<T> a, int left, int right) {
        int n = right - left + 1; // Heap size

        for (int i = n / 2 - 1; i >= 0; i--) {
            heapify(a, n, i);
        }

        for (int i = n - 1; i > 0; i--) {
            swap(a, 0, i);
            heapify(a, i, 0);     
        }
}

    public static <T extends Comparable> void heapify (ArrayList<T> a, int left, int right) {
        int largest = right;
        int l_c = 2 * right + 1;
        int r_c = 2 * right + 2;

        if (l_c < left && a.get(l_c).compareTo(a.get(largest)) > 0)
            largest = l_c;

        if (r_c < left && a.get(r_c).compareTo(a.get(largest)) > 0)
            largest = r_c;

        if (largest != right) {
            swap(a, right, largest);
            heapify(a, left, largest);
        }
}

    // Bubble Sort
    public static <T extends Comparable> int bubbleSort(ArrayList<T> a, int size) {
        int comparisons = 0;
        for(int i = 0; i < size - 1; i++){
            for(int j = 0; j < size - i - 1; j++){
                comparisons++;
                if(a.get(j).compareTo(a.get(j+1)) > 0){
                    swap(a, j, j+1);
                }
            }
        }
        return comparisons;
    }

    // Odd-Even Transposition Sort
    public static <T extends Comparable> int transpositionSort(ArrayList<T> a, int size) {
        boolean isSorted = false;
        int comparisons = 0;

        while(!isSorted){
            isSorted = true;
            for(int i = 1; i < size - 2; i += 2){
                comparisons++;
                if(a.get(i).compareTo(a.get(i+1)) > 0){
                    swap(a, i, i+1);
                    isSorted = false;
                }
            }

            for(int i = 0; i < size - 2; i += 2){
                comparisons++;
                if(a.get(i).compareTo(a.get(i+1)) > 0){
                    swap(a, i, i+1);
                    isSorted = false;
                }
            }
        }
        return comparisons;
    }

    public static void main(String [] args)  throws IOException {
        String inputFileName = args[0];
        String sortingType = args[1];
        int numLines = Integer.parseInt(args[2]);

        Scanner sc = new Scanner(new FileInputStream(inputFileName));
        sc.nextLine();

        ArrayList<World> arr = new ArrayList<>();
        int count = 0;
        while (sc.hasNextLine() && count < numLines) {
            String[] split = sc.nextLine().split(",");
            int year = Integer.parseInt(split[0]);
            long population = Long.parseLong(split[1]);
            arr.add(new World(year, population));
            count++;
        }
        sc.close();

        // Make 3 types of arrays
        ArrayList<World> sortedList = new ArrayList<>(arr);
        Collections.sort(sortedList);

        ArrayList<World> shuffledList = new ArrayList<>(arr);
        Collections.shuffle(shuffledList);

        ArrayList<World> reversedList = new ArrayList<>(arr);
        Collections.sort(reversedList, Collections.reverseOrder());

        String[] orderType = new String[]{"sorted", "shuffled", "reversed"};
        // Run and Time
        File analyzeFile = new File("analysis.txt");
        boolean newFile = !analyzeFile.exists();
        FileWriter analysisWriter = new FileWriter(analyzeFile, true);

        if(newFile){
            analysisWriter.write("#num_lines,sorting_type,array_order,time,num_comparisons\n");
        }

        FileWriter sortedWriter = new FileWriter("sorted.txt");
        for(String s : orderType){
            switch(s) {
                case "sorted": 
                    arr = new ArrayList<>(sortedList);
                    break;
                case "shuffled": 
                    arr = new ArrayList<>(shuffledList);
                    break;
                case "reversed": 
                    arr = new ArrayList<>(reversedList);
                    break;
                
                default:
                    break;
            }
            int comparisons = -1;
            long start = System.nanoTime();
            switch(sortingType) {
                case "bubble":
                    comparisons = bubbleSort(arr, arr.size());
                    break;
                case "merge":
                    mergeSort(arr, 0, arr.size() - 1);
                    break;
                case "quick":
                    quickSort(arr, 0, arr.size() - 1);
                    break;
                case "heap":
                    heapSort(arr, 0, arr.size() - 1);
                    break;
                case "transposition":
                    comparisons = transpositionSort(arr, arr.size());
                    break;
                default:
                    return;
            }
            long end = System.nanoTime();

            double time = (end - start) / 1000000.;

            analysisWriter.write(String.format("%s, %s, %s, %.3f, %d\n", numLines, sortingType, s, time, comparisons));

            if(s.equals("reversed")){
                for(World i : arr){
                    sortedWriter.write(i + "\n");
                }
            }
        }
        analysisWriter.close();
        sortedWriter.close();
    }
}
