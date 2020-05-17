package models;

import com.google.firebase.auth.FirebaseAuth;

public class SearchModel {
    private FirebaseAuth auth;

    public SearchModel( FirebaseAuth auth) {
        this.auth = auth;
    }
}
