package com.example.jab

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.ImageDecoder
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputConnection
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.app.ActivityCompat
import androidx.core.os.BuildCompat
import androidx.core.view.inputmethod.EditorInfoCompat
import androidx.core.view.inputmethod.InputConnectionCompat
import androidx.core.view.inputmethod.InputConnectionCompat.OnCommitContentListener
import com.giphy.sdk.core.models.Media
import com.giphy.sdk.core.models.enums.RatingType
import com.giphy.sdk.core.models.enums.RenditionType
import com.giphy.sdk.ui.GPHContentType
import com.giphy.sdk.ui.GPHSettings
import com.giphy.sdk.ui.Giphy
import com.giphy.sdk.ui.themes.GPHTheme
import com.giphy.sdk.ui.themes.GridType
import com.giphy.sdk.ui.views.GPHMediaView
import com.giphy.sdk.ui.views.GiphyDialogFragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import controllers.NewChatController
import custom_class.HelperFunctions
import custom_class.Place
import custom_class.User
import de.hdodenhof.circleimageview.CircleImageView
import models.NewChatModel
import java.util.*

class NewChatActivity : AppCompatActivity(), LocationListener, GiphyDialogFragment.GifSelectionListener {
    private lateinit var backBtn: CircleImageView
    private var controller: NewChatController? = null
    private lateinit var auth: FirebaseAuth
    private val local_city = 0

    private var db: FirebaseFirestore? = null
    private var model: NewChatModel? = null
    private var localUniversityPlaces = ArrayList<Place>()
    private var localCityPlaces = ArrayList<Place>()
    private lateinit var background_color: LinearLayout
    private lateinit var message_text: EditText
    private lateinit var place:Place
    private lateinit var post_image: ImageView

    protected var userLocation: Location? = null
    protected var locationManager: LocationManager? = null

    private lateinit var gif_box: LinearLayout
    private lateinit var camera_box: LinearLayout
    private lateinit var photo_box: LinearLayout
    private lateinit var poll_box: LinearLayout

    private lateinit var image_container: RelativeLayout

    private lateinit var cancel_image: LinearLayout
    private lateinit var cancel_poll: LinearLayout

    private var randomColor: Array<String>? = null
    private var user: User? = null

    private lateinit var option1: EditText
    private lateinit var option2: EditText
    private lateinit var option3: EditText
    private lateinit var option4: EditText

    private var option3Box: LinearLayout? = null
    private var option4Box: LinearLayout? = null

    private lateinit var add_poll_option: LinearLayout

    private var send_yap: TextView? = null

    private var test:Boolean = true

    val settings = GPHSettings(GridType.waterfall, GPHTheme.Dark, arrayOf(GPHContentType.recents, GPHContentType.gif, GPHContentType.sticker, GPHContentType.text, GPHContentType.emoji))

    private val TAG: String? = "NewChatActivity"
    private val REQUEST_CODE = 100
    val REQUEST_IMAGE_CAPTURE = 1
    private val YOUR_API_KEY = "elNv51lWUpNZUzzV0F765to450cpeuUV"
    private var bundle: Bundle? = null
    private var poll: RelativeLayout? = null

    private var gif_url: String? = null
    private var image_bitmap: Bitmap? = null

    private var poleSize:Int = 2

    private var messageOption: String? = "Text"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bundle = savedInstanceState
        setContentView(R.layout.activity_new_message)

        auth = FirebaseAuth.getInstance()
        controller = NewChatController(auth, this)
        db = FirebaseFirestore.getInstance()
        model = NewChatModel(auth, db!!)

        context = this

        val intent = intent
        val bundle = intent.extras
        localUniversityPlaces = bundle?.getParcelableArrayList("LocalUniversityPlaces")!!
        localCityPlaces = bundle.getParcelableArrayList("LocalCityPlaces")!!
        place = bundle.getParcelable("Place")!!
        user = bundle.getParcelable("User")

        background_color = findViewById(R.id.background_color);
        message_text = findViewById(R.id.message_text);
        gif_box = findViewById(R.id.gif_box)
        camera_box = findViewById(R.id.camera_box)
        photo_box = findViewById(R.id.photo_library_box)
        poll_box = findViewById(R.id.poll_box)

        option1 = findViewById(R.id.option_1)
        option2 = findViewById(R.id.option_2)
        option3 = findViewById(R.id.option_3)
        option4 = findViewById(R.id.option_4)

        option3Box = findViewById(R.id.option_3_box)
        option4Box = findViewById(R.id.option_4_box)

        image_container = findViewById(R.id.image_container)

        poll = findViewById(R.id.poll)
        send_yap = findViewById(R.id.send_yap)

        post_image = findViewById(R.id.post_image)

        cancel_image = findViewById(R.id.cancel_image)
        cancel_poll = findViewById(R.id.cancel_poll)

        add_poll_option = findViewById(R.id.add_poll)

        randomColor = HelperFunctions.random_color(11)

