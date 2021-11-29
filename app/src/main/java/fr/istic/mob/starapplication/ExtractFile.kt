package fr.istic.mob.starapplication

import android.content.ContentValues.TAG
import android.content.Context
import android.util.Log
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.IOException
import java.lang.Exception
import java.util.zip.ZipEntry
import java.util.zip.ZipFile
import java.util.zip.ZipInputStream

class ExtractFile(var context: Context) {

    fun extract(targetPath: String, destinationPath: String) {

        try {
            val f: File = File(targetPath)
            if (!f.isDirectory){
                f.mkdirs()
            }
            ZipFile(f).use { it ->
                it.entries()
                    .asSequence()
                    .filter { !it.isDirectory }
                    .forEach { fl ->
                        if ( Utils(context).files.contains(fl.name) ){
                            val currFile = File(destinationPath, fl.name)
                            currFile.parentFile?.mkdirs()
                            it.getInputStream(fl).use { input ->
                                currFile.outputStream().use { output ->
                                    input.copyTo(output)
                                }
                            }
                        }
                    }
            }
            f.delete()

        }catch (e: IOException){
           Log.i("",e.printStackTrace().toString())
        }


    }
}