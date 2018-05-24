package cam;

import de.tudresden.sumo.cmd.Simulation;
import de.tudresden.sumo.cmd.Vehicle;
import de.tudresden.sumo.util.SumoCommand;
import de.tudresden.ws.container.SumoPosition2D;
import it.polito.appeal.traci.SumoTraciConnection;

import java.util.ArrayList;
import java.util.List;

public class SUMOOutput {

    private String config_file_windows = "C:/Users/Daniel Costa/Documents/Sumo/map.sumo.cfg" ;
    private String config_file_linux = "jsbdjshgdkd" ;
    private String sumo_bin_windows = "C:/Users/Daniel Costa/Documents/Sumo/map.sumo.cfg" ;
    private String sumo_bin_linux = "C:/Program Files (x86)/DLR/Sumo/bin/sumo/" ;

    private double timeStep = 0.1;
    private double lat = 41.555019;
    private int idcar = 1;
    private static int delay = 1000;
    private double varialat = 0.000100;
    private double varialong = 0.000100;

   /* public SUMOOutput(double millis) {
        this.timeStep = millis;
    }
    */


    public void setTimeStep(double timeStep){
        this.timeStep = timeStep;
    }



    public List testeSUMO() throws Exception {

        String sumo_bin = "C:/Program Files (x86)/DLR/Sumo/bin/sumo/";
        List<SUMOCoordenadas> listacoordenadasveiculo0 = new ArrayList<>();

        // String config_file = "C:/Users/Daniel Costa/Desktop/simulation/config.sumo.cfg";
        String config_file = "C:/Users/Daniel Costa/Documents/Sumo/map.sumo.cfg";
        SumoTraciConnection conn = new SumoTraciConnection(sumo_bin, config_file);

        conn.addOption("step-length", "0.1"); //timestep 1 second

        try{

            //start TraCI
            conn.runServer();

            //load routes and initialize the simulation
            conn.do_timestep();

            //Começa aos 10 segundos

            for(int i=0; i<100; i++){ //100 = 10 Segundos de Simulação
                // conn.do_timestep(); // Adicionar isto para retirar os veiculos seguintes
            }

            String vehicle = "0";

            for(int i=0; i<2500; i++){

                //current simulation time

                int simtime = (int) conn.do_job_get(Simulation.getCurrentTime());

                SumoPosition2D positionveh0 = (SumoPosition2D) conn.do_job_get(Vehicle.getPosition("0"));
                SumoPosition2D positionveh1 = (SumoPosition2D) conn.do_job_get(Vehicle.getPosition("1"));

                // SumoPosition3D test2 = (SumoPosition3D) conn.do_job_get(Vehicle.getPosition3D("0"));
                // Double teste2 = (Double) conn.do_job_get(Vehicle.getLanePosition("1"));
                // System.out.println("timestep: " + i + " " + teste.toString());
                // System.out.println("timestep: " + i + " " + test2.x + " X  " +test2.y + " Y "  +test2.z + " Z" );
                // System.out.println("timestep: " + i + " " + test3.x + " X  " +test2.y + " Y "  +test2.z + " Z" );
                SumoPosition2D xyToLatLong0;
                SumoPosition2D xyToLatLong1;
                Boolean b = false;
                SumoCommand sumoCommand0 = Simulation.convertGeo(positionveh0.x, positionveh0.y, b);
                SumoCommand sumoCommand1 = Simulation.convertGeo(positionveh1.x, positionveh1.y, b);
                xyToLatLong0 = (SumoPosition2D) conn.do_job_get(sumoCommand0);
                xyToLatLong1 = (SumoPosition2D) conn.do_job_get(sumoCommand1);

                listacoordenadasveiculo0.add(new SUMOCoordenadas(xyToLatLong0.y, xyToLatLong0.x));


                //System.out.println("simtime: " + simtime + " " + xyToLatLong0.toString() + " Coordinates " + "Vehicle 0");
               // System.out.println("simtime: " + simtime + " " + xyToLatLong1.toString() + " Coordinates " + "Vehicle 1");

                //add new vehicle
                // conn.do_job_set(Vehicle.add("veh"+i, "car", "s1", simtime, 0, 13.8, (byte) 1));

                if (simtime == 65600){
                    break;
                }
                 conn.do_timestep();
            }
            conn.close();
            //stop TraCI

        }catch(Exception ex){ex.printStackTrace();}

        return listacoordenadasveiculo0;
    }
}
