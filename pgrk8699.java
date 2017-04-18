// Created by Pratik Thakkar CS610 8699 PRP @NJIT
import static java.lang.System.*;
import java.util.*;
import java.io.*;
import java.text.DecimalFormat;
class pgrk8699{
  public static void main(String[] args) throws IOException {
    //Variable declarations.
    DecimalFormat df = new DecimalFormat("0.0000000");
    int initial_value = 0;
    int iterations = 0;
    String TextFileName = "";
    int vertices = 0;
    int edges = 0;
    int iteration_counter = 0;
    double errorrate = 0.0;
    double d = 0.85;
    boolean flag = true;
    //Checking the input.
    if (args.length != 3){
      System.out.println("Wrong command. Please use the following format : 'hits8699 interations initial_vale filename'");
      return;
    }
    //Parsing the input from command line.
    for (int i=0;i<args.length;i++) {
      iterations = Integer.parseInt(args[0]);
      initial_value = Integer.parseInt(args[1]);
      TextFileName = args[2];
    }
    //Checking if the initial_value is correct.
    if (!(initial_value >= -2 && initial_value <= 1)){
      System.out.println("Please enter one of the following initial values : -2, -1, 0 or 1");
      return;
    }
    //Reading the text file.
    Scanner scanner = new Scanner(new File(TextFileName));
    vertices = scanner.nextInt();
    edges = scanner.nextInt();
    //Initializing the main graph and representing it as an Adjacency Matrix.
    double graph[][] = new double[vertices][vertices];
    for(int i = 0; i < vertices; i++){
      for(int j = 0; j < vertices; j++){
            graph[i][j] = 0.0;
      }
    }
    while(scanner.hasNextInt()){
      graph[scanner.nextInt()][scanner.nextInt()] = 1.0;
    }
    //Setting the errorrate if iterations is negative.
    if (iterations < 0){
      errorrate = Math.pow(10, iterations);
    }
    //Initializing variables after reading the input file.
    double out_degree[] = new double[vertices];
    double initial_pgrk [] = new double[vertices];
    double pgrk[] = new double[vertices];
    double sub[] = new double[vertices];
    //Calculating Out-Degree of Graph
    for(int i=0; i<vertices; i++){
      out_degree[i] = 0;
      for(int j=0; j<vertices; j++){
        out_degree[i] = out_degree[i] + graph[i][j];
      }
    }
    //Initializing the pagerank when vertices < 10.
      if (vertices < 10){
        switch(initial_value){
          case 0:
          for (int i=0; i<vertices;i++){
            initial_pgrk[i] = 0.0;
          }
          break;
          case 1:
          for (int i=0; i<vertices;i++){
            initial_pgrk[i] = 1.0;
          }
          break;
          case -1:
          for (int i=0; i<vertices;i++){
            initial_pgrk[i] = 1.0/vertices;
          }
          break;
          case -2:
          for (int i=0; i<vertices;i++){
            initial_pgrk[i] = 1.0/Math.sqrt(vertices);
          }
          break;
        }
      }
      //Initializing the pagerank for vertices > 10 by loading the default values.
      else{
        iterations = 0;
        initial_value = -1;
        errorrate = 0.00001;
        for (int i=0; i<vertices;i++){
          initial_pgrk[i] = 1.0/vertices;
        }
      }
      //Base Case.
      System.out.print("Base: " +iteration_counter + " : ");
      if (vertices > 5){
        System.out.println();
      }
      for(int i=0; i<vertices; i++){
          System.out.print(" P [" + i + "] = " + df.format(initial_pgrk[i]));
          if (vertices > 5){
            System.out.println();
        }
      }
      System.out.println();
      //Iterations that don't require the convergence function.
      if(iterations > 0){
        do{
          for (int j=0; j<vertices; j++){
            pgrk[j]=0.0;
          }
          for (int j=0; j<vertices; j++){
            for (int k=0; k<vertices; k++){
              if(graph[k][j]==1){
                pgrk[j] = pgrk[j]+initial_pgrk[k]/out_degree[k];
              }
            }
          }
          //Computing Page Rank for all vertices and printing.
          System.out.print("Iter: " + (iteration_counter+1) + " : ");
          if (vertices > 5){
            System.out.println();
          }
          for (int j=0; j<vertices; j++){
            pgrk[j] = d*pgrk[j] + (1-d)/vertices;
            System.out.print(" P [" + j + "] = " + df.format(pgrk[j]));
            if (vertices > 5){
              System.out.println();
          }
        }
        System.out.println();
        for (int j=0; j<vertices; j++){
          initial_pgrk[j]=pgrk[j];
        }
        iteration_counter++;
        iterations--;
      } while(iterations !=0);
    }
    //For iterations requiring the convergence function.
    else{
      do{
          if(flag == true)
          {
             flag = false;
          }
          else
          {
            for(int i = 0; i < vertices; i++) {
              initial_pgrk[i] = pgrk[i];
            }
          }
          for (int j=0; j<vertices; j++){
            pgrk[j]=0.0;
            sub[j]=0.0;
          }
          for (int j=0; j<vertices; j++){
            for (int k=0; k<vertices; k++){
              if(graph[k][j]==1){
                pgrk[j] = pgrk[j]+initial_pgrk[k]/out_degree[k];
              }
            }
          }
          //Computing Page Rank for all vertices and printing.
          System.out.print("Iter: " + (iteration_counter+1) + " : ");
          if (vertices > 5){
            System.out.println();
          }
          for (int j=0; j<vertices; j++){
            pgrk[j] = d*pgrk[j] + (1-d)/vertices;
            System.out.print(" P [" + j + "] = " + df.format(pgrk[j]));
            if (vertices > 5){
              System.out.println();
          }
        }
        System.out.println();
        iteration_counter++;
      } while(ConvergenceTest8699(pgrk, initial_pgrk, vertices, errorrate)!=true);
    }
  }
    //Defining the Convergence function.
    public static boolean ConvergenceTest8699(double initial[], double previous[], int n, double errorrate){
       for(int i = 0 ; i < n; i++){
        if (Math.abs(initial[i]-previous[i]) > errorrate )
          return false;
        }
       return true;
    }
}
