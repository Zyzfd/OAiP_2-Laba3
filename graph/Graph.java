package graph;
import java.util.Scanner;
import java.io.*;
import java.util.*;

public class Graph {

    private char[] vertex;    // Коллекция вершин
	private int[][] matrix;   // Матрица смежности
	private LinkedList<Integer> adjLists[][]; // Список смежности
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

		// Инициализируем списки
		adjLists = new LinkedList[vertexNum][2];
 
        for (int i = 0; i < vertexNum; i++) {
			adjLists[i] = new LinkedList[2];
			for (int j = 0; j < 2; j++) {
				adjLists[i][j] = new LinkedList();
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
				adjLists[startInedx][0].add(endIndex);
				adjLists[startInedx][1].add(weight);
				adjLists[endIndex][0].add(startInedx);
				adjLists[endIndex][1].add(weight);
				
			} else {
				matrix[startInedx][endIndex] = weight;
				adjLists[startInedx][0].add(endIndex);
				adjLists[startInedx][0].add(weight);
			}
		}

		System.out.printf("\n");
		for (int i = 0; i < vertexNum; i++) {
			for (int j = 0; j < vertexNum; j++) {
				if (matrix[i][j] == INF) {
					System.out.printf("%4s", "INF");
				} else {
					System.out.printf("%4d", matrix[i][j]);
				}
			}
			System.out.printf("\n");
		}
		System.out.printf("\n");

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
				if (count == vertex.length) {
					System.out.print(vertex[i]);
				} else {
					System.out.print(vertex[i] + "——>");
				}
				count++;
				visited[i] = true;
				queue[rear++] = i;                      // Поставить в очередь
			}
			while(head != rear) {
				int j = queue[head++];                  // Вне очереди
				for (int k = firstVertex(j); k >= 0; k = nextVertex(j, k)) { // k - соседняя вершина для посещения
					if (!visited[k]) {
						if (count == vertex.length) {
							System.out.print(vertex[k]);
						} else {
							System.out.print(vertex[k] + "——>");
						}
						visited[k] = true;
						count++;
						queue[rear++] = k;
					}
				}
			}
		}
		System.out.println();
	}

	
	private int firstVertex(int v) {
		if (v < 0 || v > (vertex.length - 1))
			return -1;
		for (int i = 0; i < vertex.length; i++) {
			if (matrix[v][i] != 0 && matrix[v][i] != INF) {
				return i;
			}
		}
		return -1;
	}

	
	private int nextVertex(int v, int j) {
		if (v < 0 || v > (vertex.length - 1) || j < 0 || j > (vertex.length - 1))
			return -1;
		for (int i = j + 1; i < vertex.length; i++) {
			if (matrix[v][i] != 0 && matrix[v][i] != INF)
				return i;
		}
		return -1;
	}


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


	private int getLocation(char c) {
		for (int i = 0; i < vertex.length; i++) 
			if (vertex[i] == c) return i;
		return -1;
	}



	public void floyd() {
		int[][] dist = new int[vertex.length][vertex.length];
		for (int i = 0; i < vertex.length; i++) {
			for (int j = 0; j < vertex.length; j++) {
				dist[i][j] = (i == j) ?  0 : INF;
			}
		}
		for (int i = 0; i < vertex.length; i++) {
			for (int k = 0; k < adjLists[i][0].size(); k++) {
				int j = adjLists[i][0].get(k);
				dist[i][j] = adjLists[i][1].get(k);
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
		
		for (int i = 0; i < vertex.length+1; i++) {
			if (i == 0) {
				System.out.printf("    ");
			} else {
				System.out.printf("%4c", vertex[i-1]);
			}
		}
		System.out.printf("\n");
		for (int i = 0; i < dist[0].length; i++) {
			System.out.printf("%4c", vertex[i]);
			for (int j = 0; j < dist[0].length; j++) {
				if (dist[i][j] == INF) {
					System.out.printf("%4s", "INF");
				} else {
					System.out.printf("%4d", dist[i][j]);
				}
			}
			System.out.printf("\n");
		}
		System.out.printf("\n");
	}
}
