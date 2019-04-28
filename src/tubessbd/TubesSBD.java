/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tubessbd;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import static java.nio.file.Files.lines;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.lang.Math;

/**
 *
 * @author Rizal Kusuma Putra
 */
public class TubesSBD {

    /**
     * @param args the command line arguments
     */
    public static int cariBFR(int indeks, String[] hasiltxt){
        //ambil nilai B 
        String[] data = hasiltxt[0].split(";");
        String[] data1 = data[1].split(" ");
        int B = Integer.parseInt(data1[1]);

        //ambil nilai R
        String[] nilai = hasiltxt[indeks].split(";");
        int i = 0;
        boolean status = false;
//        for(int j = 0; j < nilai.length; j++){
//            System.out.println(nilai[j]);
//        }
        while (i < nilai.length && !status){
            if (nilai[i].contains("R ")){
                status = true;
            }
            else {
                i++;
            }
        }
        String[] nilaiR = nilai[i].split(" ");
        int R = Integer.parseInt(nilaiR[1]);
        int BFR = Math.floorDiv(B,R);
        return BFR;
    }
    
    public static int cariFAN(int indeks, String[] hasiltxt){
        //ambil nilai B 
        String[] data = hasiltxt[0].split(";");
        String[] data1 = data[1].split(" ");
        int B = Integer.parseInt(data1[1]);

        //ambil nilai P
        String[] data2 = data[0].split(" ");
        int P = Integer.parseInt(data2[1]);

        //ambil nilai V
        String[] nilai = hasiltxt[indeks].split(";");
        int i = 0;
        boolean status = false;
//        for(int j = 0; j < nilai.length; j++){
//            System.out.println(nilai[j]);
//        }
        while (i < nilai.length && !status){
            if (nilai[i].contains("V ")){
                status = true;
            }
            else {
                i++;
            }
        }
        String[] nilaiV = nilai[i].split(" ");
        int V = Integer.parseInt(nilaiV[1]);
        
        int FAN = Math.floorDiv(B, (V+P));
        return FAN;
    }
    
    public static double jumlahBlok(int indeks, String[] hasiltxt, int BFR){
        //ambil nilai n
        String[] data = hasiltxt[indeks].split(";");
        boolean status = false;
        int i = 0;
        while (i < data.length && !status){
            if (data[i].contains("n ")){
                status = true;
            }
            else {
                i++;
            }
        }
        String[] nilaiN = data[i].split(" ");
        double N = Integer.parseInt(nilaiN[1]);

        double temp = N/BFR;
        double blok = Math.ceil(temp);
        return blok;
    }
    
    public static double jumlahIndex(int indeks, String[] hasiltxt, int FAN){
        //ambil nilai n
        String[] data = hasiltxt[indeks].split(";");
        boolean status = false;
        int i = 0;
        while (i < data.length && !status){
            if (data[i].contains("n ")){
                status = true;
            }
            else {
                i++;
            }
        }
        String[] nilaiN = data[i].split(" ");
        double N = Integer.parseInt(nilaiN[1]);

        double temp = N/FAN;
        double index = Math.ceil(temp);
        return index;
    }
    
    public static void cariRekord(String[] hasiltxt, String rekord, String tabel){
        boolean status = false;
        int i = 0;
        while (i < hasiltxt.length && !status){
            if (hasiltxt[i].contains(tabel)){
                status = true;
            }
            else {
                i++;
            }
        }

        int BFR = cariBFR(i,hasiltxt);
        int FAN = cariFAN(i,hasiltxt);
        double rekordD = Double.parseDouble(rekord);

        double temp1 = rekordD/FAN;
        double temp2 = rekordD/BFR;
        
        double index = Math.ceil(temp1);
        double nindex = Math.ceil(temp2);
        System.out.println("Menggunakan indeks, jumlah blok yang diakses : "+index);
        System.out.println("Tanpa indeks, jumlah blok yang diakses : "+nindex);
    }
    
