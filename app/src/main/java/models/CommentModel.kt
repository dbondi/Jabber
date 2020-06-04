package models

import android.content.Context
import android.graphics.Bitmap
import android.location.Location
import android.os.Bundle
import android.util.Log
import com.example.jab.CommentActivity
import com.giphy.sdk.core.models.Media
import com.google.firebase.Timestamp
import com.google.firebase.Timestamp.now
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.GeoPoint
import com.google.firebase.storage.FirebaseStorage
import custom_class.Comment
import custom_class.HelperFunctions.random_color
import custom_class.Place
import custom_class.PointMap
import custom_class.User
import java.io.ByteArrayOutputStream


class CommentModel(private val auth: FirebaseAuth, private val db: FirebaseFirestore) {
    private val storage: FirebaseStorage
    private val TAG = "CommentModel"

    fun loadComments(callback: CommentActivity.FirestoreCallBackKot, messageID: String, user: User?, place: Place) {
        println(messageID)
        val docChats = db.collection("Chats").document(place.type).collection(place.ipedsid).document(messageID).collection("Comments")
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
                    val gsReference = storage.getReferenceFromUrl("gs://jabdatabase.appspot.com/Comments/${place.type}/${place.ipedsid}/${messageID}/${commentID}/photo.jpg")
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

    fun post_string(commentPost: String, bundle: Bundle, userLocation: Location?, generateColor: String, place: Place) {

        var user = bundle.getParcelable("User") as User?
        var timestamp = now()
        var color = generateColor
        var imageBoolean = false
        var gifBoolean = false
        var stringBoolean = true
        var userID = user?.uid
        var userName = "Dan"//user?.profileName
        var geoPoint = GeoPoint(userLocation?.latitude!!, userLocation?.longitude!!)
        var messageID = bundle.getString("MessageID").toString()

        val emptyStringArray = ArrayList<String>()
        // Create a new course object with information
        val course: HashMap<String, Any> = HashMap()
        course["Timestamp"] = timestamp
        course["Color"] = color
        course["ImageBoolean"] = imageBoolean
        course["GifBoolean"] = gifBoolean
        course["StringBoolean"] = stringBoolean
        course["User"] = userID!!
        course["UserName"] = userName!!
        course["Location"] = geoPoint
        course["Content"] = commentPost
        course["LikeList"] = emptyStringArray
        course["LikeNumber"] = 0

        val collectionRef = db.collection("Chats").document(place.type).collection(place.ipedsid).document(messageID).collection("Comments")
        collectionRef.add(course).addOnSuccessListener { documentReference ->
            Log.d(TAG, "DocumentSnapshot written with ID: ${documentReference.id}")
        }
                .addOnFailureListener { e ->
                    Log.w(TAG, "Error adding document", e)
                }


    }

    fun post_gif(media: Media, bundle: Bundle, userLocation: Location?, generateColor: String, place: Place) {

        var user = bundle.getParcelable("User") as User?
        var timestamp = now()
        var color = generateColor
        var imageBoolean = false
        var gifBoolean = true
        var stringBoolean = false
        var userID = user?.uid
        var userName = user?.profileName
        var geoPoint = GeoPoint(userLocation?.latitude!!, userLocation?.longitude!!)
        var messageID = bundle.getString("MessageID").toString()

        val emptyStringArray = arrayOf<String>()
        // Create a new course object with information
        val course: HashMap<String, Any> = HashMap()
        course["Timestamp"] = timestamp
        course["Color"] = color
        course["ImageBoolean"] = imageBoolean
        course["GifBoolean"] = gifBoolean
        course["StringBoolean"] = stringBoolean
        course["User"] = userID!!
        course["UserName"] = userName!!
        course["Location"] = geoPoint
        course["GifUrl"] = media.url!!
        course["LikeList"] = emptyStringArray
        course["LikeNumber"] = 0

        val collectionRef = db.collection("Chats").document(place.type).collection(place.ipedsid).document(messageID).collection("Comments")
        collectionRef.add(course).addOnSuccessListener { documentReference ->
            Log.d(TAG, "DocumentSnapshot written with ID: ${documentReference.id}")
        }
                .addOnFailureListener { e ->
                    Log.w(TAG, "Error adding document", e)
                }

    }

    fun post_image(bitmap: Bitmap, bundle: Bundle, userLocation: Location?, generateColor: String, place: Place) {

        val smallBitmap = getResizedBitmap(bitmap,1000);
        val baos = ByteArrayOutputStream()
        smallBitmap?.compress(Bitmap.CompressFormat.JPEG, 100, baos)
        val data: ByteArray = baos.toByteArray()


        val user = bundle.getParcelable("User") as User?
        val timestamp = now()
        val color = generateColor
        val imageBoolean = true
        val gifBoolean = false
        val stringBoolean = false
        val userID = user?.uid
        val userName = user?.profileName
        val geoPoint = GeoPoint(userLocation?.latitude!!, userLocation?.longitude!!)
        val localCity =bundle.getInt("LocalCity")
        val messageID = bundle.getString("MessageID").toString()

        val emptyStringArray = arrayOf<String>()
        // Create a new course object with information
        val course: HashMap<String, Any> = HashMap()
        course["Timestamp"] = timestamp
        course["Color"] = color
        course["ImageBoolean"] = imageBoolean
        course["GifBoolean"] = gifBoolean
        course["StringBoolean"] = stringBoolean
        course["User"] = userID!!
        course["UserName"] = userName!!
        course["Location"] = geoPoint
        course["ImageHeight"] = smallBitmap!!.height
        course["ImageWidth"] = smallBitmap.width
        course["LikeList"] = emptyStringArray
        course["LikeNumber"] = 0

        val collectionRef = db.collection("Chats").document(place.type).collection(place.ipedsid).document(messageID).collection("Comments")
        collectionRef.add(course).addOnSuccessListener { documentReference ->
            Log.d(TAG, "DocumentSnapshot written with ID: ${documentReference.id}")
            val ref = storage.getReference("Comments/${place.type}/${localCity}/messageID/${documentReference.id}")
            ref.putBytes(data)
        }
                .addOnFailureListener { e ->
                    Log.w(TAG, "Error adding document", e)
                }



    }

    fun getResizedBitmap(image: Bitmap, maxSize: Int): Bitmap? {
        var width = image.width
        var height = image.height
        val bitmapRatio = width.toFloat() / height.toFloat()
        if (bitmapRatio > 1) {
            width = maxSize
            height = (width / bitmapRatio).toInt()
        } else {
            height = maxSize
            width = (height * bitmapRatio).toInt()
        }
        return Bitmap.createScaledBitmap(image, width, height, true)
    }


    companion object {
        // or return instance.getApplicationContext();
        val context: Context? = null
    }

    init {
        storage = FirebaseStorage.getInstance()
    }
}