package graph;
import java.util.Scanner;
import java.io.IOException;

public class Graph {

    private char[] vertex;    // Коллекция вершин
	private int[][] matrix;   // Матрица смежности
	private static final int INF = 999999;
	private static int count = 0;
	/**
	  * Создать график
	 */
	public Graph() {
		Scanner sca = new Scanner(System.in);
		System.out.println ("Количество вершин: ");
		int vertexNum = sca.nextInt();                 // Количество вершин
		System.out.println ("Количество ребер: ");
		int matrixNum = sca.nextInt();                 // Количество ребер
		vertex        = new char[vertexNum];
		System.out.println ("Инициализация вершин: ");              
		vertex = sca.next().toCharArray();             // Инициализируем вершины
		// Инициализируем матрицу
		matrix = new int [vertexNum][vertexNum];
		for (int i = 0; i < vertexNum; i++) {
			for (int j = 0; j < vertexNum; j++) {
				matrix[i][j] = 	(i == j) ?  0 : INF;
			}
		}
		System.out.println("Граф ориентированный? (1, 0): ");
		int orient = sca.nextInt();
		System.out.println("--Ввод графа--");
		for (int i = 0; i < matrixNum; i++) {
			System.out.println("Начальная вершина: ");
			char start     = readChar();
			System.out.println("Конечная вершина: ");
			char end       = readChar();
			System.out.println("Вес ребра: ");
			int weight     = sca.nextInt();
			int startInedx = getLocation(start);       // Получаем координаты начальной точки края
			int endIndex   = getLocation(end);         // Получаем координаты конечной точки края
			if (startInedx == -1 || endIndex == -1) return;
			if (orient == 0) {
				matrix[startInedx][endIndex] = weight;
				matrix[endIndex][startInedx] = weight;
			} else {
				matrix[startInedx][endIndex] = weight;
			}
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
				if (count == vertex.length) {
					System.out.print(vertex[i]);
				} else {
					System.out.print(vertex[i] + "————>");
				}
				queue[rear++] = i;                      // Поставить в очередь
			}
			while(head != rear) {
				int j = queue[head++];                  // Вне очереди
				for (int k = firstVertex(j); k >= 0; k = nextVertex(j, k)) { // k - соседняя вершина для посещения
					if (!visited[k]) {
						visited[k] = true;
						count++;
						if (count == vertex.length) {
							System.out.print(vertex[k]);
						} else {
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
		if (v < 0 || v > (vertex.length - 1))
			return -1;
		for (int i = 0; i < vertex.length; i++) {
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
		if (v < 0 || v > (vertex.length - 1) || j < 0 || j > (vertex.length - 1))
			return -1;
		for (int i = j + 1; i < vertex.length; i++) {
			if (matrix[v][i] != 0 && matrix[v][i] != INF)
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
		} while (!((ch >= 'a' && ch <= 'z') || (ch >= 'A' && ch <= 'Z')));
		return ch;
	}
	/**
	  * Вернуть позицию персонажа
	 */
	private int getLocation(char c) {
		for (int i = 0; i < vertex.length; i++) 
			if (vertex[i] == c) return i;
		return -1;
	}



	public void floyd() {
		int[][] dist = new int[vertex.length][vertex.length];
		for (int i = 0; i < vertex.length; i++) {
			for (int j = 0; j < vertex.length; j++) {
				dist[i][j] = matrix[i][j];
			}
		}
		
		for (int k = 0; k < vertex.length; k++) {
			for (int i = 0; i < vertex.length; i++) {
				for (int j = 0; j < vertex.length; j++) {
					if (dist[i][j] > dist[i][k] + dist[k][j]) {
						dist[i][j] = dist[i][k] + dist[k][j];
					}
				}
			}
		}

		for (int i = 0; i < vertex.length; i++) {
			System.out.printf("\n");
			for (int j = 0; j < vertex.length; j++) {
				System.out.printf("%3d", dist[i][j]);
			}
		}
	}
}