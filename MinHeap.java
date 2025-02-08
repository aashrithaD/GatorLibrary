import java.util.ArrayList;
import java.util.List;

public class MinHeap {
    private List<Patron> minHeap;

    public MinHeap() {
        this.minHeap = new ArrayList<>();
    }

    // Method to convert MinHeap elements to a string for easy printing
    public String toString() {
        String result = "";
        for (Patron item : minHeap) {
            result += item + ", ";
        }
        return result;
    }

    // Check if the MinHeap is empty
    public boolean isEmpty() {
        return minHeap.isEmpty();
    }

    // Get all elements in the MinHeap
    public List<Patron> getAllElements() {
        return minHeap;
    }

    // Insert a new Patron into the MinHeap
    public void insert(Patron Patron) {
        minHeap.add(Patron);
        int currentIndex = minHeap.size() - 1;
        while (currentIndex > 0) {
            int parentIndex = (currentIndex - 1) / 2;
            Patron parent = minHeap.get(parentIndex);
            if (Patron.compare(parent)) {
                swap(currentIndex, parentIndex);
                currentIndex = parentIndex;
            } else {
                break;
            }
        }
    }

    // Get the minimum element in the MinHeap
    public Patron getMin() {
        if (minHeap.isEmpty()) {
            return null;
        }
        return minHeap.get(0);
    }

    // Remove and return the minimum element from the MinHeap
    public Patron removeMin() {
        if (minHeap.isEmpty()) {
            return null;
        }
        Patron minPatron = getMin();
        Patron lastPatron = minHeap.remove(minHeap.size() - 1);
        // Replace root with the last element and maintain MinHeap property
        if (!minHeap.isEmpty()) {
            minHeap.set(0, lastPatron);
            int currentIndex = 0;
            while (true) {
                int leftIndex = 2 * currentIndex + 1;
                int rightIndex = 2 * currentIndex + 2;
                if (leftIndex >= minHeap.size()) {
                    break;
                }
                int minChildIndex = leftIndex;
                if (rightIndex < minHeap.size()) {
                    Patron left = minHeap.get(leftIndex);
                    Patron right = minHeap.get(rightIndex);
                    if (left.compare(right)) {
                        minChildIndex = rightIndex;
                    }
                }
                Patron minChild = minHeap.get(minChildIndex);
                if (lastPatron.compare(minChild)) {
                    swap(currentIndex, minChildIndex);
                    currentIndex = minChildIndex;
                } else {
                    break;
                }
            }
        }
        return minPatron;
    }

    // Delete a specific Patron from the MinHeap
    public void delete(Patron Patron) {
        if (minHeap.isEmpty()) {
            return;
        }
        int index;
        try {
            index = minHeap.indexOf(Patron);
        } catch (IndexOutOfBoundsException e) {
            return;
        }
        Patron lastPatron = minHeap.remove(minHeap.size() - 1);
        if (index != minHeap.size()) {
            minHeap.set(index, lastPatron);
            int currentIndex = index;
            while (true) {
                int leftIndex = 2 * currentIndex + 1;
                int rightIndex = 2 * currentIndex + 2;
                if (leftIndex >= minHeap.size()) {
                    break;
                }
                int minChildIdx = leftIndex;
                if (rightIndex < minHeap.size()) {
                    Patron left = minHeap.get(leftIndex);
                    Patron right = minHeap.get(rightIndex);
                    if (left.compare(right)) {
                        minChildIdx = rightIndex;
                    }
                }
                Patron minChild = minHeap.get(minChildIdx);
                if (lastPatron.compare(minChild)) {
                    swap(currentIndex, minChildIdx);
                    currentIndex = minChildIdx;
                } else {
                    break;
                }
                int parentIndex = (currentIndex - 1) / 2;
                if (currentIndex > 0 && lastPatron.compare(minHeap.get(parentIndex))) {
                    while (currentIndex > 0) {
                        parentIndex = (currentIndex - 1) / 2;
                        Patron parent = minHeap.get(parentIndex);
                        if (lastPatron.compare(parent)) {
                            swap(currentIndex, parentIndex);
                            currentIndex = parentIndex;
                        } else {
                            break;
                        }
                    }
                }
            }
        }
    }

    // Helper method to swap elements at given indices in the MinHeap
    private void swap(int i, int j) {
        Patron temp = minHeap.get(i);
        minHeap.set(i, minHeap.get(j));
        minHeap.set(j, temp);
    }
}
