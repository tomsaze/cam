/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cam;

/**
 *
 * @author toms
 */
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Coordenadas extends Thread {
    private long millis;
    private double longi;
    private double lat;
    private int idcar;
    private static int delay = 100;
    List<SUMOCoordenadas> getListaVehiclo = new ArrayList<>();




        public Coordenadas(int idcar) throws Exception {
            this.idcar = idcar;
            SUMOOutput s = new SUMOOutput();
            getListaVehiclo = s.testeSUMO(idcar);
            //getListaVehiclo = s.testeSUMO("0");
        }
        public Coordenadas(Coordenadas c){
         this.millis = c.getMillis();
         this.lat = c.getLat();
         this.longi = c.getLongi();
         this.idcar = c.getIdCar();
        }
        public long getMillis(){
            return this.millis;
        }
        public double getLat(){
            return this.lat;
        }
        public double getLongi(){
            return this.longi;
        }
        public int getIdCar(){
            return this.idcar;
        }
        public void setMillis(long mill){
            this.millis = mill;
        }
        public void setLat(double lat){
            this.lat = lat;
        }
        public void setLongi(double longi){
            this.longi = longi;
        }
        public void setIdCar(int idcar){
            this.idcar = idcar;
        }
        public String toString(){
        StringBuilder sb= new StringBuilder();
        sb.append("Milis: " + this.millis + "\n");
        sb.append("Lat: " + this.lat + "\n");
        sb.append("Longitude: " + this.longi + "\n");
        sb.append("id: " + this.idcar +"\n");
        
        return sb.toString();
                
        }
        public void run() {

            while (true) {
                for(SUMOCoordenadas veiculo : getListaVehiclo ) {
                    this.millis = veiculo.time;
                    this.lat = veiculo.latitude;
                    this.longi = veiculo.longitude;

                    try {
                        Thread.sleep(delay);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
