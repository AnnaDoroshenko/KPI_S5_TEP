package Lab05;

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
    double b12;
    double b23;
    double b11;
    double b22;
    double b33;
	double sb;
    int di = 0;
    double [] arrY = new double[15];

	Student(double b0, double b1, double b2,double b3, double b12, double b23, double b11, double b22, double b33,
            double[][] matrixPlan, double[] ycp, double[][] matrixOtklic){
        this.b12 = b12;
        this.b23 = b23;
        this.b11 = b11;
        this.b22 = b22;
        this.b33 = b33;
        this.ycp = ycp;
		this.matrixPlan = matrixPlan;
		this.matrixOtklic = matrixOtklic;
		this.b0 = b0;
		this.b1 = b1;
		this.b2 = b2;
		this.b3 = b3;
		student();
	}
	public void student(){
		b = new double[9];
		t = new double[9];
		System.out.println("Checking the homogeneity of dispersion by Student's test:");
        System.out.println("Finding of line dispersions:");
		dispers = new double[15];
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
			b[i] /= 15;
			System.out.println("B"+i+" = "+b[i]);
		}
		for(int i=0; i<t.length; i++){
			t[i] = Math.abs(b[i])/sum;
			System.out.println("t"+i+" = "+t[i]);
		}
        for(int i=0; i<t.length; i++){
            if(t[i]>2.131){
                di++;
            }
        }

		System.out.println("The regression equation:");
		System.out.println("y = "+b0+"+"+b1+"*x1+"+b2+"*x2+"+b3+"*x3+"+b23+"*x23+"+b11+"*x1^2+"+b22+"*x2^2+"+b33+"*x3^2");
        for(int i = 0; i < ycp.length; i++){
            arrY[i] = b0+b1*matrixPlan[i][1]+b2*matrixPlan[i][2]+b3*matrixPlan[i][3]+b23*matrixPlan[i][5]+
                    b11*matrixPlan[i][6]+b22*matrixPlan[i][7]+b33*matrixPlan[i][8];
            System.out.println("Y["+(i+1)+"] = "+arrY[i]);
        }
	}
	public double getSb(){
		return sb;
	}
    public int getDi(){
		return di;
	}

    public double[] getArrY(){
        return arrY;
    }
}

