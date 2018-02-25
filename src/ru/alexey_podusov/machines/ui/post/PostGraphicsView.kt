package ru.alexey_podusov.machines.ui.post

import com.trolltech.qt.core.Qt
import com.trolltech.qt.gui.QGraphicsView
import com.trolltech.qt.gui.QPainter
import com.trolltech.qt.gui.QWheelEvent

class PostGraphicsView(scene: PostGraphicsScene) : QGraphicsView(scene) {
    init {
        setRenderHint(QPainter.RenderHint.Antialiasing)
        setVerticalScrollBarPolicy(Qt.ScrollBarPolicy.ScrollBarAlwaysOff)
        setHorizontalScrollBarPolicy(Qt.ScrollBarPolicy.ScrollBarAlwaysOff)
        isInteractive = true
        setDragMode(QGraphicsView.DragMode.ScrollHandDrag)
    }

    override fun wheelEvent(event: QWheelEvent?) {
        setTransformationAnchor(QGraphicsView.ViewportAnchor.AnchorUnderMouse)
        val scaleFactor = 1.15
        if (event!!.delta() > 0) {
            scale(scaleFactor, scaleFactor)
        } else {
            scale(1.0 / scaleFactor, 1.0 / scaleFactor)
        }
    }
}