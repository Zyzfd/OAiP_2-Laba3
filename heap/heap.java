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

public class heap {
    private Node[] heapArray; // массив со всеми вершинами
    private int maxSize; // размер массива
    private int currentSize; // количество узлов массиве

    public heap(int maxSize) { // создание пустой пирамиды
        this.maxSize = maxSize;
        this.currentSize = 0;
        heapArray = new Node[maxSize];
    }

    public void printHeap() { // отображение перамиды в консоль
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
        int columnNumber = 0; // номер элемента в данной строке
        String line = "___________________________________________________________________";
        System.out.println(line);
        for (int i = 0; i < currentSize; i++) {
            if (columnNumber == 0) {  // проверяем первый элемент ли в текущей строке
                for (int k = 0; k < countOfGaps; k++) { // добавляем предшествующие пробелы
                    System.out.print(' ');
                }
            }
            System.out.print(heapArray[i].getValue());// выводим в консоль значение вершины
    
            if (++columnNumber == itemsPerRow) { // проверяем последний ли элемент в строке
                countOfGaps /= 2; // уменьшаем количество оступов применяемое для следующей строки
                itemsPerRow *= 2; // указываем, что элементов может быть вдвое больше
                columnNumber = 0; // сбрасываем счётчик для текущего элемента строки
                System.out.println(); // переходим на новую строку
            } else { //переход к следующему элементу
                for (int k = 0; k < countOfGaps * 2 - 2; k++) {
                    System.out.print(' '); // добавляем оступы
                }
            }
        }
        System.out.println("\n" + line); // нижний пункир
    }

    public boolean insertNode(int value) { // вставка нового значения
        if (currentSize == maxSize) { // проверяем не выходим ли мы за рамки массива
            return false;
        }
        Node newNode = new Node(value);// создание вершины с данным значением
        heapArray[currentSize] = newNode;// вершину задаём в самый низ дерева
        displaceUp(currentSize++);// пытаемся поднять вершину, если значение вершины позволяет
        return true;
    }

    public Node removeNode(int index) { // удалить элемент по индексу массива
        if (index > 0 && currentSize > index) {
            Node root = heapArray[index];
            heapArray[index] = heapArray[--currentSize]; // задаём элементу с переданным индексом, значение последнего элемента
            heapArray[currentSize] = null;// последний элемент удаляем
            displaceDown(index);// проталкиваем вниз новый элемент, чтобы он принял должное ему место
            return root;
        }
        return null;
    }
    
    public boolean changeNode(int index, int newValue) {
        if (index < 0 || currentSize<=index) {
            return false;
        }
        int oldValue = heapArray[index].getValue(); // сохраняем старое значение
        heapArray[index].setValue(newValue); // присваиваем новое

        if (oldValue < newValue) {// если узел повышается
            displaceUp(index);     // выполняется смещение вверх
        }
        else {                  // если понижается
            displaceDown(index);   // смещение вниз
        }
        return true;
    }
    
    private void displaceUp(int index) { //смещение вверх
        int parentIndex = (index - 1) / 2; // узнаем индекс родителя
        Node bottom = heapArray[index]; // берем элемент
        while (index > 0 && heapArray[parentIndex].getValue() < bottom.getValue()) {// если родительский элемент меньше
            heapArray[index] = heapArray[parentIndex];// то меняем его местами с рассматриваемым
            index = parentIndex;
            parentIndex = (parentIndex - 1) / 2;// берем новый родительский индекс и повторяем сравнение элементов
        }
        heapArray[index] = bottom;// соохраняем результат
    }
    
    private void displaceDown(int index) {// смещение вниз
        int largerChild;
        Node top = heapArray[index]; // сохранение корня, пока у узла есть хотя бы один потомок
        while (index < currentSize / 2) {// если данное условие не выполняется то элемент уже в самом низу пирамиды
            int leftChild = 2 * index + 1; // вычисляем индексы в массиве для левого узла ребенка
            int rightChild = leftChild + 1;// и правого

            if (rightChild < currentSize && heapArray[leftChild].getValue() < heapArray[rightChild].getValue()) { // вычисляем вершину с ребенка наибольшим числовым значением
                largerChild = rightChild;
            }
            else {
                largerChild = leftChild;
            }

            if (top.getValue() >= heapArray[largerChild].getValue()) {// если значение вершины больше или равно значению его наибольшего ребенка
                break;// то выходим из метода
            }

            heapArray[index] = heapArray[largerChild];// заменяем вершину, большей дочерней вершиной
            index = largerChild; // текущий индекс переходит вниз
        }
        heapArray[index] = top; // задаем конечное местоположение для элемента
    }
}