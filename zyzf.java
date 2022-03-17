import heap.heap;

public class zyzf {
    public static void main(String[] args) {
        // задаем начальные данные:
        heap heap = new heap(31);
        heap.insertNode(120);
        heap.insertNode(40);
        heap.insertNode(50);
        heap.insertNode(80);
        heap.insertNode(20);
        heap.insertNode(100);
        heap.insertNode(150);
        heap.insertNode(30);
        heap.insertNode(210);
        heap.insertNode(180);
        heap.insertNode(10);
        heap.insertNode(90);
        // выводим начальную пирамиду в консоль
        heap.printHeap();
    }
}