package com.bintangpoetra.sumbanginaja.presentation.home.adapter

import android.annotation.SuppressLint
import android.location.Location
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bintangpoetra.sumbanginaja.R
import com.bintangpoetra.sumbanginaja.databinding.FoodItemBinding
import com.bintangpoetra.sumbanginaja.domain.food.model.Food
import com.bintangpoetra.sumbanginaja.utils.ext.orZero
import com.bintangpoetra.sumbanginaja.utils.ext.toDistance
import com.bumptech.glide.Glide
import timber.log.Timber

@SuppressLint("NotifyDataSetChanged")
class FoodAdapter(private val onClick: (id: Int) -> Unit) :
    RecyclerView.Adapter<FoodAdapter.FoodViewHolder>() {

    private val listFood = ArrayList<Food>()
    private var myLocation: Location? = null

    fun setData(listFood: List<Food>) {
        this.listFood.addAll(listFood)
        notifyDataSetChanged()
    }

    fun setMyLocation(location: Location){
        this.myLocation = location
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodViewHolder {
        val binding =
            FoodItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FoodViewHolder(binding)
    }


    override fun onBindViewHolder(holder: FoodViewHolder, position: Int) {
        val data = listFood[position]
        holder.bind(data)
        holder.itemView.setOnClickListener {
            onClick(data.id)
        }
    }

    override fun getItemCount(): Int = listFood.size

    @SuppressLint("SetTextI18n")
    inner class FoodViewHolder(val binding: FoodItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        private val foodLocation = Location("locationA")
        private var distance = 0;
        fun bind(food: Food) {
            foodLocation.latitude = food.latitude.orZero()
            foodLocation.longitude = food.longitude.orZero()

            Timber.d("location RV is ${myLocation?.latitude} + ${myLocation?.longitude}")

            if (myLocation != null){
                distance = foodLocation.distanceTo(myLocation).toInt()
            }


            binding.apply {
                tvFoodName.text = food.name
                tvFoodOwnerAddress.text = food.descriptions
                tvFoodOwner.text = food.user?.name
                tvDistance.text = distance.toDistance()

                Glide.with(root.context)
                    .load(food.images)
                    .placeholder(R.color.colorSoftGrey)
                    .into(imgFoodOwner)

                Glide.with(root.context)
                    .load(food.images)
                    .placeholder(R.color.colorSoftGrey)
                    .into(imgFood)
            }
        }
    }
}
