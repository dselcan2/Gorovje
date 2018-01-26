package com.example.denis.gorovje;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Environment;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.BitSet;

/**
 * Created by Denis on 26. 01. 2018.
 */

public class DCTcompressor {

    public static int bitsBefore;

    public static void encode(Bitmap bmp, String name){
        ArrayList<double[][]> slikaRed = new ArrayList<>();
        slikaRed = razdeli(bmp, 0);
        ArrayList<double[][]> slikaGreen = new ArrayList<>();
        slikaGreen = razdeli(bmp, 1);
        ArrayList<double[][]> slikaBlue = new ArrayList<>();
        slikaBlue = razdeli(bmp, 2);
        ArrayList<ArrayList<double[][]>> RGB = new ArrayList<>();
        RGB.add(slikaRed);
        RGB.add(slikaGreen);
        RGB.add(slikaBlue);
        for(int i=0; i<RGB.size(); i++){
            if(i==0){
                //System.out.println("RED");
            }
            else if(i==1){
                //System.out.println("GREEN");
            }
            else if(i==2){
                //System.out.println("BLUE");
            }
            for(int j=0; j<RGB.get(i).size(); j++){
                //System.out.println("before");
                //izpisi(RGB.get(i).get(j));
                RGB.get(i).set(j, applyDCT(RGB.get(i).get(j)));
                //System.out.println("after");
                //izpisi(RGB.get(i).get(j));
            }
        }
        ArrayList<double[]>koncni = new ArrayList<>();
        for(int i=0; i<RGB.size(); i++){
            double[] vstavi = new double[RGB.get(i).size()*8*8];
            int count = 0;
            for(int j=0; j< RGB.get(i).size(); j++){
                double[] temp = cikcak(RGB.get(i).get(j));
                for(int k=0; k<temp.length; k++){
                    vstavi[count] = temp[k];
                    count++;
                }
            }
            koncni.add(vstavi);
        }
        zakodiraj(koncni, name);
    }

    public static Bitmap decode(byte[] data){
        ArrayList<ArrayList<double[]>> output = dekodiraj(data);
        ArrayList<ArrayList<double[][]>> RGB = new ArrayList<>();
        double[][] test = Recikcak(output.get(0).get(0));
        for(int i=0; i<output.size(); i++){
            ArrayList<double[][]> tmp = new ArrayList<>();
            for(int j=0; j<output.get(i).size(); j++){
                //RGB.get(i).set(j, applyDCT(RGB.get(i).get(j)));
                tmp.add(applyIDCT(Recikcak(output.get(i).get(j))));
            }
            RGB.add(tmp);
        }
        int len = RGB.get(0).size();
        System.out.println(len);
        len = (int)Math.sqrt(len) * 8;
        Bitmap picture = Bitmap.createBitmap(len, len, Bitmap.Config.RGB_565);
        int x=0;
        int y=0;
        for(int i=0; i<RGB.get(0).size(); i++){
            for(int w=0; w<8; w++){
                for(int h=0; h<8; h++){
                    int R = (int)RGB.get(0).get(i)[w][h];
                    int G = (int)RGB.get(1).get(i)[w][h];
                    int B = (int)RGB.get(2).get(i)[w][h];
                    if(R == -1){
                        R=0;
                    }
                    if(G == -1){
                        G = 0;
                    }
                    if(B == -1){
                        B = 0;
                    }
                    if(R > 255){
                        R = 255;
                    }
                    if(G > 255){
                        G = 255;
                    }
                    if(B > 255){
                        B = 255;
                    }
                    picture.setPixel(x+w, y+h, Color.rgb(R,G,B));
                }
            }
            System.out.println(x);
            x+=8;
            if(x == len){
                x=0;
                y+=8;
            }
        }
        return picture;
    }

