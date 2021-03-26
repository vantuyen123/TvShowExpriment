package com.example.material.tvshowexperiment.ui.detail

import android.R
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.res.Resources
import android.net.Uri
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.core.text.HtmlCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.material.tvshowexperiment.data.TvShow
import com.example.material.tvshowexperiment.data.TvShowDetail
import com.example.material.tvshowexperiment.databinding.LayoutEpisodesBottomSheetBinding
import com.example.material.tvshowexperiment.databinding.TvShowDetailFragmentBinding
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.snackbar.Snackbar
import java.lang.String

class TvShowDetailFragment : Fragment() {

    private lateinit var viewModel: TvShowDetailViewModel

    private lateinit var binding: TvShowDetailFragmentBinding

    private var sliderAdapter = SliderAdapter()


    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = TvShowDetailFragmentBinding.inflate(inflater)
        binding.lifecycleOwner = this


        val application = requireNotNull(activity).application
        val tvShow = TvShowDetailFragmentArgs.fromBundle(requireArguments()).tvshow
        val viewModelFactory = TvShowDetailViewModelFactory(tvShow, application)
        viewModel = ViewModelProvider(this, viewModelFactory).get(TvShowDetailViewModel::class.java)
        binding.viewModel = viewModel

        setupData()
        handleReadMore()
        onBackBottom()
        setupSlider()
        handleNetwork()

        return binding.root
    }

    @SuppressLint("SetTextI18n")
    private fun setupData() {
        viewModel.selectMovieDetail.observe(viewLifecycleOwner, { tvShowDetail ->
            if (tvShowDetail != null) {
                sliderAdapter.data = tvShowDetail.pictures
                binding.textGenre.text = tvShowDetail.genres[0]
                binding.textRating.text = tvShowDetail.rating
                binding.textRuntime.text = tvShowDetail.runtime.toString() + " Min"
                binding.textDescription.text = HtmlCompat.fromHtml(
                    tvShowDetail.description,
                    HtmlCompat.FROM_HTML_MODE_LEGACY
                ).toString()
                binding.buttonWebsite.setOnClickListener {
                    val intent = Intent(Intent.ACTION_VIEW)
                    intent.data = Uri.parse(tvShowDetail.url)
                    startActivity(intent)
                }
                handleEpisodesDialog(tvShowDetail)
            }
        })
    }


    private fun handleReadMore() {
        binding.textReadMore.setOnClickListener {
            if (binding.textReadMore.text.toString() == "Read More") {
                binding.textDescription.maxLines = Int.MAX_VALUE
                binding.textDescription.ellipsize = null
                "Read Less".also { binding.textReadMore.text = it }
            } else {
                binding.textDescription.maxLines = 4
                binding.textDescription.ellipsize = TextUtils.TruncateAt.END
                "Read More".also { binding.textReadMore.text = it }
            }
        }
    }

    private fun handleNetwork() {
        viewModel.status.observe(viewLifecycleOwner, { status ->
            when (status) {
                TvShowDetailStatus.DONE -> {
                    binding.scrollViewContent.visibility = View.VISIBLE
                }
                TvShowDetailStatus.ERROR -> {
                    binding.progressBar.visibility = View.GONE
                    binding.statusImage.visibility = View.VISIBLE
                }
                else -> TvShowDetailStatus.LOADING
            }
        })
        viewModel.showSnackbarEvent.observe(viewLifecycleOwner, {
            if (it == true) {
                Snackbar.make(
                    requireActivity().findViewById(R.id.content),
                    "Error Internet",
                    Snackbar.LENGTH_SHORT
                ).show()

                viewModel.doneShowingSnackBar()
            }
        })
    }

    private fun onBackBottom() {
        viewModel.navigateToHome.observe(viewLifecycleOwner, {
            if (it == true) {
                this.findNavController()
                    .navigate(TvShowDetailFragmentDirections.actionTvShowDetailFragmentToMostPopularFragment())
                viewModel.doneNavigating()
            }
        })
    }


    private fun setupSlider() {
        binding.sliderViewPager.visibility = View.VISIBLE
        binding.sliderViewPager.adapter = sliderAdapter
    }

    private fun handleEpisodesDialog(tvShowDetail: TvShowDetail) {
        binding.buttonEpisodes.setOnClickListener {
            val bottomDialog = BottomSheetDialog(this.requireContext())
            val layoutEpisodesBottomSheetBinding = LayoutEpisodesBottomSheetBinding.inflate(
                LayoutInflater.from(this.requireContext())
            )
            bottomDialog.setContentView(layoutEpisodesBottomSheetBinding.root)
            val episodeAdapter = EpisodeAdapter()
            episodeAdapter.data = tvShowDetail.episodes
            layoutEpisodesBottomSheetBinding.episodesRecyclerView.adapter = episodeAdapter
            layoutEpisodesBottomSheetBinding.textTitle.text =
                String.format("Episodes | %s", tvShowDetail.name)
            layoutEpisodesBottomSheetBinding.imageClose.setOnClickListener { bottomDialog.dismiss() }
            // ---- Optional section Start ----//
            val frameLayout = bottomDialog.findViewById<FrameLayout>(
                com.google.android.material.R.id.design_bottom_sheet
            )
            if (frameLayout != null) {
                val bottomSheetBehavior = BottomSheetBehavior.from<View>(frameLayout)
                bottomSheetBehavior.peekHeight =
                    Resources.getSystem().displayMetrics.heightPixels
                bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
            }
            bottomDialog.show()
        }
    }


}