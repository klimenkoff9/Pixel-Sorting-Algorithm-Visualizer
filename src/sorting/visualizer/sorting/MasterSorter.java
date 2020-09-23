package sorting.visualizer.sorting;

public class MasterSorter {
    
    public static boolean sort(Pixel[] pixels, SortingMethods method, int iterations) {
        for (int i = 0; i < iterations; i++) {
            if (method == SortingMethods.BubbleSort) {
                BubbleSort.sort(pixels);
            }
        }
            return isSorted(pixels);
        }

        public static boolean isSorted(Pixel[] pixels) {
            for (int i = 0; i < pixels.length ; i++) {
                Pixel pixel = pixels[i];
                if (i != pixel.id) {
                    return false;
                }
            }
            return true;
    }
}
