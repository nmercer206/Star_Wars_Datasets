import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import java.io.File;
import java.io.PrintWriter;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

public class HttpRequests {

    // This method returns how many total planets there are within the data set. It is later used by the filterAllPlanets method.
    public static int returnHowManyPlanets(){
        int numOfPlanets = 0;
        try{
            HttpRequest idRequest = HttpRequest.newBuilder()
                    .uri(new URI("https://swapi.dev/api/planets/"))
                    .build();

            HttpClient httpClientID = HttpClient.newHttpClient();
            HttpResponse<String> idResponse = httpClientID.send(idRequest, HttpResponse.BodyHandlers.ofString());

            Gson gson = new Gson();
            RequiredFields getRequestForNumberOfPlanets = gson.fromJson(idResponse.body(), RequiredFields.class);

            // pulls total number of planets from the API
            numOfPlanets = getRequestForNumberOfPlanets.getCount();
        } catch (JsonSyntaxException jse){
            System.out.println("Client-side error thrown. Can not read declared URI path. Request for number of planets unsuccessful");
        } catch (NullPointerException npe){
                System.out.println("Can not find declared endpoint. Request for number of planets unsuccessful");
        } catch (Exception e){
            System.out.println("Something went wrong. Confirm that the URI and endpoints of API are valid.\nRequest for number of planets unsuccessful");
        }

        return numOfPlanets;
    }

    // This method filters through every planet within the data set, using the returnHowManyPlanets method, and adds only the desired
    // planets to an ArrayList, which it later returns. This method is later used by the dataToCsvFile method.
    public static List<RequiredFields> filterAllPlanets(){

        List<RequiredFields> returnPlanets = new ArrayList<>();

        if (returnHowManyPlanets() != 0){
            try{
                int numberOfIterations = returnHowManyPlanets();

                HttpClient httpClient = HttpClient.newHttpClient();
                for (int i = 1; i < numberOfIterations + 1; i++) {

                    HttpRequest getRequest = HttpRequest.newBuilder()
                            .uri(new URI("https://swapi.dev/api/planets/" + i))
                            .build();

                    // pulls every planet from the API
                    HttpResponse<String> getResponse = httpClient.send(getRequest, HttpResponse.BodyHandlers.ofString());
                    Gson gson = new Gson();
                    RequiredFields required = gson.fromJson(getResponse.body(), RequiredFields.class);

                    // filters through all planets, to see if any fields has a value of "unknown" or "N/A", using the canReturnPlanet method from
                    // the Required class.
                    if (required.canReturnPlanet(required)) {
                        returnPlanets.add(new RequiredFields(required.getName(), required.getDiameter(), required.getGravity(), required.getClimate(), required.getPopulation()));
                    }
                }
            } catch (JsonSyntaxException jse){
                System.out.println("Client-side error thrown. Can not read declared URI path. Request for fields of planets unsuccessful.");
            } catch (NullPointerException npe){
                System.out.println("Can not find declared endpoint. Request for fields of planets unsuccessful.");
            } catch (Exception e){
                System.out.println("Something went wrong. Confirm that the URI and endpoints of API are valid.\nRequest for fields of planets unsuccessful");
            }
        }

        return returnPlanets;
    }

    // This method pulls the ArrayList from the filterAllPlanets method, and then loops and prints each object out onto the CSV file.
    public static void dataToCsvFile(){
        if (filterAllPlanets().size() != 0){
            try {
                List<RequiredFields> returnPlanets = HttpRequests.filterAllPlanets();
                File csvFile = new File("StarWars.csv");
                PrintWriter out = new PrintWriter(csvFile);

                for (RequiredFields planet : returnPlanets){
                    out.printf("%s, %s, %s, %s, %s\n", planet.getName(), planet.getDiameter(), planet.getGravity(), planet.getClimate(), planet.getPopulation());
                }

                out.close();
                System.out.println("Dataset successfully written onto csv file.");
            } catch (Exception e){
                System.out.println("Something went wrong. Data successfully requested from data source,\nbut printing to csv file was unsuccessful");
            }

        }

    }

}
