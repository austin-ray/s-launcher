package io.austinray.slauncher

import android.content.pm.PackageManager
import android.content.pm.ResolveInfo
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.RecyclerView.ViewHolder
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import io.austinray.slauncher.Adapter.ApplicationViewHolder

class Adapter(private val data: List<ResolveInfo>, private val pm: PackageManager) : RecyclerView.Adapter<ApplicationViewHolder>() {

    private var filteredData = data

    inner class ApplicationViewHolder(view: View) : ViewHolder(view) {
        val icon: ImageView = view.findViewById(R.id.app_icon)
        val name: TextView = view.findViewById(R.id.app_name)

        init {
            view.setOnClickListener {
                val pos = adapterPosition
                val context = view.context
                val pm = context.packageManager

                val launchIntent = pm.getLaunchIntentForPackage(filteredData[pos].activityInfo.packageName)
                context?.startActivity(launchIntent)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, position: Int): ApplicationViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_application, null)
        return ApplicationViewHolder(view)
    }

    override fun getItemCount(): Int {
        return filteredData.size
    }

    override fun onBindViewHolder(holder: ApplicationViewHolder, position: Int) {
        val dataNode = filteredData[position]

        holder.icon.setImageDrawable(dataNode.loadIcon(holder.itemView.context.packageManager))
        holder.name.text = dataNode.activityInfo.loadLabel(holder.itemView.context.packageManager)
    }

    fun applyFilter(filterStr: String) {
        filteredData = data.filter { it.loadLabel(pm).toString().toLowerCase().contains(filterStr.toLowerCase()) }
        notifyDataSetChanged()
    }
}