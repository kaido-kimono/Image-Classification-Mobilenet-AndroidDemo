package com.example.imageclassificationdemo.ui.classifier

import org.tensorflow.lite.support.image.TensorImage
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer
import org.tensorflow.lite.support.common.TensorProcessor
import android.graphics.Bitmap
import android.widget.TextView
import com.google.android.material.progressindicator.LinearProgressIndicator
import models.Maladie
import android.view.LayoutInflater
import android.view.ViewGroup
import android.os.Bundle
import com.example.imageclassificationdemo.R
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.FirebaseFirestoreException
import org.tensorflow.lite.support.label.TensorLabel
import androidx.navigation.NavDirections
import kotlin.Throws
import android.app.Activity
import android.content.res.AssetFileDescriptor
import org.tensorflow.lite.support.common.TensorOperator
import org.tensorflow.lite.support.common.ops.NormalizeOp
import com.example.imageclassificationdemo.ui.classifier.ClassifierFragment
import org.tensorflow.lite.support.image.ImageProcessor
import org.tensorflow.lite.support.image.ops.ResizeWithCropOrPadOp
import org.tensorflow.lite.support.image.ops.ResizeOp
import android.widget.Toast
import android.view.MenuInflater
import android.content.Intent
import android.provider.MediaStore
import android.content.ActivityNotFoundException
import android.net.Uri
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.ImageView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.google.firebase.storage.FirebaseStorage
import models.Conseil
import models.Diagnostic
import org.tensorflow.lite.Interpreter
import org.tensorflow.lite.support.common.FileUtil
import java.io.FileInputStream
import java.io.IOException
import java.lang.Exception
import java.nio.MappedByteBuffer
import java.nio.channels.FileChannel
import java.util.*

class ClassifierFragment : Fragment() {
    protected var tflite: Interpreter? = null
    private val tfliteModel: MappedByteBuffer? = null
    private var inputImageBuffer: TensorImage? = null
    private var imageSizeX = 0
    private var imageSizeY = 0
    private var outputProbabilityBuffer: TensorBuffer? = null
    private var probabilityProcessor: TensorProcessor? = null
    private var bitmap: Bitmap? = null
    private var labels: List<String>? = null
    private var imageView: ImageView? = null
    private var imageuri: Uri? = null
    private var buclassify: Button? = null
    private var classitext: TextView? = null
    private var progressIndicator: LinearProgressIndicator? = null
    private val maladies: MutableList<Maladie> = mutableListOf()


