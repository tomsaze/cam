/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cam;
import cam.reta;
import cam.Haversine;

/**
 *
 * @author toms
 */
public class CAM {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        double[][] millis = new double[2][2];
        double[][] lon = new double[2][2];
        double[][] lat = new double[2][2];
        double segundos1 = 0;
        double segundos2 = 0;
        double x;
        double y;

        double distancia1; //distancia em metros
        double distanciaColisao1;
        double velocidade1;
        double tempoColisao1;

        double distanciadetravagem1;
        double distanciadetravagem2;

        int tempoaviso = 10; //com quanto tempo de antecedencia avisamos o condutor
        double distanciaaviso1;
        double distanciaaviso2;

        double distancia2;
        double distanciaColisao2;
        double velocidade2;
        double tempoColisao2;
        reta r1 = new reta();
        reta r2 = new reta();
        Coordenadas c1 = new Coordenadas(0, -8.401299, 41.555019, 1, 0.000100, 0.000100);
        Coordenadas c2 = new Coordenadas(0,-8.395078 , 41.554015, 2, -0.000100 , 0.000100);
        Thread t1 = new Thread(c1);
        Thread t2 = new Thread(c2);
        t1.start() ;
        t2.start() ;
        int aux1 = 0;
        int aux2 = 0;

        while(t1.isAlive()){

            //System.out.println("TESTE " + c1.toString());

            try {
                t1.sleep(1000);
                t2.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            //---------------carro 1 ------------------------------------
            if (aux1==0){
                millis[0][0] = c1.getMillis();
                lat[0][0] = c1.getLat();
                lon[0][0] = c1.getLongi();
                aux1 = 1;
            }else if(aux1 == 1 && millis[0][0] != c1.getMillis()){
                millis[0][1] = c1.getMillis();
                lat[0][1] = c1.getLat();
                lon[0][1] = c1.getLongi();

                r1 = calculoReta(lat[0][0], lon[0][0], lat[0][1], lon[0][1]);
                segundos1 = (millis[0][1] - millis[0][0])/1000;
                aux1 = 2;
            }else if(aux1 == 2 && millis[0][1] != c1.getMillis() ){
                millis[0][0] = millis[0][1];
                lat[0][0] = lat[0][1];
                lon[0][0]  = lon[0][1];

                millis[0][1] = c1.getMillis();
                lat[0][1] = c1.getLat();
                lon[0][1] = c1.getLongi();

                r1 = calculoReta(lat[0][0], lon[0][0], lat[0][1], lon[0][1]);
                segundos1 = (millis[0][1] - millis[0][0])/1000;
            }
            //---------------carro 2 ------------------------------------
            if (aux2==0){
                millis[1][0] = c2.getMillis();
                lat[1][0] = c2.getLat();
                lon[1][0] = c2.getLongi();
                aux2 = 1;
            }else if(aux2 == 1 && millis[1][0] != c2.getMillis()){
                millis[1][1] = c2.getMillis();
                lat[1][1] = c2.getLat();
                lon[1][1] = c2.getLongi();

                r2 = calculoReta(lon[1][0], lat[1][0], lon[1][1], lat[1][1]);
                segundos2 = (millis[1][1] - millis[1][0])/1000;
                aux2 = 2;
            }else if(aux2 == 2 && millis[1][1] != c2.getMillis() ){
                millis[1][0] = millis[1][1];
                lat[1][0] = lat[1][1];
                lon[1][0]  = lon[1][1];

                millis[1][1] = c2.getMillis();
                lat[1][1] = c2.getLat();
                lon[1][1] = c2.getLongi();

                r2 = calculoReta(lon[1][0], lat[1][0], lon[1][1], lat[1][1]);
                segundos2 = (millis[1][1] - millis[1][0])/1000;
            }
//-----------------------------------------------------------
            if(aux1 == 2 && aux2 ==2){
                x = (r2.b - r1.b) / (r1.m - r2.m);
                y = (r1.m * x) + r1.b;

                distancia1 = Haversine.distance(lat[0][0], lon[0][0], lat[0][1], lon[0][1])*1000; //distancia em metros
                distanciaColisao1 = Haversine.distance(lat[0][1], lon[0][1] , x, y)*1000;
                velocidade1 = distancia1/segundos1 ;
                tempoColisao1 = distanciaColisao1/velocidade1 ;
                distanciadetravagem1 = (velocidade1 * velocidade1) / 15.696;

                distancia2 = Haversine.distance(lat[1][0], lon[1][0], lat[1][1], lon[1][1])*1000;
                distanciaColisao2 = Haversine.distance(lat[1][1], lon[1][1] , x, y)*1000;
                velocidade2 = distancia2/segundos2 ;
                tempoColisao2 = distanciaColisao2/velocidade2 ;
                distanciadetravagem2 = (velocidade2 * velocidade2) / 15.696;

                System.out.println("reta 1: y = " + r1.m + "x" + "+(" + r1.b + ")");
                System.out.println("reta 2: y = " + r2.m + "x" + "+(" + r2.b + ")");
                System.out.println("Ponto de colisão: ( " + x + ", " + y +")");
                System.out.println("---------------------------------------------");
                System.out.println("1:Distancia ao ponto de colisão: " + distanciaColisao1 + "m");
                System.out.println("1:Velocidade do Veiculo: " + velocidade1 + "m/s");
                System.out.println("1:Distancia: " + distancia1 + "m");
                System.out.println("1:Distancia de travagem: " + distanciadetravagem1 + "m");
                System.out.println("1:Tempo que demora ao ponto de colisão: " + tempoColisao1 + "s");
                System.out.println("---------------------------------------------");
                System.out.println("2:Distancia ao ponto de colisão: " + distanciaColisao2 + "m");
                System.out.println("2:Velocidade do Veiculo: " + velocidade2 + "m/s");
                System.out.println("2:Distancia " + distancia2 + "m");
                System.out.println("1:Distancia de travagem: " + distanciadetravagem2 + "m");
                System.out.println("2:Tempo que demora ao ponto de colisão: " + tempoColisao2 + "s");

            }
        }
    }
    static reta calculoReta(double x1, double y1, double x2, double y2)
    {
        double b;
        double m;

        m = (y2 - y1) / (x2 - x1);
        b = y1 -(m * x1);

        reta r = new reta(m, b);

        return r;
    }

}
