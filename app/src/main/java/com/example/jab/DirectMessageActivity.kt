package com.example.jab

import adapaters.DirectMessageAdapter
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.graphics.drawable.Animatable
import android.location.Location
import android.location.LocationListener
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.facebook.imagepipeline.image.ImageInfo
import com.giphy.sdk.core.models.Media
import com.giphy.sdk.core.models.enums.RatingType
import com.giphy.sdk.core.models.enums.RenditionType
import com.giphy.sdk.ui.GPHContentType
import com.giphy.sdk.ui.GPHSettings
import com.giphy.sdk.ui.Giphy
import com.giphy.sdk.ui.themes.GPHTheme
import com.giphy.sdk.ui.themes.GridType
import com.giphy.sdk.ui.views.GPHMediaView
import com.giphy.sdk.ui.views.GifView
import com.giphy.sdk.ui.views.GiphyDialogFragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import controllers.DirectMessageController
import custom_class.DirectMessage
import custom_class.Place
import custom_class.UserDirectMessages
import custom_class.UserProfile
import models.DirectMessageModel
import java.util.*

class DirectMessageActivity : AppCompatActivity(), LocationListener, GiphyDialogFragment.GifSelectionListener {

    val TAG = DirectMessageActivity::class.java.simpleName
    private val INVALID_KEY = "NOT_A_VALID_KEY"

    private var controller: DirectMessageController? = null
    private var model: DirectMessageModel? = null

    private var auth: FirebaseAuth? = null
    private var db: FirebaseFirestore? = null
    private val storage: FirebaseStorage? = null

    private lateinit var send_button: TextView
    private lateinit var comment_text: EditText

    private lateinit var gif_box: LinearLayout
    private lateinit var camera_box: LinearLayout
    private lateinit var photo_box: LinearLayout

    private var directMessages: ArrayList<DirectMessage?>? = null
    private var directMessageView: RecyclerView? = null
    private var directMessageAdapter: DirectMessageAdapter? = null

    val settings = GPHSettings(GridType.waterfall, GPHTheme.Dark, arrayOf(GPHContentType.recents, GPHContentType.gif, GPHContentType.sticker, GPHContentType.text, GPHContentType.emoji))

    private lateinit var bundle: Bundle

    private var createdChat: Boolean = false;

    private lateinit var myUser: UserProfile
    private lateinit var friendUser: UserProfile

    private var localUniversityPlaces = ArrayList<Place>()
    private var localCityPlaces = ArrayList<Place>()
    private lateinit var place:Place

