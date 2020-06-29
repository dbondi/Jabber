package com.example.jab

import adapaters.CommentAdapter
import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.graphics.drawable.Animatable
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.text.Editable
import android.text.TextWatcher
import android.util.DisplayMetrics
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.ScrollView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
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
import controllers.CommentController
import custom_class.Comment
import custom_class.HelperFunctions
import custom_class.Place
import custom_class.UserProfile
import models.CommentModel

class CommentActivity : AppCompatActivity(), LocationListener, GiphyDialogFragment.GifSelectionListener {

    val TAG = CommentActivity::class.java.simpleName
    private val INVALID_KEY = "NOT_A_VALID_KEY"

    private var auth: FirebaseAuth? = null
    private var db: FirebaseFirestore? = null
    private val storage: FirebaseStorage? = null

    private lateinit var gifsDialog: GiphyDialogFragment

    private var controller: CommentController? = null
    private var model: CommentModel? = null

    private var commentsVar: ArrayList<Comment?>? = null

    private lateinit var commentView_1: RecyclerView
    private lateinit var commentView_2: RecyclerView
    private lateinit var commentView_3: RecyclerView

    private lateinit var scrollView: ScrollView

    private var commentAdapter_1: CommentAdapter? = null
    private var commentAdapter_2: CommentAdapter? = null
    private var commentAdapter_3: CommentAdapter? = null

    private var local_city = 0

    private lateinit var add_gif_button: LinearLayout
    private lateinit var camera_button: LinearLayout

    private lateinit var comment_something: EditText
    private lateinit var gif_box: LinearLayout
    private lateinit var camera_box: LinearLayout
    private lateinit var photo_box: LinearLayout
    private lateinit var post_button: LinearLayout
    private lateinit var localLocationText: TextView
    private lateinit var background_color: CardView

    protected var userLocation: Location? = null
    protected var locationManager: LocationManager? = null
    protected var locationListener: LocationListener? = null

    private val test = true
    private lateinit var randomColor: ArrayList<String?>

    private lateinit var messageID: String
    private lateinit var place: Place
    private lateinit var user: UserProfile

    private lateinit var syncRecyclers: List<RecyclerView>

    private lateinit var bundle: Bundle

    val settings = GPHSettings(GridType.waterfall, GPHTheme.Dark, arrayOf(GPHContentType.recents, GPHContentType.gif, GPHContentType.sticker, GPHContentType.text, GPHContentType.emoji))

    private val REQUEST_CODE = 100
    val REQUEST_IMAGE_CAPTURE = 1
    var imageUri: Uri? = null
    private val YOUR_API_KEY = "elNv51lWUpNZUzzV0F765to450cpeuUV"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        setContentView(R.layout.activity_comments)
        auth = FirebaseAuth.getInstance()
        context = this
        commentView_1 = findViewById(R.id.comments_1)
        //commentView_2 = findViewById(R.id.comments_2)
        //commentView_3 = findViewById(R.id.comments_3)
        add_gif_button = findViewById(R.id.add_gif_button)
        localLocationText = findViewById(R.id.localLocationText)
        //background_color = findViewById(R.id.background_color);

        comment_something = findViewById(R.id.comment_text)
        gif_box = findViewById(R.id.gif_box)

        camera_box = findViewById(R.id.camera_box)
        photo_box = findViewById(R.id.photo_box)
        post_button = findViewById(R.id.post_button)

        post_button.visibility = View.GONE

        //set gif settings
        settings.showConfirmationScreen = true
        settings.rating = RatingType.r
        settings.renditionType = RenditionType.fixedWidth
        settings.confirmationRenditionType = RenditionType.original
        settings.showCheckeredBackground = false
        settings.useBlurredBackground = true
        settings.stickerColumnCount = 4

        //background_color.setBackgroundColor(Color.parseColor(randomColor.get(0)));

        val intent = intent
        bundle = intent.extras!!
        db = FirebaseFirestore.getInstance()




        messageID = bundle.getString("MessageID").toString()
        user = bundle.getParcelable("User")!!
        place = bundle.getParcelable("Place")!!
        localLocationText.text = place.name;

        //Load controller
        controller = CommentController(auth, this)

        //Load model
        model = CommentModel(auth!!, db!!)

        find_location()

        model!!.loadComments(object : CommentActivity.FirestoreCallBackKot {
            override fun onCallback(comments: ArrayList<Comment?>?) {
                val displayMetrics = DisplayMetrics()
                windowManager.defaultDisplay.getMetrics(displayMetrics)
                val height = displayMetrics.heightPixels
                val width = displayMetrics.widthPixels
                commentAdapter_1 = CommentAdapter(context,bundle,controller,width)
                //commentAdapter_2 = CommentAdapter(user, context)
                //commentAdapter_3 = CommentAdapter(user, context)
                commentsVar = comments
                //val comments_1 = getCommentsPart(comments, 1)
                //val comments_2 = getCommentsPart(comments, 2)
                //val comments_3 = getCommentsPart(comments, 3)
                commentAdapter_1!!.update(comments)
                //commentAdapter_2!!.update(comments_2)
                //commentAdapter_3!!.update(comments_3)
                var staggeredGridLayoutManager = StaggeredGridLayoutManager(2,LinearLayoutManager.VERTICAL)
                commentView_1.adapter = commentAdapter_1
                //commentView_2.adapter = commentAdapter_2
               // commentView_3.adapter = commentAdapter_3
                commentView_1.layoutManager = staggeredGridLayoutManager
                commentView_1.adapter = commentAdapter_1

            }

        }, messageID,user,place)


