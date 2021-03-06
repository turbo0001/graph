package lt.kvk.i9.meilys_orestas.core;

import lt.kvk.i9.meilys_orestas.data.Data;
import lt.kvk.i9.meilys_orestas.stack.Stack;
import lt.kvk.i9.meilys_orestas.util.ReadData;


public class Graph {

    private int MAX_VERTS = 20;

    Stack theStack = new Stack(MAX_VERTS);
    
    ReadData r = new ReadData();

    public Vertex vertexList[];    // vertex list
    private int adjMat[][];         // edges array if 0 false, if 1 true
    private int nVerts;             // number of current vertices


    // data to file object
    private ReadData data = new ReadData();
    String INPUT_FILE = "./res/duomenys5.csv";
    String OUTPUT_FILE = "./res/result.csv";

    public Graph() {
        vertexList = new Vertex[MAX_VERTS];
        adjMat = new int[MAX_VERTS][MAX_VERTS];
        nVerts = 0;

        // read from resources file
        readFromResFile();

        for (int j = 0; j < MAX_VERTS; j++) {
            for (int k = 0; k < MAX_VERTS; k++) {
                adjMat[j][k] = 0;
            }
        }
    }


    //-------------------------------
    //      METHODS
    //-------------------------------

    // add a vertex
    public void addVertex(String edgeName, String s1, String s2, String s3) {
        // vertex'ai pridedami i vertex'u masyva
        vertexList[nVerts++] = new Vertex(edgeName, new Data(s1, s2, s3));
    }

    // connect vertexes
    public void addEdge(int start, int end) {
        adjMat[start][end] = 1;
        adjMat[end][start] = 1;
    }

    // display vertex
    public void displayVertex(int v) {
        System.out.print(vertexList[v].label + ":  ");
        System.out.println(vertexList[v].data.toString());
        System.out.println("-----------------------------------------------------------------");
    }


    // returns an unvisited vertex adjacent to v
    public int getAdjUnvisitedVertex(int v) {
        for (int j = 0; j < nVerts; j++)
            // jei vertex'as matricos eilėje v ir stulpelyje j yra neaplankytas
            // grąžinama jo vieta stulpelyje - j
            if (adjMat[v][j] == 1 && vertexList[j].wasVisited == false)
                return j;
        // jei nebėra nepalankytų vertex'ų, tai grąžinama -1
        return -1;
    }


    // depth first search
    // paieska gilyn
    public void dfs() {
        // pirmajį vertex'ą nustato kaip aplankytą
        vertexList[0].wasVisited = true;
        // išvedamas į terminalą aplankytas vertex'as
        displayVertex(0);
        // steke įvedama aplankyto vertex'o indeksas
        // ++top - į kurią vieetą įrašomas vertex'o indeksas
        theStack.push(0);

        // vykdoma kol stekas nepalieka tuščias
        while (!theStack.isEmpty()) {
            // get unvisited vertex adjacent to lt.kvk.i9.pavarde_vardas.stack top
            // theStack.peek() metodas grazina didziasiame taske esancio elemento indeksa
            int v = getAdjUnvisitedVertex(theStack.peek());
            if (v == -1)
                theStack.pop();
            else {
                // nustato jog vertex'as v idekse aplankytas
                vertexList[v].wasVisited = true;
                // isvedamas vertex'as
                displayVertex(v);
                // i steka pridedamas elementas v
                theStack.push(v);
            }
        }

        // kai apeina visus vertex'as wasVisited nustato į null reikšmę
        for (int j = 0; j < nVerts; j++)
        	if(vertexList[j] != null)
        	{
        		vertexList[j].wasVisited = false;
        	}	
        	}

    //depth first bigger search


    // write data to result file
    public void updateInputFile() {
        data.writeData(OUTPUT_FILE, vertexList);
    }

    public void readFromResFile() {
        data.readData(INPUT_FILE, this);
    }

    public void printEdges() {

    }

    public void printAllVertex() {
        for (int i = 0; i < vertexList.length; i++) {
            if (vertexList[i] != null) {
                System.out.println(vertexList[i].toString());
            } else {
                break;
            }
        }
    }
    
    public void deleteLinks(int id)
    {
    		for(int i = 0; i < MAX_VERTS-1; i++) {

                adjMat[id][i] = 0;
                adjMat[i][id] = 0;
            }
    }
    
    public void deleteMax()
    {
    	Vertex tempBig = null;	// temp Vertex to comapre with others
    	int maxId = 0;			// max id for deleting
    	
    	for(int i = 0; i < MAX_VERTS-1; i++) {
    		if(vertexList[i] != null)
    		{
    				if(tempBig == null)		// if temp is null make tem first in array
    				{
    					tempBig = vertexList[i];
    				}else
    				{
        				int a = compare(vertexList[i], tempBig);	// else comapre objects bigger gose to temp
        				if(a > 0)
        				{
        					tempBig = vertexList[i];
        					maxId = i;
        				}
    				}
    		}else 
    		{
    			break;
    		}
    	}
    	
    	deleteLinks(maxId);		// deletes by id;
    	
    	vertexList[maxId] = null;	// sets all 0 at spesific 2d matrix at x and y
    	
    	r.writeData("duomenys5.csv",vertexList);	// writes new data to file.
    }

	public int compare(Vertex c1, Vertex c2)
	{
		int n = 0;
		n = c1.data.name.compareTo(c2.data.name);		//compares obj names if equals keeps going, else returns result
		if(n != 0) return n;
		n = c1.data.value.compareTo(c2.data.value);				//compares obj values if equals keeps going, else returns result
		if(n !=0) return n;
		return c1.data.date.compareTo(c2.data.date);	//lastly compares dates and returns

	}

    public int getnVerts() {
        return nVerts;
    }

    public void setnVerts(int nVerts) {
        this.nVerts = nVerts;
    }
}