    /*public static void main(String[] args) {
        Bitmap picture;
        int mode = 1; //0 = dekode, 1 = encode

        try {
            if(mode == 1){
                //picture = ImageIO.read(new File("C:\\Users\\Denis\\Desktop\\sky.jpg"));
                ArrayList<double[][]> slikaRed = new ArrayList<>();
                slikaRed = razdeli(picture, 0);
                ArrayList<double[][]> slikaGreen = new ArrayList<>();
                slikaGreen = razdeli(picture, 1);
                ArrayList<double[][]> slikaBlue = new ArrayList<>();
                slikaBlue = razdeli(picture, 2);
                ArrayList<ArrayList<double[][]>> RGB = new ArrayList<>();
                RGB.add(slikaRed);
                RGB.add(slikaGreen);
                RGB.add(slikaBlue);
                for(int i=0; i<RGB.size(); i++){
                    if(i==0){
                        //System.out.println("RED");
                    }
                    else if(i==1){
                        //System.out.println("GREEN");
                    }
                    else if(i==2){
                        //System.out.println("BLUE");
                    }
                    for(int j=0; j<RGB.get(i).size(); j++){
                        //System.out.println("before");
                        //izpisi(RGB.get(i).get(j));
                        RGB.get(i).set(j, applyDCT(RGB.get(i).get(j)));
                        //System.out.println("after");
                        //izpisi(RGB.get(i).get(j));
                    }
                }
                ArrayList<double[]>koncni = new ArrayList<>();
                for(int i=0; i<RGB.size(); i++){
                    double[] vstavi = new double[RGB.get(i).size()*8*8];
                    int count = 0;
                    for(int j=0; j< RGB.get(i).size(); j++){
                        double[] temp = cikcak(RGB.get(i).get(j));
                        for(int k=0; k<temp.length; k++){
                            vstavi[count] = temp[k];
                            count++;
                        }
                    }
                    koncni.add(vstavi);
                }
                bitsBefore = picture.getWidth() * picture.getHeight() * 8 * 3;
                System.out.println("pred stiskanjem je slika velika " + bitsBefore + " bitov");
                zakodiraj(koncni);
            }
            else{
                //byte[] data = Files.readAllBytes(new File("C:\\Users\\Denis\\Desktop\\test.bin").toPath());
                ArrayList<ArrayList<double[]>> output = dekodiraj(data);
                ArrayList<ArrayList<double[][]>> RGB = new ArrayList<>();
                double[][] test = Recikcak(output.get(0).get(0));
                for(int i=0; i<output.size(); i++){
                    ArrayList<double[][]> tmp = new ArrayList<>();
                    for(int j=0; j<output.get(i).size(); j++){
                        //RGB.get(i).set(j, applyDCT(RGB.get(i).get(j)));
                        tmp.add(applyIDCT(Recikcak(output.get(i).get(j))));
                    }
                    RGB.add(tmp);
                }
                int len = RGB.get(0).size();
                System.out.println(len);
                len = (int)Math.sqrt(len) * 8;
                picture = Bitmap.createBitmap(len, len, Bitmap.Config.RGB_565);
                int x=0;
                int y=0;
                for(int i=0; i<RGB.get(0).size(); i++){
                    for(int w=0; w<8; w++){
                        for(int h=0; h<8; h++){
                            int R = (int)RGB.get(0).get(i)[w][h];
                            int G = (int)RGB.get(1).get(i)[w][h];
                            int B = (int)RGB.get(2).get(i)[w][h];
                            if(R == -1){
                                R=0;
                            }
                            if(G == -1){
                                G = 0;
                            }
                            if(B == -1){
                                B = 0;
                            }
                            if(R > 255){
                                R = 255;
                            }
                            if(G > 255){
                                G = 255;
                            }
                            if(B > 255){
                                B = 255;
                            }
                            picture.setPixel(x+w, y+h, Color.rgb(R,G,B));
                        }
                    }
                    System.out.println(x);
                    x+=8;
                    if(x == len){
                        x=0;
                        y+=8;
                    }
                }
                //File f = new File("C:\\Users\\Denis\\Desktop\\test.png");
                //ImageIO.write(picture, "png", f);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    */
    public static BitSet fromByteArray(byte[] bytes) {
        BitSet bits = new BitSet();
        for (int i = 0; i < bytes.length * 8; i++) {
            if ((bytes[bytes.length - i / 8 - 1] & (1 << (i % 8))) > 0) {
                bits.set(i);
            }
        }
        return bits;
    }

