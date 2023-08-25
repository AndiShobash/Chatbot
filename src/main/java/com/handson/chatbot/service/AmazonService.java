package com.handson.chatbot.service;

import okhttp3.*;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

@Service
public class AmazonService {

    public static final Pattern PRODUCT_PATTERN = Pattern.compile("<span class=\"a-size-medium a-color-base a-text-bold a-text-normal\">iPod<\\/span><span class=\"a-size-medium a-color-base a-text-normal\">([^<]+)<\\/span>.*<div class=\"a-row a-size-small\"><span aria-label=([^<]+)<span class=\"a-declarative\" data-version-id=.*<span class=\"a-offscreen\">([^<]+)");
    public String searchProducts(String keyword) throws IOException {
        return parseProductHtml(getProductHtml(keyword));
    }
    private String parseProductHtml(String html) {
        String res = "";
        Matcher matcher = PRODUCT_PATTERN.matcher(html);
        while (matcher.find()) {
            res += matcher.group(1) + " - " + matcher.group(2) + ", price:" + matcher.group(3) + "<br>\n";
        }
        return res;
    }
    private String getProductHtml(String keyword) throws IOException {
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        MediaType mediaType = MediaType.parse("text/plain");
        RequestBody body = RequestBody.create(mediaType, "");
        Request request = new Request.Builder()
                .url("https://www.amazon.com/s?i=aps&k="+keyword+"&ref=nb_sb_noss&url=search-alias%3Daps")
                .method("GET", null)
                .addHeader("authority", "www.amazon.com")
                .addHeader("accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.7")
                .addHeader("accept-language", "en-US,en;q=0.9,he-IL;q=0.8,he;q=0.7")
                .addHeader("cookie", "session-id=132-2520208-4835800; session-id-time=2082787201l; i18n-prefs=USD; ubid-main=130-6218988-1394501; sp-cdn=\"L5Z9:IL\"; session-token=\"+IoJMLyRKyqR41uTQ0wMh2Me7JeFjwUrvoXQ9eODrUzB/az2qp/iLf33S6KGtYmoQdaqi/CB1JtGE5R8inzH7CtJSFDJaQ1w1iXT35qbV/a9dtmlXw3jNTN3avnb3vSwGVGeoHrn3kcHtaMz87/MIZhSjnL+Pz9jv8LIUIklNiOt0OAvIFj7NkZhkbgsfBmHnXlShJWvr9xw0NMgqZmZze0dcKPohu6D180dnbIG0JY=\"; csm-hit=tb:9KQQ3WNRHEY4GXS6F4JH+s-M2WFR4RVSER53F36RZEX|1690448346443&t:1690448346443&adb:adblk_yes")
                .addHeader("device-memory", "8")
                .addHeader("downlink", "1.3")
                .addHeader("dpr", "1")
                .addHeader("ect", "4g")
                .addHeader("referer", "https://www.amazon.com/s?k=ipod&ref=nb_sb_noss")
                .addHeader("rtt", "100")
                .addHeader("sec-ch-device-memory", "8")
                .addHeader("sec-ch-dpr", "1")
                .addHeader("sec-ch-ua", "\"Not/A)Brand\";v=\"99\", \"Google Chrome\";v=\"115\", \"Chromium\";v=\"115\"")
                .addHeader("sec-ch-ua-mobile", "?0")
                .addHeader("sec-ch-ua-platform", "\"Windows\"")
                .addHeader("sec-ch-ua-platform-version", "\"10.0.0\"")
                .addHeader("sec-ch-viewport-width", "1365")
                .addHeader("sec-fetch-dest", "document")
                .addHeader("sec-fetch-mode", "navigate")
                .addHeader("sec-fetch-site", "same-origin")
                .addHeader("sec-fetch-user", "?1")
                .addHeader("upgrade-insecure-requests", "1")
                .addHeader("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/115.0.0.0 Safari/537.36")
                .addHeader("viewport-width", "1365")
                .build();
        Response response = client.newCall(request).execute();
        return response.body().string();
    }

}
