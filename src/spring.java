package proba;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.FileNotFoundException;

public class Proba{
	 public static void main(String[] args) throws IOException {
        
		 
		 Spiral nomer = new Spiral(); // создание объекта
	        nomer.Sp(args);
	        nomer.adres="C://Users//Админ//Documents//output1.obj";
	        nomer.rastyagenie=(float) 0.1;
	        nomer.Sp(args);
	        nomer.Sp(args);
	        nomer.adres="C://Users//Админ//Documents//output2.obj";
	        nomer.rastyagenie=(float) 0.2;
	        nomer.Sp(args);
	        nomer.Sp(args);
	        nomer.adres="C://Users//Админ//Documents//output3.obj";
	        nomer.rastyagenie=(float) 0.3;
	        nomer.Sp(args);
	        nomer.Sp(args);
	        nomer.adres="C://Users//Админ//Documents//output4.obj";
	        nomer.rastyagenie=(float) 0.4;
	        nomer.Sp(args);
	        nomer.Sp(args);
	        nomer.adres="C://Users//Админ//Documents//output5.obj";
	        nomer.rastyagenie=(float) 0.5;    
	        nomer.Sp(args);
	    }
}

class Spiral {
	public   String adres;
	static float k1; 
	static float k2;
	static int n=100;
	static float a;
	static float d=(float)0.02;
	public float rastyagenie;
		public  void Sp(String[] args){
			new Spiral().makeSpring("output",200,1, 25,rastyagenie);//подключение метода makeSpring;
			int t=0;
			float X; //координаты спирали;
			float Y;
			float Z;
			int m=8; //количество точек в сечении;
			float [][]matrixA; //объявление массива для вывода координт спирали;
			matrixA=new float [n][3];
			try{
				PrintWriter pw = new PrintWriter(new File(adres));
			for (int k=1;k<=m;k++){
				for (int i=0; i<n; i++){	
				if (t<=n) {
					a=(float) (0.15+d*(Math.sin(k*2*3.14/m)));
					t=t+1;
				    X=(float) (k1*t+d*(Math.cos(k*2*3.14/m)));
					Y=(float) (a*(Math.sin(k2*t)));
					Z=(float) (a*(Math.cos(k2*t)));
					pw.print("v " + X +" " + Y +" " + Z + " ");
					pw.println();}}
				pw.println();
				t=0;}
			int x1; //объявление вершин граней;
			int y1;
			int z1;
			int nb=100;
			for (int i=1;i<n;i++){
			for (int j=0;j<7;j++){	
				x1=j*nb+i;
				y1=j*nb+i+1;
				z1=(j+1)*nb+i+1;
				pw.print("f " +x1+"// "+y1+"// "+z1+"//" );
				pw.println();
				x1=j*nb+i;
				y1=(j+1)*nb+i+1;
				z1=(j+1)*nb+i;
				pw.print("f " +x1+"// "+y1+"// "+z1+"//" );
				pw.println();}
			int j=7;
				x1=j*nb+i;
				y1=j*nb+i+1;
				z1=i+1;
				pw.print("f " +x1+"// "+y1+"// "+z1+"//" );
				pw.println();
				x1=j*nb+i;
				y1=i+1;
				z1=i;
				pw.print("f " +x1+"// "+y1+"// "+z1+"//" );
				pw.println();}
			pw.close();}
			catch(Exception e){e.printStackTrace();} }
				

		 public void makeSpring (String output, int n, float radiusOfSpring, int numberOfCoils, float spaceBetveanCoils){
			 float L=numberOfCoils*spaceBetveanCoils;
				 k1=(numberOfCoils*spaceBetveanCoils)/n;
				float m=n/numberOfCoils;
	 k2=(float) ((2*3.14)/m);
	 a=radiusOfSpring;
		 }}