    public static String[] olahQuery(String query){
        String q1 = query.replace(";"," ;");             //penambahan spasi di;
        String q2 = q1.replace(",", " ");             //mengganti koma(,) dengan spasi ( )
        String q3 = q2.replace("("," ");            //mengganti buka kurung dengan spasi
        String q4 = q3.replace(")"," ");           //mengganti tutup kurung dengan spasi
//        String q5 = q4.replace("="," ");           //mengganti sama dengan dengan spasi
        String q5 = q4.replace("  "," ");
        String q6 = q5.replace("   "," ");
        
        
        String[] array = q6.split(" ");
        return array;
    }
    
    public static boolean cekPenulisan(String[] array){
        if(array[0].equalsIgnoreCase("select") && array[array.length-1].equalsIgnoreCase(";")){
            return true;
        }
        else {
            return false;
        }
    }
    
    public static String cariTabel(String[] array){
        int i = 0;
        String tabel = null;
        while((i < array.length) && (!array[i].equalsIgnoreCase("from"))){
            i++;
        }
        if (array[i].equalsIgnoreCase("from")){
            int j = i;
            i = 0;
            while((i < array.length) && (!array[i].equalsIgnoreCase(array[j+1]))){
                i++;
            }
            if (array[i].equalsIgnoreCase(array[j+1])){
                tabel = array[i];
            }
            else {
                tabel = "Eror";
            }
        }
        return tabel;
    }
    
    public static String[] ubahHasilTxt(String[] hasiltxt){
        String[] data = hasiltxt[0].split(";");
        ArrayList<String> myList = new ArrayList<String>(Arrays.asList(data));
        
        String[] temp = hasiltxt[1].split(";");
        for (int i = 0; i < temp.length; i++){
            myList.add(temp[i]);
        }
        temp = hasiltxt[2].split(";");
        for (int i = 0; i < temp.length; i++){
            myList.add(temp[i]);
        }
        temp = hasiltxt[3].split(";");
        for (int i = 0; i < temp.length; i++){
            myList.add(temp[i]);
        }
        data = myList.toArray(data);
        System.out.println(Arrays.toString(data));
        return data;
    }
    
    public static int ambilN(String[] hasiltxt, String tabel){
        int i = 0;
        int N = 0;
        while ((i < hasiltxt.length) && (!hasiltxt[i].contains(tabel))){
            i++;
        }
        if (hasiltxt[i].contains(tabel)){
            String[] data = hasiltxt[i].split(";");
            boolean status = false;
            i = 0;
            while (i < data.length && !status){
                if (data[i].contains("n ")){
                    status = true;
                }
                else {
                    i++;
                }
            }
            String[] nilaiN = data[i].split(" ");
            N = Integer.parseInt(nilaiN[1]);
        }
        return N;
    }
    
    public static String kolom(String[] array, String[] hasiltxt, String tabel){
        int i = 0;
        String kolom = "";
        while ((i < hasiltxt.length) && (!hasiltxt[i].contains(tabel))){
            i++;
        }
        if (hasiltxt[i].contains(tabel)){
            int j = 1;
            int indexFrom = indexFrom(array);
            while (j < indexFrom){
                if (hasiltxt[i].contains(array[j])){
                    kolom = kolom + array[j] + ", ";
                }
                j++;
            }
        }
        else {
            System.out.println("Tidak ada tabel "+tabel);
        }
        kolom = kolom.substring(0,kolom.length()-1);
        kolom = kolom.substring(0,kolom.length()-1);

        return kolom;
    }
  
    public static int indexTabel(String tabel, String[] array){
        int i = 0;
        while ((i < array.length) && (!array[i].equalsIgnoreCase(tabel))){
            i++;
        }
        if (array[i].equalsIgnoreCase(tabel)){
            return i;
        }
        else {
            return 999;
        }
    }
    
    public static int indexFrom(String[] array){
        int i = 0;
        while((i < array.length) && (!array[i].equalsIgnoreCase("from"))){
            i++;
        }
        if (array[i].equalsIgnoreCase("from")){
            return i;
        }
        else {
            return 999;
        }
    }
    
