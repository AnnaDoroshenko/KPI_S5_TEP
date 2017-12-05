/**
 * Theory of experiment planning
 * Lab 8
 *
 * @variant: 7
 * @authors: Igor Boyarshin, Anna Doroshenko
 * @group: IO-52
 * @date: 06.12.2017
 */

package Lab08;

public class Lab08 {
    public static void main(String[] args) {
//ПУНКТ 1
// коэффициенты уравнения регрессии
// Подпункт 1
        double b0=5;
        double b1=16;
        double b2=30;
        double b3=67;
// Подпункт 2
        double p=0.95;
// Подпункт 3
// кодированные значения факторов припроведении ПФЭ (X0=1)
        double [][] mx = {{1, -1, -1,- 1},
                {1, -1, 1, 1},
                {1, 1, -1, 1},
                {1, 1, 1, -1},
                {1, -1, -1, 1},
                {1, -1, 1, -1},
                {1, 1, -1, -1},
                {1, 1, 1, 1}
        };
// Заполнение матрицы значений функций отклика при проведени экспериментов
// Подпункт 4
        double dy=0.1;
// Подпункт 5
        int m=2;
        int dm=2;
// Подпункт 6
        int n=8;
        boolean f=true;
        double[][] y=new double [n][m];;
        double[][] yy= new double [n][m];;
// Поиск m при котором Дисперсия однородна.
        while (f) {
            y= new double [n][m];
            yy= new double [n][m];
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < m; j++) {
                    yy[i][j]=Math.random();
                }
            }
            f=false;
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < m; j++) {
                    y[i][j]=(mx[i][0]*b0+mx[i][1]*b1+mx[i][2]*b2+mx[i][3]*b3)*(1+(
                            2*yy[i][j]*10000/10000 - 1)*dy);
                }
            }

//Вывод матрицы планирования
            for (int i = 0; i < n; i++) {
                System.out.print(mx[i][0]+ " " + mx[i][1]+ " " + mx[i][2]+ " " + mx[i][3]+ " ");

                for (int j = 0; j < m; j++) {
                    System.out.print(y[i][j]+ " ");
                }
                System.out.println();
            }

// Среднее значение функции отклика по строке.
            double[] yr= new double[n];
            for (int i = 0; i < n; i++) {

                for (int j = 0; j < m; j++) {
                    yr[i]=yr[i]+y[i][j];
                }
                yr[i]=yr[i]/m;
            }

            System.out.println("y middle");
            for (int i = 0; i < yr.length; i++) {
                System.out.println(yr[i]);
            }
// Дисперсия
            double[] disp = new double[n];
            for (int i = 0; i < n; i++) {

                for (int j = 0; j < m; j++) {
                    disp[i]= disp[i]+(yr[i]-y[i][j])*(yr[i]- y[i][j]);
                }
                disp[i]= disp[i]/m;
            }

            System.out.println("disp y");
            for (int i = 0; i < n; i++) {

                System.out.println(disp[i]);
            }

//Сумма дисперсий
            double dsum=0;
//Максимальная дисперсия
            double dmax=0;
            for (int i = 0; i < disp.length; i++) {
                if (disp[i]>dmax) dmax=disp[i];
                dsum=dsum+ disp[i];
            }

            double Gp=dmax/dsum;
            double Gt=0;
            if (m==2)
                Gt=0.6798;
            if (m==3)
                Gt=0.5157;
            if (m==4)
                Gt=0.4377;
            if (m==5)
                Gt=0.3910;
            if (m==6)
                Gt=0.3595;
//Проверка на однородность
            if (Gp<Gt)
            System.out.println("Disp odnorodna " + Gp + "<" + Gt);
else{System.out.println("Disp neodnorodna " + Gp + ">" + Gt);
                f=true;
                m=m+dm;}
        }
