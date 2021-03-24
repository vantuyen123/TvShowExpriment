//package com.example.material.tvshowexperiment.ui.detail
//
//import android.view.LayoutInflater
//import android.view.ViewGroup
//import androidx.databinding.DataBindingUtil
//import androidx.recyclerview.widget.RecyclerView
//import com.example.material.tvshowexperiment.R
//
//
//class ImageSliderAdapter(private val sliderImages: Array<String>) :
//    RecyclerView.Adapter<ImageSliderAdapter.ImageSliderViewHolder>() {
//    private var layoutInflater: LayoutInflater? = null
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageSliderViewHolder {
//        if (layoutInflater == null) {
//            layoutInflater = LayoutInflater.from(parent.context)
//        }
//        val sliderImageBinding: ItemContainerSliderImageBinding = DataBindingUtil.inflate(
//            layoutInflater!!, R.layout.item_container_slider_image, parent, false
//        )
//        return ImageSliderViewHolder(sliderImageBinding)
//    }
//
//    override fun onBindViewHolder(holder: ImageSliderViewHolder, position: Int) {
//        holder.bindSliderImage(sliderImages[position])
//    }
//
//    override fun getItemCount(): Int {
//        return sliderImages.size
//    }
//
//    internal class ImageSliderViewHolder(itemContainerSliderImageBinding: ItemContainerSliderImageBinding) :
//        RecyclerView.ViewHolder(itemContainerSliderImageBinding.getRoot()) {
//        private val itemContainerSliderImageBinding: ItemContainerSliderImageBinding
//        fun bindSliderImage(imageUrl: String?) {
//            itemContainerSliderImageBinding.setImageURL(imageUrl)
//        }
//
//        init {
//            this.itemContainerSliderImageBinding = itemContainerSliderImageBinding
//        }
//    }
//}
