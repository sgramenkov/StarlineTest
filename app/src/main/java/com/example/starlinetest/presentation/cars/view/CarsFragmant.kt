package com.example.starlinetest.presentation.cars.view

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
import com.example.starlinetest.presentation.cars.viewmodel.CarsViewModel
import com.example.starlinetest.presentation.cars.adapter.CarsAdapter
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers


class HomeFragment : Fragment(), CarsAdapter.CarListener {

    private lateinit var carsViewModel: CarsViewModel

    private lateinit var binding: FragmentCarsBinding

    private var disposable: Disposable? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        carsViewModel =
            ViewModelProvider(this).get(CarsViewModel::class.java)
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_cars, container, false)

        val adapter = CarsAdapter(mutableListOf(), this)

        binding.rv.adapter = adapter
        parseFile()

        observeCarsData(adapter)

        msgListener()
        return binding.root
    }

    private fun observeCarsData(adapter: CarsAdapter) {
        carsViewModel.getCarsLiveData().observe(viewLifecycleOwner, {
            adapter.addItems(it.devices?.list as MutableList<Car>?)
        })
    }

    private fun parseFile() {
        disposable = Single.fromCallable {
            carsViewModel.readJsonFromAssets(resources.assets)
        }.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                carsViewModel.setCars(it)
            }, {
                Toast.makeText(requireContext(), it.message, Toast.LENGTH_LONG).show()
            })
    }

    private fun msgListener() {
        carsViewModel.msgObservable.addOnPropertyChangedCallback(object :
            androidx.databinding.Observable.OnPropertyChangedCallback() {
            override fun onPropertyChanged(
                sender: androidx.databinding.Observable?,
                propertyId: Int
            ) {
                Toast.makeText(
                    requireContext(),
                    carsViewModel.msgObservable.get(),
                    Toast.LENGTH_LONG
                ).show()
            }
        })
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

    override fun onDestroy() {
        disposable?.dispose()
        super.onDestroy()
    }
}

