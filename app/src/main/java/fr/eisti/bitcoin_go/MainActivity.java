package fr.eisti.bitcoin_go;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.mongodb.stitch.android.StitchClient;
import com.mongodb.stitch.android.auth.anonymous.AnonymousAuthProvider;
import com.mongodb.stitch.android.services.mongodb.MongoClient;

import org.bson.Document;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = "##### STITCH";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        final StitchClient stitchClient = new StitchClient(this, "bictoin-go-gwmle");
        final MongoClient mongoClient = new MongoClient(stitchClient, "mongodb-atlas");
        final MongoClient.Database db = mongoClient.getDatabase("location");

        stitchClient.logInWithProvider(new AnonymousAuthProvider()).continueWithTask(
                new Continuation<String, Task<Void>>() {
                    @Override
                    public Task<Void> then(@NonNull Task<String> task) throws Exception {
                        final Document updateDoc = new Document(
                                "owner_id",
                                task.getResult()
                        );

                        updateDoc.put("number", 48);
                        return db.getCollection("bitcoins").updateOne(null, updateDoc, true);
                    }
                }
        ).continueWithTask(new Continuation<Void, Task<List<Document>>>() {
            @Override
            public Task<List<Document>> then(@NonNull Task<Void> task) throws Exception {
                if (!task.isSuccessful()) {
                    throw task.getException();
                }
                return db.getCollection("bitcoins").find(
                        new Document("owner_id", stitchClient.getUserId())
                );
            }
        }).addOnCompleteListener(new OnCompleteListener<List<Document>>() {
            @Override
            public void onComplete(@NonNull Task<List<Document>> task) {
                if (task.isSuccessful()) {
                    Log.d(TAG, task.getResult().toString());
                    return;
                }
                Log.e(TAG, task.getException().toString());
            }
        });
    }
}
