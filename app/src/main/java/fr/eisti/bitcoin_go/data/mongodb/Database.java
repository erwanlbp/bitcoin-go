package fr.eisti.bitcoin_go.data.mongodb;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.mongodb.stitch.android.StitchClient;
import com.mongodb.stitch.android.auth.anonymous.AnonymousAuthProvider;
import com.mongodb.stitch.android.services.mongodb.MongoClient;

import org.bson.BsonArray;
import org.bson.BsonDouble;
import org.bson.Document;

import java.util.Arrays;
import java.util.List;

import fr.eisti.bitcoin_go.data.Bitcoin;
import fr.eisti.bitcoin_go.data.Location;

/**
 * Created by ErwanLBP on 06/11/17.
 */

// TODO Recherche par zone : insérer en tant que lat Long


public class Database {

    public final static String TAG = "##### DATABASE";

    private static Database instance;

    private Database() {

    }

    public static Database getInstance() {
        if (instance == null) {
            instance = new Database();
        }
        return instance;
    }

    private Task<String> doAuthentication(StitchClient client) {
        return client.logInWithProvider(new AnonymousAuthProvider());
    }

    public void insertOne(final Context context, final Bitcoin bitcoin) {

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

                                      Document loc = new Document();
                                      loc.put("type", "Point");
                                      loc.put("coordinates", new BsonArray(Arrays.asList(new BsonDouble(bitcoin.getLocation().getLatitude()), new BsonDouble(bitcoin.getLocation().getLongitude()))));
                                      updateDoc.put("location", loc);

                                      Log.i(TAG, updateDoc.toJson());
                                      return db.getCollection("bitcoins").insertOne(updateDoc);
                                  }
                              }
        ).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Log.i(TAG, "Completed .");
                    Toast.makeText(context, "Inserted !", Toast.LENGTH_LONG).show();
                } else {
                    Log.i(TAG, "Failed :" + task.getException().toString());
                    Toast.makeText(context, "Failed !", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public Task<List<Document>> find(Context context, final String name) {
        // Connexion
        final StitchClient stitchClient = new StitchClient(context, "bictoin-go-gwmle");
        final MongoClient mongoClient = new MongoClient(stitchClient, "mongodb-atlas");
        final MongoClient.Database db = mongoClient.getDatabase("location");

        Task<String> task = this.doAuthentication(stitchClient);

        return task.continueWithTask(new Continuation<String, Task<List<Document>>>() {
            @Override
            public Task<List<Document>> then(@NonNull Task<String> task) throws Exception {
                if (!task.isSuccessful()) {
                    throw task.getException();
                }
                Document findDoc = new Document();
                findDoc.put(Bitcoin.NAME, name);
                Log.i(TAG, "Find by name ...");
                return db.getCollection("bitcoins").find(findDoc);
            }
        });
    }

    public Task<List<Document>> find(Context context, final Location location) {
        // Connexion
        final StitchClient stitchClient = new StitchClient(context, "bictoin-go-gwmle");
        final MongoClient mongoClient = new MongoClient(stitchClient, "mongodb-atlas");
        final MongoClient.Database db = mongoClient.getDatabase("location");

        Task<String> task = this.doAuthentication(stitchClient);

        return task.continueWithTask(new Continuation<String, Task<List<Document>>>() {
            @Override
            public Task<List<Document>> then(@NonNull Task<String> task) throws Exception {
                if (!task.isSuccessful()) {
                    throw task.getException();
                }
                Log.i(TAG, "Find by location ...");

                Document geo = new Document("type", "Point");
                geo.put("coordinates", new BsonArray(Arrays.asList(new BsonDouble(location.getLatitude()), new BsonDouble(location.getLongitude()))));
                Document nearSphere = new Document("$geometry", geo);
                nearSphere.put("$maxDistance", "5000");
                Document filter = new Document("$near", nearSphere);
                Log.i(TAG, filter.toJson());

                return db.getCollection("bitcoins").find(new Document("location", filter));
            }
        }).addOnCompleteListener(new OnCompleteListener<List<Document>>() {
            @Override
            public void onComplete(@NonNull Task<List<Document>> task) {
                if (task.isSuccessful()) {
                    Log.i(TAG, task.getResult().toString());
                    return;
                }
                Log.e(TAG, task.getException().toString());
            }
        });
    }

    public Task<List<Document>> listAll(Context context) {
        // Connexion
        final StitchClient stitchClient = new StitchClient(context, "bictoin-go-gwmle");
        final MongoClient mongoClient = new MongoClient(stitchClient, "mongodb-atlas");
        final MongoClient.Database db = mongoClient.getDatabase("location");

        Task<String> task = this.doAuthentication(stitchClient);

        return task.continueWithTask(new Continuation<String, Task<List<Document>>>() {
            @Override
            public Task<List<Document>> then(@NonNull Task<String> task) throws Exception {
                if (!task.isSuccessful()) {
                    throw task.getException();
                }
                Log.i(TAG, "Query everything ...");
                return db.getCollection("bitcoins").find(new Document());
            }
        }).addOnCompleteListener(new OnCompleteListener<List<Document>>() {
            @Override
            public void onComplete(@NonNull Task<List<Document>> task) {
                if (!task.isSuccessful()) {
                    Log.e(TAG, task.getException().toString());
                    return;
                }
                Log.i(TAG, "Everything : ");
                for (Document doc : task.getResult()) {
                    Log.i(TAG, doc.toJson());
                }
            }
        });
    }
}