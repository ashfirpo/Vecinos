package vecinos;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class Aliados {
	
	private int cantVecinos; //Cantidad de nodos
	private int cantLazos; //Cantidad de aristas
	private int oponenteX;
	private int oponenteY;
	private int[][] matriz;
	private static final int INF = 999999; //Según enunciado, el costo máximo es 100 y máx 2000 nodos
	
	public Aliados(String path) throws FileNotFoundException
	{
		Scanner sc = new Scanner(new File(path));
		
		this.cantVecinos=sc.nextInt();
		this.cantLazos = sc.nextInt();
		this.oponenteX = sc.nextInt() -1;
		this.oponenteY = sc.nextInt() -1;
		sc.nextLine();
		this.matriz = new int[this.cantVecinos][this.cantVecinos];
		for(int i=0;i<this.cantVecinos;i++)
			Arrays.fill(this.matriz[i], INF);
		
		for(int i=0;i<this.cantLazos;i++)
		{
			int origen = sc.nextInt() -1;
			int destino = sc.nextInt() -1;
			int costo = sc.nextInt();
			this.matriz[origen][destino] = this.matriz[destino][origen] = costo; //NDP
		}
		sc.close();
	}
	
	
	public void resolver()
	{
		int aliadosX=0, aliadosY=0;
		int[] opX = new int[this.cantVecinos];
		int[] opY = new int[this.cantVecinos];
		int[][] matrizX = new int[this.cantVecinos][this.cantVecinos];
		int[][] matrizY = new int[this.cantVecinos][this.cantVecinos];
		
		for(int i=0;i<this.cantVecinos;i++)
		{
			matrizX[i] = Arrays.copyOf(this.matriz[i], this.cantVecinos);
			matrizY[i] = Arrays.copyOf(this.matriz[i], this.cantVecinos);
		}
		
		//Saco a los enemigos de la lista de cada uno: no va a existir lazo de amistad entre ellos
		matrizX[this.oponenteX][this.oponenteY] = INF;
		matrizX[this.oponenteY][this.oponenteX] = INF;
		
		opX = dijkstra(this.oponenteX, matrizX);
		opY = dijkstra(this.oponenteY, matrizY);
		
		if(opX != null && opY != null)
		{
			for(int i=0;i<this.cantVecinos;i++)
			{
				if(i != this.oponenteX && i != this.oponenteY)
				{
					if(opX[i] != INF && opX[i] > opY[i])
						aliadosX++;
					else if(opY[i] != INF && opY[i] > opX[i])
						aliadosY++;
				}
			}
		}
		System.out.print("Aliados de X: " + aliadosX + " | Aliados de Y: " + aliadosY);
	}
	
	
	public int[] dijkstra(int nodoOrigen, int[][] matriz)
	{
		int[] costos = new int[this.cantVecinos];
		Set<Integer> nodosRestantes = new HashSet<Integer>();
		Arrays.fill(costos,  INF);
		
		int[] inf = new int[this.cantVecinos];
		Arrays.fill(inf,  INF);
		
		for(int i=0;i<this.cantVecinos;i++)
			nodosRestantes.add(i);
		
		nodosRestantes.remove(nodoOrigen);
		
		for(int i=0;i<this.cantVecinos;i++)
			costos[i] = matriz[nodoOrigen][i];
		
		if(Arrays.equals(costos, inf))
			return null;
		
		while(!nodosRestantes.isEmpty())
		{
			int nodoMenor=0, menorValor=INF;
			
			for(Integer i : nodosRestantes)
			{
				if(costos[i] < menorValor)
				{
					nodoMenor = i;
					menorValor = costos[i];
				}
			}
			
			for(Integer i : nodosRestantes)
			{
				if(costos[nodoMenor] + matriz[nodoMenor][i] < costos[i])
					costos[i] = costos[nodoMenor] + matriz[nodoMenor][i];
			}
			
			nodosRestantes.remove(nodoMenor);
		}
		
		return costos;//Solo quiero saber los costos desde el origen al resto de los nodos
	}
}
