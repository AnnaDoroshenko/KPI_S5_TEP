package Lab05;

public class Regresion {
	public Regresion() {
		start();
	}
	public void start(){
		double[] ycp = new double[15];
		double a0, a1, a2, a3, a12, a23, a11, a22, a33;
        double p = 3;
		double b0 = 0;
		double b1 = 0;
		double b2 = 0;
        double b3 = 0;
		double b12 = 0;
		double b23 = 0;
		double b11 = 0;
		double b22 = 0;
		double b33 = 0;
        double x1min = 10;
        double x2min = -15;
        double x3min = -15;
        double x1max = 40;
        double x2max = 35;
        double x3max = 5;
        double x10, x20, x30;
        double dX1, dX2, dX3;
		double[][] matrixPlanKoef = {{1,-1,   -1,    -1,     1, 1, 0.27,  0.27, 0.27},
								     {1,-1,    1,     1,    -1, 1, 0.27,  0.27, 0.27},
								     {1, 1,   -1,     1,    -1,-1, 0.27,  0.27, 0.27},
								     {1, 1,    1,    -1,     1,-1, 0.27,  0.27, 0.27},
								     {1,-1,   -1,     1,     1,-1, 0.27,  0.27, 0.27},
								     {1,-1,    1,    -1,    -1,-1, 0.27,  0.27, 0.27},
								     {1, 1,   -1,    -1,    -1, 1, 0.27,  0.27, 0.27},
								     {1, 1,    1,     1,     1, 1, 0.27,  0.27, 0.27},
                                     {1,-1.215,0,     0,    -0, 0, 0.746,-0.73,-0.73},
                                     {1, 1.215,0,     0,     0, 0, 0.746,-0.73,-0.73},
                                     {1, 0,   -1.215, 0,    -0,-0,-0.73, 0.746,-0.73},
                                     {1, 0,    1.215, 0,     0, 0,-0.73, 0.746,-0.73},
                                     {1, 0,    0,    -1.215, 0,-0,-0.73, -0.73, 0.746},
                                     {1, 0,    0,     1.215, 0, 0,-0.73, -0.73, 0.746},
                                     {1, 0,    0,     0,     0, 0,-0.73, -0.73,-0.73}};

		double[][] matrixPlan = {{-7,     -7,      -4,     49,    28, 3.025,  3.16, 4.89},
								 {-7,      9,      10,    -63,    90, 3.025,  3.16, 4.89},
                                 { 8,     -7,      10,    -56,   -70, 3.025,  3.16, 4.89},
								 { 8,      9,      -4,     72,   -36, 3.025,  3.16, 4.89},
								 {-7,     -7,      10,     49,   -70, 3.025,  3.16, 4.89},
								 {-7,      9,      -4,    -63,   -36, 3.025,  3.16, 4.89},
								 { 8,     -7,      -4,    -56,    28, 3.025,  3.16, 4.89},
								 { 8,      9,      10,     72,    90, 3.025,  3.16, 4.89},
                                 {-8.6125, 1,       3,-8.6125,     3, 6.095, -4.84,-2.11},
                                 { 9.6125, 1,       3, 9.6125,     3, 6.095, -4.84,-2.11},
                                 { 0.5,-8.72,       3,  -4.36,-26.16,-4.975, 6.968,-2.11},
                                 { 0.5,10.72,       3,   5.36, 32.16,-4.975, 6.968,-2.11},
                                 { 0.5,    1,  -5.505,    0.5,-5.505,-4.975, -4.84,8.222},
                                 { 0.5,    1,  11.505,    0.5,11.505,-4.975, -4.84,8.222},
                                 { 0.5,    1,       3,    0.5,     3,-4.975, -4.84,-2.11}};
		double[][] matrixOtklic = {{ 128.403, 121.568},
								   { 675.054, 673.236},
								   {-305.011,-302.028},
								   { -137.98,-137.894},
								   {-341.999,-340.789},
								   {-176.145,-178.562},
								   { 165.388, 165.468},
								   { 713.215, 713.154},
        						   {  76.234,  79.546},
        						   { 121.886, 124.324},
        						   {-210.643,-214.564},
        						   { 222.915, 222.123},
        						   { -84.798, -85.456},
        						   { 146.537, 147.462},
        						   {   6.072,  5.154}};

//		for(int i=0; i< matrixPlan.length; i++){
//			for(int j=0; j< matrixPlan[0].length; j++){
//				System.out.print(matrixPlan[i][j]+"   ");
//			}
//			System.out.println();
//		}

//        for (int i=0; i < matrixPlan.length; i++){
//            System.out.println("Y["+i+"] = "+(6.3+2.5*matrixPlan[i][0]+4.6*matrixPlan[i][1]+7.7*matrixPlan[i][2]
//            +0.0049*matrixPlan[i][3]+5.9*matrixPlan[i][4]+8.4*matrixPlan[i][5]+0.0054*matrixPlan[i][6]+2.4*matrixPlan[i][7]));
//        }


		System.out.println("The average of the response function in a row:");
		for(int i=0; i< matrixOtklic.length; i++){
			for(int j=0; j<matrixOtklic[0].length;j++){
				ycp[i] += matrixOtklic[i][j];
			}
			ycp[i] /= 2;
			System.out.println("Ycp"+(i+1)+" = "+ycp[i]);
		}


		Kohren koh = new Kohren(ycp, matrixOtklic);
        System.out.println("Calculation of coefficients b:");

        for(int i = 0; i < ycp.length; i++ ){
            b0 += ycp[i];
        }
        b0 /= ycp.length;
        for(int i = 0; i < ycp.length; i++){
            b1 += matrixPlanKoef[i][1]*ycp[i];
        }
        b1 /= Math.pow(2, p)+2*Math.pow(1.215, 2);
        for(int i = 0; i < ycp.length; i++){
            b2 += matrixPlanKoef[i][2]*ycp[i];
        }
        b2 /= Math.pow(2, p)+2*Math.pow(1.215, 2);
        for(int i = 0; i < ycp.length; i++){
            b3 += matrixPlanKoef[i][3]*ycp[i];
        }
        b3 /= Math.pow(2, p)+2*Math.pow(1.215, 2);
        for(int i = 0; i < ycp.length; i++){
            b12 += matrixPlanKoef[i][4]*ycp[i];
        }
        b12 /= Math.pow(2, p);
        for(int i = 0; i < ycp.length; i++){
            b23 += matrixPlanKoef[i][5]*ycp[i];
        }
        b23 /= Math.pow(2, p);
        for(int i = 0; i < ycp.length; i++){
            b11 += matrixPlanKoef[i][6]*ycp[i];
        }
        b11 /= 2*Math.pow(1.215, 4);
        for(int i = 0; i < ycp.length; i++){
            b22 += matrixPlanKoef[i][7]*ycp[i];
        }
        b22 /= 2*Math.pow(1.215, 4);
        for(int i = 0; i < ycp.length; i++){
            b33 += matrixPlanKoef[i][8]*ycp[i];
        }
        b33 /= 2*Math.pow(1.215, 4);
        double b0_ = 0.27*(b1+b2+b3+b12+b23+b11+b22+b33);
	  b0 -= b0_;
		System.out.println("B0 = "+b0);
		System.out.println("B1 = "+b1);
		System.out.println("B2 = "+b2);
		System.out.println("B3 = "+b3);
		System.out.println("B12 = "+b12);
		System.out.println("B23 = "+b23);
		System.out.println("B11 = "+b11);
		System.out.println("B22 = "+b22);
        System.out.println("B33 = "+b33);
        System.out.println("Y[i] = "+b0+"+"+b1+"*x1+"+b2+"*x2+"+b3+"*x3+"+b12+"*x12+"+b23+"*x23+"+b11+"*x1^2+"+b22+"*x2^2+"+b33+"*x3^2");
        for(int i = 0; i < ycp.length; i++){
            System.out.println("Y["+(i+1)+"] = "+(b0+b1*matrixPlanKoef[i][1]+b2*matrixPlanKoef[i][2]+b3*matrixPlanKoef[i][3]+
                                    b12*matrixPlanKoef[i][4]+b23*matrixPlanKoef[i][5]+
                                    b11*matrixPlanKoef[i][6]+b22*matrixPlanKoef[i][7]+b33*matrixPlanKoef[i][8]));
        }

        Student stud = new Student(b0,b1,b2,b3,b12,b23,b11,b22,b33,matrixPlanKoef, ycp, matrixOtklic);

        Fisher fish = new Fisher(b0,b1,b2,b3,b12,b23,b11,b22,b33,ycp,matrixOtklic[0].length,matrixPlan.length, stud.getDi(), stud.getSb(), matrixPlan, stud.getArrY());

        x10 = (x1max+x1min)/2;
        x20 = (x2max+x2min)/2;
        x30 = (x3max+x3min)/2;
        dX1 = Math.abs(x1max-x1min)/2;
        dX2 = Math.abs(x2max-x2min)/2;
        dX3 = Math.abs(x3max-x3min)/2;
        System.out.println("x10 = "+x10);
        System.out.println("x20 = "+x20);
        System.out.println("x30 = "+x30);
        System.out.println("dx1 = "+dX1);
        System.out.println("dx2 = "+dX2);
        System.out.println("dx3 = "+dX3);

        a0 = b0+(b11*Math.pow(x10,2))/Math.pow(dX1,2)+(b22*Math.pow(x20,2))/Math.pow(dX2,2)+(b33*Math.pow(x30,2))/Math.pow(dX3,2)+
        (b1*x10)/dX1+(b2*x20)/dX2+(b3*x30)/dX3+(b12*x10*x20)/(dX1*dX2)+(b23*x20*x30)/(dX2*dX3);
        a1 = b1/dX1-2*b11*x10/Math.pow(dX1,2)-b12*x20/(dX1*dX2);
        a2 = b2/dX2-2*b22*x20/Math.pow(dX2,2)-b12*x10/(dX1*dX2)-b23*x30/(dX2*dX3);
        a3 = b3/dX3-2*b33*x30/Math.pow(dX3,2)-b23*x20/(dX2*dX3);
        a12 = b12/(dX1*dX2);
        a23 = b23/(dX2*dX3);
        a11 = b11/Math.pow(dX1, 2);
        a22 = b22/Math.pow(dX2, 2);
        a33 = b33/Math.pow(dX3, 2);

        System.out.println("a0 = "+a0);
        System.out.println("a1 = "+a1);
        System.out.println("a2 = "+a2);
        System.out.println("a3 = "+a3);
        System.out.println("a12 = "+a12);
        System.out.println("a23 = "+a23);
        System.out.println("a11 = "+a11);
        System.out.println("a22 = "+a22);
        System.out.println("a33 = "+a33);
        System.out.println("Y = "+a0+a1+"*x1+"+a2+"*x2+"+a3+"*x3+"+a12+"*x1*x2+"+a23+"*x2*x3+"+a11+"*x1^2+"+a22+"*x2^2+"+a33+"*x3^2+");
        for(int i = 0; i < ycp.length; i++){
            ycp[i] = -a0+a1*matrixPlan[i][0]+a2*matrixPlan[i][1]+a3*matrixPlan[i][2]+a12*matrixPlan[i][0]*matrixPlan[i][1]
                    +a23*matrixPlan[i][1]*matrixPlan[i][2]+a11*matrixPlan[i][0]*matrixPlan[i][0]
                    +a22*matrixPlan[i][1]*matrixPlan[i][1]+a33*matrixPlan[i][2]*matrixPlan[i][2];
            System.out.println("Y["+(i+1)+"] = "+ycp[i]);
        }
    }
}

