package com.example.board.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.File;
import java.io.IOException;
import java.security.cert.X509Certificate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
public class TerrorZoneService {

    private final String API_URL = "https://d2runewizard.com/api/terror-zone?token=ZmWTyqTE4nrRUlUoumeIX100HHHhmug6";
    private final String HISTORY_FILE = "terror_zone_history.json";
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final RestTemplate restTemplate;

    public TerrorZoneService() throws Exception {
        this.restTemplate = createSslIgnoringRestTemplate();
    }

    private RestTemplate createSslIgnoringRestTemplate() throws Exception {
        TrustManager[] trustAllCerts = new TrustManager[]{
                new X509TrustManager() {
                    public X509Certificate[] getAcceptedIssuers() { return new X509Certificate[0]; }
                    public void checkClientTrusted(X509Certificate[] certs, String authType) {}
                    public void checkServerTrusted(X509Certificate[] certs, String authType) {}
                }
        };
        SSLContext sslContext = SSLContext.getInstance("TLS");
        sslContext.init(null, trustAllCerts, new java.security.SecureRandom());

        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory() {
            @Override
            protected void prepareConnection(java.net.HttpURLConnection connection, String httpMethod) throws java.io.IOException {
                if (connection instanceof HttpsURLConnection) {
                    ((HttpsURLConnection) connection).setSSLSocketFactory(sslContext.getSocketFactory());
                    ((HttpsURLConnection) connection).setHostnameVerifier((hostname, session) -> true);
                }
                super.prepareConnection(connection, httpMethod);
            }
        };
        return new RestTemplate(factory);
    }

    private static final Map<String, String> TZ_TIERS = new HashMap<String, String>() {{
        put("Blood Moor and Den of Evil", "FF");
        put("Cold Plains and The Cave", "CD");
        put("Burial Grounds, The Crypt, and The Mausoleum", "FF");
        put("Stony Field, Tristram", "FF");
        put("Stony Field", "FF");
        put("Dark Wood and Underground Passage", "CD");
        put("Dark Wood", "CD");
        put("Black Marsh, The Hole, and The Forgotten Tower", "CA");
        put("Black Marsh", "CA");
        put("Jail and Barracks", "BC");
        put("Cathedral, Inner Cloister, and Catacombs", "AS");
        put("Cathedral and Catacombs", "AS");
        put("Tamoe Highland, Pit, Monastery Gate, and Outer Cloister", "AA");
        put("The Pit", "AA");
        put("Tristram", "FF");
        put("Moo Moo Farm", "BS");
        put("Secret Cow Level", "BS");
        put("Lut Gholein Sewers", "BC");
        put("Sewers", "BC");
        put("Rocky Waste and Stony Tomb", "BC");
        put("Dry Hills and Halls of the Dead", "AC");
        put("Far Oasis and Maggot Lair", "FF");
        put("Far Oasis", "FF");
        put("Lost City, Valley of Snakes, Claw Viper Temple, and Ancient Tunnels", "CC");
        put("Black Marsh, The Hole, and The Forgotten Tower", "CA");
        put("Black Marsh and The Hole", "CA");
        put("Black Marsh", "CA");
        put("Tal Rasha's Tomb, Tal Rasha's Chamber, and Canyon of the Magi", "SA");
        put("Tal Rasha's Tombs and Tal Rasha's Chamber", "SA");
        put("Tal Rasha's Tombs", "SA");
        put("Lost City, Valley of Snakes, Claw Viper Temple, and Ancient Tunnels", "CC");
        put("Lost City, Valley of Snakes, and Claw Viper Temple", "CC");
        put("Spider Forest, Arachnid Lair, and Spider Cavern", "CB");
        put("Spider Forest and Spider Cavern", "CB");
        put("Burial Grounds, The Crypt, and The Mausoleum", "FF");
        put("Burial Grounds, Crypt, and Mausoleum", "FF");
        put("Flayer Jungle, Flayer Dungeon, and Swampy Pit", "AB");
        put("Flayer Jungle and Flayer Dungeon", "AB");
        put("Kurast Bazaar, Kurast Causeway, Kurast Sewers, Ruined Temple, Disused Fane, Forgotten Reliquary, Forgotten Temple, Ruined Fane, and Disused Reliquary", "BB");
        put("Kurast Bazaar, Ruined Temple, and Disused Fane", "BB");
        put("Travincal", "BA");
        put("Durance of Hate", "CA");
        put("Outer Steppes and Plains of Despair", "CC");
        put("River of Flame and City of the Damned", "BB");
        put("City of the Damned and River of Flame", "BB");
        put("Chaos Sanctuary", "SS");
        put("Bloody Foothills, Frigid Highlands, and Abaddon", "BB");
        put("Bloody Foothills, Frigid Highlands and Abaddon", "BB");
        put("Bloody Foothills and Frigid Highlands", "BB");
        put("Arreat Plateau and Pit of Acheron", "BC");
        put("Frozen Tundra and Infernal Pit", "BB");
        put("Frozen Tundra", "BB");
        put("Crystalline Passage and Frozen River", "CC");
        put("Glacial Trail and Drifter Cavern", "CC");
        put("Nihlathak's Temple and Temple Halls", "BA");
        put("Nihlathak's Temple", "BA");
        put("Nihlathak's Temple, Halls of Anguish, Halls of Pain, and Halls of Vaught", "BA");
        put("Ancient's Way and Icy Cellar", "CC");
        put("Worldstone Keep, Throne of Destruction, and Worldstone Chamber", "SS");
    }};

