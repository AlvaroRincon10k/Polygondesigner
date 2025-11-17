package com.alvaro.polygondesigner.ui.selection

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.alvaro.polygondesigner.data.model.PolygonResponse
import com.alvaro.polygondesigner.databinding.ItemPolygonBinding
import com.alvaro.polygondesigner.ui.designer.PolygonDesignerActivity

class PolygonAdapter(
    private var items: List<PolygonResponse>,
    private val onClick: (PolygonResponse) -> Unit
) : RecyclerView.Adapter<PolygonAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: ItemPolygonBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: PolygonResponse) {
            binding.txtName.text = item.name

            binding.root.setOnClickListener {
                val context = binding.root.context
                val intent = Intent(context, PolygonDesignerActivity::class.java)
                intent.putExtra("polygon", item)
                intent.putExtra("polygonName", item.name)
                context.startActivity(intent)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemPolygonBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount() = items.size
}