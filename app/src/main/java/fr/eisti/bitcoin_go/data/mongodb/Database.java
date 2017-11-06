package fr.eisti.bitcoin_go.data.mongodb;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.mongodb.stitch.android.Auth;
import com.mongodb.stitch.android.StitchClient;
import com.mongodb.stitch.android.auth.AvailableAuthProviders;
import com.mongodb.stitch.android.auth.anonymous.AnonymousAuthProvider;
import com.mongodb.stitch.android.services.mongodb.MongoClient;

import org.bson.Document;

import java.util.List;

import fr.eisti.bitcoin_go.data.Bitcoin;

/**
 * Created by ErwanLBP on 06/11/17.
 */

public class Database {

    public final static String TAG = "DATABASE";

    public void insertOne(Context context, final Bitcoin bitcoin) {

        // Connexion
        final StitchClient stitchClient = new StitchClient(context, "bictoin-go-gwmle");
        final MongoClient mongoClient = new MongoClient(stitchClient, "mongodb-atlas");
        final MongoClient.Database db = mongoClient.getDatabase("location");

        Task<String> task = this.doAuthentication(stitchClient);

        task.continueWithTask(new Continuation<String, Task<Void>>() {
                                  @Override
                                  public Task<Void> then(@NonNull Task<String> task) throws Exception {
                                      final Document updateDoc = new Document(
                                              "owner_id",
                                              task.getResult()
                                      );

                                      updateDoc.put(Bitcoin.NAME, bitcoin.getName());
                                      updateDoc.put(Bitcoin.VALUE, bitcoin.getValue());
                                      updateDoc.put(Bitcoin.LOCATION, bitcoin.getLocation());

                                      return db.getCollection("bitcoins").updateOne(null, updateDoc, true);
                                  }
                              }
        ).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Log.i(TAG, "Completed .");
            }
        });
    }


    private Task<String> doAuthentication(StitchClient client) {
        return client.logInWithProvider(new AnonymousAuthProvider());
    }

}
