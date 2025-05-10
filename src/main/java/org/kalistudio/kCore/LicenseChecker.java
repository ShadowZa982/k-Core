package org.havennode.khome;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class LicenseChecker {
    public static boolean checkLicense(String backendUrl, String license) {
        try {
            URL url = new URL(backendUrl);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("POST");
            con.setRequestProperty("Content-Type", "application/json");
            con.setDoOutput(true);

            String jsonInput = "{\"license\": \"" + license + "\"}";

            try (OutputStream os = con.getOutputStream()) {
                os.write(jsonInput.getBytes());
                os.flush();
            }

            int responseCode = con.getResponseCode();
            if (responseCode != 200) {
                return false;
            }

            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            StringBuilder response = new StringBuilder();
            String inputLine;

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            return response.toString().contains("\"valid\":true");
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