    public static ArrayList<ArrayList<double[]>> dekodiraj(byte[] data){
        BitSet bitset = fromByteArray(data);

        ArrayList<double[]> koncni = new ArrayList<>();
        double[] sqare = new double[8*8];
        int sqarecount = 0;

        for(int i=0; i<bitset.size(); i++){
            if(koncni.size() == 4){
                System.out.println();
            }
            if(sqarecount == 0){
                BitSet tmpbit = bitset.get(i, i+11);
                String tmp = "";
                for(int j=0; j<11; j++){
                    if(tmpbit.get(j)){
                        tmp += "1";
                    }
                    else{
                        tmp += "0";
                    }
                }
                sqare[sqarecount] = Integer.parseInt(tmp, 2);
                sqarecount++;
                i+=10;
                continue;
            }
            else {
                if(bitset.get(i)){
                    i++;
                    BitSet tmpbit = bitset.get(i, i+4);
                    String tmp = "";
                    for(int j=0; j<4; j++){
                        if(tmpbit.get(j)){
                            tmp += "1";
                        }
                        else{
                            tmp += "0";
                        }
                    }
                    int nofbits = Integer.parseInt(tmp, 2);
                    i+=4;
                    tmp = "";
                    tmpbit = bitset.get(i, i+nofbits);
                    for(int j=0; j<nofbits; j++){
                        if(tmpbit.get(j)){
                            tmp += "1";
                        }
                        else{
                            tmp += "0";
                        }
                    }
                    int mnoz = 1;
                    if(tmp.charAt(0) == '0'){
                        mnoz = -1;
                    }
                    tmp = tmp.substring(1);
                    if(tmp == ""){

                    }
                    int num = Integer.parseInt(tmp,2) * mnoz;
                    if(sqarecount == 63){
                        System.out.println();
                    }
                    sqare[sqarecount] = num;
                    sqarecount++;
                    i+=nofbits-1;
                    if(sqarecount == 64){
                        sqarecount = 0;
                        koncni.add(sqare);
                        sqare = new double[8*8];
                    }
                    continue;
                }
                else{
                    if(sqarecount == 63){
                        System.out.println();
                    }
                    i++;
                    BitSet tmpbit = bitset.get(i, i+6);
                    i+=6;
                    String tmp = "";
                    for(int j=0; j<6; j++){
                        if(tmpbit.get(j)){
                            tmp += "1";
                        }
                        else{
                            tmp += "0";
                        }
                    }
                    int nofzero = Integer.parseInt(tmp, 2);
                    for(int j=0; j<nofzero; j++){
                        if(sqarecount == 64){
                            System.out.println();
                        }
                        sqare[sqarecount] = 0;
                        sqarecount++;
                    }
                    if(sqarecount == 8*8){
                        sqarecount = 0;
                        koncni.add(sqare);
                        sqare = new double[8*8];
                        i--;
                        continue;
                    }
                    else{
                        tmpbit = bitset.get(i, i+4);
                        tmp = "";
                        for(int j=0; j<4; j++){
                            if(tmpbit.get(j)){
                                tmp += "1";
                            }
                            else{
                                tmp += "0";
                            }
                        }
                        if(tmp == "0001"){
                            tmpbit = tmpbit;
                        }
                        int nofbits = Integer.parseInt(tmp, 2);
                        i+=4;
                        tmp = "";
                        tmpbit = bitset.get(i, i+nofbits);
                        for(int j=0; j<nofbits; j++){
                            if(tmpbit.get(j)){
                                tmp += "1";
                            }
                            else{
                                tmp += "0";
                            }
                        }
                        if(tmp == ""){
                            break;
                        }
                        int mnoz = 1;
                        if(tmp.charAt(0) == '0'){
                            mnoz = -1;
                        }
                        tmp = tmp.substring(1);
                        if(tmp.equals("")){
                            System.out.println();
                        }
                        int num = Integer.parseInt(tmp,2) * mnoz;
                        sqare[sqarecount] = num;
                        sqarecount++;
                        i+=nofbits-1;
                        if(sqarecount == 64){
                            sqarecount = 0;
                            koncni.add(sqare);
                            sqare = new double[8*8];
                            continue;
                        }
                        continue;
                    }
                }
            }
        }
        ArrayList<ArrayList<double[]>> izhod = new ArrayList<>();
        sqarecount = koncni.size();
        int tretina1 = sqarecount/3;
        int tretina2 = tretina1*2;
        int tretina3 = sqarecount;
        izhod.add(new ArrayList<>(koncni.subList(0,tretina1)));
        izhod.add(new ArrayList<>(koncni.subList(tretina1,tretina2)));
        izhod.add(new ArrayList<>(koncni.subList(tretina2,tretina3)));
        return izhod;
    }


