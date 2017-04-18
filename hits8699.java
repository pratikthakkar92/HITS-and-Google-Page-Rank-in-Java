// Created by Pratik Thakkar CS610 8699 PRP @NJIT
import static java.lang.System.*;
import java.util.*;
import java.io.*;
import java.text.DecimalFormat;
class hits8699{
  public static void main(String[] args) throws Exception {
    //Variable declarations.
    DecimalFormat df = new DecimalFormat("0.0000000");
    int initial_value = 0;
    int iterations = 0;
    String TextFileName = "";
    int vertices = 0;
    int edges = 0;
    int iteration_counter = 0;
    double errorrate = 0.0;
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
    //Variable declarations post reading the file.
    double authority[][]= new double[vertices][1];
    double hub[][] = new double[vertices][1];
    double authority_sum = 0.0;
    double hub_sum = 0.0;
    double transpose_graph[][] = new double[vertices][vertices];
    double initial_authority[][] = new double[vertices][1];
    double initial_hub[][] = new double[vertices][1];
    double scaler_for_authority = 0;
    double scaler_for_hub = 0;
    double authority_previous[][] = new double [vertices][1];
    double hub_previous [][] = new double [vertices][1];
    //Calculating the Transpose of Graph.
    for(int i=0;i<vertices;i++){
      for(int j=0; j<vertices; j++){
        transpose_graph[i][j] = graph[j][i];
      }
    }
    //Switch case for the Initial Value of the Authority and Hub if Vertices < 10.
    if (vertices < 10){
      switch(initial_value){
        case 0:
          for(int i=0; i<vertices; i++){
            for(int j=0; j<1; j++){
              initial_authority[i][j] = 0.0;
              initial_hub[i][j] = 0.0;
            }
          }
          break;
          case 1:
          for(int i=0; i<vertices; i++){
            for(int j=0; j<1; j++){
              initial_authority[i][j] = 1.0;
              initial_hub[i][j] = 1.0;
            }
          }
          break;
          case -1:
          for(int i=0; i<vertices; i++){
            for(int j=0; j<1; j++){
              initial_authority[i][j] = 1.0/vertices;
              initial_hub[i][j] = 1.0/vertices;
            }
          }
          break;
          case -2:
          for(int i=0; i<vertices; i++){
            for(int j=0; j<1; j++){
              initial_authority[i][j] = 1.0/Math.sqrt(vertices);
              initial_hub[i][j] = 1.0/Math.sqrt(vertices);
            }
          }
          break;
      }
    }
    //Case when Vertices > 10.
    else {
      iterations = 0;
      initial_value = -1;
      errorrate = 0.00001;
      for(int i=0; i<vertices; i++){
        for(int j=0; j<1; j++){
          initial_authority[i][j] = 1.0/vertices;
          initial_hub[i][j] = 1.0/vertices;
        }
      }
    }
    //Calculating for the Base Case of Hub.
    for (int i=0; i<vertices; i++){
      for (int j=0; j<1; j++){
        for (int k=0; k<vertices; k++){
          authority_sum = authority_sum + transpose_graph[i][k]*initial_hub[k][j];
        }
        authority[i][j] = authority_sum;
        authority_sum = 0;
      }
    }
    //Calculating for the Base Case of Authority.
    for (int i=0; i<vertices; i++){
      for (int j=0; j<1; j++){
        for (int k=0; k<vertices; k++){
          hub_sum = hub_sum + graph[i][k]*authority[k][j];
        }
        hub[i][j] = hub_sum;
        hub_sum = 0;
      }
    }
    //Calculating Authority Sum Sqaure.
    for (int i=0; i<vertices; i++ ){
      for (int j=0; j<1; j++){
        authority_sum = authority_sum+ (authority[i][j]*authority[i][j]);
      }
    }
  //Calculating Hub Sum Square and printing the Hub.
    for (int i=0; i<vertices; i++ ){
      for (int j=0; j<1; j++){
        hub_sum = hub_sum + (hub[i][j]*hub[i][j]);
      }
    }
    System.out.print("Base: " +iteration_counter + " : ");
    if (vertices > 5){
      System.out.println();
    }
    for(int i=0; i<vertices; i++){
      for(int j=0; j<1; j++){
        System.out.print("A/H [" + i + "] = " + df.format(initial_authority[i][j]) + "/" + df.format(initial_hub[i][j]) + " ");
        if (vertices > 5){
          System.out.println();
        }
      }
    }
    System.out.println();
    //Going through the iterations while it is positive.
    if(iterations > 0){
      while (iterations != 0){
        System.out.print("Iter: " + (iteration_counter+1) + " :  ");
        if (vertices > 5){
          System.out.println();
        }
        for (int i=0; i<vertices; i++){
          for (int j=0; j<1; j++){
            //Scaling the A and H values.
            scaler_for_authority = Math.sqrt(authority_sum);
            scaler_for_hub = Math.sqrt(hub_sum);
            authority[i][j] = authority[i][j]/scaler_for_authority;
            hub[i][j] = hub[i][j]/scaler_for_hub;
            System.out.print(" A/H [" + i + "] = " + df.format(authority[i][j]) + "/" + df.format(hub[i][j]) + " ");
            if (vertices > 5){
              System.out.println();
            }
          }
        }
        //Calculating Authority and Hub values till iterations is 0.
        authority_sum = 0;
        hub_sum = 0;
        for (int a=0; a<vertices; a++){
          for (int b=0; b<1; b++){
            for (int c=0; c<vertices; c++){
              authority_sum = authority_sum + transpose_graph[a][c]*hub[c][b];
            }
            authority[a][b] = authority_sum;
            authority_sum = 0;
          }
        }
        for (int p=0; p<vertices; p++){
          for (int q=0; q<1; q++){
            for (int r=0; r<vertices; r++){
              hub_sum = hub_sum + graph[p][r]*authority[r][q];
            }
            hub[p][q] = hub_sum;
            hub_sum = 0;
          }
        }
        for (int s=0; s<vertices; s++ ){
          for (int t=0; t<1; t++){
            authority_sum = authority_sum+ (authority[s][t]*authority[s][t]);
          }
        }
        for (int x=0; x<vertices; x++ ){
          for (int y=0; y<1; y++){
            hub_sum = hub_sum + (hub[x][y]*hub[x][y]);
          }
        }
        System.out.println();
        iterations = iterations - 1;
        iteration_counter++;
      }
    }
    else{
      do {
        for (int i = 0; i<vertices; i++){
          for (int j = 0; j<1; j++){
            authority_previous[i][j] = authority[i][j];
            hub_previous[i][j] = hub[i][j];
          }
        }
        System.out.print("Iter: " + (iteration_counter+1) + " : ");
        if (vertices > 5){
          System.out.println();
        }
        for (int i=0; i<vertices; i++){
          for (int j=0; j<1; j++){
            //Scaling the A and H values
            scaler_for_authority = Math.sqrt(authority_sum);
            scaler_for_hub = Math.sqrt(hub_sum);
            authority[i][j] = authority[i][j]/scaler_for_authority;
            hub[i][j] = hub[i][j]/scaler_for_hub;
            System.out.print("A/H [" + i + "] = " + df.format(authority[i][j]) + "/" + df.format(hub[i][j]) + " ");
            if (vertices > 5){
              System.out.println();
            }
          }
        }
        //Calculating Authority and Hub values till convergence is achieved.
        authority_sum = 0;
        hub_sum = 0;
        for (int a=0; a<vertices; a++){
          for (int b=0; b<1; b++){
            for (int c=0; c<vertices; c++){
              authority_sum = authority_sum + transpose_graph[a][c]*hub[c][b];
            }
            authority[a][b] = authority_sum;
            authority_sum = 0;
          }
        }
        for (int p=0; p<vertices; p++){
          for (int q=0; q<1; q++){
            for (int r=0; r<vertices; r++){
              hub_sum = hub_sum + graph[p][r]*authority[r][q];
            }
            hub[p][q] = hub_sum;
            hub_sum = 0;
          }
        }
        for (int s=0; s<vertices; s++ ){
          for (int t=0; t<1; t++){
            authority_sum = authority_sum+ (authority[s][t]*authority[s][t]);
          }
        }
        for (int x=0; x<vertices; x++ ){
          for (int y=0; y<1; y++){
            hub_sum = hub_sum + (hub[x][y]*hub[x][y]);
          }
        }
        System.out.println();
        iteration_counter++;
      } while (false == ConvergenceTest8699(authority, authority_previous, vertices, errorrate) || false == ConvergenceTest8699(hub, hub_previous, vertices,errorrate));
    }
  }
  //Defining the Convergence function.
  public static boolean ConvergenceTest8699(double initial[][], double previous[][], int n, double errorrate){
     for(int i = 0 ; i < n; i++){
       for (int j = 0; j < 1; j++){
         if ( Math.abs(initial[i][j] - previous[i][j]) > errorrate )
           return false;
       }
     }
     return true;
  }
}
