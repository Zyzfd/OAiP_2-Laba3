package bfs;
import java.util.Scanner;
import java.io.IOException;

public class bfs {

    private char[] vertex;    // Коллекция вершин
	private int[][] matrix;   // Матрица смежности
	private static final int INF = 999999; // Максимум
	private static int count = 0;
	/**
	  * Создать график
	 */
	public bfs() {
		Scanner sca = new Scanner(System.in);
		int vertexNum = sca.nextInt();                 // Количество вершин
		int matrixNum = sca.nextInt();                 // Количество сторон
		vertex        = new char[vertexNum];                
		vertex = sca.next().toCharArray();             // Инициализируем вершину
		// Инициализируем матрицу
		matrix = new int [vertexNum][vertexNum];
		for (int i = 0; i < vertexNum; i++) 
			for (int j = 0; j < vertexNum; j++)
				matrix[i][j] = 	(i == j) ?  0 : INF;
		for(int i = 0; i < matrixNum; i++) {           // Инициализируем вес ребра
			char start     = readChar();               // Начальная точка края
			char end       = readChar();               // Конец ребра
			int weight     = sca.nextInt();            // Вес края
			int startInedx = getLocation(start);       // Получаем координаты начальной точки края
			int endIndex   = getLocation(end);         // Получаем координаты конечной точки края
			if(startInedx == -1 || endIndex == -1) return;
			matrix[startInedx][endIndex] = weight;
			matrix[endIndex][startInedx] = weight;
		}
		sca.close();
	}

    public void BFS() {
		count = 0;                                      // Количество точек хода возвращается к 0
		int head = 0;
		int rear = 0;
		int[] queue = new int[vertex.length];           // вспомогательная очередь
		boolean[] visited = new boolean[vertex.length]; // Метка доступа к вершине
		for(int i = 0; i < vertex.length; i++)
			visited[i] = false;
		System.out.println("BFS:");
		for(int i = 0; i < vertex.length; i++) {
			if(!visited[i]) {
				count++;
				visited[i] = true;
				if(count == vertex.length) {
					System.out.print(vertex[i]);
				}else {
					System.out.print(vertex[i] + "————>");
				}
				queue[rear++] = i;                      // Поставить в очередь
			}
			while(head != rear) {
				int j = queue[head++];                  // Вне очереди
				for(int k = firstVertex(j); k >= 0; k = nextVertex(j, k)) { // k - соседняя вершина для посещения
					if(!visited[k]) {
						visited[k] = true;
						count++;
						if(count == vertex.length) {
							System.out.print(vertex[k]);
						}else {
							System.out.print(vertex[k] + "————>");
						}
						queue[rear++] = k;
					}
				}
			}
		}
		System.out.println();
	}
    /**
	  * Вернуть индекс первой смежной вершины вершины v, вернуть -1 в случае неудачи
	 */
	private int firstVertex(int v) {
		if(v < 0 || v > (vertex.length - 1))
			return -1;
		for(int i = 0; i < vertex.length; i++) {
			if(matrix[v][i] != 0 && matrix[v][i] != INF) {
				return i;
			}
		}
		return -1;
	}
	/**
	  * Возвращает индекс следующей смежной вершины вершины v относительно w или -1 в случае ошибки
	 */
	private int nextVertex(int v, int j) {
		if(v < 0 || v > (vertex.length - 1) || j < 0 || j > (vertex.length - 1))
			return -1;
		for(int i = j + 1; i < vertex.length; i++) {
			if(matrix[v][i] != 0 && matrix[v][i] != INF)
				return i;
		}
		return -1;
	}
	/**
	  * Прочитать введенный символ
	 * @return
	 */
	private char readChar() {
		char ch = '0';
		do {
			try {
				ch = (char)System.in.read();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}while(!((ch >= 'a' && ch <= 'z') || (ch >= 'A' && ch <= 'Z')));
		return ch;
	}
	/**
	  * Вернуть позицию персонажа
	 */
	private int getLocation(char c) {
		for(int i = 0; i < vertex.length; i++) 
			if(vertex[i] == c) return i;
		return -1;
	}

}
