package Task1;

/*
Задание 1 "IP-адреса"
	IpV4 адрес состоит из 4 октетов, значит его можно хранить в переменной типа int32.
	Реализуйте пару функций, которые однозначно преобразуют строковое представление IpV4 адрес (вида "127.0.0.1") в значение типа int32 и наоборот.

	Примеры:
	2149583360 ==> "128.32.10.0"
	255        ==> "0.0.0.255"

 */

import java.util.Scanner;

public class Main {
    public static void Convert(String ip){
        long result=0;
        boolean res = ip.matches("^([0-9]|[0-9][0-9]|1[0-9][0-9]|2[0-5][0-5])?\\.([0-9]|[0-9][0-9]|1[0-9][0-9]|2[0-5][0-5])?\\.([0-9]|[0-9][0-9]|1[0-9][0-9]|2[0-5][0-5])?\\.([0-9]|[0-9][0-9]|1[0-9][0-9]|2[0-5][0-5])?$");
        if(res){
            System.out.println("to int");
            String [] octets = ip.split("[.]");
            int [] num = new int[octets.length];
            for (int i = 0; i <octets.length ; i++) {
                num[i]=Integer.parseInt(octets[i]);
            }
            result= (long) ((num[0]*Math.pow(256,3))
                    +(num[1]*Math.pow(256,2))
                    +(num[2]*Math.pow(256,1))
                    +(num[3]*Math.pow(256,0)));

            System.out.println(result);
        }else {
            System.out.println("to ipv4");
            long i = Long.parseLong(ip);
            System.out.println(((i >> 24) & 0xFF) +
                    "." + ((i >> 16) & 0xFF) +
                    "." + ((i >> 8) & 0xFF) +
                    "." + (i & 0xFF));
        }
    }


    public static void main(String[] args) {
        System.out.println("Введите данные по примеру(0.0.0.255 или 255)");
        Convert(new Scanner(System.in).next());
    }
}
