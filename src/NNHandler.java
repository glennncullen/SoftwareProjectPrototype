public class NNHandler {

    public static int inputNum = 5;
    public static int hiddenNum = 7;
    public static int outputNum = 3;

    public static double[] inputLayer;
    public static double[] hiddenLayer;
    public static double[] outputLayer;

    public static double[] errors;

    public static double[][] inToHidWeights;
    public static double[][] hidToOutWeights;

    public static double learningRate;

    public static boolean training = true;

    public static void init(){
        inToHidWeights = new double[inputNum][hiddenNum];
        hidToOutWeights = new double[hiddenNum][outputNum];
        setWeights();
        learningRate = 0.15;
    }

    public static void setWeights(){
        for(int i = 0; i < inToHidWeights.length; i++){
            for(int x = 0; x < inToHidWeights[i].length; x++){
                inToHidWeights[i][x] = (Math.random() * 2 -1);
                System.out.println("Set Weight: " + inToHidWeights[i][x]);
            }
        }
        inToHidWeights[2][2] = -inToHidWeights[2][2];
        for(int i = 0; i < hidToOutWeights.length; i++){
            for(int x = 0; x < hidToOutWeights[i].length; x++){
                hidToOutWeights[i][x] = (Math.random() * 2 -1);
                System.out.println("Set Weight: " + hidToOutWeights[i][x]);
            }
        }
    }

    public static void calculateInput(){
        inputLayer = new double[inputNum];
        inputLayer[0] = 1;
        if(Handler.cars.size() > 0){
            for(Car car : Handler.cars){
                if(car.getCoordinates()[1] < 220 && car.getDirection().equalsIgnoreCase("south")){
                    inputLayer[1] = inputLayer[1] + 0.1;
                    inputLayer[2] = inputLayer[2] + ((220 - car.getCoordinates()[1]) / 100);
                }else if(car.getCoordinates()[1] > 350 && car.getDirection().equalsIgnoreCase("north")){
                    inputLayer[1] = inputLayer[1] + 0.1;
                    inputLayer[2] = inputLayer[2] + ((car.getCoordinates()[1] - 250) / 100);
                }else if(car.getCoordinates()[0] < 320 && car.getDirection().equalsIgnoreCase("east")){
                    inputLayer[3] = inputLayer[3] + 0.1;
                    inputLayer[4] = inputLayer[4] + ((320 - car.getCoordinates()[0]) / 100);
                }else if(car.getCoordinates()[0] > 450 && car.getDirection().equalsIgnoreCase("west")){
                    inputLayer[3] = inputLayer[3] + 0.1;
                    inputLayer[4] = inputLayer[4] + ((car.getCoordinates()[0] - 450) / 100);
                }
            }
            for(int i = 0; i < inputLayer.length; i++){
                System.out.println("Input Layer at: " + i + "\t" + inputLayer[i]);
            }
        }
    }

    public static void calculateHidden(){
        hiddenLayer = new double[hiddenNum];
        hiddenLayer[0] = 1;
        double total = 0;
        for(int i = 1; i < hiddenLayer.length; i++){
            for(int x = 0; x < inputLayer.length; x++){
                total = total + (inputLayer[x] * inToHidWeights[x][i]);
            }
            hiddenLayer[i] = total;
        }
        for(int i = 0; i < hiddenLayer.length; i++){
            System.out.println("Hidden Layer at: " + i + "\t" + hiddenLayer[i]);
        }
    }

    public static void activation(){
        for(int i = 0; i < hiddenLayer.length; i++){
            hiddenLayer[i] = Math.max(0.001, hiddenLayer[i]);
        }
        for(int i = 0; i < hiddenLayer.length; i++){
            System.out.println("Hidden Layer after Activation at: " + i + "\t" + hiddenLayer[i]);
        }
    }

    public static void calculateOutput(){
        outputLayer = new double[outputNum];
        for(int i = 0; i < outputLayer.length; i++){
            double total = 0;
            for(int x = 0; x < hiddenLayer.length; x++){
                if(!Double.isNaN(outputLayer[i] * hidToOutWeights[x][i]) || !Double.isInfinite(outputLayer[i] * hidToOutWeights[x][i])){
                    total = total + outputLayer[i] + (hiddenLayer[i] * hidToOutWeights[x][i]);
                }
            }
            outputLayer[i] = total;
        }
        for(int i = 0; i < outputLayer.length; i++){
            System.out.println("Output Layer at: " + i + "\t" + outputLayer[i]);
        }
        softMax();
        for(int i = 0; i < outputLayer.length; i++){
            System.out.println("Output Layer after SoftMax at: " + i + "\t" + outputLayer[i]);
        }
        double choice = Math.max(outputLayer[0], Math.max(outputLayer[1], outputLayer[2]));
        if(!training){
            for(int i = 0; i < outputLayer.length; i++){
                if(choice == outputLayer[i]){
                    controlLights(i);
                    break;
                }
            }
        }else{
            backPropagation();
        }
    }

    public static void softMax(){
        double sum = 0;
        for(int i = 0; i < outputLayer.length; i++){
            if(!Double.isInfinite(Math.exp(outputLayer[i]))){
                sum = sum + Math.exp(outputLayer[i]);
            }
        }
        for(int i = 0; i < outputLayer.length; i++){
            if(!Double.isInfinite(Math.exp(outputLayer[i]) / sum)){
                outputLayer[i] = Math.exp(outputLayer[i]) / sum;
            }
        }
    }

    public static void controlLights(int choice){
        switch (choice){
            case 0:
                Handler.changeLightsBothRed();
                break;
            case 1:
                Handler.changeLightsEastToWest();
                break;
            case 2:
                Handler.changeLightsNorthToSouth();
                break;
            default:
                break;
        }
    }

    public static void backPropagation(){
        errors = new double[outputNum];
        for(int i = 0; i < errors.length; i++){
            if(i == Handler.userChoice){
                errors[i] = 1;
            }else{
                errors[i] = 0;
            }
        }
        for(int i = 0; i < hidToOutWeights.length; i++){
            for(int x = 0; x < hidToOutWeights[i].length; x++){
                errors[x] = errors[x] - outputLayer[x];
                if(!Double.isNaN(learningRate * (errors[x] * hiddenLayer[i]))) {
                    hidToOutWeights[i][x] = learningRate * (errors[x] * hiddenLayer[i]);
                }
            }
        }
        double averageError = 0;
        for(int i = 0; i < errors.length; i++){
            averageError = averageError + errors[i];
        }
        averageError = averageError / errors.length;

        for(int i = 0; i < inToHidWeights.length; i++){
            for(int x = 0; x < inToHidWeights[i].length; x++){
                if(!Double.isNaN(learningRate * (averageError * inputLayer[i]))) {
                    if(hiddenLayer[i] > 0.001){
                        inToHidWeights[i][x] = learningRate * (averageError * inputLayer[i]);
                    }
                }
            }
        }
        System.out.println("UPDATED inToHidWeights: ");
        for(int i = 0; i < inputLayer.length; i++){
            for(int x = 0; x < hiddenLayer.length; x++){
                System.out.print(inToHidWeights[i][x] + " ");
            }
            System.out.print("\n");
        }
        System.out.print("\n");

        System.out.println("UPDATED hidToOutWeights: ");
        for(int i = 0; i < hiddenLayer.length; i++){
            for(int x = 0; x < outputLayer.length; x++){
                System.out.print(hidToOutWeights[i][x] + " ");
            }
            System.out.print("\n");
        }
        System.out.print("\n");
    }

}
