package heap;

class Node {
    private int value;

    public Node(int value) {
        this.value = value;
    }

    public int getValue() {
        return this.value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}

public class Heap {
    private Node[] heapArray; // массив со всеми вершинами
    private int maxSize; // размер массива
    private int currentSize; // количество узлов массиве
    final int deleted = -999999;

    public Heap(int maxSize) {
        this.maxSize = maxSize;
        this.currentSize = 0;
        heapArray = new Node[maxSize];
    }

    public void printHeap() {
        System.out.println("Массив значений: ");
    
        for (int i = 0; i < currentSize; i++) {
            if (heapArray[i] != null) {
                System.out.print(i + ": " + heapArray[i].getValue() + "   ");
            } else {
                System.out.println("-");
            }
        }
        System.out.println();
        
        int countOfGaps = 32;
        int itemsPerRow = 1;
        int columnNumber = 0;
        String line = "___________________________________________________________________";
        System.out.println(line);
        for (int i = 0; i < currentSize; i++) {
            if (columnNumber == 0) {
                for (int k = 0; k < countOfGaps; k++) {
                    System.out.print(' ');
                }
            }
            System.out.print(heapArray[i].getValue());
    
            if (++columnNumber == itemsPerRow) { // проверяем последний ли элемент в строке
                countOfGaps /= 2; // уменьшаем количество оступов применяемое для следующей строки
                itemsPerRow *= 2; // указываем, что элементов может быть вдвое больше
                columnNumber = 0;
                System.out.println();
            } else {
                for (int k = 0; k < countOfGaps * 2 - 2; k++) {
                    System.out.print(' ');
                }
            }
        }
        System.out.println("\n" + line);
    }

    public boolean insertNode(int value) {
        if (currentSize == maxSize) {
            return false;
        }
        Node newNode = new Node(value);
        heapArray[currentSize] = newNode;// вершину задаём в самый низ дерева
        displaceUp(currentSize++);
        return true;
    }

    public Node removeNode(int index) {
        if (index > 0 && currentSize > index) {
            Node root = heapArray[index];
            heapArray[index] = heapArray[--currentSize]; // задаём элементу с переданным индексом, значение последнего элемента
            heapArray[currentSize] = null;// последний элемент удаляем
            displaceDown(index);
            return root;
        }
        return null;
    }
    
    public boolean changeNode(int index, int newValue) {
        if (index < 0 || currentSize<=index) {
            return false;
        }
        int oldValue = heapArray[index].getValue();
        heapArray[index].setValue(newValue);

        if (oldValue < newValue) {
            displaceUp(index);
        }
        else {
            displaceDown(index);
        }
        return true;
    }
    
    private void displaceUp(int index) {
        int parentIndex = (index - 1) / 2;
        Node bottom = heapArray[index];
        while (index > 0 && heapArray[parentIndex].getValue() < bottom.getValue()) {
            heapArray[index] = heapArray[parentIndex];
            index = parentIndex;
            parentIndex = (parentIndex - 1) / 2;
        }
        heapArray[index] = bottom;
    }
    
    private void displaceDown(int index) {
        int largerChild;
        Node top = heapArray[index];
        while (index < currentSize / 2) {// если данное условие не выполняется то элемент уже в самом низу пирамиды
            int leftChild = 2 * index + 1;
            int rightChild = leftChild + 1;

            if (rightChild < currentSize && heapArray[leftChild].getValue() < heapArray[rightChild].getValue()) {
                largerChild = rightChild;
            }
            else {
                largerChild = leftChild;
            }

            if (top.getValue() >= heapArray[largerChild].getValue()) {// если значение вершины больше или равно значению его наибольшего ребенка
                break;
            }

            heapArray[index] = heapArray[largerChild];
            index = largerChild;
        }
        heapArray[index] = top;
    }
    
}