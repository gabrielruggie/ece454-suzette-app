package com.example.suzette;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.suzette.virtualassistant.Message;
import com.example.suzette.virtualassistant.MessageAdapter;
import com.google.android.material.button.MaterialButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class CookingActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    String rec;
    String system;
    boolean first;
    EditText messageEditText;
    MaterialButton sendButton;
    List<Message> messageList;
    MessageAdapter messageAdapter;
    List<JSONObject> messageHistory;
    public static final MediaType JSON
            = MediaType.get("application/json; charset=utf-8");
    OkHttpClient client = new OkHttpClient.Builder()
            .connectTimeout(30, TimeUnit.SECONDS)  // Connection timeout
            .readTimeout(30, TimeUnit.SECONDS)     // Read timeout
            .build();

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        first = true;
        setContentView(R.layout.activity_cooking_activity);
        messageList = new ArrayList<>();
        Intent intent = getIntent();
        system = intent.getStringExtra("secondPrompt");
         rec = intent.getStringExtra("firstPrompt");
        recyclerView = findViewById(R.id.recycler_view);

        messageEditText = findViewById(R.id.editTextUserInput);
        sendButton = findViewById(R.id.buttonSend);

        //setup recycler view
        messageAdapter = new MessageAdapter(messageList);
        recyclerView.setAdapter(messageAdapter);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setStackFromEnd(true);
        recyclerView.setLayoutManager(llm);

        if(first){

            messageEditText.setText("");
            callAPI("help me cook " + rec);

        }
        sendButton.setOnClickListener((v)->{
            String question = messageEditText.getText().toString().trim();
            addToChat(question,Message.SENT_BY_ME);
            messageEditText.setText("");
            callAPI(question);

        });
    }

    void addToChat(String message,String sentBy){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                messageList.add(new Message(message,sentBy));
                messageAdapter.notifyDataSetChanged();
                recyclerView.smoothScrollToPosition(messageAdapter.getItemCount());
            }
        });
    }

    void addResponse(String response){
        messageList.remove(messageList.size()-1);
        addToChat(response,Message.SENT_BY_BOT);
    }

    void callAPI(String question){
        //okhttp
        messageList.add(new Message("Typing...", Message.SENT_BY_BOT));
        if (messageHistory == null) {
            messageHistory = new ArrayList<>();
        }


// Create a user message
        JSONObject userMessage = new JSONObject();
        try {
            userMessage.put("role", "user");
            userMessage.put("content", question);  // Your question content
            messageHistory.add(userMessage);  // Add to messages array
        } catch (JSONException e) {
            e.printStackTrace();  // Handle error
        }
        if(first) {
            first = false;
// Create a system message
            JSONObject systemMessage = new JSONObject();
            try {
                systemMessage.put("role", "system");
                systemMessage.put("content", system);  // System message content
                messageHistory.add(systemMessage);  // Add to messages array
            } catch (JSONException e) {
                e.printStackTrace();  // Handle error
            }
        }


        long timerInMilliseconds = extractTimerDuration(question);
        JSONArray messages = new JSONArray();
        for (JSONObject message : messageHistory) {
            messages.put(message);
        }
        Log.d("timer", ""+timerInMilliseconds);
        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("model", "gpt-4");
            jsonBody.put("messages", messages);  // Correctly structured messages array
            jsonBody.put("max_tokens", 4000);
            jsonBody.put("temperature", 0);
            JSONArray tools = new JSONArray();
            JSONObject functionTool = new JSONObject();

            if (timerInMilliseconds > 0) {

                functionTool.put("type", "function");

                JSONObject functionDetails = new JSONObject();
                functionDetails.put("name", "startCountdownTimer");
                functionDetails.put("description", "Start a countdown timer with a specified duration");

                JSONObject parameters = new JSONObject();
                parameters.put("type", "object");

                JSONObject properties = new JSONObject();
                properties.put("time_in_ms", new JSONObject().put("type", "integer"));

                parameters.put("properties", properties);
                parameters.put("required", new JSONArray().put("time_in_ms"));

                functionDetails.put("parameters", parameters);

                functionTool.put("function", functionDetails);


                tools.put(functionTool);


                jsonBody.put("tools", tools);


// Adding function parameters
                JSONObject functionArgs = new JSONObject();
                functionArgs.put("time_in_ms", timerInMilliseconds);


            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestBody body = RequestBody.create(jsonBody.toString(),JSON);
        Request request = new Request.Builder()
                .url("https://api.openai.com/v1/chat/completions")
                .header("Authorization","Bearer ")
                .post(body)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                addResponse("Failed to load response due to " + e.getMessage());
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if (response.body() == null) {
                    addResponse("Failed to load response: empty body");
                    return;
                }

                String responseBodyString = response.body().string();

                try {
                    if (response.isSuccessful()) {
                        JSONObject jsonObject = new JSONObject(responseBodyString);
                        JSONArray choices = jsonObject.getJSONArray("choices");

                        // Validate if the expected structure is correct
                        if (choices.length() > 0 && choices.getJSONObject(0).has("message")) {
                            JSONObject message = choices.getJSONObject(0).getJSONObject("message");
                            messageHistory.add(message);
                            if (message.has("content")) {
                                String content = message.getString("content");
                                addResponse(content.trim());
                            } else {
                                addResponse("Failed to parse response: 'content' field missing");
                            }
                        } else {
                            addResponse("Failed to parse response: 'message' object missing");
                        }
                    } else {
                       addResponse("Failed to load response: " + responseBodyString);
                    }
                } catch (JSONException e) {
                    addResponse("Failed to parse response: " + e.getMessage());
                }

            }
        });

    }
    private long extractTimerDuration(String userInput) {
        // Simple regex to extract a number and a time unit from the user's input
        Pattern pattern = Pattern.compile("(\\d+)\\s*(seconds?|minutes?|hours?)", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(userInput);

        if (matcher.find()) {
            int timeValue = Integer.parseInt(matcher.group(1));
            String timeUnit = matcher.group(2).toLowerCase();

            switch (timeUnit) {
                case "second":
                case "seconds":
                    return timeValue * 1000; // Convert to milliseconds
                case "minute":
                case "minutes":
                    return timeValue * 60 * 1000; // Convert to milliseconds
                case "hour":
                case "hours":
                    return timeValue * 60 * 60 * 1000; // Convert to milliseconds
                default:
                    return 0;
            }
        }

        return 0; // Return zero if no valid timer duration is found
    }

}

