package server.recognition;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.FileEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

/**
 * Gets information out of an image, observing if there is a car or not.
 */
public class RecognitionMain implements Callable {
    private static final String subscriptionKey = "KEY";
    private static final String uriBase = "https://westcentralus.api.cognitive.microsoft.com/vision/v1.0/analyze";

    public Boolean call() {
        HttpClient httpclient = new DefaultHttpClient();
        try {
            URIBuilder builder = new URIBuilder(uriBase);

            // Request parameters. All of them are optional.
            builder.setParameter("visualFeatures", "Categories,Description,Color");
            builder.setParameter("language", "en");

            // Prepare the URI for the REST API call.
            URI uri = builder.build();
            HttpPost request = new HttpPost(uri);

            // Request headers.
            request.setHeader("Content-Type", "application/octet-stream");
            request.setHeader("Ocp-Apim-Subscription-Key", subscriptionKey);

            // Request body.
            FileEntity content = new FileEntity(new GetRandomImage().getRandomImage());
            request.setEntity(content);

            // Execute the REST API call and get the response entity.
            HttpResponse response = httpclient.execute(request);
            HttpEntity entity = response.getEntity();

            if (entity != null) {
                String jsonString = EntityUtils.toString(entity);
                JSONObject json = new JSONObject(jsonString);
                List<String> tags = new ArrayList(json.getJSONObject("description").getJSONArray("tags").toList());
                return tags.contains("car");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }
}
