package services;

/**
 * Created by admin on 2017/7/12.
 */
public class MyServices {
    public String sayHelloWorldFrom(String jsonArg,String generalArgument) {
        String result = "Hello, world, from " + jsonArg;
        System.out.println(result);
        return result;
    }

    public String getLoginResult(String jsonArg,String generalArgument){
        return PracticeServices.getInstance().getLoginResult(jsonArg,generalArgument);
    }

    public String updateUser(String jsonArg,String generalArgument){
        return PracticeServices.getInstance().updateUser(jsonArg,generalArgument);
    }


    public String getAllCities(String jsonArg,String generalArgument){
        return PracticeServices.getInstance().getAllCities(jsonArg,generalArgument);
    }

    public String getRegions(String jsonArg,String generalArgument){
        return PracticeServices.getInstance().getRegions(jsonArg,generalArgument);
    }

    public String getAllUsers(String jsonArg,String generalArgument){
        return PracticeServices.getInstance().getAllUsers(jsonArg,generalArgument);
    }

    public String getAllAreas(String jsonArg,String generalArgument){
        return PracticeServices.getInstance().getAllAreas(jsonArg,generalArgument);
    }
}
