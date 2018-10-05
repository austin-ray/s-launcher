package io.austinray.slauncher.icons

import android.content.res.Resources
import org.xmlpull.v1.XmlPullParser

class IconPackParser(private val handler: IconHandler, private val res: Resources) {
    private lateinit var parser: XmlPullParser

    fun parseXml(appFilterId: Int) {
        parser = res.getXml(appFilterId)

        var eventType = parser.eventType

        while (eventType != XmlPullParser.END_DOCUMENT) {
            when (eventType) {
                XmlPullParser.START_TAG -> parseTag()
            }

            eventType = parser.next()
        }
    }

    private fun parseTag() {
        val tagName: String = parser.name

        when (tagName) {
            "item" -> parseTagItem()
        }
    }

    private fun parseTagItem() {
        var componentName = ""
        var drawableName = ""

        (0 until parser.attributeCount).forEach { i ->
            val attributeName = parser.getAttributeName(i)

            when (attributeName) {
                "component" -> componentName = parser.getAttributeValue(i)
                "drawable" -> drawableName = parser.getAttributeValue(i)
            }
        }

        if (!handler.iconPackDrawables.containsKey(componentName)) {
            handler.iconPackDrawables[componentName] = drawableName
        }
    }
}
