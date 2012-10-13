package questao01.pph.ordenacao;

import java.util.Arrays;
import java.util.List;

/**
 * This class sorts an array using the Quick Sort algorithm. <br/>
 * http://www.vogella.com/articles/JavaAlgorithmsQuicksort/article.html
 */
public class QuickSortLinear extends Sorter implements ISortable {

  @Override
  public <T extends Comparable<T>> void sortAscending(List<T> list) {
    // Desta forma ficou genérico.
    @SuppressWarnings("unchecked")
    T[] arValues = (T[]) java.lang.reflect.Array.newInstance(list.get(0).getClass(), list.size());
    for (int i = 0; i < list.size(); i++) {
      arValues[i] = list.get(i);
    }

    sortAscending(arValues);

    list.clear();
    list.addAll(Arrays.asList(arValues));
  }

  /**
   * @param arValues The array that will be sorted.
   */
  @Override
  public <T extends Comparable<T>> void sortAscending(T[] arValues) {
    quickSort(arValues, 0, arValues.length - 1);
  }

  /**
   * @param a
   * @param l
   * @param r
   * @param <T>
   * @param arValues
   * @param low
   * @param high
   */
  //  private <T extends Comparable<T>> void quickSort(T[] arValues, int low, int high) {
  //    int i = low, j = high;
  //    // Get the pivot element from the middle of the list
  //    T pivot = arValues[low + (high - low) / 2];
  //
  //    //randomIndex(low, high)
  //    //T pivot = arValues[partition(arValues, low, high, high)];// arValues[randomIndex(low, high)];
  //
  //    //    int pos;
  //    //    Random rdn = new Random();
  //    //    pos = rdn.nextInt(high + 1 - low) + low;
  //    //    swap(arValues, high, pos);
  //    //    T pivot = arValues[pos];
  //
  //    // Divide into two lists
  //    while (i <= j) {
  //      this.incIterations();
  //      // If the current value from the left list is smaller then the pivot
  //      // element then get the next element from the left list
  //      while (arValues[i].compareTo(pivot) > 0 && i < j) {
  //        this.incIterations();
  //        i++;
  //      }
  //      // If the current value from the right list is larger then the pivot
  //      // element then get the next element from the right list
  //      while (arValues[j].compareTo(pivot) < 0 && i < j) {
  //        this.incIterations();
  //        j--;
  //      }
  //
  //      // If we have found a values in the left list which is larger then
  //      // the pivot element and if we have found a value in the right list
  //      // which is smaller then the pivot element then we exchange the
  //      // values.
  //      // As we are done we can increase i and j
  //      if (i <= j) {
  //        swap(arValues, i, j);
  //        i++;
  //        j--;
  //      }
  //    }
  //    // Recursion
  //    if (low < j)
  //      quickSort(arValues, low, j);
  //    if (i < high)
  //      quickSort(arValues, i, high);
  //  }

  private <T extends Comparable<T>> void quickSort(T[] a, int l, int r) {
    int i, j, k, p, q;
    T v;
    if (r <= l)
      return;
    v = a[r];
    i = l;// - 1;
    j = r;
    p = l - 1;
    q = r;
    for (;;) {
      while (a[i].compareTo(v) > 0)
        ;
      //(less(a[++i], v))

      while (a[j].compareTo(v) < 0) {
        //(less(v, a[--j]))
        if (j == l)
          break;
      }
      if (i >= j)
        break;

      //exch(a[i], a[j]);
      this.swap(a, i, j);
      if (a[i].equals(v)) { //(eq(a[i], v)) {
        p++;
        //exch(a[p], a[i]);
        this.swap(a, p, i);
      }
      if (v.equals(a[j])) {//(eq(v, a[j])) {
        q--;
        //exch(a[q], a[j]); s
        this.swap(a, q, j);
      }
    }
    //exch(a[i], a[r]);
    this.swap(a, i, r);
    j = i - 1;
    i = i + 1;
    for (k = l; k < p; k++, j--) {
      //exch(a[k], a[j]);
      this.swap(a, k, j);
    }
    for (k = r - 1; k > q; k--, i++) {
      //exch(a[k], a[i]);
      this.swap(a, k, i);
    }
    quickSort(a, l, j);
    quickSort(a, i, r);
  }

  private <T extends Comparable<T>> void swap(T[] a, int l, int r) {
    T temp = a[r];
    a[l] = a[r];
    a[r] = temp;

  }

  private <T extends Comparable<T>> int partition(T[] list, int left, int right, int pivotIndex) {
    int i = left, j = right - 1;
    T tmp;
    T pivot = list[pivotIndex];
    while (i < j) {
      while (list[i].compareTo(pivot) < 0 && i < j)
        i++;
      while (list[j].compareTo(pivot) > 1 && i < j)
        j--;

      //if (i <= j) {
      tmp = list[i];
      list[i] = list[j];
      list[j] = tmp;
      i++;
      j--;
    }
    ;
    return i;
  }

}