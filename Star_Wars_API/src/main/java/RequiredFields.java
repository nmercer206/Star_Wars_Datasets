
public class RequiredFields {

    private String name;
    private String diameter;
    private String gravity;
    private String climate;
    private String population;
    private Integer count;

    public RequiredFields(String name, String diameter, String gravity, String climate, String population){
        this.name = name;
        this.diameter = diameter;
        this.gravity = gravity;
        this.climate = climate;
        this.population = population;

    }

    public String getName() {
        return name;
    }

    public String getDiameter() {
        return diameter;
    }

    public String getGravity() {
        return gravity;
    }

    public String getClimate() {
        return climate;
    }

    public String getPopulation() {
        return population;
    }

    public int getCount() {
        return count;
    }

    public int isThereName(RequiredFields required){
            if (required.getName().equals("unknown") || required.getName().equals("N/A")){
                return 1;
            } else {
                return 0;
            }
    }


    public int isThereDiameter(RequiredFields required){
        if (required.getDiameter().equals("unknown") || required.getDiameter().equals("N/A")){
            return 1;
        } else {
            return 0;
        }
    }

    public int isThereGravity(RequiredFields required){
        if (required.getGravity().equals("unknown") || required.getGravity().equals("N/A")){
            return 1;
        } else {
            return 0;
        }
    }

    public int isThereClimate(RequiredFields required){
        if (required.getClimate().equals("unknown") || required.getClimate().equals("N/A")){
            return 1;
        } else {
            return 0;
        }
    }

    public int isTherePopulation(RequiredFields required){
        if (required.getPopulation().equals("unknown") || required.getPopulation().equals("N/A")){
            return 1;
        } else {
            return 0;
        }
    }

    // this method pulls the output from every isThere<field> method. If the sum of the total output is greater than zero, then the
    // observed planet is ignored.
    public boolean canReturnPlanet(RequiredFields singlePlanet){
        boolean returnVariable = true;

        if (isThereName(singlePlanet) + isThereDiameter(singlePlanet) + isThereGravity(singlePlanet) + isThereClimate(singlePlanet) + isTherePopulation(singlePlanet) > 0){
            returnVariable = false;
        }
        return returnVariable;
    }



}
