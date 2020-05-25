package com.example.jab

import adapaters.CommentAdapter
import android.content.Context
import android.content.Intent
import android.graphics.Point
import android.location.Location
import android.location.LocationListener
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.giphy.sdk.core.models.Media
import com.giphy.sdk.ui.Giphy
import com.giphy.sdk.ui.views.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import controllers.CommentController
import custom_class.Comment
import custom_class.PointMap
import custom_class.User
import models.CommentModel
import timber.log.Timber
import kotlin.collections.ArrayList

class CommentActivity : AppCompatActivity(), LocationListener, GiphyDialogFragment.GifSelectionListener {
    protected var userLocation: Location? = null
    private var auth: FirebaseAuth? = null
    private var db: FirebaseFirestore? = null
    private val storage: FirebaseStorage? = null
    private var controller: CommentController? = null
    private var model: CommentModel? = null
    private lateinit var commentView_1: RecyclerView
    private lateinit var commentView_2: RecyclerView
    private lateinit var commentView_3: RecyclerView
    private var commentAdapter_1: CommentAdapter? = null
    private var commentAdapter_2: CommentAdapter? = null
    private var commentAdapter_3: CommentAdapter? = null
    private val messageBtn: Button? = null
    private val textLeft: TextView? = null
    private val textRight: TextView? = null
    private val localLocationText: TextView? = null
    private var local_city = 0
    private val searchTabBtn: Button? = null
    private val chatTabBtn: Button? = null
    private val storiesTabBtn: Button? = null
    private val profileTabBtn: Button? = null

    //locationData
    private lateinit var cityLocation: String
    private lateinit var cityCoordinates: ArrayList<PointMap>
    private lateinit var cityLocationKey: String

    //localData
    private lateinit var localLocation: String
    private lateinit var localCoordinates: ArrayList<PointMap>
    private lateinit var localLocationKey: String
    private lateinit var messageID: String
    private lateinit var user: User
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_comments)
        auth = FirebaseAuth.getInstance()
        context = this
        commentView_1 = findViewById(R.id.comments_1)
        commentView_2 = findViewById(R.id.comments_2)
        commentView_3 = findViewById(R.id.comments_3)
        val intent = intent
        val bundle = intent.extras
        db = FirebaseFirestore.getInstance()
        cityLocation = bundle!!.getString("CityLocation").toString()
        cityCoordinates = bundle.getParcelableArrayList("CityCoordinates")!!
        cityLocationKey = bundle.getString("CityLocationKey").toString()
        localLocation = bundle.getString("LocalLocation").toString()
        localCoordinates = bundle.getParcelableArrayList("LocalCoordinates")!!
        localLocationKey = bundle.getString("LocalLocationKey").toString()
        messageID = bundle.getString("MessageID").toString()
        user = bundle.getParcelable("User")!!

        //textRight.setLayoutParams(textRightLayoutParams);
        local_city = getIntent().getIntExtra("LocalCity", 0)

        //messageBtn.setBackground(getResources().getDrawable(R.drawable.round_bound_pink));
        val display = windowManager.defaultDisplay
        val size = Point()
        display.getSize(size)
        val widthScreen = size.x
        val heightScreen = size.y
        controller = CommentController(auth, this)
        model = CommentModel(auth!!, db!!)
        model!!.loadComments(object : CommentActivity.FirestoreCallBackKot {
            override fun onCallback(comments: ArrayList<Comment?>?) {
                commentAdapter_1 = CommentAdapter(user, context)
                commentAdapter_2 = CommentAdapter(user, context)
                commentAdapter_3 = CommentAdapter(user, context)
                val comments_1 = getCommentsPart(comments, 1)
                val comments_2 = getCommentsPart(comments, 2)
                val comments_3 = getCommentsPart(comments, 3)
                commentAdapter_1!!.update(comments_1)
                commentAdapter_2!!.update(comments_2)
                commentAdapter_3!!.update(comments_3)
                commentView_1.setAdapter(commentAdapter_1)
                commentView_2.setAdapter(commentAdapter_2)
                commentView_3.setAdapter(commentAdapter_3)
                commentView_1.setLayoutManager(LinearLayoutManager(context))
                commentView_2.setLayoutManager(LinearLayoutManager(context))
                commentView_3.setLayoutManager(LinearLayoutManager(context))
            }

        }, cityLocation, cityCoordinates, cityLocationKey, localLocation, localCoordinates, localLocationKey, messageID, user)
        Giphy.configure(this, getString(R.string.GIPHY_API_KEY))

        GiphyDialogFragment.newInstance().show(supportFragmentManager, "giphy_dialog");

        val mediaView = GPHMediaView(this);

        mediaView.setMediaWithId(R.id.giphy.toString())

        mediaView.callback = object: GPHGridCallback {
            override fun contentDidUpdate(resultCount: Int) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun didSelectMedia(media: Media) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }
        }

        mediaView.searchCallback = object: GPHSearchGridCallback {
            override fun didTapUsername(username: String) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun didLongPressCell(cell: GifView) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun didScroll(dx: Int, dy: Int) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }
        }
    }

    interface FirestoreCallBackKot {
        fun onCallback(comments: ArrayList<Comment?>?)
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

    override fun onLocationChanged(location: Location) {}
    override fun onStatusChanged(provider: String, status: Int, extras: Bundle) {}
    override fun onProviderEnabled(provider: String) {}
    override fun onProviderDisabled(provider: String) {}

    companion object {
        // or return instance.getApplicationContext();
        var context: Context? = null
            private set
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        Timber.d("onActivityResult")
        super.onActivityResult(requestCode, resultCode, data)
    }


}