    private val REQUEST_CODE = 100
    val REQUEST_IMAGE_CAPTURE = 1
    var imageUri: Uri? = null
    private val YOUR_API_KEY = "elNv51lWUpNZUzzV0F765to450cpeuUV"
    private lateinit var directMessagePath: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_direct_messages)
        val intent = intent
        bundle = intent.extras!!
        context = this

        localUniversityPlaces = bundle?.getParcelableArrayList("LocalUniversityPlaces")!!
        localCityPlaces = bundle.getParcelableArrayList("LocalCityPlaces")!!
        myUser = bundle.getParcelable("User")!!
        friendUser = bundle.getParcelable("ChatUser")!!


        auth = FirebaseAuth.getInstance()
        controller = DirectMessageController(auth, this)
        db = FirebaseFirestore.getInstance()
        model = DirectMessageModel(auth,db)

        directMessageView = findViewById(R.id.direct_messages)
        send_button = findViewById(R.id.send_button)
        comment_text = findViewById(R.id.comment_text)

        send_button.visibility = View.GONE

        camera_box = findViewById(R.id.camera_button)
        photo_box = findViewById(R.id.photo_library_button)
        gif_box = findViewById(R.id.add_gif_button)

        settings.showConfirmationScreen = true
        settings.rating = RatingType.r
        settings.renditionType = RenditionType.fixedWidth
        settings.confirmationRenditionType = RenditionType.original
        settings.showCheckeredBackground = false
        settings.useBlurredBackground = true
        settings.stickerColumnCount = 4

        model!!.loadCheckMessage(object : DirectMessageActivity.FirestoreCallBackFirst {
            override fun onCallback(path: UserDirectMessages) {
                if (path.sharedPath=="") {
                    createdChat = false;
                    directMessagePath = ""
                } else {
                    directMessagePath = path.sharedPath
                    model!!.loadDirectMessages(object : DirectMessageActivity.FirestoreCallBackSecond {
                        override fun onCallback(messages: ArrayList<DirectMessage?>?) {
                            directMessageAdapter = DirectMessageAdapter(context,controller,myUser,friendUser)
                            directMessages = messages
                            directMessageAdapter!!.update(directMessages)
                            var linearLayoutManager = LinearLayoutManager(context,LinearLayoutManager.VERTICAL,true)
                            directMessageView!!.layoutManager = linearLayoutManager
                            directMessageView!!.adapter = directMessageAdapter
                        }
                    }, path.sharedPath, myUser);

                }

            }

        }, myUser, friendUser)

        Giphy.configure(this, YOUR_API_KEY)

        val mediaView = GPHMediaView(this);
        mediaView.setMediaWithId(R.id.giphy.toString())

        gif_box.setOnClickListener(View.OnClickListener {
            val gifsDialog = GiphyDialogFragment.newInstance(settings)
            gifsDialog.gifSelectionListener = getGifSelectionListener()
            gifsDialog.show(supportFragmentManager, "giphy_dialog");
        })

        photo_box.setOnClickListener(View.OnClickListener {
            openGalleryForImage()
        })

        camera_box.setOnClickListener(View.OnClickListener {
            dispatchTakePictureIntent()
        })

        comment_text.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
                if(p0.toString().length == 0){
                    gif_box.visibility = View.VISIBLE
                    camera_box.visibility = View.VISIBLE
                    photo_box.visibility = View.VISIBLE
                    send_button.visibility = View.GONE
                }
                if(p0.toString().length != 0){
                    gif_box.visibility = View.GONE
                    camera_box.visibility = View.GONE
                    photo_box.visibility = View.GONE
                    send_button.visibility = View.VISIBLE
                }
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
        })


        send_button.setOnClickListener(View.OnClickListener {
            var comment_post = comment_text.text.toString()

            if(!createdChat){
                model!!.loadCheckMessage(object : DirectMessageActivity.FirestoreCallBackFirst {
                    override fun onCallback(path: UserDirectMessages) {
                        if (path.sharedPath=="") {
                            var uuid = UUID.randomUUID().toString()
                            model!!.createSharedPath(object : DirectMessageActivity.FirestoreCallBackCreate {
                                override fun onCallback() {
                                    directMessagePath = uuid
                                    var comment = model!!.send_message(object : DirectMessageActivity.FirestoreCallBackMessage {
                                        override fun onCallback(directMessage: DirectMessage){
                                            directMessages?.add(directMessage)
                                            directMessageAdapter?.update(directMessages)
                                        }
                                    },comment_post,myUser,directMessagePath)
                                }
                            },myUser,friendUser,uuid);
                        } else {
                            model!!.updateSharedPath(object : DirectMessageActivity.FirestoreCallBackCreate {
                                override fun onCallback() {
                                    directMessagePath = path.sharedPath
                                    var comment = model!!.send_message(object : DirectMessageActivity.FirestoreCallBackMessage {
                                        override fun onCallback(directMessage: DirectMessage) {
                                            directMessages?.add(directMessage)
                                            directMessageAdapter?.update(directMessages)
                                        }
                                    }, comment_post, myUser, directMessagePath)
                                }
                            },myUser,friendUser,path.sharedPath);
                        }

                    }

                }, myUser, friendUser)
            }
            else {
                var comment = model!!.send_message(object : DirectMessageActivity.FirestoreCallBackMessage {
                    override fun onCallback(directMessage: DirectMessage) {
                        directMessages?.add(directMessage)
                        directMessageAdapter?.update(directMessages)
                    }
                }, comment_post, myUser, directMessagePath)
            }

        })

        mediaView.gifCallback = object: DirectMessageActivity.GPHGridCallback, GifView.GifCallback {
            override fun contentDidUpdate(resultCount: Int) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun didSelectMedia(media: Media) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onFailure(throwable: Throwable?) {
                TODO("Not yet implemented")
            }

            override fun onImageSet(imageInfo: ImageInfo?, anim: Animatable?, loopDuration: Long, loopCount: Int) {
                TODO("Not yet implemented")
            }
        }

    }


    interface FirestoreCallBackFirst {
        fun onCallback(userDirectMessage: UserDirectMessages)
    }

    interface FirestoreCallBackSecond {
        fun onCallback(directMessages: ArrayList<DirectMessage?>?)
    }

    interface FirestoreCallBackCreate {
        fun onCallback()
    }

    interface FirestoreCallBackMessage {
        fun onCallback(directMessage: DirectMessage)
    }

    private fun dispatchTakePictureIntent() {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
            takePictureIntent.resolveActivity(packageManager)?.also {
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
            }
        }
    }
    private fun openGalleryForImage() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, REQUEST_CODE)
    }

    private fun getGifSelectionListener() = object : GiphyDialogFragment.GifSelectionListener {
        override fun onGifSelected(media: Media, searchTerm: String?) {
            Log.d(TAG, "onGifSelected")
            if(!createdChat){
                model!!.loadCheckMessage(object : DirectMessageActivity.FirestoreCallBackFirst {
                    override fun onCallback(path: UserDirectMessages) {
                        if (path.sharedPath=="") {
                            var uuid = UUID.randomUUID().toString()
                            model!!.createSharedPath(object : DirectMessageActivity.FirestoreCallBackCreate {
                                override fun onCallback() {
                                    directMessagePath = uuid
                                    var comment = model!!.send_gif(object : DirectMessageActivity.FirestoreCallBackMessage {
                                        override fun onCallback(directMessage: DirectMessage){
                                            directMessages?.add(directMessage)
                                            directMessageAdapter?.update(directMessages)
                                        }
                                    },media,myUser,directMessagePath)
                                }
                            },myUser,friendUser,uuid);
                        } else {

                            model!!.updateSharedPath(object : DirectMessageActivity.FirestoreCallBackCreate {
                                override fun onCallback() {
                                    directMessagePath = path.sharedPath
                                    var comment = model!!.send_gif(object : DirectMessageActivity.FirestoreCallBackMessage {
                                        override fun onCallback(directMessage: DirectMessage) {
                                            directMessages?.add(directMessage)
                                            directMessageAdapter?.update(directMessages)
                                        }
                                    }, media, myUser, directMessagePath)
                                }
                            },myUser,friendUser,path.sharedPath);
                        }

                    }

                }, myUser, friendUser)
            }
            else {
                var comment = model!!.send_gif(object : DirectMessageActivity.FirestoreCallBackMessage {
                    override fun onCallback(directMessage: DirectMessage) {
                        directMessages?.add(directMessage)
                        directMessageAdapter?.update(directMessages)
                    }
                }, media, myUser, directMessagePath)
            }
        }
        override fun onDismissed() {
            Log.d(TAG, "onDismissed")
        }

        override fun didSearchTerm(term: String) {
            Log.d(TAG, "didSearchTerm $term")
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == REQUEST_CODE){
            val selectedPhotoUri = data?.data
            try {
                selectedPhotoUri?.let {
                    if(Build.VERSION.SDK_INT < 28) {
                        val bitmap = MediaStore.Images.Media.getBitmap(
                                this.contentResolver,
                                selectedPhotoUri
                        )
                        var comment = model!!.send_photo(object : DirectMessageActivity.FirestoreCallBackMessage {
                            override fun onCallback(directMessage: DirectMessage){
                                directMessages?.add(directMessage)
                                directMessageAdapter?.update(directMessages)
                            }
                        }, bitmap, myUser, directMessagePath)

                    } else {
                        val source = ImageDecoder.createSource(this.contentResolver, selectedPhotoUri)
                        val bitmap = ImageDecoder.decodeBitmap(source)
                        var comment = model!!.send_photo(object : DirectMessageActivity.FirestoreCallBackMessage {
                            override fun onCallback(directMessage: DirectMessage){
                                directMessages?.add(directMessage)
                                directMessageAdapter?.update(directMessages)
                            }
                        },bitmap, myUser, directMessagePath)
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            val bitmap = data?.extras?.get("data") as Bitmap
            var comment = model!!.send_photo(object : DirectMessageActivity.FirestoreCallBackMessage {
                override fun onCallback(directMessage: DirectMessage){
                    directMessages?.add(directMessage)
                    directMessageAdapter?.update(directMessages)
                }
            },bitmap, myUser, directMessagePath)
        }
    }


    companion object {
        var context: Context? = null
            private set
    }

    override fun didSearchTerm(term: String) {
        //TODO("Not yet implemented")
    }

    override fun onDismissed() {
        //TODO("Not yet implemented")
    }

    override fun onGifSelected(media: Media, searchTerm: String?) {
        //TODO("Not yet implemented")
    }

    interface GPHGridCallback {
        fun contentDidUpdate(resultCount: Int)
        fun didSelectMedia(media: Media)
    }

    interface GPHSearchGridCallback {
        fun didTapUsername(username: String)
        fun didLongPressCell(cell: GifView)
        fun didScroll()
    }


    fun Fragment.hideKeyboard() {
        view?.let { activity?.hideKeyboard(it) }
    }

    fun Activity.hideKeyboard() {
        hideKeyboard(currentFocus ?: View(this))
    }

    fun Context.hideKeyboard(view: View) {
        val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }

    override fun onLocationChanged(location: Location) {}
    override fun onStatusChanged(provider: String, status: Int, extras: Bundle) {}
    override fun onProviderEnabled(provider: String) {}
    override fun onProviderDisabled(provider: String) {}
}