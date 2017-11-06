package fr.eisti.bitcoin_go.data.mongodb;

import android.content.Context;

import com.mongodb.stitch.android.StitchClient;
import com.mongodb.stitch.android.services.mongodb.MongoClient;

/**
 * Created by ErwanLBP on 06/11/17.
 */

public class Database {

    public static StitchClient findAllBitcoin(Context context) {
        final StitchClient stitchClient = new StitchClient(context, "bictoin-go-gwmle");
        final MongoClient mongoClient = new MongoClient(stitchClient, "mongodb-atlas");
        final MongoClient.Database db = mongoClient.getDatabase("guidebook");
        return stitchClient;
    }


}
