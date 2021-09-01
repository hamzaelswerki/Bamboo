package com.example.bamboo.view;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.bamboo.R;
import com.example.bamboo.adapter.ChatAdapter;
import com.example.bamboo.model.SharedPrefHelper;
import com.example.bamboo.model.entity.Chat;
import com.example.bamboo.veiwModel.ChatViewModel;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class ChatBranch extends AppCompatActivity {
    TextView nameBranch;

    RecyclerView recyclerView;
    ArrayList<Chat> msgs = new ArrayList<>();
    EditText ed_messege;
    ImageView img_send;
    ImageButton btnBake;
    ChatAdapter chatAdabter;
    FirebaseFirestore firebaseFirestore;
    ChatViewModel chatViewModel;

    int listSize=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_branch);
        nameBranch = findViewById(R.id.text_title_condition);
        recyclerView = findViewById(R.id.list_chat);
        ed_messege = findViewById(R.id.edit_chat);
        setStatusBarColor();
        img_send = findViewById(R.id.img_send);
        btnBake = findViewById(R.id.btn_back);
        firebaseFirestore = FirebaseFirestore.getInstance();

        nameBranch.setText(getIntent().getStringExtra("branchName"));


        chatViewModel = ViewModelProviders.of(this).get(ChatViewModel.class);
        getMessages();


  /*      FirebaseFirestore.getInstance().collection("branchs").
                document(getIntent().getStringExtra("branchId"))
                .collection(FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber())
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                for (DocumentSnapshot snapshot : task.getResult().getDocuments()) {
                    Chat chat = snapshot.toObject(Chat.class);

                    msgs.add(chat);

                    chatAdabter.notifyDataSetChanged();
                }
            }
        });

*/
        img_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendMsg();
            }


        });

        btnBake.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });


    }

    @Override
    protected void onStart() {
        super.onStart();
        if (chatAdabter != null) {
            chatAdabter.startListening();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (chatAdabter != null) {
            chatAdabter.stopListening();
        }
    }

    private void sendMsg() {
        String message = ed_messege.getText().toString().trim();
        if (!message.equals("") && message != null) {
            ed_messege.setText("");
            Chat chat = new Chat(message, SharedPrefHelper.getToken(getApplicationContext()),
                    getIntent().getStringExtra("branchId"), Timestamp.now(), "user");


            if (listSize==0){
                Chat chatBranch = new Chat("Hello , How can I help you ?", SharedPrefHelper.getToken(getApplicationContext()),
                        getIntent().getStringExtra("branchId"), Timestamp.now(), "branch");

            new Thread(new Runnable() {
                 @Override
                 public void run() {
                         try {
                             Log.d("ttt","hi1");
                             chatViewModel.addMessageToChat(chat);
                             Thread.sleep(2500);
                             Log.d("ttt","hi2");
                             chatViewModel.addMessageToChat(chatBranch);

                         } catch (InterruptedException e) {
                             e.printStackTrace();
                          }}

             }).start();

                listSize=1;
            }else {
                chatViewModel.addMessageToChat(chat);
            }

            chatAdabter.notifyDataSetChanged();

        }

    }

    private void setStatusBarColor() {

        Window window = this.getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(getResources().getColor(R.color.text));
    }


    private void getMessages() {
        getSize();
        LinearLayoutManager llm = new LinearLayoutManager(getApplicationContext());
        llm.setOrientation(LinearLayoutManager.VERTICAL);

        recyclerView.setLayoutManager(llm);

        chatViewModel.getListMessages(getIntent().getStringExtra("branchId"),getApplicationContext())
                .observe(this, new Observer<FirestoreRecyclerOptions<Chat>>() {
            @Override
            public void onChanged(FirestoreRecyclerOptions<Chat> chatFirestoreRecyclerOptions) {

  Log.d("ttt",
          chatFirestoreRecyclerOptions.getSnapshots().size()+"a=====> dsds");
                chatAdabter = new ChatAdapter(chatFirestoreRecyclerOptions);
                chatAdabter.startListening();
                recyclerView.setAdapter(chatAdabter);
            }


        });

    }



    void  getSize(){
        FirebaseFirestore.getInstance().collection("branchs").
                document(getIntent().getStringExtra("branchId")).
                        collection(SharedPrefHelper.getToken(getApplicationContext())).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {

                if (task.isSuccessful())
            listSize=task.getResult().size();
            }
        });
    }
}