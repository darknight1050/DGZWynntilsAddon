package ch.darknight1050.dgzwynntilsaddon.utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.stream.Collectors;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;

import com.wynntils.modules.map.MapModule;
import com.wynntils.modules.map.configs.MapConfig;
import com.wynntils.modules.map.instances.WaypointProfile;
import com.wynntils.modules.map.instances.WaypointProfile.WaypointType;

import ch.darknight1050.dgzwynntilsaddon.Reference;

public class WebUtils {

    public static void UploadWaypoints() {
        try {
            String base64 = WaypointProfile.encode(
                    MapConfig.Waypoints.INSTANCE.waypoints.stream()
                            .filter(w -> w.getType().equals(WaypointType.LOOTCHEST_T1)
                                    || w.getType().equals(WaypointType.LOOTCHEST_T2)
                                    || w.getType().equals(WaypointType.LOOTCHEST_T3)
                                    || w.getType().equals(WaypointType.LOOTCHEST_T4))
                            .collect(Collectors.toList()),
                    WaypointProfile.currentFormat);
            HttpClient client = HttpClients.createDefault();
            HttpPost post = new HttpPost(Reference.CONFIG.getUploadURL());

            ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("password", Reference.CONFIG.getPassword()));
            params.add(new BasicNameValuePair("base64", base64));
            post.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));

            HttpResponse response = client.execute(post);
            HttpEntity entity = response.getEntity();

            if (entity != null) {
                entity.getContent().close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static int DownloadWaypoints() {
        int count = 0;
        try {
            HttpClient client = HttpClients.createDefault();
            HttpPost post = new HttpPost(Reference.CONFIG.getDownloadURL());

            ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("password", Reference.CONFIG.getPassword()));
            post.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));

            HttpResponse response = client.execute(post);
            HttpEntity entity = response.getEntity();

            if (entity != null) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(entity.getContent()));
                StringBuffer sb = new StringBuffer();
                String line;
                while ((line = reader.readLine()) != null) {
                    sb.append(line);
                }
                reader.close();
                for (WaypointProfile profile : WaypointProfile.decode(sb.toString())) {
                    if (!MapConfig.Waypoints.INSTANCE.waypoints.stream().anyMatch(c -> c.getX() == profile.getX()
                            && c.getY() == profile.getY() && c.getZ() == profile.getZ())) {
                        MapConfig.Waypoints.INSTANCE.waypoints.add(profile);
                        count++;
                    }
                }
                MapConfig.Waypoints.INSTANCE.saveSettings(MapModule.getModule());

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return count;
    }
}
