/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cam;
import de.tudresden.sumo.cmd.*;
import de.tudresden.sumo.config.Constants;
import de.tudresden.sumo.util.SumoCommand;
import de.tudresden.ws.container.SumoPosition2D;
import de.tudresden.ws.container.SumoPosition3D;
import de.tudresden.ws.container.SumoStringList;
import de.tudresden.ws.container.SumoVehicleData;
import it.polito.appeal.traci.SumoTraciConnection;

import java.awt.*;
import java.awt.geom.Point2D;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collection;

/**
 *
 * @author toms
 */
public class CAM {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception {


        testeSUMO();
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




     public static void testeSUMO() throws Exception {

        String sumo_bin = "C:/Program Files (x86)/DLR/Sumo/bin/sumo/";

        // String config_file = "C:/Users/Daniel Costa/Desktop/simulation/config.sumo.cfg";
           String config_file = "C:/Users/Daniel Costa/Documents/Sumo/map.sumo.cfg";
           SumoTraciConnection conn = new SumoTraciConnection(sumo_bin, config_file);

         conn.addOption("step-length", "0.1"); //timestep 1 second

         try{

             //start TraCI
             conn.runServer();

             //load routes and initialize the simulation
             conn.do_timestep();
             conn.do_timestep();
             conn.do_timestep();
             conn.do_timestep();

             for(int i=0; i<100; i++){
                // conn.do_timestep(); // Adicionar isto para retirar os veiculos seguintes
             }

             for(int i=0; i<1800; i++){

                 //current simulation time

                 int simtime = (int) conn.do_job_get(Simulation.getCurrentTime());

                 SumoPosition2D positionveh = (SumoPosition2D) conn.do_job_get(Vehicle.getPosition("2"));
                 // SumoPosition3D test2 = (SumoPosition3D) conn.do_job_get(Vehicle.getPosition3D("0"));
                 // Double teste2 = (Double) conn.do_job_get(Vehicle.getLanePosition("1"));
                 // System.out.println("timestep: " + i + " " + teste.toString());
                 // System.out.println("timestep: " + i + " " + test2.x + " X  " +test2.y + " Y "  +test2.z + " Z" );
                 // System.out.println("timestep: " + i + " " + test3.x + " X  " +test2.y + " Y "  +test2.z + " Z" );
                 SumoPosition2D xyToLatLong;
                 Boolean b = false;
                 SumoCommand sumoCommand = Simulation.convertGeo(positionveh.x, positionveh.y, b);
                 xyToLatLong = (SumoPosition2D) conn.do_job_get(sumoCommand);

                 System.out.println("simtime: " + simtime + " " + xyToLatLong.toString() + " Coordinates");

                 //add new vehicle
                // conn.do_job_set(Vehicle.add("veh"+i, "car", "s1", simtime, 0, 13.8, (byte) 1));
                 conn.do_timestep();
             }

             //stop TraCI
             conn.close();
         }catch(Exception ex){ex.printStackTrace();}

     }





         /*       //conn.do_timestep();
         for (int i = 0; i < 3600; i++) {
             conn.do_timestep();
             //System.out.println(conn.do_job_get(Vehicle.getIDList()));
              Object o = conn.do_job_get(Vehicle.getLanePosition("veh1"));
            // Collection<Vehicle> vehicles = conn.getVehicleRepository().getAll().values();
         }
/*
            System.out.println(conn.do_job_get(Simulation.getLoadedNumber()));
            System.out.println(conn.do_job_get(Simulation.getDepartedNumber()));
            System.out.println(conn.do_job_get(Vehicle.getDistance("1")));
            System.out.println(conn.do_job_get(Vehicle.getColor("1")));
            System.out.println(conn.do_job_get(Vehicletype.getIDList()));
             System.out.println(conn.do_job_get(Simulation.getArrivedIDList()));
             //double co2 = (double) conn.do_job_get(Vehicle.getCO2Emission("11"));
               // System.out.println("CO2: " + co2 + " g/s");

               */
}


