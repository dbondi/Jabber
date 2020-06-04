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
import custom_class.User
import java.io.ByteArrayOutputStream
import java.util.*

class NewChatModel(private val auth: FirebaseAuth, private val db: FirebaseFirestore) {
    private val storage: FirebaseStorage
    private val TAG = "ChatModel"
    fun loadLocalChats(cityLocation: String?, cityCoordinates: ArrayList<PointMap?>?, cityLocationKey: String?, localLocation: String?, localCoordinates: ArrayList<PointMap?>?, localLocationKey: String?) {
        db.collection("Chats").document("Local")
    }

    init {
        storage = FirebaseStorage.getInstance()
    }

    /*
    fun post_image(bitmap: Bitmap, bundle: Bundle, userLocation: Location?, generateColor: String, place: Place) {

        val smallBitmap = getResizedBitmap(bitmap,1000);
        val baos = ByteArrayOutputStream()
        smallBitmap?.compress(Bitmap.CompressFormat.JPEG, 100, baos)
        val data: ByteArray = baos.toByteArray()


        val user = bundle.getParcelable("User") as User?
        val timestamp = Timestamp.now()
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

     */
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

    fun post_image(text: String?, imageBitmap: Bitmap?, userLocation: Location?, generateColor: String, place: Place, user: User?) {
        val smallBitmap = getResizedBitmap(imageBitmap!!,1000);
        val baos = ByteArrayOutputStream()
        smallBitmap?.compress(Bitmap.CompressFormat.JPEG, 100, baos)
        val data: ByteArray = baos.toByteArray()

        val timestamp = Timestamp.now()
        val color = generateColor
        val imageBoolean = true
        val gifBoolean = false
        val stringBoolean = false
        val pollBoolean = false
        val userID = user?.uid
        val userName = user?.profileName
        val geoPoint = GeoPoint(userLocation?.latitude!!, userLocation?.longitude!!)

        val emptyStringArray = arrayOf<String>()
        // Create a new course object with information
        val course: HashMap<String, Any> = HashMap()
        course["Content"] = text!!
        course["Timestamp"] = timestamp
        course["Color"] = color
        course["ImageBoolean"] = imageBoolean
        course["GifBoolean"] = gifBoolean
        course["StringBoolean"] = stringBoolean
        course["pollBoolean"] = pollBoolean
        course["GifURL"] = ""
        course["User"] = userID!!
        course["UserName"] = userName!!
        course["Location"] = geoPoint
        course["ImageHeight"] = smallBitmap!!.height
        course["ImageWidth"] = smallBitmap.width
        course["PollValues"] = emptyStringArray
        course["LikeList"] = emptyStringArray
        course["LikeNumber"] = 0

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

    fun post_gif(text: String, gif_url: String?, userLocation: Location?, generateColor: String, place: Place, user: User?) {

        val timestamp = Timestamp.now()
        val color = generateColor
        val imageBoolean = false
        val gifBoolean = true
        val stringBoolean = false
        val pollBoolean = false
        val userID = user?.uid
        val userName = user?.profileName
        val geoPoint = GeoPoint(userLocation?.latitude!!, userLocation?.longitude!!)

        val emptyStringArray = arrayOf<String>()
        // Create a new course object with information
        val course: HashMap<String, Any> = HashMap()
        course["Content"] = text
        course["Timestamp"] = timestamp
        course["Color"] = color
        course["ImageBoolean"] = imageBoolean
        course["GifBoolean"] = gifBoolean
        course["StringBoolean"] = stringBoolean
        course["pollBoolean"] = pollBoolean
        course["GifURL"] = gif_url!!
        course["User"] = userID!!
        course["UserName"] = userName!!
        course["Location"] = geoPoint
        course["ImageHeight"] = 0
        course["ImageWidth"] = 0
        course["PollValues"] = emptyStringArray
        course["LikeList"] = emptyStringArray
        course["LikeNumber"] = 0

        val collectionRef = db.collection("Chats").document(place.type).collection(place.ipedsid)
        collectionRef.add(course).addOnSuccessListener { documentReference ->
            Log.d(TAG, "DocumentSnapshot written with ID: ${documentReference.id}")
        }
    }

    fun post_poll(text: String, pollValues: ArrayList<String>, userLocation: Location?, generateColor: String, place: Place, user: User?) {
        val timestamp = Timestamp.now()
        val color = generateColor
        val imageBoolean = false
        val gifBoolean = false
        val stringBoolean = false
        val pollBoolean = true
        val userID = user?.uid
        val userName = user?.profileName
        val geoPoint = GeoPoint(userLocation?.latitude!!, userLocation?.longitude!!)

        val emptyStringArray = arrayOf<String>()
        // Create a new course object with information
        val course: HashMap<String, Any> = HashMap()
        course["Content"] = text
        course["Timestamp"] = timestamp
        course["Color"] = color
        course["ImageBoolean"] = imageBoolean
        course["GifBoolean"] = gifBoolean
        course["StringBoolean"] = stringBoolean
        course["pollBoolean"] = pollBoolean
        course["GifURL"] = ""
        course["User"] = userID!!
        course["UserName"] = userName!!
        course["Location"] = geoPoint
        course["ImageHeight"] = 0
        course["ImageWidth"] = 0
        course["PollValues"] = pollValues
        course["LikeList"] = emptyStringArray
        course["LikeNumber"] = 0

        val collectionRef = db.collection("Chats").document(place.type).collection(place.ipedsid)
        collectionRef.add(course).addOnSuccessListener { documentReference ->
            Log.d(TAG, "DocumentSnapshot written with ID: ${documentReference.id}")
        }
    }

    fun post_text(text: String, userLocation: Location?, generateColor: String, place: Place, user: User?) {


        val timestamp = Timestamp.now()
        val color = generateColor
        val imageBoolean = false
        val gifBoolean = false
        val stringBoolean = true
        val pollBoolean = false
        val userID = user?.uid
        val userName = user?.profileName
        val geoPoint = GeoPoint(userLocation?.latitude!!, userLocation?.longitude!!)

        val emptyStringArray = arrayOf<String>()
        // Create a new course object with information
        val course: HashMap<String, Any> = HashMap()
        course["Content"] = text
        course["Timestamp"] = timestamp
        course["Color"] = color
        course["ImageBoolean"] = imageBoolean
        course["GifBoolean"] = gifBoolean
        course["StringBoolean"] = stringBoolean
        course["pollBoolean"] = pollBoolean
        course["GifURL"] = ""
        course["User"] = userID!!
        course["UserName"] = userName!!
        course["Location"] = geoPoint
        course["ImageHeight"] = 0
        course["ImageWidth"] = 0
        course["PollValues"] = emptyStringArray
        course["LikeList"] = emptyStringArray
        course["LikeNumber"] = 0

        val collectionRef = db.collection("Chats").document(place.type).collection(place.ipedsid)
        collectionRef.add(course).addOnSuccessListener { documentReference ->
            Log.d(TAG, "DocumentSnapshot written with ID: ${documentReference.id}")
        }
    }
}