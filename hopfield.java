
import rutinaAuxilar.Terminal;
import rutinaMatrices.matriz;

class algoHopfield {
public static void main (String[] args) {
System.out.println("Reconocimiento de patrones usando el Modelo de Hopfield");


// Entrenamiento
System.out.println("Fase de Entrenamiento");
int fila,columna;
System.out.println("Dimension del patron de Entrenamiento");
fila = Terminal.leeEntero("Cantidad de filas(Y): ");
columna = Terminal.leeEntero("Cantidad de columnas(X): ");
System.out.println("______________________________");
int Patrones;
Patrones = Terminal.leeEntero("Cantidad de patrones de entrenamiento: ");
System.out.println("______________________________________");
System.out.println("Se van a definir "+Patrones+" patrones de entrenamiento de "+fila+" filas y "+columna+" columnas cada uno");

// Neuronas --> numero de neuronas de la red: vectores de Entrenamiento
int Neuronas = fila*columna;

/* Declaracion de la matriz de Patrones P
de la forma E[Neuronas] = [e1,.., eN] */
int[][]E = new int[Patrones][Neuronas];


int i, cont;
int Nfilas,NColumnas;
for (i=0;i<Patrones;i++) {
cont=0;
System.out.println("Utilice 1 para pixel negro y -1 para pixel blanco");
System.out.println("Patron num. "+(i+1)+"]");
for (Nfilas=0;Nfilas<fila;Nfilas++)
for (NColumnas=0;NColumnas<columna;NColumnas++)
E[i][cont++] = Terminal.leeEntero("E["+Nfilas+"]["+NColumnas+"] = ");
}


//ETAPA DE APRENDIZAJE
//Algoritmo de Hopfield - Definicion de la matriz identidad de tamaño N*N
int j;
int [][]I = new int[Neuronas][Neuronas];
for (i=0;i<Neuronas;i++)
for (j=0;j<Neuronas;j++)
if (i==j)
I[i][j]= 1;
else
I[i][j] = 0;

/*Para cada patron calcular la matriz de pesos de acuerdo a
la formula W = Sum (TE . E - I)
donde Sum es la sumatoria de
TE (matriz traspuesta del patron de entrada E) por la
entrada E menos la matriz identidad I
W es la matriz de pesos de N*N */
int[][]W = new int[Neuronas][Neuronas];
for (i=0;i<Patrones;i++) {

int [][]T = new int[Neuronas][1];
int [][]Ei = new int[1][Neuronas];

for (j=0;j<Neuronas;j++)
Ei[0][j]= E[i][j];
matriz.traspuesta(Ei,1,Neuronas,T);
int [][]P = new int[Neuronas][Neuronas];
matriz.productoMatrices(T,Ei,Neuronas,Neuronas,1,P);
int [][]OP = new int[Neuronas][Neuronas];
matriz.opuesta(I,Neuronas,Neuronas,OP);
int [][]S = new int[Neuronas][Neuronas];
matriz.suma(P,OP,Neuronas,Neuronas,S);
matriz.suma(W,S,Neuronas,Neuronas,W);
}


// ETAPA DE FUNCIONAMIENTO 

System.out.println(" ");
System.out.println("Etapa de Funcionamiento");
System.out.println("Ingrese una matriz de entrada de "+fila+" filas y "+columna+" columnas");
System.out.println("Utilice un 1 para pixel negro y -1 para pixel blanco");
System.out.println("Entrada: ");

/* Declaracion de la matriz de entrada Ent
de la forma Ent = [e1,.., eN] */
int[][]Ent = new int[1][Neuronas];
// Lectura de la entrada Ent
cont=0;
for (Nfilas=0;Nfilas<fila;Nfilas++)
for (NColumnas=0;NColumnas<columna;NColumnas++)
Ent[0][cont++] = Terminal.leeEntero("Ent["+Nfilas+"]["+NColumnas+"] = ");

System.out.print("Calculando salida ");

// salida(t+1) <> salida(t)
int [][]Salida = new int[1][Neuronas];
boolean igual=false;
do {
System.out.print("..");
// Aplicacion de la funcion escalon con desplazamiento 0
matriz.productoMatrices(Ent,W,1,4,4,Salida);
/* Transformacion de los valores de la salida S a valores discretos 1, -1
si S[i,j] < 0 entonces S[i,j]= -1
si S[i,j] >= 0 entonces S[i,j] = +1 */
for (j=0;j<Neuronas;j++)
if (Salida[0][j]<0)
Salida[0][j]= -1;
else
Salida[0][j]= 1;
// Comparacion de las salidas en t y (t+1)
if (matriz.iguales(Ent,Salida,1,4))
igual=true;
else
// La salida es la nueva entrada
for (j=0;j<Neuronas;j++)
Ent[0][j] = Salida[0][j];
} while (!igual);

// Impresión de la salida
System.out.println(" ");
System.out.println("Salida:");
for (j=0;j<Neuronas;j++) {
if ((j % columna) == 0)
System.out.println("");
System.out.print(Salida[0][j]+ " ");
}
}
}