package com.example.imageclassificationdemo.ui.detailclassifier

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.imageclassificationdemo.R
import com.example.imageclassificationdemo.conseil
import com.example.imageclassificationdemo.header
import com.example.imageclassificationdemo.maladie
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.fragment_detail_classifier.view.*
import models.Conseil
import models.Maladie
import java.util.*

class DetailClassifierFragment : Fragment(R.layout.fragment_detail_classifier) {

    private val conseils = mutableListOf<Conseil>()
    private val args by lazy {
        DetailClassifierFragmentArgs.fromBundle(requireArguments())
    }

    private var maladie: Maladie? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getMaladie()
        getConseilMaladie()
    }

    private fun displayData(view: View) {
        view.rvDetailMaladie.withModels {
            header {
                id(UUID.randomUUID().toString())
                headerTitle("Maladie")
            }
            maladie {
                id(maladie?.uid)
                maladie(maladie)
            }
            header {
                id(UUID.randomUUID().toString())
                headerTitle("Conseils")
            }
            conseils.forEach { conseil ->
                conseil {
                    id(conseil.uid)
                    conseil(conseil)
                }
            }
        }
    }

    private fun getConseilMaladie() {
        FirebaseFirestore.getInstance().collection("conseils")
            .whereEqualTo("uidMaladie", args.uidMaladie)
            .addSnapshotListener { value, error ->
                if (error != null && value == null) {
                    return@addSnapshotListener
                }

                conseils.clear()
                conseils.addAll(value?.toObjects(Conseil::class.java)!!)
                conseils.forEach {
                    Log.e("ericampire", it.titre)
                }
                displayData(requireView())
            }
    }

    private fun getMaladie() {
        FirebaseFirestore.getInstance().document("maladies/${args.uidMaladie}")
            .addSnapshotListener { value, error ->
                if (error != null && value == null) {
                    return@addSnapshotListener
                }

                maladie = value?.toObject(Maladie::class.java)!!
                Log.e("ericampire", maladie.toString())

            }

    }
}