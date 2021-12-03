package fr.istic.mob.starapplication

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.MultiAutoCompleteTextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import fr.istic.mob.starapplication.entities.BusRoutes
import fr.istic.mob.starapplication.viewmodels.BusRoutesViewModel

class AddFragment : Fragment() {

    private lateinit var busRouteViewModel : BusRoutesViewModel
    private lateinit var longName : EditText
    private lateinit var shortName : EditText
    private lateinit var description : MultiAutoCompleteTextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_add, container, false).apply {
            longName = findViewById(R.id.longNameText)
            shortName = findViewById(R.id.shortNameText)
            description = findViewById(R.id.descriptionText)
        }

        busRouteViewModel = ViewModelProvider(this)[BusRoutesViewModel::class.java]

        val btn: View = view.findViewById(R.id.saveBtn)
        btn.setOnClickListener {
            createNew()
        }

        return view
    }

    private fun createNew() {
        val longNameText = longName.text.toString()
        val shortNameText = shortName.text.toString()
        val descriptionText = description.text.toString()

        val busRoutes = BusRoutes(0, shortNameText, longNameText, descriptionText, "", "", "")
        busRouteViewModel.createBusRoutes(busRoutes)
        Toast.makeText(requireContext(), "Successfully added.", Toast.LENGTH_SHORT).show()
        findNavController().navigate(R.id.action_addFragment_to_detailsFragment)
    }

}