package Lab05;

public class Fisher {
	double b0;
	double b1;
	double b2;
	double b3;
    double b12;
    double b23;
    double b11;
    double b22;
    double b33;
	double[] ycp;
	double sum;
	int m;
	int n;
	int d;
	double sb;
	double fp;
	double[][] matrixPlan;
    private double[] arrY;

    Fisher(double b0, double b1, double b2, double b3, double b12, double b23, double b11, double b22, double b33,
           double[] ycp, int m, int n, int d, double sb, double[][] matrixPlan, double [] arrY){
		this.b0 = b0;
		this.b1 = b1;
		this.b2 = b2;
		this.b3 = b3;
		this.ycp = ycp;
		this.m = m;
		this.n = n;
		this.d = d;
		this.sb = sb;
		this.matrixPlan = matrixPlan;
        this.arrY = arrY;
        start();
	}
	public void start(){
		System.out.println("Fisher's test:");
        for(int i = 0; i < arrY.length; i++){
            sum += Math.pow((arrY[i]-ycp[i]), 2);
        }
		sum = m*sum/(n-d);
		System.out.println("S^2 = "+sum);
		fp = sum/sb;
		System.out.println("Fp = "+fp);
        if(fp < 2.8){
            System.out.println("The regression equation is adequate to the original at a significance level 0.05");
        }
	}
}

