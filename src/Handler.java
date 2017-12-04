import java.util.ArrayList;
import javax.swing.Timer;

public class Handler {

    public static ArrayList<Car> cars;
    public static Timer simulationTimer, carTimer;
    public static int SimulationTimerSpeed, carTimerSpeed;
    public static boolean[] trafficLights;
    public static int userChoice;

    public static void init(){
        cars = new ArrayList<>();
        SimulationTimerSpeed = 1000;
        carTimerSpeed = 30;
        trafficLights = new boolean[2];
        changeLightsNorthToSouth();
    }

    public static void addCar(int type){
        int[] startingPoint = new int[2];
        int[] dimensions = new int[2];
        switch (type){
            case 0:
                startingPoint[0] = 368;
                startingPoint[1] = 650;
                dimensions[0] = 20;
                dimensions[1] = 30;
                cars.add(new Car(startingPoint, -5, "north", dimensions, 350));
                break;
            case 1:
                startingPoint[0] = 407;
                startingPoint[1] = -50;
                dimensions[0] = 20;
                dimensions[1] = 30;
                cars.add(new Car(startingPoint, 5, "south", dimensions, 220));
                break;
            case 2:
                startingPoint[0] = -50;
                startingPoint[1] = 268;
                dimensions[0] = 30;
                dimensions[1] = 20;
                cars.add(new Car(startingPoint, 5, "east", dimensions, 320));
                break;
            case 3:
                startingPoint[0] = 850;
                startingPoint[1] = 307;
                dimensions[0] = 30;
                dimensions[1] = 20;
                cars.add(new Car(startingPoint, -5, "west", dimensions, 450));
                break;
            default:
                break;
        }
    }

    public static void updateCars(){
        ArrayList<Car> carsToBeRemoved = new ArrayList<>();
        for(int i = 0; i < cars.size(); i++){
            int queue = 0;
            Car car = cars.get(i);
            int[] coor = car.getCoordinates();
            switch (car.getDirection()){
                case "north":
                    if(car.isMoving()){
                        coor[1] = coor[1] + car.getSpeed();
                        car.setCoordinates(coor);
                    }
                    for(int x = 0; x < cars.size(); x++){
                        if(cars.get(x).getDirection().equalsIgnoreCase(car.getDirection()) && car.isMoving()){
                            if(!cars.get(x).equals(car)) {
                                if(cars.get(x).getCoordinates()[1] >= 350) {
                                    car.setStopLocation(cars.get(x).getStopLocation() + 35);
                                }else{
                                    car.setStopLocation(350);
                                }
                            }
                        }
                    }
//                    car.setStopLocation(350 + (queue * 35));
                    if(car.getStopLocation() == coor[1] && !trafficLights[1]){
                        car.setMoving(false);
                    }else{
                        car.setMoving(true);
                    }
                    if(coor[1] < -30){
                        carsToBeRemoved.add(car);
                    }
                    break;
                case "south":
                    if(car.isMoving()){
                        coor[1] = coor[1] + car.getSpeed();
                        car.setCoordinates(coor);
                    }
                    for(int x = 0; x < cars.size(); x++){
                        if(cars.get(x).getDirection().equalsIgnoreCase(car.getDirection()) && car.isMoving()){
                            if(!cars.get(x).equals(car)){
                                if(cars.get(x).getCoordinates()[1] <= 220){
                                    car.setStopLocation(cars.get(x).getStopLocation()-35);
                                }else{
                                    car.setStopLocation(220);
                                }
                            }
                        }
                    }
//                    car.setStopLocation(220 + (queue * -35));
                    if(car.getStopLocation() == coor[1] && !trafficLights[1]){
                        car.setMoving(false);
                    }else{
                        car.setMoving(true);
                    }
                    if(coor[1] > 630){
                        carsToBeRemoved.add(car);
                    }
                    break;
                case "east":
                    if(car.isMoving()){
                        coor[0] = coor[0] + car.getSpeed();
                        car.setCoordinates(coor);
                    }
                    for(int x = 0; x < cars.size(); x++){
                        if(cars.get(x).getDirection().equalsIgnoreCase(car.getDirection()) && car.isMoving()){
                            if(!cars.get(x).equals(car)) {
                                if(cars.get(x).getCoordinates()[0] <= 320){
                                    car.setStopLocation(cars.get(x).getStopLocation() - 35);
                                }else{
                                    car.setStopLocation(320);
                                }
                            }
                        }
                    }
//                    car.setStopLocation(320 + (queue * -35));
                    if(car.getStopLocation() == coor[0] && !trafficLights[0]){
                        car.setMoving(false);
                    }else{
                        car.setMoving(true);
                    }
                    if(coor[0] > 830){
                        carsToBeRemoved.add(car);
                    }
                    break;
                case "west":
                    if(car.isMoving()){
                        coor[0] = coor[0] + car.getSpeed();
                        car.setCoordinates(coor);
                    }
                    for(int x = 0; x < cars.size(); x++){
                        if(cars.get(x).getDirection().equalsIgnoreCase(car.getDirection()) && car.isMoving()){
                            if(!cars.get(x).equals(car)) {
                                if(cars.get(x).getCoordinates()[0] >= 450){
                                    car.setStopLocation(cars.get(x).getStopLocation() + 35);
                                }else{
                                    car.setStopLocation(450);
                                }
                            }
                        }
                    }
//                    car.setStopLocation(450 + (queue * 35));
                    if(car.getStopLocation() == coor[0] && !trafficLights[0]){
                        car.setMoving(false);
                    }else{
                        car.setMoving(true);
                    }
                    if(coor[0] < -30){
                        carsToBeRemoved.add(car);
                    }
                    break;
                default:
                    break;
            }
            cars.removeAll(carsToBeRemoved);
        }
    }


    public static void changeLightsNorthToSouth(){
        Handler.trafficLights[0] = false;
        Handler.trafficLights[1] = true;
        userChoice = 2;
    }

    public static void changeLightsEastToWest(){
        Handler.trafficLights[0] = true;
        Handler.trafficLights[1] = false;
        userChoice = 1;
    }

    public static void changeLightsBothRed(){
        Handler.trafficLights[0] = false;
        Handler.trafficLights[1] = false;
        userChoice = 0;
    }

}