    public static int indexJoin(String[] array){
        int i = 0;
        while((i < array.length) && (!array[i].equalsIgnoreCase("join"))){
            i++;
        }
        if (array[i].equalsIgnoreCase("join")){
            return i;
        }
        else {
            return 999;
        }
    }
    
    public static boolean cekPK(String kolom, int index){
        if (kolom.contains("id_supplier") || kolom.contains("id_supermarket")){
            return true;
        }
        else {
            return false;
        }
    }
    
    public static String ambilWhere(String[] array){
        int i = 0;
        String where = "";
        while ((i < array.length) && (!array[i].equalsIgnoreCase("where"))){
            i++;
        }
        i++;
        while (i < array.length){
            where = where + array[i] + " ";
            i++;
        }
        return where;
    }
    
    public static int hitungCost(String type, int n, String tabel, String[] array){
        int temp = 0;
        if (type == "A1"){
            temp = n/2;
        }  
        else if (type == "A2"){
            int i = 0;
            while ((i < array.length) && (!array[i].contains(tabel))){
                i++;
            }
            if (array[i].contains(tabel)){
                int FAN = cariFAN(i,array);
                temp = (int) (Math.ceil(Math.log(n)/Math.log(FAN))+1);
            }
        }
        else if (type == "A1n"){
            temp = n;
        }
        else if (type == "A1J"){
            String[] array2 = tabel.split(" ");
            int n1 = ambilN(array,array2[0]);
            int n2 = ambilN(array,array2[1]);
            temp = n1 * n2 + n1;
        }
        else if (type == "A2J"){
            String[] array2 = tabel.split(" ");
            int n1 = ambilN(array,array2[0]);
            int n2 = ambilN(array,array2[1]);
            temp = n2 * n1 + n2;
        }
        return temp;
    }
    
    public static void menu1(String[] hasiltxt){
        System.out.println("Menu 1 : BFR dan Fanout Ratio");
        
        int BFR1 = cariBFR(1,hasiltxt);
        int FAN1 = cariFAN(1,hasiltxt);
        System.out.println("    BFR Supplier : "+BFR1);
        System.out.println("    FAN Supplier : "+FAN1);
        System.out.println("");
        
        int BFR2 = cariBFR(2,hasiltxt);
        int FAN2 = cariFAN(2,hasiltxt);
        System.out.println("    BFR Supermarket : "+BFR2);
        System.out.println("    FAN Supermarket : "+FAN2);
        System.out.println("");
        
        int BFR3 = cariBFR(3,hasiltxt);
        int FAN3 = cariFAN(3,hasiltxt);
        System.out.println("    BFR Mensupply : "+BFR3);
        System.out.println("    FAN Mensupply : "+FAN3);
    }
    
    public static void menu2(String[] hasiltxt){
        System.out.println("Menu 2 : Jumlah Blok");
        
        int BFR1 = cariBFR(1,hasiltxt);
        int FAN1 = cariFAN(1,hasiltxt);
        double blok1 = jumlahBlok(1,hasiltxt,BFR1);
        double index1 = jumlahIndex(1,hasiltxt,FAN1);
        System.out.println("    Tabel data Supplier : "+blok1);
        System.out.println("    Indeks Supplier : "+index1);
        System.out.println("");
        
        int BFR2 = cariBFR(2,hasiltxt);
        int FAN2 = cariFAN(2,hasiltxt);
        double blok2 = jumlahBlok(2,hasiltxt,BFR2);
        double index2 = jumlahIndex(2,hasiltxt,FAN2);
        System.out.println("    Tabel data Supermarket : "+blok2);
        System.out.println("    Indeks Supermarket : "+index2);
        System.out.println("");
        
        int BFR3 = cariBFR(3,hasiltxt);
        int FAN3 = cariFAN(3,hasiltxt);
        double blok3 = jumlahBlok(3,hasiltxt,BFR3);
        double index3 = jumlahIndex(3,hasiltxt,FAN3);
        System.out.println("    Tabel data Mensupply : "+blok3);
        System.out.println("    Indeks Mensupply : "+index3);
    }
    
