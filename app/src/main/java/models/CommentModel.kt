package models

import android.content.Context
import android.util.Log
import com.example.jab.CommentActivity
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.GeoPoint
import com.google.firebase.storage.FirebaseStorage
import custom_class.Comment
import custom_class.PointMap
import custom_class.User
import java.util.*
import kotlin.collections.ArrayList

class CommentModel(private val auth: FirebaseAuth, private val db: FirebaseFirestore) {
    private val storage: FirebaseStorage
    private val TAG = "CommentModel"

    fun loadComments(callback: CommentActivity.FirestoreCallBackKot, cityLocation: String?, cityCoordinates: ArrayList<PointMap>, cityLocationKey: String, localLocation: String?, localCoordinates: ArrayList<PointMap>, localLocationKey: String?, messageID: String, user: User?) {
        println(cityLocationKey)
        println(messageID)
        val docChats = db.collection("Chats").document("Cities").collection(cityLocationKey).document(messageID).collection("Comments")
        val listOfDocuments: MutableList<String> = ArrayList()
        docChats.get().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val comments = ArrayList<Comment?>()
                for (document in task.result!!) {
                    listOfDocuments.add(document.id)
                    val commentID = document.id
                    val content = document["Content"] as String?
                    val imageID = document["Image"] as String?
                    val location = document["Location"] as GeoPoint?
                    val timestamp = document["Timestamp"] as Timestamp?
                    val userUID = document["User"] as String?
                    val userName = document["UserName"] as String?
                    val likeNumber: Int = (document["LikeNumber"] as Number?)!!.toInt()
                    val imageWidth: Int = (document["ImageWidth"] as Number?)!!.toInt()
                    val imageHeight: Int = (document["ImageHeight"] as Number?)!!.toInt()
                    val imageBoolean = document["ImageBoolean"] as Boolean?
                    val columnNumber: Int = (document["ColumnNumber"] as Number?)!!.toInt()
                    val likeList = document["LikeList"] as ArrayList<String>?

                    //ArrayList<String> commentList = (ArrayList<String>) document.get("CommentList");
                    val gsReference = storage.getReferenceFromUrl("gs://jabdatabase.appspot.com/Comments/Cities/$cityLocationKey/$messageID/$commentID/photo.jpg")
                    val profPicReference = storage.getReferenceFromUrl("gs://jabdatabase.appspot.com/UserData/$userUID/profilePic.jpg")
                    val comment = Comment(content, imageID, location, timestamp, userUID, userName, likeNumber, likeList, gsReference, profPicReference, imageWidth, imageHeight, imageBoolean, columnNumber)
                    comments.add(comment)
                }
                callback.onCallback(comments)
                Log.d(TAG, listOfDocuments.toString())
            } else {
                Log.d(TAG, "Error getting documents: ", task.exception)
                callback.onCallback(null)
            }
        }
    }


    companion object {
        // or return instance.getApplicationContext();
        val context: Context? = null
    }

    init {
        storage = FirebaseStorage.getInstance()
    }
}