package utils;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;

public class JsonConfigReader {

    private final Double L;
    private final Integer N;
    private final Double particleRadius;
    private final Double interactionRadius;
    private final Integer M;
    private final Double n;
    private final Integer times;
    private final Double speed;
    private final String staticFilePath;
    private final String dynamicFilePath;

    public JsonConfigReader(String jsonConfigFilePath) {
        JSONParser jsonParser = new JSONParser();

        try (FileReader reader = new FileReader(jsonConfigFilePath)) {
            // Read JSON file
            Object obj = jsonParser.parse(reader);
            JSONObject jsonObject = (JSONObject) obj;

            L = Double.parseDouble(jsonObject.get("L").toString());
            N = Integer.parseInt(jsonObject.get("N").toString());
            times = Integer.parseInt(jsonObject.get("times").toString());
            n = Double.parseDouble(jsonObject.get("n").toString());
            speed = Double.parseDouble(jsonObject.get("speed").toString());
            particleRadius = Double.parseDouble(jsonObject.get("particleRadius").toString());
            interactionRadius = Double.parseDouble(jsonObject.get("interactionRadius").toString());
            staticFilePath = jsonObject.get("staticFile").toString();
            dynamicFilePath = jsonObject.get("dynamicFile").toString();
            M = (int) (L / (interactionRadius + particleRadius * 2));
        } catch (IOException | ParseException e) {
            throw new RuntimeException("Error reading parameters from config.json");
        }
    }

    public Double getL() {
        return L;
    }

    public Integer getN() {
        return N;
    }

    public Double getParticleRadius() {
        return particleRadius;
    }

    public Double getInteractionRadius() {
        return interactionRadius;
    }

    public Integer getM() {
        return M;
    }

    public Double getNoise() {
        return n;
    }

    public Integer getTimes() {
        return times;
    }

    public Double getSpeed() {
        return speed;
    }

    public String getStaticFilePath() {
        return staticFilePath;
    }

    public String getDynamicFilePath() {
        return dynamicFilePath;
    }
}