    public static void menu3(String[] hasiltxt){
        String rekord;
        String tabel;
        
        Scanner input = new Scanner(System.in);

        System.out.println("Menu 3 : Pencarian Rekord");
        System.out.println("Input : ");
        System.out.print(">> Cari Rekord ke - : ");
        rekord = input.next();
        System.out.print(">> Nama Tabel : ");
        tabel = input.next();
        cariRekord(hasiltxt,rekord,tabel);
    }
    
    public static void menu4(String[] hasiltxt){
        String query;
        String[] hasiltxt2 = null;
        int indexTabel = 0;
        int indexTabel2 = 0;
        int indexJoin = 0;
                
        Scanner input = new Scanner(System.in);
        
        System.out.println("Menu 4 : QEP dan Cost");
        System.out.println("    Input Query : ");
        System.out.print(">> ");
        query = input.nextLine();
        
        String[] array = olahQuery(query);
        boolean cek = cekPenulisan(array);
        if (cek) {
            String tabel = cariTabel(array);
            indexTabel = indexTabel(tabel, array);
            indexJoin = indexTabel+1;
            indexTabel2 = indexJoin+1;
            int N = ambilN(hasiltxt, tabel);
            if (array[indexTabel+1].equalsIgnoreCase("where")){
                String kolom = kolom(array,hasiltxt,tabel);
                boolean cekPK = cekPK(kolom,indexTabel);
                if (cekPK){
                    System.out.println("Tabel (1) : "+tabel);
                    System.out.println("List Kolom : "+kolom);
                    System.out.println("");
                    System.out.println("QEP #1");
                    System.out.println("PROJECTION "+kolom+" -- on the fly");
                    System.out.println("SELECTION "+ambilWhere(array)+" -- A1 key");
                    System.out.println(tabel);
                    int cost1 = hitungCost("A1",N,tabel,hasiltxt);
                    System.out.println("Cost : "+cost1);
                    System.out.println("");
                    System.out.println("QEP #2");
                    System.out.println("PROJECTION "+kolom+" -- on the fly");
                    System.out.println("SELECTION "+ambilWhere(array)+" -- A2");
                    System.out.println(tabel);
                    int cost2 = hitungCost("A2",N,tabel,hasiltxt);
                    System.out.println("Cost : "+cost2);
                    if (cost1 > cost2){
                        System.out.println("QEP optimal : QEP#2");
                        try{
                            BufferedWriter br = new BufferedWriter(new FileWriter("F:\\SBD\\TubesSBD\\src\\tubessbd//sharedpool.txt",true));
                            br.write("QEP #2");    
                            br.newLine();
                            br.write("Projection "+kolom+" -- on the fly");    
                            br.newLine();
                            br.write("SELECTION "+ambilWhere(array)+" -- A2");   
                            br.newLine();
                            br.write(tabel);   
                            br.newLine();
                            br.write("Cost (worse case): "+cost2);    
                            br.newLine();
                            br.write("");    
                            br.newLine();
                            br.close();
                        }
                        catch(Exception e){
                            System.out.println("LOAD FILE ERROR ATAU GAK NEMU ("+e+") "); //ketika gagal load data
                        }                     
                    }
                    else{
                        System.out.println("QEP optimal : QEP#1");
                        try{
                            BufferedWriter br = new BufferedWriter(new FileWriter("F:\\SBD\\TubesSBD\\src\\tubessbd//sharedpool.txt",true)); 
                            br.write("QEP #1");   br.newLine();
                            br.write("Projection "+kolom+" -- on the fly");    br.newLine();
                            br.write("SELECTION "+ambilWhere(array)+" -- A1 key");    br.newLine();
                            br.write(tabel);   br.newLine();
                            br.write("Cost (worse case): "+cost1);   br.newLine();
                            br.write("");   br.newLine();
                            br.close();
                        }
                        catch(Exception e){
                            System.out.println("LOAD FILE ERROR ATAU GAK NEMU ("+e+") "); //ketika gagal load data
                        }       
                    }
                }
                else {
                    System.out.println("Tabel (1) : "+tabel);
                    System.out.println("List Kolom : "+kolom);
                    System.out.println("");
                    System.out.println("QEP #1");
                    System.out.println("PROJECTION "+kolom+" -- on the fly");
                    System.out.println("SELECTION "+ambilWhere(array)+" -- A1 non key");
                    System.out.println(tabel);
                    int cost1 = hitungCost("A1n",N,tabel,hasiltxt);
                    System.out.println("Cost : "+cost1);
                    
                    try{
                        BufferedWriter br = new BufferedWriter(new FileWriter("F:\\SBD\\TubesSBD\\src\\tubessbd//sharedpool.txt",true)); 
                        br.write("QEP #1");   br.newLine();
                        br.write("Projection "+kolom+" -- on the fly");    br.newLine();
                        br.write("SELECTION "+ambilWhere(array)+" -- A1 non key");    br.newLine();
                        br.write(tabel);   br.newLine();
                        br.write("Cost (worse case): "+cost1);   br.newLine();
                        br.write("");   br.newLine();
                        br.close();
                    }
                    catch(Exception e){
                        System.out.println("LOAD FILE ERROR ATAU GAK NEMU ("+e+") "); //ketika gagal load data
                    }
                }
            }
            else if (array[indexTabel+1].equalsIgnoreCase("join")) {
                String kolom = kolom(array,hasiltxt,tabel);
                String kolomm = kolom(array,hasiltxt,array[indexJoin+1]);
                boolean cekPK = cekPK(kolom,indexTabel);
                if (cekPK){
                    String tabelTemp = tabel + " " + array[indexJoin+1];
                    System.out.println("Tabel (1) : "+tabel);
                    System.out.println("List Kolom : "+kolom);
                    System.out.println("Tabel (2) : "+array[indexJoin+1]);
                    System.out.println("List Kolom : "+kolomm);
                    System.out.println("");
                    System.out.println("QEP #1");
                    System.out.println("PROJECTION "+kolom+" -- on the fly");
                    System.out.println("Join "+tabel+"."+array[array.length-2]+" = "+array[indexJoin+1]+"."+array[array.length-2]+" -- BLNJ");
                    System.out.println(tabel+"  "+array[indexJoin+1]);
                    N = 0;
                    int cost1 = hitungCost("A1J",N,tabelTemp,hasiltxt);
                    System.out.println("Cost (worse case) : "+cost1);
                    System.out.println("");
                    System.out.println("QEP #2");
                    System.out.println("PROJECTION "+kolom+" -- on the fly");
                    System.out.println("Join "+tabel+"."+array[array.length-2]+" = "+array[indexJoin+1]+"."+array[array.length-2]+" -- BLNJ");
                    System.out.println(array[indexJoin+1]+" "+tabel);
                    int cost2 = hitungCost("A2J",N,tabelTemp,hasiltxt);
                    System.out.println("Cost (worse case) : "+cost2);
                    
                    if (cost1 > cost2){
                        System.out.println("QEP optimal : QEP#2");
                        try{
                            BufferedWriter br = new BufferedWriter(new FileWriter("F:\\SBD\\TubesSBD\\src\\tubessbd//sharedpool.txt",true));
                            br.write("QEP #2");    
                            br.newLine();
                            br.write("Projection "+kolomm+" -- on the fly");    
                            br.newLine();
                            br.write("Join "+tabel+"."+array[array.length-2]+" = "+array[indexJoin+1]+"."+array[array.length-2]+" -- BLNJ");   
                            br.newLine();
                            br.write(array[indexJoin+1]+" "+tabel);   
                            br.newLine();
                            br.write("Cost (worse case): "+cost2);    
                            br.newLine();
                            br.write("");    
                            br.newLine();
                            br.close();
                        }
                        catch(Exception e){
                            System.out.println("LOAD FILE ERROR ATAU GAK NEMU ("+e+") "); //ketika gagal load data
                        }                     
                    }
                    else{
                        System.out.println("QEP optimal : QEP#1");
                        try{
                            BufferedWriter br = new BufferedWriter(new FileWriter("F:\\SBD\\TubesSBD\\src\\tubessbd//sharedpool.txt",true)); 
                            br.write("QEP #1");   
                            br.newLine();
                            br.write("Projection "+kolom+" -- on the fly");    
                            br.newLine();
                            br.write("Join "+tabel+"."+array[array.length-2]+" = "+array[indexJoin+1]+"."+array[array.length-2]+" -- BLNJ");    
                            br.newLine();
                            br.write(tabel+"  "+array[indexJoin+1]);   
                            br.newLine();
                            br.write("Cost (worse case): "+cost1);   
                            br.newLine();
                            br.write("");   
                            br.newLine();
                            br.close();
                        }
                        catch(Exception e){
                            System.out.println("LOAD FILE ERROR ATAU GAK NEMU ("+e+") "); //ketika gagal load data
                        }       
                    }
                }
            }
            else {
                String kolom = kolom(array,hasiltxt,tabel);
                System.out.println("Tabel (1) : "+tabel);
                System.out.println("List Kolom : "+kolom);
            }
//            hasiltxt2 = ubahHasilTxt(hasiltxt);
//            int indexTabel = indexTabel(tabel, hasiltxt2);
        }
//        System.out.println(Arrays.toString(hasiltxt2));
//        for (int i = 0; i < array.length; i++){
//            System.out.println(array[i]);
    }
    
