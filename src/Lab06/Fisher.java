package Lab06;

public class Fisher {
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
	double[] ycp;
	double sum;
	int m;
	int n;
	int d;
	double sb;
	double fp;
	double[][] matrixPlan;
	Fisher(double b0, double b1, double b2, double b3,double b4,double b5,double b6,double b7,
			double b8,double b9,double b10,double[] ycp, int m, int n, double sb, double[][] matrixPlan, int d ){
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
		this.ycp = ycp;
		this.m = m;
		this.n = n;
		this.d = d;
		this.sb = sb;
		this.matrixPlan = matrixPlan;
		start();
	}
	public void start(){
		System.out.println("\nFisher:");
		sum = (Math.pow((b0+b3*matrixPlan[0][2]-ycp[0]), 2)+Math.pow((b0+b3*matrixPlan[1][2]-ycp[1]), 2)+
				Math.pow((b0+b3*matrixPlan[2][2]-ycp[2]), 2)+Math.pow((b0+b3*matrixPlan[3][2]-ycp[3]), 2)+
				Math.pow((b0+b3*matrixPlan[4][2]-ycp[4]), 2)+Math.pow((b0+b3*matrixPlan[5][2]-ycp[5]), 2)+
				Math.pow((b0+b3*matrixPlan[6][2]-ycp[6]), 2)+Math.pow((b0+b3*matrixPlan[7][2]-ycp[7]), 2));
		sum = m*sum/(n-d);
		System.out.println("S^2 = "+sum);
		fp = sum/sb;
		System.out.println("Fp = "+fp);
	}
}