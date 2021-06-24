package com.example.paxeltest.utill

import android.annotation.SuppressLint
import android.content.ContentResolver
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.media.ThumbnailUtils
import android.net.Uri
import android.provider.MediaStore
import android.provider.OpenableColumns
import android.webkit.MimeTypeMap
import androidx.exifinterface.media.ExifInterface
import com.bumptech.glide.load.resource.bitmap.TransformationUtils
import com.example.paxeltest.base.BaseApplication
import com.google.android.gms.common.util.IOUtils
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.*
import java.util.*

object FileUtils {
    @SuppressLint("QueryPermissionsNeeded")
    fun addIntentsToList(
        context: Context,
        list: MutableList<Intent>,
        intent: Intent
    ): MutableList<Intent> {
        val resInfo = context.packageManager.queryIntentActivities(intent, 0)
        for (resolveInfo in resInfo) {
            val packageName = resolveInfo.activityInfo.packageName
            val targetedIntent = Intent(intent)
            targetedIntent.setPackage(packageName)
            list.add(targetedIntent)
        }
        return list
    }

    fun createPartFromString(param: String): RequestBody {
        return param.toRequestBody("multipart/form-data".toMediaType())
    }

    fun compressImage(file: File, imgPath: String): File {
        val targetW = 100
        val targetH = 100

        val bmOptions = BitmapFactory.Options().apply {
            inJustDecodeBounds = true
            val photoW: Int = outWidth
            val photoH: Int = outHeight
            val scaleFactor: Int = Math.min(photoW / targetW, photoH / targetH)
            inJustDecodeBounds = false
            inSampleSize = scaleFactor
        }
        val bitmapCompress = changeRotate(imgPath, BitmapFactory.decodeFile(imgPath, bmOptions))
        val byteArrayOutputStream = ByteArrayOutputStream()
        bitmapCompress?.compress(Bitmap.CompressFormat.JPEG, 40, byteArrayOutputStream)
        val fileOutputStream = FileOutputStream(file)
        fileOutputStream.write(byteArrayOutputStream.toByteArray())
        fileOutputStream.flush()
        fileOutputStream.close()
        return file
    }

    private fun changeRotate(path: String, bitmap: Bitmap): Bitmap? {
        val ei = ExifInterface(path)
        val orientation = ei.getAttributeInt(
            ExifInterface.TAG_ORIENTATION,
            ExifInterface.ORIENTATION_UNDEFINED
        )

        return when (orientation) {
            ExifInterface.ORIENTATION_ROTATE_90 -> TransformationUtils.rotateImage(bitmap, 90)
            ExifInterface.ORIENTATION_ROTATE_180 -> TransformationUtils.rotateImage(bitmap, 180)
            ExifInterface.ORIENTATION_ROTATE_270 -> TransformationUtils.rotateImage(bitmap, 270)
            else -> bitmap
        }
    }

    fun writeFile(context: Context, imgPath: String, data: Uri) {
        val inputStream = context.contentResolver?.openInputStream(data)
        val fileOutputStream = FileOutputStream(imgPath)
        copyStream(inputStream!!, fileOutputStream)
        fileOutputStream.close()
        inputStream.close()
    }

    @Throws(IOException::class)
    fun copyStream(input: InputStream, output: OutputStream) {
        val buffer = ByteArray(1024)
        var bytesRead: Int
        while (input.read(buffer).also {
                bytesRead = it
            } != -1) {
            output.write(buffer, 0, bytesRead)
        }
    }

    fun getFileName(uri: Uri): String? {
        var result: String? = null
        if (uri.scheme == "content") {
            val cursor = BaseApplication.applicationContext().contentResolver.query(
                uri,
                null,
                null,
                null,
                null
            )
            cursor.use { it ->
                if (it != null && it.moveToFirst()) {
                    result = it.getString(it.getColumnIndex(OpenableColumns.DISPLAY_NAME))
                }
            }
        }
        if (result == null) {
            result = uri.path
            val cut = result?.lastIndexOf('/')
            if (cut != -1) {
                result = result?.substring(cut ?: 0 + 1)
            }
        }
        return result
    }

    fun getRealPathFromURI(contentURI: Uri): String? {
        val result: String?
        val cursor = BaseApplication.applicationContext().contentResolver.query(
            contentURI,
            null,
            null,
            null,
            null
        )
        if (cursor == null) { // Source is Dropbox or other similar local file path
            result = contentURI.path
        } else {
            cursor.moveToFirst()
            val idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA)
            result = cursor.getString(idx)
            cursor.close()
        }
        return result
    }

    fun getFileInputStream(uri: Uri): File {
        val context = BaseApplication.applicationContext()
        val parcelFileDescriptor = context.contentResolver.openFileDescriptor(uri, "r", null)
        val inputStream = FileInputStream(parcelFileDescriptor?.fileDescriptor)
        val file = File(context.cacheDir, context.contentResolver.getFileName(uri))
        val outputStream = FileOutputStream(file)
        IOUtils.copyStream(inputStream, outputStream)
        return file
    }

    private fun ContentResolver.getFileName(fileUri: Uri): String {
        var name = ""
        val returnCursor = this.query(fileUri, null, null, null, null)
        if (returnCursor != null) {
            val nameIndex = returnCursor.getColumnIndex(OpenableColumns.DISPLAY_NAME)
            returnCursor.moveToFirst()
            name = returnCursor.getString(nameIndex)
            returnCursor.close()
        }
        return name
    }

    fun getThumbnail(path: String): Bitmap? = ThumbnailUtils.createVideoThumbnail(
        path,
        MediaStore.Images.Thumbnails.MINI_KIND
    )

    fun getFileType(mContext: Context, uri: Uri): String {
        var extension = MimeTypeMap.getFileExtensionFromUrl(uri.toString())
        if (extension.isEmpty()) {
            val i: Int = uri.toString().lastIndexOf('.')
            if (i > 0) {
                extension = uri.toString().substring(i + 1)
            }
        }
        val type = if (extension.isNotEmpty()) {
            MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension.toLowerCase(Locale.getDefault()))
        } else mContext.contentResolver.getType(uri)

        return type ?: ""
    }

    fun deleteCache(context: Context) {
        try {
            val dir = context.cacheDir
            deleteDir(dir)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun deleteDir(dir: File?): Boolean {
        return if (dir != null && dir.isDirectory) {
            val children = dir.list()
            for (i in children.indices) {
                val success = deleteDir(File(dir, children[i]))
                if (!success) {
                    return false
                }
            }
            dir.delete()
        } else if (dir != null && dir.isFile) {
            dir.delete()
        } else {
            false
        }
    }
}
