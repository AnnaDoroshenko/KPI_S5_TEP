package Lab06;

public class Student {
	double[][] matrixOtklic;
	double[] ycp;
	double[][] matrixPlan;
	double[] dispers;
	double sum;
	double[] b;
	double[] t;
	double b0;
	double b1;
	double b2;
	double b3;
	double b4;
	double b5;
	double b6;
	double b7;
	double b8;
	double b9;
	double b10;
	double sb;
	int d = 0;

	Student(double b0, double b1, double b2,double b3,double b4,double b5,double b6,double b7,
			double b8,double b9,double b10,double[][] matrixPlan, double[] ycp, double[][] matrixOtklic){
		this.ycp = ycp;
		this.matrixPlan = matrixPlan;
		this.matrixOtklic = matrixOtklic;
		this.b0 = b0;
		this.b1 = b1;
		this.b2 = b2;
		this.b3 = b3;
		this.b4 = b4;
		this.b5 = b5;
		this.b6 = b6;
		this.b7 = b7;
		this.b8 = b8;
		this.b9 = b9;
		this.b10 = b10;
		student();
	}

	public void student(){
		b = new double[10];
		t = new double[10];
		System.out.println("Student dispersion check:");
		dispers = new double[10];
		for(int i=0; i< dispers.length; i++){
			dispers[i] = (Math.pow(((double)matrixOtklic[i][0]-ycp[i]),2)+
						Math.pow(((double)matrixOtklic[i][1]-ycp[i]),2))/2;
			System.out.println("S2{y"+(i+1)+"} = "+dispers[i]);
		}
		sum=0;
		for(int i=0; i<dispers.length; i++){
			sum += dispers[i];
		}
		sum /= matrixPlan.length;
		sb = sum;
		System.out.println("S2b = "+sum);
		sum /= matrixPlan.length*matrixOtklic[0].length;
		System.out.println("S^2(Bs) = "+sum);
		sum = Math.pow(sum, 0.5);
		System.out.println("S(Bs) = "+sum);
		for(int i=0; i<matrixPlan[0].length; i++){
			for(int j=0; j<matrixPlan.length; j++){
				b[i] += matrixPlan[j][i]*ycp[j];
			}
			b[i] /= matrixPlan[0].length;
			System.out.println("B"+i+" = "+b[i]);
		}
		for(int i=0; i<t.length; i++){
			t[i] = Math.abs(b[i])/sum;
			System.out.println("t"+i+" = "+t[i]);
		}
		for(int i=0; i<t.length; i++){
			if(t[i] > 2.131){
				d++;
			}
		}
	}
	public double getSb(){
		return sb;
	}
	
	public int getDi(){
		return d;
	}
}
