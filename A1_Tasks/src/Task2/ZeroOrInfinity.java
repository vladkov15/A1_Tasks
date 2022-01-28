package Task2;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Scanner;

/*Задание 2 "Стремится к нулю или к бесконечности?"
	(n! обозначает factorial(n))
	Будет ли выражение вида:
		un = (1 / n!) * (1! + 2! + 3! + ... + n!)
	стремится к 0 или к бесконечности?
	Реализуйте функцию, которая расчитывает значение un для целочисленных n > 1 (с точностью не хуже 6 знаков после запятой).

 */

public class ZeroOrInfinity {
    public static void main(String[] args) {
        System.out.println("Введите n");
       Check(new Scanner(System.in).nextInt());
    }
    public static BigDecimal Print(int n){
        BigDecimal un = GetFactorial(n);
        double temp=un.doubleValue();
        temp=1/temp;
        un= BigDecimal.valueOf(temp);
        System.out.println("un= "+un);

       BigDecimal sum_factorial =new BigDecimal(0);
        for (int i = 1; i <=n ; i++) {
            BigDecimal helper = GetFactorial(i);
            sum_factorial=sum_factorial.add(helper);
        }
        System.out.println("sum= "+sum_factorial);
        un= un.multiply(sum_factorial);


      return un;
    };
    public static BigDecimal GetFactorial(int f) {
        if (f <= 1) {
            return BigDecimal.valueOf(1);
        }
        else {
            return BigDecimal.valueOf(f).multiply(GetFactorial(f - 1));
        }
    }
    public static void Check(int i){
        if (i>1){
            System.out.println("un ="+Print(i));
            System.out.println("стремиться к бесконечности");
        }else{
            System.out.println("проверьте введенные данные(условие n>1)");
        }

    }
}
