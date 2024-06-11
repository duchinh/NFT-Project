package function;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
// chi class trong package moi truy cap dc 

class JsonParser {
    private String filename;
    protected void setFilename(String filename) {
        this.filename = filename;
    }
    
    protected String getJSONFromFile() {
        
        StringBuilder jsonText = new StringBuilder();
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                jsonText.append(line).append("\n");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonText.toString();
    }
     


    protected String getJSONFromURL(String strUrl) {
        StringBuilder jsonText = new StringBuilder();
        try {
            URL url = new URL(strUrl);
            try (InputStream is = url.openStream(); BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(is))) {
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    jsonText.append(line).append("\n");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonText.toString();
    }

}