// ПУНКТ 2
//Погрешности

        double[] ddy=new double [5];
        ddy[0]=0.1;
        ddy[1]=0.05;
        ddy[2]=0.02;
        ddy[3]=0.01;
        ddy[4]=0.001;
        for (int k = 0; k < 5; k++) {
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < m; j++) {
                    y[i][j]=(mx[i][0]*b0+mx[i][1]*b1+mx[i][2]*b2+mx[i][3]*b3)*(1+(
                            2*yy[i][j]*10000/10000 - 1)*ddy[k]);
                }
            }
            for (int i = 0; i < n; i++) {
                System.out.print(mx[i][0]+ " " +mx[i][1]+ " " +mx[i][2]+ " " +mx[i][3]+ " ");
                for (int j = 0; j < m; j++) {
                    System.out.print(y[i][j]+ " ");
                }
                System.out.println();

            }
// Среднее значение функции отклика по строке.
            double[] yr= new double[n];
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < m; j++) {
                    yr[i]=yr[i]+y[i][j];
                }
                yr[i]=yr[i]/m;
            }
            System.out.println("y middle");
            for (int i = 0; i < yr.length; i++) {
                System.out.println(yr[i]);
            }
            double[] B= new double[5];
            double[] sigma = new double[5];
            double a11=0;
            double a22=0;
            double a33=0;
            double a1=0;
            double a2=0;
            double a3=0;
            double a4=0;
            double a5=0;
            double a6=0;
            double my=0;
            for (int i = 0; i < n; i++) {
                my=my+yr[i];

                a1= a1+mx[i][1]*mx[i][1];
                a4= a4+mx[i][2]*mx[i][2];
                a6= a6+mx[i][3]*mx[i][3];
                a2= a2+mx[i][1]*mx[i][2];
                a3= a3+mx[i][1]*mx[i][3];
                a5= a5+mx[i][2]*mx[i][3];
                a11=a11+mx[i][1]*yr[i];
                a22=a22+mx[i][2]*yr[i];
                a33=a33+mx[i][3]*yr[i];
            }

            my=my/n;
            a1= a1/n;
            a4= a4/n;
            a6= a6/n;
            a2= a2/n;
            a3= a3/n;
            a5= a5/n;
            a11=a11/n;
            a22=a22/n;
            a33=a33/n;
// Вычисление определителей
            double d0=my*(a1*a4*a6+a2*a5*a3+ a2*a5*a3-a3*a4*a3- a2*a2*a6-

                    a1*a5*a5);

            double d1=1*(a11*a4*a6+a2*a5*a33+a22*a5*a3-a3*a4*a33-

                    a22*a2*a6-a11*a5*a5);

            double d2=1*(a1*a22*a6+a11*a5*a3+a2*a33*a3-a3*a22*a3-

                    a2*a11*a6-a1*a33*a5);

            double d3=1*(a1*a4*a33+a2*a22*a3+a2*a5*a11-a3*a4*a11-

                    a2*a2*a33-a1*a5*a22);

            double d=(a1*a4*a6+a2*a5*a3+ a2*a5*a3-a3*a4*a3- a2*a2*a6-

                    a1*a5*a5);
// Нахождение коэффициентов регрессии

            B[0]=d0/d;
            B[1]=d1/d;
            B[2]=d2/d;
            B[3]=d3/d;
            System.out.println("dy=" + ddy[k]);
            System.out.println(B[0]+"+"+B[1]+"*x1+"+B[2]+"*x2+"+B[3]+"*x3");
// Погрешности
            sigma[0]=Math.abs(B[0]-b0)/B[0];
            sigma[1]=Math.abs(B[1]-b1)/B[1];
            sigma[2]=Math.abs(B[2]-b2)/B[2];
            sigma[3]=Math.abs(B[3]-b3)/B[3];
            System.out.println("sigma0=" + sigma[0]+ " sigma1=" + sigma[1]+ " sigma2=" + sigma[2] +  " sigma3=" + sigma[3]);

        }

    }
}
