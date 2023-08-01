package DAO;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.Objects;
import javax.servlet.http.HttpServletRequest;

public class readJsonParameter {
    
    public static JsonObject getJsonParameter(HttpServletRequest request) {
        StringBuffer jb = new StringBuffer();
        String line = null;
        Gson para=new Gson();
        try {
            BufferedReader reader = request.getReader();
            while ((line = reader.readLine()) != null){  jb.append(line);  }
        }catch (IOException e){}
            JsonElement element = para.fromJson(jb.toString(), JsonElement.class);
            JsonObject jsonObj = element.getAsJsonObject();
            return jsonObj;
    }
    
    public static String printJson(Object object){
        Objects.requireNonNull(object);
        Gson g = new Gson();
        return g.toJson(object);
    }
}
