package models

import android.graphics.Bitmap
import android.location.Location
import android.util.Log
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.GeoPoint
import com.google.firebase.storage.FirebaseStorage
import custom_class.Place
import custom_class.PointMap
import custom_class.UserProfile
import java.io.ByteArrayOutputStream
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

class NewChatModel(private val auth: FirebaseAuth, private val db: FirebaseFirestore) {
    private val storage: FirebaseStorage
    private val TAG = "ChatModel"
    fun loadLocalChats(cityLocation: String?, cityCoordinates: ArrayList<PointMap?>?, cityLocationKey: String?, localLocation: String?, localCoordinates: ArrayList<PointMap?>?, localLocationKey: String?) {
        db.collection("Chats").document("Local")
    }

    init {
        storage = FirebaseStorage.getInstance()
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

    fun post_image(text: String?, imageBitmap: Bitmap?, userLocation: Location?, generateColor: ArrayList<String?>, place: Place, user: UserProfile?) {
        val smallBitmap = getResizedBitmap(imageBitmap!!,1000);
        val baos = ByteArrayOutputStream()
        smallBitmap?.compress(Bitmap.CompressFormat.JPEG, 100, baos)
        val data: ByteArray = baos.toByteArray()

        val timestamp = Timestamp.now()
        val imageBoolean = true
        val gifBoolean = false
        val stringBoolean = false
        val pollBoolean = false
        val userID = user?.uid
        val userName = user?.profileName
        val geoPoint = GeoPoint(userLocation?.latitude!!, userLocation?.longitude!!)

        val emptyStringArray: ArrayList<String> = ArrayList<String>()
        // Create a new course object with information
        val course: HashMap<String, Any> = HashMap()
        val pollVoteList: HashMap<String, Integer> = HashMap()
        val pollValues: ArrayList<String> = ArrayList<String>()
        val pollVotes: ArrayList<Integer> = ArrayList<Integer>()

        course["PollVoteList"] = pollVoteList
        course["PollValues"] = pollValues
        course["PollVotes"] = pollVotes
        course["Content"] = text!!
        course["Timestamp"] = timestamp
        course["Color"] = generateColor
        course["ImageBoolean"] = imageBoolean
        course["GifBoolean"] = gifBoolean
        course["StringBoolean"] = stringBoolean
        course["PollBoolean"] = pollBoolean
        course["GifURL"] = ""
        course["User"] = userID!!
        //TODO
        course["UserName"] = userName!!
        course["Location"] = geoPoint
        course["ImageHeight"] = smallBitmap!!.height
        course["ImageWidth"] = smallBitmap.width
        course["LikeList"] = emptyStringArray
        course["LikeNumber"] = 0
        course["CommentNumber"] = 0

        val collectionRef = db.collection("Chats").document(place.type).collection(place.ipedsid)
        collectionRef.add(course).addOnSuccessListener { documentReference ->
            Log.d(TAG, "DocumentSnapshot written with ID: ${documentReference.id}")
            val ref = storage.getReference("Chats/${place.type}/${place.ipedsid}/${documentReference.id}")
            ref.putBytes(data)
        }
                .addOnFailureListener { e ->
                    Log.w(TAG, "Error adding document", e)
        }
    }

    fun post_gif(text: String, gif_url: String?, userLocation: Location?, generateColor: ArrayList<String?>, place: Place, user: UserProfile?) {

        val timestamp = Timestamp.now()
        val imageBoolean = false
        val gifBoolean = true
        val stringBoolean = false
        val pollBoolean = false
        val userID = user?.uid
        val userName = user?.profileName
        val geoPoint = GeoPoint(userLocation?.latitude!!, userLocation?.longitude!!)

        val emptyStringArray: ArrayList<String> = ArrayList<String>()
        // Create a new course object with information
        val course: HashMap<String, Any> = HashMap()
        val pollVoteList: HashMap<String, Integer> = HashMap()
        val pollValues: ArrayList<String> = ArrayList<String>()
        val pollVotes: ArrayList<Integer> = ArrayList<Integer>()

        course["PollVoteList"] = pollVoteList
        course["PollValues"] = pollValues
        course["PollVotes"] = pollVotes
        course["Content"] = text
        course["Timestamp"] = timestamp
        course["Color"] = generateColor
        course["ImageBoolean"] = imageBoolean
        course["GifBoolean"] = gifBoolean
        course["StringBoolean"] = stringBoolean
        course["PollBoolean"] = pollBoolean
        course["GifURL"] = gif_url!!
        course["User"] = userID!!
        course["UserName"] = userName!!
        course["Location"] = geoPoint
        course["ImageHeight"] = 0
        course["ImageWidth"] = 0
        course["LikeList"] = emptyStringArray
        course["LikeNumber"] = 0
        course["CommentNumber"] = 0

        val collectionRef = db.collection("Chats").document(place.type).collection(place.ipedsid)
        collectionRef.add(course).addOnSuccessListener { documentReference ->
            Log.d(TAG, "DocumentSnapshot written with ID: ${documentReference.id}")
        }
    }

    fun post_poll(text: String, pollValues: ArrayList<String>, userLocation: Location?, generateColor: ArrayList<String?>, place: Place, user: UserProfile?) {
        val timestamp = Timestamp.now()
        val imageBoolean = false
        val gifBoolean = false
        val stringBoolean = false
        val pollBoolean = true
        val userID = user?.uid
        val userName = user?.profileName
        val geoPoint = GeoPoint(userLocation?.latitude!!, userLocation?.longitude!!)

        val emptyStringArray: ArrayList<String> = ArrayList<String>()
        // Create a new course object with information
        val course: HashMap<String, Any> = HashMap()
        val pollVoteList: HashMap<String, Integer> = HashMap()
        val emptyIntegerArray = arrayListOf<Int>()
            emptyIntegerArray.add(0)
            emptyIntegerArray.add(0)
            emptyIntegerArray.add(0)
            emptyIntegerArray.add(0)

        course["PollVoteList"] = pollVoteList
        course["PollValues"] = pollValues
        course["PollVotes"] = emptyIntegerArray
        course["Content"] = text
        course["Timestamp"] = timestamp
        course["Color"] = generateColor
        course["ImageBoolean"] = imageBoolean
        course["GifBoolean"] = gifBoolean
        course["StringBoolean"] = stringBoolean
        course["PollBoolean"] = pollBoolean
        course["GifURL"] = ""
        course["User"] = userID!!
        course["UserName"] = userName!!
        course["Location"] = geoPoint
        course["ImageHeight"] = 0
        course["ImageWidth"] = 0
        course["LikeList"] = emptyStringArray
        course["LikeNumber"] = 0
        course["CommentNumber"] = 0

        val collectionRef = db.collection("Chats").document(place.type).collection(place.ipedsid)
        collectionRef.add(course).addOnSuccessListener { documentReference ->
            Log.d(TAG, "DocumentSnapshot written with ID: ${documentReference.id}")
        }
    }

    fun post_text(text: String, userLocation: Location?, generateColor: ArrayList<String?>, place: Place, user: UserProfile?) {


        val timestamp = Timestamp.now()
        val imageBoolean = false
        val gifBoolean = false
        val stringBoolean = true
        val pollBoolean = false
        val userID = user?.uid
        val userName = user?.profileName
        val geoPoint = GeoPoint(userLocation?.latitude!!, userLocation?.longitude!!)

        val emptyStringArray: ArrayList<String> = ArrayList<String>()
        // Create a new course object with information
        val course: HashMap<String, Any> = HashMap()
        val pollVoteList: HashMap<String, Integer> = HashMap()
        val pollValues: ArrayList<String> = ArrayList<String>()
        val pollVotes: ArrayList<Integer> = ArrayList<Integer>()

        course["PollVoteList"] = pollVoteList
        course["PollValues"] = pollValues
        course["PollVotes"] = pollVotes
        course["Content"] = text
        course["Timestamp"] = timestamp
        course["Color"] = generateColor
        course["ImageBoolean"] = imageBoolean
        course["GifBoolean"] = gifBoolean
        course["StringBoolean"] = stringBoolean
        course["PollBoolean"] = pollBoolean
        course["GifURL"] = ""
        course["User"] = userID!!
        course["UserName"] = userName!!
        course["Location"] = geoPoint
        course["ImageHeight"] = 0
        course["ImageWidth"] = 0
        course["PollValues"] = emptyStringArray
        course["LikeList"] = emptyStringArray
        course["LikeNumber"] = 0
        course["CommentNumber"] = 0
        val collectionRef = db.collection("Chats").document(place.type).collection(place.ipedsid)
        collectionRef.add(course).addOnSuccessListener { documentReference ->
            Log.d(TAG, "DocumentSnapshot written with ID: ${documentReference.id}")
        }
    }
}