    public static void menu5(){
        System.out.println("Menu 5 : Shared Pool");
        String datasss="";
        try {
            BufferedReader files = new BufferedReader(new FileReader("F:\\SBD\\TubesSBD\\src\\tubessbd//sharedpool.txt")); //baca data text
            datasss=files.readLine(); //masukan isi data ke variable baru
            while((datasss = files.readLine()) != null) {
                System.out.println(datasss);
            }   
        } 
        catch (Exception e) {
            System.out.println("LOAD FILE ERROR ATAU GAK NEMU ("+e+") "); //ketika gagal load data
                   
        }
    }
    
    public static void main(String[] args) throws IOException {
        // TODO code application logic here
        String data ="";
        try {
            BufferedReader readData = new BufferedReader(new FileReader("src\\tubessbd\\input.txt"));
            data = readData.readLine();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(TubesSBD.class.getName()).log(Level.SEVERE, null, ex);
        }
        String dataOlah = data.replace("; ",";");
        
        String[] hasiltxt = dataOlah.split("#");
//        for(int i = 0; i < hasiltxt.length; i++){
//            System.out.println(hasiltxt[i]);
//        }
        
        int pilihan;
        
        Scanner input = new Scanner(System.in);
        
        System.out.println(">> Menu Utama");
        System.out.println("    1. Tampilkan BFR dan Fanout Ratio setiap tabel");
        System.out.println("    2. Tampilkan Total Blok Data + Blok Index setiap tabel");
        System.out.println("    3. Tampilkan Jumlah Box yang diakses untuk Pencarian Rekord");
        System.out.println("    4. Tampilkan QEP dan Cost");
        System.out.println("    5. Tampilkan isi File Shared Pool");
        System.out.print(">> Masukan Pilihan Anda : ");
        pilihan = input.nextInt();
        
        if (pilihan == 1){
            menu1(hasiltxt);
        } else if (pilihan == 2){
            menu2(hasiltxt);
        } else if (pilihan == 3){
            menu3(hasiltxt);
        } else if (pilihan == 4){
            menu4(hasiltxt);
        } else if (pilihan == 5){
            menu5();
        }
    }
    
}