        background_color.setBackgroundColor(Color.parseColor(randomColor!![0]));
        message_text.hint = "Send a Yap to people in " + place.name;

        //set gif settings
        settings.showConfirmationScreen = true
        settings.rating = RatingType.r
        settings.renditionType = RenditionType.fixedWidth
        settings.confirmationRenditionType = RenditionType.original
        settings.showCheckeredBackground = false
        settings.useBlurredBackground = true
        settings.stickerColumnCount = 4

        image_container.visibility = View.GONE
        poll!!.visibility = View.GONE


        Giphy.configure(this, YOUR_API_KEY)
        find_location()

        val mediaView = GPHMediaView(this);
        mediaView.setMediaWithId(R.id.giphy.toString())

        sendableCalculator()

        message_text.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {

            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                sendableCalculator()
            }
        })

        option1.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {

            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                sendableCalculator()
            }
        })
        option2.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {

            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                sendableCalculator()
            }
        })
        option3.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {

            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                sendableCalculator()
            }
        })
        option4.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {

            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                sendableCalculator()
            }
        })

        add_poll_option.setOnClickListener(View.OnClickListener {

            if(poleSize == 2){
                poleSize = 3
                option3Box!!.visibility = View.VISIBLE
            }
            else if(poleSize == 3){
                poleSize = 4
                option4Box!!.visibility = View.VISIBLE
                add_poll_option.visibility = View.GONE
            }
            sendableCalculator()
        })

        cancel_image.setOnClickListener(View.OnClickListener {
            image_container.visibility = View.GONE
            messageOption = "Text"
            sendableCalculator()
        })

        cancel_poll.setOnClickListener(View.OnClickListener {
            poll!!.visibility = View.GONE
            messageOption = "Text"
            sendableCalculator()
        })

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

        poll_box.setOnClickListener(View.OnClickListener {
            messageOption = "Poll"
            poleSize = 2
            image_container.visibility = View.GONE
            option3Box!!.visibility = View.GONE
            option4Box!!.visibility = View.GONE
            add_poll_option.visibility = View.VISIBLE
            poll!!.visibility = View.GONE
            poll?.visibility = View.VISIBLE
            sendableCalculator()
        })



        send_yap!!.setOnClickListener(View.OnClickListener {
            if(messageOption.equals("Text")){
                model!!.post_text(message_text.text.toString(),userLocation,randomColor!![0],place,user)
            }
            if(messageOption.equals("Poll")){
                val pollValues: ArrayList<String> = ArrayList()

                pollValues.add(option1.text.toString())
                pollValues.add(option2.text.toString())
                if(poleSize==3){
                    pollValues.add(option3.text.toString())
                }
                if(poleSize==4){
                    pollValues.add(option3.text.toString())
                    pollValues.add(option4.text.toString())
                }

                model!!.post_poll(message_text.text.toString(),pollValues,userLocation,randomColor!![0],place,user)
            }
            if(messageOption.equals("Image")){
                model!!.post_image(message_text.text.toString(),image_bitmap,userLocation,randomColor!![0],place,user)
            }
            if(messageOption.equals("Gif")){
                model!!.post_gif(message_text.text.toString(),gif_url,userLocation,randomColor!![0],place,user)
            }
        })


        backBtn = findViewById(R.id.goBack)
        val editText: EditText = object : AppCompatEditText(this) {
            override fun onCreateInputConnection(editorInfo: EditorInfo): InputConnection {
                val ic = super.onCreateInputConnection(editorInfo)
                EditorInfoCompat.setContentMimeTypes(editorInfo, arrayOf("image/png"))
                val callback = OnCommitContentListener { inputContentInfo, flags, opts ->
                    // read and display inputContentInfo asynchronously
                    if (BuildCompat.isAtLeastNMR1() && flags and
                            InputConnectionCompat.INPUT_CONTENT_GRANT_READ_URI_PERMISSION != 0) {
                        try {
                            inputContentInfo.requestPermission()
                        } catch (e: Exception) {
                            return@OnCommitContentListener false // return false if failed
                        }
                    }

                    // read and display inputContentInfo asynchronously.
                    // call inputContentInfo.releasePermission() as needed.
                    true // return true if succeeded
                }
                return InputConnectionCompat.createWrapper(ic, editorInfo, callback)
            }
        }
        backBtn.setOnClickListener(View.OnClickListener {
            try {
                controller!!.goBack(getIntent(),localUniversityPlaces,localCityPlaces, user,place)
            } catch (e: ClassNotFoundException) {
                e.printStackTrace()
            }
        })

    }
    override fun onBackPressed() {
        try {
            controller!!.goBack(getIntent(),localUniversityPlaces,localCityPlaces, user,place)
        } catch (e: ClassNotFoundException) {
            e.printStackTrace()
        }
    }
    private fun openGalleryForImage() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, REQUEST_CODE)
    }
    private fun dispatchTakePictureIntent() {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
            takePictureIntent.resolveActivity(packageManager)?.also {
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
            }
        }
    }
    interface GPHGridCallback {
        fun contentDidUpdate(resultCount: Int)
        fun didSelectMedia(media: Media)
    }

    private fun getGifSelectionListener() = object : GiphyDialogFragment.GifSelectionListener {
        override fun onGifSelected(media: Media, searchTerm: String?) {
            Log.d(TAG, "onGifSelected")
            var content = if (media.url?.lastIndexOf("-") != -1) {
                media.url?.substring(media.url?.lastIndexOf("-")!! + 1)
            } else{
                media.url?.substring(media.url?.lastIndexOf("gifs/")!! + 5)
            }
            var url = "https://media.giphy.com/media/${content}/giphy.gif"
            println(url)

            poll?.visibility = View.GONE

            GlideApp.with(context!!)
                    .asGif()
                    .load(url)
                    .into(post_image)


            messageOption = "Gif"
            image_container.visibility = View.VISIBLE
            gif_url = url

        }
        override fun onDismissed() {
            Log.d(TAG, "onDismissed")
        }

        override fun didSearchTerm(term: String) {
            Log.d(TAG, "didSearchTerm $term")
        }
    }

    override fun didSearchTerm(term: String) {
        TODO("Not yet implemented")
    }

    override fun onDismissed() {
        TODO("Not yet implemented")
    }

    override fun onGifSelected(media: Media, searchTerm: String?) {
        TODO("Not yet implemented")
    }

    companion object {
        //return instance.getApplicationContext();
        var context: Context? = null
            private set
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

                        poll?.visibility = View.GONE
                        GlideApp.with(context!!)
                                .load(bitmap)
                                .into(post_image)

                        image_bitmap = bitmap

                        messageOption = "Image"
                        image_container.visibility = View.VISIBLE


                    } else {
                        val source = ImageDecoder.createSource(this.contentResolver, selectedPhotoUri)
                        val bitmap = ImageDecoder.decodeBitmap(source)
                        poll?.visibility = View.GONE
                        GlideApp.with(context!!)
                                .load(bitmap)
                                .into(post_image)

                        image_bitmap = bitmap

                        messageOption = "Image"
                        image_container.visibility = View.VISIBLE


                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            val imageBitmap = data?.extras?.get("data") as Bitmap

            image_bitmap = imageBitmap

            messageOption = "Image"
            image_container.visibility = View.VISIBLE
            poll?.visibility = View.GONE

            GlideApp.with(context!!)
                    .load(imageBitmap)
                    .into(post_image)
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
    private fun find_location() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), 1)
            return
        } else {
            locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
            locationManager!!.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 1000f, this)
        }
    }

    override fun onStatusChanged(provider: String, status: Int, extras: Bundle) {}
    override fun onProviderEnabled(provider: String) {}
    override fun onProviderDisabled(provider: String) {}

    fun sendableCalculator(){
        if(messageOption.equals("Text")){
            if(message_text.text.toString().equals("")){
                send_yap?.isClickable = false
                send_yap?.setTextColor(Color.parseColor("#CCCCCCCC"))
            }
            else{
                send_yap?.isClickable = true
                send_yap?.setTextColor(Color.parseColor("#000000"))
            }
        }
        if(messageOption.equals("Poll")){
            //TODO
            if(message_text.text.toString().equals("")){
                send_yap?.isClickable = false
                send_yap?.setTextColor(Color.parseColor("#CCCCCCCC"))
            }
            else{
                if(poleSize==2) {
                    if(option1.text.toString().equals("") or option2.text.toString().equals("")){
                        send_yap?.isClickable = false
                        send_yap?.setTextColor(Color.parseColor("#CCCCCCCC"))
                    }
                    else {
                        send_yap?.isClickable = true
                        send_yap?.setTextColor(Color.parseColor("#000000"))
                    }
                }
                if(poleSize==3) {
                    if(option1.text.toString().equals("") or option2.text.toString().equals("") or option3.text.toString().equals("")){
                        send_yap?.isClickable = false
                        send_yap?.setTextColor(Color.parseColor("#CCCCCCCC"))
                    }
                    else {
                        send_yap?.isClickable = true
                        send_yap?.setTextColor(Color.parseColor("#000000"))
                    }
                }
                if(poleSize==4) {
                    if(option1.text.toString().equals("") or option2.text.toString().equals("") or option3.text.toString().equals("") or option4.text.toString().equals("")){
                        send_yap?.isClickable = false
                        send_yap?.setTextColor(Color.parseColor("#CCCCCCCC"))
                    }
                    else {
                        send_yap?.isClickable = true
                        send_yap?.setTextColor(Color.parseColor("#000000"))
                    }
                }
            }
        }
        if(messageOption.equals("Image")){
            send_yap?.isClickable = true
            send_yap?.setTextColor(Color.parseColor("#000000"))
        }
        if(messageOption.equals("Gif")){
            send_yap?.isClickable = true
            send_yap?.setTextColor(Color.parseColor("#000000"))
        }
    }

}
