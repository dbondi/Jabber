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
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.ScrollView
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.core.app.ActivityCompat
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
import de.hdodenhof.circleimageview.CircleImageView
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

    private lateinit var commentView_1: RecyclerView
    private lateinit var commentView_2: RecyclerView
    private lateinit var commentView_3: RecyclerView

    private lateinit var scrollView: ScrollView

    private var commentAdapter_1: CommentAdapter? = null
    private var commentAdapter_2: CommentAdapter? = null
    private var commentAdapter_3: CommentAdapter? = null

    private var local_city = 0

    private lateinit var add_gif_button: CircleImageView
    private lateinit var camera_button: CircleImageView

    private lateinit var comment_something: EditText
    private lateinit var gif_box: LinearLayout
    private lateinit var camera_box: LinearLayout
    private lateinit var photo_box: LinearLayout
    private lateinit var post_button: LinearLayout
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

        //textRight.setLayoutParams(textRightLayoutParams);
        local_city = getIntent().getIntExtra("LocalCity", 0)

        //TODO might use later
        //messageBtn.setBackground(getResources().getDrawable(R.drawable.round_bound_pink));
        //val display = windowManager.defaultDisplay
        //val size = Point()
        //display.getSize(size)
        //val widthScreen = size.x
        //val heightScreen = size.y

        //Load controller
        controller = CommentController(auth, this)

        //Load model
        model = CommentModel(auth!!, db!!)

        find_location()

        model!!.loadComments(object : CommentActivity.FirestoreCallBackKot {
            override fun onCallback(comments: ArrayList<Comment?>?) {
                commentAdapter_1 = CommentAdapter(user, context)
                //commentAdapter_2 = CommentAdapter(user, context)
                //commentAdapter_3 = CommentAdapter(user, context)
                //val comments_1 = getCommentsPart(comments, 1)
                //val comments_2 = getCommentsPart(comments, 2)
                //val comments_3 = getCommentsPart(comments, 3)
                commentAdapter_1!!.update(comments)
                //commentAdapter_2!!.update(comments_2)
                //commentAdapter_3!!.update(comments_3)
                var staggeredGridLayoutManager = StaggeredGridLayoutManager(3,LinearLayoutManager.VERTICAL)
                commentView_1.adapter = commentAdapter_1
                //commentView_2.adapter = commentAdapter_2
               // commentView_3.adapter = commentAdapter_3
                commentView_1.layoutManager = staggeredGridLayoutManager
                commentView_1.adapter = commentAdapter_1
                //commentView_2.layoutManager = LinearLayoutManager(context)
                //commentView_3.layoutManager = LinearLayoutManager(context)
                //commentView_1.isNestedScrollingEnabled = false;
                //commentView_2.isNestedScrollingEnabled = false;
                //commentView_3.isNestedScrollingEnabled = false;

                /*
                val params1: ViewGroup.LayoutParams = commentView_1.getLayoutParams()
                params1.height = 8000
                commentView_1.setLayoutParams(params1)
                val params2: ViewGroup.LayoutParams = commentView_2.getLayoutParams()
                params2.height = 8000
                commentView_1.setLayoutParams(params2)
                val params3: ViewGroup.LayoutParams = commentView_3.getLayoutParams()
                params3.height = 8000
                commentView_1.setLayoutParams(params3)
                */

                //rec(commentView_1)
                //rec(commentView_2)
                //rec(commentView_3)


                /*
                val maxheight = maxOf(commentView_1.layoutParams.height,maxOf(commentView_2.layoutParams.height,commentView_3.layoutParams.height))

                println("MaxHeight")
                println(maxheight)

                comments_1.add(Comment(maxheight-commentView_1.layoutParams.height))
                comments_2.add(Comment(maxheight-commentView_2.layoutParams.height))
                comments_3.add(Comment(maxheight-commentView_3.layoutParams.height))

                commentAdapter_1!!.update(comments_1)
                commentAdapter_2!!.update(comments_2)
                commentAdapter_3!!.update(comments_3)
                commentAdapter_1!!.notifyItemInserted(comments_1.size - 1);
                commentAdapter_2!!.notifyItemInserted(comments_2.size - 1);
                commentAdapter_3!!.notifyItemInserted(comments_3.size - 1);
                commentAdapter_1!!.notifyDataSetChanged();
                commentAdapter_2!!.notifyDataSetChanged();
                commentAdapter_3!!.notifyDataSetChanged();

                */


                //commentView_1.setHasFixedSize(false)
                //commentView_2.setHasFixedSize(false)
                //commentView_3.setHasFixedSize(false)


            }

        }, messageID,user,place)

        /*
        val scrollListeners = arrayOfNulls<RecyclerView.OnScrollListener>(3)
        scrollListeners[0] = object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                commentView_2.removeOnScrollListener(scrollListeners[1]!!)
                commentView_2.scrollBy(dx, dy)
                commentView_2.addOnScrollListener(scrollListeners[1]!!)
                commentView_3.removeOnScrollListener(scrollListeners[2]!!)
                commentView_3.scrollBy(dx, dy)
                commentView_3.addOnScrollListener(scrollListeners[2]!!)
            }
        }
        scrollListeners[1] = object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                commentView_1.removeOnScrollListener(scrollListeners[0]!!)
                commentView_1.scrollBy(dx, dy)
                commentView_1.addOnScrollListener(scrollListeners[0]!!)
                commentView_3.removeOnScrollListener(scrollListeners[2]!!)
                commentView_3.scrollBy(dx, dy)
                commentView_3.addOnScrollListener(scrollListeners[2]!!)
            }
        }
        scrollListeners[2] = object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                commentView_1.removeOnScrollListener(scrollListeners[0]!!)
                commentView_1.scrollBy(dx, dy)
                commentView_1.addOnScrollListener(scrollListeners[0]!!)
                commentView_2.removeOnScrollListener(scrollListeners[1]!!)
                commentView_2.scrollBy(dx, dy)
                commentView_2.addOnScrollListener(scrollListeners[1]!!)
            }
        }
        commentView_1.addOnScrollListener(scrollListeners[0]!!)
        commentView_2.addOnScrollListener(scrollListeners[1]!!)
        commentView_3.addOnScrollListener(scrollListeners[2]!!)

         */

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
            model!!.post_string(comment_post,bundle,userLocation,randomColor,place)


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
            model!!.post_gif(media,bundle,userLocation,randomColor,place)
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
                        model!!.post_image(bitmap,bundle,userLocation,randomColor,place)
                    } else {
                        randomColor = HelperFunctions.random_color(11)
                        val source = ImageDecoder.createSource(this.contentResolver, selectedPhotoUri)
                        val bitmap = ImageDecoder.decodeBitmap(source)
                        model!!.post_image(bitmap,bundle,userLocation,randomColor,place)
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            randomColor = HelperFunctions.random_color(11)
            val imageBitmap = data?.extras?.get("data") as Bitmap
            model!!.post_image(imageBitmap,bundle,userLocation,randomColor,place)
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


}