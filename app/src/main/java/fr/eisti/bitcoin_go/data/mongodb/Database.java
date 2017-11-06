package fr.eisti.bitcoin_go.data.mongodb;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.mongodb.stitch.android.StitchClient;
import com.mongodb.stitch.android.auth.anonymous.AnonymousAuthProvider;
import com.mongodb.stitch.android.services.mongodb.MongoClient;

import org.bson.Document;

import java.util.List;

/**
 * Created by ErwanLBP on 06/11/17.
 */

public class Database {

    public final static String TAG = "DATABASE";

    public static StitchClient findAllBitcoin(Context context) {
        final StitchClient stitchClient = new StitchClient(context, "bictoin-go-gwmle");
        final MongoClient mongoClient = new MongoClient(stitchClient, "mongodb-atlas");
        final MongoClient.Database db = mongoClient.getDatabase("guidebook");
        return stitchClient;
    }
//
//    public void insertOne(Context context) {
//
//        final StitchClient stitchClient = new StitchClient(this, "bictoin-go-gwmle");
//        final MongoClient mongoClient = new MongoClient(stitchClient, "mongodb-atlas");
//        final MongoClient.Database db = mongoClient.getDatabase("location");
//
//        stitchClient.logInWithProvider(new AnonymousAuthProvider())
//                .continueWithTask(new Continuation<String, Task<Void>>() {
//                                      @Override
//                                      public Task<Void> then(@NonNull Task<String> task) throws Exception {
//                                          final Document updateDoc = new Document(
//                                                  "owner_id",
//                                                  task.getResult()
//                                          );
//
//                                          updateDoc.put("number", 48);
//                                          return db.getCollection("bitcoins").updateOne(null, updateDoc, true);
//                                      }
//                                  }
//                ).continueWithTask(new Continuation<Void, Task<List<Document>>>() {
//            @Override
//            public Task<List<Document>> then(@NonNull Task<Void> task) throws Exception {
//                if (!task.isSuccessful()) {
//                    throw task.getException();
//                }
//                return db.getCollection("bitcoins").find(
//                        new Document("owner_id", stitchClient.getUserId())
//                );
//            }
//        }).addOnCompleteListener(new OnCompleteListener<List<Document>>() {
//            @Override
//            public void onComplete(@NonNull Task<List<Document>> task) {
//                if (task.isSuccessful()) {
//                    Log.d(TAG, task.getResult().toString());
//                    return;
//                }
//                Log.e(TAG, task.getException().toString());
//            }
//        });
//    }

//
//
//    private void doAnonymousAuthentication() {
//        _client.getAuthProviders().continueWithTask(new Continuation<AvailableAuthProviders, Task<Auth>>() {
//            @Override
//            public Task<Auth> then(@NonNull Task<AvailableAuthProviders> task) throws Exception {
//                if (!task.isSuccessful()) {
//                    Log.e(TAG, "Could not retrieve authentication providers", task.getException());
//                    throw task.getException();
//                }
//
//                Log.i(TAG, "Retrieved authentication providers");
//                if (!task.getResult().hasAnonymous()) {
//                    throw new Exception("Anonymous login not allowed");
//                }
//
//                return _client.logInWithProvider(new AnonymousAuthProvider());
//            }}).addOnSuccessListener(new OnSuccessListener<Auth>() {
//            @Override
//            public void onSuccess(@NonNull Auth auth) {
//                _client.getUserProfile().addOnCompleteListener(new OnCompleteListener<UserProfile>() {
//                    @Override
//                    public void onComplete(@NonNull Task<UserProfile> task) {
//                        Log.i(TAG, "User Authenticated as " + task.getResult().getId());
//                    }
//                });
//
//                _refreshButton.setEnabled(true);
//                _saveButton.setEnabled(true);
//                _textWidget.setEnabled(true);
//            }
//        }).addOnFailureListener(new OnFailureListener() {
//            @Override
//            public void onFailure(@NonNull Exception e) {
//                Log.e(TAG, "Error logging in anonymously", e);
//            }
//        });
//    }

}
