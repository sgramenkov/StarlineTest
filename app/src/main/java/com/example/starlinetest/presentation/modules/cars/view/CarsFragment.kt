package com.example.starlinetest.presentation.modules.cars.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.example.starlinetest.R
import com.example.starlinetest.databinding.FragmentCarsBinding
import com.example.starlinetest.domain.entity.Car
import com.example.starlinetest.domain.utils.Keys.LAT_BUNDLE
import com.example.starlinetest.domain.utils.Keys.LNG_BUNDLE
import com.example.starlinetest.presentation.base.App
import com.example.starlinetest.presentation.modules.cars.viewmodel.CarsViewModel
import com.example.starlinetest.presentation.modules.cars.adapter.CarsAdapter
import com.example.starlinetest.presentation.modules.cars.viewmodel.CarsVmFactory

import javax.inject.Inject


class CarsFragment : Fragment(), CarsAdapter.CarListener {

    @Inject
    lateinit var carsVmFactory: CarsVmFactory

    private lateinit var viewModel: CarsViewModel

    private lateinit var binding: FragmentCarsBinding


    init {
        App.getComponent().inject(this)
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_cars, container, false)

        viewModel = ViewModelProvider(this, carsVmFactory).get(CarsViewModel::class.java)

        binding.viewModel = viewModel

        viewModel.parseFile()

        observeCarsList()

        return binding.root
    }

    private fun observeCarsList() {
        viewModel.getCarsList().observe(viewLifecycleOwner, {
            if (it.error != null)
                showToast(it.error)
            else if (it.data != null)
                binding.rv.adapter = CarsAdapter(it?.data?.devices?.list, this)
        })
    }

    private fun showToast(msg: String?) {
        msg?.let {
            Toast.makeText(requireContext(), it, Toast.LENGTH_LONG).show()
        }
    }

    override fun onCarItemClick(item: Car?) {
        if (item?.lat != null && item.lon != null) {

            val bundle = Bundle().apply {
                putDouble(LAT_BUNDLE, item.lat)
                putDouble(LNG_BUNDLE, item.lon)
            }

            activity?.findNavController(R.id.nav_host_fragment)
                ?.navigate(R.id.navigation_map, bundle)

        }
    }
}

