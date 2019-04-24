/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tubessbd;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
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
        if (Integer.parseInt(rekord) <= 100000){
            System.out.print(">> Nama Tabel : ");
            tabel = input.next();
            cariRekord(hasiltxt,rekord,tabel);
        }
        else {
            System.out.println("Maaf banyak rekord hanya 100000");
        }
        
    }
    
    public static void menu4(){
        System.out.println("menu 4");
    }
    
    public static void menu5(){
        System.out.println("menu 5");
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
            menu4();
        } else if (pilihan == 5){
            menu5();
        }
    }
    
}
