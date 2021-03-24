package com.example.material.tvshowexperiment.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.material.tvshowexperiment.databinding.TvShowDetailFragmentBinding

class TvShowDetailFragment : Fragment() {

    private lateinit var viewModel: TvShowDetailViewModel

    private lateinit var binding: TvShowDetailFragmentBinding

    private var sliderAdapter = SliderAdapter()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = TvShowDetailFragmentBinding.inflate(inflater)


        val application = requireNotNull(activity).application
        val tvShow = TvShowDetailFragmentArgs.fromBundle(requireArguments()).tvshow
        val viewModelFactory = TvShowDetailViewModelFactory(tvShow, application)
        viewModel = ViewModelProvider(this, viewModelFactory).get(TvShowDetailViewModel::class.java)
        binding.viewModel = viewModel

        viewModel.getTvShowDetail(tvShow.id.toInt())
        binding.viewModel = viewModel

        viewModel.selectMovieDetail.observe(viewLifecycleOwner, {
            if (it != null) {
                sliderAdapter.data = it.pictures
            }
        })
        binding.sliderViewPager.adapter = sliderAdapter

        binding.lifecycleOwner = this
        return binding.root
    }


}