package com.example.suzette;

import android.annotation.SuppressLint;
import android.os.Bundle;
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

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class CookingActivity extends AppCompatActivity {
    RecyclerView recyclerView;

    EditText messageEditText;
    MaterialButton sendButton;
    List<Message> messageList;
    MessageAdapter messageAdapter;
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
        setContentView(R.layout.activity_cooking_activity);
        messageList = new ArrayList<>();

        recyclerView = findViewById(R.id.recycler_view);

        messageEditText = findViewById(R.id.editTextUserInput);
        sendButton = findViewById(R.id.buttonSend);

        //setup recycler view
        messageAdapter = new MessageAdapter(messageList);
        recyclerView.setAdapter(messageAdapter);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setStackFromEnd(true);
        recyclerView.setLayoutManager(llm);

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

        // Build the JSON array of messages
        JSONArray messages = new JSONArray();

        // This example creates a new JSON object for the user message
        JSONObject userMessage = new JSONObject();
        try {
            userMessage.put("role", "user");
            userMessage.put("content", question);
            messages.put(userMessage);
        } catch (JSONException e) {
            addResponse("Failed to construct message: " + e.getMessage());
        }

        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("model", "gpt-4");
            jsonBody.put("messages", messages);  // Correctly structured messages array
            jsonBody.put("max_tokens", 4000);
            jsonBody.put("temperature", 0);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestBody body = RequestBody.create(jsonBody.toString(),JSON);
        Request request = new Request.Builder()
                .url("https://api.openai.com/v1/chat/completions")
                .header("Authorization","Bearer sk-proj-FOumMRQ3cjbbjjVlPhf3T3BlbkFJKTMsZWXIGka9cN8c6wa3")
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


}