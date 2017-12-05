package Lab06;

public class Kohren {
	double[][] matrixOtklic;
	double[] ycp;
	double[] dispers;
	double G;
	double max;
	Kohren(double[] ycp, double[][] matrixOtklic){
		this.ycp = ycp;
		this.matrixOtklic = matrixOtklic;
		kohren();
	}
	public void kohren(){
		System.out.println("Kohren dispersion check :");
        System.out.println("Dispersion per row:");
		dispers = new double[14];
		for(int i=0; i< dispers.length; i++){
			dispers[i] = (Math.pow((matrixOtklic[i][0]-ycp[i]),2)+
						Math.pow((matrixOtklic[i][1]-ycp[i]),2))/2;
			System.out.println("S2{y"+(i+1)+"} = "+dispers[i]);
		}
		G = 0;
		max = dispers[0];
		for(int i=0; i<dispers.length; i++){
			G += dispers[i];
			if(dispers[i]>max){
				max = dispers[i];
			}
		}
		G = max/G;
		System.out.println("G = "+G);
		if(G<0.4709){
			System.out.println("All is fine");
		}else{
			System.out.println("Dispersion is not fine");
		}
	}
}