    public String fetchRawData() {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.set("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/120.0.0.0 Safari/537.36");
            HttpEntity<String> entity = new HttpEntity<>(headers);
            ResponseEntity<String> response = restTemplate.exchange(API_URL, HttpMethod.GET, entity, String.class);
            
            // Raw 데이터에도 등급 정보 미리 주입 (Optional: FE 편의를 위해)
            String raw = response.getBody();
            return injectTiersToRawData(raw);
        } catch (Exception e) {
            return "{\"error\": \"" + e.getMessage() + "\"}";
        }
    }

    private String injectTiersToRawData(String raw) {
        try {
            Map<String, Object> data = objectMapper.readValue(raw, new TypeReference<Map<String, Object>>() {});
            processZoneWithTier(data, "currentTerrorZone");
            processZoneWithTier(data, "nextTerrorZone");
            return objectMapper.writeValueAsString(data);
        } catch (Exception e) {
            return raw;
        }
    }

    private void processZoneWithTier(Map<String, Object> data, String key) {
        @SuppressWarnings("unchecked")
        Map<String, Object> zoneData = (Map<String, Object>) data.get(key);
        if (zoneData != null && zoneData.containsKey("zone")) {
            String zoneName = (String) zoneData.get("zone");
            String tier = TZ_TIERS.getOrDefault(zoneName, "??");
            zoneData.put("zone", tier + " " + zoneName);
        }
    }

    @Scheduled(fixedRate = 60000) // Every 1 minute
    public void scheduleHistoryTracking() {
        System.out.println("Running Terror Zone history tracker...");
        String rawData = fetchRawData(); // 등급이 주입된 데이터 사용
        try {
            Map<String, Object> data = objectMapper.readValue(rawData, new TypeReference<Map<String, Object>>() {});
            @SuppressWarnings("unchecked")
            Map<String, Object> currentTz = (Map<String, Object>) data.get("currentTerrorZone");
            if (currentTz != null && currentTz.containsKey("zone")) {
                saveToHistory((String) currentTz.get("zone"));
            }
        } catch (Exception e) {
            System.err.println("Failed to parse TZ data for history: " + e.getMessage());
        }
    }

    private synchronized void saveToHistory(String zoneWithTier) {
        LocalDateTime now = LocalDateTime.now();
        String dateKey = now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        
        int hour = now.getHour();
        int min = now.getMinute() < 30 ? 0 : 30;
        String timeKey = String.format("%02d:%02d", hour, min);

        try {
            Map<String, Map<String, String>> history = loadHistory();
            history.computeIfAbsent(dateKey, k -> new LinkedHashMap<>());
            history.get(dateKey).put(timeKey, zoneWithTier);

            objectMapper.writerWithDefaultPrettyPrinter().writeValue(new File(HISTORY_FILE), history);
            System.out.println("Saved TZ history with Tier: " + dateKey + " " + timeKey + " -> " + zoneWithTier);
        } catch (IOException e) {
            System.err.println("Failed to save TZ history file: " + e.getMessage());
        }
    }

    public Map<String, Map<String, String>> loadHistory() {
        File file = new File(HISTORY_FILE);
        if (!file.exists()) return new TreeMap<>(Collections.reverseOrder());
        try {
            return objectMapper.readValue(file, new TypeReference<TreeMap<String, Map<String, String>>>() {});
        } catch (IOException e) {
            return new TreeMap<>(Collections.reverseOrder());
        }
    }

    public List<Map<String, String>> getRecentHistory(int limit) {
        Map<String, Map<String, String>> loadedHistory = loadHistory();
        // 날짜 역순 정렬 (최신 날짜 우선)
        TreeMap<String, Map<String, String>> fullHistory = new TreeMap<>(Collections.reverseOrder());
        fullHistory.putAll(loadedHistory);
        
        List<Map<String, String>> flatList = new ArrayList<>();
        
        for (Map.Entry<String, Map<String, String>> dateEntry : fullHistory.entrySet()) {
            String date = dateEntry.getKey();
            Map<String, String> dayMap = dateEntry.getValue();
            
            // 시간 슬롯 역순 정렬 (최신 시간 우선)
            List<String> times = new ArrayList<>(dayMap.keySet());
            Collections.sort(times, Collections.reverseOrder());
            
            for (String time : times) {
                Map<String, String> item = new HashMap<>();
                item.put("time", date + " " + time);
                item.put("zone", dayMap.get(time));
                flatList.add(item);
                if (flatList.size() >= limit) return flatList;
            }
        }
        return flatList;
    }
}
