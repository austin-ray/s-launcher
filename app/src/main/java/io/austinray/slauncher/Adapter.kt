package io.austinray.slauncher

import android.content.Intent
import android.content.pm.ApplicationInfo
import android.content.pm.ResolveInfo
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.RecyclerView.ViewHolder
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import io.austinray.slauncher.Adapter.ApplicationViewHolder

class Adapter(val data: List<ResolveInfo>) : RecyclerView.Adapter<ApplicationViewHolder>() {

    inner class ApplicationViewHolder(view: View) : ViewHolder(view) {
        val icon: ImageView = view.findViewById(R.id.app_icon)
        val name: TextView = view.findViewById(R.id.app_name)

        init {
            view.setOnClickListener {
                val pos = adapterPosition
                val context = view.context
                val pm = context.packageManager

                val launchIntent = pm.getLaunchIntentForPackage(data[pos].activityInfo.packageName)
                context?.startActivity(launchIntent)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, position: Int): ApplicationViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_application, null)
        return ApplicationViewHolder(view)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: ApplicationViewHolder, position: Int) {
        val dataNode = data[position]

        holder.icon.setImageDrawable(dataNode.loadIcon(holder.itemView.context.packageManager))
        holder.name.text = dataNode.activityInfo.loadLabel(holder.itemView.context.packageManager)
    }
}