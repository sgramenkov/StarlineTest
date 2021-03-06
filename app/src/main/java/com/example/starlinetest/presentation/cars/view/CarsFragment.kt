package com.example.starlinetest.presentation.cars.view

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.BindingAdapter
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.example.starlinetest.App
import com.example.starlinetest.R
import com.example.starlinetest.databinding.FragmentCarsBinding
import com.example.starlinetest.di.CarsModule

import com.example.starlinetest.domain.entity.Car
import com.example.starlinetest.presentation.cars.viewmodel.CarsViewModel
import com.example.starlinetest.presentation.cars.adapter.CarsAdapter

import javax.inject.Inject


class CarsFragment : Fragment(), CarsAdapter.CarListener {

    @Inject
    lateinit var carsViewModel: CarsViewModel

    private lateinit var binding: FragmentCarsBinding


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_cars, container, false)
        binding.viewModel = carsViewModel

        carsViewModel.parseFile()

        observeCarsList()

        return binding.root
    }

    private fun observeCarsList() {
        carsViewModel.getcarsList().observe(viewLifecycleOwner, {
            binding.rv.adapter = CarsAdapter(it, this)
        })
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        App.getBuilder().carsModule(CarsModule((this))).build().inject(this)
    }

    override fun onCarItemClick(item: Car?) {
        if (item?.lat != null && item.lon != null) {

            val bundle = Bundle().apply {
                putDouble("lat", item.lat)
                putDouble("lng", item.lon)
            }

            activity?.findNavController(R.id.nav_host_fragment)
                ?.navigate(R.id.navigation_map, bundle)

        }
    }


}

