package controllers

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.google.firebase.auth.FirebaseAuth
import custom_class.Place
import custom_class.PointMap
import custom_class.User
import java.util.*

class NewChatController(private val auth: FirebaseAuth, private val context: Context) {

    @Throws(ClassNotFoundException::class)
    fun goBack(intent: Intent?,localUniversityPlaces: ArrayList<Place>?, localCityPlaces: ArrayList<Place>?, user: User?, place: Place?) {
        val caller = intent?.getStringExtra("caller")
        val callerClass = Class.forName(caller!!)
        val bundle = Bundle()
        bundle.putParcelableArrayList("LocalUniversityPlaces", localUniversityPlaces)
        bundle.putParcelableArrayList("LocalCityPlaces", localCityPlaces)
        bundle.putParcelable("Place",place)
        bundle.putParcelable("User", user)

        val newIntent = Intent(context, callerClass)
        newIntent.putExtras(bundle)
        newIntent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
        context.startActivity(newIntent)
    }

}