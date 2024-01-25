package com.riverpath.petfinderdemo.common

import android.content.ContentProvider
import android.content.ContentValues
import android.database.Cursor
import android.net.Uri
import androidx.annotation.MainThread

/**
 * Extend this to create an initializer content provider.
 *
 * The way it works is: the OS creates all content providers registered in the AndroidManifest
 * right in the beginning, before even calling `Application.onCreate()`. So each content provider's
 * onCreate() method will be called before `Application.onCreate()`, giving us a reliable way to
 * perform early initialization in the `initialize()` method.
 */
abstract class StartupInitializer : ContentProvider() {

    /**
     * Perform initialization logic here. Don't block the main thread.
     */
    @MainThread
    protected abstract fun initialize()
    override fun onCreate(): Boolean {
        initialize()
        return true
    }

    override fun query(
        uri: Uri,
        projection: Array<out String>?,
        selection: String?,
        selectionArgs: Array<out String>?,
        sortOrder: String?
    ): Cursor? = null

    override fun getType(uri: Uri): String? = null

    override fun insert(uri: Uri, values: ContentValues?): Uri? = null

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<out String>?): Int = 0

    override fun update(
        uri: Uri,
        values: ContentValues?,
        selection: String?,
        selectionArgs: Array<out String>?
    ): Int = 0
}