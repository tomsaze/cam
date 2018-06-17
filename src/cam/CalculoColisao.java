package cam;

public class CalculoColisao {

    public void colisao() {

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

        double distanciadeTravagem1;
        double distanciadeTravagem2;

        int tempoaviso = 2; //com quanto tempo de antecedencia avisamos o condutor
        double distanciaAviso1;
        double distanciaAviso2;
        double auxDistancia1 = 10000000;//inicializar com um numero grande para poder calcular o menor
        double auxDistancia2 = 10000000;

        double distanciaEntreCarros;

        double distancia2;
        double distanciaColisao2;
        double velocidade2;
        double tempoColisao2;
        reta r1 = new reta();
        reta r2 = new reta();
        Coordenadas c1 = null;
        try {
            c1 = new Coordenadas(0);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Coordenadas c2 = null;
        try {
            c2 = new Coordenadas(1);
        } catch (Exception e) {
            e.printStackTrace();
        }

        Thread t1 = new Thread(c1);
        Thread t2 = new Thread(c2);
        t1.start();
        t2.start();
        int aux1 = 0;
        int aux2 = 0;

        while (t1.isAlive() && t2.isAlive()) {

            try {
                t1.sleep(100);
                t2.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            //---------------carro 1 ------------------------------------
            if (aux1 == 0) {
                millis[0][0] = c1.getMillis();
                lat[0][0] = c1.getLat();
                lon[0][0] = c1.getLongi();
                aux1 = 1;
            } else if (aux1 == 1 && millis[0][0] != c1.getMillis()) {
                millis[0][1] = c1.getMillis();
                lat[0][1] = c1.getLat();
                lon[0][1] = c1.getLongi();

                r1 = calculoReta(lat[0][0], lon[0][0], lat[0][1], lon[0][1]);
                segundos1 = (millis[0][1] - millis[0][0]) / 1000;
                aux1 = 2;
            } else if (aux1 == 2 && millis[0][1] != c1.getMillis()) {
                millis[0][0] = millis[0][1];
                lat[0][0] = lat[0][1];
                lon[0][0] = lon[0][1];

                millis[0][1] = c1.getMillis();
                lat[0][1] = c1.getLat();
                lon[0][1] = c1.getLongi();

                r1 = calculoReta(lat[0][0], lon[0][0], lat[0][1], lon[0][1]);
                segundos1 = (millis[0][1] - millis[0][0]) / 1000;
            }
            //---------------carro 2 ------------------------------------
            if (aux2 == 0) {
                millis[1][0] = c2.getMillis();
                lat[1][0] = c2.getLat();
                lon[1][0] = c2.getLongi();
                aux2 = 1;
            } else if (aux2 == 1 && millis[1][0] != c2.getMillis()) {
                millis[1][1] = c2.getMillis();
                lat[1][1] = c2.getLat();
                lon[1][1] = c2.getLongi();

                r2 = calculoReta(lat[1][0], lon[1][0], lat[1][1], lon[1][1]);
                segundos2 = (millis[1][1] - millis[1][0]) / 1000;
                aux2 = 2;
            } else if (aux2 == 2 && millis[1][1] != c2.getMillis()) {
                millis[1][0] = millis[1][1];
                lat[1][0] = lat[1][1];
                lon[1][0] = lon[1][1];

                millis[1][1] = c2.getMillis();
                lat[1][1] = c2.getLat();
                lon[1][1] = c2.getLongi();

                r2 = calculoReta(lat[1][0], lon[1][0], lat[1][1], lon[1][1]);
                segundos2 = (millis[1][1] - millis[1][0]) / 1000;
            }

//            System.out.println("Carro1:");
//            System.out.println("Coordenada 1 "+millis[0][0] +" "+lat[0][0] +" "+ lon[0][0]);
//            System.out.println("Coordenada 2 "+millis[0][1] +" "+lat[0][1] +" "+ lon[0][1] + "\n" );
//            System.out.println("Carro2:");
//            System.out.println("Coordenada 1 "+millis[1][0] +" "+lat[1][0] +" "+ lon[1][0]);
//            System.out.println("Coordenada 2 "+millis[1][1] +" "+lat[1][1] +" "+ lon[1][1] + "\n" );

//-----------------------------------------------------------
            if (aux1 == 2 && aux2 == 2
                    && lat[0][0] != lat[0][1]
                    && lat[1][0] != lat[1][1]
                    && lon[0][0] != lon[0][1]
                    && lon[1][0] != lon[1][1]) { //se existirem 2 coordenadas para os dois carros

//                System.out.println("Tempo carro 1: "+ millis[0][1] + "\nTempo Carro 2: " + millis[1][1]);
                x = (r2.b - r1.b) / (r1.m - r2.m);
                y = (r1.m * x) + r1.b;

                distanciaEntreCarros = Haversine.distance(lat[0][1], lon[0][1], lat[1][1], lon[1][1]) * 1000;
//carro 1------------
                distancia1 = Haversine.distance(lat[0][0], lon[0][0], lat[0][1], lon[0][1]) * 1000; //distancia em metros
                distanciaColisao1 = Haversine.distance(lat[0][1], lon[0][1], y, x) * 1000;
                velocidade1 = distancia1 / segundos1;
                tempoColisao1 = distanciaColisao1 / velocidade1;
                distanciadeTravagem1 = (velocidade1 * velocidade1) / 15.696;
                distanciaAviso1 = tempoaviso * velocidade1 + distanciadeTravagem1; //distancia à qual vai ser gerado o aviso
//carro 2------------
                distancia2 = Haversine.distance(lat[1][0], lon[1][0], lat[1][1], lon[1][1]) * 1000;
                distanciaColisao2 = Haversine.distance(lat[1][1], lon[1][1], y, x) * 1000;
                velocidade2 = distancia2 / segundos2;
                tempoColisao2 = distanciaColisao2 / velocidade2;
                distanciadeTravagem2 = (velocidade2 * velocidade2) / 15.696;
                distanciaAviso2 = tempoaviso * velocidade2 + distanciadeTravagem2;

                if (distanciaAviso1 >= distanciaColisao1 || distanciaAviso2 >= distanciaColisao2 && Math.abs(tempoColisao2 - tempoColisao1) < 10) {
                    System.out.println("!!!!!TRAVAR Colisão Eminente!!!!!");
                }
                
                    System.out.println("----------------------------------------");
                    System.out.println("\n         "+millis[0][1]);
                    System.out.println("Ponto de colisão: ( " + y + ", " + x + ")");
                    System.out.println("reta 1: y = " + r1.m + "x" + "+(" + r1.b + ")");
                    System.out.println("Carro1 posição: "+lat[0][1] +" "+ lon[0][1]);
                    System.out.println("reta 2: y = " + r2.m + "x" + "+(" + r2.b + ")");
                    System.out.println("Carro2 posição: "+lat[1][1] +" "+ lon[1][1]);
//                    System.out.println("------------------Carro 1----------------------");
//                    System.out.println("1:Distancia ao ponto de colisão: " + distanciaColisao1 + "m");
//                    System.out.println("1:Tempo que demora ao ponto de colisão: " + tempoColisao1 + "s");
//                    System.out.println("1:Velocidade do Veiculo: " + velocidade1 + "m/s");
//                    System.out.println("1:Distancia de travagem: " + distanciadeTravagem1 + "m");
//                    System.out.println("1:Distancia do aviso: " + distanciaAviso1 + "m");
//                    System.out.println("------------------Carro 2----------------------");
                    System.out.println("2:Distancia ao ponto de colisão: " + distanciaColisao2 + "m");
//                    System.out.println("2:Tempo que demora ao ponto de colisão: " + tempoColisao2 + "s");
//                    System.out.println("2:Velocidade do Veiculo: " + velocidade2 + "m/s");
//                    System.out.println("2:Distancia de travagem: " + distanciadeTravagem2 + "m");
//                    System.out.println("2:Distancia do aviso: " + distanciaAviso2 + "m");
//                    System.out.println("---------------------------------------------");
//                }
            }
        }
    }
    static reta calculoReta(double y1, double x1, double y2, double x2)
    {
        double b;
        double m;

        m = (y2 - y1) / (x2 - x1);
        b = y1 -(m * x1);

        reta r = new reta(m, b);

        return r;
    }
}