    override fun onStart() {
        super.onStart()
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        maladies
        return inflater.inflate(R.layout.fragment_classifier, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        imageView = view.findViewById<View>(R.id.image) as ImageView
        buclassify = view.findViewById<View>(R.id.classify) as Button
        classitext = view.findViewById<View>(R.id.classifytext) as TextView
        progressIndicator = view.findViewById(R.id.linearProgressIndicator2)
        try {
            tflite = Interpreter(loadmodelfile(requireActivity()))
        } catch (e: Exception) {
            e.printStackTrace()
        }
        buclassify!!.setOnClickListener { v: View? -> classifier() }
    }

    private fun classifier() {
        progressIndicator!!.show()
        val imageTensorIndex = 0
        val imageShape = tflite!!.getInputTensor(imageTensorIndex).shape() // {1, height, width, 3}
        imageSizeY = imageShape[1]
        imageSizeX = imageShape[2]
        val imageDataType = tflite!!.getInputTensor(imageTensorIndex).dataType()
        val probabilityTensorIndex = 0
        val probabilityShape =
            tflite!!.getOutputTensor(probabilityTensorIndex).shape() // {1, NUM_CLASSES}
        val probabilityDataType = tflite!!.getOutputTensor(probabilityTensorIndex).dataType()
        inputImageBuffer = TensorImage(imageDataType)
        outputProbabilityBuffer =
            TensorBuffer.createFixedSize(probabilityShape, probabilityDataType)
        probabilityProcessor = TensorProcessor.Builder().add(postprocessNormalizeOp).build()
        inputImageBuffer = loadImage(bitmap)
        tflite!!.run(inputImageBuffer!!.buffer, outputProbabilityBuffer!!.buffer.rewind())
        showresult()
    }

    private fun getConseilMaladie(uidMaladie: String) {
        FirebaseFirestore.getInstance().collection("conseils")
            .whereEqualTo("uidMaladie", uidMaladie)
            .addSnapshotListener { value, error ->
                if (error != null && value == null) {
                    return@addSnapshotListener
                }


                val data = value?.toObjects(Conseil::class.java)


                val ref = FirebaseStorage.getInstance().getReference("image")
                ref.putFile(imageuri!!).addOnSuccessListener {
                        ref.downloadUrl.addOnSuccessListener { url ->
                            val diagnostique = Diagnostic()
                            diagnostique.uidMaladie = uidMaladie
                            diagnostique.uidConseil = data?.firstOrNull()?.uid ?: ""
                            diagnostique.urlImage = url.toString()

                            Log.e("ericampire", url.toString())

                            val docRef = FirebaseFirestore.getInstance().collection("diagnostiques")
                                .document()

                            diagnostique.uid = docRef.id
                            docRef.set(diagnostique).addOnSuccessListener {
                                progressIndicator?.visibility = View.INVISIBLE

                                val direction: NavDirections = ClassifierFragmentDirections.navToDetailClassifier(uidMaladie)
                                Navigation.findNavController(requireView()).navigate(direction)

                            }
                        }
                    }

            }
    }

    private fun saveClassification() {

    }


    private fun showresult() {
        try {
            labels = FileUtil.loadLabels(requireContext(), "newdict.txt")
        } catch (e: Exception) {
            e.printStackTrace()
        }
        val labeledProbability = TensorLabel(
            labels!!, probabilityProcessor!!.process(outputProbabilityBuffer)
        )
            .mapWithFloatValue
        val maxValueInMap = Collections.max(labeledProbability.values)
        for ((key, value) in labeledProbability) {
            if (value == maxValueInMap) {
                // Todo:df
                getConseilMaladie(key)
            }
        }
    }

    @Throws(IOException::class)
    private fun loadmodelfile(activity: Activity): MappedByteBuffer {
        val fileDescriptor = activity.assets.openFd("newmodel.tflite")
        val inputStream = FileInputStream(fileDescriptor.fileDescriptor)
        val fileChannel = inputStream.channel
        val startoffset = fileDescriptor.startOffset
        val declaredLength = fileDescriptor.declaredLength
        return fileChannel.map(FileChannel.MapMode.READ_ONLY, startoffset, declaredLength)
    }

    private val preprocessNormalizeOp: TensorOperator
        private get() = NormalizeOp(IMAGE_MEAN, IMAGE_STD)
    private val postprocessNormalizeOp: TensorOperator
        private get() = NormalizeOp(PROBABILITY_MEAN, PROBABILITY_STD)

    private fun loadImage(bitmap: Bitmap?): TensorImage? {
        // Loads bitmap into a TensorImage.
        var tensorImage: TensorImage? = null
        try {
            inputImageBuffer!!.load(bitmap)
            val cropSize = Math.min(bitmap!!.width, bitmap.height)
            // TODO(b/143564309): Fuse ops inside ImageProcessor.
            val imageProcessor = ImageProcessor.Builder()
                .add(ResizeWithCropOrPadOp(cropSize, cropSize))
                .add(ResizeOp(imageSizeX, imageSizeY, ResizeOp.ResizeMethod.NEAREST_NEIGHBOR))
                .add(preprocessNormalizeOp)
                .build()
            tensorImage = imageProcessor.process(inputImageBuffer)
        } catch (e: Exception) {
            Toast.makeText(requireContext(), e.localizedMessage, Toast.LENGTH_LONG).show()
        }
        return tensorImage
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.menu_item_capture) {
            captureImage()
        } else {
            selectOnGalery()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun selectOnGalery() {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), 12)
    }

    private fun captureImage() {
        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        try {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
        } catch (e: ActivityNotFoundException) {
            // display error state to the user
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == Activity.RESULT_OK && data != null) {
            imageuri = data.data
            val extras = data.extras
            bitmap = extras!!["data"] as Bitmap?
            imageView!!.setImageBitmap(bitmap)
        }
        if (requestCode == 12 && resultCode == Activity.RESULT_OK && data != null) {
            imageuri = data.data
            try {
                bitmap =
                    MediaStore.Images.Media.getBitmap(requireActivity().contentResolver, imageuri)
                imageView!!.setImageBitmap(bitmap)
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }

    companion object {
        const val REQUEST_IMAGE_CAPTURE = 1
        private const val IMAGE_MEAN = 0.0f
        private const val IMAGE_STD = 1.0f
        private const val PROBABILITY_MEAN = 0.0f
        private const val PROBABILITY_STD = 255.0f
    }
}