        Giphy.configure(this, YOUR_API_KEY)

        val mediaView = GPHMediaView(this);
        mediaView.setMediaWithId(R.id.giphy.toString())

        add_gif_button.setOnClickListener(View.OnClickListener {
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

        comment_something.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
                if(p0.toString().length == 0){
                    gif_box.visibility = View.VISIBLE
                    camera_box.visibility = View.VISIBLE
                    photo_box.visibility = View.VISIBLE
                    post_button.visibility = View.GONE
                }
                if(p0.toString().length != 0){
                    gif_box.visibility = View.GONE
                    camera_box.visibility = View.GONE
                    photo_box.visibility = View.GONE
                    post_button.visibility = View.VISIBLE
                }
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
        })

        post_button.setOnClickListener(View.OnClickListener {
            var comment_post = comment_something.text.toString()
            randomColor = HelperFunctions.random_color(11)

            var comment = model!!.post_string(object : CommentActivity.FirestoreCallBack {
                override fun onCallback(comment: Comment){
                    commentsVar?.add(comment)
                    comment_something.setText("")
                    hideKeyboard()
                    commentAdapter_1?.updateNew(commentsVar)

                }
            },comment_post,bundle,userLocation,randomColor,place)



        })

        mediaView.gifCallback = object: GPHGridCallback, GifView.GifCallback {
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

    interface FirestoreCallBack {
        fun onCallback(comments: Comment)
    }

    interface FirestoreCallBackKot {
        fun onCallback(comments: ArrayList<Comment?>?)
    }

    private fun getGifSelectionListener() = object : GiphyDialogFragment.GifSelectionListener {
        override fun onGifSelected(media: Media, searchTerm: String?) {
            Log.d(TAG, "onGifSelected")
            //System.out.println("Bitly Gif URL")
            //System.out.println(media.bitlyGifUrl)
            //messageItems.add(GifItem(media, User.Me))
            randomColor = HelperFunctions.random_color(11)
            var comment = model!!.post_gif(object : CommentActivity.FirestoreCallBack {
                override fun onCallback(comment: Comment){
                    commentsVar?.add(comment)
                    commentAdapter_1?.updateNew(commentsVar)
                }
            },media,bundle,userLocation,randomColor,place)
        }
        override fun onDismissed() {
            Log.d(TAG, "onDismissed")
        }

        override fun didSearchTerm(term: String) {
            Log.d(TAG, "didSearchTerm $term")
        }
    }

    private fun getCommentsPart(comments: ArrayList<Comment?>?, i: Int): ArrayList<Comment> {
        val columnComments = ArrayList<Comment>()
        for (comment in comments!!) {
            if (comment != null) {
                if (comment.columnNumber == i) {
                    columnComments.add(comment)
                }
            }
        }
        return columnComments
    }

    companion object {
        //return instance.getApplicationContext();
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


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == REQUEST_CODE){
            val selectedPhotoUri = data?.data
            try {
                selectedPhotoUri?.let {
                    if(Build.VERSION.SDK_INT < 28) {
                        randomColor = HelperFunctions.random_color(11)
                        val bitmap = MediaStore.Images.Media.getBitmap(
                                this.contentResolver,
                                selectedPhotoUri
                        )
                        var comment = model!!.post_image(object : CommentActivity.FirestoreCallBack {
                            override fun onCallback(comment: Comment){
                                commentsVar?.add(comment)
                                commentAdapter_1?.updateNew(commentsVar)
                            }
                        },bitmap,bundle,userLocation,randomColor,place)

                    } else {
                        randomColor = HelperFunctions.random_color(11)
                        val source = ImageDecoder.createSource(this.contentResolver, selectedPhotoUri)
                        val bitmap = ImageDecoder.decodeBitmap(source)
                        var comment = model!!.post_image(object : CommentActivity.FirestoreCallBack {
                            override fun onCallback(comment: Comment){
                                commentsVar?.add(comment)
                                commentAdapter_1?.updateNew(commentsVar)
                            }
                        },bitmap,bundle,userLocation,randomColor,place)
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            randomColor = HelperFunctions.random_color(11)
            val bitmap = data?.extras?.get("data") as Bitmap
            var comment = model!!.post_image(object : CommentActivity.FirestoreCallBack {
                override fun onCallback(comment: Comment){
                    commentsVar?.add(comment)
                    commentAdapter_1?.updateNew(commentsVar)
                }
            },bitmap,bundle,userLocation,randomColor,place)
        }
    }

    override fun onLocationChanged(location: Location) {
        if (test) {
            userLocation = location
            userLocation!!.latitude = -89.408054
            userLocation!!.longitude = 43.077293
        } else {
            userLocation = location
        }
    }

    override fun onStatusChanged(provider: String, status: Int, extras: Bundle) {}
    override fun onProviderEnabled(provider: String) {}
    override fun onProviderDisabled(provider: String) {}

    private fun find_location() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), 1)
            return
        } else {
            locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
            locationManager!!.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 1000f, this)
        }
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


}