    public static void zakodiraj(ArrayList<double[]>koncni, String name){
        BitSet biti = new BitSet(1000000);
        int counter = 0;
        for(int i=0; i<koncni.size(); i++){
            for(int j=0; j<koncni.get(i).length; j++){
                double trenutni = koncni.get(i)[j];
                if((j)%64 == 0 || j==0){
                    String binary = Integer.toString((int)trenutni, 2);
                    if(binary.length() < 11){
                        binary = addZero(binary, 11);
                    }
                    for(int l=0; l<binary.length(); l++){
                        if(binary.charAt(l) == '1'){
                            biti.set(counter, true);
                            counter++;
                        }
                        else{
                            biti.set(counter, false);
                            counter++;
                        }
                    }
                }
                else if(trenutni == 0){
                    boolean allZero = true;
                    int zeroCount = 0;
                    for(int k = j;k<j+64; k++){
                        if(koncni.get(i)[k] != 0 ){
                            allZero = false;
                            break;
                        }
                        zeroCount ++;
                        if((k+1)%64 == 0){
                            break;
                        }
                    }
                    biti.set(counter, false);
                    counter++;
                    String binary = Integer.toString(zeroCount, 2);
                    if(binary.length()<6){
                        binary = addZero(binary, 6);
                    }
                    for(int l=0; l<binary.length(); l++){
                        if(binary.charAt(l) == '1'){
                            biti.set(counter, true);
                            counter++;
                        }
                        else{
                            biti.set(counter, false);
                            counter++;
                        }
                    }
                    j+=zeroCount-1;
                    if(allZero){
                        continue;
                    }
                    else{
                        j++;
                        binary = Integer.toString((int)koncni.get(i)[j], 2);
                        if(binary.charAt(0) == '-'){
                            binary = "0" + binary.substring(1);
                        }
                        else{
                            binary = "1" + binary;
                        }
                        String bitCount = Integer.toString(binary.length(), 2);
                        if(bitCount.length()<4){
                            bitCount = addZero(bitCount, 4);
                        }
                        for(int l=0; l<bitCount.length(); l++){
                            if(bitCount.charAt(l) == '1'){
                                biti.set(counter, true);
                                counter++;
                            }
                            else{
                                biti.set(counter, false);
                                counter++;
                            }
                        }
                        for(int l=0; l<binary.length(); l++){
                            if(binary.charAt(l) == '1'){
                                biti.set(counter, true);
                                counter++;
                            }
                            else{
                                biti.set(counter, false);
                                counter++;
                            }
                        }
                        continue;
                    }
                }
                else{
                    if(j == 63){
                        System.out.println();
                    }
                    biti.set(counter, true);
                    counter++;
                    String binary = Integer.toString((int)trenutni, 2);
                    if(binary.charAt(0) == '-'){
                        binary = "0" + binary.substring(1);
                    }
                    else{
                        binary = "1" + binary;
                    }
                    if(j==9){

                    }
                    String bitCount = Integer.toString(binary.length(), 2);
                    if(bitCount.length()<4){
                        bitCount = addZero(bitCount, 4);
                    }
                    for(int l=0; l<bitCount.length(); l++){
                        if(bitCount.charAt(l) == '1'){
                            biti.set(counter, true);
                            counter++;
                        }
                        else{
                            biti.set(counter, false);
                            counter++;
                        }
                    }
                    for(int l=0; l<binary.length(); l++){
                        if(binary.charAt(l) == '1'){
                            biti.set(counter, true);
                            counter++;
                        }
                        else{
                            biti.set(counter, false);
                            counter++;
                        }
                    }
                    continue;
                }
            }
        }
        BitSet output;
        output = biti.get(0,counter);
        byte[] byteOutput =  toByteArray(output);//output.toByteArray();
        File myFile = new File(Environment.getExternalStorageDirectory().toString()+ "/" + name + ".bin");
        try{
            myFile.createNewFile();
            FileOutputStream fOut = new FileOutputStream(myFile);
            fOut.write(byteOutput);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static byte[] toByteArray(BitSet bits) {
        byte[] bytes = new byte[bits.length()/8+1];
        for (int i=0; i<bits.length(); i++) {
            if (bits.get(i)) {
                bytes[bytes.length-i/8-1] |= 1<<(i%8);
            }
        }
        return bytes;
    }

    public static String addZero(String str, int length){
        for(int l = str.length(); l<length; l++){
            str = "0" + str;
        }
        return str;
    }

    public static void izpisi(double[][]kos){
        for(int i=0; i<kos.length; i++){
            for(int j=0; j<kos[i].length; j++){
                System.out.print(kos[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println();
    }

    public static ArrayList<double[][]> razdeli(Bitmap img, int color){
        ArrayList<double[][]> ret =  new ArrayList<>();
        int w = img.getWidth();
        int h = img.getHeight();
        int slika[][] = new int[h][w];
        for(int i=0; i<h; i++){
            for(int j=0; j<w; j++){
                if(color == 0){//red
                    slika[j][i] = (img.getPixel(j,i) >> 16) & 0xff;
                }
                else if(color == 1){//green
                    slika[j][i] = (img.getPixel(j,i) >> 8) & 0xff;
                }
                else{//blue
                    slika[j][i] = (img.getPixel(j,i)) & 0xff;
                }
            }
        }
        for(int i=0; i<h; i+=8) {
            for (int j = 0; j < w; j+=8) {
                ret.add(razrez(slika, i, j));
            }
        }
        return ret;
    }

    public static double[][] razrez(int[][] img, int x, int y){
        double[][] ret = new double[8][8];
        for(int i= x; i<x+8; i++){
            for(int j=y; j<y+8; j++){
                ret[j-y][i-x] = img[j][i];
            }
        }
        return ret;
    }

    public void test(){
        double[][] vhod = new double[8][8];
        for(int i=0; i<8; i++){
            for(int j=0; j<8 ; j++){
                if(j>3){
                    vhod[i][j] = 45;
                }
                else{
                    vhod[i][j] = 5;
                }
            }
        }
        System.out.println("VHOD: ");
        for(int i=0; i<8; i++){
            for(int j=0; j<8 ; j++){
                System.out.print(vhod[i][j] + ", ");
            }
            System.out.println();
        }
        double izhod[][] = new double[8][8];
        izhod = applyDCT(vhod);
        System.out.println("IZHOD: ");
        for(int i=0; i<8; i++){
            for(int j=0; j<8 ; j++){
                System.out.print(izhod[i][j] + ", ");
            }
            System.out.println();
        }
        //double odrezano[][] = new double[8][8];
        //odrezano = cikcak(izhod);
        double restore[][] = new double[8][8];
        restore = applyIDCT(izhod);
        System.out.println("ODSTISNJENO: ");
        for(int i=0; i<8; i++){
            for(int j=0; j<8 ; j++){
                System.out.print(restore[i][j] + ", ");
            }
            System.out.println();
        }
    }

    public static double[] cikcak(double[][]f){
        boolean konec = false;
        double[][] a = f;
        int x=0;
        int y=0;
        int index = 0;
        int N = 7;
        double[] F = new double[(N+1)*(N+1)];
        int OVERFLOW = 2033;
        do{
            F[index] = f[y][x];
            a[y][x] = OVERFLOW;
            if ((x>0) && (y<N) && (a[y+1][x-1]<OVERFLOW)) // lahko gre levo dol
            { x--; y++;}
            else
            if ((x<N) && (y>0) && (a[y-1][x+1]<OVERFLOW)) // lahko gre desno gor
            { x++; y--;}
            else if ((x>0) && (x<N)) // lahko gre desno in ni v 1. stolpcu
                x++;
            else if ((y>0) && (y<N)) // lahko gre dol in ni v 1. vrstici
                y++;
            else if (x<N) // lahko gre desno (in je v 1. stolpcu)
                x++;
            else konec=true;
            index++;
        }
        while(konec == false);
        return F;
    }

    public static double[][] Recikcak(double[] F){
        boolean konec = false;
        int x=0;
        int y=0;
        int index = 0;
        int N = 7;
        //double[] F = new double[(N+1)*(N+1)];
        double[][]f = new double[N+1][N+1];
        double[][] a = new double[N+1][N+1];
        int OVERFLOW = 2033;
        do{
            //F[index] = f[y][x];
            f[y][x] = F[index];
            a[y][x] = OVERFLOW;
            if ((x>0) && (y<N) && (a[y+1][x-1]<OVERFLOW)) // lahko gre levo dol
            { x--; y++;}
            else
            if ((x<N) && (y>0) && (a[y-1][x+1]<OVERFLOW)) // lahko gre desno gor
            { x++; y--;}
            else if ((x>0) && (x<N)) // lahko gre desno in ni v 1. stolpcu
                x++;
            else if ((y>0) && (y<N)) // lahko gre dol in ni v 1. vrstici
                y++;
            else if (x<N) // lahko gre desno (in je v 1. stolpcu)
                x++;
            else konec=true;
            index++;
        }
        while(konec == false);
        return f;
    }

    public static double[][] applyDCT(double[][]f){
        double[][] F = new double[8][8];
        for(int u=0; u<8; u++){
            for(int v=0; v<8; v++){
                double sum = 0.0;
                for(int i=0; i<8; i++) {
                    for (int j = 0; j < 8; j++) {
                        sum += Math.cos(((2 * i + 1) / (2.0 * 8)) * u * Math.PI) * Math.cos(((2 * j + 1) / (2.0 * 8)) * v * Math.PI) * f[i][j];
                    }
                }
                double c1 = 1;
                double c2 = 1;
                if(u==0){
                    c1 = 1/Math.sqrt(2);
                }
                if(v == 0){
                    c2 = 1/Math.sqrt(2);
                }
                F[u][v] = Math.round(sum*((c1*c2)/4));
            }
        }
        return F;
    }
    public static double[][] applyIDCT(double[][]f){
        double[][] F = new double[8][8];
        for(int i=0; i<8; i++){
            for(int j=0; j<8; j++){
                double sum = 0.0;
                for(int u=0; u<8; u++) {
                    for (int v = 0; v < 8; v++) {
                        double c1 = 1;
                        double c2 = 1;
                        if(u==0){
                            c1 = 1/Math.sqrt(2);
                        }
                        if(v == 0){
                            c2 = 1/Math.sqrt(2);
                        }
                        sum += ((c1*c2)/4)*Math.cos(((2 * i + 1) / (2.0 * 8)) * u * Math.PI) * Math.cos(((2 * j + 1) / (2.0 * 8)) * v * Math.PI) * f[u][v];
                    }
                }
                F[i][j] = Math.round(sum);
            }
        }
        return F;
    }
}
