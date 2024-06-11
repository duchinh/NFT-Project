/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package function;

import java.util.ArrayList;
import java.util.List;
import model.Tweet;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.JSONObject;
import java.time.LocalDate;
import java.time.temporal.WeekFields;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import model.Nft;

/**
 *
 * @author Admin
 */
public class TweetFunction extends JsonParser implements FUNCInterface<Tweet> {

    public static TweetFunction getInstance() {

        return new TweetFunction();
    }

   public TweetFunction(){
       setFilename("C:\\Users\\Admin\\Downloads\\tweetdata600.json");//tìm đường dẫn của tweetdata600 rồi add vào dòng này
   }
   

    @Override
    protected String getJSONFromFile() {
        return super.getJSONFromFile(); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
    }

    @Override
    public ArrayList<Tweet> read() {
        String jsonString = getJSONFromFile();
        List<Tweet> tweetList = new ArrayList<>();

        try {
            JSONParser parser = new JSONParser();
            JSONArray jsonArray = (JSONArray) parser.parse(jsonString);

            for (Object jsonObject : jsonArray) {
                JSONObject tweetObject = (JSONObject) jsonObject;
                JSONObject userObject = (JSONObject) tweetObject.get("user");

                Tweet tweet = new Tweet();

                // Extract screen_name
                tweet.setAuthor((String) userObject.get("screen_name"));

                // Extrat favourites_count
                if (userObject.get("favourites_count") != null) {
                    tweet.setLike((Long) userObject.get("favourites_count"));
                }

                // Exract hashtags
                if (tweetObject.get("hashtags") != null) {
                    JSONArray hashtagArray = (JSONArray) tweetObject.get("hashtags");
                    List<String> hashtagList = new ArrayList<>();
                    for (Object hashtag : hashtagArray) {
                        hashtagList.add((String) hashtag);
                    }
                    tweet.setHashtag(hashtagList);
                }

                tweet.setId((String) tweetObject.get("id"));
                tweet.setContent((String) tweetObject.get("full_text"));
                //Extract date created 
                if (tweetObject.get("created_at") != null) {
                    String date = (String) tweetObject.get("created_at");
                    date = date.substring(0, 10); // "2021-06-03"
                    tweet.setDate(date);
                }

                tweetList.add(tweet);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return (ArrayList<Tweet>) tweetList;
    }

    public ArrayList<Tweet> searchByHashtag(String hashtag) {
        // Use a HashSet for faster search
        Set<String> targetHashtags = new HashSet<>();
        targetHashtags.add(hashtag.toLowerCase()); // Case-insensitive search

        ArrayList<Tweet> filteredTweets = new ArrayList<>();
        // Iterate through all tweets
        for (Tweet tweet : read()) {
            // Check if the tweet contains any of the target hashtags
            for (String tweetHashtag : tweet.getHashtag()) {
                if (targetHashtags.contains(tweetHashtag.toLowerCase())) {
                    // Add the tweet to the filtered list
                    filteredTweets.add(tweet);
                    break; // No need to check remaining hashtags
                }
            }
        }

        return filteredTweets;
    }

    public ArrayList<Tweet> tweetsWithHottestHashtagByDay(String date) {
        HashMap<String, Integer> hashtagCounts = new HashMap<>();
        ArrayList<Tweet> tweetsWithHottestHashtag = new ArrayList<>();

        // Iterate through tweets to find tweets on the specified date
        for (Tweet tweet : read()) {
            if (tweet.getDate().equals(date)) {
                List<String> hashtags = tweet.getHashtag();
                for (String hashtag : hashtags) {
                    hashtagCounts.put(hashtag, hashtagCounts.getOrDefault(hashtag, 0) + 1);
                }
            }
        }

        // Find the hashtag with the highest count
        String hottestHashtag = "";
        int maxCount = 0;
        for (Map.Entry<String, Integer> entry : hashtagCounts.entrySet()) {
            if (entry.getValue() > maxCount) {
                maxCount = entry.getValue();
                hottestHashtag = entry.getKey();
            }
        }

        // Retrieve tweets that contain the hottest hashtag
        for (Tweet tweet : read()) {
            if (tweet.getDate().equals(date) && tweet.getHashtag().contains(hottestHashtag)) {
                tweetsWithHottestHashtag.add(tweet);
            }
        }

        return tweetsWithHottestHashtag;
    }

    public ArrayList<Tweet> hottestHashtagByMonth(String yearMonth) {
        HashMap<String, Integer> hashtagCounts = new HashMap<>();
        ArrayList<Tweet> tweetsInMonth = new ArrayList<>();

        String[] parts = yearMonth.split("-");
        String targetYear = parts[0]; // Lấy năm
        String targetMonth = parts[1]; // Lấy tháng

        // Lặp qua các Tweet để tìm Tweet trong năm và tháng được chỉ định
        for (Tweet tweet : read()) {
            String tweetDate = tweet.getDate();
            String[] tweetDateParts = tweetDate.split("-");
            String tweetYear = tweetDateParts[0]; // Lấy năm từ ngày của Tweet
            String tweetMonth = tweetDateParts[1]; // Lấy tháng từ ngày của Tweet

            if (tweetYear.equals(targetYear) && tweetMonth.equals(targetMonth)) {
                tweetsInMonth.add(tweet); // Thêm Tweet vào danh sách nếu nó được tạo ra trong năm và tháng cụ thể

                List<String> hashtags = tweet.getHashtag();
                // Đếm hashtag trong tweets của tháng và cập nhật số lần xuất hiện vào HashMap
                for (String hashtag : hashtags) {
                    hashtagCounts.put(hashtag, hashtagCounts.getOrDefault(hashtag, 0) + 1);
                }
            }
        }

        // Tìm hashtag có số lần xuất hiện nhiều nhất trong tháng
        int maxCount = 0;
        String hottestHashtag = "";
        for (Map.Entry<String, Integer> entry : hashtagCounts.entrySet()) {
            if (entry.getValue() > maxCount) {
                maxCount = entry.getValue();
                hottestHashtag = entry.getKey();
            }
        }

        // Lọc lại danh sách Tweet chỉ giữ lại những Tweet có chứa hashtag nhiều nhất
        ArrayList<Tweet> hottestTweets = new ArrayList<>();
        for (Tweet tweet : tweetsInMonth) {
            if (tweet.getHashtag().contains(hottestHashtag)) {
                hottestTweets.add(tweet);
            }
        }

        return hottestTweets;
    }

    private boolean isDateInRange(String tweetDate, String startDate, String endDate) {

        return (tweetDate.compareTo(startDate) >= 0) && (tweetDate.compareTo(endDate) <= 0);
    }

    public ArrayList<Tweet> tweetsWithHottestHashtagBetweenDates(String date1, String date2) {
        HashMap<String, Integer> hashtagCounts = new HashMap<>();
        ArrayList<Tweet> tweetsWithinDateRange = new ArrayList<>();

        // Iterate through tweets to find tweets within the specified date range
        for (Tweet tweet : read()) {
            String tweetDate = tweet.getDate(); // Assuming tweet.getDate() returns the date string

            // Check if the tweet date is within the specified range
            if (isDateInRange(tweetDate, date1, date2)) {
                List<String> hashtags = tweet.getHashtag();
                for (String hashtag : hashtags) {
                    hashtagCounts.put(hashtag, hashtagCounts.getOrDefault(hashtag, 0) + 1);
                }
                // Collect tweets within the date range
                tweetsWithinDateRange.add(tweet);
            }
        }

        // Find the hashtag with the highest count
        String hottestHashtag = "";
        int maxCount = 0;
        for (Map.Entry<String, Integer> entry : hashtagCounts.entrySet()) {
            if (entry.getValue() > maxCount) {
                maxCount = entry.getValue();
                hottestHashtag = entry.getKey();
            }
        }

        // Filter tweets that contain the hottest hashtag within the date range
        ArrayList<Tweet> tweetsWithHottestHashtag = new ArrayList<>();
        for (Tweet tweet : tweetsWithinDateRange) {
            if (tweet.getHashtag().contains(hottestHashtag)) {
                tweetsWithHottestHashtag.add(tweet);
            }
        }

        return tweetsWithHottestHashtag;
    }
    


}
