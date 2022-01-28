/*
Задание 3 "Неавторизованные поставки"
	Реализуйте любым из доступных способов следующие шаги:
	1. Прочитать файл logins.csv с локальной файловой системы
	2. Прочитать файл postings.csv с локальной файловой системы (строки со значениями в поле Mat. Doc.)
	3. Добавить булевое поле "авторизованная поставка" в данные из postings.csv, которое будет указывать, что User Name (postings.csv) находится в списке AppAccountName (logins.csv) и IsActive
	4. Cохранить в SQL СУБД данные файла logins.csv
	5. Сохранить в SQL СУБД данные файла postings.csv (с дополнительным полем)
	6. Отдавать по GET (REST API) за период (день, месяц, квартал, год) данные из базы, загруженные из postings.csv (с возможностью запроса с фильтром по полю "авторизованная поставка")

	Описание postings.cvs

	Mat.Doc - номер поставки
	Item - порядковый номер товара в поставке (в одной поставке несколько товарных позиций)
	Doc. Date - дата договора
	Pstng. Date - дата поставки
	Material Description - описание материала
	Quantity - количество
	BUn - единица измерения
	Amount LC - стоимость в валюте (Crcy)
	Crcy - валюта
	User Name - пользователь, который провел поставку

	Связанные таблицы, которые не несут смысловой нагрузки (поставка и товар - несут) можно не усложнять и наделять только минимально необходимыми для парсинга этого файла атрибутами.
	Аналогично с таблицами-справочниками, можно заполнить по принципу минимально необходимого.
	API для вспомогательных таблиц можно не реализовывать.
 */


import au.com.bytecode.opencsv.CSVReader;

import java.io.*;
import java.util.*;


public class Main {

    public static void main(String[] args) throws Exception {


       //1
        //ReadAll();
        //2
       // ReadPosting();
        //3
        //AddAutomaticDelivery();
        //4
         //DB.AddLoginsToDB();
        //5
        DB.AddPostingsToDB();




    }


    public static void ReadAll() throws Exception {
        CSVReader reader = new CSVReader(new FileReader("logins.csv"), ',' , '"' , 1);
        String[] nextLine;
        while ((nextLine = reader.readNext()) != null) {
            if (nextLine != null) {
                System.out.println(Arrays.toString(nextLine));
            }
        }
    }


    public static void ReadPosting() throws Exception {
        Scanner in = new Scanner(new File("postings.csv"), "UTF-8");
        ArrayList<String> data = new ArrayList<>();
        String check;
        while (in.hasNextLine()) {
            check = in.nextLine();
            if (!check.isEmpty())
                data.add(check);
        }
        PrintWriter out = new PrintWriter(new FileWriter("postings.csv", false));
        for (String s : data)
            out.println(s);
        in.close();
        out.close();

        BufferedReader br = new BufferedReader(new FileReader("postings.csv"));
        String line;
        while ((line = br.readLine()) != null) {
            String[] cols = line.split(";");
            System.out.println(cols[0]);
        }
    }
    public static void AddAutomaticDelivery() throws Exception {
        BufferedReader br = new BufferedReader(new FileReader("postings.csv"));
        BufferedReader bfr = new BufferedReader(new FileReader("logins.csv"));
        String line;
        String sline;
        ArrayList<String> user_name = new ArrayList<>();
        while ((line = br.readLine()) != null) {
            String[] cols = line.split(";");
            user_name.add(cols[9]);
        }
        ArrayList<String> logs_name = new ArrayList<>();
        ArrayList<String> logs_isActive = new ArrayList<>();
        ArrayList<String> isActive = new ArrayList<>();
        while ((sline = bfr.readLine()) != null) {
            String[] cols = sline.split(",");
            logs_name.add(cols[1]);
            logs_isActive.add(cols[2]);
        }

        for (int i = 1; i < user_name.size() ; i++) {
            for (int j = 1; j < logs_name.size() ; j++) {
                if (user_name.get(i).equals(logs_name.get(j))){
                    isActive.add(";"+logs_isActive.get(j));
                    break;
                } if(j<=logs_name.size()){
                    isActive.add(";\tFalse");
                    break;
                }
            }
        }

        Scanner in = new Scanner(new File("postings.csv"), "UTF-8");
        ArrayList<String> data = new ArrayList<>();
        int count=0;
        String check;
        while (in.hasNextLine()) {
            check = in.nextLine();
            if (!check.isEmpty()) {
                if (count==0){
                    data.add(check+";\tAut Delivery" );
                    count++;
                }else {
                    data.add(check+isActive.get(count-1));
                }

            }
        }
        PrintWriter out = new PrintWriter(new FileWriter("postings.csv", false));
        for (String s : data)
            out.println(s);
        in.close();
        out.close();

        CSVReader reader = new CSVReader(new FileReader("postings.csv"), ',' , '"' , 0);
        String[] nextLine;
        while ((nextLine = reader.readNext()) != null) {
            if (nextLine != null) {
                System.out.println(Arrays.toString(nextLine));
            }
        }

    }


}
