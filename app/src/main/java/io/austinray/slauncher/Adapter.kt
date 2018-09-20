package io.austinray.slauncher

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.provider.Settings
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.RecyclerView.ViewHolder
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import io.austinray.slauncher.Adapter.ApplicationViewHolder
import io.austinray.slauncher.model.AppInfo

class Adapter(var data: List<AppInfo>, private val pm: PackageManager) :
    RecyclerView.Adapter<ApplicationViewHolder>() {

    var filter = ""

    private var filteredData = data
    private var layout: LinearLayoutManager? = null

    inner class ApplicationViewHolder(view: View) : ViewHolder(view) {
        val icon: ImageView = view.findViewById(R.id.app_icon)
        val name: TextView = view.findViewById(R.id.app_name)

        init {
            view.setOnClickListener {
                val context = view.context
                val pos = adapterPosition
                val launchIntent = pm.getLaunchIntentForPackage(filteredData[pos].packageName)
                context?.startActivity(launchIntent)
            }

            view.setOnLongClickListener {
                val context = view.context
                val pos = adapterPosition

                val settingsIntent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                val uri = Uri.fromParts("package", filteredData[pos].packageName, null)

                settingsIntent.data = uri
                settingsIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)

                context?.startActivity(settingsIntent)

                true
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, position: Int): ApplicationViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_application, parent, false)
        return ApplicationViewHolder(view)
    }

    override fun getItemCount(): Int {
        return filteredData.size
    }

    override fun onBindViewHolder(holder: ApplicationViewHolder, position: Int) {
        val dataNode = filteredData[position]

        holder.icon.setImageDrawable(dataNode.icon)
        holder.name.text = dataNode.label
    }

    fun applyFilter(filterStr: String) {
        filter = filterStr

        if (filter.isEmpty()) {
            filteredData = data
            notifyDataSetChanged()
            layout?.scrollToPositionWithOffset(0, 0)
            return
        }

        filteredData = data.filter { app ->
              app.label.split(" ").find { word ->
                  word.toLowerCase()
                      .contains(filterStr.toLowerCase())
              } != null
        }

        notifyDataSetChanged()
    }

    fun reset() {
        applyFilter("")
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        layout = recyclerView.layoutManager as? LinearLayoutManager
        applyFilter("")
    }
}
