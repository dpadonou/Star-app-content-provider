package fr.istic.mob.starapplication

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController

class DetailsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_details, container, false)

        val fab: View = view.findViewById(R.id.downloadBtn)
        fab.setOnClickListener {
            Log.v("Error", "Clicked.")
            findNavController().navigate(R.id.action_detailsFragment_to_addFragment)
        }

        return view
    }

}